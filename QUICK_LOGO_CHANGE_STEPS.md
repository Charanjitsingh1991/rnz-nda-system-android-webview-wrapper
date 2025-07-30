# Quick Steps to Change Logo in RNZ NDA System

## Option 1: Use the Alternative Logo I Created

To switch to the modern alternative logo:

```bash
# Backup current logo
mv app/src/main/res/drawable/logo.xml app/src/main/res/drawable/logo_backup.xml

# Use the alternative logo
mv app/src/main/res/drawable/logo_alternative.xml app/src/main/res/drawable/logo.xml
```

## Option 2: Replace with Your Own Image

1. **Place your logo image** in `app/src/main/res/drawable/`
2. **Name it `logo.png`** (or `logo.jpg`)
3. **Delete the XML file**:
   ```bash
   rm app/src/main/res/drawable/logo.xml
   ```

## Option 3: Edit Current Vector Logo

Edit `app/src/main/res/drawable/logo.xml` and modify:
- Colors: Change `@color/rnz_green` and `@color/white`
- Shapes: Modify the `pathData` attributes
- Size: Change `android:width` and `android:height`

## Where Logo Appears

The logo shows in 2 places:
1. **Splash Screen** - Large (200dp x 200dp)
2. **Error Screen** - Small (80dp x 80dp)

Both reference `@drawable/logo`, so changing one file updates both locations.

## Test Your Changes

After changing the logo:
1. Clean project: `./gradlew clean`
2. Build project: `./gradlew build`
3. Run app to see splash screen
4. Turn off internet to see error screen logo
