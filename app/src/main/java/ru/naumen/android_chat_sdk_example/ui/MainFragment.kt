package ru.naumen.android_chat_sdk_example.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.naumen.android_chat_sdk_example.MainActionListener
import ru.naumen.android_chat_sdk_example.R
import ru.naumen.android_chat_sdk_example.model.SDKIntegrationType

class MainMenuFragment : Fragment() {

    private lateinit var continueChatButtonActivity: Button
    private lateinit var continueChatButtonFragment: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false).also { view ->
            setupViews(view)
            setupClickListeners()
        }
    }

    private fun setupViews(view: View) {
        continueChatButtonFragment = view.findViewById(R.id.fragment_start)
        continueChatButtonActivity = view.findViewById(R.id.activity_start)
    }

    private fun setupClickListeners() {
        continueChatButtonActivity.setOnClickListener {
            (activity as MainActionListener).onOpenChatClick(
                SDKIntegrationType.ACTIVITY
            )
        }
        continueChatButtonFragment.setOnClickListener {
            (activity as MainActionListener).onOpenChatClick(
                SDKIntegrationType.FRAGMENT
            )
        }
    }
}