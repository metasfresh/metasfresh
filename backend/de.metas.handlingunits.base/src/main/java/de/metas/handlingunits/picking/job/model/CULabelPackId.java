package de.metas.handlingunits.picking.job.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/** The pack ID a CU label carries (scan-each-CU mode): the text that tells one CU of a product and lot from another */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CULabelPackId
{
	@NonNull String value;

	/** A pack ID with surrounding blanks (e.g. a scanner's trailing blank) is the same pack: it is trimmed, so the duplicate check sees it; a blank one is no pack ID */
	@Nullable
	public static CULabelPackId ofNullable(@Nullable final String value)
	{
		final String trimmed = value != null ? value.trim() : "";
		return trimmed.isEmpty() ? null : new CULabelPackId(trimmed);
	}

	/** Like {@link #ofNullable(String)}, but a blank value is refused */
	@NonNull
	public static CULabelPackId ofString(@NonNull final String value)
	{
		final CULabelPackId packId = ofNullable(value);
		if (packId == null)
		{
			throw new IllegalArgumentException("A pack ID must not be blank");
		}
		return packId;
	}

	@Override
	public String toString() {return value;}
}
