package de.metas.material.model.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.material.event.transactions.TransactionCreatedEvent;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class })
public class M_TransactionTest
{

	private static final int INOUT_LINE_ID = 30;
	private I_M_Warehouse wh;
	private I_M_Locator locator;
	private I_M_Product product;
	private Timestamp movementDate;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		wh = BusinessTestHelper.createWarehouse("wh");
		locator = BusinessTestHelper.createLocator("l", wh);
		product = BusinessTestHelper.createProduct("product", BusinessTestHelper.createUomEach());
		movementDate = SystemTime.asTimestamp();
	}

	@Test
	public void createTransactionEvent()
	{
		final I_M_Transaction transaction = createShipmentTransaction();

		final List<TransactionCreatedEvent> events = M_Transaction.createTransactionEvents(transaction, ModelChangeType.AFTER_NEW);
		final TransactionCreatedEvent event = events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-10");
		assertThat(event.getShipmentScheduleId()).isZero();
	}

	@Test
	public void createTransactionEvent_single_shipmentSchedule()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine_ID(INOUT_LINE_ID);
		shipmentScheduleQtyPicked.setQtyPicked(BigDecimal.TEN);
		save(shipmentScheduleQtyPicked);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine_ID(INOUT_LINE_ID);
		save(transaction);

		final List<TransactionCreatedEvent> events = M_Transaction.createTransactionEvents(transaction, ModelChangeType.AFTER_NEW);
		final TransactionCreatedEvent event = events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-10");
		assertThat(event.getShipmentScheduleId()).isEqualTo(20);
	}

	@Test
	public void createTransactionEvent_single_shipmentSchedule_with_partial_quantity()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine_ID(INOUT_LINE_ID);
		shipmentScheduleQtyPicked.setQtyPicked(BigDecimal.ONE);
		save(shipmentScheduleQtyPicked);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine_ID(INOUT_LINE_ID);
		save(transaction);

		final List<TransactionCreatedEvent> events = M_Transaction.createTransactionEvents(transaction, ModelChangeType.AFTER_NEW);
		assertThat(events).hasSize(2);

		final TransactionCreatedEvent event = events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-9");
		assertThat(event.getShipmentScheduleId()).isZero();

		final TransactionCreatedEvent event1 = events.get(1);
		assertCommon(transaction, event1);
		assertThat(event1.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-1");
		assertThat(event1.getShipmentScheduleId()).isEqualTo(20);
	}

	@Test
	public void createTransactionEvent_multiple_shipmentSchedules_with_partial_quantity()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine_ID(INOUT_LINE_ID);
		shipmentScheduleQtyPicked.setQtyPicked(BigDecimal.ONE);
		save(shipmentScheduleQtyPicked);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked2 = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked2.setM_ShipmentSchedule_ID(21);
		shipmentScheduleQtyPicked2.setM_InOutLine_ID(INOUT_LINE_ID);
		shipmentScheduleQtyPicked2.setQtyPicked(new BigDecimal("2"));
		save(shipmentScheduleQtyPicked2);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked3 = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked3.setM_ShipmentSchedule_ID(21);
		shipmentScheduleQtyPicked3.setM_InOutLine_ID(INOUT_LINE_ID + 30); // references another iol
		shipmentScheduleQtyPicked3.setQtyPicked(new BigDecimal("3"));
		save(shipmentScheduleQtyPicked3);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine_ID(INOUT_LINE_ID);
		save(transaction);

		final List<TransactionCreatedEvent> events = M_Transaction.createTransactionEvents(transaction, ModelChangeType.AFTER_NEW);
		assertThat(events).hasSize(3);

		final TransactionCreatedEvent event = events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-7");
		assertThat(event.getShipmentScheduleId()).isZero();

		final TransactionCreatedEvent event1 = events.get(1);
		assertCommon(transaction, event1);
		assertThat(event1.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-1");
		assertThat(event1.getShipmentScheduleId()).isEqualTo(20);

		final TransactionCreatedEvent event2 = events.get(2);
		assertCommon(transaction, event2);
		assertThat(event2.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("-2");
		assertThat(event2.getShipmentScheduleId()).isEqualTo(21);
	}

	private I_M_Transaction createShipmentTransaction()
	{
		final I_M_Transaction transaction = newInstance(I_M_Transaction.class);
		transaction.setM_Locator(locator);
		transaction.setMovementType(X_M_Transaction.MOVEMENTTYPE_CustomerShipment);
		transaction.setM_Product(product);
		transaction.setMovementDate(movementDate);
		transaction.setMovementQty(BigDecimal.TEN.negate());
		save(transaction);
		return transaction;
	}

	private void assertCommon(final I_M_Transaction transaction, final TransactionCreatedEvent result)
	{
		assertThat(result).isNotNull();
		assertThat(result.getTransactionId()).isEqualTo(transaction.getM_Transaction_ID());
		assertThat(result.getMaterialDescriptor().getWarehouseId()).isEqualTo(wh.getM_Warehouse_ID());
		assertThat(result.getMaterialDescriptor().getProductId()).isEqualTo(product.getM_Product_ID());
		assertThat(result.getMaterialDescriptor().getDate()).isEqualTo(movementDate);
	}
}
