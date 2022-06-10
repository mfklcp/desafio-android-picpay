package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.UserFactory.createListUser
import com.picpay.desafio.android.service.repository.GetUserRepository
import com.picpay.desafio.android.utils.PicPayResult
import com.picpay.desafio.android.utils.extensions.get
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getUserRepository: GetUserRepository

    @Before
    fun setUp() {
        getUserRepository = mockk(relaxed = true)
        getUserUseCase = GetUserUseCase(getUserRepository)
    }

    @Test
    fun `should return PicPayResult of User list when call succeed`() =
        runBlocking {
            coEvery { getUserRepository.getListUsers() } returns PicPayResult.Success(createListUser())

            val result = getUserRepository.getListUsers()

            coVerify(exactly = 1) {
                getUserRepository.getListUsers()
            }

            Assert.assertEquals(
                listOf(
                    User(1, "img", "name", "username"),
                    User(2, "img2", "name2", "username2"),
                    User(3, "img3", "name3", "username3"),
                ),
                result.get()
            )
        }
}
