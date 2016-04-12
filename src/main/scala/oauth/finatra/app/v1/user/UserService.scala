package oauth.finatra.app.v1.user

import javax.inject.Inject
import com.twitter.util.Future
import oauth.finatra.persistence.UserRepository
import scala.concurrent.ExecutionContext.Implicits.global

class UserService @Inject() (userRepository: UserRepository) {

  def findByIdWithQuill(id: Int): Future[Option[UserDto]] = {
    userRepository.findById(id).map(_.map(user => UserDto(user.id, user.name)))
  }

}
