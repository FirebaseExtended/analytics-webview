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

@import WebKit;
@import FirebaseAnalytics;

#import "ViewController.h"

@interface ViewController () <WKScriptMessageHandler>
@property (nonatomic, readonly) NSURL *projectURL;
@property (nonatomic, readonly) WKWebView *webView;
@end

@implementation ViewController

- (void)viewDidLoad {
  [super viewDidLoad];

  // Get the hosted site url from the info.plist file.
  NSString *plistPath = [[NSBundle mainBundle] pathForResource:@"GoogleService-Info"
                                                        ofType:@"plist"];
  NSDictionary *plist = [NSDictionary dictionaryWithContentsOfFile:plistPath];
  NSString *appID = plist[@"PROJECT_ID"];

  NSString *projectURLString = [NSString stringWithFormat:@"https://%@.firebaseapp.com", appID];
  _projectURL = [NSURL URLWithString:projectURLString];

  // Initialize the webview and add self as a script message handler.
  _webView = [[WKWebView alloc] initWithFrame:self.view.frame];
  // [START add_handler]
  [self.webView.configuration.userContentController addScriptMessageHandler:self
                                                                       name:@"firebase"];
  // [END add_handler]
  [self.view addSubview:self.webView];
}

- (void)viewDidAppear:(BOOL)animated {
  [super viewDidAppear:animated];

  NSURLRequest *request = [NSURLRequest requestWithURL:self.projectURL];
  [self.webView loadRequest:request];
}

#pragma mark - WKScriptMessageHandler
// [START handle_messages]
- (void)userContentController:(WKUserContentController *)userContentController
      didReceiveScriptMessage:(WKScriptMessage *)message {
  if ([message.body[@"command"] isEqual:@"setUserProperty"]) {
    [FIRAnalytics setUserPropertyString:message.body[@"value"] forName:message.body[@"name"]];
  } else if ([message.body[@"command"] isEqual: @"logEvent"]) {
    [FIRAnalytics logEventWithName:message.body[@"name"] parameters:message.body[@"parameters"]];
  }
}
// [END handle_messages]

@end
