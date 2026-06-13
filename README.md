# Lottery Manager — Android App (WhatsApp PDF Share)

Yeh ek ready-to-build **Android project** hai jo aapke Lottery Manager
web-app ko ek installable APK mein badal deta hai, aur **PDF Share** button
seedha Android ke native share-dialog ko trigger karta hai — jisme
**WhatsApp** aur **WhatsApp Business** dono options aate hain, "Just once /
Always" choice ke saath.

---

## ✅ APK Online Banayein (koi software install nahi karna — sirf browser)

Is project mein ek **GitHub Actions** file already add ki hai
(`.github/workflows/build.yml`) jo GitHub ke free servers par automatically
APK build kar degi. Aapko sirf yeh karna hai:

1. **github.com** par jaayein aur free account banayein (agar nahi hai).
2. Login karne ke baad, top-right `+` icon → **"New repository"**.
   - Naam kuch bhi de dein, jaise `LotteryManagerApp`
   - **Public** ya **Private** — dono chalega
   - "Create repository" dabayein
3. Ab is `LotteryManagerApp` zip ko apne computer mein **unzip** karein.
4. Naye repository page par **"uploading an existing file"** link par
   click karein (ya "Add file → Upload files").
5. Unzip ki hui `LotteryManagerApp` folder ke **andar ki saari files aur
   folders** (build.gradle, settings.gradle, app/, .github/ , gradle/, etc.)
   browser window mein **drag-and-drop** kar dein (Chrome/Edge folder
   drag-drop support karte hain).
   - **Zaroori:** `.github` folder bhi upload honi chahiye (yeh hidden naam
     jaisa dikhta hai par GitHub upload mein ye sahi se chala jata hai).
6. Neeche "Commit changes" green button dabayein.
7. Top par **"Actions"** tab par click karein.
   - Ek workflow run automatically shuru ho jayega (naam "Build APK").
   - Agar nahi shuru hua, to us workflow par click karke
     **"Run workflow"** button dabayein.
8. **3–6 minute wait karein** — jab tak green tick (✅) na aa jaye.
9. Run complete hone par usi page ko scroll karein, neeche
   **"Artifacts"** section milega → **"LotteryManager-apk"** par click
   karke download karein (ek `.zip` file aayegi).
10. Yeh zip apne **Android phone** mein extract karein — andar
    `app-debug.apk` milegi.
11. `app-debug.apk` par tap karke install karein.
    - Phone "Install unknown apps" permission ka ek baar prompt dega —
      Allow kar dein.

App ka naam hoga **"Lottery Manager"** aur icon home screen par aa jayega.

---

## Alternative — Android Studio (apne computer par)

Agar GitHub wala tareeka use nahi karna, to Android Studio se bhi APK ban
sakti hai:

1. **Android Studio install karein** (free): https://developer.android.com/studio
2. Yeh `LotteryManagerApp` folder unzip karein.
3. Android Studio kholen → **Open** → `LotteryManagerApp` folder select karein.
4. Pehli baar open karne par Gradle sync hoga (internet chahiye, automatic hota hai).
5. Top menu se: **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
6. Build complete hone par "locate" link pe click karein —
   APK yahan milegi: `app/build/outputs/apk/debug/app-debug.apk`

---

## PDF → WhatsApp kaise kaam karta hai

- App ke andar History tab mein **"PDF Share"** button dabane par:
  1. PDF generate hota hai (jaisa pehle tha).
  2. App PDF file ko temporarily save karta hai.
  3. Android ka **native "Open with" dialog** khulta hai jisme
     **WhatsApp** aur **WhatsApp Business** dikhte hain.
  4. Aap **"Just once"** ya **"Always"** choose kar sakte ho — "Always"
     choose karne par next time wahi app directly open hoga (bina dialog ke).
  5. Agar future mein dobara dialog chahiye (app change karna ho), to:
     Phone **Settings → Apps → Lottery Manager → Open by default → Clear
     defaults**.

- Yeh feature **sirf is Android app ke andar** kaam karega. Agar aap yeh
  HTML file seedha Chrome browser mein khol kar use karenge, tab PDF share
  Chrome ke generic "Share" sheet se hoga (jo bhi WhatsApp + sab apps
  dikhata hai) — wo bhi already kaam karta hai.

---

## Notes / Limitations

- Login data, history etc. browser ke `localStorage` mein hi save hota hai
  (jaisa pehle tha) — ye is app ke andar bhi kaam karega, har baar same
  jagah persist hoga jab tak app data clear na karein.
- `.lmdata` encrypted backup file ka **Save/Load** feature is WebView app
  ke andar thoda limited ho sakta hai (kuch Android versions blob-download
  ko WebView mein directly support nahi karte). Backup/restore ke liye
  Chrome browser version use karna better rahega; PDF/WhatsApp sharing dono
  jagah kaam karega.
- App ka package name: `com.lotterymanager.app` — agar Play Store pe daalna
  ho to apna unique package name aur signing key set karna hoga.
