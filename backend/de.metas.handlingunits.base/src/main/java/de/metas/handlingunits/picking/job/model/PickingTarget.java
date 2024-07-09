package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
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

	public static PickingTarget ofExistingHU(@NonNull final HuId luId, @NonNull final HUQRCode qrCode)
	{
		return builder().luId(luId).caption(qrCode.toDisplayableQRCode()).build();
	}

	public static boolean equals(final PickingTarget o1, final PickingTarget o2) {return Objects.equals(o1, o2);}

	public boolean isExistingLU() {return luId != null;}

	public boolean isNewLU() {return luId == null && luPIId != null;}

	public HuPackingInstructionsId getLuPIIdNotNull()
	{
		return Check.assumeNotNull(luPIId, "LU PI shall be set for {}", this);
	}

	public HuId getLuIdNotNull()
	{
		return Check.assumeNotNull(luId, "LU shall be set for {}", this);
	}

	public interface CaseConsumer
	{
		void noLU();

		void newLU(final HuPackingInstructionsId luPackingInstructionsId);

		void existingLU(final HuId luId);
	}

	public interface CaseMapper<T>
	{
		T noLU();

		T newLU(final HuPackingInstructionsId luPackingInstructionsId);

		T existingLU(final HuId luId);
	}

	public static void apply(@Nullable final PickingTarget target, @NonNull final CaseConsumer consumer)
	{
		if (target == null)
		{
			consumer.noLU();
		}
		else if (target.isNewLU())
		{
			consumer.newLU(target.getLuPIIdNotNull());
		}
		else if (target.isExistingLU())
		{
			consumer.existingLU(target.getLuIdNotNull());
		}
		else
		{
			throw new AdempiereException("Unsupported target type: " + target);
		}
	}

	public static <T> T apply(@Nullable final PickingTarget target, @NonNull final CaseMapper<T> mapper)
	{
		if (target == null)
		{
			return mapper.noLU();
		}
		else if (target.isNewLU())
		{
			return mapper.newLU(target.getLuPIIdNotNull());
		}
		else if (target.isExistingLU())
		{
			return mapper.existingLU(target.getLuIdNotNull());
		}
		else
		{
			throw new AdempiereException("Unsupported target type: " + target);
		}
	}

}
