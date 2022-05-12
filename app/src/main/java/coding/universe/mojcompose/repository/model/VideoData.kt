package coding.universe.mojcompose.repository.model

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("id") val id : Int,
    @SerializedName("username") val userName : String,
    @SerializedName("caption") val caption : String,
    @SerializedName("video_url") val videoUrl : String,
    @SerializedName("profile_pic") val profilePic : String,
    @SerializedName("music_pic") val musicPic : String,
    @SerializedName("like_count") val likeCount : String,
    @SerializedName("comment_count") val commentCount : String,
    @SerializedName("share_count") val shareCount : String,
    @SerializedName("genre") val genre : String
)
