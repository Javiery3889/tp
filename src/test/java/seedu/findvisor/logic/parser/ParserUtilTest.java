package seedu.findvisor.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.findvisor.testutil.Assert.assertThrows;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.logic.parser.exceptions.ParseException;
import seedu.findvisor.model.person.Address;
import seedu.findvisor.model.person.Email;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.model.person.Name;
import seedu.findvisor.model.person.Phone;
import seedu.findvisor.model.person.Remark;
import seedu.findvisor.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE_STRING = "10/12/2012";
    private static final String INVALID_DATETIME_STRING = "INVALID DATETIME STRING";
    private static final String INVALID_REMARK = "/r is ♀";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_NAME_EXTENDED = "Rachel Lee Walker";
    private static final String VALID_NAME_WITH_BIG_SPACE_IN_BETWEEN = "Rachel       Walker";
    private static final String VALID_NAME_WITH_DIFFERING_SPACES = "Rachel  Lee  Walker";
    private static final String VALID_PHONE = "99123456";
    private static final String VALID_PHONE_WITH_SPACE_BETWEEN = "9912           3456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DATE_STRING = "10-12-2024";
    private static final String VALID_DATETIME_STRING = "10-12-2024T14:00";
    private static final String VALID_REMARK = "Wants to own 2 luxury cars worth $3 million each";
    private static final LocalDateTime START_DATETIME = LocalDateTime.of(2024, 12, 1, 13, 0);
    private static final LocalDateTime END_DATETIME = LocalDateTime.of(2024, 12, 1, 14, 0);


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseName_validValueWithBigSpaceInBetween_returnsTrimmedName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_WITH_BIG_SPACE_IN_BETWEEN));
    }

    @Test
    public void parseName_validValueWithDifferingSpaces_returnsTrimmedName() throws Exception {
        Name expectedName = new Name(VALID_NAME_EXTENDED);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_WITH_DIFFERING_SPACES));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhone_validValueWithWhitespaceInBetween_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespaceInBetween = VALID_PHONE_WITH_SPACE_BETWEEN;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespaceInBetween));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMeetingDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingDate((String) null));
    }

    @Test
    public void parseMeetingDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingDate(INVALID_DATE_STRING));
    }

    @Test
    public void parseMeetingDate_validValueWithoutWhitespace_returnsPhone() throws Exception {
        LocalDate expectedMeetingDate = LocalDate.parse(VALID_DATE_STRING, DateTimeUtil.DATE_FORMAT);
        assertEquals(expectedMeetingDate, ParserUtil.parseMeetingDate(VALID_DATE_STRING));
    }

    @Test
    public void parseMeetingDate_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String meetingDateWithWhitespace = WHITESPACE + VALID_DATE_STRING + WHITESPACE;
        LocalDate expectedMeetingDate = LocalDate.parse(VALID_DATE_STRING, DateTimeUtil.DATE_FORMAT);
        assertEquals(expectedMeetingDate, ParserUtil.parseMeetingDate(meetingDateWithWhitespace));
    }

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark(null));
    }

    @Test
    public void parseRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRemark(INVALID_REMARK));
    }

    @Test
    public void parseRemark_valueWithoutWhitespace_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK).get());
    }

    @Test
    public void parseRemark_valueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace).get());
    }

    @Test
    public void parseRemark_emptyValue_returnsOptionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseRemark(""));
    }

    @Test
    public void parseMeetingDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingDateTime(null));
    }

    @Test
    public void parseMeetingDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingDateTime(INVALID_DATETIME_STRING));
    }

    @Test
    public void parseMeetingDateTime_validValue_returnsLocalDateTime() throws Exception {
        assertEquals(DateTimeUtil.parseDateTimeString(VALID_DATETIME_STRING),
                ParserUtil.parseMeetingDateTime(VALID_DATETIME_STRING));
    }

    @Test
    public void parseMeetingRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingRemark(null));
    }

    @Test
    public void parseMeetingRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingRemark(INVALID_REMARK));
    }

    @Test
    public void parseMeetingRemark_validValue_returnsTrimmedRemark() throws Exception {
        assertEquals(VALID_REMARK, ParserUtil.parseMeetingRemark(VALID_REMARK));
    }

    @Test
    public void parseMeeting_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeeting(null, null, null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeeting(START_DATETIME, null, null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeeting(START_DATETIME, END_DATETIME, null));
    }

    @Test
    public void parseMeeting_invalidRemark_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeeting(START_DATETIME, END_DATETIME, INVALID_REMARK));
    }

    @Test
    public void parseMeeting_invalidDateTimes_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeeting(END_DATETIME, START_DATETIME, VALID_REMARK));
    }

    @Test
    public void parseMeeting_validMeeting_returnsMeeting() throws Exception {
        assertEquals(new Meeting(START_DATETIME, END_DATETIME, VALID_REMARK),
                ParserUtil.parseMeeting(START_DATETIME, END_DATETIME, VALID_REMARK));
    }
}
