package com.ikhsanulkarim.mynote

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * from note")
    fun getAllNote(): List<NoteModel>

    @Insert(onConflict = REPLACE)
    fun insertNote(note: NoteModel)

    @Delete
    fun dalateNote(note: NoteModel)

    @Query("UPDATE note Set title=:noteTitle, message =:noteMassage WHERE id =:noteId")
    fun upDatenote(noteTitle: String, noteMassage: String, noteId: Long)

}