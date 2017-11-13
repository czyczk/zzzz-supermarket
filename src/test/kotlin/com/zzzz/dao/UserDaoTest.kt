package com.zzzz.dao

import com.zzzz.enum.UserTypeEnum
import com.zzzz.model.User
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDaoTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var userDao: UserDao

    /**
     * queryById, queryByUsername
     */
    @Test
    fun testQueryOne() {
        val id = 1L
        val username = "root"
        val defaultPassword = "root"
        val enumOfAdministrator = UserTypeEnum.ADMINISTRATOR

        // Query by ID
        val user1 = userDao.queryById(id)

        // A user object should be correctly fetched
        assertTrue(user1 != null)
        user1 as User

        // The properties should be the same as expected
        assertEquals(id, user1.id)
        assertEquals(username, user1.username)
        assertEquals(defaultPassword, user1.password)
        assertEquals(enumOfAdministrator, user1.type)

        // Query by username
        val user2 = userDao.queryByUsername(username)

        // The two objects should have the same properties
        assertTrue(user1 == user2)
    }

    /**
     * queryByType
     */
    @Test
    fun testQueryByType() {
        val enumAdministrator = UserTypeEnum.ADMINISTRATOR
        val enumClerk = UserTypeEnum.CLERK
        val enumCsWorker = UserTypeEnum.CS_WORKER

        val listAdministrator = userDao.queryByType(enumAdministrator)
        val listClerk = userDao.queryByType(enumClerk)
        val listCsWorker = userDao.queryByType(enumCsWorker)

        assertTrue(listAdministrator.filterNot { it.type == enumAdministrator }.isEmpty())
        assertTrue(listClerk.filterNot { it.type == enumClerk }.isEmpty())
        assertTrue(listCsWorker.filterNot { it.type == enumCsWorker }.isEmpty())
    }

    /**
     * insert (Insert a user with the username already present)
     */
    @Test
    fun testInsertUsernameConflict() {
        val conflictingUsername = "root"
        val password = "whatever"
        val type = UserTypeEnum.ADMINISTRATOR

        val rAffected = userDao.insert(conflictingUsername, password, type)
        assertEquals(0, rAffected)
    }
}