// Patient details for a booking: a name and an Australian mobile number.
public class Patient {

    private final String name;
    private final String mobileNumber;

    public Patient(String name, String mobileNumber) {
        this.name = validateName(name);
        this.mobileNumber = validateMobileNumber(mobileNumber);
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getFormattedMobileNumber() {
        return mobileNumber.substring(0, 4) + " " + mobileNumber.substring(4, 7) + " "
                + mobileNumber.substring(7);
    }

    // Used by the phone filter; spaces, dashes and +61 are ignored.
    public boolean matchesMobileNumber(String candidate) {
        if (candidate == null) {
            return false;
        }
        String digits = candidate.replaceAll("[^0-9]", "");
        if (digits.startsWith("61") && digits.length() == 11) {
            digits = "0" + digits.substring(2);
        }
        return mobileNumber.equals(digits);
    }

    @Override
    public String toString() {
        return name + " (" + getFormattedMobileNumber() + ")";
    }

    private static String validateName(String name) {
        String trimmed = (name == null) ? "" : name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("A patient must have a name.");
        }
        return trimmed;
    }

    private static String validateMobileNumber(String mobileNumber) {
        String digits = (mobileNumber == null) ? "" : mobileNumber.replaceAll("[^0-9]", "");
        if (digits.startsWith("61") && digits.length() == 11) {
            digits = "0" + digits.substring(2);
        }
        if (!digits.matches("04\\d{8}")) {
            throw new IllegalArgumentException("A patient mobile number must be a 10-digit "
                    + "Australian mobile number starting with 04, for example 0412 345 678, but was: "
                    + mobileNumber);
        }
        return digits;
    }
}
