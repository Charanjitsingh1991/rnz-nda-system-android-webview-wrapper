# How to Change the Logo in RNZ NDA System App

## Method 1: Replace with Image File (PNG/JPG)

1. **Prepare your logo image:**
   - Recommended size: 512x512px or 1024x1024px
   - Format: PNG (with transparency) or JPG
   - Name: `logo.png` or `logo.jpg`

2. **Replace the current logo:**
   ```bash
   # Delete current vector logo
   rm app/src/main/res/drawable/logo.xml
   
   # Copy your new logo (replace 'your_logo.png' with actual filename)
   cp your_logo.png app/src/main/res/drawable/logo.png
   ```

3. **No code changes needed** - the layouts already reference `@drawable/logo`

## Method 2: Edit the Vector Drawable (Current Method)

If you want to modify the current vector logo, edit `app/src/main/res/drawable/logo.xml`:

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="200dp"
    android:height="200dp"
    android:viewportWidth="200"
    android:viewportHeight="200">
    
    <!-- Modify these paths to change the logo design -->
    <!-- Background circle -->
    <path android:fillColor="@color/white" ... />
    
    <!-- Text/shapes -->
    <path android:fillColor="@color/rnz_green" ... />
    
</vector>
```

## Method 3: Use Different Logo for Splash vs Error Screen

You can use different logos in different places:

1. **Splash screen logo:** Referenced in `activity_splash.xml`
2. **Error screen logo:** Referenced in `activity_main.xml`

To use different logos:

1. Create multiple logo files:
   - `logo_splash.png` - for splash screen
   - `logo_error.png` - for error overlay

2. Update the layout files:

**In `activity_splash.xml`:**
```xml
<ImageView
    android:src="@drawable/logo_splash" />
```

**In `activity_main.xml`:**
```xml
<ImageView
    android:src="@drawable/logo_error" />
```

## Method 4: Download Logo from RNZ Website

If you want to use the actual RNZ logo from their website:

1. **Download the logo** from https://rnz-group.com/wp-content/uploads/2022/07/white-bg.png
2. **Save it as** `app/src/main/res/drawable/logo.png`
3. **Delete** `app/src/main/res/drawable/logo.xml`

## Logo Usage Locations

The logo is currently used in:

1. **Splash Screen** (`activity_splash.xml`):
   - Size: 200dp x 200dp
   - Centered on screen
   - White background with RNZ green

2. **Error Overlay** (`activity_main.xml`):
   - Size: 80dp x 80dp
   - Shown when network errors occur
   - Smaller version for error state

## Testing Your Logo Change

After changing the logo:

1. **Clean and rebuild** the project:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

2. **Run the app** to see:
   - Splash screen with new logo
   - Error screen (simulate by turning off internet)

## Logo Design Recommendations

- **Size:** 512x512px minimum for crisp display
- **Format:** PNG with transparency preferred
- **Colors:** Should work well with RNZ green (#2E7D32) background
- **Style:** Simple, clean design that scales well at different sizes
