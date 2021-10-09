/*
 * #%L
 * de.metas.business
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

package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * A BOM useful for quantity calculations
 */
@Value
public class QtyCalculationsBOM
{
	@NonNull
	ImmutableList<QtyCalculationsBOMLine> lines;

	// References
	@Nullable
	PPOrderId orderId;

	@Builder
	private QtyCalculationsBOM(
			@NonNull @Singular final List<QtyCalculationsBOMLine> lines,
			//
			@Nullable final PPOrderId orderId)
	{
		this.lines = ImmutableList.copyOf(lines);
		this.orderId = orderId;
	}

	public ProductId getBomProductId()
	{
		if (lines.isEmpty())
		{
			throw new AdempiereException("No lines");
		}

		return lines.get(0).getBomProductId();
	}

	public  I_C_UOM getBomProductUOM()
	{
		if (lines.isEmpty())
		{
			throw new AdempiereException("No lines");
		}

		return lines.get(0).getBomProductUOM();
	}


	public QtyCalculationsBOMLine getLineByOrderBOMLineId(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		return lines.stream()
				.filter(line -> PPOrderBOMLineId.equals(line.getOrderBOMLineId(), orderBOMLineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No BOM line found for " + orderBOMLineId + " in " + this));
	}

	public Optional<QtyCalculationsBOMLine> getLineByComponentId(@NonNull final ProductId componentId)
	{
		return lines.stream()
				.filter(line -> ProductId.equals(line.getProductId(), componentId))
				.findFirst();
	}

}
