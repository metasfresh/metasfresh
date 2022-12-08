package de.metas.manufacturing.job.model;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import lombok.Builder;
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
}
