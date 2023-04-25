package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStepPickFrom
{
	@NonNull String alternativeId;
	@NonNull String locatorName;
	@NonNull JsonRenderedHUQRCode huQRCode;
	@NonNull BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable String qtyRejectedReasonCode;

	public static JsonPickingJobStepPickFrom of(final PickingJobStepPickFrom pickFrom)
	{
		final JsonPickingJobStepPickFromBuilder builder = builder()
				.alternativeId(pickFrom.getPickFromKey().getAsString())
				.locatorName(pickFrom.getPickFromLocator().getCaption())
				.huQRCode(pickFrom.getPickFromHU().getQrCode().toRenderedJson())
				.qtyPicked(BigDecimal.ZERO);

		final PickingJobStepPickedTo pickedTo = pickFrom.getPickedTo();
		if (pickedTo != null)
		{
			builder.qtyPicked(pickedTo.getQtyPicked().toBigDecimal());

			final QtyRejectedWithReason qtyRejected = pickedTo.getQtyRejected();
			if (qtyRejected != null)
			{
				builder.qtyRejected(qtyRejected.toBigDecimal());
				builder.qtyRejectedReasonCode(qtyRejected.getReasonCode().getCode());
			}
		}

		return builder.build();
	}
}
