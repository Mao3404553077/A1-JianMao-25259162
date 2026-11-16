import java.time.DayOfWeek;
import java.util.Set;

// Doctors share a registration number and the ability to prescribe.
public abstract class Doctor extends HealthProfessional {

    private final String practitionerNumber;

    protected Doctor(String id, String name, Set<DayOfWeek> workingDays, String practitionerNumber) {
        super(id, name, workingDays);
        this.practitionerNumber = validatePractitionerNumber(practitionerNumber);
    }

    protected Doctor(String id, String name, String practitionerNumber) {
        super(id, name);
        this.practitionerNumber = validatePractitionerNumber(practitionerNumber);
    }

    public String getPractitionerNumber() {
        return practitionerNumber;
    }

    // Kept here so GP and Specialist do not each write it.
    public String prescribe(String medicationName) {
        String medication = (medicationName == null) ? "" : medicationName.trim();
        if (medication.isEmpty()) {
            throw new IllegalArgumentException("A medication name is required to write a prescription.");
        }
        return getName() + " writes a prescription for " + medication
                + " (registration " + practitionerNumber + ").";
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator()
                + "           Registration: " + practitionerNumber;
    }

    private static String validatePractitionerNumber(String practitionerNumber) {
        String trimmed = (practitionerNumber == null) ? "" : practitionerNumber.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("A doctor must have a practitioner registration number.");
        }
        return trimmed;
    }
}
