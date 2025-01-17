package com.example.notes.model

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var currentTime: String
) : Parcelable
