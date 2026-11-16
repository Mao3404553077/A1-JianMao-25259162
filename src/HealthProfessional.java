import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// Common base for every health professional: id, name and the days they work.
public abstract class HealthProfessional {

    private static final EnumSet<DayOfWeek> DEFAULT_WORKING_DAYS =
            EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

    private static final String[] SHORT_DAY_NAMES =
            {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    // id must be digits only, name cannot be blank, roster is copied so nobody outside can change it.
    private final String id;
    private final String name;
    private final EnumSet<DayOfWeek> workingDays;

    protected HealthProfessional(String id, String name, Set<DayOfWeek> workingDays) {
        this.id = validateId(id);
        this.name = validateName(name);
        this.workingDays = copyWorkingDays(workingDays);
    }

    // Two ways to build one: pass a roster, or use Monday to Friday by default.
    protected HealthProfessional(String id, String name) {
        this(id, name, DEFAULT_WORKING_DAYS);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Hand out a copy, otherwise callers could clear the roster of a professional.
    public Set<DayOfWeek> getWorkingDays() {
        return Collections.unmodifiableSet(EnumSet.copyOf(workingDays));
    }

    public boolean worksOn(DayOfWeek day) {
        return day != null && workingDays.contains(day);
    }

    public abstract String getRole();

    public abstract String performDuty();

    public String getFormattedWorkingDays() {
        StringBuilder text = new StringBuilder();
        DayOfWeek[] days = DayOfWeek.values();
        for (int i = 0; i < days.length; i++) {
            if (workingDays.contains(days[i])) {
                if (text.length() > 0) {
                    text.append(", ");
                }
                text.append(SHORT_DAY_NAMES[i]);
            }
        }
        return text.toString();
    }

    @Override
    // Same type and same id means the same person.
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        HealthProfessional that = (HealthProfessional) other;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }

    @Override
    // Subclasses call this and add their own fields on top.
    public String toString() {
        return String.format("ID: %-4s | Name: %-18s | Role: %-28s | Works: %s",
                id, name, getRole(), getFormattedWorkingDays());
    }

    private static String validateId(String id) {
        String trimmed = (id == null) ? "" : id.trim();
        if (!trimmed.matches("\\d+")) {
            throw new IllegalArgumentException(
                    "A health professional ID must contain numbers only, but was: " + id);
        }
        return trimmed;
    }

    private static String validateName(String name) {
        String trimmed = (name == null) ? "" : name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("A health professional must have a name.");
        }
        return trimmed;
    }

    private static EnumSet<DayOfWeek> copyWorkingDays(Set<DayOfWeek> workingDays) {
        if (workingDays == null || workingDays.isEmpty()) {
            throw new IllegalArgumentException(
                    "A health professional must work on at least one day of the week.");
        }
        EnumSet<DayOfWeek> copy = EnumSet.noneOf(DayOfWeek.class);
        for (DayOfWeek day : workingDays) {
            if (day == null) {
                throw new IllegalArgumentException("Working days cannot contain null.");
            }
            copy.add(day);
        }
        return copy;
    }
}
