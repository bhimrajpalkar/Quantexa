package reader

import better.files.{File => SFile}
import com.typesafe.scalalogging.LazyLogging
import mapper.TransactionMapper._
import model.Transaction

import scala.collection.immutable.TreeMap

object UserStories extends LazyLogging {

  import logger._

  def main(args: Array[String]): Unit = {

    val file = SFile("src/main/resources/transactions.txt")
    val transactions = toTransactions(file.lines)
    story1(transactions)
    debug("\n\n\n\n\n")
    story2(transactions)
  }

  private def story1(transactions: List[Transaction]) = {

    for{
      (day, transactionsByDay) <- TreeMap( transactions.groupBy(trxn => trxn.transactionDay).toSeq: _*)
      totalTransactedAmountPerDay = transactionsByDay.map(_.transactionAmount).sum
      _ = debug(s"day $day : amount $totalTransactedAmountPerDay")
    } yield(day, totalTransactedAmountPerDay)
  }

  private def story2(transactions: List[Transaction]) = {

    for {
      (accountId, trxnsByAccountId) <- TreeMap(transactions.groupBy(trxn => trxn.accountId).toSeq: _*)
      (category, trxnsByCategoryByAccountId) <- TreeMap(trxnsByAccountId.groupBy(t => t.category).toSeq: _*)
      amount = trxnsByCategoryByAccountId.map(_.transactionAmount).sum
      avg = amount / trxnsByCategoryByAccountId.size
      _ = println(s"\nAccount Id $accountId for $category : " + avg + " (" + trxnsByCategoryByAccountId.map(_.transactionAmount).mkString(",") + "), " + trxnsByCategoryByAccountId.size + ")")
    } yield(accountId, category, avg)
  }
}