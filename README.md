# 🛡️ X_WebBlocker

[![License: Proprietary](https://img.shields.io/badge/License-Proprietary-red.svg)](LICENSE)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/your-github-username/X_WebBlocker/graphs/commit-activity)
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://www.android.com/)
[![Device Admin](https://img.shields.io/badge/Device_Admin-Required-orange.svg)](https://developer.android.com/guide/topics/admin/device-admin)
[![Accessibility Service](https://img.shields.io/badge/Accessibility_Service-Required-orange.svg)](https://developer.android.com/guide/topics/ui/accessibility)

**X_WebBlocker** is a utility application designed to block access to adult websites and content on Android devices. It utilizes device administrator and accessibility service permissions to enforce blocking based on a predefined list of adult website domains and filter keywords. Once activated, the blocking remains in place until the device is reset or the **correct uninstall password obtained from the developer** is entered.

##  Blocking Features

* **Adult Website Blocking:** Prevents access to a curated list of websites known for adult content.
* **Keyword Filtering:** Blocks websites containing specified keywords (e.g., "nude", "sex", and other related terms) in their content or URL.
* **Persistent Blocking:** Once activated, the blocking mechanism remains active even after app closure or device restarts (until uninstalled or the device is reset).
* **Uninstall Protection:** Leverages accessibility services to block standard methods of uninstalling the application without the correct uninstall password obtained from the developer.

## ⚠️ Permissions Required

This application requires the following permissions to function correctly:

* **Device Administrator:** This permission is necessary to prevent users from easily uninstalling the application without the correct uninstall password from the developer, ensuring the blocking remains active.
* **Accessibility Service:** This permission is used to monitor and filter website content and URLs as they are accessed by the user, enabling the blocking mechanism. It is also used to block standard uninstall procedures.

**Granting these permissions is crucial for the application to provide the intended level of protection.**

## ️ Installation Guide

1.  **Install X_WebBlocker:**
    * Install the X_WebBlocker application on the Android device you wish to protect. (Provide instructions on how to obtain and install the app here, e.g., from a website or app store).

2.  **Grant Device Administrator Permission:**
    * Upon initial launch, the application will request Device Administrator permission. **You must grant this permission for the uninstall protection to work.** Follow the on-screen instructions to activate X_WebBlocker as a device administrator.

3.  **Grant Accessibility Service Permission:**
    * The application will also request permission to use the Accessibility Service. **You must grant this permission for the website and keyword blocking, as well as the uninstall protection, to function.** Follow the on-screen instructions to enable the X_WebBlocker Accessibility Service.

4.  **Blocking Activated:**
    * Once the necessary permissions are granted, X_WebBlocker will begin blocking access to adult websites and content based on its internal list and keyword filters.

## ⚙️ Uninstallation

Due to the Device Administrator and Accessibility Service permissions, standard uninstallation methods are initially blocked. To uninstall X_WebBlocker, you must follow these steps:

1.  **Open X_WebBlocker:** Launch the X_WebBlocker application.
2.  **Request Uninstall Password:** You will see an option to uninstall the application. You will be prompted to **contact the developer to obtain the uninstall password.**
3.  **Enter Developer Password:** Enter the correct uninstall password provided by the developer in the designated field.
4.  **Stop Accessibility Service:** Upon entering the correct developer-provided password, the application will **automatically stop the X_WebBlocker Accessibility Service.**
5.  **Uninstall the App:** With the Accessibility Service stopped, you can now uninstall the application using the standard Android app uninstallation methods (e.g., through the device's Settings or by long-pressing the app icon).

**If the uninstall password from the developer is lost, the only way to remove the application is to perform a factory reset of the Android device. This will erase all data on the device.**

## ⚠️ Important Notes

* This application is intended for legitimate purposes, such as protecting children from inappropriate online content. Misuse is strictly prohibited.
* Ensure you understand the implications of granting Device Administrator and Accessibility Service permissions.
* The effectiveness of the blocking depends on the comprehensiveness of the internal website list and keyword filters.
* The developer is not responsible for any unintended blocking or circumvention of the application's features.
* **The uninstall password for this application must be obtained from the developer.** Please contact [eduroket001@gmail.com] to request the password.

##  License

This project is licensed under a **Proprietary License**.

##  Support

For any questions, issues, or support inquiries, please contact [your support email address or link to support page].

---

**Thank you for using X_WebBlocker!**
