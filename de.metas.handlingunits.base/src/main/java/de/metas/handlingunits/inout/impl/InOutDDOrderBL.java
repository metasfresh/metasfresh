package de.metas.handlingunits.inout.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_DD_Order;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.inout.IInOutDDOrderBL;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.material.planning.IProductPlanningDAO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InOutDDOrderBL implements IInOutDDOrderBL
{

	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public I_DD_Order createDDOrderForInOutLine(final I_M_InOutLine inOutLine)
	{

		final I_DD_Order ddOrderHeader = createDDOrderHeader(inOutLine);

		createDDOrderLine(ddOrderHeader, inOutLine);

		documentBL.processEx(ddOrderHeader, X_DD_Order.DOCACTION_Complete, X_DD_Order.DOCSTATUS_Completed);

		return ddOrderHeader;

	}

	private I_DD_Order createDDOrderHeader(final I_M_InOutLine inOutLine)
	{

		final int productId = inOutLine.getM_Product_ID();
		final int orgId = inOutLine.getAD_Org_ID();
		final int asiId = inOutLine.getM_AttributeSetInstance_ID();

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(
				orgId //
				, 0  // M_Warehouse_ID
				, 0  // S_Resource_ID
				, productId,//
				asiId);

		Check.errorIf(productPlanning == null, "No Product Planning found for product Id {}", productId);

		final int docTypeId = docTypeDAO
				.getDocTypeId(DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(inOutLine.getAD_Client_ID())
						.adOrgId(inOutLine.getAD_Org_ID())
						.build());

		final I_M_InOut inout = inOutLine.getM_InOut();

		final I_DD_Order ddOrderHeader = newInstance(I_DD_Order.class);

		ddOrderHeader.setC_BPartner_ID(inout.getC_BPartner_ID());
		ddOrderHeader.setC_BPartner_Location_ID(inout.getC_BPartner_Location_ID());
		ddOrderHeader.setDeliveryViaRule(inout.getDeliveryViaRule());
		ddOrderHeader.setDeliveryRule(inout.getDeliveryRule());
		ddOrderHeader.setPriorityRule(inout.getPriorityRule());
		ddOrderHeader.setM_Warehouse(productPlanning.getM_Warehouse());
		ddOrderHeader.setC_DocType_ID(docTypeId);
		ddOrderHeader.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrderHeader.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrderHeader.setIsInDispute(inOutLine.isInDispute());
		ddOrderHeader.setIsInTransit(inout.isInTransit());

		save(ddOrderHeader);

		return ddOrderHeader;
	}

	private I_DD_OrderLine createDDOrderLine(final I_DD_Order ddOrderHeader, final I_M_InOutLine inOutLine)
	{
		final I_M_Locator locator = inOutLine.getM_Locator();

		final I_M_Warehouse warehouseDest = inOutLine.getM_Warehouse_Dest();
		Check.errorIf(warehouseDest == null, "Warehouse Dest is null in the Receipt line {}. Please, set it.", inOutLine);

		final I_M_Locator locatorTo = warehouseDAO.retrieveLocators(warehouseDest).get(0);

		final I_M_InOut inout = inOutLine.getM_InOut();

		final I_DD_OrderLine ddOrderLine = newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order(ddOrderHeader);
		ddOrderLine.setLine(10);
		ddOrderLine.setM_Product_ID(inOutLine.getM_Product_ID());
		ddOrderLine.setQtyEntered(inOutLine.getQtyEntered());
		ddOrderLine.setC_UOM(inOutLine.getC_UOM());
		ddOrderLine.setQtyEnteredTU(inOutLine.getQtyEnteredTU());
		ddOrderLine.setM_HU_PI_Item_Product(inOutLine.getM_HU_PI_Item_Product());
		ddOrderLine.setM_Locator(locator);
		ddOrderLine.setM_LocatorTo(locatorTo);
		ddOrderLine.setIsInvoiced(false);
		ddOrderLine.setDateOrdered(inout.getDateOrdered());
		ddOrderLine.setDatePromised(inout.getMovementDate());

		save(ddOrderLine);

		return ddOrderLine;

	}

}
