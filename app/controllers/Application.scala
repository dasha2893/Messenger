package controllers

import domain.{Message, User}
import play.api.mvc._

import scala.Option
import scala.collection.mutable

object Storage {
  val usersAndMassages = collection.mutable.HashMap[(String, String), mutable.Buffer[Message]]()
  val usersAndFriends = collection.mutable.HashMap[User, mutable.Buffer[User]]()
  val usersAndDialogs = collection.mutable.HashMap[String, mutable.Buffer[User]]()
  val usersAndInfo = collection.mutable.HashMap[String, User]()
  init()

  def init() {

    usersAndMassages +=(
      "Daria" -> "Max" -> mutable.Buffer(
        Message(from = "Daria", to = "Max", "Привет! Как дела?)"),
        Message(from = "Max", to = "Daria", "Привет!) норм, а у тя?"),
        Message(from = "Daria", to = "Max", "ЗБС"),
        Message(from = "Daria", to = "Max", "Че делаешь"),
        Message(from = "Max", to = "Daria", "ниче, сижу в Messenger"),
        Message(from = "Daria", to = "Max", "аа, ну и как?"),
        Message(from = "Max", to = "Daria", "круто!!")
      ),
      "Lebron" -> "Max" -> mutable.Buffer(
        Message(from = "Max", to = "Lebron", "Hey man! How are user after loosing 6th NBA final?"),
        Message(from = "Lebron", to = "Max", "Shit, I will kill Steph next season"),
        Message(from = "Lebron", to = "Max", "Good luck!!!")
      )
      )

    usersAndFriends +=(
      User("Max", "http://cs625722.vk.me/v625722760/5b1de/qGLHEiFw11o.jpg") -> mutable.Buffer(
        User("Kate", "http://cs630216.vk.me/v630216644/2e126/mYG74XCSFcw.jpg"),
        User("Igor", "http://cs636930.vk.me/v636930922/2e4b/kponFFQLxUs.jpg"),
        User("Egor", "http://cs605817.vk.me/v605817644/3537/Jrl90veK9xE.jpg"),
        User("Masha", "http://cs411823.vk.me/v411823644/208c/HhJOWfXTTRY.jpg")
      ),
      User("Daria", "http://cs628423.vk.me/v628423360/1b5b2/5jdASCmA_Q0.jpg") -> mutable.Buffer(
        User("Kate", "http://cs630216.vk.me/v630216644/2e126/mYG74XCSFcw.jpg"),
        User("Igor", "http://cs636930.vk.me/v636930922/2e4b/kponFFQLxUs.jpg"),
        User("Egor", "http://cs605817.vk.me/v605817644/3537/Jrl90veK9xE.jpg"),
        User("Masha", "http://cs411823.vk.me/v411823644/208c/HhJOWfXTTRY.jpg"),
        User("Alex", "http://cs9537.vk.me/u19071528/128071405/y_321ff615.jpg"),
        User("Lebron", "http://cs9537.vk.me/u19071528/128071405/y_e4b59912.jpg"),
        User("Tom", "http://cs9537.vk.me/u19071528/128071405/y_b658902c.jpg"),
        User("Daria", "http://cs628423.vk.me/v628423360/1b5b2/5jdASCmA_Q0.jpg"),
        User("Max", "http://cs625722.vk.me/v625722760/5b1de/qGLHEiFw11o.jpg")
      )
      )
    usersAndDialogs += (
      "Max" -> mutable.Buffer(
        User("Lebron", "202cb962ac59075b964b07152d234b70", "http://cs9537.vk.me/u19071528/128071405/y_e4b59912.jpg"),
        User("Daria", "202cb962ac59075b964b07152d234b70", "http://cs628423.vk.me/v628423360/1b5b2/5jdASCmA_Q0.jpg")),
      "Lebron" -> mutable.Buffer(
        User("Max", "202cb962ac59075b964b07152d234b70", "http://cs625722.vk.me/v625722760/5b1de/qGLHEiFw11o.jpg")),
      "Daria" -> mutable.Buffer(
        User("Max", "202cb962ac59075b964b07152d234b70", "http://cs625722.vk.me/v625722760/5b1de/qGLHEiFw11o.jpg"))
      )

    usersAndInfo +=(
      "Max" -> User("Max", "202cb962ac59075b964b07152d234b70", "http://cs625722.vk.me/v625722760/5b1de/qGLHEiFw11o.jpg"),
      "Lebron" -> User("Lebron", "202cb962ac59075b964b07152d234b70", "http://cs9537.vk.me/u19071528/128071405/y_e4b59912.jpg"),
      "Daria" -> User("Daria", "202cb962ac59075b964b07152d234b70", "http://cs628423.vk.me/v628423360/1b5b2/5jdASCmA_Q0.jpg"),
      "Kate" -> User("Kate", "202cb962ac59075b964b07152d234b70", "http://cs630216.vk.me/v630216644/2e126/mYG74XCSFcw.jpg"),
      "Igor" -> User("Igor", "202cb962ac59075b964b07152d234b70", "http://cs636930.vk.me/v636930922/2e4b/kponFFQLxUs.jpg"),
      "Egor" -> User("Egor", "202cb962ac59075b964b07152d234b70", "http://cs605817.vk.me/v605817644/3537/Jrl90veK9xE.jpg"),
      "Masha" -> User("Masha", "202cb962ac59075b964b07152d234b70", "http://cs411823.vk.me/v411823644/208c/HhJOWfXTTRY.jpg"),
      "Alex" -> User("Alex", "202cb962ac59075b964b07152d234b70", "http://cs9537.vk.me/u19071528/128071405/y_321ff615.jpg"),
      "Tom" -> User("Tom", "202cb962ac59075b964b07152d234b70", "http://cs9537.vk.me/u19071528/128071405/y_b658902c.jpg")
      )
  }
}

