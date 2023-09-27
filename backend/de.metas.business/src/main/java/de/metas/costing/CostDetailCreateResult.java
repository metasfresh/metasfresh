package de.metas.costing;

import de.metas.costing.methods.CostAmountAndQtyDetailed;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostAmountType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class CostDetailCreateResult
{
	@NonNull CostSegment costSegment;
	@NonNull CostElement costElement;
	@NonNull @With @Getter CostAmountAndQtyDetailed amtAndQty;

	@Builder
	private CostDetailCreateResult(
			@NonNull final CostSegment costSegment,
			@NonNull final CostElement costElement,
			@NonNull final CostAmountAndQtyDetailed amtAndQty)
	{
		this.costSegment = costSegment;
		this.costElement = costElement;
		this.amtAndQty = amtAndQty;
	}

	public CostAmountAndQty getAmtAndQty(@NonNull final CostAmountType type) {return amtAndQty.getAmtAndQty(type);}

	public CostAmountDetailed getAmt() {return amtAndQty.getAmt();}
}
