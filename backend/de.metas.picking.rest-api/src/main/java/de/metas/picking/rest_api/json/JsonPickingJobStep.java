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

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.i18n.ITranslatableString;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStep
{
	@NonNull String pickingStepId;
	@NonNull JsonCompleteStatus completeStatus;

	@NonNull String productId;
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToPick;

	//
	// Main PickFrom
	@NonNull JsonPickingJobStepPickFrom mainPickFrom;

	//
	// PickFrom alternatives
	@NonNull Map<String, JsonPickingJobStepPickFrom> pickFromAlternatives;

	public static JsonPickingJobStep of(
			final PickingJobStep step,
			final JsonOpts jsonOpts,
			@NonNull final Function<UomId, ITranslatableString> getUOMSymbolById)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		final JsonPickingJobStepPickFrom mainPickFrom = JsonPickingJobStepPickFrom.of(
				step.getPickFrom(PickingJobStepPickFromKey.MAIN), jsonOpts, getUOMSymbolById);

		final ImmutableMap<String, JsonPickingJobStepPickFrom> pickFromAlternatives = step.getPickFromKeys()
				.stream()
				.filter(PickingJobStepPickFromKey::isAlternative)
				.map(step::getPickFrom)
				.map(pickFrom -> JsonPickingJobStepPickFrom.of(pickFrom, jsonOpts, getUOMSymbolById))
				.collect(ImmutableMap.toImmutableMap(JsonPickingJobStepPickFrom::getAlternativeId, alt -> alt));

		return builder()
				.pickingStepId(step.getId().getAsString())
				.completeStatus(JsonCompleteStatus.of(step.getProgress()))
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
}
