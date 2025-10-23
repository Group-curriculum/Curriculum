# Study Hub - Tanzanian O-Level & A-Level Study App

A comprehensive native Android application designed specifically for Tanzanian O-Level and A-Level students to enhance their learning experience.

## Features

### Core Features
- **Subjects**: Complete coverage of NECTA O-Level and A-Level subjects
- **Study Notes**: Structured, simplified notes with diagrams, formulas, and key points
- **Past Papers**: NECTA exam papers with solutions and explanations
- **Quizzes & Mock Tests**: Multiple-choice, true/false, fill-in-the-blank, and timed mock exams
- **Progress Tracking**: Performance analytics, study streaks, and personalized recommendations
- **Offline Mode**: Access notes, past papers, and quizzes without internet
- **User Authentication**: Email/password and Google sign-in
- **Notifications**: Study reminders and motivational alerts

### Advanced Features
- **AI Study Assistant**: Step-by-step explanations in English and Kiswahili
- **Community Forum**: Student interaction and knowledge sharing
- **Bookmark & Favorites**: Save important content for quick access
- **Multilingual Support**: English and Kiswahili localization

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (local) + Firebase Firestore (cloud)
- **Authentication**: Firebase Auth
- **Storage**: Firebase Storage
- **Dependency Injection**: Koin
- **Navigation**: Navigation Compose
- **Image Loading**: Glide
- **Networking**: Retrofit + OkHttp
- **Background Tasks**: WorkManager
- **Notifications**: Firebase Cloud Messaging

## Project Structure

```
app/
├── src/main/java/com/studyhub/tanzania/
│   ├── data/
│   │   ├── models/           # Data models
│   │   ├── repository/       # Repository implementations
│   │   └── local/           # Room database and DAOs
│   ├── ui/
│   │   ├── activities/      # Main activities
│   │   ├── screens/         # Compose screens
│   │   ├── components/      # Reusable UI components
│   │   ├── navigation/      # Navigation setup
│   │   ├── viewmodels/      # ViewModels
│   │   └── theme/           # App theming
│   ├── di/                  # Dependency injection
│   └── services/            # Background services
└── src/main/res/
    ├── values/              # English strings
    ├── values-sw/           # Kiswahili strings
    ├── drawable/            # Vector drawables
    └── themes/              # App themes
```

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21+ (Android 5.0)
- Kotlin 1.9.20+
- Gradle 8.2.0+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/study-hub-android.git
cd study-hub-android
```

2. Open the project in Android Studio

3. Add your Firebase configuration:
   - Create a Firebase project
   - Download `google-services.json` and place it in `app/`
   - Enable Firestore, Authentication, and Storage

4. Build and run the project

### Configuration

1. **Firebase Setup**:
   - Enable Email/Password authentication
   - Enable Google sign-in
   - Set up Firestore security rules
   - Configure Cloud Messaging

2. **App Configuration**:
   - Update `applicationId` in `build.gradle`
   - Configure ProGuard rules for release builds
   - Set up signing configuration

## Features in Detail

### Study Notes
- Organized by subject and topic
- Rich text with formulas and diagrams
- Offline availability
- Bookmark functionality
- Difficulty levels (Beginner, Intermediate, Advanced)

### Quizzes & Mock Tests
- Multiple question types
- Timed and untimed options
- Instant feedback and explanations
- Progress tracking
- Offline support

### Past Papers
- NECTA exam papers by year and subject
- Downloadable PDFs
- Marking schemes
- Search and filter functionality

### Progress Tracking
- Study streak counter
- Performance analytics
- Subject-wise progress
- Achievement system
- Weekly/monthly reports

## Localization

The app supports both English and Kiswahili languages:
- All UI text is localized
- Content available in both languages
- Automatic language detection
- Manual language switching

## Offline Support

- Download notes and quizzes for offline use
- Cached past papers
- Offline progress tracking
- Sync when online

## Performance Optimization

- Optimized for low-end Android devices
- Efficient image loading and caching
- Minimal data usage
- Fast app startup
- Memory management

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Email: support@studyhub.tz
- Website: https://studyhub.tz
- GitHub Issues: [Create an issue](https://github.com/yourusername/study-hub-android/issues)

## Acknowledgments

- NECTA for exam papers and syllabus
- Tanzanian education community
- Open source contributors
- Material Design team

## Roadmap

### Phase 1 (Current)
- [x] Basic app structure
- [x] User authentication
- [x] Subject navigation
- [x] Notes and quizzes
- [x] Progress tracking

### Phase 2 (Planned)
- [ ] AI Study Assistant
- [ ] Community Forum
- [ ] Advanced analytics
- [ ] Parent dashboard
- [ ] Teacher tools

### Phase 3 (Future)
- [ ] iOS version
- [ ] Web platform
- [ ] Advanced AI features
- [ ] Gamification
- [ ] Social learning

---

**Study Hub** - Empowering Tanzanian students for academic excellence! 🎓📚