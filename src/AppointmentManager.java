import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Holds every appointment and answers the questions the clinic asks.
public class AppointmentManager {

    private final List<Appointment> appointments = new ArrayList<>();

    // Returns a message: either confirmed, or a warning saying what went wrong.
    public String addAppointment(Patient patient, HealthProfessional professional, LocalTime time) {
        if (patient == null || professional == null || time == null) {
            return "Warning: a patient, a health professional and a time are all required "
                    + "to book an appointment.";
        }
        if (!ClinicSchedule.isValidTimeSlot(time)) {
            return "Warning: " + ClinicSchedule.format(time) + " is not a clinic time slot ("
                    + ClinicSchedule.describe() + ").";
        }

        // Bookings are for today only, so the professional has to work today.
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        if (!professional.worksOn(today)) {
            return "Warning: " + professional.getName() + " (ID " + professional.getId()
                    + ") does not work on " + formatDay(today) + ".";
        }

        Appointment candidate = new Appointment(patient, professional, time);
        // The same professional must not be booked twice in the same slot.
        for (Appointment existing : appointments) {
            if (existing.clashesWith(candidate)) {
                return "Warning: " + professional.getName() + " (ID " + professional.getId()
                        + ") is already booked at " + ClinicSchedule.format(time) + " for "
                        + existing.getPatient().getName() + ".";
            }
        }

        appointments.add(candidate);
        return "Booking confirmed: " + ClinicSchedule.format(time) + " with "
                + professional.getName() + " (ID " + professional.getId() + ") for "
                + patient.getName() + ".";
    }

    public List<Appointment> getAllAppointments() {
        return Collections.unmodifiableList(new ArrayList<>(appointments));
    }

    public List<Appointment> getAllAppointmentsSortedByTime() {
        return sortByTime(appointments);
    }

    // Sort a copy so the original order is not changed.
    public List<Appointment> sortByTime(List<Appointment> source) {
        List<Appointment> sorted = new ArrayList<>(source);
        Collections.sort(sorted);
        return sorted;
    }

    // Filter by professional id.
    public List<Appointment> findByProfessionalId(String professionalId) {
        List<Appointment> matches = new ArrayList<>();
        if (professionalId == null) {
            return matches;
        }
        String wantedId = professionalId.trim();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equalsIgnoreCase(wantedId)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    // Filter by the patient's mobile number.
    public List<Appointment> findByPatientMobileNumber(String mobileNumber) {
        List<Appointment> matches = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().matchesMobileNumber(mobileNumber)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    // Find it by professional id and time; warn if it is not there.
    public String cancelAppointment(String professionalId, LocalTime time) {
        if (professionalId == null || time == null) {
            return "Warning: a professional ID and an appointment time are required to cancel.";
        }
        String wantedId = professionalId.trim();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getId().equalsIgnoreCase(wantedId)
                    && appointment.getTime().equals(time)) {
                appointments.remove(i);
                return "Appointment cancelled: " + ClinicSchedule.format(time) + " with "
                        + appointment.getProfessional().getName() + " (ID "
                        + appointment.getProfessional().getId() + ") for "
                        + appointment.getPatient().getName() + ".";
            }
        }
        return "Warning: no appointment found for professional ID " + professionalId + " at "
                + ClinicSchedule.format(time) + ".";
    }

    // A patient may have more than one booking, so remove all of them.
    public String cancelAppointmentsForPatient(String mobileNumber) {
        List<Appointment> matches = findByPatientMobileNumber(mobileNumber);
        if (matches.isEmpty()) {
            return "Warning: no appointments found for mobile number " + mobileNumber + ".";
        }
        appointments.removeAll(matches);
        StringBuilder message = new StringBuilder("Appointments cancelled for mobile number ")
                .append(mobileNumber).append(": ").append(matches.size()).append(" appointment(s) removed.");
        for (Appointment appointment : matches) {
            message.append(System.lineSeparator())
                    .append("           - ").append(ClinicSchedule.format(appointment.getTime()))
                    .append(" with ").append(appointment.getProfessional().getName())
                    .append(" (ID ").append(appointment.getProfessional().getId()).append(")");
        }
        return message.toString();
    }

    public void printAppointments(String heading, List<Appointment> appointmentsToShow) {
        System.out.println(heading);
        if (appointmentsToShow.isEmpty()) {
            System.out.println("   (no appointments to show)");
            return;
        }
        int position = 1;
        for (Appointment appointment : appointmentsToShow) {
            System.out.printf("   %2d. %s%n", position, appointment);
            position++;
        }
    }

    public int size() {
        return appointments.size();
    }

    private static String formatDay(DayOfWeek day) {
        String name = day.toString();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
