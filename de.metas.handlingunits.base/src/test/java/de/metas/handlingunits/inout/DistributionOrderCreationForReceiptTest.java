package de.metas.handlingunits.inout;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Test;

import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.impl.ReceiptSchedule_WarehouseDest_Test;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.interfaces.I_M_Movement;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class DistributionOrderCreationForReceiptTest extends ReceiptSchedule_WarehouseDest_Test
{

	@Test
	public void createDistributionOrder_noMovementCreated()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty1 = new BigDecimal(300);
		final I_M_InOutLine receiptLine = createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		final I_M_ReceiptSchedule rs = createReceiptSchedule(receiptWarehouse, X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder);

		createRSAlloc(receiptLine, rs);

		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(Collections.singletonList(receiptLine));

		assertThat(movements).isEmpty();
	}

	@Test
	public void createDistributionOrder_DDOrderCreated()
	{
		final IInOutDDOrderBL inOutDDOrderBL = Services.get(IInOutDDOrderBL.class);

		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");
		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_Warehouse transitWarehouse = createWarehouse("TransitWarehouse");
		transitWarehouse.setIsInTransit(true);
		save(transitWarehouse);
		final I_M_Locator transitLocator = createLocator(transitWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty1 = new BigDecimal(300);
		final I_M_InOutLine receiptLine = createReceiptLine("Product1", receiptLocator, receipt, qty1, false);
		receiptLine.setM_Warehouse_Dest(transitWarehouse);
		save(receiptLine);
		final int productID = receiptLine.getM_Product_ID();

		final I_DD_NetworkDistribution networkDistribution = createNetworkDistributionWithLine(receiptWarehouse, transitWarehouse);

		final String createDDOrder = X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder;

		createProductPlanning(productID, networkDistribution, createDDOrder);

		final I_M_ReceiptSchedule rs = createReceiptSchedule(transitWarehouse, X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder);

		createRSAlloc(receiptLine, rs);

		final de.metas.handlingunits.model.I_M_InOutLine huIOL = create(receiptLine, de.metas.handlingunits.model.I_M_InOutLine.class);

		createDDOrderDocType("DDOrderDocType");

		final I_DD_Order distributionOrder = inOutDDOrderBL.createDDOrderForInOutLine(huIOL);

		assertThat(distributionOrder).isNotNull();

		List<I_DD_OrderLine> ddOrderLines = Services.get(IDDOrderDAO.class).retrieveLines(distributionOrder);

		final I_DD_OrderLine

		ddOrderLine = ddOrderLines.get(0);

		assertThat(ddOrderLine.getM_Locator()).isEqualTo(receiptLocator);
		assertThat(ddOrderLine.getM_LocatorTo()).isEqualTo(transitLocator);

	}

	private void createDDOrderDocType(final String name)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);

		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder);
		docType.setName(name);
		save(docType);

	}

	private I_PP_Product_Planning createProductPlanning(final int productID, final I_DD_NetworkDistribution nwDist, final String onmaterialreceiptwithdestwarehouse)
	{

		final I_PP_Product_Planning prodPlanning = newInstance(I_PP_Product_Planning.class);
		prodPlanning.setDD_NetworkDistribution(nwDist);
		prodPlanning.setM_Product_ID(productID);
		prodPlanning.setM_Warehouse_ID(0);
		prodPlanning.setS_Resource_ID(0);
		prodPlanning.setM_AttributeSetInstance_ID(0);
		prodPlanning.setAD_Org_ID(0);
		prodPlanning.setIsAttributeDependant(false);
		prodPlanning.setOnMaterialReceiptWithDestWarehouse(onmaterialreceiptwithdestwarehouse);

		save(prodPlanning);
		return prodPlanning;
	}

	private I_DD_NetworkDistribution createNetworkDistributionWithLine(final I_M_Warehouse whSource, final I_M_Warehouse whTarget)
	{
		final I_DD_NetworkDistribution networkDistribution = newInstance(I_DD_NetworkDistribution.class);

		save(networkDistribution);

		final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
		networkDistributionLine.setDD_NetworkDistribution(networkDistribution);
		networkDistributionLine.setM_WarehouseSource(whSource);
		networkDistributionLine.setM_Warehouse(whTarget);

		save(networkDistributionLine);

		return networkDistribution;
	}

	private I_M_ReceiptSchedule_Alloc createRSAlloc(final I_M_InOutLine receiptLine, final I_M_ReceiptSchedule rs)
	{
		final I_M_ReceiptSchedule_Alloc rsa = newInstance(I_M_ReceiptSchedule_Alloc.class);
		rsa.setM_ReceiptSchedule(rs);
		rsa.setM_InOutLine(receiptLine);
		save(rsa);

		return rsa;
	}

	private I_M_ReceiptSchedule createReceiptSchedule(final I_M_Warehouse warehouseDest, final String onMaterialReceiptWithDestWarehouse)
	{
		final I_M_ReceiptSchedule rs = newInstance(I_M_ReceiptSchedule.class);
		rs.setOnMaterialReceiptWithDestWarehouse(onMaterialReceiptWithDestWarehouse);
		rs.setM_Warehouse_Dest(warehouseDest);
		save(rs);

		return rs;
	}

}
