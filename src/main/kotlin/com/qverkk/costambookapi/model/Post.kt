package com.qverkk.costambookapi.model

import javax.persistence.*

@Entity
@Table(name = "posts")
data class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "post_id", unique = true, nullable = false)
        val id: Long,
        @Column(name = "user_id", nullable = false)
        val userId: Long,
        val description: String,
        @Lob
        @Column(name = "image", nullable = true, columnDefinition = "mediumblob")
        val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (description != other.description) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

data class PostDTO(
        val id: Long,
        val userId: Long,
        val description: String,
        val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostDTO

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (description != other.description) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}