package de.metas.handlingunits.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;
import java.util.UUID;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.Assert;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.OrderReceiptScheduleProducerTest;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.impl.HUReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.impl.OrderReceiptScheduleProducer;

public abstract class AbstractHUReceiptScheduleProducerTest extends OrderReceiptScheduleProducerTest
{
	// Services
	protected IHandlingUnitsBL handlingUnitsBL;
	protected IHandlingUnitsDAO handlingUnitsDAO;
	protected IHUAssignmentDAO huAssignmentDAO;
	protected IHUReceiptScheduleDAO huReceiptScheduleDAO;

	protected HUTestHelper huTestHelper;

	protected I_M_Product product;
	protected I_M_HU_PI piLU;
	protected I_M_HU_PI piTU;
	private I_M_HU_PI_Item_Product piTU_ItemProduct;

	protected I_M_Warehouse w1;

	@Override
	public void setup()
	{
		huTestHelper = new HUTestHelper(false);
		huTestHelper.setInitAdempiere(false); // the caller already inited adempiere
		huTestHelper.init();

		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);

		Services.get(IReceiptScheduleProducerFactory.class).registerProducer(I_C_Order.Table_Name, OrderReceiptScheduleProducer.class);
		Services.get(IReceiptScheduleProducerFactory.class).registerProducer(I_C_Order.Table_Name, HUReceiptScheduleProducer.class);

		super.setup();

		setupData();
	}

	@Override
	protected IReceiptScheduleProducer createReceiptScheduleProducer()
	{
		return Services.get(IReceiptScheduleProducerFactory.class).createProducer(I_C_Order.Table_Name, false);
	}

	protected void setupData()
	{
		product = huTestHelper.pTomato;
		productUOM = huTestHelper.uomKg;

		w1 = createWarehouse("Warehouse1");

	}

	protected void setupLUandTUPackingInstructions(
			final I_M_HU_PI piTUToUse,
			final BigDecimal qtyTUsPerLU,
			final BigDecimal qtyCUsPerTU)
	{
		if (piTUToUse == null)
		{
			piTU = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			huTestHelper.createHU_PI_Item_PackingMaterial(piTU, huTestHelper.pmIFCO);
		}
		else
		{
			piTU = piTUToUse;
		}

		if (qtyCUsPerTU != null)
		{
			final I_M_HU_PI_Item itemMA = huTestHelper.createHU_PI_Item_Material(piTU);
			piTU_ItemProduct = huTestHelper.assignProduct(itemMA, product, qtyCUsPerTU, productUOM);
		}

		if (qtyTUsPerLU != null)
		{
			piLU = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			{
				final I_C_BPartner bpartner = null; // match any BP
				huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU, bpartner);

				huTestHelper.createHU_PI_Item_PackingMaterial(piLU, huTestHelper.pmPalet);
			}
		}
		else
		{
			piLU = null;
		}
	}

	@Override
	protected I_C_OrderLine createOrderLine(final I_C_Order order, final I_M_Product product)
	{
		final org.compiere.model.I_C_OrderLine orderLine = super.createOrderLine(order, product);

		final I_C_OrderLine orderLineHU = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
		orderLineHU.setM_HU_PI_Item_Product(piTU_ItemProduct);
		orderLineHU.setPackDescription("PackDescription-" + UUID.randomUUID());

		// orderLine.setQtyItemCapacity(new BigDecimal(10));

		// more if needed
		InterfaceWrapperHelper.save(orderLineHU);
		return orderLineHU;
	}

	protected I_C_OrderLine createOrderLine(final I_C_Order order, final BigDecimal qtyCUsToOrder, final BigDecimal qtyTUsToOrder)
	{
		final I_C_OrderLine orderLine = createOrderLine(order, product);

		orderLine.setM_HU_PI_Item_Product(piTU_ItemProduct);
		orderLine.setPackDescription("PackDescription-" + UUID.randomUUID());

		orderLine.setQtyEntered(qtyCUsToOrder);
		orderLine.setQtyOrdered(qtyCUsToOrder);
		orderLine.setQtyEnteredTU(qtyTUsToOrder);
		orderLine.setC_UOM(productUOM);

		InterfaceWrapperHelper.save(orderLine); // this triggers *all* the stuff, including receipt schedules, HUs, allocations and assignments
		return orderLine;
	}

	@Override
	protected void assertOrderLineMatches(final I_M_ReceiptSchedule rc, final org.compiere.model.I_C_OrderLine fromOrderLine)
	{
		final de.metas.handlingunits.model.I_M_ReceiptSchedule receiptScheduleHU = InterfaceWrapperHelper.create(rc, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);
		final I_C_OrderLine fromOrderLineHU = InterfaceWrapperHelper.create(fromOrderLine, I_C_OrderLine.class);

		Assert.assertEquals("Invalid PackDescription", fromOrderLineHU.getPackDescription(), receiptScheduleHU.getPackDescription());
		Assert.assertEquals("Invalid M_HU_PI_ItemProduct", fromOrderLineHU.getM_HU_PI_Item_Product_ID(), receiptScheduleHU.getM_HU_PI_Item_Product_ID());
		Assert.assertEquals("Invalid qty item capacity", fromOrderLineHU.getQtyItemCapacity(), receiptScheduleHU.getQtyItemCapacity());
	}
}
