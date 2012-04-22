package com.blogspot.nurkiewicz.recaptcha

import com.blogspot.nurkiewicz.web.ReCaptchaSecured
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.{Value, Autowired}
import scala.collection.JavaConverters._

/**
 * @author Tomasz Nurkiewicz
 * @since 06.03.12, 22:05
 */

trait ReCaptchaVerifier {
	def validate(reCaptchaRequest: ReCaptchaSecured): Boolean
}

@Service
class ReCaptchaFormToHttpRequest @Autowired() (servletRequest: HttpServletRequest, @Value("${recaptcha_private_key}") recaptchaPrivateKey: String) {

	def transform(form: ReCaptchaSecured) = Map(
		"privatekey" -> recaptchaPrivateKey,
		"remoteip" -> servletRequest.getRemoteAddr,
		"challenge" -> form.recaptchaChallenge,
		"response" -> form.recaptchaResponse).asJava

}

@Service
class ReCaptchaServerResponseToResult {

	def transform(response: String) = {
		val responseLines = response.split('\n').toList
		responseLines match {
			case "true" :: _ => true
			case "false" :: "incorrect-captcha-sol" :: _=> false
			case "false" :: msg :: _ => throw new ReCaptchaException(msg)
			case resp => throw new ReCaptchaException("Unrecognized response: " + resp.toList)
		}
	}

}

class ReCaptchaException(msg: String) extends RuntimeException(msg)
