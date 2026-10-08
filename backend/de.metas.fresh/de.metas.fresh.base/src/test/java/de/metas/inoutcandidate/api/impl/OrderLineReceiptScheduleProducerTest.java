package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.uom.UomId;
import de.metas.util.Services;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineReceiptScheduleProducerTest extends ReceiptScheduleTestBase
{
	private IReceiptScheduleProducerFactory receiptScheduleProducer;

	@Override
	protected void setup()
	{
		receiptScheduleProducer = Services.get(IReceiptScheduleProducerFactory.class);
	}

	/**
	 * Regression Test for http://dewiki908/mediawiki/index.php/05940_Wareneingang_Lagerumbuchung fresh needs the destination warehouse to be set from m_product. This is currently implemented in
	 * swat, but might change there. This test is in fresh to make sure we notice that sth's wrong for fresh when things are changes in swat.
	 */
	@Test
	public void createReceiptScheduleFromOrderLine()
	{
		final I_M_Warehouse orderWarehouse = createWarehouse("WH_Order");

		final I_M_Warehouse productWarehouse = createWarehouse("WH_Product");
		final I_M_Locator productWarehouseLocator = createLocator(productWarehouse);

		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM");
		final I_M_Product product = createProduct("Test Product", UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), productWarehouseLocator);

		final I_C_Order order = createOrder(orderWarehouse);
		createOrderLine(order, product);

		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> receiptSched = producer.createOrUpdateReceiptSchedules(order, Collections.<I_M_ReceiptSchedule>emptyList());

		Assertions.assertEquals(orderWarehouse.getM_Warehouse_ID(), receiptSched.get(0).getM_Warehouse_ID(), "Invalid M_Warehouse_ID");
		Assertions.assertEquals(0, receiptSched.get(0).getM_Warehouse_Override_ID(), "Invalid M_Warehouse_Override_ID");
		Assertions.assertEquals(productWarehouse.getM_Warehouse_ID(), receiptSched.get(0).getM_Warehouse_Dest_ID(), "Invalid M_Warehouse_Dest_ID");
	}

	/**
	 * Verify that POReference set on a receipt schedule is NOT overwritten
	 * when the receipt schedule is updated (e.g. after PO reactivation + re-completion).
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void poReference_isPreservedOnUpdate()
	{
		final I_M_Warehouse orderWarehouse = createWarehouse("WH_Order");
		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM2");
		final I_M_Product product = createProduct("Test Product 2", UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), null);

		final I_C_Order order = createOrder(orderWarehouse);
		order.setPOReference("ORDER-REF-001");
		InterfaceWrapperHelper.save(order);

		createOrderLine(order, product);

		// Create the receipt schedule (initial creation)
		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> receiptSchedules = producer.createOrUpdateReceiptSchedules(order, Collections.emptyList());
		assertThat(receiptSchedules).hasSize(1);

		final I_M_ReceiptSchedule receiptSchedule = receiptSchedules.get(0);
		assertThat(receiptSchedule.getPOReference()).isEqualTo("ORDER-REF-001");

		// Simulate user manually changing the POReference on the receipt schedule
		receiptSchedule.setPOReference("MANUAL-REF-999");
		InterfaceWrapperHelper.save(receiptSchedule);

		// Simulate PO re-completion: the producer runs createOrUpdateReceiptSchedules again.
		// Since the receipt schedule already exists, it takes the update path
		// which should NOT overwrite the manually set POReference.
		final List<I_M_ReceiptSchedule> updatedSchedules = producer.createOrUpdateReceiptSchedules(order, Collections.emptyList());
		assertThat(updatedSchedules).hasSize(1);

		// Reload from DB to verify persistence
		InterfaceWrapperHelper.refresh(receiptSchedule);
		assertThat(receiptSchedule.getPOReference())
				.as("POReference should be preserved after receipt schedule update")
				.isEqualTo("MANUAL-REF-999");
	}

	/**
	 * Verify that POReference IS set from the order when creating a NEW receipt schedule.
	 */
	@Test
	public void poReference_isSetFromOrderOnCreation()
	{
		final I_M_Warehouse orderWarehouse = createWarehouse("WH_Order");
		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM3");
		final I_M_Product product = createProduct("Test Product 3", UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), null);

		final I_C_Order order = createOrder(orderWarehouse);
		order.setPOReference("NEW-ORDER-REF");
		InterfaceWrapperHelper.save(order);

		createOrderLine(order, product);

		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> receiptSchedules = producer.createOrUpdateReceiptSchedules(order, Collections.emptyList());

		assertThat(receiptSchedules).hasSize(1);
		assertThat(receiptSchedules.get(0).getPOReference())
				.as("POReference should be set from order on initial creation")
				.isEqualTo("NEW-ORDER-REF");
	}

	/**
	 * Verify that when the receipt schedule's POReference is blank,
	 * the order's POReference IS synced on update.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void poReference_isSyncedFromOrderWhenBlank()
	{
		final I_M_Warehouse orderWarehouse = createWarehouse("WH_Order");
		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM4");
		final I_M_Product product = createProduct("Test Product 4", UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), null);

		// Create order without POReference initially
		final I_C_Order order = createOrder(orderWarehouse);
		InterfaceWrapperHelper.save(order);

		createOrderLine(order, product);

		// Create the receipt schedule (initial creation — POReference is blank)
		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> receiptSchedules = producer.createOrUpdateReceiptSchedules(order, Collections.emptyList());
		assertThat(receiptSchedules).hasSize(1);

		final I_M_ReceiptSchedule receiptSchedule = receiptSchedules.get(0);
		assertThat(receiptSchedule.getPOReference()).isNullOrEmpty();

		// Now set POReference on the order (simulating PO reactivation + edit + re-complete)
		order.setPOReference("LATE-REF-001");
		InterfaceWrapperHelper.save(order);

		// Update: since the receipt schedule's POReference is blank, it should sync from order
		final List<I_M_ReceiptSchedule> updatedSchedules = producer.createOrUpdateReceiptSchedules(order, Collections.emptyList());
		assertThat(updatedSchedules).hasSize(1);

		assertThat(updatedSchedules.get(0).getPOReference())
				.as("Blank POReference should be synced from order on update")
				.isEqualTo("LATE-REF-001");
	}
}
