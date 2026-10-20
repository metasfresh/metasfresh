/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.service.AsyncBatchService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.CreateInvoiceForModelService;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.invoicecandidate.C_Order_Handler;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_EnqueueInvoiceCandidateCreation;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class InvoiceServiceTest
{
	private AsyncBatchService asyncBatchService;
	private InvoiceService invoiceService;
	private final List<Set<InvoiceCandidateId>> invoicedICIds = new ArrayList<>();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		createAsyncBatchType(C_Async_Batch_InternalName_EnqueueInvoiceCandidateCreation);
		createInvoiceCandidateHandlerForOrders();

		asyncBatchService = Mockito.mock(AsyncBatchService.class);
		Mockito.when(asyncBatchService.executeBatch(any(), any()))
				.thenAnswer(invocation -> enqueueAndProcessBatch(invocation.getArgument(0), invocation.getArgument(1)));

		// the actual invoicing of the collected invoice candidates is not what this test is about; we only capture which candidates are handed to it
		invoiceService = Mockito.spy(new InvoiceService(asyncBatchService, new CreateInvoiceForModelService(asyncBatchService)));
		Mockito.doAnswer(invocation -> {
			invoicedICIds.add(invocation.getArgument(0));
			return ImmutableSet.of();
		}).when(invoiceService).generateInvoicesFromInvoiceCandidateIds(any());
	}

	/**
	 * The shipment-lines' order has no invoice candidate yet, because its IC-creation workpackage (which is not part of any async batch) is still pending in the queue.
	 * The invoicing must nevertheless get the order line's invoice candidate.
	 */
	@Test
	void generateInvoicesFromShipmentLines_orderICsNotYetCreated()
	{
		final I_C_OrderLine orderLine = createOrderLine();
		final I_M_InOutLine shipmentLine = createShipmentLine(orderLine);

		// like when the order is completed without an async batch: the IC-creation workpackage is enqueued, but it is not (yet) processed
		CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(orderLine.getC_Order());
		assertThat(retrieveWorkpackagesWithoutAsyncBatch()).hasSize(1); // guard
		assertThat(retrieveICsForOrderLine(orderLine)).isEmpty(); // guard

		invoiceService.generateInvoicesFromShipmentLines(ImmutableList.of(shipmentLine));

		final List<I_C_Invoice_Candidate> orderLineICs = retrieveICsForOrderLine(orderLine);
		assertThat(orderLineICs).hasSize(1);
		assertThat(retrieveInvoicedICIds())
				.as("the shipped order line's invoice candidate shall be handed to the invoicing")
				.containsExactly(InvoiceCandidateId.ofRepoId(orderLineICs.get(0).getC_Invoice_Candidate_ID()));
	}

	/**
	 * The order's invoice candidates already exist; they are invoiced without any async-batch round trip.
	 */
	@Test
	void generateInvoicesFromShipmentLines_orderICsAlreadyCreated()
	{
		final I_C_OrderLine orderLine = createOrderLine();
		final I_C_Invoice_Candidate existingIC = createIC(orderLine);
		final I_M_InOutLine shipmentLine = createShipmentLine(orderLine);

		invoiceService.generateInvoicesFromShipmentLines(ImmutableList.of(shipmentLine));

		Mockito.verify(asyncBatchService, Mockito.never()).executeBatch(any(), any());
		assertThat(retrieveICsForOrderLine(orderLine)).hasSize(1);
		assertThat(retrieveInvoicedICIds()).containsExactly(InvoiceCandidateId.ofRepoId(existingIC.getC_Invoice_Candidate_ID()));
	}

	/**
	 * Fakes {@link AsyncBatchService#executeBatch(Supplier, AsyncBatchId)}: like the real async queue, the workpackages of an awaited async batch are processed before the method returns.
	 * Workpackages of other batches (or of no batch) stay pending.
	 * Processing a C_Order element mimics the C_Order IC-handler: it creates the missing invoice candidates of the order's lines.
	 */
	private static IEnqueueResult enqueueAndProcessBatch(final Supplier<IEnqueueResult> workPackageEnqueuer, final AsyncBatchId asyncBatchId)
	{
		final IEnqueueResult result = Services.get(ITrxManager.class).callInNewTrx(workPackageEnqueuer::get);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Async_Batch_ID, asyncBatchId)
				.andCollectChildren(I_C_Queue_Element.COLUMN_C_Queue_WorkPackage_ID)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Order.class))
				.create()
				.list()
				.forEach(element -> createMissingICsForOrder(element.getRecord_ID()));

		return result;
	}

	private static void createMissingICsForOrder(final int orderRepoId)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRepoId)
				.create()
				.list()
				.stream()
				.filter(orderLine -> retrieveICsForOrderLine(orderLine).isEmpty())
				.forEach(InvoiceServiceTest::createIC);
	}

	private Set<InvoiceCandidateId> retrieveInvoicedICIds()
	{
		assertThat(invoicedICIds).hasSize(1);
		return invoicedICIds.get(0);
	}

	private static List<I_C_Queue_WorkPackage> retrieveWorkpackagesWithoutAsyncBatch()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Async_Batch_ID, null)
				.create()
				.list();
	}

	private static List<I_C_Invoice_Candidate> retrieveICsForOrderLine(final I_C_OrderLine orderLine)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.create()
				.list();
	}

	private static I_C_Invoice_Candidate createIC(final I_C_OrderLine orderLine)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		ic.setRecord_ID(orderLine.getC_OrderLine_ID());
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setC_Order_ID(orderLine.getC_Order_ID());
		saveRecord(ic);
		return ic;
	}

	private static I_C_OrderLine createOrderLine()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		saveRecord(orderLine);
		return orderLine;
	}

	private static I_M_InOutLine createShipmentLine(final I_C_OrderLine orderLine)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setIsSOTrx(true);
		shipment.setC_Order_ID(orderLine.getC_Order_ID());
		saveRecord(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		saveRecord(shipmentLine);
		return shipmentLine;
	}

	/**
	 * Lets the IC-creation workpackage scheduler accept C_Order records, like in a real system.
	 */
	private static void createInvoiceCandidateHandlerForOrders()
	{
		final I_C_ILCandHandler handlerRecord = newInstance(I_C_ILCandHandler.class);
		handlerRecord.setName("C_Order");
		handlerRecord.setClassname(C_Order_Handler.class.getName());
		handlerRecord.setTableName(I_C_Order.Table_Name);
		saveRecord(handlerRecord);
	}

	private static void createAsyncBatchType(final String internalName)
	{
		final I_C_Async_Batch_Type asyncBatchType = newInstance(I_C_Async_Batch_Type.class);
		asyncBatchType.setInternalName(internalName);
		saveRecord(asyncBatchType);
	}
}
