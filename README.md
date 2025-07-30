# RNZ NDA System - Android WebView Wrapper

A minimal Android app built with Kotlin that acts as a WebView wrapper for the RNZ WordPress-based NDA plugin system.

## Features

- **Full-screen WebView** loading the RNZ registration page
- **JavaScript enabled** for full web functionality
- **File upload support** with camera integration for QR code/ID capture
- **Download handling** for PDFs and files via native browser
- **Disabled back button** to prevent accidental logouts
- **Error handling** for no internet/page not found scenarios
- **Splash screen** with RNZ logo and branding
- **RNZ green theme** (#2E7D32) for status bar and UI elements

## Target URL

The app loads: `https://rnz-group.com/rnz-registration-page/`

## Project Structure

```
app/
├── src/main/
│   ├── AndroidManifest.xml          # App permissions and activities
│   ├── java/com/rnz/ndasystem/
│   │   ├── SplashActivity.kt        # Splash screen with RNZ logo
│   │   └── MainActivity.kt          # Main WebView wrapper
│   └── res/
│       ├── drawable/
│       │   └── logo.xml             # RNZ logo vector drawable
│       ├── layout/
│       │   ├── activity_splash.xml  # Splash screen layout
│       │   └── activity_main.xml    # Main WebView layout
│       └── values/
│           ├── colors.xml           # RNZ green theme colors
│           ├── strings.xml          # App strings
│           └── themes.xml           # Material Design themes
```

## Requirements

- **Android Studio** Arctic Fox or later
- **Android SDK** API level 24+ (Android 7.0)
- **Kotlin** 1.9.20+
- **Gradle** 8.2.0+

## Permissions

The app requests the following permissions:
- `INTERNET` - For loading web content
- `CAMERA` - For file uploads with camera capture
- `READ_EXTERNAL_STORAGE` - For file access
- `WRITE_EXTERNAL_STORAGE` - For file downloads

## Building the App

1. **Clone or download** this project
2. **Open in Android Studio**
3. **Sync Gradle** files
4. **Build and run** on device or emulator

```bash
# Command line build (optional)
./gradlew assembleDebug
```

## Key Features Implementation

### WebView Configuration
- JavaScript enabled for full web functionality
- File access permissions for uploads/downloads
- Mixed content support for HTTPS/HTTP resources
- DOM storage and database enabled

### File Upload Support
- Camera integration for capturing images
- File chooser with multiple selection support
- Permission handling for camera access

### Error Handling
- Network connectivity error detection
- HTTP error handling (404, 500, etc.)
- User-friendly error overlay with retry functionality

### Security Features
- Back button disabled to prevent accidental logout
- External link handling (opens in system browser)
- Secure file download via system intents

## App Behavior

1. **Splash Screen**: Shows RNZ logo for 2.5 seconds
2. **Main WebView**: Loads the registration page in full-screen
3. **Authentication**: Uses existing WordPress login system
4. **File Operations**: Supports uploads (with camera) and downloads
5. **Error Recovery**: Shows retry option for network issues

## Customization

### Colors
Edit `app/src/main/res/values/colors.xml` to modify the RNZ green theme:
```xml
<color name="rnz_green">#2E7D32</color>
```

### URLs
Modify the target URL in `MainActivity.kt`:
```kotlin
private val TARGET_URL = "https://rnz-group.com/rnz-registration-page/"
```

### Logo
Replace `app/src/main/res/drawable/logo.xml` with your custom logo.

## Testing

Test the following scenarios:
- Normal page loading with internet connection
- No internet connectivity (should show error overlay)
- File upload functionality (camera and gallery)
- PDF download and viewing
- Back button behavior (should be disabled)

## Deployment

1. **Generate signed APK** in Android Studio
2. **Install on target devices**
3. **Test all functionality** in production environment

## Support

For issues related to the WordPress NDA plugin system, contact the RNZ development team.

---

**App Name**: RNZ NDA System  
**Package**: com.rnz.ndasystem  
**Version**: 1.0  
**Target SDK**: Android 14 (API 34)  
**Minimum SDK**: Android 7.0 (API 24)
