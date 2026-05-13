package de.metas.handlingunits.grai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class GRAI implements Comparable<GRAI>
{
	private static final int GS1_COMPANY_PREFIX_LENGTH = 7;

	@NonNull String canonicalValue;

	/**
	 * Create from canonical dot-separated format: {@code companyPrefix.assetType.serial}
	 */
	@JsonCreator
	@NonNull
	public static GRAI ofCanonicalString(@NonNull final String canonicalValue)
	{
		final String trimmed = StringUtils.trimBlankToNull(canonicalValue);
		if (trimmed == null || !isCanonicalFormat(trimmed))
		{
			throw new AdempiereException("Invalid canonical GRAI (expected companyPrefix.assetType.serial): " + canonicalValue);
		}

		return new GRAI(trimmed);
	}

	@Nullable
	public static GRAI ofNullableCanonicalString(@Nullable final String canonicalValue)
	{
		final String trimmed = StringUtils.trimBlankToNull(canonicalValue);
		return trimmed != null ? ofCanonicalString(trimmed) : null;
	}

	/**
	 * Parse a GRAI from any supported format:
	 * <ul>
	 *   <li>Canonical dot-separated: {@code 7613204.00307.1234567890}</li>
	 *   <li>GS1 AI 8003 barcode: {@code 8003 0 7613204 00307 C serial} (indicator + 13 digits + check digit + serial)</li>
	 * </ul>
	 *
	 * @return parsed GRAI or {@code null} if input cannot be parsed
	 */
	@Nullable
	public static GRAI parse(@Nullable final String input)
	{
		final String trimmed = StringUtils.trimBlankToNull(input);
		if (trimmed == null)
		{
			return null;
		}

		// Try canonical (dot-separated) first
		if (isCanonicalFormat(trimmed))
		{
			return new GRAI(trimmed);
		}

		// Try GS1 AI 8003
		return parseGS1AI8003(trimmed);
	}

	private static boolean isCanonicalFormat(@NonNull final String value)
	{
		final String[] parts = value.split("\\.");
		return parts.length == 3
				&& !parts[0].isEmpty()
				&& !parts[1].isEmpty()
				&& !parts[2].isEmpty();
	}

	/**
	 * Parse GS1 AI 8003 barcode data.
	 * <p>
	 * Format after AI prefix: {@code 0} (indicator) + 13 digits (company prefix + asset type) + check digit + serial
	 * <p>
	 * The raw barcode string may or may not include the AI prefix "8003".
	 */
	@Nullable
	private static GRAI parseGS1AI8003(@NonNull final String rawInput)
	{
		String data = rawInput;

		// Strip AI prefix if present
		if (data.startsWith("8003"))
		{
			data = data.substring(4);
		}

		// Per GS1 General Specifications, AI 8003 (GRAI) has a fixed-length asset
		// reference of 14 digits after the AI: 1 padding digit + 13-digit asset
		// reference (GS1 Company Prefix + Item Reference + check digit at the end).
		// An optional alphanumeric serial (0..16 chars) may follow.
		// Minimum meaningful length: 14 (asset reference) + 1 (at least one serial char,
		// required to form a non-empty canonical serial part) = 15.
		if (data.length() < 15)
		{
			return null;
		}

		// Position 0: padding digit (skip)
		// Positions 1-13: 13-digit asset reference (company prefix + asset type, last digit is the GS1 check digit)
		// Position 14+: serial reference
		final String base = data.substring(1, 14);
		final String serial = data.substring(14);

		final String companyPrefix = base.substring(0, GS1_COMPANY_PREFIX_LENGTH);
		final String assetType = base.substring(GS1_COMPANY_PREFIX_LENGTH);

		return new GRAI(companyPrefix + "." + assetType + "." + serial);
	}

	@Override
	@Deprecated
	public String toString() {return toCanonicalString();}

	@JsonValue
	@NonNull
	public String toCanonicalString()
	{
		return canonicalValue;
	}

	@Override
	public int compareTo(@NonNull final GRAI o)
	{
		return canonicalValue.compareTo(o.canonicalValue);
	}
}
