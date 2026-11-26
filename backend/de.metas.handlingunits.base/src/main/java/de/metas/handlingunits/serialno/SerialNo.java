package de.metas.handlingunits.serialno;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * Immutable value object for a single serial number captured during picking.
 * <p>
 * A serial is a standalone barcode string (no GS1 parsing): trimmed, non-blank.
 */
@Value
public class SerialNo implements Comparable<SerialNo>
{
	@NonNull String value;

	@JsonCreator
	@NonNull
	public static SerialNo ofString(@NonNull final String value)
	{
		final String trimmed = StringUtils.trimBlankToNull(value);
		if (trimmed == null)
		{
			throw new AdempiereException("Invalid serial number (blank): `" + value + "`");
		}

		return new SerialNo(trimmed);
	}

	@Nullable
	public static SerialNo ofNullableString(@Nullable final String value)
	{
		final String trimmed = StringUtils.trimBlankToNull(value);
		return trimmed != null ? new SerialNo(trimmed) : null;
	}

	/** @deprecated use {@link #getValueAsString()} — {@code toString()} is for debugging only. */
	@Override
	@Deprecated
	public String toString() {return value;}

	@JsonValue
	@NonNull
	public String getValueAsString()
	{
		return value;
	}

	@Override
	public int compareTo(@NonNull final SerialNo o)
	{
		return value.compareTo(o.value);
	}
}
