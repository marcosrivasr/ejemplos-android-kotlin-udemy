package com.vidamrr.checkins.Interfaces

import com.vidamrr.checkins.Foursquare.Photo
import com.vidamrr.checkins.Foursquare.Photos


interface ImagePreviewInterface {

    fun obtenerImagePreview(photos: ArrayList<Photo>)
}