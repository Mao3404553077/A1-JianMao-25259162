import java.time.DayOfWeek;
import java.util.Set;

// A specialist such as a cardiologist or paediatrician; the specialty is just a field.
public class Specialist extends Doctor {

    private final String specialty;

    public Specialist(String id, String name, Set<DayOfWeek> workingDays,
                      String practitionerNumber, String specialty) {
        super(id, name, workingDays, practitionerNumber);
        this.specialty = validateSpecialty(specialty);
    }

    public Specialist(String id, String name, String practitionerNumber, String specialty) {
        super(id, name, practitionerNumber);
        this.specialty = validateSpecialty(specialty);
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String getRole() {
        return "Specialist (" + specialty + ")";
    }

    @Override
    public String performDuty() {
        return "provides specialist assessment and treatment in " + specialty;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator()
                + "           Specialty: " + specialty;
    }

    private static String validateSpecialty(String specialty) {
        String trimmed = (specialty == null) ? "" : specialty.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("A specialist must have a specialty.");
        }
        return trimmed;
    }
}
