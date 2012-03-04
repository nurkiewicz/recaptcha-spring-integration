package com.blogspot.nurkiewicz.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping}
import RequestMethod._
import org.springframework.beans.factory.annotation.Autowired
import reflect.BeanProperty
import com.blogspot.nurkiewicz.CommentService
import java.util.Date
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.springframework.web.servlet.ModelAndView
import scala.collection.JavaConverters._

/**
 * @author Tomasz Nurkiewicz
 * @since 03.03.12, 18:31
 */
@Controller
class CommentsController @Autowired()(commentService: CommentService) {

	@RequestMapping(value = Array("/comments"), method = Array(GET))
	def displayComments(req: HttpServletRequest, resp: HttpServletResponse) =
		new ModelAndView("comments", "comments", commentService.listComments().asJava)

	@RequestMapping(value = Array("/comments"), method = Array(POST))
	def addComment(comment: Comment) = {
		commentService addComment comment
		"redirect:/"
	}

}

class Comment {
	@BeanProperty var name = ""
	@BeanProperty var date: Date = _
	@BeanProperty var contents = ""
}