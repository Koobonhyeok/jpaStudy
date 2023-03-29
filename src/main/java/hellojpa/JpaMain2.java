package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member(200L, "member200");
//            em.persist(member);
//
//            em.flush();

            // 준 영속 상태
            Member member = em.find(Member.class, 150L);
            member.setName("TEST");
            // 이렇게 하면 member에 대한 update를 진행 하지 않는다.
            em.detach(member);
            
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

}
//  플러시
//- 영속성 컨텍스트를 비우지 않음
//- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
//- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨

//   준영속 상태
//  detach : 특정 엔티티만 준영속 상태로 전환
//  clear  : 영속성 컨텍스트를 완전히 초기화
//  close  : 영속성 컨텍스트 종료
