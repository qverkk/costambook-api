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
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        val post: Post,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,
        @Column(name = "like_type", nullable = false)
        val type: LikeType
)

data class LikesDTO(
        val id: Long,
        val post: Post,
        val user: User,
        val type: LikeType
)
