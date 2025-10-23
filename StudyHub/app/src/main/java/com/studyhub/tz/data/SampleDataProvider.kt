package com.studyhub.tz.data

import com.studyhub.tz.data.model.*

/**
 * Provides sample data for NECTA subjects, notes, quizzes, and past papers
 * This data should be uploaded to Firebase Firestore for production use
 */
object SampleDataProvider {

    /**
     * Sample O-Level subjects according to NECTA syllabus
     */
    fun getOLevelSubjects(): List<Subject> {
        return listOf(
            Subject(
                id = "math_o",
                name = "Mathematics",
                nameSwahili = "Hisabati",
                level = EducationLevel.O_LEVEL,
                description = "Algebra, Geometry, Trigonometry, Statistics",
                descriptionSwahili = "Aljebra, Jiometria, Trigonometria, Takwimu",
                color = "#3F51B5",
                isPopular = true,
                order = 1
            ),
            Subject(
                id = "english_o",
                name = "English Language",
                nameSwahili = "Lugha ya Kiingereza",
                level = EducationLevel.O_LEVEL,
                description = "Grammar, Composition, Comprehension",
                descriptionSwahili = "Sarufi, Uandishi, Uelewa",
                color = "#E91E63",
                isPopular = true,
                order = 2
            ),
            Subject(
                id = "kiswahili_o",
                name = "Kiswahili",
                nameSwahili = "Kiswahili",
                level = EducationLevel.O_LEVEL,
                description = "Sarufi, Insha, Fasihi",
                descriptionSwahili = "Sarufi, Insha, Fasihi",
                color = "#FFC107",
                isPopular = true,
                order = 3
            ),
            Subject(
                id = "physics_o",
                name = "Physics",
                nameSwahili = "Fizikia",
                level = EducationLevel.O_LEVEL,
                description = "Mechanics, Electricity, Waves, Energy",
                descriptionSwahili = "Mikanika, Umeme, Mawimbi, Nishati",
                color = "#00BCD4",
                isPopular = true,
                order = 4
            ),
            Subject(
                id = "chemistry_o",
                name = "Chemistry",
                nameSwahili = "Kemia",
                level = EducationLevel.O_LEVEL,
                description = "Organic, Inorganic, Physical Chemistry",
                descriptionSwahili = "Kemia Hai, Kemia Isiyohai, Kemia Fizikia",
                color = "#9C27B0",
                isPopular = true,
                order = 5
            ),
            Subject(
                id = "biology_o",
                name = "Biology",
                nameSwahili = "Biolojia",
                level = EducationLevel.O_LEVEL,
                description = "Botany, Zoology, Human Biology",
                descriptionSwahili = "Mimea, Wanyama, Biolojia ya Binadamu",
                color = "#4CAF50",
                isPopular = true,
                order = 6
            ),
            Subject(
                id = "geography_o",
                name = "Geography",
                nameSwahili = "Jiografia",
                level = EducationLevel.O_LEVEL,
                description = "Physical and Human Geography",
                descriptionSwahili = "Jiografia ya Kimwili na Kibinadamu",
                color = "#8BC34A",
                order = 7
            ),
            Subject(
                id = "history_o",
                name = "History",
                nameSwahili = "Historia",
                level = EducationLevel.O_LEVEL,
                description = "World and African History",
                descriptionSwahili = "Historia ya Dunia na Afrika",
                color = "#FF5722",
                order = 8
            ),
            Subject(
                id = "civics_o",
                name = "Civics",
                nameSwahili = "Uraia",
                level = EducationLevel.O_LEVEL,
                description = "Citizenship, Government, Democracy",
                descriptionSwahili = "Uraia, Serikali, Demokrasia",
                color = "#607D8B",
                order = 9
            ),
            Subject(
                id = "commerce_o",
                name = "Commerce",
                nameSwahili = "Biashara",
                level = EducationLevel.O_LEVEL,
                description = "Business Studies, Economics",
                descriptionSwahili = "Masomo ya Biashara, Uchumi",
                color = "#FF9800",
                order = 10
            )
        )
    }

