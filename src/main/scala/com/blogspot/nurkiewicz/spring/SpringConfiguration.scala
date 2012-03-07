package com.blogspot.nurkiewicz.spring

import net._01001111.text.LoremIpsum
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.springframework.context.annotation._
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

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
@PropertySource(Array("classpath:/app.properties"))
class SpringConfiguration {

	@Bean
	def httpClient() = new DefaultHttpClient(clientConnManager())

	@Bean
	def clientConnManager() = new ThreadSafeClientConnManager()

	@Bean
	def propertySourcesPlaceholderConfigurer() = new PropertySourcesPlaceholderConfigurer()

}
