package study.datajpa.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.repository.MemberJpaRepository

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberJpaRepositoryTest(
  @Autowired
  val memberJpaRepository: MemberJpaRepository
) {

  @Test
  fun testMember() {
    val member = Member(
      username = "test",
      age = 10
    )

    val saveMember = memberJpaRepository.save(member)

    var findMember: Member? = null
    if (saveMember.id != null) {
      findMember = memberJpaRepository.find(saveMember.id!!)
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
    memberJpaRepository.save(member1)
    memberJpaRepository.save(member2)

    // 변경 감지 (dirty checking)
    member2.username = "test!!"

    val findMember1 = member1.id?.let { memberJpaRepository.findById(it) }
    val findMember2 = member2.id?.let { memberJpaRepository.findById(it) }
    assertThat(findMember1).isEqualTo(member1)
    assertThat(findMember2).isEqualTo(member2)

    // 리스트 조회 검증
    val all = memberJpaRepository.findAll()

    assertThat(all.size).isEqualTo(2)

    // 카운트 검증
    val count = memberJpaRepository.count()
    assertThat(count).isEqualTo(2)

    // 삭제 검증
    memberJpaRepository.delete(member1)
    memberJpaRepository.delete(member2)
    val deletedCount = memberJpaRepository.count()
    assertThat(deletedCount).isEqualTo(0)
  }

  @Test
  fun findByUsernameAndAgeGreaterThen() {
    val m1 = Member(username = "AAA", age = 10)
    val m2 = Member(username = "AAA", age = 20)
    memberJpaRepository.save(m1)
    memberJpaRepository.save(m2)

    val result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

    assertThat(result[0].username).isEqualTo("AAA")
    assertThat(result[0].age).isEqualTo(20)
    assertThat(result.size).isEqualTo(1)
  }

}