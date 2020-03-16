package com.qverkk.costambookapi.model

import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comments(
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "comment_id", unique = true, nullable = false)
      val id: Long,
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "post_id", nullable = false)
      val post: Post,
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id", nullable = false)
      val user: User,
      @Column(name = "text", nullable = false)
      val text: String
)

data class CommentsDTO(
        val id: Long,
        val post: Post,
        val user: User,
        val text: String
)