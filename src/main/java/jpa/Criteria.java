package jpa;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Criteria {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where( cb.equal(m.get("userName"), "kim") );
            List<Member> resultList = em.createQuery(cq).getResultList();

            // 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
            // JPQL 빌더 역할
            // JPA 공식 기능 
            // 단점 : 너무 복잡하고 실용성이 없다.
            // Criteria 대신 QueryDSL 사용 권장
            
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

// QueryDSL 소개
//  문자가 아닌 자보코드로 JPQL을 작성할 수 있음
//  JPQL 빌더 역할
//  컴파일 시점에 문법 오류를 찾을 수 있음
//  동적쿼리 작성 편리함
//  단순하고 쉬움, 실무 사용 권장