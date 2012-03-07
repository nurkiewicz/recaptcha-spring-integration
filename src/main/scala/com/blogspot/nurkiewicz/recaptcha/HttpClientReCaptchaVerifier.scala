package com.blogspot.nurkiewicz.recaptcha

import java.io.InputStream
import org.apache.commons.io.IOUtils
import com.blogspot.nurkiewicz.web.ReCaptchaSecured
import org.springframework.stereotype.Service
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions._
import org.springframework.beans.factory.annotation.{Value, Autowired}
import io.Source

/**
 * @author Tomasz Nurkiewicz
 * @since 06.03.12, 22:05
 */

trait ReCaptchaVerifier {
	def validate(reCaptchaRequest: ReCaptchaSecured): Boolean

}

@Service
class HttpClientReCaptchaVerifier @Autowired()(
		                                              httpClient: HttpClient,
		                                              servletRequest: HttpServletRequest,
		                                              @Value("${recaptcha_url}") recaptchaUrl: String,
		                                              @Value("${recaptcha_private_key}") recaptchaPrivateKey: String
		                                              ) extends ReCaptchaVerifier {

	def validate(reCaptchaRequest: ReCaptchaSecured): Boolean = {
		val post = new HttpPost(recaptchaUrl)
		post.setEntity(new UrlEncodedFormEntity(List(
			new BasicNameValuePair("privatekey", recaptchaPrivateKey),
			new BasicNameValuePair("remoteip", servletRequest.getRemoteAddr),
			new BasicNameValuePair("challenge", reCaptchaRequest.recaptchaChallenge),
			new BasicNameValuePair("response", reCaptchaRequest.recaptchaResponse)))
		)
		val response = httpClient.execute(post)
		isReCaptchaSuccess(response.getEntity.getContent)
	}

	private def isReCaptchaSuccess(response: InputStream) = {
		val responseLines = Option(response) map {
			Source.fromInputStream(_).getLines().toList
		} getOrElse Nil
		responseLines match {
			case "true" :: _ => true
			case "false" :: "incorrect-captcha-sol" :: _=> false
			case "false" :: msg :: _ => throw new ReCaptchaException(msg)
			case resp => throw new ReCaptchaException("Unrecognized response: " + resp.toList)
		}
	}

}

class ReCaptchaException(msg: String) extends RuntimeException(msg)
