import java.time.LocalTime;
import java.util.Objects;

// One booking: a patient, a professional and a time slot.
public class Appointment implements Comparable<Appointment> {

    private final Patient patient;
    private final HealthProfessional professional;
    private final LocalTime time;

    // Nothing may be null and the time has to be a real clinic slot.
    public Appointment(Patient patient, HealthProfessional professional, LocalTime time) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment needs a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("An appointment needs a health professional.");
        }
        if (time == null) {
            throw new IllegalArgumentException("An appointment needs a time.");
        }
        if (!ClinicSchedule.isValidTimeSlot(time)) {
            throw new IllegalArgumentException("The time " + ClinicSchedule.format(time)
                    + " is not a clinic slot (" + ClinicSchedule.describe() + ").");
        }
        this.patient = patient;
        this.professional = professional;
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthProfessional getProfessional() {
        return professional;
    }

    public LocalTime getTime() {
        return time;
    }

    // Same professional at the same time is a clash.
    public boolean clashesWith(Appointment other) {
        return other != null && time.equals(other.time)
                && professional.equals(other.professional);
    }

    @Override
    // Earliest first; the id breaks a tie so the order stays stable.
    public int compareTo(Appointment other) {
        int byTime = time.compareTo(other.time);
        if (byTime != 0) {
            return byTime;
        }
        return professional.getId().compareTo(other.professional.getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Appointment that = (Appointment) other;
        return time.equals(that.time) && professional.equals(that.professional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professional, time);
    }

    @Override
    public String toString() {
        return String.format("%s | %-26s | %s %s (ID %s)",
                ClinicSchedule.format(time),
                patient.toString(),
                professional.getRole(),
                professional.getName(),
                professional.getId());
    }
}
