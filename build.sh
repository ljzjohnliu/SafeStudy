#!/bin/sh
./gradlew clean
./gradlew :safe-app:assembleDebug
adb install -r safe-app/build/outputs/apk/debug/safe-app-debug.apk
adb shell am start -n com.study.android/com.study.android.testfrag.MainActivity