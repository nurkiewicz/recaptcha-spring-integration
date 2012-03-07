package com.blogspot.nurkiewicz.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping}
import RequestMethod._
import org.springframework.beans.factory.annotation.Autowired
import reflect.BeanProperty
import com.blogspot.nurkiewicz.CommentService
import java.util.Date
import org.springframework.web.servlet.ModelAndView
import scala.collection.JavaConverters._
import com.blogspot.nurkiewicz.recaptcha.ReCaptchaVerifier

/**
 * @author Tomasz Nurkiewicz
 * @since 03.03.12, 18:31
 */
@Controller
class CommentsController @Autowired()(
		                                     commentService: CommentService,
		                                     reCaptchaVerifier: ReCaptchaVerifier
		                                     ) {

	@RequestMapping(value = Array("/comments"), method = Array(GET))
	def displayComments() =
		new ModelAndView("comments", "comments", commentService.listComments().asJava)

	@RequestMapping(value = Array("/comments"), method = Array(POST))
	def addComment(comment: NewComment) = {
		if(!reCaptchaVerifier.validate(comment)) {
			throw new IllegalArgumentException("Incorrect ReCaptcha")
		}
		commentService addComment new Comment(comment.name, comment.contents)
		"redirect:/"
	}

}

class Comment(_name: String, _contents: String) {
	@BeanProperty var name = _name
	@BeanProperty var date: Date = _
	@BeanProperty var contents = _contents
}

class NewComment extends ReCaptchaSecured {
	@BeanProperty var name = ""
	@BeanProperty var contents = ""
}