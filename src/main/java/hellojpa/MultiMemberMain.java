package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class MultiMemberMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            MultiMember mMember = new MultiMember();
            mMember.setName("member1");
            mMember.setTeam(team);
            em.persist(mMember);

            MultiMember findMember = em.find(MultiMember.class, mMember.getId() );
            List<MultiMember> members = findMember.getTeam().getMembers();

            for( MultiMember m : members ){
                System.out.println("m  = " + m.getName());
            }


            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}
