package de.metas.tourplanning.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;

import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.api.ITourDAO;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.TourId;
import de.metas.util.Check;
import de.metas.util.Services;

public class M_ShippingPackage_CreateFromTourplanning extends JavaProcess implements IProcessPrecondition
{
	//
	// Services
	private final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

	private final PurchaseOrderToShipperTransportationRepository orderToShipperTransportationRepo = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationRepository.class);

	@Param(parameterName = I_M_Tour.COLUMNNAME_M_Tour_ID)
	private TourId p_M_Tour_ID;

	private static final String CreateFromPickingSlots_MSG_DOC_PROCESSED = "CreateFromPickingSlots_Msg_Doc_Processed";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		// task 06058: if the document is processed, we not allowed to run this process
		final I_M_ShipperTransportation shipperTransportation = context.getSelectedModel(I_M_ShipperTransportation.class);
		return ProcessPreconditionsResolution.acceptIf(!shipperTransportation.isProcessed());
	}

	@Override
	protected String doIt() throws Exception
	{
		final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(getRecord_ID());

		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(shipperTransportationId);

		Check.assumeNotNull(shipperTransportation, "shipperTransportation not null");

		//
		// If the document is processed, we not allowed to run this process (06058)
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException("@" + CreateFromPickingSlots_MSG_DOC_PROCESSED + "@");
		}

		final I_M_Tour tour = Services.get(ITourDAO.class).getById(p_M_Tour_ID);
		Check.assumeNotNull(tour, "tour not null");

		//
		// Fetch delivery days
		final Timestamp deliveryDate = shipperTransportation.getDateDoc();
		final List<I_M_DeliveryDay> deliveryDays = deliveryDayDAO.retrieveDeliveryDays(tour, deliveryDate);
		if (deliveryDays.isEmpty())
		{
			// No delivery days for tour;
			return "OK";
		}

		// used to make sure no order is added more than once
		final Map<Integer, I_C_Order> orderId2order = new LinkedHashMap<>();

		// we shall allow only those partner which are set in delivery days and that are vendors and have purchase orders
		for (final I_M_DeliveryDay dd : deliveryDays)
		{
			if (!dd.isToBeFetched())
			{
				continue; // nothing to do
			}

			// skip generic delivery days
			final I_C_BPartner_Location bpLocation = dd.getC_BPartner_Location();
			if (bpLocation == null)
			{
				continue;
			}

			final List<I_C_Order> purchaseOrders = orderDAO.retrievePurchaseOrdersForPickup(bpLocation, dd.getDeliveryDate(), dd.getDeliveryDateTimeMax());
			if (purchaseOrders.isEmpty())
			{
				continue; // nothing to do
			}

			for (final I_C_Order purchaseOrder : purchaseOrders)
			{
				orderId2order.put(purchaseOrder.getC_Order_ID(), purchaseOrder);
			}
		}

		//
		// Iterate collected orders and create shipment packages for them
		for (final I_C_Order order : orderId2order.values())
		{
			orderToShipperTransportationRepo.addPurchaseOrderToShipperTransportation(
					OrderId.ofRepoId(order.getC_Order_ID()),
					ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));
		}

		return "OK";
	}

}
