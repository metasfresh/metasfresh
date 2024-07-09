/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.plan.model;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.AlternativePickFromsList;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;

/**
 * Contains a plan about how a picker can precisely pick.
 */
@Value
@Builder(toBuilder = true)
public class PickingPlan
{
	@NonNull @Singular ImmutableList<PickingPlanLine> lines;

	@NonNull AlternativePickFromsList alternatives;

	public ProductId getSingleProductId()
	{
		return lines.stream()
				.map(PickingPlanLine::getProductId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Expected the plan to contain only one product")));
	}

	public Quantity getQtyToPick()
	{
		return lines.stream()
				.map(PickingPlanLine::getQty)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No QtyToPick found in " + lines));
	}

	@NonNull
	public ImmutableList<HuId> getSortedPickFromTopLevelHUIds()
	{
		return lines.stream()
				.map(PickingPlanLine::getPickFromHU)
				.filter(Objects::nonNull)
				.map(PickFromHU::getTopLevelHUId)
				.collect(ImmutableList.toImmutableList());
	}
}
