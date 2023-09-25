package com.tarc.edu.etrack.ui.register

data class user(
    val username: String ?= null,
    val email: String ?= null,
    val password: String ?= null,
    val role: String ?= null,
)
