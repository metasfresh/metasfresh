/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.invoicecandidate;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.order.invoicecandidate.OrderLineHandlerExtension;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class DeliveryPlanningICHandlerExtension implements OrderLineHandlerExtension
{
	@NonNull
	private final DeliveryPlanningService deliveryPlanningService;

	@Override
	public void setDeliveryRelatedData(@NonNull final OrderLineId orderLineId, @NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final Timestamp actualLoadingDate = deliveryPlanningService.getMinActualLoadingDateFromPlanningsWithCompletedInstructions(orderLineId)
				.orElse(null);

		invoiceCandidate.setActualLoadingDate(actualLoadingDate);
	}
}
