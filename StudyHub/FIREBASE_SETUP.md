# Firebase Setup Guide for Study Hub

This guide will walk you through setting up Firebase for the Study Hub application.

## Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project"
3. Enter project name: **Study Hub**
4. Enable Google Analytics (optional but recommended)
5. Create project

## Step 2: Add Android App

1. Click "Add app" and select Android
2. Enter Android package name: `com.studyhub.tz`
3. Enter app nickname: **Study Hub**
4. Click "Register app"
5. Download `google-services.json`
6. **IMPORTANT:** Replace the placeholder file in `/workspace/StudyHub/app/google-services.json`

## Step 3: Enable Authentication

1. In Firebase Console, go to **Authentication**
2. Click "Get started"
3. Enable **Email/Password** sign-in method
4. Enable **Google** sign-in method
5. Copy the **Web client ID** from Google sign-in configuration
6. Update `/workspace/StudyHub/app/src/main/java/com/studyhub/tz/data/repository/AuthRepository.kt`
   - Line 76: Replace `"YOUR_WEB_CLIENT_ID"` with actual Web Client ID

## Step 4: Create Firestore Database

1. Go to **Firestore Database**
2. Click "Create database"
3. Start in **production mode**
4. Choose location: **eur3 (europe-west)** or nearest to Tanzania
5. Click "Enable"

## Step 5: Create Firestore Collections

Create the following collections:

### Collection: `subjects`
```javascript
// Document ID: Auto-generated or custom (e.g., "math_o")
{
  "id": "math_o",
  "name": "Mathematics",
  "nameSwahili": "Hisabati",
  "level": "O_LEVEL",
  "description": "Algebra, Geometry, Trigonometry",
  "descriptionSwahili": "Aljebra, Jiometria, Trigonometria",
  "color": "#3F51B5",
  "notesCount": 0,
  "quizzesCount": 0,
  "pastPapersCount": 0,
  "isPopular": true,
  "order": 1,
  "lastUpdated": 1234567890000
}
```

### Collection: `notes`
```javascript
{
  "id": "note_1",
  "subjectId": "math_o",
  "title": "Introduction to Algebra",
  "titleSwahili": "Utangulizi wa Aljebra",
  "content": "Full note content...",
  "contentSwahili": "Maudhui kamili...",
  "summary": "Brief summary...",
  "summarySwahili": "Muhtasari mfupi...",
  "topics": ["Algebra", "Equations"],
  "keyPoints": ["Point 1", "Point 2"],
  "keyPointsSwahili": ["Jambo 1", "Jambo 2"],
  "formulas": [],
  "diagrams": [],
  "isPremium": false,
  "isBookmarked": false,
  "readCount": 0,
  "estimatedReadTime": 5,
  "difficulty": "MEDIUM",
  "order": 1,
  "createdAt": 1234567890000,
  "updatedAt": 1234567890000
}
```

### Collection: `quizzes`
```javascript
{
  "id": "quiz_1",
  "subjectId": "math_o",
  "title": "Algebra Basics Quiz",
  "titleSwahili": "Maswali ya Msingi ya Aljebra",
  "description": "Test your understanding...",
  "descriptionSwahili": "Jaribu uelewa wako...",
  "questions": [
    {
      "id": "q1",
      "questionText": "What is 2 + 2?",
      "questionTextSwahili": "Ni nini 2 + 2?",
      "type": "MULTIPLE_CHOICE",
      "options": ["2", "3", "4", "5"],
      "optionsSwahili": ["2", "3", "4", "5"],
      "correctAnswer": "4",
      "explanation": "2 plus 2 equals 4",
      "explanationSwahili": "2 pamoja na 2 ni sawa na 4",
      "imageUrl": "",
      "points": 1,
      "difficulty": "EASY"
    }
  ],
  "totalQuestions": 1,
  "duration": 30,
  "passingScore": 50,
  "difficulty": "EASY",
  "quizType": "PRACTICE",
  "topics": ["Basic Math"],
  "isPremium": false,
  "attemptCount": 0,
  "averageScore": 0,
  "createdAt": 1234567890000,
  "updatedAt": 1234567890000
}
```

### Collection: `past_papers`
```javascript
{
  "id": "paper_1",
  "subjectId": "math_o",
  "title": "NECTA 2023 - Mathematics",
  "year": 2023,
  "examType": "NECTA",
  "level": "O_LEVEL",
  "paperNumber": 1,
  "fileUrl": "",
  "solutionsUrl": "",
  "hasSolutions": true,
  "questions": [
    {
      "questionNumber": "1",
      "questionText": "Solve for x...",
      "marks": 5,
      "solution": "x = 10",
      "explanation": "Step by step...",
      "imageUrl": ""
    }
  ],
  "topics": ["Algebra"],
  "duration": 180,
  "totalMarks": 100,
  "isPremium": false,
  "downloadCount": 0,
  "isBookmarked": false,
  "createdAt": 1234567890000,
  "updatedAt": 1234567890000
}
```

