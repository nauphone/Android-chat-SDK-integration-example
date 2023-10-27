package ru.naumen.android_chat_sdk_example

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.naumen.chat.chatsdk.activity.IChatHolder
import ru.naumen.chat.chatsdk.chatapi.config.Config
import ru.naumen.chat.chatsdk.chatapi.model.AuthData
import ru.naumen.chat.chatsdk.fragment.ChatFragment
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main), IChatHolder {

    private var customToolbar: Toolbar? = null
    private var customOperatorAvatar: ImageView? = null
    private var customOperatorName: TextView? = null
    private var customOperatorIsTyping: TextView? = null
    private var customRatingButton: ImageView? = null
    private var customVideoCallButton: ImageView? = null
    private var customSaveChatButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        customToolbar = findViewById(R.id.chat_toolbar)
        customOperatorAvatar = findViewById(R.id.operator_avatar)
        customOperatorName = findViewById(R.id.operator_name)
        customOperatorIsTyping = findViewById(R.id.operator_is_typing)
        customRatingButton = findViewById(R.id.rating_button)
        customSaveChatButton = findViewById(R.id.send_chat_history)
        customVideoCallButton = findViewById(R.id.video_call_button)
    }

    override fun onStart() {
        super.onStart()
        startChat()
    }

    private fun startChat() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                findViewById<FrameLayout>(R.id.fragment_holder).id,
                ChatFragment.newInstance(getAuthData(), getConfig(), null, null, true)
            )
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

    override fun getOperatorAvatarImageView(): ImageView? {
        return customOperatorAvatar
    }

    override fun getOperatorNameTextView(): TextView? {
        return customOperatorName
    }

    override fun ratingButton(): ImageView? {
        return customRatingButton
    }

    override fun getOperatorIsTypingTextView(): TextView? {
        return customOperatorIsTyping
    }

    override fun getVideoCallButton(): ImageView? {
        return customVideoCallButton
    }

    override fun getSaveChatButton(): ImageView? {
        return customSaveChatButton
    }

    override fun getToolbar(): Toolbar? {
        return customToolbar
    }
}