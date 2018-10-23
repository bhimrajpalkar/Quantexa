package model

case class Transaction(transactionId: String, accountId: String, transactionDay: Int, category : String, transactionAmount: Double) {}

case class InvalidRow(lineNumber : Int, line : String)