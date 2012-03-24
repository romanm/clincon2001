package org.clincon2001.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public",name = "cctype")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "cctype", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Cctype {

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@OneToMany(mappedBy = "cctype")
    private Set<Ccdate> ccdates;

	@Column(name = "name", length = 100)
    private String name;

	public Set<Ccdate> getCcdates() {
        return ccdates;
    }

	public void setCcdates(Set<Ccdate> ccdates) {
        this.ccdates = ccdates;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Cctype().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCctypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Cctype o", Long.class).getSingleResult();
    }

	public static List<Cctype> findAllCctypes() {
        return entityManager().createQuery("SELECT o FROM Cctype o", Cctype.class).getResultList();
    }

	public static Cctype findCctype(Long id) {
        if (id == null) return null;
        return entityManager().find(Cctype.class, id);
    }

	public static List<Cctype> findCctypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Cctype o", Cctype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Cctype attached = Cctype.findCctype(this.id);
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
    public Cctype merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Cctype merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
