package study.datajpa.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.repository.MemberRepository

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberRepositoryTest(
  @Autowired
  val memberRepository: MemberRepository
) {

  @Test
  fun testMember() {
    val member = Member(
      username = "test",
      age = 10
    )

    val saveMember = memberRepository.save(member)

    var findMember: Member? = null
    if (saveMember.id != null) {
      findMember = memberRepository.findByIdOrNull(saveMember.id!!)
    }

    if (findMember != null) {
      assertThat(findMember.id).isEqualTo(member.id)
      assertThat(findMember.username).isEqualTo(member.username)
      assertThat(findMember).isEqualTo(findMember)
    }
  }

}