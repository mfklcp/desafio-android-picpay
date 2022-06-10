package com.picpay.desafio.android.service.repository

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.factory.UserFactory.createListUserResponse
import com.picpay.desafio.android.service.api.PicPayApi
import com.picpay.desafio.android.service.mapper.UserMapper
import com.picpay.desafio.android.utils.PicPayResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeoutException

class GetUserRepositoryTest {

    private lateinit var getUserRepository: GetUserRepository
    private lateinit var picPayApi: PicPayApi

    @Before
    fun setUp() {
        picPayApi = mockk(relaxed = true)
        getUserRepository = GetUserRepository(picPayApi, UserMapper())
    }

    @Test
    fun `should return User list when call succeed in alphabetic order`() =
        runBlocking {
            coEvery { picPayApi.getUsers() } returns createListUserResponse()

            val result: PicPayResult<List<User>> = getUserRepository.getListUsers()

            coVerify(exactly = 1) {
                picPayApi.getUsers()
            }

            assertThat(result, instanceOf(PicPayResult.Success::class.java))
            assertEquals(
                listOf(
                    User(1, "img", "name", "username"),
                    User(2, "img2", "name2", "username2"),
                    User(3, "img3", "name3", "username3"),
                ),
                (result as PicPayResult.Success).data
            )
        }

    @Test
    fun `should return type error when the api search returns an empty list`() =
        runBlocking {
            coEvery { picPayApi.getUsers() } returns listOf()

            val result: PicPayResult<List<User>> = getUserRepository.getListUsers()

            coVerify(exactly = 1) {
                picPayApi.getUsers()
            }

            assertThat(result, instanceOf(PicPayResult.Error::class.java))
            assertEquals("Falhou! A lista veio vazia :-(", (result as PicPayResult.Error).exception)
        }

    @Test
    fun `should throw exception when api fails`() =
        runBlocking {
            coEvery { picPayApi.getUsers() } throws TimeoutException()

            val result: PicPayResult<List<User>> = getUserRepository.getListUsers()

            coVerify(exactly = 1) {
                picPayApi.getUsers()
            }

            assertThat(result, instanceOf(PicPayResult.Error::class.java))
            assertEquals("java.util.concurrent.TimeoutException", (result as PicPayResult.Error).exception)
        }

}
