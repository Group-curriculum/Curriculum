# Study Hub - Android Application for Tanzanian Students

**Study Hub** is a comprehensive native Android application designed specifically for Tanzanian O-Level and A-Level students. The app provides access to NECTA-aligned study materials, past papers, quizzes, and progress tracking to help students excel in their examinations.

## 🎯 Features

### Core Features
- **📚 Subjects**: Complete coverage of O-Level and A-Level subjects according to NECTA syllabus
- **📝 Notes & Summaries**: Structured notes with diagrams, formulas, and key points
- **📄 Past Papers**: NECTA exam papers with solutions and explanations, searchable by year and subject
- **❓ Quizzes & Mock Tests**: Multiple question types (MCQ, True/False, Fill-in-the-blank) with instant feedback
- **📊 Progress Tracking**: Track performance, study streaks, and get personalized recommendations
- **📴 Offline Mode**: Access notes, past papers, and quizzes without internet connection
- **🔐 User Authentication**: Email/password login and Google sign-in
- **🔔 Notifications**: Study reminders and motivational alerts

### UI/UX Design
- Clean, modern Material Design interface
- Blue and white color scheme for trust and clarity
- Bottom navigation for easy access to main sections
- Adaptive layouts for all screen sizes
- Optimized for low-end Android devices

### Localization
- **English** and **Kiswahili** language support
- Tanzanian context in examples and explanations

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with the following layers:

### Data Layer
- **Models**: Data classes for Subject, Note, Quiz, PastPaper, UserProgress
- **Local Storage**: Room database for offline caching
- **Remote Storage**: Firebase Firestore for cloud sync
- **Repositories**: Handle data operations and caching logic

### Presentation Layer
- **ViewModels**: Business logic and state management
- **Activities & Fragments**: UI components
- **Adapters**: RecyclerView adapters for lists

### Key Technologies
- **Kotlin**: Primary programming language
- **Firebase**: Authentication, Firestore, Cloud Messaging, Storage
- **Room**: Local database for offline support
- **Coroutines**: Asynchronous programming
- **LiveData & ViewModel**: Reactive data binding
- **Material Design Components**: Modern UI
- **MPAndroidChart**: Progress visualization
- **Navigation Component**: App navigation

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 17 or higher
- Android SDK 21+ (minimum) / 34 (target)
- Firebase account

### Firebase Setup

1. **Create Firebase Project**:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project named "Study Hub"
   - Enable Google Analytics (optional)

2. **Add Android App to Firebase**:
   - Package name: `com.studyhub.tz`
   - Download `google-services.json`
   - Replace the placeholder file in `app/google-services.json`

3. **Enable Firebase Services**:
   - **Authentication**: Enable Email/Password and Google Sign-In
   - **Firestore Database**: Create database in production mode
   - **Cloud Storage**: Enable for file storage
   - **Cloud Messaging**: Enable for notifications

4. **Get Google Sign-In Web Client ID**:
   - In Firebase Console, go to Authentication > Sign-in method > Google
   - Copy the Web client ID
   - Update in `AuthRepository.kt`: Replace `YOUR_WEB_CLIENT_ID`

### Firestore Database Structure

Create the following collections in Firestore:

```
/users/{userId}
  - uid, email, displayName, educationLevel, ...

/subjects/{subjectId}
  - name, nameSwahili, level, description, ...

/notes/{noteId}
  - subjectId, title, content, topics, ...

/quizzes/{quizId}
  - subjectId, title, questions[], ...

/past_papers/{paperId}
  - subjectId, year, fileUrl, questions[], ...

/user_progress/{progressId}
  - userId, subjectId, averageScore, ...

/quiz_attempts/{attemptId}
  - userId, quizId, score, answers{}, ...
```

### Installation Steps

