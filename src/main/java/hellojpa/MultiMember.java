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
        team.getMembers().add(this);
    }
}

// 양방향 매핑 절리
//  - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
//  - 양방향 매핑은 반대 방향으로 조회 기능이 추가된 것 뿐
//  - JPQL에서 역방향으로 탐색할 일이 많음
//  - 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