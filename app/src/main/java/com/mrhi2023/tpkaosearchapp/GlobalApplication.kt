package com.mrhi2023.tpkaosearchapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //Kakao SDK 초기화 - 개발자 사이트에 등록한 [네이티브 앱키]
        KakaoSdk.init(this, "dbe04356c52b86527f7721db3c0d5bf0")
    }
}