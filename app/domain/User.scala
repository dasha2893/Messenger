package domain


case class User(login: String, pmd5: String = "", photo: String = "")