package ru.naumen.android_chat_sdk_example.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import ru.naumen.android_chat_sdk_example.MainActionListener
import ru.naumen.android_chat_sdk_example.R
import ru.naumen.android_chat_sdk_example.model.SDKIntegrationType
import ru.naumen.chat.chatsdk.activity.ChatActivity
import ru.naumen.chat.chatsdk.chatapi.config.Config
import ru.naumen.chat.chatsdk.chatapi.model.AuthData
import java.util.UUID

class MainActivity : AppCompatActivity(R.layout.activity_main), MainActionListener {

    private var chatFragment: ChatFragmentWithToolbar? = null
    private var mainMenuFragment: MainMenuFragment? = null

    private lateinit var fragmentHolder: FrameLayout
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var bottomNavListener: NavigationBarView.OnItemSelectedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentHolder = findViewById(R.id.fragment_holder)
        if (supportFragmentManager.findFragmentById(R.id.fragment_holder) == null) {
            inflateMainMenuFragment()
        }

        setupBottomNavigationListener()
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener(bottomNavListener)

        supportFragmentManager.addOnBackStackChangedListener {
            updateBottomNavigationState()
        }
    }

    private fun setupBottomNavigationListener() {
        bottomNavListener = NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_page -> {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
                    if (currentFragment !is MainMenuFragment) {
                        inflateMainMenuFragment()
                    }
                    true
                }

                R.id.chat_page -> {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
                    if (currentFragment !is ChatFragmentWithToolbar) {
                        inflateChatFragment()
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun disableBottomNavigationListener() {
        bottomNavigation.setOnItemSelectedListener(null)
    }

    private fun enableBottomNavigationListener() {
        bottomNavigation.setOnItemSelectedListener(bottomNavListener)
    }

    private fun setBottomNavigationItemSafely(itemId: Int) {
        disableBottomNavigationListener()
        bottomNavigation.selectedItemId = itemId
        enableBottomNavigationListener()
    }

    private fun inflateMainMenuFragment() {
        if (mainMenuFragment == null) {
            mainMenuFragment = MainMenuFragment()
        }

        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
            .beginTransaction()
            .replace(fragmentHolder.id, requireNotNull(mainMenuFragment))
            .commit()
    }

    private fun startChatSDKWithActivity() {
        ChatActivity.start(
            context = this,
            authData = getAuthData(),
            config = getConfig(),
            appPushId = null,
            customOkHttpClient = null
        )
    }

    private fun inflateChatFragment() {
        if (chatFragment == null) {
            chatFragment = ChatFragmentWithToolbar.newInstance(
                authData = getAuthData(),
                config = getConfig(),
                appPushId = null,
            )
        }

        supportFragmentManager
            .beginTransaction()
            .replace(fragmentHolder.id, requireNotNull(chatFragment))
            .addToBackStack("backstack_id")
            .commit()
    }

    private fun getConfig(): Config {
        val apiHost = resources.getString(R.string.nchat_api_host)
        val wsHost = resources.getString(R.string.nchat_ws_host)
        val showcaseId = resources.getString(R.string.nchat_showcase_id).toLongOrNull()

        if (apiHost.isEmpty() || wsHost.isEmpty() || showcaseId == null) {
            throw UnsupportedOperationException(resources.getString(R.string.params_empty_error_message))
        }

        return Config(apiHost, wsHost, showcaseId)
    }

    private fun getAuthData(): AuthData {
        return AuthData(UUID.randomUUID().toString())
    }

    private fun updateBottomNavigationState() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
        when (currentFragment) {
            is MainMenuFragment -> {
                setBottomNavigationItemSafely(R.id.main_menu_page)
            }

            is ChatFragmentWithToolbar -> {
                setBottomNavigationItemSafely(R.id.chat_page)
            }
        }
    }

    override fun onOpenChatClick(integrationType: SDKIntegrationType) {
        when (integrationType) {
            SDKIntegrationType.ACTIVITY -> startChatSDKWithActivity()
            SDKIntegrationType.FRAGMENT -> {
                inflateChatFragment()
                setBottomNavigationItemSafely(R.id.chat_page)
            }
        }
    }
}