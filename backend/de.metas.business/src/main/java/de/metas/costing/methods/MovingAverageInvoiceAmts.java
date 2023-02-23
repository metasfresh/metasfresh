/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.costing.methods;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostSegment;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MovingAverageInvoiceAmts
{
	@NonNull Money grir;

	@NonNull Money adjustmentProportion;
	@NonNull Money cogs;

	@NonNull CostElement costElement;

	@NonNull CostSegment costSegment;

	public MovingAverageInvoiceAmts add(@NonNull final MovingAverageInvoiceAmts amtToAdd)
	{
		final Money addedGrir = grir.add((amtToAdd.getGrir()));
		final Money addedCogs = cogs.add(amtToAdd.getCogs());
		final Money addedAdjProportion = adjustmentProportion.add(amtToAdd.getAdjustmentProportion());

		return MovingAverageInvoiceAmts.builder()
				.costSegment(costSegment)
				.costElement(costElement)
				.grir(addedGrir)
				.cogs(addedCogs)
				.adjustmentProportion(addedAdjProportion)
				.build();
	}

}
