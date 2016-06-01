package domain

import java.util

case class User(login: String, photo: String = "https://www.google.ru/imgres?imgurl=http%3A%2F%2Fimg0.liveinternet.ru%2Fimages%2Fattach%2Fb%2F3%2F3%2F277%2F3277148_2.jpg&imgrefurl=http%3A%2F%2Fwww.liveinternet.ru%2Fcommunity%2F-no_entry-%2Frubric%2F662086%2F&docid=f9cniV7PaW1u_M&tbnid=6gVj_N5Tk3fF3M%3A&w=150&h=150&bih=643&biw=1366&ved=0ahUKEwiJmp_rqYfNAhWJHpoKHZEYCzUQMwgiKAUwBQ&iact=mrc&uact=8"){
  override def equals( arg:Any) = arg match {
    case User(l, _) => l == login
    case _ => false
  }
}

