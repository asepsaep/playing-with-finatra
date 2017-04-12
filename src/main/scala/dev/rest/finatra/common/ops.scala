package dev.rest.finatra.common

object ops {

  import io.getquill._
  import org.joda.time.{ DateTime, DateTimeZone }

  implicit class RichDate(left_l: DateTime) {
    def >=(right_r: DateTime) = quote(infix"$left_l >= $right_r".as[Boolean])
    def <=(right_r: DateTime) = quote(infix"$left_l <= $right_r".as[Boolean])
    def <(right_r: DateTime) = quote(infix"$left_l < $right_r".as[Boolean])
    def >(right_r: DateTime) = quote(infix"$left_l > $right_r".as[Boolean])
  }

  implicit class Pageable[Q <: Query[_]](q: Q) {
    def slice(limit_l: Int, offset_o: Int) = quote(infix"$q LIMIT $limit_l OFFSET $offset_o".as[Q])
  }

  implicit val decodeDateTime = mappedEncoding[Long, DateTime](new DateTime(_).withZone(DateTimeZone.forID("Asia/Jakarta")))
  implicit val encodeDateTime = mappedEncoding[DateTime, Long](_.getMillis)

}
