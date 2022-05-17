package net.carfax.api.model

import java.io.Serializable

class AssignmentModel(
    var listings: ArrayList<ListingObject>
):Serializable {
    inner class ListingObject(
        var year: Int,
        var listPrice:Long,
        var mileage:Long,
        var make:String,
        var exteriorColor:String,
        var interiorColor:String,
        var drivetype:String,
        var transmission:String,
        var fuel:String,
        var engine:String,
        var dealer: DealerObject,
        var model :String,
        var trim:String,
        var images: ImagesObject
    ):Serializable
    {
        inner class DealerObject(
            var address:String,
            var phone:String
        ):Serializable
        inner class ImagesObject(
            var firstPhoto: FirstPhotoObject
        ):Serializable
        {
            inner class FirstPhotoObject(
                var medium:String
            ):Serializable
        }
    }
}