package net.carfax.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.carfax.api.BaseCallbackState
import net.carfax.api.GetDetailsRepository
import net.carfax.api.ResponseWrapper
import net.carfax.api.model.AssignmentModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val repository: GetDetailsRepository,
) : ViewModel() {


    /**
     * assignment -GET
     */

    private var assignmentMutable = MutableLiveData<BaseCallbackState>()
    val assignmentLive: LiveData<BaseCallbackState> get() = assignmentMutable

    fun assignments() {
        viewModelScope.launch(Dispatchers.IO) {
            assignmentMutable.postValue(
                BaseCallbackState(
                    isLoading = true,
                )
            )
            when (val serverResponse = repository.getData()) {
                is ResponseWrapper.Success -> {
                    (serverResponse.value as Response<AssignmentModel>).apply {
                        when (code()) {
                            200 -> {
                                body().apply {
                                    assignmentMutable.postValue(
                                        BaseCallbackState(
                                            isLoading = false,
                                            success = true,
                                            response = this
                                        )
                                    )
                                }
                            }

                            else -> {
                                assignmentMutable.postValue(
                                    BaseCallbackState(
                                        isLoading = false,
                                        success = false,
                                        message = "Issue from backend. Received status code: ${code()}"
                                    )
                                )
                            }
                        }
                    }
                }
                is ResponseWrapper.Error -> {
                    assignmentMutable.postValue(
                        BaseCallbackState(
                            isLoading = false,
                            success = false,
                            message = serverResponse.value.toString()
                        )
                    )
                }
            }
        }
    }
}