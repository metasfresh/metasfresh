package de.metas.handlingunits.grai;

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
		final String prefix = companyPrefix + "." + assetType + "." + serialPrefix;
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

	private static void checkCounterLimit(final int counter)
	{
		if (counter > MAX_COUNTER)
		{
			throw new AdempiereException("Cannot generate more than " + MAX_COUNTER + " dummy GRAIs per order");
		}
	}

	@NonNull
	private static String padSerialPrefix(@NonNull final String serialPrefix)
	{
		if (serialPrefix.length() > 10)
		{
			throw new AdempiereException("Serial prefix too long for dummy GRAI generation (max 10 chars): " + serialPrefix);
		}
		return StringUtils.lpadZero(serialPrefix, 10, "serialPrefix");
	}
}
