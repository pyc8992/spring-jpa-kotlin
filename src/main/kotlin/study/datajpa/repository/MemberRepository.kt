package study.datajpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import study.datajpa.entity.Member

interface MemberRepository: JpaRepository<Member, Long> {

  fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>
}