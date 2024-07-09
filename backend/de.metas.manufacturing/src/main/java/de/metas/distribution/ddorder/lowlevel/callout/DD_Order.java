package de.metas.distribution.ddorder.lowlevel.callout;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.CalloutInOut;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.eevolution.model.I_DD_Order;

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
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;

	public DD_Order(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
	}

	@CalloutMethod(columnNames = { I_DD_Order.COLUMNNAME_C_DocType_ID })
	public void onC_DocType_ID(final I_DD_Order ddOrder)
	{
		final IDocumentNoInfo documentNoInfo = documentNoBuilderFactory
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(DocTypeId.optionalOfRepoId(ddOrder.getC_DocType_ID())
						.map(docTypeDAO::getRecordById)
						.orElse(null))
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
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		//
		// BPartner Location (i.e. ShipTo)
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		final I_C_BPartner_Location shipToLocation = CalloutInOut.suggestShipToLocation(calloutField, bpartner);
		ddOrder.setC_BPartner_Location_ID(shipToLocation != null ? shipToLocation.getC_BPartner_Location_ID() : -1);

		//
		// BPartner Contact
		final boolean isSOTrx = ddOrder.isSOTrx();
		if (!isSOTrx)
		{
			I_AD_User contact = null;
			if (shipToLocation != null)
			{
				contact = bpartnerBL.retrieveUserForLoc(shipToLocation);
			}
			if (contact == null)
			{
				contact = bpartnerBL.retrieveShipContact(bpartner);
			}
			ddOrder.setAD_User_ID(contact != null ? contact.getAD_User_ID() : -1);
		}
	}

	@CalloutMethod(columnNames = { I_DD_Order.COLUMNNAME_C_Order_ID })
	public void onC_Order_ID(final I_DD_Order ddOrder)
	{
		final I_C_Order order = ddOrder.getC_Order();
		if (order == null || order.getC_Order_ID() <= 0)
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
		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		Check.assumeNotNull(warehouseId, "IWarehouseAdvisor finds a ware house for {}", order);
		ddOrder.setM_Warehouse_ID(warehouseId.getRepoId());

		//
		ddOrder.setDeliveryRule(order.getDeliveryRule());
		ddOrder.setDeliveryViaRule(order.getDeliveryViaRule());
		ddOrder.setM_Shipper_ID(order.getM_Shipper_ID());
		ddOrder.setFreightCostRule(order.getFreightCostRule());
		ddOrder.setFreightAmt(order.getFreightAmt());

		ddOrder.setC_BPartner_ID(order.getC_BPartner_ID());
		ddOrder.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		ddOrder.setAD_User_ID(order.getAD_User_ID());
	}
}
