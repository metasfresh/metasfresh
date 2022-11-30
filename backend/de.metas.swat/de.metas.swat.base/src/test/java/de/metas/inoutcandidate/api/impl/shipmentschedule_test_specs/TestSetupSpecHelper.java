package de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.Disabled;

import java.util.HashMap;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
@Disabled
public class TestSetupSpecHelper
{
	public ImmutableList<OlAndSched> setup(@NonNull final TestSetupSpec spec)
	{
		final SetupData setupData = new SetupData();

		setupData.bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(setupData.bpartnerRecord);

		createUOMs(setupData, spec);
		createProducts(setupData, spec);
		createOrders(setupData, spec);
		createOrderLines(setupData, spec);
		createStocks(setupData, spec);

		return createShipmentSchedules(setupData, spec);
	}

	private ImmutableList<OlAndSched> createShipmentSchedules(final SetupData setupData, final TestSetupSpec spec)
	{
		final ImmutableList.Builder<OlAndSched> olAndScheds = ImmutableList.builder();
		for (final ShipmentScheduleSpec shipmentSchedule : spec.getShipmentSchedules())
		{
			final OlAndSched olAndSched = createOlAndSched(setupData, shipmentSchedule);
			olAndScheds.add(olAndSched);
		}

		return olAndScheds.build();
	}

	private OlAndSched createOlAndSched(final SetupData setupData, final ShipmentScheduleSpec shipmentSchedule)
	{
		final I_M_Product productRecord = setupData.getProductByValue(shipmentSchedule.getProduct());
		final I_C_Order orderRecord = setupData.value2order.get(shipmentSchedule.getOrder());
		final I_C_OrderLine orderLineRecord = setupData.value2orderLine.get(shipmentSchedule.getOrderLine());

		final I_M_ShipmentSchedule shipmentScheduleRecord = newInstance(I_M_ShipmentSchedule.class);
		shipmentScheduleRecord.setM_Product_ID(productRecord.getM_Product_ID());
		shipmentScheduleRecord.setIsClosed(false);
		shipmentScheduleRecord.setC_BPartner_ID(setupData.bpartnerRecord.getC_BPartner_ID());
		shipmentScheduleRecord.setM_Warehouse_ID(setupData.warehouseId.getRepoId());
		shipmentScheduleRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		shipmentScheduleRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		shipmentScheduleRecord.setM_Product_ID(productRecord.getM_Product_ID());
		shipmentScheduleRecord.setBPartnerAddress_Override("BPartnerAddress_Override"); // not flagged as mandatory in AD, but always set
		shipmentScheduleRecord.setAD_Table_ID(getTableId(I_C_OrderLine.class));
		shipmentScheduleRecord.setRecord_ID(orderLineRecord.getC_OrderLine_ID());
		
		shipmentScheduleRecord.setQtyOrdered(shipmentSchedule.getQtyOrdered());

		shipmentScheduleRecord.setDeliveryRule(shipmentSchedule.getDeliveryRule().getCode()); // <==

		final PPOrderId pickFromOrderId = createPickFromOrder(setupData, shipmentSchedule.getPickFromOrder());
		shipmentScheduleRecord.setPickFrom_Order_ID(PPOrderId.toRepoId(pickFromOrderId));

		saveRecord(shipmentScheduleRecord);

		return OlAndSched.builder()
				.deliverRequest(orderLineRecord::getQtyOrdered)
				.shipmentSchedule(shipmentScheduleRecord)
				.build();
	}

	private PPOrderId createPickFromOrder(final SetupData setupData, final PickFromOrderSpec spec)
	{
		if (spec == null)
		{
			return null;
		}

		final PPOrderId pickingOrderId;
		{
			final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
			ppOrder.setM_Product_ID(setupData.getProductIdByValue(spec.getMainProduct()).getRepoId());
			ppOrder.setC_UOM_ID(setupData.getUOMByName(spec.getMainProductUOM()).getC_UOM_ID());
			ppOrder.setM_Warehouse_ID(setupData.warehouseId.getRepoId());
			ppOrder.setDocStatus(DocStatus.Completed.getCode());
			ppOrder.setProcessed(true);
			saveRecord(ppOrder);

			pickingOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		}

		for (final PickFromOrderBOMLineSpec bomLineSpec : spec.getBomLines())
		{
			final I_PP_Order_BOMLine orderBOMLine = newInstance(I_PP_Order_BOMLine.class);
			orderBOMLine.setPP_Order_ID(pickingOrderId.getRepoId());
			orderBOMLine.setComponentType(BOMComponentType.Component.getCode());
			orderBOMLine.setM_Product_ID(setupData.getProductIdByValue(bomLineSpec.getProduct()).getRepoId());
			orderBOMLine.setC_UOM_ID(setupData.getUOMByName(bomLineSpec.getUom()).getC_UOM_ID());
			orderBOMLine.setIsQtyPercentage(false);
			orderBOMLine.setQtyBOM(bomLineSpec.getQtyForOneFinishedGood());
			saveRecord(orderBOMLine);
		}

		return pickingOrderId;
	}

