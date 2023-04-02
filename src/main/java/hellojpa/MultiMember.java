package hellojpa;

import javax.persistence.*;

@Entity
public class MultiMember {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "Username")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne  //  member는 많고 team은 하나일때
    @JoinColumn(name = "TEAM_ID")  //조인해야되는 컬럼 값
    private Team team;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}