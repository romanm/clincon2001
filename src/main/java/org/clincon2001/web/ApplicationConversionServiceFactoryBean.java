package org.clincon2001.web;

import org.clincon2001.domain.Ccdate;
import org.clincon2001.domain.Ccdiagnosis;
import org.clincon2001.domain.Cctype;
import org.clincon2001.domain.Clincon;
import org.clincon2001.domain.Diagnosis;
import org.clincon2001.domain.Doctor;
import org.clincon2001.domain.Participant;
import org.clincon2001.domain.Patient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Ccdate, String> getCcdateToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Ccdate, java.lang.String>() {
            public String convert(Ccdate ccdate) {
                return new StringBuilder().append(ccdate.getClincondatetime()).toString();
            }
        };
    }

	public Converter<Long, Ccdate> getIdToCcdateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Ccdate>() {
            public org.clincon2001.domain.Ccdate convert(java.lang.Long id) {
                return Ccdate.findCcdate(id);
            }
        };
    }

	public Converter<String, Ccdate> getStringToCcdateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Ccdate>() {
            public org.clincon2001.domain.Ccdate convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Ccdate.class);
            }
        };
    }

	public Converter<Ccdiagnosis, String> getCcdiagnosisToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Ccdiagnosis, java.lang.String>() {
            public String convert(Ccdiagnosis ccdiagnosis) {
                return new StringBuilder().toString();
            }
        };
    }

	public Converter<Long, Ccdiagnosis> getIdToCcdiagnosisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Ccdiagnosis>() {
            public org.clincon2001.domain.Ccdiagnosis convert(java.lang.Long id) {
                return Ccdiagnosis.findCcdiagnosis(id);
            }
        };
    }

	public Converter<String, Ccdiagnosis> getStringToCcdiagnosisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Ccdiagnosis>() {
            public org.clincon2001.domain.Ccdiagnosis convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Ccdiagnosis.class);
            }
        };
    }

	public Converter<Cctype, String> getCctypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Cctype, java.lang.String>() {
            public String convert(Cctype cctype) {
                return new StringBuilder().append(cctype.getName()).toString();
            }
        };
    }

	public Converter<Long, Cctype> getIdToCctypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Cctype>() {
            public org.clincon2001.domain.Cctype convert(java.lang.Long id) {
                return Cctype.findCctype(id);
            }
        };
    }

	public Converter<String, Cctype> getStringToCctypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Cctype>() {
            public org.clincon2001.domain.Cctype convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Cctype.class);
            }
        };
    }

	public Converter<Clincon, String> getClinconToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Clincon, java.lang.String>() {
            public String convert(Clincon clincon) {
                return new StringBuilder().append(clincon.getAnamnesis()).append(" ").append(clincon.getSuggestion()).toString();
            }
        };
    }

	public Converter<Long, Clincon> getIdToClinconConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Clincon>() {
            public org.clincon2001.domain.Clincon convert(java.lang.Long id) {
                return Clincon.findClincon(id);
            }
        };
    }

	public Converter<String, Clincon> getStringToClinconConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Clincon>() {
            public org.clincon2001.domain.Clincon convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Clincon.class);
            }
        };
    }

	public Converter<Diagnosis, String> getDiagnosisToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Diagnosis, java.lang.String>() {
            public String convert(Diagnosis diagnosis) {
                return new StringBuilder().append(diagnosis.getDiagnosisName()).toString();
            }
        };
    }

	public Converter<Long, Diagnosis> getIdToDiagnosisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Diagnosis>() {
            public org.clincon2001.domain.Diagnosis convert(java.lang.Long id) {
                return Diagnosis.findDiagnosis(id);
            }
        };
    }

	public Converter<String, Diagnosis> getStringToDiagnosisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Diagnosis>() {
            public org.clincon2001.domain.Diagnosis convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Diagnosis.class);
            }
        };
    }

	public Converter<Doctor, String> getDoctorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Doctor, java.lang.String>() {
            public String convert(Doctor doctor) {
                return new StringBuilder().append(doctor.getDfamilyname()).append(" ").append(doctor.getDgivenname()).append(" ").append(doctor.getDtitle()).toString();
            }
        };
    }

	public Converter<Long, Doctor> getIdToDoctorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Doctor>() {
            public org.clincon2001.domain.Doctor convert(java.lang.Long id) {
                return Doctor.findDoctor(id);
            }
        };
    }

	public Converter<String, Doctor> getStringToDoctorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Doctor>() {
            public org.clincon2001.domain.Doctor convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Doctor.class);
            }
        };
    }

	public Converter<Participant, String> getParticipantToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Participant, java.lang.String>() {
            public String convert(Participant participant) {
                return new StringBuilder().toString();
            }
        };
    }

	public Converter<Long, Participant> getIdToParticipantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Participant>() {
            public org.clincon2001.domain.Participant convert(java.lang.Long id) {
                return Participant.findParticipant(id);
            }
        };
    }

	public Converter<String, Participant> getStringToParticipantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Participant>() {
            public org.clincon2001.domain.Participant convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Participant.class);
            }
        };
    }

	public Converter<Patient, String> getPatientToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.clincon2001.domain.Patient, java.lang.String>() {
            public String convert(Patient patient) {
                return new StringBuilder().append(patient.getBirthDate()).append(" ").append(patient.getFamilyname()).append(" ").append(patient.getGivenname()).append(" ").append(patient.getSex()).toString();
            }
        };
    }

	public Converter<Long, Patient> getIdToPatientConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.clincon2001.domain.Patient>() {
            public org.clincon2001.domain.Patient convert(java.lang.Long id) {
                return Patient.findPatient(id);
            }
        };
    }

	public Converter<String, Patient> getStringToPatientConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.clincon2001.domain.Patient>() {
            public org.clincon2001.domain.Patient convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Patient.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getCcdateToStringConverter());
        registry.addConverter(getIdToCcdateConverter());
        registry.addConverter(getStringToCcdateConverter());
        registry.addConverter(getCcdiagnosisToStringConverter());
        registry.addConverter(getIdToCcdiagnosisConverter());
        registry.addConverter(getStringToCcdiagnosisConverter());
        registry.addConverter(getCctypeToStringConverter());
        registry.addConverter(getIdToCctypeConverter());
        registry.addConverter(getStringToCctypeConverter());
        registry.addConverter(getClinconToStringConverter());
        registry.addConverter(getIdToClinconConverter());
        registry.addConverter(getStringToClinconConverter());
        registry.addConverter(getDiagnosisToStringConverter());
        registry.addConverter(getIdToDiagnosisConverter());
        registry.addConverter(getStringToDiagnosisConverter());
        registry.addConverter(getDoctorToStringConverter());
        registry.addConverter(getIdToDoctorConverter());
        registry.addConverter(getStringToDoctorConverter());
        registry.addConverter(getParticipantToStringConverter());
        registry.addConverter(getIdToParticipantConverter());
        registry.addConverter(getStringToParticipantConverter());
        registry.addConverter(getPatientToStringConverter());
        registry.addConverter(getIdToPatientConverter());
        registry.addConverter(getStringToPatientConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
