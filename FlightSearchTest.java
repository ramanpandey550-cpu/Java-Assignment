import org.junit.Test;

import static org.junit.Assert.*;

public class FlightSearchTest extends FlightSearch {

    // =================== Case 1: Too many passengers (>9) ===================
    @Test
    public void tooManyPassengers1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "Economy", 5, 3, 2)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Passenger count must be in range 1-9.", ex.getMessage());
    }

    @Test
    public void tooManyPassengers2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("25/10/2025", "LAX", false,
                        "05/11/2025", "CDG", "Economy", 4, 4, 2)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Passenger count must be in range 1-9.", ex.getMessage());
    }

    // =================== Case 2: Child in emergency row ===================
    @Test
    public void childInEmergencyRow1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", true,
                        "28/10/2025", "DOH", "Economy", 1, 1, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Children cannot be seated in emergency row seating.", ex.getMessage());
    }

    @Test
    public void childInEmergencyRow2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("22/11/2025", "SYD", true,
                        "30/11/2025", "MEL", "Economy", 2, 1, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Children cannot be seated in emergency row seating.", ex.getMessage());
    }

    // =================== Case 3: Child in First Class ===================
    @Test
    public void childInFirstClass1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "First", 1, 1, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Children are not allowed in First Class.", ex.getMessage());
    }

    @Test
    public void childInFirstClass2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("15/12/2025", "CDG", false,
                        "25/12/2025", "LAX", "First", 2, 2, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Children are not allowed in First Class.", ex.getMessage());
    }

    // =================== Case 4: Too many children per adult ===================
    @Test
    public void tooManyChildrenPerAdult1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "Economy", 1, 3, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Each adult can accompany up to 2 children only.", ex.getMessage());
    }

    @Test
    public void tooManyChildrenPerAdult2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("10/11/2025", "MEL", false,
                        "20/11/2025", "PVG", "Economy", 1, 4, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Each adult can accompany up to 2 children only.", ex.getMessage());
    }

    // =================== Case 5: Infant in emergency row ===================
    @Test
    public void infantInEmergencyRow1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", true,
                        "28/10/2025", "DOH", "Economy", 1, 0, 1)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants cannot be seated in emergency row seating.", ex.getMessage());
    }

    @Test
    public void infantInEmergencyRow2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("22/11/2025", "SYD", true,
                        "30/11/2025", "MEL", "Economy", 2, 0, 2)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants cannot be seated in emergency row seating.", ex.getMessage());
    }

    // =================== Case 6: Infant in Business Class ===================
    @Test
    public void infantInBusinessClass1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "Business", 1, 0, 1)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants are not allowed in Business Class.", ex.getMessage());
    }

    @Test
    public void infantInBusinessClass2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("25/12/2025", "LAX", false,
                        "05/01/2026", "CDG", "Business", 2, 0, 2)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants are not allowed in Business Class.", ex.getMessage());
    }

    // =================== Case 7: Infant without adult ===================
    @Test
    public void infantWithoutAdult1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "Economy", 0, 0, 1)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants must be accompanied by at least one adult.", ex.getMessage());
    }

    @Test
    public void infantWithoutAdult2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("01/11/2025", "MEL", false,
                        "10/11/2025", "PVG", "Economy", 0, 0, 2)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Infants must be accompanied by at least one adult.", ex.getMessage());
    }

    // =================== Case 8: Return date before departure ===================
    @Test
    public void returnDateBeforeDeparture1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("28/10/2025", "DEL", false,
                        "20/10/2025", "DOH", "Economy", 1, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Return date cannot be before departure date.", ex.getMessage());
    }

    @Test
    public void returnDateBeforeDeparture2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("10/12/2025", "LAX", false,
                        "05/12/2025", "CDG", "Economy", 2, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Return date cannot be before departure date.", ex.getMessage());
    }

    // =================== Case 9: Invalid departure airport ===================
    @Test
    public void invalidDepartureAirport1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "XYZ", false,
                        "28/10/2025", "DOH", "Economy", 1, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Departure airport 'XYZ' is not allowed.", ex.getMessage());
    }

    @Test
    public void invalidDepartureAirport2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("15/11/2025", "ABC", false,
                        "25/11/2025", "MEL", "Economy", 1, 1, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Departure airport 'ABC' is not allowed.", ex.getMessage());
    }

    // =================== Case 10: Same departure & destination ===================
    @Test
    public void sameDepartureAndDestination1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DEL", "Economy", 1, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Departure and destination airports cannot be the same.", ex.getMessage());
    }

    @Test
    public void sameDepartureAndDestination2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("25/12/2025", "LAX", false,
                        "02/01/2026", "LAX", "Economy", 2, 1, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Departure and destination airports cannot be the same.", ex.getMessage());
    }

    // =================== Case 11: Invalid seating class ===================
    @Test
    public void invalidSeatingClass1() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("20/10/2025", "DEL", false,
                        "28/10/2025", "DOH", "Economyy", 1, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Seating class must be one of: economy, premium economy, business, first.", ex.getMessage());
    }

    @Test
    public void invalidSeatingClass2() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                 runFlightSearch("15/11/2025", "MEL", false,
                        "25/11/2025", "PVG", "Prem Economy", 2, 0, 0)
        );
        System.out.println("Exception message: " + ex.getMessage());
        assertEquals("Seating class must be one of: economy, premium economy, business, first.", ex.getMessage());
    }

    // =================== Case 12: Valid input cases ===================
    @Test
    public void validInput1() {
        assertTrue( runFlightSearch("20/10/2025", "DEL", false,
                "28/10/2025", "DOH", "Economy", 2, 1, 0));
    }

    @Test
    public void validInput2() {
        assertTrue( runFlightSearch("10/11/2025", "LAX", false,
                "20/11/2025", "CDG", "Premium Economy", 1, 0, 0));
    }

    @Test
    public void validInput3() {
        assertTrue( runFlightSearch("05/12/2025", "MEL", false,
                "15/12/2025", "PVG", "Business", 2, 0, 0));
    }

    @Test
    public void validInput4() {
        assertTrue( runFlightSearch("20/10/2025", "SYD", false,
                "30/10/2025", "DOH", "First", 1, 0, 0));
    }
}