package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingTarget
{
	@NonNull String id;

	@NonNull String caption;

	//
	// New HU
	@Nullable HuPackingInstructionsId luPIId;

	//
	// Created HU
	@Nullable HuId huId;

	@Builder
	private PickingTarget(
			@NonNull final String caption,
			@Nullable final HuPackingInstructionsId luPIId,
			@Nullable final HuId huId)
	{
		this.caption = caption;

		if (huId != null)
		{
			this.luPIId = luPIId;
			this.huId = huId;
			this.id = "existing-" + huId.getRepoId();
		}
		else if (luPIId != null)
		{
			this.luPIId = luPIId;
			this.huId = null;
			this.id = "new-" + luPIId.getRepoId();
		}
		else
		{
			throw new AdempiereException("Invalid picking target");
		}
	}

	public static boolean equals(final PickingTarget o1, final PickingTarget o2) {return Objects.equals(o1, o2);}
}
