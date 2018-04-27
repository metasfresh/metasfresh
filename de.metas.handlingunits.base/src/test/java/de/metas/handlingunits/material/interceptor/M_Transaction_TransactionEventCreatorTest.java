package de.metas.handlingunits.material.interceptor;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
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

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import lombok.NonNull;
import mockit.Expectations;
import mockit.Mocked;

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
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class })
public class M_Transaction_TransactionEventCreatorTest
{

	private static final BigDecimal SEVEN = new BigDecimal("7");;
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal ONE = BigDecimal.ONE;
	private static final BigDecimal MINUS_ONE = ONE.negate();
	private static final BigDecimal MINUS_TWO = new BigDecimal("-2");
	private static final BigDecimal MINUS_SEVEN = new BigDecimal("-7");
	private static final BigDecimal MINUS_TEN = BigDecimal.TEN.negate();

	private static final int SOME_OTHER_INOUT_LINE_ID = 30;

	private I_M_Warehouse wh;
	private I_M_Locator locator;
	private I_M_Product product;
	private Timestamp movementDate;
	private I_M_InOutLine inoutLine;

	@Mocked
	M_Transaction_HuDescriptor huDescriptorCreator;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		wh = BusinessTestHelper.createWarehouse("wh");
		locator = BusinessTestHelper.createLocator("l", wh);
		product = BusinessTestHelper.createProduct("product", BusinessTestHelper.createUomEach());
		movementDate = SystemTime.asTimestamp();

		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		save(bPartner);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setC_BPartner(bPartner);
		save(inout);

