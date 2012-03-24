package org.clincon2001.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public",name = "ccdate")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "ccdate", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Ccdate {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Ccdate().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCcdates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Ccdate o", Long.class).getSingleResult();
    }

	public static List<Ccdate> findAllCcdates() {
        return entityManager().createQuery("SELECT o FROM Ccdate o", Ccdate.class).getResultList();
    }

	public static Ccdate findCcdate(Long id) {
        if (id == null) return null;
        return entityManager().find(Ccdate.class, id);
    }

	public static List<Ccdate> findCcdateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Ccdate o", Ccdate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Ccdate attached = Ccdate.findCcdate(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Ccdate merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Ccdate merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@OneToMany(mappedBy = "ccdate")
    private Set<Clincon> clincons;

	@ManyToOne
    @JoinColumn(name = "cctype", referencedColumnName = "id", nullable = false)
    private Cctype cctype;

	@Column(name = "clincondatetime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date clincondatetime;

	public Set<Clincon> getClincons() {
        return clincons;
    }

	public void setClincons(Set<Clincon> clincons) {
        this.clincons = clincons;
    }

	public Cctype getCctype() {
        return cctype;
    }

	public void setCctype(Cctype cctype) {
        this.cctype = cctype;
    }

	public Date getClincondatetime() {
        return clincondatetime;
    }

	public void setClincondatetime(Date clincondatetime) {
        this.clincondatetime = clincondatetime;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
