package com.example.musiclyrics


const val API_KEY = "fb20e5b416b5d8f3bb484102abca1638"
const val BASE_URL_MUSICXMATCH = "https://api.musixmatch.com/ws/1.1/"
const val API_KEY_APPLE = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgQ4nLC+W4ytzNzgkOPV8cQxFEsAw7rNijYtX5UvM33IGgCgYIKoZIzj0DAQehRANCAAT7yObspKznpoBX1DkCKHCBeguLvIWx4wjqTAjhLPuQyPZBwF9N5fE9EjtyIPglI8ArHyoQdApNJmUc3fMg580T"
const val BASE_URL_APPLEMUSIC = "https://api.music.apple.com/v1/"


enum class storefront(val country: String) {
    US("us"),
    FR("fr")
}