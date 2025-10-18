//package flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Set;


public class FlightSearch {

    private String  departureDate;
    private String  departureAirportCode;
    private boolean emergencyRowSeating;
    private String  returnDate;
    private String  destinationAirportCode;
    private String  seatingClass;
    private int     adultPassengerCount;
    private int     childPassengerCount;
    private int     infantPassengerCount;


    private LocalDate parseStrictDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date: " + dateStr
                    + ". Ensure format is DD/MM/YYYY and the date is valid.");
        }
    }


    public boolean runFlightSearch(String departureDate, String departureAirportCode, boolean emergencyRowSeating,
                                   String returnDate, String destinationAirportCode, String seatingClass,
                                   int adultPassengerCount, int childPassengerCount, int infantPassengerCount) {

        this.departureAirportCode = departureAirportCode;
        this.departureDate = departureDate;
        this.emergencyRowSeating = emergencyRowSeating;
        this.returnDate = returnDate;
        this.destinationAirportCode = destinationAirportCode;
        this.seatingClass = seatingClass;


        Set<String> allowedAirports = Set.of("syd", "mel", "lax", "cdg", "del", "pvg", "doh");
        boolean valid = true;


        // 1. Passenger counts must be non-negative
        if (adultPassengerCount < 0 || childPassengerCount < 0 || infantPassengerCount < 0) {
            throw new IllegalArgumentException("Passenger counts cannot be negative.");
        }

        // Total passenger count must be 1-9
        int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;
        if (totalPassengers < 1 || totalPassengers > 9) {
            throw new IllegalArgumentException("Passenger count must be in range 1-9.");
        }

        // 2. Children seating restrictions
        if (childPassengerCount > 0) {
            if (emergencyRowSeating)
                throw new IllegalArgumentException("Children cannot be seated in emergency row seating.");
            if ("First".equalsIgnoreCase(seatingClass))
                throw new IllegalArgumentException("Children are not allowed in First Class.");
            if (childPassengerCount > 2 * adultPassengerCount)
                throw new IllegalArgumentException("Each adult can accompany up to 2 children only.");
        }

        // 3. Infants seating restrictions
        if (infantPassengerCount > 0) {
            if (emergencyRowSeating)
                throw new IllegalArgumentException("Infants cannot be seated in emergency row seating.");
            if ("Business".equalsIgnoreCase(seatingClass))
                throw new IllegalArgumentException("Infants are not allowed in Business Class.");
            if (adultPassengerCount == 0)
                throw new IllegalArgumentException("Infants must be accompanied by at least one adult.");
            if (infantPassengerCount > adultPassengerCount)
                throw new IllegalArgumentException("Each infant must be seated on an adult's lap. Only one infant per adult.");
        }


        // Departure date validation
        LocalDate depDate = parseStrictDate(departureDate);
        LocalDate today = LocalDate.now();
        if (depDate.isBefore(today)) throw new IllegalArgumentException("Departure date cannot be in the past.");

        // Return date must exist and not be before departure
        if (returnDate == null || returnDate.isEmpty()) {
            throw new IllegalArgumentException("All flights are round-trip. Return date is required.");
        }
        LocalDate retDate = parseStrictDate(returnDate);
        if (retDate.isBefore(depDate))
            throw new IllegalArgumentException("Return date cannot be before departure date.");

        // Seating class validation
        String seatClassLower = seatingClass.toLowerCase();
        if (!seatClassLower.equals("economy") && !seatClassLower.equals("premium economy")
                && !seatClassLower.equals("business") && !seatClassLower.equals("first")) {
            throw new IllegalArgumentException("Seating class must be one of: economy, premium economy, business, first.");
        }

        // Emergency row seating allowed only in economy
        if (emergencyRowSeating && !seatClassLower.equals("economy")) {
            throw new IllegalArgumentException("Emergency row seating is only allowed in Economy class.");
        }

        String depCodeLower = departureAirportCode.toLowerCase();
        String destCodeLower = destinationAirportCode.toLowerCase();

        if (!allowedAirports.contains(depCodeLower)) {
            throw new IllegalArgumentException("Departure airport '" + departureAirportCode + "' is not allowed.");
        }
        if (!allowedAirports.contains(destCodeLower)) {
            throw new IllegalArgumentException("Destination airport '" + destinationAirportCode + "' is not allowed.");
        }
        if (depCodeLower.equals(destCodeLower)) {
            throw new IllegalArgumentException("Departure and destination airports cannot be the same.");
        }

        System.out.println("Booking validated successfully ✅");
        return valid;
    }

}
