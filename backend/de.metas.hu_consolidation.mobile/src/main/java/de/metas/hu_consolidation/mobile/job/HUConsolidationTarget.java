package de.metas.hu_consolidation.mobile.job;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class HUConsolidationTarget
{
	@NonNull String id;

	@NonNull String caption;

	//
	// New LU
	@Nullable HuPackingInstructionsId luPIId;

	//
	// Existing LU
	@Nullable HuId luId;
	@Nullable HUQRCode luQRCode;

	@Builder
	private HUConsolidationTarget(
			@NonNull final String caption,
			@Nullable final HuPackingInstructionsId luPIId,
			@Nullable final HuId luId,
			@Nullable final HUQRCode luQRCode)
	{
		this.caption = caption;

		if (luId != null)
		{
			this.luPIId = null;
			this.luId = luId;
			this.luQRCode = luQRCode;
			this.id = "existing-" + luId.getRepoId();
		}
		else if (luPIId != null)
		{
			this.luPIId = luPIId;
			this.luId = null;
			this.luQRCode = null;
			this.id = "new-" + luPIId.getRepoId();
		}
		else
		{
			throw new AdempiereException("Invalid HU consolidation target");
		}
	}

	public static HUConsolidationTarget ofPI(@NonNull final HuPackingInstructionsIdAndCaption luPIAndCaption)
	{
		return builder()
				.caption(luPIAndCaption.getCaption())
				.luPIId(luPIAndCaption.getId())
				.build();
	}

}
