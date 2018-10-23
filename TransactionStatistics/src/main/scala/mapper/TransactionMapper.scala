package mapper

import model.{InvalidRow, Transaction}

import scala.util.{Failure, Success, Try}

object TransactionMapper {

  def toTransaction(line : String) : Try[Transaction] = Try {
    val Array(tId, aId, day, category, amount) = line.split(",")
    Transaction(tId, aId, day.toInt, category, amount.toDouble)
  }

  def toTransactions(lines : Traversable[String]) : List[Transaction] = {

    val allTransactions = for{
      line <- lines

    } yield( toTransaction(line), line )

    val validTrxnData = allTransactions collect{ case (Success(trxn), _) => trxn }
    validTrxnData.toList
  }
}
