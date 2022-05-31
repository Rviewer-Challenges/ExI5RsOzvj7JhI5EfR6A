package com.fernan.game.memory.provider

import android.content.Context
import android.content.SharedPreferences
import com.fernan.game.memory.provider.SharedManager

class SharedManager(_context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // shared pref mode
    var PRIVATE_MODE = 0
    fun setBoolean(KEY_BOOL: String?, VAL: Boolean?) {
        editor.putBoolean(KEY_BOOL, VAL!!)
        editor.commit()
    }

    fun setString(KEY_STRING: String?, VAL: String?) {
        editor.putString(KEY_STRING, VAL)
        editor.commit()
    }

    fun setInt(KEY_INT: String?, VAL: Int) {
        editor.putInt(KEY_INT, VAL)
        editor.commit()
    }

    operator fun set(KEY: String?, VAL: Any?) {
        when (VAL) {
            is String -> {
                setString(KEY, VAL as String?)
            }
            is Int -> {
                setInt(KEY, VAL)
            }
            is Boolean -> {
                setBoolean(KEY, VAL as Boolean?)
            }
        }
    }

    fun getBoolean(KEY_BOOL: String?): Boolean {
        return pref.getBoolean(KEY_BOOL, false)
    }

    fun remove(KEY_NAME: String?) {
        if (pref.contains(KEY_NAME)) {
            editor.remove(KEY_NAME)
            editor.commit()
        }
    }

    operator fun contains(KEY: String?): Boolean {
        return pref.contains(KEY)
    }

    fun getString(KEY_STRING: String?): String? {
        return if (pref.contains(KEY_STRING)) {
            pref.getString(KEY_STRING, "")
        } else ""
    }

    fun getInt(KEY_INT: String?): Int {
        return if (pref.contains(KEY_INT)) {
            pref.getInt(KEY_INT, -1)
        } else -1
    }

    companion object {
        private const val PREF_NAME = "com.fernan.game.memory.shared"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}