	private void createStocks(final SetupData setupData, final TestSetupSpec spec)
	{
		for (final StockSpec stock : spec.getStocks())
		{
			final I_M_Product productRecord = setupData.getProductByValue(stock.getProduct());

			final I_MD_Stock stockRecord = newInstance(I_MD_Stock.class);
			stockRecord.setM_Product_ID(productRecord.getM_Product_ID());
			stockRecord.setM_Warehouse_ID(setupData.warehouseId.getRepoId());
			stockRecord.setQtyOnHand(stock.getQtyStock());
			saveRecord(stockRecord);
		}
	}

	private void createOrderLines(final SetupData setupData, final TestSetupSpec spec)
	{
		for (final OrderLineSpec orderLine : spec.getOrderLines())
		{
			final I_M_Product productRecord = setupData.getProductByValue(orderLine.getProduct());
			final I_C_Order orderRecord = assumeNotNull(setupData.value2order.get(orderLine.getOrder()), "");

			final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
			orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
			orderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
			orderLineRecord.setQtyOrdered(orderLine.getQtyOrdered()); // <==
			orderLineRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-20"));
			saveRecord(orderLineRecord);

			setupData.value2orderLine.put(orderLine.getValue(), orderLineRecord);
		}
	}

	private void createOrders(final SetupData setupData, final TestSetupSpec spec)
	{
		for (final OrderSpec order : spec.getOrders())
		{
			final I_C_Order orderRecord = newInstance(I_C_Order.class);
			orderRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-19"));
			orderRecord.setM_Warehouse_ID(setupData.warehouseId.getRepoId());
			saveRecord(orderRecord);

			setupData.value2order.put(order.getValue(), orderRecord);
		}
	}

	private void createProducts(final SetupData setupData, final TestSetupSpec spec)
	{
		for (final ProductSpec product : spec.getProducts())
		{
			final I_C_UOM uom = setupData.getUOMByName(product.getUomValue());

			final I_M_Product productRecord = newInstance(I_M_Product.class);
			productRecord.setValue(product.getValue());
			productRecord.setIsStocked(product.isStocked());
			productRecord.setProductType(product.getProductType());
			productRecord.setC_UOM_ID(uom.getC_UOM_ID());

			saveRecord(productRecord);

			setupData.addProduct(productRecord);
		}
	}

	private void createUOMs(final SetupData setupData, final TestSetupSpec spec)
	{
		for (final UomSpec uom : spec.getUoms())
		{
			final I_C_UOM uomRecord = createUOM(uom);

			setupData.addUOM(uomRecord);
		}
	}

	private I_C_UOM createUOM(final UomSpec spec)
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		uomRecord.setName(spec.getName());
		saveRecord(uomRecord);
		return uomRecord;
	}

	private static class SetupData
	{
		private final WarehouseId warehouseId = WarehouseId.ofRepoId(35);

		private final HashMap<String, I_C_UOM> uomsByName = new HashMap<>();
		private final HashMap<String, I_M_Product> productsByValue = new HashMap<>();
		private final HashMap<String, I_C_Order> value2order = new HashMap<>();
		private final HashMap<String, I_C_OrderLine> value2orderLine = new HashMap<>();
		private I_C_BPartner bpartnerRecord;

		public void addUOM(final I_C_UOM uomRecord)
		{
			uomsByName.put(uomRecord.getName(), uomRecord);
		}

		public I_C_UOM getUOMByName(final String name)
		{
			final I_C_UOM uom = uomsByName.get(name);
			if (uom == null)
			{
				throw new AdempiereException("No UOM defined for '" + name + "'");
			}
			return assumeNotNull(uom, "");
		}

		public void addProduct(final I_M_Product productRecord)
		{
			productsByValue.put(productRecord.getValue(), productRecord);
		}

		public ProductId getProductIdByValue(final String productValue)
		{
			return ProductId.ofRepoId(getProductByValue(productValue).getM_Product_ID());
		}

		public I_M_Product getProductByValue(final String productValue)
		{
			final I_M_Product product = productsByValue.get(productValue);
			if (product == null)
			{
				throw new AdempiereException("No product defined for '" + productValue + "'");
			}
			return product;
		}
	}
}