    /**
     * Sample A-Level subjects
     */
    fun getALevelSubjects(): List<Subject> {
        return listOf(
            Subject(
                id = "advanced_math",
                name = "Advanced Mathematics",
                nameSwahili = "Hisabati za Juu",
                level = EducationLevel.A_LEVEL,
                description = "Calculus, Advanced Algebra, Statistics",
                descriptionSwahili = "Calculus, Aljebra za Juu, Takwimu",
                color = "#3F51B5",
                isPopular = true,
                order = 1
            ),
            Subject(
                id = "physics_a",
                name = "Physics",
                nameSwahili = "Fizikia",
                level = EducationLevel.A_LEVEL,
                description = "Advanced Mechanics, Thermodynamics, Modern Physics",
                descriptionSwahili = "Mikanika za Juu, Themodynamiki, Fizikia ya Kisasa",
                color = "#00BCD4",
                isPopular = true,
                order = 2
            ),
            Subject(
                id = "chemistry_a",
                name = "Chemistry",
                nameSwahili = "Kemia",
                level = EducationLevel.A_LEVEL,
                description = "Advanced Organic, Inorganic, and Physical Chemistry",
                descriptionSwahili = "Kemia Hai, Kemia Isiyohai, Kemia Fizikia za Juu",
                color = "#9C27B0",
                isPopular = true,
                order = 3
            ),
            Subject(
                id = "biology_a",
                name = "Biology",
                nameSwahili = "Biolojia",
                level = EducationLevel.A_LEVEL,
                description = "Genetics, Ecology, Evolution",
                descriptionSwahili = "Jenetiki, Ikolojia, Mageuzi",
                color = "#4CAF50",
                isPopular = true,
                order = 4
            )
        )
    }

    /**
     * Sample notes for Mathematics
     */
    fun getSampleMathNotes(): List<Note> {
        return listOf(
            Note(
                id = "note_math_1",
                subjectId = "math_o",
                title = "Introduction to Algebra",
                titleSwahili = "Utangulizi wa Aljebra",
                content = """
                    # Introduction to Algebra
                    
                    Algebra is a branch of mathematics dealing with symbols and the rules for manipulating those symbols.
                    
                    ## Key Concepts:
                    - Variables (x, y, z)
                    - Constants (numbers)
                    - Operations (+, -, ×, ÷)
                    - Equations and expressions
                    
                    ## Example:
                    Solve for x: 2x + 5 = 15
                    
                    Solution:
                    2x = 15 - 5
                    2x = 10
                    x = 5
                """.trimIndent(),
                summary = "Learn the basics of algebraic expressions and equations",
                topics = listOf("Algebra", "Equations", "Variables"),
                keyPoints = listOf(
                    "Variables represent unknown values",
                    "Constants are fixed numbers",
                    "Equations show equality between expressions",
                    "Operations follow specific rules"
                ),
                formulas = listOf(
                    Formula(
                        title = "Linear Equation",
                        latex = "ax + b = c",
                        description = "Standard form of a linear equation"
                    )
                ),
                difficulty = Difficulty.EASY,
                estimatedReadTime = 5,
                order = 1
            )
        )
    }

    /**
     * Sample quiz for Mathematics
     */
    fun getSampleMathQuiz(): Quiz {
        return Quiz(
            id = "quiz_math_1",
            subjectId = "math_o",
            title = "Algebra Basics Quiz",
            titleSwahili = "Maswali ya Msingi ya Aljebra",
            description = "Test your understanding of basic algebraic concepts",
            questions = listOf(
                Question(
                    id = "q1",
                    questionText = "Solve for x: 2x + 3 = 11",
                    questionTextSwahili = "Tatua x: 2x + 3 = 11",
                    type = QuestionType.MULTIPLE_CHOICE,
                    options = listOf("x = 2", "x = 4", "x = 6", "x = 8"),
                    correctAnswer = "x = 4",
                    explanation = "2x = 11 - 3 = 8, therefore x = 4",
                    difficulty = Difficulty.EASY
                ),
                Question(
                    id = "q2",
                    questionText = "Is 5 + 3 = 3 + 5 true?",
                    questionTextSwahili = "Je, 5 + 3 = 3 + 5 ni kweli?",
                    type = QuestionType.TRUE_FALSE,
                    options = listOf("True", "False"),
                    correctAnswer = "True",
                    explanation = "This demonstrates the commutative property of addition",
                    difficulty = Difficulty.EASY
                )
            ),
            totalQuestions = 2,
            duration = 10,
            difficulty = Difficulty.EASY,
            quizType = QuizType.PRACTICE
        )
    }
}
