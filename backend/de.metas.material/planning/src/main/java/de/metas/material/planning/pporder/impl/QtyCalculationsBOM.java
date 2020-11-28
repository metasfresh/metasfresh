package de.metas.material.planning.pporder.impl;

import com.google.common.collect.ImmutableList;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * A BOM useful for quantity calculations
 */
@Value
public class QtyCalculationsBOM
{
	ImmutableList<QtyCalculationsBOMLine> lines;

	// References
	@Getter
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

	public QtyCalculationsBOMLine getLineByOrderBOMLineId(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		return lines.stream()
				.filter(line -> PPOrderBOMLineId.equals(line.getOrderBOMLineId(), orderBOMLineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No BOM line found for " + orderBOMLineId + " in " + this));
	}
}
