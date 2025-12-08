package de.metas.handlingunits.inout.impl;

import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.inout.IInOutDDOrderBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.api.ReceiptLineFindForwardToLocatorTool;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
	@Override
	public Optional<I_DD_Order> createDDOrderForInOutLine(
			@NonNull final I_M_InOutLine inOutLine,
			@Nullable final LocatorId locatorToId)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		final LocatorId effectiveLocatorToId = ReceiptLineFindForwardToLocatorTool.findLocatorIdOrNull(inOutLine, locatorToId);

		if (effectiveLocatorToId == null || effectiveLocatorToId.getRepoId() == inOutLine.getM_Locator_ID())
		{
			return Optional.empty();
		}

		final I_DD_Order ddOrderHeader = createDDOrderHeader(inOutLine);

		createDDOrderLine(ddOrderHeader, inOutLine, effectiveLocatorToId);

		documentBL.processEx(ddOrderHeader, X_DD_Order.DOCACTION_Complete, X_DD_Order.DOCSTATUS_Completed);

		return Optional.of(ddOrderHeader);

	}

	private I_DD_Order createDDOrderHeader(@NonNull final I_M_InOutLine inOutLine)
	{
		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final ProductId productId = ProductId.ofRepoId(inOutLine.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(inOutLine.getAD_Org_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(inOutLine.getM_AttributeSetInstance_ID());

		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				// no warehouse, no plant
				.build();
		final ProductPlanning productPlanning = productPlanningDAO.find(query)
				.orElseThrow(() -> new AdempiereException("No Product Planning found for " + query));

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
				.adClientId(inOutLine.getAD_Client_ID())
				.adOrgId(inOutLine.getAD_Org_ID())
				.build();
		final int docTypeId = docTypeDAO.getDocTypeId(docTypeQuery).getRepoId();

		final I_M_InOut inout = inOutLine.getM_InOut();

		final I_DD_Order ddOrderHeader = newInstance(I_DD_Order.class);

		ddOrderHeader.setC_BPartner_ID(inout.getC_BPartner_ID());
		ddOrderHeader.setC_BPartner_Location_ID(inout.getC_BPartner_Location_ID());
		ddOrderHeader.setDeliveryViaRule(inout.getDeliveryViaRule());
		ddOrderHeader.setDeliveryRule(inout.getDeliveryRule());
		ddOrderHeader.setPriorityRule(inout.getPriorityRule());
		ddOrderHeader.setM_Warehouse_ID(WarehouseId.toRepoId(productPlanning.getWarehouseId()));
		ddOrderHeader.setC_DocType_ID(docTypeId);
		ddOrderHeader.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrderHeader.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrderHeader.setIsInDispute(inOutLine.isInDispute());
		ddOrderHeader.setIsInTransit(inout.isInTransit());

		save(ddOrderHeader);

		return ddOrderHeader;
	}

	private I_DD_OrderLine createDDOrderLine(
			@NonNull final I_DD_Order ddOrderHeader,
			@NonNull final I_M_InOutLine inOutLine,
			@Nullable final LocatorId locatorToId)
	{
		final I_M_InOut inout = inOutLine.getM_InOut();

		final I_DD_OrderLine ddOrderLine = newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order(ddOrderHeader);
		ddOrderLine.setLine(10);
		ddOrderLine.setM_Product_ID(inOutLine.getM_Product_ID());
		ddOrderLine.setQtyEntered(inOutLine.getQtyEntered());
		ddOrderLine.setC_UOM_ID(inOutLine.getC_UOM_ID());
		ddOrderLine.setQtyEnteredTU(inOutLine.getQtyEnteredTU());
		ddOrderLine.setM_HU_PI_Item_Product(inOutLine.getM_HU_PI_Item_Product());
		ddOrderLine.setM_Locator_ID(inOutLine.getM_Locator_ID());
		ddOrderLine.setM_LocatorTo_ID(locatorToId.getRepoId());
		ddOrderLine.setIsInvoiced(false);
		ddOrderLine.setDateOrdered(inout.getDateOrdered());
		ddOrderLine.setDatePromised(inout.getMovementDate());

		save(ddOrderLine);

		return ddOrderLine;

	}

}
