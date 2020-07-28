package ru.skillbranch.kotlinexample

import android.util.Log
import java.util.*

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User{
        return when{
            map.containsKey(email.toLowerCase(Locale.ROOT)) -> throw IllegalArgumentException("A user with this email already exists")
            else -> User.makeUser(fullName, email = email, password = password)
                .also { user-> map[user.login] = user }
        }
    }

    fun registerUserByPhone(
        fullName: String,
        rawPhone: String
    ): User{
        val mapInfo = map.toString()
        return when{
            !isValidPhone(rawPhone) -> throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
            map.containsKey(rawPhone.replace("[^+\\d]".toRegex(), "")) -> throw IllegalArgumentException("A user with this phone already exists")
            else -> User.makeUser(fullName, phone = rawPhone)
                .also { user-> map[user.login] = user }
        }
    }

    private fun isValidPhone(rawPhone: String): Boolean {
        //+7 (917) 971 11-11
        var digitCounter = 0

        for (item in rawPhone) {
            val number = Character.getNumericValue(item)
            if(number in 1..9) digitCounter++
        }

        return when{
            rawPhone[0] != '+'  -> false
            digitCounter != 11  -> false
            else -> true
        }
    }

    fun loginUser(login: String, password: String): String? {
        return map[login.trim()]?.run {
            if(checkPassword(password)) this.userInfo
            else null
        }
    }

    fun requestAccessCode(login: String) : Unit{

    }

}