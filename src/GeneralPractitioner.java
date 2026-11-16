import java.time.DayOfWeek;
import java.util.Set;

// A GP: a doctor who may or may not be taking new patients.
public class GeneralPractitioner extends Doctor {

    private final boolean acceptingNewPatients;

    public GeneralPractitioner(String id, String name, Set<DayOfWeek> workingDays,
                               String practitionerNumber, boolean acceptingNewPatients) {
        super(id, name, workingDays, practitionerNumber);
        this.acceptingNewPatients = acceptingNewPatients;
    }

    public GeneralPractitioner(String id, String name, String practitionerNumber) {
        super(id, name, practitionerNumber);
        this.acceptingNewPatients = true;
    }

    public boolean isAcceptingNewPatients() {
        return acceptingNewPatients;
    }

    @Override
    public String getRole() {
        return "General Practitioner";
    }

    @Override
    public String performDuty() {
        return "conducts general consultations, diagnoses common conditions and prescribes medication";
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator()
                + "           Accepting new patients: " + (acceptingNewPatients ? "yes" : "no");
    }
}
