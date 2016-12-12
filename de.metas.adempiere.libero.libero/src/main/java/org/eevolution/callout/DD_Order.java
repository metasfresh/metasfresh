package org.eevolution.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.CalloutInOut;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_Order;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Callout(I_DD_Order.class)
public class DD_Order
{
	public static final transient DD_Order instance = new DD_Order();

	@CalloutMethod(columnNames = { I_DD_Order.COLUMNNAME_C_DocType_ID })
	public void onC_DocType_ID(final I_DD_Order ddOrder)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(ddOrder.getC_DocType())
				.setOldDocumentNo(ddOrder.getDocumentNo())
				.setDocumentModel(ddOrder)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		ddOrder.setIsSOTrx(documentNoInfo.isSOTrx());

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			ddOrder.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@CalloutMethod(columnNames = { I_DD_Order.COLUMNNAME_C_BPartner_ID })
	public void onC_BPartner_ID(final I_DD_Order ddOrder, final ICalloutField calloutField)
	{
		final I_C_BPartner bpartner = ddOrder.getC_BPartner();
		if (bpartner == null || bpartner.getC_BPartner_ID() <= 0)
		{
			return;
		}

		//
		// BPartner Location (i.e. ShipTo)
		final I_C_BPartner_Location shipToLocation = CalloutInOut.suggestShipToLocation(calloutField, bpartner);
		ddOrder.setC_BPartner_Location(shipToLocation);

		//
		// BPartner Contact
		final boolean isSOTrx = ddOrder.isSOTrx();
		if (!isSOTrx)
		{
			I_AD_User contact = null;
			if (shipToLocation != null)
			{
				contact = Services.get(IBPartnerBL.class).retrieveUserForLoc(shipToLocation);
			}
			if (contact == null)
			{
				contact = Services.get(IBPartnerBL.class).retrieveShipContact(bpartner);
			}
			ddOrder.setAD_User(contact);
		}
	}

	@CalloutMethod(columnNames = { I_DD_Order.COLUMNNAME_C_Order_ID })
	public void onC_Order_ID(final I_DD_Order ddOrder)
	{
		final I_C_Order order = ddOrder.getC_Order();
		if(order == null || order.getC_Order_ID() <= 0)
		{
			return;
		}
		
		// Get Details
		ddOrder.setDateOrdered(order.getDateOrdered());
		ddOrder.setPOReference(order.getPOReference());
		ddOrder.setAD_Org_ID(order.getAD_Org_ID());
		ddOrder.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		ddOrder.setC_Activity_ID(order.getC_Activity_ID());
		ddOrder.setC_Campaign_ID(order.getC_Campaign_ID());
		ddOrder.setC_Project_ID(order.getC_Project_ID());
		ddOrder.setUser1_ID(order.getUser1_ID());
		ddOrder.setUser2_ID(order.getUser2_ID());
		
		// Warehouse (05251 begin: we need to use the advisor)
		final I_M_Warehouse wh =  Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		Check.assumeNotNull(wh, "IWarehouseAdvisor finds a ware house for {}", order);
		ddOrder.setM_Warehouse_ID(wh.getM_Warehouse_ID());
		
		//
		ddOrder.setDeliveryRule(order.getDeliveryRule());
		ddOrder.setDeliveryViaRule(order.getDeliveryViaRule());
		ddOrder.setM_Shipper_ID(order.getM_Shipper_ID());
		ddOrder.setFreightCostRule(order.getFreightCostRule());
		ddOrder.setFreightAmt(order.getFreightAmt());

		ddOrder.setC_BPartner_ID(order.getC_BPartner_ID());
		ddOrder.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		ddOrder.setAD_User_ID(new Integer(order.getAD_User_ID()));
	}
}
