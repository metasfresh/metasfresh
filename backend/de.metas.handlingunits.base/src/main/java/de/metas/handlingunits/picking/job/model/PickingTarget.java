package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingTarget
{
	@NonNull String id;

	@NonNull String caption;

	//
	// New LU
	@Nullable HuPackingInstructionsId luPIId;

	//
	// Existing LU
	@Nullable HuId luId;

	@Builder
	private PickingTarget(
			@NonNull final String caption,
			@Nullable final HuPackingInstructionsId luPIId,
			@Nullable final HuId luId)
	{
		this.caption = caption;

		if (luId != null)
		{
			this.luPIId = null;
			this.luId = luId;
			this.id = "existing-" + luId.getRepoId();
		}
		else if (luPIId != null)
		{
			this.luPIId = luPIId;
			this.luId = null;
			this.id = "new-" + luPIId.getRepoId();
		}
		else
		{
			throw new AdempiereException("Invalid picking target");
		}
	}

	public static PickingTarget ofPackingInstructions(@NonNull final HuPackingInstructionsId luPIId, @NonNull final String caption)
	{
		return builder().luPIId(luPIId).caption(caption).build();
	}

	public static PickingTarget ofPackingInstructions(@NonNull final HuPackingInstructionsIdAndCaption luPI)
	{
		return builder().luPIId(luPI.getId()).caption(luPI.getCaption()).build();
	}

	public static PickingTarget ofExistingHU(@NonNull final HuId luId, @NonNull final String caption)
	{
		return builder().luId(luId).caption(caption).build();
	}

	public static boolean equals(final PickingTarget o1, final PickingTarget o2) {return Objects.equals(o1, o2);}

	public HuPackingInstructionsId getLuPIIdNotNull()
	{
		return Check.assumeNotNull(luPIId, "LU PI shall be set for {}", this);
	}

	public HuId getLuIdNotNull()
	{
		return Check.assumeNotNull(luId, "LU shall be set for {}", this);
	}

}
