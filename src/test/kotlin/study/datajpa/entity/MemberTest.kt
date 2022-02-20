package study.datajpa.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberTest @Autowired
//No ParameterResolver registered for parameter
constructor(
  @PersistenceContext
  val em: EntityManager
) {

  @Test
  fun testEntity() {
    val teamA = Team(name = "teamA")
    val teamB = Team(name = "teamB")

    em.persist(teamA)
    em.persist(teamB)

    val member1 = Member(username = "memeber1", age = 10, team = teamA)
    val member2 = Member(username = "memeber2", age = 10, team = teamA)
    val member3 = Member(username = "memeber3", age = 10, team = teamB)
    val member4 = Member(username = "memeber4", age = 10, team = teamB)
    em.persist(member1)
    em.persist(member2)
    em.persist(member3)
    em.persist(member4)

    //초기화
    em.flush()
    em.clear()

    //확인
    val members = em.createQuery("select m from Member m", Member::class.java).resultList

    println("#############")
    for (member in members) {
      println("member = ${member.username}")
      println("-> member.team = ${member.team?.name}")
    }

  }

}