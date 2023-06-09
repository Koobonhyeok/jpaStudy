package locker;

import javax.persistence.*;

@Entity
public class LockerMember {

    @Id @GeneratedValue
    private Long id;

    private  String username;

    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private  Locker locker;

    @ManyToMany
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}
