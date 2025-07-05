package de.metas.manufacturing.job.model;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class ReceivingTarget
{
	@Nullable HUPIItemProductId tuPIItemProductId;
	@Nullable HuId tuId;
	@Nullable HuId luId;

	@Builder
	private ReceivingTarget(
			@Nullable final HUPIItemProductId tuPIItemProductId,
			@Nullable final HuId tuId,
			@Nullable final HuId luId)
	{
		// A HU ID shall be specified
		if (tuId == null && luId == null)
		{
			throw new AdempiereException("At least one of tuId and luId must be set");
		}

		// A way to identify a TU to receive shall be specified
		if (tuId == null && tuPIItemProductId == null)
		{
			throw new AdempiereException("At least one of tuId and tuPIItemProductId must be set");
		}

		this.tuPIItemProductId = tuPIItemProductId;
		this.tuId = tuId;
		this.luId = luId;
	}

	public static ReceivingTarget ofExistingTU(@NonNull final I_M_HU tu)
	{
		return ofExistingTUId(HuId.ofRepoId(tu.getM_HU_ID()));
	}

	public static ReceivingTarget ofExistingTUId(@NonNull final HuId tuId)
	{
		return builder().tuId(tuId).build();
	}

	public static ReceivingTarget ofExistingLU(@NonNull final I_M_HU lu, @NonNull final HUPIItemProductId tuPIItemProductId)
	{
		return ReceivingTarget.builder()
				.luId(HuId.ofRepoId(lu.getM_HU_ID()))
				.tuPIItemProductId(tuPIItemProductId)
				.build();
	}

}
