package com.zzzz.dao

import com.zzzz.enum.UserTypeEnum
import com.zzzz.model.User
import org.apache.ibatis.annotations.Param
import org.springframework.dao.DuplicateKeyException

/**
 * User DAO
 */
interface UserDao {
    /**
     * Query a user by an ID
     * @param id The ID of the user
     * @return The required user or null if not found
     */
    fun queryById(id: Long): User?

    /**
     * Query a user by a username
     * @param username The username of the user
     * @return The required user or null if not found
     */
    fun queryByUsername(username: String): User?

    /**
     * Query users by a user type
     * @param type The type of the users
     * @return A list of users of that type
     */
    fun queryByType(type: UserTypeEnum): List<User>

    /**
     * Insert a user
     * @param username The username (should be unique)
     * @param password The password
     * @param type The user type
     * @return Number of rows affected. 1 if succeeded or 0 if not (can be caused by a username conflict).
     */
    fun insert(@Param("username") username: String,
               @Param("password") password: String,
               @Param("type") type: UserTypeEnum): Int

    /**
     * Update a user
     * @param id The ID of the user to be updated
     * @param username Specified if an update is needed
     * @param password Specified if an update is needed
     * @param type Specified if an update is needed
     * @throws DuplicateKeyException Thrown when meeting a username conflict
     */
    @Throws(DuplicateKeyException::class)
    fun update(@Param("id") id: Long,
               @Param("username") username: String?,
               @Param("password") password: String?,
               @Param("type") type: UserTypeEnum?): Int
}