package de.metas.handlingunits.material.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.material.interceptor.transactionevent.HUDescriptorFromInventoryLineService;
import de.metas.handlingunits.material.interceptor.transactionevent.HUDescriptorService;
import de.metas.handlingunits.material.interceptor.transactionevent.HUDescriptorsFromHUAssignmentService;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptor;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptorFactory;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionEventFactory;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.inout.InOutAndLineId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.util.TimeUtil.asInstant;

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

public class M_Transaction_PostTransactionEvent_InOutLineEventCreatorTest
{
	private static final BigDecimal SEVEN = new BigDecimal("7");
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal MINUS_ONE = new BigDecimal("-1");
	private static final BigDecimal MINUS_TWO = new BigDecimal("-2");
	private static final BigDecimal MINUS_SEVEN = new BigDecimal("-7");
	private static final BigDecimal MINUS_TEN = TEN.negate();

	private static final int SOME_OTHER_INOUT_LINE_ID = 30;

	private I_M_Warehouse wh;
	private I_M_Locator locator;
	private I_M_Product product;
	private Timestamp movementDate;
	private I_M_InOutLine inoutLine;

	private TransactionDescriptorFactory transactionDescriptorFactory;

	@BeforeEach
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
		inout.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		save(inout);

		inoutLine = newInstance(I_M_InOutLine.class);
		inoutLine.setM_Product_ID(product.getM_Product_ID());
		inoutLine.setM_InOut_ID(inout.getM_InOut_ID());
		save(inoutLine);

		transactionDescriptorFactory = new TransactionDescriptorFactory();
	}

	@Test
	public void createEventsForTransaction_shipment_but_not_shipmentSchedule()
	{
		final I_M_Transaction transaction = createShipmentTransaction();

		final HUDescriptorFromInventoryLineService huDescriptorFromInventoryLineService = Mockito.mock(HUDescriptorFromInventoryLineService.class);
		final ModelProductDescriptorExtractor modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
		final HUDescriptorsFromHUAssignmentService huDescriptionFactory = createM_Transaction_HuDescriptor("7");
		final TransactionEventFactory transactionEventCreator = new TransactionEventFactory(
				huDescriptionFactory,
				new ReplenishInfoRepository(),
				modelProductDescriptorExtractor,
				huDescriptorFromInventoryLineService);

		// invoke the method under test
		final List<MaterialEvent> events = transactionEventCreator
				.createEventsForTransaction(transactionDescriptorFactory.ofRecord(transaction), false);
		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);
	}

	@Test
	public void createEventsForTransaction_single_shipmentSchedule()
	{
		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_ShipmentSchedule_ID(20);
		shipmentScheduleQtyPicked.setM_InOutLine(inoutLine);
		shipmentScheduleQtyPicked.setQtyPicked(TEN);
		save(shipmentScheduleQtyPicked);

		final I_M_Transaction transaction = createShipmentTransaction();
		transaction.setM_InOutLine(inoutLine);
		save(transaction);

		final HUDescriptorFromInventoryLineService huDescriptorFromInventoryLineService = Mockito.mock(HUDescriptorFromInventoryLineService.class);
		final ModelProductDescriptorExtractor modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
		final HUDescriptorsFromHUAssignmentService huDescriptionFactory = createM_Transaction_HuDescriptor("7");
		final TransactionEventFactory transactionEventCreator = new TransactionEventFactory(
				huDescriptionFactory,
				new ReplenishInfoRepository(),
				modelProductDescriptorExtractor,
				huDescriptorFromInventoryLineService);

		// invoke the method under test
		final List<MaterialEvent> events = transactionEventCreator
				.createEventsForTransaction(transactionDescriptorFactory.ofRecord(transaction), false);

		assertThat(events).hasSize(1);
		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);
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

		final HUDescriptorFromInventoryLineService huDescriptorFromInventoryLineService = Mockito.mock(HUDescriptorFromInventoryLineService.class);
		final ModelProductDescriptorExtractor modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
		final HUDescriptorsFromHUAssignmentService huDescriptionFactory = createM_Transaction_HuDescriptor("7");
		final TransactionEventFactory transactionEventCreator = new TransactionEventFactory(
				huDescriptionFactory,
				new ReplenishInfoRepository(),
				modelProductDescriptorExtractor,
				huDescriptorFromInventoryLineService);

		//
		// invoke the method under test
		final List<MaterialEvent> events = transactionEventCreator
				.createEventsForTransaction(transactionDescriptorFactory.ofRecord(transaction), false);

		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN); // the HUs' qty takes precendence of the transaction's movementQty

	}

	private HUDescriptorsFromHUAssignmentService createM_Transaction_HuDescriptor(@NonNull final String huQty)
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				product.getM_Product_ID(),
				AttributesKey.NONE);

		final HUDescriptor huDescriptor = HUDescriptor.builder()
				.huId(10)
				.productDescriptor(productDescriptor)
				.quantity(new BigDecimal(huQty))
				.build();

		return new HUDescriptorsFromHUAssignmentService(new HUDescriptorService(), new HUTraceRepository())
		{
			public ImmutableList<HUDescriptor> createHuDescriptorsForInOutLine(@NonNull final InOutAndLineId inOutLineId, final boolean deleted)
			{
				return ImmutableList.of(huDescriptor);
			}
		};
		//		// @formatter:off
