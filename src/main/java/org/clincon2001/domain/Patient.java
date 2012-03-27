package org.clincon2001.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
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
@Table(schema = "public",name = "patient")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "patient", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Patient {

	public Patient() {}
	public Patient(String givenname, String familyname, String sex, Date birthDate) {
		this.givenname=givenname;
		this.familyname=familyname;
		this.sex=sex;
		this.birthDate=birthDate;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@OneToMany(mappedBy = "patient")
    private Set<Clincon> clincons;

	@Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date birthDate;

	@Column(name = "familyname", length = 255)
    @NotNull
    private String familyname;

	@Column(name = "givenname", length = 255)
    @NotNull
    private String givenname;

	@Column(name = "sex", length = 255)
    @NotNull
    private String sex;

	public Set<Clincon> getClincons() {
        return clincons;
    }

	public void setClincons(Set<Clincon> clincons) {
        this.clincons = clincons;
    }

	public Date getBirthDate() {
        return birthDate;
    }

	public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

	public String getFamilyname() {
        return familyname;
    }

	public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

	public String getGivenname() {
        return givenname;
    }

	public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

	public String getSex() {
        return sex;
    }

	public void setSex(String sex) {
        this.sex = sex;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Patient().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPatients() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Patient o", Long.class).getSingleResult();
    }

	public static List<Patient> findAllPatients() {
        return entityManager().createQuery("SELECT o FROM Patient o", Patient.class).getResultList();
    }

	public static Patient findPatient(Long id) {
        if (id == null) return null;
        return entityManager().find(Patient.class, id);
    }

	public static List<Patient> findPatientEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Patient o", Patient.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Patient attached = Patient.findPatient(this.id);
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
    public Patient merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Patient merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	public Set<Patientows1stgeorg> getPatientows1stgeorgs() {
        return patientows1stgeorgs;
    }

	public void setPatientows1stgeorgs(Set<Patientows1stgeorg> patientows1stgeorgs) {
        this.patientows1stgeorgs = patientows1stgeorgs;
    }
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Patientows1stgeorg> patientows1stgeorgs;
}
