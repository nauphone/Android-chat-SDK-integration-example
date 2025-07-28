package ru.naumen.android_chat_sdk_example

import ru.naumen.android_chat_sdk_example.model.SDKIntegrationType

interface MainActionListener {

    fun onOpenChatClick(integrationType: SDKIntegrationType)
}