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
	public static CULabelPackId ofNullable(@Nullable final String value)
	{
		return value != null ? new CULabelPackId(value) : null;
	}

	public static CULabelPackId ofString(@NonNull final String value)
	{
		return new CULabelPackId(value);
	}

	@Override
	public String toString() {return value;}
}
