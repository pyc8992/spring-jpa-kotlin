package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberJpaRepository(
  @PersistenceContext
  val em: EntityManager
) {
  fun save(member: Member): Member {
    em.persist(member)
    return member
  }

  fun delete(member: Member) {
    em.remove(member)
  }

  fun findAll() =
    em.createQuery("select m from Member m", Member::class.java).resultList

  fun findById(id: Long): Member? {
    val member = em.find(Member::class.java, id)
    return member ?: null
  }

  fun count(): Long = em.createQuery("select count(m) from Member m", Long::class.javaObjectType).singleResult

  fun find(id: Long): Member = em.find(Member::class.java, id)

  fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member> {
    return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member::class.java)
      .setParameter("username", username)
      .setParameter("age", age)
      .resultList
  }
}