package ru.naumen.android_chat_sdk_example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID
import ru.naumen.android_chat_sdk_example.StartSDKWithFragmentActivity.Companion.AUTH_DATA_EXTRA
import ru.naumen.android_chat_sdk_example.StartSDKWithFragmentActivity.Companion.CONFIG_EXTRA
import ru.naumen.android_chat_sdk_example.StartSDKWithFragmentActivity.Companion.IS_NEW_CONFIG
import ru.naumen.chat.chatsdk.activity.ChatActivity
import ru.naumen.chat.chatsdk.chatapi.config.Config
import ru.naumen.chat.chatsdk.chatapi.model.AuthData

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var activityStartButton: Button
    private lateinit var fragmentStartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityStartButton = findViewById<Button>(R.id.activity_start).also {
            it.setOnClickListener { startChatSDKWithActivity() }
        }

        fragmentStartButton = findViewById<Button>(R.id.fragment_start).also {
            it.setOnClickListener { startChatSDKWithFragment() }
        }
    }

    private fun startChatSDKWithActivity() {
        ChatActivity.start(
            context = this,
            authData = getAuthData(),
            config = getConfig(),
            appToken = null,
            appPushId = null,
            customOkHttpClient = null
        )
    }

    private fun startChatSDKWithFragment() {
        startActivity(
            Intent(this, StartSDKWithFragmentActivity::class.java).apply {
                putExtra(AUTH_DATA_EXTRA, getAuthData())
                putExtra(CONFIG_EXTRA, getConfig())
                putExtra(IS_NEW_CONFIG, true)
            }
        )
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
}