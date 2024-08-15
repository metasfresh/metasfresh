package de.metas.picking.rest_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStepPickFromHU
{
	@NonNull JsonDisplayableQRCode huQRCode;

	public static JsonPickingJobStepPickFromHU of(@NonNull final PickingJobStepPickedToHU pickedToHU)
	{
		return builder()
				.huQRCode(pickedToHU.getActualPickedHU().toQRCodeRenderedJson())
				.build();
	}
}