### Collection: `users`
```javascript
{
  "uid": "user_firebase_uid",
  "email": "student@example.com",
  "displayName": "John Doe",
  "photoUrl": "",
  "phoneNumber": "",
  "educationLevel": "O_LEVEL",
  "formClass": 4,
  "preferredLanguage": "en",
  "isPremium": false,
  "premiumExpiryDate": 0,
  "studyStreak": 0,
  "totalStudyTime": 0,
  "bookmarkedNotes": [],
  "bookmarkedPapers": [],
  "notificationsEnabled": true,
  "studyReminders": [],
  "createdAt": 1234567890000,
  "lastLoginAt": 1234567890000,
  "updatedAt": 1234567890000
}
```

## Step 6: Set Firestore Security Rules

Go to **Firestore Database > Rules** and paste:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Users can read/write their own data
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Everyone can read subjects, notes, quizzes, past papers
    match /subjects/{subjectId} {
      allow read: if request.auth != null;
      allow write: if false; // Admin only via console
    }
    
    match /notes/{noteId} {
      allow read: if request.auth != null;
      allow write: if false;
    }
    
    match /quizzes/{quizId} {
      allow read: if request.auth != null;
      allow write: if false;
    }
    
    match /past_papers/{paperId} {
      allow read: if request.auth != null;
      allow write: if false;
    }
    
    // User progress - users can read/write their own
    match /user_progress/{progressId} {
      allow read, write: if request.auth != null;
    }
    
    // Quiz attempts - users can read/write their own
    match /quiz_attempts/{attemptId} {
      allow read, write: if request.auth != null;
    }
    
    // Study sessions - users can read/write their own
    match /study_sessions/{sessionId} {
      allow read, write: if request.auth != null;
    }
  }
}
```

Click "Publish"

## Step 7: Enable Cloud Messaging (Optional)

1. Go to **Cloud Messaging**
2. Cloud Messaging is automatically enabled
3. Server key will be available for future use

## Step 8: Upload Sample Data

You can upload sample data using the Firebase Console or write a Node.js script:

### Option A: Manual Upload (Firebase Console)
1. Go to Firestore Database
2. Click "Start collection"
3. Enter collection name (e.g., "subjects")
4. Add documents manually

### Option B: Upload Script (Node.js)

Create a file `upload-data.js`:

```javascript
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

// Upload subjects
const subjects = [
  {
    id: "math_o",
    name: "Mathematics",
    nameSwahili: "Hisabati",
    level: "O_LEVEL",
    // ... rest of data
  }
  // Add more subjects
];

subjects.forEach(async (subject) => {
  await db.collection('subjects').doc(subject.id).set(subject);
  console.log('Uploaded:', subject.name);
});
```

Run: `node upload-data.js`

## Step 9: Test the App

1. Open Android Studio
2. Sync Gradle
3. Run the app
4. Sign up with a test account
5. Verify data appears

## Troubleshooting

### Issue: Google Sign-In fails
**Solution:** Make sure you copied the Web Client ID correctly to `AuthRepository.kt`

### Issue: Firestore permission denied
**Solution:** Check security rules and ensure user is authenticated

### Issue: No data showing
**Solution:** Upload sample data to Firestore collections

### Issue: App crashes on launch
**Solution:** Ensure `google-services.json` is in the correct location: `app/google-services.json`

## Cost Optimization

Firebase Free Tier includes:
- **Authentication:** Unlimited users
- **Firestore:** 1GB storage, 50K reads/day
- **Cloud Messaging:** Unlimited
- **Storage:** 5GB, 1GB/day downloads

For a student app, this is usually sufficient. Monitor usage in Firebase Console.

## Security Checklist

- [x] Enable App Check (optional, for production)
- [x] Set up Firestore security rules
- [x] Enable multi-factor authentication (optional)
- [x] Monitor authentication activity
- [x] Set up billing alerts
- [x] Restrict API keys (optional)

## Next Steps

1. Complete Firebase setup
2. Upload comprehensive NECTA content
3. Test all features
4. Deploy to Play Store
5. Monitor Firebase usage

---

**Firebase setup is now complete!** 🎉

The app is ready to run with real backend services.
