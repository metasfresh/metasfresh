package de.metas.handlingunits.grai;

import de.metas.i18n.AdMessageKey;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class DummyGRAITemplate
{
	public static final String MIGROS_COMPANY_PREFIX = "7613204";
	public static final String MIGROS_ASSET_TYPE = "00307";
	public static final int MAX_COUNTER = 99;

	// User-facing, translated prerequisite messages for dummy-GRAI generation. Centralised here so every
	// validation layer (order change/completion, picking job-open, picking completion) surfaces the same text.
	public static final AdMessageKey MSG_DUMMY_GRAI_SERIAL_PREFIX_TOO_LONG = AdMessageKey.of("de.metas.handlingunits.grai.DummyGRAISerialPrefixTooLong");
	public static final AdMessageKey MSG_DUMMY_GRAI_POREFERENCE_MISSING = AdMessageKey.of("de.metas.handlingunits.grai.DummyGRAIPOReferenceMissing");

	@NonNull String companyPrefix;
	@NonNull String assetType;
	@NonNull String serialPrefix;

	@NonNull
	public static DummyGRAITemplate migros(@NonNull final String serialPrefix)
	{
		return new DummyGRAITemplate(MIGROS_COMPANY_PREFIX, MIGROS_ASSET_TYPE, padSerialPrefix(serialPrefix));
	}

	@NonNull
	public GRAI buildGRAI(final int counter)
	{
		checkCounterLimit(counter);
		return GRAI.ofCanonicalString(companyPrefix + "." + assetType + "." + serialPrefix + String.format("%02d", counter));
	}

	public int extractCounter(@Nullable final GRAI grai)
	{
		if (grai == null)
		{
			return 0;
		}
		final String value = grai.toCanonicalString();
		final String prefix = prefix();
		if (!value.startsWith(prefix))
		{
			return 0;
		}
		final String counterStr = value.substring(prefix.length());
		if (counterStr.length() != 2)
		{
			return 0;
		}
		try
		{
			return Integer.parseInt(counterStr);
		}
		catch (final NumberFormatException e)
		{
			return 0;
		}
	}

	@NonNull
	private String prefix()
	{
		return companyPrefix + "." + assetType + "." + serialPrefix;
	}

	private static void checkCounterLimit(final int counter)
	{
		if (counter > MAX_COUNTER)
		{
			throw new AdempiereException("Cannot generate more than " + MAX_COUNTER + " dummy GRAIs per order");
		}
	}

	/**
	 * @return {@code true} iff {@code serialPrefix} (the sales order's PO reference) can form a valid dummy-GRAI
	 * serial prefix — i.e. it is at most 10 characters. Non-throwing counterpart of {@link #assertValidSerialPrefix};
	 * use it to gate a call to {@link #migros(String)} that would otherwise throw on an over-length prefix.
	 */
	public static boolean isValidSerialPrefix(@NonNull final String serialPrefix)
	{
		return serialPrefix.length() <= 10;
	}

	/**
	 * Asserts that {@code serialPrefix} (the sales order's PO reference) can form a valid dummy-GRAI serial
	 * prefix — it must be at most 10 characters. Throws the translated prerequisite message otherwise.
	 * The single source of truth for the dummy-GRAI length rule, reused by the early validation layers.
	 */
	public static void assertValidSerialPrefix(@NonNull final String serialPrefix)
	{
		if (!isValidSerialPrefix(serialPrefix))
		{
			throw new AdempiereException(MSG_DUMMY_GRAI_SERIAL_PREFIX_TOO_LONG, serialPrefix);
		}
	}

	@NonNull
	private static String padSerialPrefix(@NonNull final String serialPrefix)
	{
		assertValidSerialPrefix(serialPrefix);
		return StringUtils.lpadZero(serialPrefix, 10, "serialPrefix");
	}
}
