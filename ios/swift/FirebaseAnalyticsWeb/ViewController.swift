//
//  Copyright (c) 2016 Google Inc.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

import WebKit

import FirebaseAnalytics

class ViewController: UIViewController, WKScriptMessageHandler {

  private var webView: WKWebView!
  private var projectURL: URL!

  override func viewDidLoad() {
    super.viewDidLoad()

    // Get the hosted site url from the GoogleService-Info.plist file.
    let plistPath = Bundle.main.path(forResource: "GoogleService-Info",
                                     ofType: "plist")!
    let plist = NSDictionary(contentsOfFile: plistPath)!
    let appID = plist["PROJECT_ID"] as! String

    let projectURLString = "https://\(appID).firebaseapp.com"
    self.projectURL = URL(string: projectURLString)!

    // Initialize the webview and add self as a script message handler.
    self.webView = WKWebView(frame: self.view.frame)

    // [START add_handler]
    self.webView.configuration.userContentController.add(self, name: "firebase")
    // [END add_handler]

    self.view.addSubview(self.webView)
  }

  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)

    let request = URLRequest(url: self.projectURL)
    self.webView.load(request)
  }


  // [START handle_messages]
  func userContentController(_ userContentController: WKUserContentController,
                           didReceive message: WKScriptMessage) {
    guard let body = message.body as? [String: Any] else { return }
    guard let command = body["command"] as? String else { return }
    guard let name = body["name"] as? String else { return }

    if command == "setUserProperty" {
        guard let value = body["value"] as? String else { return }
        Analytics.setUserProperty(value, forName: name)
    } else if command == "logEvent" {
        guard let params = body["parameters"] as? [String: NSObject] else { return }
        Analytics.logEvent(name, parameters: params)
    }
  }
  // [END handle_messages]

}

