package study.datajpa.entity

import javax.persistence.*

@Entity
data class Team(
  @Id @GeneratedValue
  @Column(name = "team_id")
  val id: Long? = null,
  var name: String,

  @OneToMany(mappedBy = "team")
  val members: MutableList<Member> = mutableListOf()
)