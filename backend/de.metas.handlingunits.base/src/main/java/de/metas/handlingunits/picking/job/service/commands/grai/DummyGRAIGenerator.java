package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.grai.GRAI;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
class DummyGRAIGenerator
{
	static final String MIGROS_COMPANY_PREFIX = "7613204";
	static final String MIGROS_ASSET_TYPE = "00307";
	static final int MAX_DUMMY_COUNTER = 99;

	/**
	 * Build a dummy GRAI in Migros format.
	 * Format: 7613204.00307.<paddedPOReference><2-digit-counter>
	 *
	 * @param paddedPORef POReference left-padded to 10 digits
	 * @param counter     sequential counter (1-99)
	 */
	@NonNull
	public GRAI buildDummyGRAI(@NonNull final String paddedPORef, final int counter)
	{
		return GRAI.ofCanonicalString(MIGROS_COMPANY_PREFIX + "." + MIGROS_ASSET_TYPE + "." + paddedPORef + String.format("%02d", counter));
	}

	/**
	 * Pad a POReference to 10 digits with leading zeros.
	 * Throws if longer than 10 characters.
	 */
	@NonNull
	public String padPOReference(@NonNull final String poReference)
	{
		if (poReference.length() > 10)
		{
			throw new org.adempiere.exceptions.AdempiereException("POReference too long for dummy GRAI generation (max 10 chars): " + poReference);
		}
		return StringUtils.lpadZero(poReference, 10, "POReference");
	}

	/**
	 * Extract the 2-digit dummy counter from a GRAI, if it matches the dummy prefix.
	 * Returns 0 if not a dummy GRAI.
	 */
	public int extractDummyCounter(@Nullable final GRAI grai, @NonNull final String dummyPrefix)
	{
		if (grai == null)
		{
			return 0;
		}
		final String value = grai.toCanonicalString();
		if (!value.startsWith(dummyPrefix))
		{
			return 0;
		}
		final String counterStr = value.substring(dummyPrefix.length());
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

	/**
	 * Build the prefix used for dummy GRAI matching (for counter extraction).
	 */
	@NonNull
	public String buildDummyPrefix(@NonNull final String paddedPORef)
	{
		return MIGROS_COMPANY_PREFIX + "." + MIGROS_ASSET_TYPE + "." + paddedPORef;
	}
}
