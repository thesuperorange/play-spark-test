package controllers

import javax.inject.Inject

import org.apache.spark.ml.regression.LinearRegression

import org.apache.spark.{SparkConf, SparkContext}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import views.html

/**
  * Created by superorange on 4/1/16.
  */
class Regression @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val InputParam = Form(
    tuple(
      "inputpath" -> text,
      "maxIter" -> text,
      "regParam" -> text,
      "elaParam" -> text

    )
  )



  def callRegression = Action { implicit request =>
    InputParam.bindFromRequest.fold(
      formWithErrors => {
        println("ERROR" + formWithErrors)
        BadRequest("error in callRegression")
      }, { case (inputFilename, maxIter, regParam, elaParam) =>
        val conf = new SparkConf(false) // skip loading external settings
          .setMaster("spark://TCPOC001:7077") // run locally with enough threads
          .setAppName("firstSparkApp")
          //.set("spark.logConf", "true")
         // .set("spark.driver.host", "140.110.141.62")
        val sc = new SparkContext(conf)


        val sqlContext = new org.apache.spark.sql.SQLContext(sc)

        val training = sqlContext.read.format("libsvm").load(inputFilename)

        val lr = new LinearRegression().setMaxIter(maxIter.toInt).setRegParam(regParam.toDouble).setElasticNetParam(elaParam.toDouble)
        // Fit the model
        val lrModel = lr.fit(training)

        sc.stop()
        Ok(html.mlModel.linearRegression(InputParam, lrModel.intercept.toString))


      }

    )
  }



}
