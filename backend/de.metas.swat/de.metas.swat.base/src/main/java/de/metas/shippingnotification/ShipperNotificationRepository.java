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

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.shippingnotification.model.X_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Delivery_Planning;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class ShipperNotificationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public static ShippingNotification fromRecord(@NonNull final I_M_Shipping_Notification record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		return ShippingNotification.builder()
				.shippingNotificationId(ShippingNotificationId.ofRepoId(record.getM_Shipping_Notification_ID()))
				.orgId(orgId)
				.physicalClearanceDate(record.getPhysicalClearanceDate().toInstant())
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.build();
	}

	@NonNull
	public ShippingNotification getById(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		final I_M_Shipping_Notification record = InterfaceWrapperHelper.load(shippingNotificationId, I_M_Shipping_Notification.class);
		if (record == null)
		{
			throw new AdempiereException("No record found for " + shippingNotificationId);
		}
		return fromRecord(record);
	}

	public OrderId getOrderId(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		final int orderId = queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID, shippingNotificationId)
				.andCollect(I_M_Shipping_NotificationLine.COLUMNNAME_C_OrderLine_ID, I_C_OrderLine.class)
				.andCollect(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_Order.class)
				.create()
				.firstNotNull(I_C_Order.class)
				.getC_Order_ID();

		return OrderId.ofRepoId(orderId);
	}

	public Stream<I_M_Shipping_Notification> retrieveForOrder(@NonNull final OrderId orderId)
	{

		final IQuery<I_C_OrderLine> orderLinesQuery = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.create();

		final IQuery<I_M_Shipping_NotificationLine> notificationLinesQuery = queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Shipping_NotificationLine.COLUMNNAME_C_OrderLine_ID, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID,
									 orderLinesQuery)
				.create();

		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addInSubQueryFilter(I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID, I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID,
									 notificationLinesQuery)
				.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_DocStatus, X_M_Shipping_Notification.DOCSTATUS_Completed, X_M_Shipping_Notification.DOCSTATUS_Closed)
				.create()
				.stream(I_M_Shipping_Notification.class)
				;
	}

	public List<I_M_Shipping_NotificationLine> retrieveLines(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addEqualsFilter(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID, shippingNotificationId)
				.create()
				.list(I_M_Shipping_NotificationLine.class)
				;

	}

	public I_M_Shipping_Notification generateDeliveryInstruction(@NonNull final OrderId orderId)
	{
		final I_C_Order orderRecord = orderDAO.getById(orderId);

		final I_M_Shipping_Notification shippingNotificationRecord = newInstance(I_M_Shipping_Notification.class);
		shippingNotificationRecord.setAD_Org_ID(orderRecord.getAD_Org_ID());
		shippingNotificationRecord.setC_DocType_ID(getDocTypeId(orderRecord.getAD_Client_ID(), orderRecord.getAD_Org_ID()).getRepoId());
		shippingNotificationRecord.setC_BPartner_ID(orderRecord.getC_BPartner_ID());
		shippingNotificationRecord.setC_BPartner_Location_ID(orderRecord.getC_BPartner_Location_ID());
		shippingNotificationRecord.setAD_User_ID(orderRecord.getAD_User_ID());
		shippingNotificationRecord.setC_Auction_ID(orderRecord.getC_Auction_ID());
		shippingNotificationRecord.setM_Warehouse_ID(orderRecord.getM_Warehouse_ID());
		shippingNotificationRecord.setM_Locator_ID(orderRecord.getM_Locator_ID());
		shippingNotificationRecord.setC_Harvesting_Calendar_ID(orderRecord.getC_Harvesting_Calendar_ID());
		shippingNotificationRecord.setHarvesting_Year_ID(orderRecord.getHarvesting_Year_ID());
		shippingNotificationRecord.setPOReference(orderRecord.getPOReference());
		shippingNotificationRecord.setDescription(orderRecord.getDescription());

		save(shippingNotificationRecord);

		return shippingNotificationRecord;
	}

	public Iterator<de.metas.I_C_Order.I_M_ShipmentSchedule> extractDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	@NonNull
	private DocTypeId getDocTypeId(final int ad_client_id, final int ad_org_id)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(DocBaseType.ShippingNotification)
				.adClientId(ad_client_id)
				.adOrgId(ad_org_id)
				.build();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeId == null)
		{
			throw new DocTypeNotFoundException(docTypeQuery);
		}
		return docTypeId;
	}
}