class Application extends Controller {

  def index = Action { request =>
    println("index ")
    val parameters = request.queryString.toMap
    if (parameters.keys.isEmpty) {
      Ok(views.html.authorization())
    } else {
      val login = parameters.get("login").get.head
      val pmd5 = parameters.get("pmd5").get.head
      val maybeUser = Storage.usersAndInfo.get(login)

      maybeUser match {
        case Some(User(l, md5, photo)) if l == login && md5 == pmd5 =>
          println(s"User $login is log in")
          val currentUserDialogs = Storage.usersAndDialogs.getOrElse(login,collection.mutable.Buffer[User]())
          Ok(views.html.main(currentUserDialogs,l,md5))
        case _ => Ok(views.html.authorization())
      }
    }

  }

  def getMessages(f1: String, f2: String) = Action {
    println("getMessagesByFriend()")

    val infoTo = Storage.usersAndInfo(f1)
    val infoFrom = Storage.usersAndInfo(f2)
    if (f1.compare(f2) < 0) {
      val massages = Storage.usersAndMassages.getOrElse((f1, f2), mutable.Buffer[Message]())
      Ok(views.html.messages(infoTo,infoFrom, massages))

    } else {
      val messages = Storage.usersAndMassages.getOrElse((f2, f1), mutable.Buffer[Message]())
      Ok(views.html.messages(infoTo, infoFrom, messages))
    }
  }

  def getFriends(user: String) = Action {
    println("getFriends()")
    val info = Storage.usersAndInfo.get(user).get
    println("info= "+ info)
    if (Storage.usersAndFriends.exists(_._1 == User(info.login,info.photo))) {
      val listFriends = Storage.usersAndFriends.get(User(info.login,info.photo)).get
      println(listFriends)
      Ok(views.html.friends(listFriends))
    }
    else {
      println("nothing")
      Ok(views.html.friends(collection.mutable.Buffer()))
    }
  }

  def saveMessage(from: String, to: String, message: String) = Action {
    println("saveMessage()")
    val infoTo = Storage.usersAndInfo(to)
    println("infoTo= " + infoTo)
    println("from = " + from)
    println("check exist of dialog in hashMap= " + Storage.usersAndDialogs.exists(_._1 == from))

    //check exist of dialog in hashMap. If it doesn't exist then add new dialog
    if (Storage.usersAndDialogs.exists(_._1 == from)) {
      if (!Storage.usersAndDialogs(from).contains(infoTo)) {
        Storage.usersAndDialogs.get(from).get += infoTo
        println(Storage.usersAndDialogs.get(from).get)
      }
    }
    else Storage.usersAndDialogs += from -> mutable.Buffer(infoTo)

    //save new massage
    if (from.compare(to) < 0) {
      if (Storage.usersAndMassages.exists(_._1 ==(from, to))) Storage.usersAndMassages.get((from, to)).get += Message(from, to, message)
      else Storage.usersAndMassages += (from, to) -> mutable.Buffer(Message(from, to, message))
    }
    else {
      if (Storage.usersAndMassages.exists(_._1 ==(to, from))) Storage.usersAndMassages.get((to, from)).get += Message(from, to, message)
      else Storage.usersAndMassages += (to, from) -> mutable.Buffer(Message(from, to, message))
    }
    println(Message(from, to, message))

    Ok("")
  }

//  def getDialogs(user: String) = Action {
//    println("getDialogs")
//    val currentUserDialogs = Storage.usersAndDialogs(user)
//
//    Ok(views.html.dialogs(currentUserDialogs))
//  }


}