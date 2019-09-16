package de.metas.handlingunits.shipmentschedule.spi.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_C_UOM_Conversion;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleBL;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;

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

public class ShipmentLineBuilderTest
{
	private static final BigDecimal EIGHT = new BigDecimal(8);

	private I_M_ShipmentSchedule shipmentSchedule;
	private I_M_InOut shipment;
	private I_C_OrderLine orderLine;
	private I_M_HU_PI_Item_Product piipWithCapacityEight;
	private IHUContext huContext;

	private StockQtyAndUOMQty oneWithoutCatch;
	private StockQtyAndUOMQty oneWithCatch;

	private HUTestHelper huTestHelper;

	@Before
	public void init()
	{
		huTestHelper = new HUTestHelper();

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item itemMA = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		piipWithCapacityEight = huTestHelper.assignProduct(itemMA, huTestHelper.pTomatoProductId, EIGHT, huTestHelper.uomEach);

		final I_C_Order order = newInstance(I_C_Order.class);
		save(order);

		orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(huTestHelper.pTomato.getM_Product_ID());
		save(orderLine);

		shipment = newInstance(I_M_InOut.class);
		shipment.setM_Warehouse_ID(huTestHelper.defaultWarehouse.getM_Warehouse_ID());
		save(shipment);

		shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse_ID(huTestHelper.defaultWarehouse.getM_Warehouse_ID());
		shipmentSchedule.setC_Order_ID(order.getC_Order_ID());
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());
		shipmentSchedule.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_OrderLine.Table_Name));
		shipmentSchedule.setM_Product_ID(huTestHelper.pTomato.getM_Product_ID());
		shipmentSchedule.setQtyTU_Calculated(TEN);
		shipmentSchedule.setM_HU_PI_Item_Product_ID(piipWithCapacityEight.getM_HU_PI_Item_Product_ID());
		save(shipmentSchedule);

		huContext = Services.get(IHUContextFactory.class).createMutableHUContext();

		oneWithoutCatch = StockQtyAndUOMQtys.create(ONE, huTestHelper.pTomatoProductId, null, null);
		oneWithCatch = StockQtyAndUOMQtys.create(ONE, huTestHelper.pTomatoProductId, new BigDecimal("1.2"), huTestHelper.uomKgId);

		final I_C_UOM_Conversion catchUOMConversionRecord = newInstance(I_C_UOM_Conversion.class);
		catchUOMConversionRecord.setM_Product_ID(huTestHelper.pTomatoProductId.getRepoId());
		catchUOMConversionRecord.setC_UOM_ID(huTestHelper.uomEachId.getRepoId());
		catchUOMConversionRecord.setC_UOM_To_ID(huTestHelper.uomKgId.getRepoId());
		catchUOMConversionRecord.setMultiplyRate(TEN);
		catchUOMConversionRecord.setDivideRate(ONE.divide(TEN));
		catchUOMConversionRecord.setIsCatchUOMForProduct(true);
		catchUOMConversionRecord.setIsActive(true);
		saveRecord(catchUOMConversionRecord);

		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(OrderLineShipmentScheduleHandler.class);
		Services.registerService(IShipmentScheduleBL.class, ShipmentScheduleBL.newInstanceForUnitTesting());
	}

	@Test
	public void createShipmentLine_shipmentScheduleWithoutHu_noCatchQty()
	{
		final ShipmentScheduleWithHU shipmentScheduleWithoutHu = ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				oneWithoutCatch,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final ShipmentLineBuilder shipmentLineBuilder = new ShipmentLineBuilder(shipment);
		shipmentLineBuilder.setManualPackingMaterial(true);

		// invoke the methods under test
		shipmentLineBuilder.add(shipmentScheduleWithoutHu);
		final I_M_InOutLine shipmentLine = shipmentLineBuilder.createShipmentLine();

		assertThat(shipmentLine).isNotNull();
		assertThat(shipmentLine.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(shipmentSchedule.getM_Product_ID());
		assertThat(shipmentLine.getQtyTU_Override()).isEqualByComparingTo(TEN); // we want 10, not the piip's 8

		assertThat(shipmentLine.getCatch_UOM_ID()).isEqualTo(huTestHelper.uomKgId.getRepoId());
		assertThat(isNull(shipmentLine, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch)).isTrue();
	}

	@Test
	public void createShipmentLine_shipmentScheduleWithoutHu_QtyTypeBoth_noCatchQty()
	{
		final ShipmentScheduleWithHU shipmentScheduleWithoutHu = ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				oneWithoutCatch,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final ShipmentLineBuilder shipmentLineBuilder = new ShipmentLineBuilder(shipment);
		shipmentLineBuilder.setQtyTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_BOTH);
		shipmentLineBuilder.setManualPackingMaterial(true);

		// invoke the methods under test
		shipmentLineBuilder.add(shipmentScheduleWithoutHu);
		final I_M_InOutLine shipmentLine = shipmentLineBuilder.createShipmentLine();

		assertThat(shipmentLine).isNotNull();
		assertThat(shipmentLine.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(shipmentSchedule.getM_Product_ID());
		assertThat(shipmentLine.getQtyEntered()).isEqualByComparingTo(ONE);
		assertThat(shipmentLine.getQtyTU_Override()).isEqualByComparingTo(ONE);

		assertThat(shipmentLine.getCatch_UOM_ID()).isEqualTo(huTestHelper.uomKgId.getRepoId());
		assertThat(isNull(shipmentLine, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch)).isTrue();
	}

	@Test
	public void createShipmentLine_shipmentScheduleWithoutHu_withCatchQty()
	{
		final ShipmentScheduleWithHU shipmentScheduleWithoutHu = ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				oneWithCatch,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final ShipmentLineBuilder shipmentLineBuilder = new ShipmentLineBuilder(shipment);
		shipmentLineBuilder.setManualPackingMaterial(true);

		// invoke the methods under test
		shipmentLineBuilder.add(shipmentScheduleWithoutHu);
		final I_M_InOutLine shipmentLine = shipmentLineBuilder.createShipmentLine();

		assertThat(shipmentLine).isNotNull();
		assertThat(shipmentLine.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(shipmentSchedule.getM_Product_ID());
		assertThat(shipmentLine.getQtyTU_Override()).isEqualByComparingTo(TEN); // we want 10, not the piip's 8

		assertThat(shipmentLine.getCatch_UOM_ID()).isEqualTo(huTestHelper.uomKgId.getRepoId());
		assertThat(shipmentLine.getQtyDeliveredCatch()).isEqualByComparingTo("1.2");
	}

	@Test
	public void createShipmentLine_shipmentScheduleWithoutHu_QtyTypeBoth_withCatchQty()
	{
		final ShipmentScheduleWithHU shipmentScheduleWithoutHu = ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				oneWithCatch,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final ShipmentLineBuilder shipmentLineBuilder = new ShipmentLineBuilder(shipment);
		shipmentLineBuilder.setQtyTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_BOTH);
		shipmentLineBuilder.setManualPackingMaterial(true);

		// invoke the methods under test
		shipmentLineBuilder.add(shipmentScheduleWithoutHu);
		final I_M_InOutLine shipmentLine = shipmentLineBuilder.createShipmentLine();

		assertThat(shipmentLine).isNotNull();
		assertThat(shipmentLine.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(shipmentSchedule.getM_Product_ID());
		assertThat(shipmentLine.getQtyEntered()).isEqualByComparingTo(ONE);
		assertThat(shipmentLine.getQtyTU_Override()).isEqualByComparingTo(ONE);

		assertThat(shipmentLine.getCatch_UOM_ID()).isEqualTo(huTestHelper.uomKgId.getRepoId());
		assertThat(shipmentLine.getQtyDeliveredCatch()).isEqualByComparingTo("1.2");
	}
}
