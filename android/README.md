# Firebase Analytics WebView Quickstart

This sample will demonstrate how to use Firebase Analytics from a WebView
in your Android application.

## Setup

  * Follow the instructions to [add Firebase to your Android Project](https://firebase.google.com/docs/android/setup).
    * If you are working on Android and iOS, use the same project for both
      halves of this sample.
  * Run the application and watch `logcat`.
  * To see more detailed logs in `logcat` for Firebase Analytics, follow
    the [instructions here](https://firebase.google.com/docs/analytics/android/events#view_events_in_the_android_studio_debug_log)
    to enable verbose logging.

## Architecture

  * `MainActivity` - Activity to host a `WebView` that loads the content of
    your website in Firebase Hosting.
  * `AnalyticsWebInterface` - class to forward events from JavaScript to
    the Firebase Analytics Android SDK.
  * `SingleDomainWebViewClient` - subclass of WebViewClient to restrict a
    WebView to a single domain. This is a recommended security precaution
    when allowing JavaScript to call native Java code.
