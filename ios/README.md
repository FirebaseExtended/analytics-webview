# Firebase Analytics WebView Quickstart

This sample will demonstrate how to use Firebase Analytics from a `WKWebView`
in your iOS application.

## Setup

  * Follow the instructions to [add Firebase to your iOS Project](https://firebase.google.com/docs/ios/setup).
    * If you are working on Android and iOS, use the same project for both
      halves of this sample.
  * Run the application and watch Xcode's console.
  * To see more detailed logs in console for Firebase Analytics, follow
    the [instructions here](https://firebase.google.com/docs/analytics/ios/events#view_events_in_the_xcode_debug_console)
    to enable verbose logging.

## Architecture

  * `ViewController` - View controller containing a `WKWebView` that loads the content of
    your website from Firebase Hosting. This view controller also receives messages
    from the webview and logs them to Firebase Analytics.
