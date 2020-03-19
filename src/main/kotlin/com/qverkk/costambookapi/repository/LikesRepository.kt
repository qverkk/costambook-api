package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface LikesRepository : JpaRepository<Likes, Long> {
    fun findAllByPost(post: Post): List<LikesDTO>
    fun findAllByPostEquals(post: Post): List<Likes>

    @Modifying
    @Transactional
    @Query("update Likes l set l.type =?3 where l.user.userId =?1 and l.post.postId =?2")
    fun setLikeByUserAndPost(user: Long, post: Long, likeType: LikeType)

    @Query(
            "select new com.qverkk.costambookapi.model.LikesDTO(l.id, l.type) from Likes l where l.user.userId = ?1 and l.post.postId = ?2"
    )
    fun getLikeByUserIdAndPostId(userId: Long, postId: Long): LikesDTO?

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into likes (like_id, post_id, user_id, like_type) values (like_id_seq.nextval, :postId, :userId, :likeType)"
    )
    fun saveNewLike(@Param("userId") userId: Long, @Param("postId") postId: Long, @Param("likeType") ordinal: Int)
}