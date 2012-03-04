package com.blogspot.nurkiewicz.spring

import org.springframework.web.servlet.config.annotation.{WebMvcConfigurerAdapter, EnableWebMvc}
import org.springframework.context.annotation.{Bean, Configuration, ComponentScan}
import org.springframework.web.servlet.view.{JstlView, InternalResourceViewResolver}

/**
 * @author Tomasz Nurkiewicz
 * @since 30.10.11, 13:32
 */
@Configuration
@ComponentScan(basePackages = Array("com.blogspot.nurkiewicz.web"))
@EnableWebMvc
class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	def viewResolver() = {
		val resolver = new InternalResourceViewResolver()
		resolver.setViewClass(classOf[JstlView])
		resolver.setPrefix("/WEB-INF/")
		resolver.setSuffix(".jsp")
		resolver
	}

}