package de.metas.picking.rest_api.json;

import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStepPickFrom
{
	@NonNull String alternativeId;
	@NonNull String locatorName;
	@NonNull JsonDisplayableQRCode huQRCode;
	@NonNull BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable String qtyRejectedReasonCode;
	@Nullable JsonQuantity pickedCatchWeight;

	public static JsonPickingJobStepPickFrom of(
			final PickingJobStepPickFrom pickFrom,
			@NonNull final JsonOpts jsonOpts,
			@NonNull final Function<UomId, ITranslatableString> getUOMSymbolById)
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

			if (pickedTo.getCatchWeight() != null)
			{
				final Quantity catchWeight = pickedTo.getCatchWeight();

				builder.pickedCatchWeight(JsonQuantity.builder()
												  .qty(catchWeight.toBigDecimal())
												  .uomCode(catchWeight.getUOM().getX12DE355())
												  .uomSymbol(getUOMSymbolById.apply(catchWeight.getUomId()).translate(jsonOpts.getAdLanguage()))
												  .build());
			}
		}

		return builder.build();
	}
}
