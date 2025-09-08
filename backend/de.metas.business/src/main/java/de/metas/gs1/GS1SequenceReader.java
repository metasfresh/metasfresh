package de.metas.gs1;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

class GS1SequenceReader
{
	private static final char SEPARATOR_CHAR = 0x1D;

	private final String sequence;
	@Getter private int position = 0;

	GS1SequenceReader(String sequence)
	{
		this.sequence = sequence;
	}

	public Object readDataFieldInStandardFormat(GS1ApplicationIdentifier identifier)
	{
		switch (identifier.getFormat())
		{
			case NUMERIC_FIXED:
				return readFixedLengthNumeric(identifier.getMaxLength());
			case NUMERIC_VARIABLE:
				return readVariableLengthNumeric(identifier.getMinLength(), identifier.getMaxLength());
			case ALPHANUMERIC_FIXED:
				return readFixedLengthAlphanumeric(identifier.getMaxLength());
			case ALPHANUMERIC_VARIABLE:
				return readVariableLengthAlphanumeric(identifier.getMinLength(), identifier.getMaxLength());
			case DECIMAL:
				return readDecimal(identifier.getMinLength(), identifier.getMaxLength());
			case DATE:
				return readDate();
		}
		return null;
	}

	public Object readDataFieldInCustomFormat(@NonNull final GS1ApplicationIdentifier identifier)
	{
		switch (identifier)
		{
			case AMOUNT_PAYABLE_WITH_CURRENCY:
			case AMOUNT_PAYABLE_PER_SINGLE_ITEM_WITH_CURRENCY:
				return readCurrencyAndAmount();
			case SHIP_TO_POSTAL_CODE_WITH_COUNTRY:
			{
				String countryCode = readNumericDataField(3, 3);
				String postalCode = readDataField(1, 9);
				return Arrays.asList(countryCode, postalCode);
			}
			case COUNTRY_OF_INITIAL_PROCESSING:
			case COUNTRY_OF_DISASSEMBLY:
				return readCountryList();
			case EXPIRATION_DATE_AND_TIME:
				return readDateAndTimeWithoutSeconds();
			case HARVEST_DATE:
				return readDateOrDateRange();
			case PRODUCTION_DATE_AND_TIME:
				return readDateAndTimeWithOptionalMinutesAndSeconds();
		}
		return null;
	}

	public String readFixedLengthNumeric(int length)
	{
		return readVariableLengthNumeric(length, length);
	}

	private String readVariableLengthNumeric(int minLength, int maxLength)
	{
		String dataField = readNumericDataField(minLength, maxLength);
		skipSeparatorIfPresent();
		return dataField;
	}

	private String readFixedLengthAlphanumeric(int length)
	{
		return readVariableLengthAlphanumeric(length, length);
	}

	public String readVariableLengthAlphanumeric(int minLength, int maxLength)
	{
		String dataField = readDataField(minLength, maxLength);
		skipSeparatorIfPresent();
		return dataField;
	}

	private LocalDateTime readDate()
	{
		try
		{
			return parseDateAndTime(readNumericDataField(6, 6));
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("data field must be numeric");
		}
	}

	private List<LocalDateTime> readDateOrDateRange()
	{
		final String dataField = readNumericDataField(6, 12);
		if (dataField.length() == 6)
		{
			return ImmutableList.of(parseDateAndTime(dataField.substring(0, 6)));
		}
		else if (dataField.length() == 12)
		{
			final LocalDateTime first = parseDateAndTime(dataField.substring(0, 6));
			final LocalDateTime second = parseDateAndTime(dataField.substring(6, 12));
			return ImmutableList.of(first, second);
		}
		else
		{
			throw new AdempiereException("invalid date/date range field: " + dataField);
		}
	}

	private LocalDateTime readDateAndTimeWithoutSeconds()
	{
		try
		{
			return parseDateAndTime(readNumericDataField(10, 10));
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("data field must be numeric");
		}
	}

	private LocalDateTime readDateAndTimeWithOptionalMinutesAndSeconds()
	{
		try
		{
			String dataField = readNumericDataField(8, 12);
			if (dataField.length() != 8 && dataField.length() != 12)
			{
				throw new AdempiereException("invalid data field length");
			}
			return parseDateAndTime(dataField);
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("data field must be numeric");
		}
	}

