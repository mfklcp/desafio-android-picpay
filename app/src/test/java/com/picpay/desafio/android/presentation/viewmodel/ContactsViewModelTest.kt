package com.picpay.desafio.android.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUserUseCase
import com.picpay.desafio.android.util.UserFactory.createPicPayResultError
import com.picpay.desafio.android.util.UserFactory.createPicPayResultSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactsViewModelTest {

    private lateinit var userUseCase: GetUserUseCase
    private lateinit var userViewModel: ContactsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userUseCase = mockk(relaxed = true)
        userViewModel = ContactsViewModel(userUseCase)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun `should init list user when call init method and return is success`() {
        coEvery { userUseCase.invoke() } returns createPicPayResultSuccess()

        userViewModel.init()

        assertEquals(
            listOf(
                User(1, "img", "name", "username"),
                User(2, "img2", "name2", "username2"),
                User(3, "img3", "name3", "username3"),
            ),
            userViewModel.contactsList.value
        )
    }

    @Test
    fun `should init fault property when call init method and return is error`() {
        coEvery { userUseCase.invoke() } returns createPicPayResultError()

        userViewModel.init()

        assertEquals(
            "GENERIC ERROR",
            userViewModel.failure.value
        )
    }

}
