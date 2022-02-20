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


  @Test
  fun basicCRUD() {
    val member1 = Member(username = "member1", age = 10)
    val member2 = Member(username = "member2", age = 10)
    memberRepository.save(member1)
    memberRepository.save(member2)

    // 변경 감지 (dirty checking)
    member2.username = "test!!"

    val findMember1 = member1.id?.let { memberRepository.findByIdOrNull(it) }
    val findMember2 = member2.id?.let { memberRepository.findByIdOrNull(it) }
    assertThat(findMember1).isEqualTo(member1)
    assertThat(findMember2).isEqualTo(member2)

    // 리스트 조회 검증
    val all = memberRepository.findAll()

    assertThat(all.size).isEqualTo(2)

    // 카운트 검증
    val count = memberRepository.count()
    assertThat(count).isEqualTo(2)

    // 삭제 검증
    memberRepository.delete(member1)
    memberRepository.delete(member2)
    val deletedCount = memberRepository.count()
    assertThat(deletedCount).isEqualTo(0)
  }

  @Test
  fun findByUsernameAndAgeGreaterThen() {
    val m1 = Member(username = "AAA", age = 10)
    val m2 = Member(username = "AAA", age = 20)
    memberRepository.save(m1)
    memberRepository.save(m2)

    val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

    assertThat(result[0].username).isEqualTo("AAA")
    assertThat(result[0].age).isEqualTo(20)
    assertThat(result.size).isEqualTo(1)
  }
}