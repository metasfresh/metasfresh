/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.util.CatchWeightLoader;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.rest_api.json.JsonPickFromAlternative;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingJobStep;
import de.metas.picking.rest_api.json.JsonPickingJobStepPickFrom;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
class JsonPickingJobFactory
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final CatchWeightLoader catchWeightLoader;
	@NonNull private final JsonOpts jsonOpts;

	public JsonPickingJob toJsonPickingJob(@NonNull final PickingJob pickingJob)
	{
		return JsonPickingJob.builder()
				.lines(pickingJob.getLines()
						.stream()
						.map(line -> toJsonPickingJobLine(line)
								.allowPickingAnyHU(pickingJob.isAllowPickingAnyHU())
								.build())
						.collect(ImmutableList.toImmutableList()))
				.pickFromAlternatives(pickingJob.getPickFromAlternatives()
						.stream()
						.map(JsonPickFromAlternative::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonPickingJobLine.JsonPickingJobLineBuilder toJsonPickingJobLine(@NonNull final PickingJobLine line)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return JsonPickingJobLine.builder()
				.pickingLineId(line.getId().getAsString())
				.productId(line.getProductId().getAsString())
				.caption(line.getProductName().translate(adLanguage))
				.uom(line.getQtyToPick().getUOMSymbol())
				.qtyToPick(line.getQtyToPick().toBigDecimal())
				.catchWeightUOM(line.getCatchUomId() != null ? getUOMSymbolById(line.getCatchUomId()).translate(adLanguage) : null)
				.steps(line.getSteps()
						.stream()
						.map(this::toJsonPickingJobStep)
						.collect(ImmutableList.toImmutableList()));
	}

	private JsonPickingJobStep toJsonPickingJobStep(final PickingJobStep step)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		final JsonPickingJobStepPickFrom mainPickFrom = toJsonPickingJobStepPickFrom(step.getPickFrom(PickingJobStepPickFromKey.MAIN));

		final ImmutableMap<String, JsonPickingJobStepPickFrom> pickFromAlternatives = step.getPickFromKeys()
				.stream()
				.filter(PickingJobStepPickFromKey::isAlternative)
				.map(step::getPickFrom)
				.map(this::toJsonPickingJobStepPickFrom)
				.collect(ImmutableMap.toImmutableMap(JsonPickingJobStepPickFrom::getAlternativeId, alt -> alt));

		return JsonPickingJobStep.builder()
				.pickingStepId(step.getId().getAsString())
				.productId(step.getProductId().getAsString())
				.productName(step.getProductName().translate(adLanguage))
				.uom(step.getQtyToPick().getUOMSymbol())
				.qtyToPick(step.getQtyToPick().toBigDecimal())
				//
				// Main PickFrom
				.mainPickFrom(mainPickFrom)
				//
				// PickFrom Alternatives
				.pickFromAlternatives(pickFromAlternatives)
				//
				.build();
	}

	private JsonPickingJobStepPickFrom toJsonPickingJobStepPickFrom(final PickingJobStepPickFrom pickFrom)
	{
		final JsonPickingJobStepPickFrom.JsonPickingJobStepPickFromBuilder builder = JsonPickingJobStepPickFrom.builder()
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

			catchWeightLoader.getCatchWeight(pickedTo)
					.map(this::toJsonQuantity)
					.ifPresent(builder::pickedCatchWeight);
		}

		return builder.build();
	}

	private JsonQuantity toJsonQuantity(final Quantity qty)
	{
		return JsonQuantity.builder()
				.qty(qty.toBigDecimal())
				.uomCode(qty.getUOM().getX12DE355())
				.uomSymbol(getUOMSymbolById(qty.getUomId()).translate(jsonOpts.getAdLanguage()))
				.build();
	}

	@NonNull
	private ITranslatableString getUOMSymbolById(@Nullable final UomId uomId) {return uomId != null ? uomDAO.getUOMSymbolById(uomId) : TranslatableStrings.empty();}

}
