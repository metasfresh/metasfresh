/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Delivery_Planning;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPlanningService
{
	private static final String SYSCONFIG_M_Delivery_Planning_CreateAutomatically = "de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically";
	private static final AdMessageKey MSG_M_Delivery_Planning_AtLeastOnePerOrderLine = AdMessageKey.of("M_Delivery_Planning_AtLeastOnePerOrderLine");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final DeliveryPlanningRepository deliveryPlanningRepository;

	public DeliveryPlanningService(@NonNull final DeliveryPlanningRepository deliveryPlanningRepository)
	{
		this.deliveryPlanningRepository = deliveryPlanningRepository;
	}

	public boolean autoCreateEnabled(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_M_Delivery_Planning_CreateAutomatically, false, clientAndOrgId);
	}

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	public void generateIncomingDeliveryPlanning(final I_M_ReceiptSchedule receiptScheduleRecord)
	{
		final DeliveryPlanningCreateRequest deliveryPlanningRequest = receiptScheduleBL.createDeliveryPlanningRequest(receiptScheduleRecord);
		deliveryPlanningRepository.generateDeliveryPlanning(deliveryPlanningRequest);
	}

	public void generateOutgoingDeliveryPlanning(final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final DeliveryPlanningCreateRequest deliveryPlanningRequest = shipmentScheduleBL.createDeliveryPlanningRequest(shipmentScheduleRecord);
		deliveryPlanningRepository.generateDeliveryPlanning(deliveryPlanningRequest);
	}

	public void validateDeletion(final I_M_Delivery_Planning deliveryPlanning)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// nothing to do: delivery planning is not based on any order line
			return;
		}

		final boolean otherDeliveryPlanningsExistForOrderLine = deliveryPlanningRepository.otherDeliveryPlanningsExistForOrderLine(orderLineId, DeliveryPlanningId.ofRepoIdOrNull(deliveryPlanning.getM_Delivery_Planning_ID()));

		if (!otherDeliveryPlanningsExistForOrderLine)
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AtLeastOnePerOrderLine);
		}
	}
}
