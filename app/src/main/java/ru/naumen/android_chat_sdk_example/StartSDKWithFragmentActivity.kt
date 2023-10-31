package ru.naumen.android_chat_sdk_example

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import ru.naumen.chat.chatsdk.activity.IChatHolder
import ru.naumen.chat.chatsdk.chatapi.config.Config
import ru.naumen.chat.chatsdk.chatapi.model.AuthData
import ru.naumen.chat.chatsdk.fragment.ChatFragment

class StartSDKWithFragmentActivity : AppCompatActivity(R.layout.fragment_integration_activity), IChatHolder {

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
        val extras = requireNotNull(intent.extras)
        val authData = extras.getParcelable(AUTH_DATA_EXTRA) as AuthData?
        val config = extras.getParcelable(CONFIG_EXTRA) as Config?
        val isNewConfig = extras.getBoolean(IS_NEW_CONFIG, false)

        supportFragmentManager
            .beginTransaction()
            .replace(
                findViewById<FragmentContainerView>(R.id.fragment_holder).id,
                ChatFragment.newInstance(
                    authData = authData,
                    config = config,
                    appToken = null,
                    appPushId = null,
                    isNewConfig = isNewConfig,
                    customOkHttpClient = null,
                    chatThemeId = R.style.ChatSDKTheme
                )
            ).commit()
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

    companion object {
        const val AUTH_DATA_EXTRA = "AUTH_DATA_EXTRA"
        const val CONFIG_EXTRA = "CONFIG_EXTRA"
        const val IS_NEW_CONFIG = "IS_NEW_CONFIG"
    }
}