/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.ean13.EAN13ProductCode;
import de.metas.handlingunits.picking.job.model.CurrentPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.i18n.ITranslatableString;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.JsonQRCode;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonPickingJobLine
{
	@NonNull String pickingLineId;
	@NonNull String productId;
	@NonNull String productNo;
	@Nullable EAN13ProductCode ean13ProductCode;
	@NonNull String caption;

	@Nullable JsonQRCode pickingSlot;
	@Nullable JsonLUPickingTarget luPickingTarget;
	@Nullable JsonTUPickingTarget tuPickingTarget;

	@NonNull PickingUnit pickingUnit;
	@NonNull String packingItemName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToPick;
	@NonNull BigDecimal qtyPicked;
	@NonNull BigDecimal qtyRejected;
	@NonNull BigDecimal qtyPickedOrRejected;
	@NonNull BigDecimal qtyRemainingToPick;
	@Nullable String catchWeightUOM;
	@Nullable JsonPickFromManufacturingOrder pickFromManufacturingOrder;
	@NonNull List<JsonPickingJobStep> steps;
	boolean allowPickingAnyHU;
	@NonNull JsonCompleteStatus completeStatus;
	boolean manuallyClosed;
	@NonNull String displayGroupKey;
	@Nullable String salesOrderDocumentNo;
	int orderLineSeqNo;

	public static JsonPickingJobLineBuilder builderFrom(
			@NonNull final PickingJobLine line,
			@NonNull final Function<UomId, ITranslatableString> getUOMSymbolById,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		final String uom;
		final BigDecimal qtyToPick;
		final BigDecimal qtyPicked;
		final BigDecimal qtyRejected;
		final BigDecimal qtyRemainingToPick;
		final PickingUnit pickingUnit = line.getPickingUnit();
		if (pickingUnit.isTU())
		{
			uom = "TU";
			qtyRemainingToPick = Objects.requireNonNull(line.getQtyRemainingToPickTUs()).toBigDecimal();
			qtyToPick = Objects.requireNonNull(line.getQtyToPickTUs()).toBigDecimal();
			qtyPicked = Objects.requireNonNull(line.getQtyPickedTUs()).toBigDecimal();
			qtyRejected = Objects.requireNonNull(line.getQtyRejectedTUs()).toBigDecimal();
		}
		else
		{
			uom = line.getQtyToPick().getUOMSymbol();
			qtyToPick = line.getQtyToPick().toBigDecimal();
			qtyPicked = line.getQtyPicked().toBigDecimal();
			qtyRejected = line.getQtyRejected().toBigDecimal();
			qtyRemainingToPick = line.getQtyRemainingToPick().toBigDecimal();
		}

		final CurrentPickingTarget currentPickingTarget = line.getCurrentPickingTarget();

		return builder()
				.pickingLineId(line.getId().getAsString())
				.productId(line.getProductId().getAsString())
				.productNo(line.getProductNo())
				.ean13ProductCode(line.getEan13ProductCode())
				.caption(line.getCaption().translate(adLanguage))
				.pickingSlot(currentPickingTarget.getPickingSlot().map(JsonPickingJobLine::toJsonQRCode).orElse(null))
				.luPickingTarget(currentPickingTarget.getLuPickingTarget().map(JsonLUPickingTarget::of).orElse(null))
				.tuPickingTarget(currentPickingTarget.getTuPickingTarget().map(JsonTUPickingTarget::of).orElse(null))
				.pickingUnit(pickingUnit)
				.packingItemName(line.getPackingInfo().getName().translate(adLanguage))
				.uom(uom)
				.qtyToPick(qtyToPick)
				.qtyPicked(qtyPicked)
				.qtyRejected(qtyRejected)
				.qtyPickedOrRejected(qtyPicked.add(qtyRejected))
				.qtyRemainingToPick(qtyRemainingToPick)
				.catchWeightUOM(line.getCatchUomId() != null ? getUOMSymbolById.apply(line.getCatchUomId()).translate(adLanguage) : null)
				.pickFromManufacturingOrder(line.getPickFromManufacturingOrderId() != null
						? JsonPickFromManufacturingOrder.ofPPOrderId(line.getPickFromManufacturingOrderId())
						: null)
				.steps(line.getSteps()
						.stream()
						.map(step -> JsonPickingJobStep.of(step, jsonOpts, getUOMSymbolById))
						.collect(ImmutableList.toImmutableList()))
				.completeStatus(JsonCompleteStatus.of(line.getProgress()))
				.manuallyClosed(line.isManuallyClosed())
				.salesOrderDocumentNo(line.getSalesOrderDocumentNo())
				.orderLineSeqNo(line.getOrderLineSeqNo())
				;
	}

	public static JsonQRCode toJsonQRCode(final PickingSlotIdAndCaption pickingSlotIdAndCaption)
	{
		return JsonQRCode.builder()
				.qrCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption).toGlobalQRCodeJsonString())
				.caption(pickingSlotIdAndCaption.getCaption())
				.build();
	}

}
