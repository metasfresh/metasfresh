package de.metas.handlingunits.picking.job.model;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/** The pack ID a CU label carries (scan-each-CU mode): the text that tells one CU of a product and lot from another */
@Value
public class CULabelPackId
{
	@NonNull String value;

	@Nullable
	/** A pack ID with surrounding blanks (e.g. a scanner's trailing blank) is the same pack: it is trimmed, so the duplicate check sees it; a blank one is no pack ID */
	public static CULabelPackId ofNullable(@Nullable final String value)
	{
		final String trimmed = value != null ? value.trim() : "";
		return trimmed.isEmpty() ? null : new CULabelPackId(trimmed);
	}

	public static CULabelPackId ofString(@NonNull final String value)
	{
		return new CULabelPackId(value);
	}

	@Override
	public String toString() {return value;}
}
