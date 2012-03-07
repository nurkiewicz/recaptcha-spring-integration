package com.blogspot.nurkiewicz.web

import reflect.BeanProperty

/**
 * @author Tomasz Nurkiewicz
 * @since 05.03.12, 22:43
 */
trait ReCaptchaSecured {

	@BeanProperty var recaptchaChallenge = ""
	@BeanProperty var recaptchaResponse = ""

}
