package com.studyhub.tz.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.studyhub.tz.data.model.*

/**
 * Type converters for Room database to handle complex data types
 */
class Converters {
    private val gson = Gson()

    // String List
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Formula List
    @TypeConverter
    fun fromFormulaList(value: List<Formula>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFormulaList(value: String): List<Formula> {
        val listType = object : TypeToken<List<Formula>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Diagram List
    @TypeConverter
    fun fromDiagramList(value: List<Diagram>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDiagramList(value: String): List<Diagram> {
        val listType = object : TypeToken<List<Diagram>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Question List
    @TypeConverter
    fun fromQuestionList(value: List<Question>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toQuestionList(value: String): List<Question> {
        val listType = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(value, listType)
    }

    // PaperQuestion List
    @TypeConverter
    fun fromPaperQuestionList(value: List<PaperQuestion>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPaperQuestionList(value: String): List<PaperQuestion> {
        val listType = object : TypeToken<List<PaperQuestion>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Achievement List
    @TypeConverter
    fun fromAchievementList(value: List<Achievement>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAchievementList(value: String): List<Achievement> {
        val listType = object : TypeToken<List<Achievement>>() {}.type
        return gson.fromJson(value, listType)
    }

    // StudyReminder List
    @TypeConverter
    fun fromStudyReminderList(value: List<StudyReminder>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStudyReminderList(value: String): List<StudyReminder> {
        val listType = object : TypeToken<List<StudyReminder>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Map<String, String>
    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }

    // Enums
    @TypeConverter
    fun fromEducationLevel(value: EducationLevel): String {
        return value.name
    }

    @TypeConverter
    fun toEducationLevel(value: String): EducationLevel {
        return EducationLevel.valueOf(value)
    }

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String {
        return value.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }

    @TypeConverter
    fun fromQuizType(value: QuizType): String {
        return value.name
    }

    @TypeConverter
    fun toQuizType(value: String): QuizType {
        return QuizType.valueOf(value)
    }

    @TypeConverter
    fun fromQuestionType(value: QuestionType): String {
        return value.name
    }

    @TypeConverter
    fun toQuestionType(value: String): QuestionType {
        return QuestionType.valueOf(value)
    }

    @TypeConverter
    fun fromExamType(value: ExamType): String {
        return value.name
    }

    @TypeConverter
    fun toExamType(value: String): ExamType {
        return ExamType.valueOf(value)
    }

    @TypeConverter
    fun fromStudyActivityType(value: StudyActivityType): String {
        return value.name
    }

    @TypeConverter
    fun toStudyActivityType(value: String): StudyActivityType {
        return StudyActivityType.valueOf(value)
    }
}
