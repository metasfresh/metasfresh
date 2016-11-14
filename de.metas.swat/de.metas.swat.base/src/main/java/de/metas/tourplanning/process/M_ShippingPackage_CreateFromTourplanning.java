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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Shipper;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.service.IOrderDAO;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;

public class M_ShippingPackage_CreateFromTourplanning extends SvrProcess implements ISvrProcessPrecondition
{
	//
	// Services
	private final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private int p_M_ShipperTransportation_ID = -1;

	public static final String PARAM_M_Tour_ID = "M_Tour_ID";
	private int p_M_Tour_ID = -1;

	private static final String CreateFromPickingSlots_MSG_DOC_PROCESSED = "CreateFromPickingSlots_Msg_Doc_Processed";

	private I_M_ShipperTransportation shipperTransportation;
	private I_M_Shipper shipper;

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		// task 06058: if the document is processed, we not allowed to run this process
		final I_M_ShipperTransportation shipperTransportation = context.getModel(I_M_ShipperTransportation.class);
		return !shipperTransportation.isProcessed();
	}

	@Override
	protected void prepare()
	{
		if (I_M_ShipperTransportation.Table_Name.equals(getTableName()))
		{
			p_M_ShipperTransportation_ID = getRecord_ID();
		}

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			final String parameterName = para.getParameterName();

			if (PARAM_M_Tour_ID.equals(parameterName))
			{
				p_M_Tour_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		//
		// Load M_ShipperTransportation and M_Shipper
		if (p_M_ShipperTransportation_ID <= 0)
		{
			throw new FillMandatoryException(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID);
		}

		shipperTransportation = InterfaceWrapperHelper.create(getCtx(), p_M_ShipperTransportation_ID, I_M_ShipperTransportation.class, get_TrxName());
		Check.assumeNotNull(shipperTransportation, "shipperTransportation not null");

		//
		// If the document is processed, we not allowed to run this process (06058)
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException("@" + CreateFromPickingSlots_MSG_DOC_PROCESSED + "@");
		}

		shipper = shipperTransportation.getM_Shipper();

		final I_M_Tour tour = InterfaceWrapperHelper.create(getCtx(), p_M_Tour_ID, I_M_Tour.class, getTrxName());
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
		final Map<Integer, I_C_Order> orderId2order = new LinkedHashMap<Integer, I_C_Order>();

		// we shall allow only those partner which are set in delivery days and that are vendors and have purchase orders
		for (final I_M_DeliveryDay dd : deliveryDays)
		{
			if (!dd.isToBeFetched())
			{
				continue; // nothing to do
			}

			final List<I_C_Order> purchaseOrders = orderDAO.retrievePurchaseOrdersForPickup(dd.getC_BPartner_Location(), dd.getDeliveryDate(), dd.getDeliveryDateTimeMax());
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
			createShippingPackage(order);
		}

		return "OK";
	}

	private I_M_ShippingPackage createShippingPackage(final I_C_Order order)
	{
		final I_C_BPartner partner = order.getC_BPartner();
		final I_C_BPartner_Location bpLoc = order.getC_BPartner_Location();
		final Timestamp deliverydate = order.getDatePromised();

		// create package
		final I_M_Package mpackage = InterfaceWrapperHelper.newInstance(I_M_Package.class, partner);
		mpackage.setM_Shipper_ID(shipper.getM_Shipper_ID());
		mpackage.setShipDate(deliverydate);
		mpackage.setC_BPartner_ID(partner.getC_BPartner_ID());
		mpackage.setC_BPartner_Location_ID(bpLoc.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(mpackage);

		// create shipping package
		final I_M_ShippingPackage shippingPackage = Services.get(IShipperTransportationBL.class)
				.createShippingPackage(shipperTransportation, mpackage);
		shippingPackage.setC_Order_ID(order.getC_Order_ID());
		InterfaceWrapperHelper.save(shippingPackage);

		return shippingPackage;
	}
}
