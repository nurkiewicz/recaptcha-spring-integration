package com.blogspot.nurkiewicz.spring

import org.springframework.context.annotation.{Bean, Configuration, ComponentScan}
import org.springframework.web.servlet.view.{JstlView, InternalResourceViewResolver}
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.servlet.config.annotation.{WebMvcConfigurationSupport, WebMvcConfigurerAdapter, EnableWebMvc}
import java.util.List
import org.springframework.web.method.support.InvocableHandlerMethod
import org.springframework.beans.MutablePropertyValues
import javax.servlet.ServletRequest
import org.springframework.web.servlet.mvc.method.annotation.{ServletRequestDataBinderFactory, RequestMappingHandlerMapping, ExtendedServletRequestDataBinder, RequestMappingHandlerAdapter}
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.bind.{ServletRequestDataBinder, WebDataBinder}
import java.lang.Object
import collection.mutable.{ListBuffer, ArrayBuffer, Buffer}

/**
 * @author Tomasz Nurkiewicz
 * @since 30.10.11, 13:32
 */
@Configuration
@ComponentScan(basePackages = Array("com.blogspot.nurkiewicz.web"))
@EnableWebMvc
class MvcConfiguration extends WebMvcConfigurationSupport {

	@Bean
	def viewResolver() = {
		val resolver = new InternalResourceViewResolver()
		resolver.setViewClass(classOf[JstlView])
		resolver.setPrefix("/WEB-INF/")
		resolver.setSuffix(".jsp")
		resolver
	}

	@Bean
	override def requestMappingHandlerAdapter() =
		new RequestMappingHandlerAdapter {
			override def createDataBinderFactory(binderMethods: List[InvocableHandlerMethod]) =
				new ServletRequestDataBinderFactory(binderMethods, getWebBindingInitializer) {
					override def createBinderInstance(target: AnyRef, objectName: String, request: NativeWebRequest): ServletRequestDataBinder =
						new MyWebDataBinder(target, objectName)
				}
		}
}

class MyWebDataBinder(target: AnyRef, objectName: String) extends ExtendedServletRequestDataBinder(target, objectName) {

	val aliases: Buffer[(String, String)] = new ArrayBuffer[(String, String)]

	def addFieldAlias(from: String, to: String) {
		aliases += (from -> to)
	}

	override def addBindValues(mpvs: MutablePropertyValues, request: ServletRequest) {
		aliases foreach  {case(from, to) =>
			mpvs.add(to, mpvs.getPropertyValue(from).getValue)
		}
	}
}