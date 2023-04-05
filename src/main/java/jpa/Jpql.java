package jpa;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Jpql {
    // JPQL은 객체지향 쿼리 언어이다. 따라서 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
    // JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
    // JPQL은 결국 SQL로 변환된다.
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
                                                                    // Member는 객체이다.
            List<Member> resuList = em.createQuery("select m from Member m where m.username like '%kim%'", Member.class).getResultList();
            // 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
            // SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
            // JPQL을 한마디로 정의하면 객체 지향 SQL

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }
}
