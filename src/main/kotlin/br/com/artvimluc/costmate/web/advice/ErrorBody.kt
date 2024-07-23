package br.com.artvimluc.costmate.web.advice

class ErrorBody (
    var erroCode: String? = null,
    var statusCode: Int? = null,
    var message: String? = null,
)