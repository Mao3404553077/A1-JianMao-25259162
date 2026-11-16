import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// All the hours live here, so changing them changes the whole system.
public final class ClinicSchedule {

    public static final LocalTime OPENING_TIME = LocalTime.of(8, 0);
    public static final LocalTime CLOSING_TIME = LocalTime.of(16, 0);
    public static final int SLOT_MINUTES = 30;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private static final List<LocalTime> TIME_SLOTS = buildTimeSlots();

    private ClinicSchedule() {
    }

    public static List<LocalTime> getTimeSlots() {
        return TIME_SLOTS;
    }

    private static List<LocalTime> buildTimeSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime time = OPENING_TIME;
        while (time.isBefore(CLOSING_TIME)) {
            slots.add(time);
            time = time.plusMinutes(SLOT_MINUTES);
        }
        return Collections.unmodifiableList(slots);
    }

    // A slot starts on the hour or half hour and must be before closing time.
    public static boolean isValidTimeSlot(LocalTime time) {
        if (time == null) {
            return false;
        }
        if (time.isBefore(OPENING_TIME) || !time.isBefore(CLOSING_TIME)) {
            return false;
        }
        if (time.getSecond() != 0 || time.getNano() != 0) {
            return false;
        }
        int minutesFromOpening = time.toSecondOfDay() / 60 - OPENING_TIME.toSecondOfDay() / 60;
        return minutesFromOpening % SLOT_MINUTES == 0;
    }

    public static String format(LocalTime time) {
        return time.format(TIME_FORMAT);
    }

    public static String describe() {
        return "between " + format(OPENING_TIME) + " and " + format(CLOSING_TIME)
                + " in " + SLOT_MINUTES + "-minute slots";
    }
}
