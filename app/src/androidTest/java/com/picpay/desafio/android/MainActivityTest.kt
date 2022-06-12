package com.picpay.desafio.android

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.presentation.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test

class MainActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val contactTitle = context.getString(R.string.title_contacts)
    private val recyclerViewContacts = R.id.recyclerViewContacts
    private val emptyImage = R.id.emptyImageContacts
    private val textLoadingFailed = R.id.tvLoadingFailed
    private val buttonTryAgain = R.id.btTryAgainContacts
    private val threadSleep = 2000L

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {

            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.recyclerViewContacts)).perform()
            onView(withText(contactTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDontDisplayEmptyListImage() {
        launchActivity<MainActivity>().apply {

            onView(withId(textLoadingFailed)).check { view, _ ->
                assert(view.visibility == View.GONE)
            }
        }
    }

    @Test
    fun shouldDontDisplayLoadingFailedText() {
        launchActivity<MainActivity>().apply {

            onView(withId(emptyImage)).check { view, _ ->
                assert(view.visibility == View.GONE)
            }
        }
    }

    @Test
    fun shouldDontDisplayTryAgainButton() {
        launchActivity<MainActivity>().apply {

            onView(withId(buttonTryAgain)).check { view, _ ->
                assert(view.visibility == View.GONE)
            }
        }
    }

    @Test
    fun shouldDisplayLoadingFailedTextWhenContactsListIsEmpty() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponseEmpty
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)
        launchActivity<MainActivity>().apply {
            Thread.sleep(threadSleep)
            onView(withId(textLoadingFailed)).check { view, _ ->
                assert(view.visibility == View.VISIBLE)
            }
        }
        server.close()
    }

    @Test
    fun shouldDisplayEmptyListImageWhenContactsListIsEmpty() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponseEmpty
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)
        launchActivity<MainActivity>().apply {
            Thread.sleep(threadSleep)
            onView(withId(emptyImage)).check { view, _ ->
                assert(view.visibility == View.VISIBLE)
            }
        }
        server.close()
    }

    @Test
    fun shouldDisplayTryAgainButtonWhenContactsListIsEmpty() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponseEmpty
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)
        launchActivity<MainActivity>().apply {
            Thread.sleep(threadSleep)
            onView(withId(buttonTryAgain)).check { view, _ ->
                assert(view.visibility == View.VISIBLE)
            }
        }
        server.close()
    }

    @Test
    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)
        launchActivity<MainActivity>().apply {
            Thread.sleep(threadSleep)
            RecyclerViewMatchers.checkRecyclerViewItem(recyclerViewContacts, 0, isDisplayed())
        }
        server.close()
    }

    companion object {
        private const val serverPort = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Marcio\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"mfklcp\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val successResponseEmpty by lazy {
            val body = ""

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}
