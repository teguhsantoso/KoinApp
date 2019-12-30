package de.koinapp.repositories

import de.koinapp.api.GithubApi

class UserRepository(private val api: GithubApi) {
    fun getAllUsers() = api.getUsers()
}