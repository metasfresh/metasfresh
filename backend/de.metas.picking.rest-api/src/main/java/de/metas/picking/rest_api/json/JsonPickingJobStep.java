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

import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStep
{
	@NonNull String pickingStepId;

	@NonNull String productName;
	@NonNull String locatorName;
	@NonNull String huBarcode;
	@NonNull String uom;
	@NonNull BigDecimal qtyToPick;
	@NonNull BigDecimal qtyPicked;

	public static JsonPickingJobStep of(final PickingJobStep step, final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.pickingStepId(step.getId().getAsString())
				.productName(step.getProductName().translate(adLanguage))
				.locatorName(step.getLocatorName())
				.huBarcode(step.getHuBarcode().getAsString())
				.uom(step.getQtyToPick().getUOMSymbol())
				.qtyToPick(step.getQtyToPick().toBigDecimal())
				.qtyPicked(step.getQtyPicked().toBigDecimal())
				.build();
	}
}
