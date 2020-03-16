package com.qverkk.costambookapi.model

import javax.persistence.*

enum class LikeType {
    LIKE,
    DISLIKE
}

@Entity
@Table(name = "likes")
data class Likes(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "like_id", nullable = false, unique = true)
        val id: Long,
        @Column(name = "comment_id", nullable = false)
        val commentId: Long,
        @Column(name = "like_type", nullable = false)
        val type: LikeType
)

data class LikesDTO(
        val id: Long,
        val commentId: Long,
        val type: LikeType
)
