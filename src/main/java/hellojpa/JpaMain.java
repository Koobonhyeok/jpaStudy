package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // hello는 persistence의 name과 같은 값을 써야한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("helloB");
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);
//            System.out.println(findMember.getName());
//            findMember.setName("helloJPA");

//            List<Member> result =  em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)  // 페이지 처리 할 수 있는 조건
//                    .setMaxResults(8)
//                    .getResultList();
//            for ( Member member : result) {
//                System.out.println(member.getName());
//            }

            Member member1 = new Member( 150L, "test1");
            Member member2 = new Member( 160L, "test2");

            em.persist(member1);
            em.persist(member2);

            // commit 할때에 쿼리가 날라간다.
            tx.commit();
        }catch(Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }

}

// JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
// JPQL은 엔티티 객체를 대상으로 쿼리
// SQL은 데이터베이스 테이블을 대상으로 쿼리