package sa.com.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsObject(
    var `data`: Data,
    var kind: String
):java.io.Serializable

@Serializable
data class Data(
    var after: String,
    var children: List<Children>,
    var dist: Int,
    var modhash: String
):java.io.Serializable

@Serializable
data class DataX(
    var allow_live_comments: Boolean,
    var archived: Boolean,
    var author: String,
    var author_flair_text: String?="",
    var author_flair_type: String,
    var author_fullname: String,
    var author_is_blocked: Boolean,
    var author_patreon_flair: Boolean,
    var author_premium: Boolean,
    var can_gild: Boolean,
    var can_mod_post: Boolean,
    var clicked: Boolean,
    var contest_mode: Boolean,
    var created: Double,
    var created_utc: Double,
    var domain: String,
    var downs: Int,
    var gilded: Int,
    var gildings: Gildings?,
    var hidden: Boolean,
    var hide_score: Boolean,
    var id: String,
    var is_created_from_ads_ui: Boolean,
    var is_crosspostable: Boolean,
    var is_meta: Boolean,
    var is_original_content: Boolean,
    var is_reddit_media_domain: Boolean,
    var is_robot_indexable: Boolean,
    var is_self: Boolean,
    var is_video: Boolean,
    var link_flair_background_color: String,
    var link_flair_text_color: String,
    var link_flair_type: String,
    var locked: Boolean,
    var media_embed: MediaEmbed?,
    var media_only: Boolean,
    var name: String,
    var no_follow: Boolean,
    var num_comments: Int,
    var num_crossposts: Int,
    var over_18: Boolean,
    var parent_whitelist_status: String,
    var permalink: String,
    var pinned: Boolean,
    var pwls: Int,
    var quarantine: Boolean,
    var saved: Boolean,
    var score: Int,
    var secure_media_embed: SecureMediaEmbed?,
    var selftext: String,
    var send_replies: Boolean,
    var spoiler: Boolean,
    var stickied: Boolean,
    var subreddit: String,
    var subreddit_id: String,
    var subreddit_name_prefixed: String,
    var subreddit_subscribers: Int,
    var subreddit_type: String,
    var thumbnail: String,
    var title: String,
    var total_awards_received: Int,
    var ups: Int,
    var upvote_ratio: Double,
    var url: String,
    var user_reports: List<String>,
    var visited: Boolean,
    var whitelist_status: String,
    var wls: Int,
    var media: Media? = null
):java.io.Serializable

@Serializable
data class Children(
    var `data`: DataX,
    var kind: String
):java.io.Serializable

@Serializable
data class Oembed(
    var author_name: String,
    var author_url: String,
    var height: Int,
    var html: String,
    var provider_name: String,
    var provider_url: String,
    var thumbnail_height: Int,
    var thumbnail_url: String,
    var thumbnail_width: Int,
    var title: String,
    var type: String,
    var version: String,
    var width: Int
):java.io.Serializable

@Serializable
class SecureMediaEmbed:java.io.Serializable
@Serializable
class MediaEmbed:java.io.Serializable
@Serializable
class Gildings:java.io.Serializable
@Serializable
data class Media(var oembed: Oembed, var type: String):java.io.Serializable
