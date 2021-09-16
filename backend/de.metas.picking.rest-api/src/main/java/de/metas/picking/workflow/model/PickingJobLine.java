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

package de.metas.picking.workflow.model;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.stream.Stream;

@Value
@Builder
public class PickingJobLine
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull ImmutableList<PickingJobStep> steps;

	Stream<ShipmentScheduleId> streamShipmentScheduleId()
	{
		return steps.stream().map(PickingJobStep::getShipmentScheduleId);
	}

	public PickingJobProgress getProgress()
	{
		int countDoneSteps = 0;
		int countNotDoneSteps = 0;
		for (final PickingJobStep step : steps)
		{
			if (step.isPickingDone())
			{
				countDoneSteps++;
			}
			else
			{
				countNotDoneSteps++;
			}
		}

		if (countDoneSteps <= 0)
		{
			return countNotDoneSteps <= 0
					? PickingJobProgress.FULLY_PICKED // corner case: when there are no steps because steps were invalid
					: PickingJobProgress.NOTHING_PICKED;
		}
		else // countDoneSteps > 0
		{
			return countNotDoneSteps <= 0
					? PickingJobProgress.FULLY_PICKED
					: PickingJobProgress.PARTIAL_PICKED;
		}
	}
}
