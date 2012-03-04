package com.blogspot.nurkiewicz

import org.springframework.stereotype.Service
import web.Comment
import collection.mutable.{ArrayBuffer, Buffer}
import java.util.Date

/**
 * @author Tomasz Nurkiewicz
 * @since 03.03.12, 18:33
 */
@Service
class CommentService {

	private val comments = new ArrayBuffer[Comment]

	def addComment(comment: Comment) {
		comment.date = new Date()
		comments += comment
	}

	def listComments() = comments.reverse

}
