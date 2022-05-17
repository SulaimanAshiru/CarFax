package net.carfax.view.list

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.carfax.R
import net.carfax.api.model.AssignmentModel
import net.carfax.databinding.ActivityMainBinding
import net.carfax.room.Assignment
import net.carfax.room.AssignmentDao
import net.carfax.utils.DexterPermissions
import net.carfax.utils.checkInternetConnection
import net.carfax.utils.loaderManager
import net.carfax.utils.showSnackBar
import net.carfax.view.detail.DetailActivity
import net.carfax.viewmodel.AssignmentViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),AssignmentAdapter.AssignmentCallbacks {
    lateinit var viewBinding: ActivityMainBinding
    private val viewModel: AssignmentViewModel by viewModels()
    var detailList= ArrayList<AssignmentModel.ListingObject>()
    lateinit var assignmentAdapter: AssignmentAdapter
    private val permissions = arrayListOf(Manifest.permission.CALL_PHONE)

    @Inject
    lateinit var assignmentDao: AssignmentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        if (!checkInternetConnection())
        {
// get data from room database and inflate the data into lists
            CoroutineScope(Dispatchers.IO).launch {
                async {
                    val response=  assignmentDao.getAssignment().assignments
                    if(response!=null)
                    {
                        detailList= response.listings.clone() as ArrayList<AssignmentModel.ListingObject>
                        runOnUiThread {
                            setUpList()
                        }
                    }
                }
            }
        }
        else
            viewModel.assignments()
        observeViewModel()
    }

    // function to set up detail list
    private fun setUpList()
    {
        assignmentAdapter= AssignmentAdapter(detailList,this)
        viewBinding.rvCars.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=assignmentAdapter
        }
    }

    /**
     * API IMPLEMENTATION
     */
    private fun observeViewModel()
    {
        viewModel.assignmentLive.observe(this){
            loaderManager(it.isLoading)
            when(it.success)
            {
                true->
                {
                    val response= it.response as AssignmentModel
                    detailList= response.listings.clone() as ArrayList<AssignmentModel.ListingObject>
                    setUpList()
                    val assignment= Assignment(0,
                        response
                    )

                    // saving the response into room so that it can be used even when
                    // device is not connected to the network
                    CoroutineScope(Dispatchers.IO).launch {
                        async {
                            assignmentDao.saveAssignment(assignment)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    override fun call(number: String) {

        DexterPermissions.askPermission(
            this@MainActivity,
            permissions,
            object : DexterPermissions.PermissionCallback {
                override fun granted() {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$number")
                    startActivity(intent)
                }

                override fun denied() {
                    viewBinding.root.showSnackBar(resources.getString(R.string.permission_message))
                }

                override fun error(error: String) {

                }
            })
    }

    override fun moveToDetails(position: Int) {
        startActivity(Intent(this,DetailActivity::class.java)
            .putExtra("detail",detailList[position]))
    }
}