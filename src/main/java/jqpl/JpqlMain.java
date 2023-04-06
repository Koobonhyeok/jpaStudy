package jqpl;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.List;

public class JpqlMain {
//  JPQL 문법
//    엔티티와 속성은 대소문자 구분을 한다.
//    JPQL 키워드는 대소문자 구분을 하지 않는다.
//    엔티티 이름 사용, 테이블 이름이 아아니다.
//    별칭은 필수 ( as는 생략이 가능 )

    //  TypeQuery : 반환 타입이 명확할 때 사용
//  Query : 반환 타입이 명확하지 않을 때 사용
//  query.getResultList() : 결과가 하나 이상일 때, 리스트 반환
//                          결과가 없으면 빈 리스트 반환
//  query.getSingleResult() : 결과가 정확히 하나, 단일 객체 반환
//                           결과가 없으면 - NoResultException
//                           둘 이상이면 - NonUniqueResultException
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("username");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            TypedQuery<Member> singleQuery = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query1 = em.createQuery("select m.username from Member m", String.class);
            List<Member> list = query.getResultList();
            Member singleMember = singleQuery.getSingleResult();

            for(Member l : list){
                System.out.println(l);
            }

            System.out.println(singleMember);

            // :username는 setParameter의 name의 명칭과 동일 해야함
            TypedQuery<Member> paramQuery = em.createQuery("select m from Member m where username = :usernane", Member.class);
            paramQuery.setParameter("username", "username");
            Member paramResult = query.getSingleResult();

            System.out.println("paramResult =  " +paramResult);


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

