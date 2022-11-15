package sa.com.data.local.articles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Entity(tableName = "articles", indices = [Index(value = ["id"], unique = true)])
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "img")
    val img: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
):java.io.Serializable{
    constructor(img:String ,title: String, body: String) : this(0, img, title,body)
}