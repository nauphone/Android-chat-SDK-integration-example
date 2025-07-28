package ru.naumen.android_chat_sdk_example.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.naumen.android_chat_sdk_example.R
import ru.naumen.chat.chatsdk.activity.IChatHolder
import ru.naumen.chat.chatsdk.chatapi.config.Config
import ru.naumen.chat.chatsdk.chatapi.model.AuthData
import ru.naumen.chat.chatsdk.fragment.ChatFragment

class ChatFragmentWithToolbar : Fragment(), IChatHolder {

    private var customToolbar: Toolbar? = null
    private var customOperatorAvatar: ImageView? = null
    private var customOperatorName: TextView? = null
    private var customOperatorIsTyping: TextView? = null
    private var customVideoCallButton: ImageView? = null
    private var customSaveChatButton: ImageView? = null

    private lateinit var fragmentHolder: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_with_custom_toolbar, container, false)
            .also { view ->
                customOperatorAvatar = view.findViewById(R.id.operator_avatar)
                customOperatorName = view.findViewById(R.id.operator_name)
                customOperatorIsTyping = view.findViewById(R.id.operator_is_typing)
                customVideoCallButton = view.findViewById(R.id.video_call_button)
                customSaveChatButton = view.findViewById(R.id.send_chat_history)
                customToolbar = view.findViewById(R.id.chat_toolbar)
                fragmentHolder = view.findViewById(R.id.fragment_holder)

                if (childFragmentManager.findFragmentById(R.id.fragment_holder) == null) {
                    inflateChatFragment()
                }
            }
    }

    private fun inflateChatFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(
                fragmentHolder.id,
                ChatFragment.newInstance(
                    authData = getAuthData(),
                    config = getConfig(),
                    appPushId = getAppPushId(),
                    customOkHttpClient = null,
                    chatThemeId = R.style.ChatSDKTheme
                )
            ).commit()
    }

    private fun getAuthData(): AuthData {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireNotNull(requireArguments().getParcelable(AUTH_DATA_EXTRA, AuthData::class.java))
        } else {
            @Suppress("DEPRECATION")
            requireNotNull(requireArguments().getParcelable(AUTH_DATA_EXTRA)) as AuthData
        }
    }

    private fun getConfig(): Config {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireNotNull(requireArguments().getParcelable(CONFIG_EXTRA, Config::class.java))
        } else {
            @Suppress("DEPRECATION")
            requireNotNull(requireArguments().getParcelable(CONFIG_EXTRA)) as Config
        }
    }

    private fun getAppPushId(): String? {
        return requireArguments().getString(PUSH_TOKEN)
    }

    override fun getOperatorAvatarImageView(): ImageView? {
        return customOperatorAvatar
    }

    override fun getOperatorNameTextView(): TextView? {
        return customOperatorName
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
        private const val AUTH_DATA_EXTRA = "AUTH_DATA_EXTRA"
        private const val CONFIG_EXTRA = "CONFIG_EXTRA"
        private const val PUSH_TOKEN = "PUSH_TOKEN"

        fun newInstance(
            authData: AuthData,
            config: Config,
            appPushId: String?
        ): ChatFragmentWithToolbar {
            return ChatFragmentWithToolbar().apply {
                val bundle = Bundle()
                bundle.putParcelable(AUTH_DATA_EXTRA, authData)
                bundle.putParcelable(CONFIG_EXTRA, config)
                bundle.putString(PUSH_TOKEN, appPushId)
                arguments = bundle
            }
        }
    }
}