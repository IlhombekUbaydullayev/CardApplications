package com.example.creditcart_inkotlin.dao

import androidx.room.*
import com.example.creditcart_inkotlin.entity.User


@Dao
interface PostDao {


//    @Insert
//    fun createUser(user: User)
//
//    @Query("SELECT * FROM user")
//    fun getUser(): List<User>


    @Insert
    fun createPost(post: User)

    @Query("SELECT * FROM posts")
    fun getPosts(): List<User>



//    @Query("SELECT * FROM posts WHERE id=:id")
//    fun getPostById(id: Int): User
//
//    @Delete()
//    fun deletePostWithDelete(post: User)
//
//    @Query("DELETE FROM posts WHERE id=:id")
//    fun deletePost(id: Int)
//
//    @Update
//    fun updatePostWithUpdate(post: User)
//
//    @Query("UPDATE posts SET title=:title WHERE id=:id")
//    fun updatePost(title: String, id: Int)

}