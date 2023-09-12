/*
 * #%L
 * de.metas.swat.base
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

package de.metas.shippingnotification;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.OrgId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Service
public class ShippingNotificationService
{
	private final ShippingNotificationRepository shipperNotificationRepository;
	private final DocTypeService docTypeService;

	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	public ShippingNotificationService(
			@NonNull final ShippingNotificationRepository shipperNotificationRepository,
			@NonNull final DocTypeService docTypeService)
	{
		this.shipperNotificationRepository = shipperNotificationRepository;
		this.docTypeService = docTypeService;
	}

	public boolean isCompletedDocument(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return shipperNotificationRepository.getById(shippingNotificationId).getDocStatus().isCompleted();
	}

	public I_M_Shipping_Notification generateShippingNotificationAndPropagatePhysicalClearanceDate(@NonNull final ShippingNotificationCreateRequest request)
	{

		final I_M_Shipping_Notification shippingNotificationRecord = shipperNotificationRepository.createAndSaveShippingNotification(request);

		// shipperNotificationRepository.createAndSaveShippingNotificationLine();

		updatetPhysicalClearanceDateToOrder(shippingNotificationRecord);

		return shippingNotificationRecord;
	}

	// private void createShippingNotificationLines(@NonNull I_M_Shipping_Notification shippingNotificationRecord)
	// {
	//
	// 		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesByIds = shipmentSchedulePA.getByIds(shipmentSchedulePA.retrieveScheduleIdsByOrderId(orderId), de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	//
	// 		final ImmutableSet<I_M_ShipmentSchedule> shipmentSchedules = ImmutableSet.copyOf(shipmentSchedulesByIds.values());
	//
	// 		shipmentSchedules.forEach(shipmentSchedule ->
	// 								  {
	// 									  createAndSaveShippingNotificationLine(shipmentSchedule, record);
	// 									  updatetPhysicalClearanceDateToShipmentSchedule(shipmentSchedule, record);
	// 								  });
	// }



	private void updatetPhysicalClearanceDateToShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final I_M_Shipping_Notification shippingNotification)
	{
		// shipmentSchedule.setPhysicalClearanceDate(shippingNotification.getPhysicalClearanceDate());
		shipmentSchedulePA.save(shipmentSchedule);
	}

	private void updatetPhysicalClearanceDateToOrder(@NonNull final I_M_Shipping_Notification shippingNotification)
	{
		final I_C_Order orderRecord = orderDAO.getById(OrderId.ofRepoId(shippingNotification.getC_Order_ID()));
		orderRecord.setPhysicalClearanceDate(shippingNotification.getPhysicalClearanceDate());
		orderDAO.save(orderRecord);
	}

}