	private LocalDateTime parseDateAndTime(String s)
	{
		try
		{
			int year = Integer.parseInt(s.substring(0, 2));
			final Month month = Month.of(Integer.parseInt(s.substring(2, 4)));
			int dayOfMonth = Integer.parseInt(s.substring(4, 6));

			final int hour = s.length() >= 8 ? Integer.parseInt(s.substring(6, 8)) : 0;
			final int minutes = s.length() >= 10 ? Integer.parseInt(s.substring(8, 10)) : 0;
			final int seconds = s.length() >= 12 ? Integer.parseInt(s.substring(10, 12)) : 0;

			final int currentYear = SystemTime.asZonedDateTime().getYear();
			year = resolveTwoDigitYear(year, currentYear);

			// When day is zero that means last day of the month
			final boolean isLastDayOfMonth = dayOfMonth == 0;
			dayOfMonth = isLastDayOfMonth ? 1 : dayOfMonth;

			LocalDate date = LocalDate.of(year, month, dayOfMonth);
			if (isLastDayOfMonth)
			{
				date = date.with(lastDayOfMonth());
			}

			final LocalTime time = LocalTime.of(hour, minutes, seconds);

			return date.atTime(time);
		}
		catch (Exception e)
		{
			throw new AdempiereException("Invalid date: " + s);
		}
	}

	/**
	 * Converts a two digit year to four digits using the algorithm in GS1 General specification section 7.12.
	 *
	 * @param year        two digit year
	 * @param currentYear four digit century and year
	 */
	private static int resolveTwoDigitYear(int year, int currentYear)
	{
		int century = currentYear - (currentYear % 100);
		int difference = year - (currentYear % 100);
		if (difference >= 51 && difference <= 99)
		{
			century -= 100;
		}
		else if (difference >= -99 && difference <= -50)
		{
			century += 100;
		}
		return century + year;
	}

	private BigDecimal readDecimal(int minLength, int maxLength)
	{
		try
		{
			int decimalPointPosition = readDecimalPointPosition();
			String dataField = readNumericDataField(minLength, maxLength);
			return new BigDecimal(dataField).movePointLeft(decimalPointPosition);
		}
		catch (ArithmeticException e)
		{
			throw new IllegalArgumentException("invalid decimal");
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("data field must be numeric");
		}
	}

	private int readDecimalPointPosition()
	{
		char decimalPointIndicator = readChar();
		if (decimalPointIndicator < '0' || decimalPointIndicator > '9')
		{
			throw new IllegalArgumentException("decimal point indicator must be a digit");
		}
		return decimalPointIndicator - '0';
	}

	private List<?> readCurrencyAndAmount()
	{
		int decimalPointPosition = readDecimalPointPosition();
		String currencyCode = readNumericDataField(3, 3);
		String digits = readDataField(1, 15);
		BigDecimal amount = new BigDecimal(digits).movePointLeft(decimalPointPosition);
		return Arrays.asList(currencyCode, amount);
	}

	private List<String> readCountryList()
	{
		String dataField = readNumericDataField(3, 15);
		if (dataField.length() % 3 != 0)
		{
			throw new IllegalArgumentException("invalid data field length");
		}
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < dataField.length(); i += 3)
		{
			list.add(dataField.substring(i, i + 3));
		}
		return list;
	}

	private String readNumericDataField(int minLength, int maxLength)
	{
		String dataField = readDataField(minLength, maxLength);
		if (!StringUtils.isNumber(dataField))
		{
			throw new AdempiereException("data field must be numeric: " + dataField);
		}
		return dataField;
	}

	private String readDataField(int minLength, int maxLength)
	{
		int length = 0;
		int endIndex = position;
		while (length < maxLength && endIndex < sequence.length())
		{
			if (sequence.charAt(endIndex) == SEPARATOR_CHAR)
			{
				break;
			}
			endIndex++;
			length++;
		}
		if (length < minLength && minLength == maxLength)
		{
			throw new IllegalArgumentException("data field must be exactly " + minLength + " characters long");
		}
		if (length < minLength)
		{
			throw new IllegalArgumentException("data field must be at least " + minLength + " characters long");
		}
		String dataField = sequence.substring(position, endIndex);
		position = endIndex;
		return dataField;
	}

	private void skipSeparatorIfPresent()
	{
		if (position < sequence.length() && sequence.charAt(position) == SEPARATOR_CHAR)
		{
			position++;
		}
	}

	public int remainingLength()
	{
		return sequence.length() - position;
	}

	public boolean startsWith(String prefix)
	{
		return sequence.startsWith(prefix, position);
	}

	private char readChar()
	{
		return sequence.charAt(position++);
	}

	public String read(int length)
	{
		String s = peek(length);
		position += length;
		return s;
	}

	public String peek(int length)
	{
		return sequence.substring(position, position + length);
	}
}