		inoutLine = newInstance(I_M_InOutLine.class);
		inoutLine.setM_Product(product);
		inoutLine.setM_InOut(inout);
		save(inoutLine);

	}

	@Test
	public void createEventsForTransaction_shipment_but_not_shipmentSchedule()
	{
		final I_M_Transaction transaction = createShipmentTransaction();

		setupSingleHuDescriptor(SEVEN);

		// invoke the method under test
		final List<MaterialEvent> events = M_Transaction_TransactionEventCreator.INSTANCE
				.createEventsForTransaction(TransactionDescriptor.ofRecord(transaction), false);
		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);
		assertThat(event.getShipmentScheduleIds2Qtys()).hasSize(0);
	}

	@Test
	public void createEventsForTransaction_single_shipmentSchedule()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine(inoutLine);
		shipmentScheduleQtyPicked.setQtyPicked(BigDecimal.TEN);
		save(shipmentScheduleQtyPicked);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine(inoutLine);
		save(transaction);

		setupSingleHuDescriptor(SEVEN);

		// invoke the method under test
		final List<MaterialEvent> events = M_Transaction_TransactionEventCreator.INSTANCE
				.createEventsForTransaction(TransactionDescriptor.ofRecord(transaction), false);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);

		assertThat(event.getShipmentScheduleIds2Qtys()).hasSize(1);
		final Entry<Integer, BigDecimal> entry = event.getShipmentScheduleIds2Qtys().entrySet().iterator().next();
		assertThat(entry.getKey()).isEqualTo(20);
		assertThat(entry.getValue()).isEqualByComparingTo(MINUS_TEN);
	}

	@Test
	public void createEventsForTransaction_single_shipmentSchedule_with_partial_quantity()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine(inoutLine);
		shipmentScheduleQtyPicked.setQtyPicked(ONE);
		save(shipmentScheduleQtyPicked);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine(inoutLine);
		save(transaction);

		setupSingleHuDescriptor(SEVEN);

		//
		// invoke the method under test
		final List<MaterialEvent> events = M_Transaction_TransactionEventCreator.INSTANCE
				.createEventsForTransaction(TransactionDescriptor.ofRecord(transaction), false);

		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN); // the HUs' qty takes precendence of the transaction's movementQty

		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = event.getShipmentScheduleIds2Qtys();
		assertThat(shipmentScheduleIds2Qtys).containsOnly(entry(20, MINUS_ONE));

	}

	private void setupSingleHuDescriptor(
			@NonNull final BigDecimal huQty)
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				product.getM_Product_ID(),
				AttributesKey.NONE);

		final HUDescriptor huDescriptor = HUDescriptor.builder()
				.huId(10)
				.productDescriptor(productDescriptor)
				.quantity(huQty)
				.quantityDelta(huQty)
				.build();

		// @formatter:off
		new Expectations()
		{{
			huDescriptorCreator.createHuDescriptorsForInOutLine(inoutLine, false);
			result = ImmutableList.of(huDescriptor);
		}}; // @formatter:on
	}

	@Test
	public void createEventsForTransaction_multiple_shipmentSchedules_with_partial_quantity()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine(inoutLine);
		shipmentScheduleQtyPicked.setQtyPicked(ONE);
		save(shipmentScheduleQtyPicked);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked2 = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked2.setM_ShipmentSchedule_ID(21);
		shipmentScheduleQtyPicked2.setM_InOutLine(inoutLine);
		shipmentScheduleQtyPicked2.setQtyPicked(TWO);
		save(shipmentScheduleQtyPicked2);

		// this one references another iol - needs to be ignroed for this M_Transaction
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked3 = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked3.setM_ShipmentSchedule_ID(21);
		shipmentScheduleQtyPicked3.setM_InOutLine_ID(SOME_OTHER_INOUT_LINE_ID);
		shipmentScheduleQtyPicked3.setQtyPicked(THREE);
		save(shipmentScheduleQtyPicked3);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine(inoutLine);
		save(transaction);

		setupSingleHuDescriptor(SEVEN);

		// invoke the method under test
		final List<MaterialEvent> events = M_Transaction_TransactionEventCreator.INSTANCE
				.createEventsForTransaction(TransactionDescriptor.ofRecord(transaction), false);

		assertThat(events).hasSize(1);
		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);

		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = event.getShipmentScheduleIds2Qtys();
		assertThat(shipmentScheduleIds2Qtys).containsOnly(
				entry(20, MINUS_ONE),
				entry(21, MINUS_TWO));
	}

	private I_M_Transaction createShipmentTransaction()
	{
		return createTransaction(X_M_Transaction.MOVEMENTTYPE_CustomerShipment, MINUS_TEN);
	}

	@Test
	public void createEventsForTransaction_receipt()
	{
		final I_M_Transaction transaction = createReceiptTransaction();

		setupSingleHuDescriptor(SEVEN);

		// invoke the method under test
		final List<MaterialEvent> events = M_Transaction_TransactionEventCreator.INSTANCE
				.createEventsForTransaction(TransactionDescriptor.ofRecord(transaction), false);
		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(SEVEN);
		assertThat(event.getShipmentScheduleIds2Qtys()).hasSize(0);
	}

	private I_M_Transaction createReceiptTransaction()
	{
		return createTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, TEN);
	}

	private I_M_Transaction createTransaction(
			@NonNull final String movementType,
			@NonNull final BigDecimal movementQty)
	{
		final I_M_Transaction transaction = newInstance(I_M_Transaction.class);
		transaction.setM_Locator(locator);
		transaction.setMovementType(movementType);
		transaction.setM_Product(product);
		transaction.setMovementDate(movementDate);
		transaction.setM_InOutLine(inoutLine);
		transaction.setMovementQty(movementQty);
		save(transaction);
		return transaction;
	}

	private void assertCommon(final I_M_Transaction transaction, final AbstractTransactionEvent result)
	{
		assertThat(result).isNotNull();
		assertThat(result.getTransactionId()).isEqualTo(transaction.getM_Transaction_ID());
		assertThat(result.getMaterialDescriptor().getWarehouseId()).isEqualTo(wh.getM_Warehouse_ID());
		assertThat(result.getMaterialDescriptor().getProductId()).isEqualTo(product.getM_Product_ID());
		assertThat(result.getMaterialDescriptor().getDate()).isEqualTo(movementDate);
	}

	@Test
	public void createMaterialDescriptors_HUs_with_different_attributes()
	{
		final ProductDescriptor productDescriptor1 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), AttributesKey.NONE);
		final HUDescriptor huDescriptor1 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor1)
				.quantity(SEVEN)
				.quantityDelta(SEVEN)
				.build();

		final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(10, 20);
		final ProductDescriptor productDescriptor2 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKey);
		final HUDescriptor huDescriptor2 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor2)
				.quantity(THREE)
				.quantityDelta(THREE)
				.build();

		final I_M_Transaction transaction = createReceiptTransaction();
		final TransactionDescriptor transactionDescriptor = TransactionDescriptor.ofRecord(transaction);

		//
		// invoke the method under test
		final Map<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptors = M_Transaction_TransactionEventCreator
				.createMaterialDescriptors(
						transactionDescriptor,
						0, // bPartnerId
						ImmutableList.of(huDescriptor1, huDescriptor2));

		final Set<Entry<MaterialDescriptor, Collection<HUDescriptor>>> entrySet = materialDescriptors.entrySet();
		assertThat(entrySet).hasSize(2);

		assertThat(entrySet)
				.filteredOn(e -> e.getKey().getStorageAttributesKey().equals(AttributesKey.NONE))
				.hasSize(1)
				.allSatisfy(singleEntry -> {
					assertThat(singleEntry.getKey().getProductId()).isEqualTo(product.getM_Product_ID());
					assertThat(singleEntry.getKey().getQuantity()).isEqualByComparingTo(SEVEN);
					assertThat(singleEntry.getValue()).containsExactly(huDescriptor1);
				});

		assertThat(entrySet)
				.filteredOn(e -> e.getKey().getStorageAttributesKey().equals(attributesKey))
				.hasSize(1)
				.allSatisfy(singleEntry -> {
					assertThat(singleEntry.getKey().getProductId()).isEqualTo(product.getM_Product_ID());
					assertThat(singleEntry.getKey().getQuantity()).isEqualByComparingTo(THREE);
					assertThat(singleEntry.getValue()).containsExactly(huDescriptor2);
				});
	}

	@Test
	public void createMaterialDescriptors_HUs_with_equal_attributes()
	{
		final AttributesKey attributesKeys = AttributesKey.ofAttributeValueIds(10, 20);

		final ProductDescriptor productDescriptor1 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKeys);
		final HUDescriptor huDescriptor1 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor1)
				.quantity(SEVEN)
				.quantityDelta(SEVEN)
				.build();

		final ProductDescriptor productDescriptor2 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKeys);
		final HUDescriptor huDescriptor2 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor2)
				.quantity(THREE)
				.quantityDelta(THREE)
				.build();

		final I_M_Transaction transaction = createReceiptTransaction();
		final TransactionDescriptor transactionDescriptor = TransactionDescriptor.ofRecord(transaction);

		//
		// invoke the method under test
		final Map<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptors = M_Transaction_TransactionEventCreator
				.createMaterialDescriptors(
						transactionDescriptor,
						0, // bpartnerId
						ImmutableList.of(huDescriptor1, huDescriptor2));

		final Set<Entry<MaterialDescriptor, Collection<HUDescriptor>>> entrySet = materialDescriptors.entrySet();
		assertThat(entrySet).hasSize(1);

		final Entry<MaterialDescriptor, Collection<HUDescriptor>> singleEntry = entrySet.iterator().next();
		assertThat(singleEntry.getKey().getProductId()).isEqualTo(product.getM_Product_ID());
		assertThat(singleEntry.getKey().getStorageAttributesKey()).isEqualTo(attributesKeys);
		assertThat(singleEntry.getKey().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(singleEntry.getValue()).hasSize(2);
		assertThat(singleEntry.getValue()).containsExactlyInAnyOrder(huDescriptor1, huDescriptor2);
	}
}
