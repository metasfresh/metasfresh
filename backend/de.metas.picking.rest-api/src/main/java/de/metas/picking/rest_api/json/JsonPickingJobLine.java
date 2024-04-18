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
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.i18n.ITranslatableString;
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
public class JsonPickingJobLine
{
	@NonNull String pickingLineId;
	@NonNull String productId;
	@NonNull String productNo;
	@NonNull String caption;
	@NonNull String uom;
	@NonNull BigDecimal qtyToPick;
	@Nullable String catchWeightUOM;
	@NonNull List<JsonPickingJobStep> steps;
	boolean allowPickingAnyHU;
	boolean manuallyClosed;

	public static JsonPickingJobLineBuilder builderFrom(
			@NonNull final PickingJobLine line,
			@NonNull final Function<UomId, ITranslatableString> getUOMSymbolById,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.pickingLineId(line.getId().getAsString())
				.productId(line.getProductId().getAsString())
				.productNo(line.getProductNo())
				.caption(line.getProductName().translate(adLanguage))
				.uom(line.getQtyToPick().getUOMSymbol())
				.qtyToPick(line.getQtyToPick().toBigDecimal())
				.catchWeightUOM(line.getCatchUomId() != null ? getUOMSymbolById.apply(line.getCatchUomId()).translate(adLanguage) : null)
				.steps(line.getSteps()
						.stream()
						.map(step -> JsonPickingJobStep.of(step, jsonOpts, getUOMSymbolById))
						.collect(ImmutableList.toImmutableList()))
				.manuallyClosed(line.isManuallyClosed());
	}
}
