package com.studyhub.tanzania.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * Type converters for Room database
 */
class Converters {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromAttachmentList(value: List<Attachment>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAttachmentList(value: String?): List<Attachment>? {
        return value?.let {
            val listType = object : TypeToken<List<Attachment>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromFormulaList(value: List<Formula>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toFormulaList(value: String?): List<Formula>? {
        return value?.let {
            val listType = object : TypeToken<List<Formula>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromDiagramList(value: List<Diagram>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toDiagramList(value: String?): List<Diagram>? {
        return value?.let {
            val listType = object : TypeToken<List<Diagram>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromTopicList(value: List<Topic>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toTopicList(value: String?): List<Topic>? {
        return value?.let {
            val listType = object : TypeToken<List<Topic>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromQuestionList(value: List<Question>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toQuestionList(value: String?): List<Question>? {
        return value?.let {
            val listType = object : TypeToken<List<Question>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromAnswerList(value: List<Answer>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAnswerList(value: String?): List<Answer>? {
        return value?.let {
            val listType = object : TypeToken<List<Answer>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromPaperQuestionList(value: List<PaperQuestion>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toPaperQuestionList(value: String?): List<PaperQuestion>? {
        return value?.let {
            val listType = object : TypeToken<List<PaperQuestion>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromMarkingSchemeList(value: List<MarkingScheme>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toMarkingSchemeList(value: String?): List<MarkingScheme>? {
        return value?.let {
            val listType = object : TypeToken<List<MarkingScheme>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromPaperAnswerList(value: List<PaperAnswer>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toPaperAnswerList(value: String?): List<PaperAnswer>? {
        return value?.let {
            val listType = object : TypeToken<List<PaperAnswer>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromWeeklyProgressList(value: List<WeeklyProgress>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toWeeklyProgressList(value: String?): List<WeeklyProgress>? {
        return value?.let {
            val listType = object : TypeToken<List<WeeklyProgress>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromMonthlyProgressList(value: List<MonthlyProgress>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toMonthlyProgressList(value: String?): List<MonthlyProgress>? {
        return value?.let {
            val listType = object : TypeToken<List<MonthlyProgress>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromAchievementList(value: List<Achievement>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAchievementList(value: String?): List<Achievement>? {
        return value?.let {
            val listType = object : TypeToken<List<Achievement>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromUserPreferences(value: UserPreferences?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toUserPreferences(value: String?): UserPreferences? {
        return value?.let {
            gson.fromJson(it, UserPreferences::class.java)
        }
    }
}