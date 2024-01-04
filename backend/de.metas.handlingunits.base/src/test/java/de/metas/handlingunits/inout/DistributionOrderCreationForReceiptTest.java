package de.metas.handlingunits.inout;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.impl.ReceiptSchedule_WarehouseDest_Test;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.interfaces.I_M_Movement;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.organization.OrgId;
import de.metas.product.OnMaterialReceiptWithDestWarehouse;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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
	private IProductPlanningDAO productPlanningDAO;

	@Override
	public void setup()
	{
		super.setup();
		this.productPlanningDAO = Services.get(IProductPlanningDAO.class);
	}

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

		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(Collections.singletonList(receiptLine), null);

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
		// receiptLine.setM_Warehouse_Dest(transitWarehouse);
		save(receiptLine);
		final ProductId productId = ProductId.ofRepoId(receiptLine.getM_Product_ID());

		final DistributionNetworkId distributionNetworkId = createNetworkDistributionWithLine(receiptWarehouse, transitWarehouse);

		createProductPlanning(productId, distributionNetworkId, OnMaterialReceiptWithDestWarehouse.CREATE_DISTRIBUTION_ORDER);

		final I_M_ReceiptSchedule rs = createReceiptSchedule(transitWarehouse, X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder);

		createRSAlloc(receiptLine, rs);

		final de.metas.handlingunits.model.I_M_InOutLine huIOL = create(receiptLine, de.metas.handlingunits.model.I_M_InOutLine.class);

		createDDOrderDocType("DDOrderDocType");

		// invoke the method under test
		final I_DD_Order distributionOrder = inOutDDOrderBL
				.createDDOrderForInOutLine(huIOL, null)
				.orElse(null);

		assertThat(distributionOrder).isNotNull();

		List<I_DD_OrderLine> ddOrderLines = new DDOrderLowLevelDAO().retrieveLines(distributionOrder);

		final I_DD_OrderLine

				ddOrderLine = ddOrderLines.get(0);

		assertThat(ddOrderLine.getM_Locator_ID()).isEqualTo(receiptLocator.getM_Locator_ID());
		assertThat(ddOrderLine.getM_LocatorTo_ID()).isEqualTo(transitLocator.getM_Locator_ID());

	}

	@SuppressWarnings("SameParameterValue")
	private void createDDOrderDocType(final String name)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);

		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder);
		docType.setName(name);
		save(docType);

	}

	@SuppressWarnings("SameParameterValue")
	private void createProductPlanning(final ProductId productId, final DistributionNetworkId distributionNetworkId, final OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse)
	{
		productPlanningDAO.save(ProductPlanning.builder()
				.distributionNetworkId(distributionNetworkId)
				.productId(productId)
				.warehouseId(null)
				.plantId(null)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.orgId(OrgId.ANY)
				.isAttributeDependant(false)
				.onMaterialReceiptWithDestWarehouse(onMaterialReceiptWithDestWarehouse)
				.build());
	}

	private DistributionNetworkId createNetworkDistributionWithLine(final I_M_Warehouse whSource, final I_M_Warehouse whTarget)
	{
		final I_DD_NetworkDistribution networkDistribution = newInstance(I_DD_NetworkDistribution.class);

		save(networkDistribution);

		final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
		networkDistributionLine.setDD_NetworkDistribution(networkDistribution);
		networkDistributionLine.setM_WarehouseSource(whSource);
		networkDistributionLine.setM_Warehouse(whTarget);

		save(networkDistributionLine);

		return DistributionNetworkId.ofRepoId(networkDistribution.getDD_NetworkDistribution_ID());
	}

	private void createRSAlloc(final I_M_InOutLine receiptLine, final I_M_ReceiptSchedule rs)
	{
		final I_M_ReceiptSchedule_Alloc rsa = newInstance(I_M_ReceiptSchedule_Alloc.class);
		rsa.setM_ReceiptSchedule(rs);
		rsa.setM_InOutLine(receiptLine);
		save(rsa);

	}

	@SuppressWarnings("SameParameterValue")
	private I_M_ReceiptSchedule createReceiptSchedule(final I_M_Warehouse warehouseDest, final String onMaterialReceiptWithDestWarehouse)
	{
		final I_M_ReceiptSchedule rs = newInstance(I_M_ReceiptSchedule.class);
		rs.setOnMaterialReceiptWithDestWarehouse(onMaterialReceiptWithDestWarehouse);
		rs.setM_Warehouse_Dest_ID(warehouseDest.getM_Warehouse_ID());
		save(rs);

		return rs;
	}

	private I_M_InOut createReceipt(final I_M_Locator receiptLocator)
	{
		final BPartnerLocationId receiptPartner = createBPartner("Receipt Partner");

		// NOTE: we need to use some dummy transaction, else movement generation will fail
		final String trxName = Services.get(ITrxManager.class).createTrxName("DummyTrx", true);

		final I_M_InOut receipt = create(ctx, I_M_InOut.class, trxName);
		receipt.setAD_Org_ID(receiptLocator.getAD_Org_ID());
		receipt.setM_Warehouse_ID(receiptLocator.getM_Warehouse_ID());
		receipt.setC_BPartner_ID(receiptPartner.getBpartnerId().getRepoId());
		save(receipt);

		return receipt;
	}

	@SuppressWarnings("SameParameterValue")
	private I_M_InOutLine createReceiptLine(
			final String productName,
			final I_M_Locator locator,
			final I_M_InOut receipt,
			final BigDecimal qty,
			final boolean isInDispute)
	{
		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM");
		final I_M_Product product = createProduct(productName, UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), locator);

		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);

		line.setAD_Org_ID(receipt.getAD_Org_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setMovementQty(qty);
		line.setIsInDispute(isInDispute);
		line.setM_InOut_ID(receipt.getM_InOut_ID());
		line.setM_Locator_ID(locator.getM_Locator_ID());

		save(line);

		return line;
	}

}