1. **Clone or Download the Project**:
   ```bash
   cd /path/to/StudyHub
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the StudyHub folder

3. **Update Firebase Configuration**:
   - Replace `app/google-services.json` with your Firebase config
   - Update `YOUR_WEB_CLIENT_ID` in `AuthRepository.kt`

4. **Sync Gradle**:
   - Click "Sync Project with Gradle Files" in Android Studio
   - Wait for dependencies to download

5. **Populate Sample Data** (Optional):
   - Use `SampleDataProvider.kt` to get sample subjects and content
   - Upload to Firestore using Firebase Console or a script

6. **Run the App**:
   - Connect an Android device or start an emulator
   - Click "Run" (Shift+F10)

## 📱 App Structure

### Main Sections

1. **Home**: Overview, study streak, popular subjects, quick access
2. **Subjects**: Browse O-Level and A-Level subjects
3. **Quizzes**: Practice quizzes, mock exams, topic tests
4. **Past Papers**: NECTA past papers by year and subject
5. **Progress**: Performance analytics and study time tracking

### Additional Activities

- **SplashActivity**: App launch screen
- **AuthActivity**: Sign in / Sign up
- **NoteDetailActivity**: View detailed notes
- **QuizActivity**: Take quizzes
- **QuizResultActivity**: View quiz results
- **PastPaperDetailActivity**: View past paper details

## 🔧 Configuration

### Gradle Configuration

- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **ProGuard**: Enabled for release builds with code shrinking

### Dependencies

Key dependencies include:
- Firebase BOM 32.7.0
- Room 2.6.1
- Coroutines 1.7.3
- Material Components 1.11.0
- Navigation 2.7.6
- MPAndroidChart 3.1.0

See `app/build.gradle.kts` for complete list.

## 📊 Sample Data

The app includes `SampleDataProvider.kt` with sample NECTA subjects:

### O-Level Subjects
- Mathematics, English, Kiswahili, Physics, Chemistry, Biology
- Geography, History, Civics, Commerce

### A-Level Subjects
- Advanced Mathematics, Physics, Chemistry, Biology

To populate your Firestore database with sample data, you can:
1. Use the Firebase Console to manually add documents
2. Create a script to upload data from `SampleDataProvider.kt`
3. Use Firebase Functions to initialize data

## 🌐 Localization

The app supports:
- **English** (`values/strings.xml`)
- **Kiswahili** (`values-sw/strings.xml`)

To add more languages:
1. Create new `values-{language_code}/strings.xml`
2. Translate all string resources
3. Update language selection in settings

## 🎨 Customization

### Colors
Edit `res/values/colors.xml`:
- Primary: `#2196F3` (Blue)
- Accent: `#FF5722` (Orange)
- Subject-specific colors defined

### Themes
Edit `res/values/themes.xml`:
- Material Design 3 components
- Custom button, card, and input styles

### Branding
Replace icons in `res/mipmap/` and update:
- App icon
- Splash screen background
- Subject icons

## 🔒 Security

- **ProGuard Rules**: Configured for release builds
- **Firebase Security Rules**: Set up Firestore rules to protect user data
- **Authentication**: Required for all app features
- **Data Validation**: Input validation on client and server

### Recommended Firestore Security Rules

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only read/write their own data
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
    
    // Everyone can read subjects, notes, quizzes, past papers
    match /{document=**} {
      allow read: if request.auth != null;
    }
    
    // Only authenticated users can write progress and attempts
    match /user_progress/{progressId} {
      allow write: if request.auth != null;
    }
    
    match /quiz_attempts/{attemptId} {
      allow write: if request.auth != null;
    }
  }
}
```

## 🚢 Deployment

### Release Build

1. **Generate Signing Key**:
   ```bash
   keytool -genkey -v -keystore studyhub.keystore -alias studyhub -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Configure Signing** in `app/build.gradle.kts`:
   ```kotlin
   signingConfigs {
       release {
           storeFile file("studyhub.keystore")
           storePassword "your-password"
           keyAlias "studyhub"
           keyPassword "your-password"
       }
   }
   ```

3. **Build Release APK**:
   ```bash
   ./gradlew assembleRelease
   ```

4. **Upload to Google Play Console**

## 📈 Future Enhancements

- **AI Study Assistant**: Chat-based Q&A in English and Kiswahili
- **Community Forum**: Student interaction and knowledge sharing
- **Video Lessons**: Integrated video tutorials
- **Premium Features**: Advanced analytics, unlimited quizzes
- **Google AdMob**: Ad integration for free tier
- **Dark Mode**: Theme switching
- **Widgets**: Home screen study widgets

## 🤝 Contributing

Contributions are welcome! To contribute:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Support

For issues, questions, or suggestions:
- Create an issue on GitHub
- Email: support@studyhub.tz (example)

## 🙏 Acknowledgments

- NECTA (National Examinations Council of Tanzania) for syllabus reference
- Tanzanian students and teachers for feedback and support
- Open-source community for excellent libraries and tools

---

**Made with ❤️ for Tanzanian Students**

*Developed to help O-Level and A-Level students excel in their NECTA examinations through accessible, quality study materials.*
