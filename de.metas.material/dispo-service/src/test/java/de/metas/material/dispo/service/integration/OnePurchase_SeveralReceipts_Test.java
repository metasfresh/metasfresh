package de.metas.material.dispo.service.integration;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.AbstractObjectAssert;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerAndLogRequest;
import de.metas.inout.InOutAndLineId;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.dispo.service.event.handler.TransactionEventHandler;
import de.metas.material.dispo.service.event.handler.receiptschedule.ReceiptsScheduleCreatedHandler;
import de.metas.material.dispo.service.event.handler.receiptschedule.ReceiptsScheduleUpdatedHandler;
import de.metas.material.dispo.service.integration.OnePurchase_SeveralReceipts_Test.AfterTest;
import de.metas.material.event.MaterialEventHandlerRegistry;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import lombok.Builder;

/*
 * #%L
 * metasfresh-material-dispo-service
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

@ExtendWith(AfterTest.class)
public class OnePurchase_SeveralReceipts_Test
{
	private MaterialEventHandlerRegistry materialEventHandlerRegistry;

	private EventDescriptor eventDescriptor;
	private WarehouseId warehouseId;
	private int productId;
	private int receiptScheduleId;
	private AttributeId monthsUntilExpiryAttributeId;
	private MaterialDescriptor receiptScheduleMaterialDescriptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		setupServices();
	}

	static class AfterTest implements AfterTestExecutionCallback
	{
		@Override
		public void afterTestExecution(final ExtensionContext context)
		{
			dumpAllCandidates("Candidates after " + context.getDisplayName());
		}
	}

	private void setupServices()
	{
		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		final EventLogUserService eventLogUserService = createEventLogUserService();

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final Collection<CandidateHandler> candidateChangeHandlers = ImmutableList.of(new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService));
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateChangeHandlers);

		final ReceiptsScheduleCreatedHandler receiptsScheduleCreatedHandler = new ReceiptsScheduleCreatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
		final ReceiptsScheduleUpdatedHandler receiptsScheduleUpdatedHandler = new ReceiptsScheduleUpdatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
		final TransactionEventHandler transactionEventHandler = new TransactionEventHandler(candidateChangeHandler, candidateRepositoryRetrieval, postMaterialEventService);

		materialEventHandlerRegistry = new MaterialEventHandlerRegistry(
				Optional.of(ImmutableList.of(receiptsScheduleCreatedHandler, receiptsScheduleUpdatedHandler, transactionEventHandler)),
				eventLogUserService);
	}

	private EventLogUserService createEventLogUserService()
	{
		final EventLogUserService eventLogUserService = Mockito.spy(EventLogUserService.class);
		Mockito.doAnswer(invocation -> {
			final InvokeHandlerAndLogRequest request = (InvokeHandlerAndLogRequest)invocation.getArguments()[0];
			request.getInvokaction().run();
			return null; // void
		})
				.when(eventLogUserService)
				.invokeHandlerAndLog(ArgumentMatchers.any());

		return eventLogUserService;
	}

	private AttributeId createNumericAttribute(final String name)
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number);
		saveRecord(attribute);

		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	private AttributesKey monthsUntilExpiryAttributesKey(final int monthsUntilExpiry)
	{
		return AttributesKey.ofParts(AttributesKeyPart.ofNumberAttribute(monthsUntilExpiryAttributeId, new BigDecimal(monthsUntilExpiry)));
	}

	private static void dumpAllCandidates(final String description)
	{
		System.out.println("----[ " + description + "]--------------------------------------------------------------------");
		DispoTestUtils.retrieveAllRecords()
				.stream()
				.map(record -> toString(record))
				.forEach(line -> System.out.println("\t" + line));
		System.out.println("\n");
	}

	private static String toString(final I_MD_Candidate record)
	{
		return "id=" + record.getMD_Candidate_ID()
				+ ", type=" + record.getMD_Candidate_Type()
				+ ", storageAttributesKey=" + record.getStorageAttributesKey()
				+ ", qty=" + record.getQty()
				+ ", dateProjected=" + TimeUtil.asInstant(record.getDateProjected());
	}

	private AbstractObjectAssert<?, I_MD_Candidate> assertStockCandidate(
			final AttributesKey storageAttributesKey,
			final String dateProjected)
	{
		final Timestamp dateProjectedTS = TimeUtil.asTimestamp(Instant.parse(dateProjected));

		final ArrayList<I_MD_Candidate> result = new ArrayList<>();
		for (final I_MD_Candidate candidate : DispoTestUtils.retrieveAllRecords())
		{
			if (!CandidateType.STOCK.getCode().equals(candidate.getMD_Candidate_Type()))
			{
				continue;
			}
			if (!storageAttributesKey.getAsString().equals(candidate.getStorageAttributesKey()))
			{
				continue;
			}
			if (!dateProjectedTS.equals(candidate.getDateProjected()))
			{
				continue;
			}

			result.add(candidate);
		}

		assertThat(result)
				.as("candidates for storageAttributesKey=" + storageAttributesKey + " and dateProjected=" + dateProjected)
				.hasSize(1);

		return assertThat(result.get(0));
	}

	@Builder(builderMethodName = "prepareMasterdata", buildMethodName = "initialize", builderClassName = "MasterdataBuilder")
	private void setupMasterdata(
			final int qtyOrdered,
			final String datePromised)
	{
		eventDescriptor = EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID);
		warehouseId = WarehouseId.ofRepoId(55);
		productId = 121212;
		receiptScheduleId = 111;
		monthsUntilExpiryAttributeId = createNumericAttribute("MonthsUntilExpiry");
		receiptScheduleMaterialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(ProductDescriptor.forProductAndAttributes(
						productId, // productId
						AttributesKey.NONE,
						AttributeSetInstanceId.NONE.getRepoId()))
				.warehouseId(warehouseId)
				.customerId(null)
				.quantity(BigDecimal.valueOf(qtyOrdered))
				.date(Instant.parse(datePromised))
				.build();
	}

	@Builder(builderMethodName = "prepareReceiptScheduleCreatedEvent", buildMethodName = "fire", builderClassName = "ReceiptScheduleCreatedEventBuilder")
	private void fireReceiptScheduleCreatedEvent(
			final int reservedQuantity)
	{
		materialEventHandlerRegistry.onEvent(
				ReceiptScheduleCreatedEvent.builder()
						.eventDescriptor(eventDescriptor)
						.orderLineDescriptor(OrderLineDescriptor.builder().build())
						.materialDescriptor(receiptScheduleMaterialDescriptor)
						.reservedQuantity(BigDecimal.valueOf(reservedQuantity))
						.receiptScheduleId(receiptScheduleId)
						.build());
		dumpAllCandidates("After ReceiptScheduleCreatedEvent: reservedQuantity=" + reservedQuantity);
	}

	@Builder(builderMethodName = "prepareReceiptScheduleUpdatedEvent", buildMethodName = "fire", builderClassName = "ReceiptScheduleUpdatedEventBuilder")
	private void fireReceiptScheduleUpdatedEvent(
			final int reservedQuantity,
			final int reservedQuantityDelta)
	{
		materialEventHandlerRegistry.onEvent(
				ReceiptScheduleUpdatedEvent.builder()
						.eventDescriptor(eventDescriptor)
						.materialDescriptor(receiptScheduleMaterialDescriptor)
						.orderedQuantityDelta(new BigDecimal("0"))
						.reservedQuantity(BigDecimal.valueOf(reservedQuantity))
						.reservedQuantityDelta(BigDecimal.valueOf(reservedQuantityDelta))
						.receiptScheduleId(receiptScheduleId)
						.build());
		dumpAllCandidates("After ReceiptScheduleCreatedEvent: reservedQuantity=" + reservedQuantity + ", reservedQuantityDelta=" + reservedQuantityDelta);
	}

	@Builder(builderMethodName = "prepareTransactionCreatedEvent", buildMethodName = "fire", builderClassName = "TransactionCreatedEventBuilder")
	private void fireTransactionCreatedEvent(
			final String date,
			final int qty,
			final int monthsUntilExpiry)
	{
		final BigDecimal qtyBD = BigDecimal.valueOf(qty);

		final ProductDescriptor transactionProductDescriptor = ProductDescriptor.forProductAndAttributes(
				productId, // productId
				monthsUntilExpiryAttributesKey(monthsUntilExpiry),
				12345 // dummy ASI
		);

		materialEventHandlerRegistry.onEvent(TransactionCreatedEvent.builder()
				.eventDescriptor(eventDescriptor)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(transactionProductDescriptor)
						.warehouseId(warehouseId)
						.customerId(null)
						.quantity(qtyBD)
						.date(Instant.parse(date))
						.build())
				.receiptId(InOutAndLineId.ofRepoId(1, 2))
				.transactionId(1)
				.receiptScheduleIdsQty(receiptScheduleId, qtyBD)
				.huOnHandQtyChangeDescriptors(ImmutableList.of(
						HUDescriptor.builder()
								.productDescriptor(transactionProductDescriptor)
								.huId(11112222) // dummy
								.quantity(qtyBD)
								.quantityDelta(qtyBD)
								.build()))
				.build());
		dumpAllCandidates("After TransactionCreatedEvent: date=" + date + ", qty=" + qty + ", monthsUntilExpiry=" + monthsUntilExpiry);
	}

	@Builder(builderMethodName = "prepareMaterialReceiptEvents", buildMethodName = "fire", builderClassName = "MaterialReceiptEventsBuilder")
	private void fireMaterialReceiptEvents(
			final String date,
			final int qtyReceived,
			final int monthsUntilExpiry,
			final int receiptScheduleRemainingReservedQuantity)
	{
		prepareReceiptScheduleUpdatedEvent()
				.reservedQuantity(receiptScheduleRemainingReservedQuantity)
				.reservedQuantityDelta(-qtyReceived)
				.fire();

		prepareTransactionCreatedEvent()
				.date(date)
				.qty(qtyReceived)
				.monthsUntilExpiry(monthsUntilExpiry)
				.fire();
	}

	@Test
	public void receive_1item_then_2items()
	{
		prepareMasterdata()
				.qtyOrdered(10000)
				.datePromised("2019-10-31T23:59:59Z")
				.initialize();

		prepareReceiptScheduleCreatedEvent()
				.reservedQuantity(10000)
				.fire();
		assertStockCandidate(AttributesKey.NONE, "2019-10-31T23:59:59Z")
				.returns(new BigDecimal("10000"), I_MD_Candidate::getQty);

		//
		// Receive on 1 item with monthsUntilExpiry=1
		prepareMaterialReceiptEvents()
				.date("2019-11-01T13:00:00Z")
				.qtyReceived(1)
				.monthsUntilExpiry(1)
				.receiptScheduleRemainingReservedQuantity(9999)
				.fire();
		assertStockCandidate(AttributesKey.NONE, "2019-10-31T23:59:59Z")
				.returns(new BigDecimal("9999"), I_MD_Candidate::getQty);
		assertStockCandidate(monthsUntilExpiryAttributesKey(1), "2019-11-01T13:00:00Z")
				.returns(new BigDecimal("1"), I_MD_Candidate::getQty);

		//
		// Receive on 2 items with monthsUntilExpiry=4
		prepareMaterialReceiptEvents()
				.date("2019-11-01T13:01:00Z")
				.qtyReceived(2)
				.monthsUntilExpiry(4)
				.receiptScheduleRemainingReservedQuantity(9997)
				.fire();
		assertStockCandidate(AttributesKey.NONE, "2019-10-31T23:59:59Z")
				.returns(new BigDecimal("9997"), I_MD_Candidate::getQty);
		assertStockCandidate(monthsUntilExpiryAttributesKey(4), "2019-11-01T13:01:00Z")
				.returns(new BigDecimal("2"), I_MD_Candidate::getQty);

		//
		// Receive on 10 item with monthsUntilExpiry=1, same date as the first receipt
		// TODO: following case is not working
		// prepareMaterialReceiptEvents()
		// .date("2019-11-01T13:00:00Z")
		// .qtyReceived(100)
		// .monthsUntilExpiry(1)
		// .receiptScheduleRemainingReservedQuantity(9897)
		// .fire();
		// assertStockCandidate(AttributesKey.NONE, "2019-10-31T23:59:59Z")
		// .returns(new BigDecimal("9897"), I_MD_Candidate::getQty);
		// assertStockCandidate(monthsUntilExpiryAttributesKey(1), "2019-11-01T13:00:00Z")
		// .returns(new BigDecimal("100"), I_MD_Candidate::getQty);
	}
}
