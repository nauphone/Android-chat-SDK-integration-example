# Пример встраивания NAUMEN Android Chat SDK 

<p align="center">
<img src="https://i.ibb.co/TDy1R7B/Screenshot-20221115-101254.png" alt="drawing" width="340"/>
</p>

## Первичная настройка
- Необходимо выставить в файле [constants.gradle](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/constants.gradle) логин и пароль от maven репозитория для получения доступа к пакету SDK
- Необходимо выставить в файле строк [strings.xml](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/res/values/strings.xml) пустые параметры. Значения параметров можно найти в [документации](https://callcenter.naumen.ru/docs/ru/ncc76/ncc/web/ncc.htm#Integration/MobileSDK_Chat/MobileSDK_Android/MobileSDK_Android_Parameters.htm%3FTocPath%3D%25D0%2598%25D0%25BD%25D1%2582%25D0%25B5%25D0%25B3%25D1%2580%25D0%25B0%25D1%2586%25D0%25B8%25D0%25BE%25D0%25BD%25D0%25BD%25D1%258B%25D0%25B5%2520%25D0%25B2%25D0%25BE%25D0%25B7%25D0%25BC%25D0%25BE%25D0%25B6%25D0%25BD%25D0%25BE%25D1%2581%25D1%2582%25D0%25B8%7CSDK%2520%25D0%25B4%25D0%25BB%25D1%258F%2520%25D0%25B8%25D0%25BD%25D1%2582%25D0%25B5%25D0%25B3%25D1%2580%25D0%25B0%25D1%2586%25D0%25B8%25D0%25B8%2520NCC-%25D1%2587%25D0%25B0%25D1%2582%25D0%25B0%2520%25D0%25B2%2520%25D0%25BC%25D0%25BE%25D0%25B1%25D0%25B8%25D0%25BB%25D1%258C%25D0%25BD%25D1%258B%25D0%25B5%2520%25D0%25BF%25D1%2580%25D0%25B8%25D0%25BB%25D0%25BE%25D0%25B6%25D0%25B5%25D0%25BD%25D0%25B8%25D1%258F%7CNCC-%25D1%2587%25D0%25B0%25D1%2582%2520Android%2520SDK%7C_____4)

## На что обратить внимание при интеграции
- В файле [settings.gradle](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/settings.gradle) можно увидеть настройку репозитория, из которого будут приходить сборки
- В файле [app/build.gradle](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/build.gradle) можно выставить версию SDK
- В файле [MainActivity.kt](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/java/ru/naumen/android_chat_sdk_example/MainActivity.kt) можно увидеть само встраивание SDK через фрагмент. Важно отметить, что toolbar в таком случае встраивается самостоятельно
- В качестве примера тулбар [custom_chat_toolbar.xml](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/res/layout/custom_chat_toolbar.xml) скопирован из самого SDK и встроен как кастомный
- Стоит обратить внимание на [вёрстку-пример](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/res/layout/activity_main.xml) встраивания через фрагмент 
- Стоит обратить внимание на настройку [AndroidManifest.xml](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/AndroidManifest.xml)

## Важно
- В данном примере сессия пользователя сбрасывается при перезапуске приложения. Это ожидаемое поведение, т.к. реализация хранения crmId должна осуществляться на стороне приложения, в которое интегрируется SDK. Сменить поведение в примере можно подставив константу в [MainActivity.kt](https://github.com/nauphone/Android-chat-SDK-integration-example/blob/master/app/src/main/java/ru/naumen/android_chat_sdk_example/MainActivity.kt) в методе getAuthData()