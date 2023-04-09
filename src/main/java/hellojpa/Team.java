package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    public List<MultiMember> getMembers() {
        return members;
    }

//    public void setMembers(List<MultiMember> members) {
//        this.members = members;
//    }

    @OneToMany(mappedBy = "team")
    private List<MultiMember> members = new ArrayList<>();
    // mappedBy : 객체와 테이블이 관계를 맺는 차이
    
//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// 양방향 매핑 규칙
//  - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
//  - 연관관계의 주인만이 외래 키를 관리
//  - 주인이 아닌쪽은 읽기만 가능
//  - 주인은 mappedBy 속성 사용 x
//  - 주인이 아니면 mappedBy 속성으로 주인 지정
//  ※ 주인은 외래 키가 있는 곳을 주인으로 지정, 1:N 이라면 N쪽이 주인으로 지정