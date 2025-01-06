package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
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
import java.util.List;
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

	@NonNull List<JsonPickingJobStepPickFromHU> actuallyPickedHUs;

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
			final QtyRejectedWithReason qtyRejected = pickedTo.getQtyRejected();

			builder.qtyPicked(pickedTo.getQtyPicked().toBigDecimal())
					.qtyRejected(qtyRejected != null ? qtyRejected.toBigDecimal() : null)
					.qtyRejectedReasonCode(qtyRejected != null ? qtyRejected.getReasonCode().getCode() : null)
					.pickedCatchWeight(toJsonQuantity(pickedTo.getCatchWeight(), jsonOpts, getUOMSymbolById))
					.actuallyPickedHUs(pickedTo.stream()
							.map(JsonPickingJobStepPickFromHU::of)
							.collect(ImmutableList.toImmutableList()));
		}
		else
		{
			builder.actuallyPickedHUs(ImmutableList.of());
		}

		return builder.build();
	}

	@Nullable
	private static JsonQuantity toJsonQuantity(
			@Nullable final Quantity qty,
			@NonNull final JsonOpts jsonOpts,
			@NonNull final Function<UomId, ITranslatableString> getUOMSymbolById)
	{
		if (qty == null)
		{
			return null;
		}

		return JsonQuantity.builder()
				.qty(qty.toBigDecimal())
				.uomCode(qty.getUOM().getX12DE355())
				.uomSymbol(getUOMSymbolById.apply(qty.getUomId()).translate(jsonOpts.getAdLanguage()))
				.build();
	}
}
