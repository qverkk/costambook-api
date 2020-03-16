package com.qverkk.costambookapi.model

import javax.persistence.*

@Entity
@Table(name = "posts")
data class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "post_id", unique = true, nullable = false)
        val postId: Long?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        var user: User?,
        val description: String,
        @Lob
        @Column(name = "image", nullable = true)
        val image: ByteArray?
) {
    constructor() : this(null, null, "", null)

//    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    val likes = mutableListOf<Likes>()
//
//    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    val comments = mutableListOf<Comments>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (postId != other.postId) return false
        if (description != other.description) return false
        if (user != other.user) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }

}

data class PostDTO(
        val postId: Long?,
        var user: User?,
        val description: String,
        val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostDTO

        if (postId != other.postId) return false
        if (user != other.user) return false
        if (description != other.description) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postId.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}