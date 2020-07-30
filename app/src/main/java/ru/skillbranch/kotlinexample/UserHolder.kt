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
        val phoneLogin = rawPhone.replace("[^+\\d]".toRegex(), "")
        return when{
            phoneLogin[0] != '+' || phoneLogin.length != 12 -> throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
            map.containsKey(phoneLogin) -> throw IllegalArgumentException("A user with this phone already exists")
            else -> User.makeUser(fullName, phone = phoneLogin)
                .also { user-> map[user.login] = user }
        }
    }

//    private fun isValidPhone(rawPhone: String): Boolean {
//        //+7 (917) 971 11-11
//        var digitCounter = 0
//
//        for (item in rawPhone) {
//            val number = Character.getNumericValue(item)
//            if(number in 1..9) digitCounter++
//        }
//
//        return when{
//            rawPhone[0] != '+'  -> false
//            digitCounter != 11  -> false
//            else -> true
//        }
//    }

    fun loginUser(login: String, password: String): String? {
        if(login[0] == '+') {
            val loginPhone = login.replace("[^+\\d]".toRegex(), "")
            return map[loginPhone.trim()]?.let {
                if (it.checkPassword(password)) it.userInfo
                else null}}
        return map[login.trim()]?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }



//    Запрос кода авторизации
//    Необходимо реализовать метод объекта (object UserHolder) для запроса нового кода авторизации пользователя по номеру телефона
//    Реализуй метод requestAccessCode(login: String) : Unit, после выполнения данного метода у пользователя с соответствующим логином
//    должен быть сгенерирован новый код авторизации и помещен в свойство accessCode,
//    соответственно должен измениться и хеш пароля пользователя (вызов метода loginUser должен отрабатывать корректно)

    fun requestAccessCode(login: String) : Unit{

    }

}