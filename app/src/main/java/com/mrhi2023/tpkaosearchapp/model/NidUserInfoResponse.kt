package com.mrhi2023.tpkaosearchapp.model

data class NidUserInfoResponse(var resultcode:String, var message:String, var response: NidUser)

data class NidUser(var id:String, var email:String)
