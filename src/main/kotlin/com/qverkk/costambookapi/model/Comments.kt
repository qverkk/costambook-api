package com.qverkk.costambookapi.model

import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comments(
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "comment_id", unique = true, nullable = false)
      val id: Long,
      @Column(name = "post_id", nullable = false)
      val postId: Long,
      @Column(name = "user_id", nullable = false)
      val userId: Long,
      @Column(name = "text", nullable = false)
      val text: String
)

data class CommentsDTO(
        val id: Long,
        val postId: Long,
        val userId: Long,
        val text: String
)