package seedu.findvisor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.testutil.PersonBuilder;

public class PersonTodayMeetingsPredicateTest {

    @Test
    public void equals() {
        PersonTodayMeetingsPredicate predicate = new PersonTodayMeetingsPredicate();
        PersonTodayMeetingsPredicate predicateCopy = new PersonTodayMeetingsPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        // This should always return true, unless test occurs in between 2 dates.
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_meetingStartDateIsToday_returnsTrue() {
        PersonTodayMeetingsPredicate predicate = new PersonTodayMeetingsPredicate();
        assertTrue(predicate.test(new PersonBuilder().withMeeting(
                Optional.of(new Meeting(LocalDateTime.now(), LocalDateTime.now().plusHours(1), ""))).build()));
    }

    @Test
    public void test_meetingStartDateIsNotToday_returnsFalse() {
        PersonTodayMeetingsPredicate predicate = new PersonTodayMeetingsPredicate();
        assertFalse(predicate.test(new PersonBuilder().withMeeting(
                Optional.of(new Meeting(LocalDateTime.now().minusDays(1), LocalDateTime.now(), ""))).build()));
    }

    @Test
    public void testGetPredicateDescription() {
        String dateString = DateTimeUtil.dateToString(LocalDate.now());
        PersonTodayMeetingsPredicate predicate = new PersonTodayMeetingsPredicate();
        String expected = String.format("Today's meeting on \"%1$s\"", dateString);
        assertEquals(expected, predicate.getPredicateDescription());
    }

    @Test
    public void toStringMethod() {
        String dateString = DateTimeUtil.dateToString(LocalDate.now());
        PersonTodayMeetingsPredicate predicate = new PersonTodayMeetingsPredicate();
        String expected = PersonTodayMeetingsPredicate.class.getCanonicalName() + "{date=" + dateString + "}";
        assertEquals(expected, predicate.toString());
    }
}
