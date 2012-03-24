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
import javax.validation.constraints.NotNull;
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
@Table(schema = "public",name = "doctor")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "doctor", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Doctor {

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@OneToMany(mappedBy = "doctor01")
    private Set<Participant> participants;

	@OneToMany(mappedBy = "doctor02")
    private Set<Participant> participants1;

	@OneToMany(mappedBy = "doctor03")
    private Set<Participant> participants2;

	@OneToMany(mappedBy = "doctor04")
    private Set<Participant> participants3;

	@OneToMany(mappedBy = "doctor05")
    private Set<Participant> participants4;

	@OneToMany(mappedBy = "doctor06")
    private Set<Participant> participants5;

	@OneToMany(mappedBy = "doctor07")
    private Set<Participant> participants6;

	@OneToMany(mappedBy = "doctor08")
    private Set<Participant> participants7;

	@OneToMany(mappedBy = "doctor09")
    private Set<Participant> participants8;

	@OneToMany(mappedBy = "doctor10")
    private Set<Participant> participants9;

	@Column(name = "dfamilyname", length = 50)
    @NotNull
    private String dfamilyname;

	@Column(name = "dgivenname", length = 50)
    @NotNull
    private String dgivenname;

	@Column(name = "dtitle", length = 50)
    private String dtitle;

	public Set<Participant> getParticipants() {
        return participants;
    }

	public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

	public Set<Participant> getParticipants1() {
        return participants1;
    }

	public void setParticipants1(Set<Participant> participants1) {
        this.participants1 = participants1;
    }

	public Set<Participant> getParticipants2() {
        return participants2;
    }

	public void setParticipants2(Set<Participant> participants2) {
        this.participants2 = participants2;
    }

	public Set<Participant> getParticipants3() {
        return participants3;
    }

	public void setParticipants3(Set<Participant> participants3) {
        this.participants3 = participants3;
    }

	public Set<Participant> getParticipants4() {
        return participants4;
    }

	public void setParticipants4(Set<Participant> participants4) {
        this.participants4 = participants4;
    }

	public Set<Participant> getParticipants5() {
        return participants5;
    }

	public void setParticipants5(Set<Participant> participants5) {
        this.participants5 = participants5;
    }

	public Set<Participant> getParticipants6() {
        return participants6;
    }

	public void setParticipants6(Set<Participant> participants6) {
        this.participants6 = participants6;
    }

	public Set<Participant> getParticipants7() {
        return participants7;
    }

	public void setParticipants7(Set<Participant> participants7) {
        this.participants7 = participants7;
    }

	public Set<Participant> getParticipants8() {
        return participants8;
    }

	public void setParticipants8(Set<Participant> participants8) {
        this.participants8 = participants8;
    }

	public Set<Participant> getParticipants9() {
        return participants9;
    }

	public void setParticipants9(Set<Participant> participants9) {
        this.participants9 = participants9;
    }

	public String getDfamilyname() {
        return dfamilyname;
    }

	public void setDfamilyname(String dfamilyname) {
        this.dfamilyname = dfamilyname;
    }

	public String getDgivenname() {
        return dgivenname;
    }

	public void setDgivenname(String dgivenname) {
        this.dgivenname = dgivenname;
    }

	public String getDtitle() {
        return dtitle;
    }

	public void setDtitle(String dtitle) {
        this.dtitle = dtitle;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Doctor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDoctors() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Doctor o", Long.class).getSingleResult();
    }

	public static List<Doctor> findAllDoctors() {
        return entityManager().createQuery("SELECT o FROM Doctor o", Doctor.class).getResultList();
    }

	public static Doctor findDoctor(Long id) {
        if (id == null) return null;
        return entityManager().find(Doctor.class, id);
    }

	public static List<Doctor> findDoctorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Doctor o", Doctor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Doctor attached = Doctor.findDoctor(this.id);
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
    public Doctor merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Doctor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
}
