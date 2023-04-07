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

    public static void MemberDTOTEst(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("username");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // DTO를 사용할 때에는 select 문 안에 new 생성자를 작성을 해야한다.
            List<MemberDTO> result =  em.createQuery("select new MemberDTO( m.username, m.age ) from Member m", MemberDTO.class).getResultList();

            MemberDTO memberDTO = result.get(0);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

    public static void pageTest(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            for( int i = 0; i< 100; i++){
                Member member = new Member();
                member.setUsername("username"+i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> result =  em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for( Member m : result ){
                System.out.println(m.toString());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

    //  JPA 서브 쿼리 한계
    //    JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
    //    SELECT 절도 가능 ( 하이버네이트에서 지원 )
    //    FROM 절의 서브 쿼리는 현재 JPQL에서 불가능 ( 조인으로 풀 수 있으면 풀어서 해결 )
    public static void joinTest(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("team");

            Member member = new Member();
            member.setUsername("username");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member m inner join m.team t";
            List<Member> result =  em.createQuery(query, Member.class)
                            .getResultList();

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

