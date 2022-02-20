package study.datajpa.entity

import javax.persistence.*

@Entity
data class Member (
  @Id @GeneratedValue
  @Column(name = "member_id")
  val id: Long? = null,
  var username: String,
  var age: Int,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  var team: Team? = null
) {
  fun changeTeam(team: Team) {
    this.team = team
    team.members.add(this)
  }
}