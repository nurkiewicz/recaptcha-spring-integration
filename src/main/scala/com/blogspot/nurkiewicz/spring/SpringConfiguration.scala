package com.blogspot.nurkiewicz.spring

import net._01001111.text.LoremIpsum
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.context.annotation.{Bean, ScopedProxyMode, ComponentScan, Configuration}

/**
 * @author Tomasz Nurkiewicz
 * @since 09.10.11, 23:01
 */
@Configuration
@ComponentScan(basePackages = Array("com.blogspot.nurkiewicz"),
	scopedProxy = ScopedProxyMode.TARGET_CLASS,
	excludeFilters = Array(
		new ComponentScan.Filter(value = Array[Class[_]](classOf[Controller], classOf[ComponentScan], classOf[EnableWebMvc]))
))
class SpringConfiguration {

	@Bean
	def loremIpsum() = new LoremIpsum

}