//		new Expectations(M_Transaction_HuDescriptor.class)
//		{{
//			// partial mocking - we only want to mock this one method
//			huDescriptorCreator.createHuDescriptorsForInOutLine(inoutLineId, false);
//			result = ImmutableList.of(huDescriptor);
//		}}; // @formatter:on
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

		final HUDescriptorFromInventoryLineService huDescriptorFromInventoryLineService = Mockito.mock(HUDescriptorFromInventoryLineService.class);
		final ModelProductDescriptorExtractor modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
		final HUDescriptorsFromHUAssignmentService huDescriptionFactory = createM_Transaction_HuDescriptor("7");
		final TransactionEventFactory transactionEventCreator = new TransactionEventFactory(
				huDescriptionFactory,
				new ReplenishInfoRepository(),
				modelProductDescriptorExtractor,
				huDescriptorFromInventoryLineService);

		// invoke the method under test
		final List<MaterialEvent> events = transactionEventCreator
				.createEventsForTransaction(transactionDescriptorFactory.ofRecord(transaction), false);

		assertThat(events).hasSize(1);
		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(MINUS_SEVEN);
	}

	private I_M_Transaction createShipmentTransaction()
	{
		return createTransaction(X_M_Transaction.MOVEMENTTYPE_CustomerShipment, MINUS_TEN);
	}

	@Test
	public void createEventsForTransaction_receipt()
	{
		final I_M_Transaction transaction = createReceiptTransaction();

		final HUDescriptorFromInventoryLineService huDescriptorFromInventoryLineService = Mockito.mock(HUDescriptorFromInventoryLineService.class);
		final ModelProductDescriptorExtractor modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
		final HUDescriptorsFromHUAssignmentService huDescriptionFactory = createM_Transaction_HuDescriptor("7");
		final TransactionEventFactory transactionEventCreator = new TransactionEventFactory(
				huDescriptionFactory,
				new ReplenishInfoRepository(),
				modelProductDescriptorExtractor,
				huDescriptorFromInventoryLineService);

		// invoke the method under test
		final List<MaterialEvent> events = transactionEventCreator
				.createEventsForTransaction(transactionDescriptorFactory.ofRecord(transaction), false);
		assertThat(events).hasSize(1);

		final AbstractTransactionEvent event = (AbstractTransactionEvent)events.get(0);
		assertCommon(transaction, event);
		assertThat(event.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(SEVEN);
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
		assertThat(result.getMaterialDescriptor().getWarehouseId().getRepoId()).isEqualTo(wh.getM_Warehouse_ID());
		assertThat(result.getMaterialDescriptor().getProductId()).isEqualTo(product.getM_Product_ID());
		assertThat(result.getMaterialDescriptor().getDate()).isEqualTo(asInstant(movementDate));
	}

	@Test
	public void createMaterialDescriptors_HUs_with_different_attributes()
	{
		final ProductDescriptor productDescriptor1 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), AttributesKey.NONE);
		final HUDescriptor huDescriptor1 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor1)
				.quantity(SEVEN)
				.build();

		final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(10, 20);
		final ProductDescriptor productDescriptor2 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKey);
		final HUDescriptor huDescriptor2 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor2)
				.quantity(THREE)
				.build();

		final I_M_Transaction transaction = createReceiptTransaction();
		final TransactionDescriptor transactionDescriptor = transactionDescriptorFactory.ofRecord(transaction);

		//
		// invoke the method under test
		final HUDescriptorsFromHUAssignmentService huDescriptorCreator = new HUDescriptorsFromHUAssignmentService(new HUDescriptorService(), new HUTraceRepository());
		final Map<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptors = huDescriptorCreator.newMaterialDescriptors()
				.transaction(transactionDescriptor)
				.huDescriptors(ImmutableList.of(huDescriptor1, huDescriptor2))
				.build();

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

		final ProductDescriptor productDescriptor1 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKeys, 24);
		final HUDescriptor huDescriptor1 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor1)
				.quantity(SEVEN)
				.build();

		// despite the different ASI-ID we will expect just one result.
		final ProductDescriptor productDescriptor2 = ProductDescriptor.forProductAndAttributes(product.getM_Product_ID(), attributesKeys, 25);
		final HUDescriptor huDescriptor2 = HUDescriptor.builder()
				.huId(20)
				.productDescriptor(productDescriptor2)
				.quantity(THREE)
				.build();

		final I_M_Transaction transaction = createReceiptTransaction();
		final TransactionDescriptor transactionDescriptor = transactionDescriptorFactory.ofRecord(transaction);

		// invoke the method under test
		final HUDescriptorsFromHUAssignmentService huDescriptorCreator = new HUDescriptorsFromHUAssignmentService(new HUDescriptorService(), new HUTraceRepository());
		final Map<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptors = huDescriptorCreator.newMaterialDescriptors()
				.transaction(transactionDescriptor)
				.huDescriptors(ImmutableList.of(huDescriptor1, huDescriptor2))
				.build();

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
