import java.time.DayOfWeek;
import java.util.Set;

// Fourth type, mainly to show that one more type costs one small class.
public class Dietitian extends HealthProfessional {

    private final String dietaryFocus;

    public Dietitian(String id, String name, Set<DayOfWeek> workingDays, String dietaryFocus) {
        super(id, name, workingDays);
        this.dietaryFocus = validateFocus(dietaryFocus);
    }

    public Dietitian(String id, String name, String dietaryFocus) {
        super(id, name);
        this.dietaryFocus = validateFocus(dietaryFocus);
    }

    public String getDietaryFocus() {
        return dietaryFocus;
    }

    @Override
    public String getRole() {
        return "Dietitian";
    }

    @Override
    public String performDuty() {
        return "assesses nutritional needs and prepares dietary plans for " + dietaryFocus;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator()
                + "           Dietary focus: " + dietaryFocus;
    }

    private static String validateFocus(String focus) {
        String trimmed = (focus == null) ? "" : focus.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("A dietitian must have a dietary focus.");
        }
        return trimmed;
    }
}
