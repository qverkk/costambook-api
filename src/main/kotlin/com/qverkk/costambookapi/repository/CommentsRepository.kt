package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.Comments
import com.qverkk.costambookapi.model.CommentsDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CommentsRepository: JpaRepository<Comments, Long> {

    @Query("select new com.qverkk.costambookapi.model.CommentsDTO(c.id, c.user.username, c.user.userId, c.text, c.date) from Comments c where c.post.postId = ?1 order by c.date")
    fun findAllByPostId(postId: Long): List<CommentsDTO>

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into comments (comment_id, user_id, post_id, text, add_time) values (comments_id_seq.nextval, ?1, ?2, ?3, SYSDATE)"
    )
    fun addNewComment(userId: Long, postId: Long, text: String)
}