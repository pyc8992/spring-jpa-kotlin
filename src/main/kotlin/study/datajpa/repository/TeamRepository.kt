package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Team
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class TeamRepository(
  @PersistenceContext
  val em: EntityManager
) {
  fun save(team: Team): Team {
    em.persist(team)
    return team
  }

  fun delete(team: Team) {
    em.remove(team)
  }

  fun findAll() =
    em.createQuery("select t from Team t").resultList

  fun findById(id: Long): Team? {
    val team = em.find(Team::class.java, id)
    return team ?: null
  }

  fun count(): Long =
    em.createQuery("select count(t) from Team t", Long::class.java).singleResult
}