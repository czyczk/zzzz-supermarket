package com.zzzz.dao

import com.zzzz.enum.UserTypeEnum
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDaoOneTimeTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var userDao: UserDao

    /**
     * insert (!!!One-time test)
     */
    @Test
    fun testInsertOneTime() {
        val username1 = "Administrator Tester 1"
        val password = "asdf"
        val type1 = UserTypeEnum.ADMINISTRATOR

        val username2 = "Clerk Tester 1"
        val type2 = UserTypeEnum.CLERK

        val username3 = "CS Worker Tester 1"
        val type3 = UserTypeEnum.CS_WORKER

        var rAffected = userDao.insert(username1, password, type1)
        assertEquals(1, rAffected)
        rAffected = userDao.insert(username2, password, type2)
        assertEquals(1, rAffected)
        rAffected = userDao.insert(username3, password, type3)
        assertEquals(1, rAffected)
    }
}