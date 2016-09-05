package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms._
import play.api.db.Database
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import views.html

/**
  * Created by superorange on 4/1/16.
  */
class Basic @Inject()(db: Database)(val messagesApi: MessagesApi) extends Controller with I18nSupport {
//-------index
val helloForm = Form(
  tuple(
    "name" -> nonEmptyText,
    "color" -> optional(text)
  )
)
  def index = Action {

    // Future{SparkMLLibUtility.SparkMLLibExample}
    Ok(html.index(helloForm))
  }

//--------------regression training---------------
  def simp_reg = Action {
    val InputParam = Form(
      tuple(
        "inputpath" -> text,
        "maxIter" -> text,
        "regParam" -> text,
        "elaParam" -> text

      )
    )
  val anyData = Map("inputpath" -> "bob", "maxIter" -> "21", "regParam"->"0.3","elaParam"->"0.8")
  InputParam.bind(anyData)

  def username = System.getenv("USER")
  println("USER= " +username)

    Ok(html.mlModel.linearRegression(InputParam, null))

  }




}
