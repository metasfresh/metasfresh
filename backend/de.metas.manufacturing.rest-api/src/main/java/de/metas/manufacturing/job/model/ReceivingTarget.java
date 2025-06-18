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
		return builder().tuId(HuId.ofRepoId(tu.getM_HU_ID())).build();
	}
}
