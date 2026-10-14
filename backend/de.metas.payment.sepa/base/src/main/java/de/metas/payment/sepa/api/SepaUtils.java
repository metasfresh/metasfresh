/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.payment.sepa.api;

import de.metas.banking.BankAccount;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.api.impl.ESRCheckDigitBuilder;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.jetbrains.annotations.Nullable;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@UtilityClass
public class SepaUtils
{
	/**
	 * Message for a structured reference that is not a valid QR reference although the payment needs one.
	 */
	public static final AdMessageKey ERR_SEPA_Export_InvalidReference = AdMessageKey.of("de.metas.payment.sepa.SEPA_Export_InvalidReference");

	/**
	 * Swiss QR reference: 26 digits plus one check digit.
	 */
	public static final int QR_REFERENCE_LENGTH = 27;

	/**
	 * Position of the Swiss institution identification (IID, formerly "BC") within a CH/LI IBAN (without spaces).
	 */
	private static final int IBAN_IID_START_IDX = 4;
	private static final int IBAN_IID_END_IDX = 9;

	/**
	 * coming from the SEPA specifications https://www.sepaforcorporates.com/sepa-implementation/valid-xml-characters-sepa-payments/
	 */
	private static final Pattern SEPA_COMPLIANT_PATTERN = Pattern.compile(
			"([a-zA-Z0-9.,;:'\\+\\-/()\\*\\[\\]{}\\\\`´~ ]|[!\"#%&<>÷=@_$£]|[àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])*");

	/**
	 * Strict ISO-3166-1 alpha-2 country code: exactly two uppercase ASCII letters.
	 * Used to validate {@link BankAccount#getAccountCountry()} before it is written
	 * into a SEPA {@code <Ctry>} element. Catches the common data-quality mistake of
	 * storing a country name (e.g. {@code "Switzerland"}) instead of its ISO code
	 * ({@code "CH"}), which would otherwise produce schema-invalid SEPA XML.
	 */
	private static final Pattern ISO_3166_ALPHA2 = Pattern.compile("^[A-Z]{2}$");

	private final static Map<String, String> SEPA_REPLACEMENT_MAP = new HashMap<>();

	static
	{
		SEPA_REPLACEMENT_MAP.put("’", "'");
		SEPA_REPLACEMENT_MAP.put("“", "\"");
		SEPA_REPLACEMENT_MAP.put("INVALID_CHAR", "_"); // Define replacement for any non-compliant character
	}

	/**
	 * Cleans a given input string, replacing non-compliant SEPA characters with their respective
	 * mapped values, or an underscore for unsupported characters.
	 *
	 * @param input the string to sanitize
	 * @return the sanitized SEPA-compliant string
	 */
	@Nullable
	public static String replaceForbiddenChars(@Nullable final String input)
	{
		if (input == null)
		{
			return null;
		}

		// Apply specific replacements from SEPA_REPLACEMENT_MAP
		String sanitizedText = input;
		for (Map.Entry<String, String> entry : SEPA_REPLACEMENT_MAP.entrySet())
		{
			if (!entry.getKey().equals("INVALID_CHAR"))
			{ // Exclude INVALID_CHAR replacement here
				sanitizedText = sanitizedText.replace(entry.getKey(), entry.getValue());
			}
		}

		// Use SEPA_COMPLIANT_PATTERN to retain only compliant characters
		StringBuilder compliantText = new StringBuilder();
		for (int i = 0; i < sanitizedText.length(); i++)
		{
			String currentChar = String.valueOf(sanitizedText.charAt(i));
			if (SEPA_COMPLIANT_PATTERN.matcher(currentChar).matches())
			{
				compliantText.append(currentChar);
			}
			else
			{
				// Use the map's value for invalid characters (underscore)
				compliantText.append(SEPA_REPLACEMENT_MAP.get("INVALID_CHAR"));
			}
		}

		return compliantText.toString();
	}

	/**
	 * Joins {@code a} and {@code b} with a single space, skipping blank values. Returns {@code ""}
	 * when both are blank. Used to assemble the "{zip} {city}" address line on a SEPA PostalAddress
	 * without emitting "null null" / " " when one side is missing.
	 */
	@NonNull
	public static String joinNonBlank(@Nullable final String a, @Nullable final String b)
	{
		final boolean hasA = Check.isNotBlank(a);
		final boolean hasB = Check.isNotBlank(b);
		if (hasA && hasB)
		{
			return a + " " + b;
		}
		return hasA ? a : (hasB ? b : "");
	}

	/**
	 * Verifies that the bank account carries a valid ISO-3166-1 alpha-2 country code
	 * (exactly two uppercase ASCII letters), as required for the {@code <Ctry>} field
	 * of a SEPA {@code PostalAddress}. To be called from the marshalers' bank-account
	 * branches whenever any address field is populated, so that data-quality issues
	 * (missing country, country stored as a full name, lowercase) surface as a clear
	 * SEPA exception instead of silently producing schema-invalid XML.
	 */
	public static void assertValidAccountCountry(@NonNull final BankAccount bankAccount)
	{
		final int bankAccountId = bankAccount.getId().getRepoId();
		final String country = bankAccount.getAccountCountry();
		if (Check.isBlank(country))
		{
			throw new SepaMarshallerException(String.format(
					"C_BP_BankAccount has an address but no country set; A_Country is required"
							+ " (C_BP_BankAccount_ID=%d)",
					bankAccountId));
		}
		if (!ISO_3166_ALPHA2.matcher(country).matches())
		{
			throw new SepaMarshallerException(String.format(
					"C_BP_BankAccount.A_Country '%s' is not a valid ISO-3166 alpha-2 country code;"
							+ " expected two uppercase letters"
							+ " (C_BP_BankAccount_ID=%d)",
					country, bankAccountId));
		}
	}

	/**
	 * @return {@code true} if the given IBAN (stripped from spaces) starts with "CH" or "LI", i.e. contains a Swiss institution identification (IID).
	 */
	public static boolean isSwissIBAN(@Nullable final String iban)
	{
		if (Check.isBlank(iban))
		{
			return false;
		}
		final String ibanToUse = iban.replaceAll(" ", "");
		return ibanToUse.startsWith("CH") || ibanToUse.startsWith("LI");
	}

	/**
	 * For IBANs that start with "CH" or "LI", extracts the Swiss institution identification (IID, formerly "BC" for "bank code").
	 *
	 * @param ibanOwnerInfo describes where the IBAN comes from; only used in the error message if the IBAN is too short
	 * @return {@code null} if the given {@code iban} is {@code null}, empty or not "CH"/"LI".
	 * @see <a href="http://www.swissiban.com/de.htm">http://www.swissiban.com/de.htm</a>
	 */
	@Nullable
	public static String extractIIDFromSwissIbanOrNull(@Nullable final String iban, @NonNull final Supplier<String> ibanOwnerInfo)
	{
		if (!isSwissIBAN(iban))
		{
			return null;
		}

		final String ibanToUse = iban.replaceAll(" ", "");
		if (ibanToUse.length() < IBAN_IID_END_IDX)
		{
			throw new SepaMarshallerException("Given IBAN " + iban + " for " + ibanOwnerInfo.get() + " is to short. Please verify that it's actually an IBAN at all");
		}
		return ibanToUse.substring(IBAN_IID_START_IDX, IBAN_IID_END_IDX);
	}

	/**
	 * Swiss QR reference: 26 digits plus one check digit (modulo 10, recursive; the same algorithm as the ESR reference number, see {@link ESRCheckDigitBuilder}).
	 *
	 * @return {@code false} if the given reference is {@code null}, does not have 27 characters, contains anything else than the ASCII digits {@code 0-9}, or has a wrong check digit
	 */
	public static boolean isValidQrReference(@Nullable final String reference)
	{
		if (reference == null || reference.length() != QR_REFERENCE_LENGTH)
		{
			return false;
		}
		for (int i = 0; i < QR_REFERENCE_LENGTH; i++)
		{
			final char c = reference.charAt(i);
			if (c < '0' || c > '9')
			{
				return false;
			}
		}

		final int expectedCheckDigit = new ESRCheckDigitBuilder().calculateESRCheckDigit(reference.substring(0, QR_REFERENCE_LENGTH - 1));
		return reference.charAt(QR_REFERENCE_LENGTH - 1) - '0' == expectedCheckDigit;
	}

	/**
	 * @return the business partner and bank account of the given line, as user-readable (translated) text for error messages
	 */
	public static String createInfo(@NonNull final I_SEPA_Export_Line line)
	{
		final StringBuilder sb = new StringBuilder();
		if (line.getC_BPartner_ID() > 0)
		{
			sb.append("@C_BPartner_ID@ ");
			sb.append(getBPartnerNameById(line.getC_BPartner_ID()));
		}
		if (line.getC_BP_BankAccount_ID() > 0)
		{
			sb.append(" @C_BP_BankAccount_ID@ ");
			sb.append(line.getC_BP_BankAccount().getDescription());
		}
		return Services.get(IMsgBL.class).parseTranslation(InterfaceWrapperHelper.getCtx(line), sb.toString());
	}

	public static String getBPartnerNameById(final int bpartnerRepoId)
	{
		return Services.get(IBPartnerBL.class).getBPartnerName(BPartnerId.ofRepoIdOrNull(bpartnerRepoId));
	}

	/**
	 * @return the first of the given values that is not blank, trimmed; {@code null} if all are blank
	 */
	@SafeVarargs
	@Nullable
	public static String getFirstNonEmpty(@NonNull final Supplier<String>... values)
	{
		final String result = CoalesceUtil.firstValidValue(Check::isNotBlank, values);
		return result != null ? result.trim() : null;
	}

	/**
	 * @return the date part (yyyy-MM-dd, without time and timezone) of the given date, as needed e.g. for the requested execution date
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendarDate(@NonNull final DatatypeFactory datatypeFactory, @NonNull final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return datatypeFactory.newXMLGregorianCalendar(df.format(date));
	}
}
