package com.qverkk.costambookapi.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comments(
        @Id
        @SequenceGenerator(name = "id_seq", sequenceName = "comments_id_seq", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
        @Column(name = "comment_id", unique = true, nullable = false)
        val id: Long,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        val post: Post,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,
        @Column(name = "text", nullable = false)
        val text: String,
        @Column(name = "addTime", nullable = false)
        val date: Date
)

data class CommentsDTO(
        val id: Long,
        val username: String,
        val userId: Long,
        val text: String,
        val date: Date
)