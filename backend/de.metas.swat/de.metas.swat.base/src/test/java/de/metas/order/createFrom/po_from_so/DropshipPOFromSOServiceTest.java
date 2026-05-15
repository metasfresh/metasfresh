/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.createFrom.po_from_so;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregator;
import de.metas.order.model.I_C_Order;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DropshipPOFromSOService}.
 *
 * <p>Design note: {@link CreatePOFromSOsAggregator} is instantiated inside the service with {@code new}
 * and its {@code closeAllGroups()} method is {@code final} (inherited from {@link de.metas.util.collections.MapReduceAggregator}),
 * so it cannot be spied or verified by Mockito. The approach here is:
 * <ul>
 *   <li>A minimal {@link TestableDropshipPOFromSOService} subclass overrides the two protected factory methods
 *       (extracted as part of the required minimal refactor in {@link DropshipPOFromSOService}) to capture
 *       the constructor arguments.</li>
 *   <li>A {@link SpyAggregator} anonymous subclass overrides the non-final {@link CreatePOFromSOsAggregator#getSkippedLinesMessage()}
 *       to inject test-controlled values and count calls (test c, d).</li>
 * </ul>
 * </p>
 */
class DropshipPOFromSOServiceTest
{
	private IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO;
	private IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL;
	private IDocumentBL documentBL;

	/** Testable subclass that captures factory arguments. */
	private TestableDropshipPOFromSOService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register mocks BEFORE creating the service — its field initializers call Services.get(...).
		orderCreatePOFromSOsDAO = mock(IC_Order_CreatePOFromSOsDAO.class);
		orderCreatePOFromSOsBL = mock(IC_Order_CreatePOFromSOsBL.class);
		documentBL = mock(IDocumentBL.class);

		Services.registerService(IC_Order_CreatePOFromSOsDAO.class, orderCreatePOFromSOsDAO);
		Services.registerService(IC_Order_CreatePOFromSOsBL.class, orderCreatePOFromSOsBL);
		Services.registerService(IDocumentBL.class, documentBL);

		when(orderCreatePOFromSOsBL.getConfigPurchaseQtySource()).thenReturn("QtyOrdered");
		when(orderCreatePOFromSOsDAO.retrieveOrderLines(any(), anyBoolean(), anyString()))
				.thenReturn(Collections.emptyList());

		service = new TestableDropshipPOFromSOService();
	}

	/** Creates a persisted in-memory sales order and returns the instance. */
	private I_C_Order createSalesOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		saveRecord(order);
		return order;
	}

	// -----------------------------------------------------------------------
	// Test (a): Passes PurchaseTypeEnum.DROPSHIP to the aggregator constructor
	// -----------------------------------------------------------------------

	/**
	 * Verifies that {@link PurchaseTypeEnum#DROPSHIP} is passed as the purchase type to the aggregator.
	 * Captured via the {@link TestableDropshipPOFromSOService#capturedPurchaseType} field.
	 */
	@Test
	void createDropshipPOForSO_passesDROPSHIPTypeToAggregator()
	{
		// Given
		final I_C_Order salesOrder = createSalesOrder();

		// When
		service.createDropshipPOForSO(salesOrder);

		// Then
		assertThat(service.capturedPurchaseType)
				.as("Aggregator must be constructed with PurchaseTypeEnum.DROPSHIP")
				.isEqualTo(PurchaseTypeEnum.DROPSHIP);
	}

	// -----------------------------------------------------------------------
	// Test (b): Passes IsVendorInOrderLinesRequired=true to the key builder
	// -----------------------------------------------------------------------

	/**
	 * Verifies that {@code isVendorInOrderLinesRequired=true} is passed to the
	 * {@link CreatePOFromSOsAggregationKeyBuilder} constructor.
	 */
	@Test
	void createDropshipPOForSO_passesIsVendorRequiredTrueToKeyBuilder()
	{
		// Given
		final I_C_Order salesOrder = createSalesOrder();

		// When
		service.createDropshipPOForSO(salesOrder);

		// Then
		assertThat(service.capturedIsVendorInOrderLinesRequired)
				.as("isVendorInOrderLinesRequired must be true")
				.isTrue();
	}

	// -----------------------------------------------------------------------
	// Test (c): closeAllGroups is called exactly once per SO
	// -----------------------------------------------------------------------

	/**
	 * Verifies that {@code closeAllGroups()} is called exactly once per SO.
	 *
	 * <p>Since {@code closeAllGroups()} is {@code final} in {@link de.metas.util.collections.MapReduceAggregator},
	 * it cannot be overridden directly. We verify indirectly: the service calls {@code getSkippedLinesMessage()}
	 * (which is non-final) exactly once, immediately after {@code closeAllGroups()}. The
	 * {@link SpyAggregator} counts those calls.</p>
	 */
	@Test
	void createDropshipPOForSO_callsCloseAllGroupsExactlyOnce()
	{
		// Given
		final I_C_Order salesOrder = createSalesOrder();

		// When
		service.createDropshipPOForSO(salesOrder);

		// Then: getSkippedLinesMessage is called once — it sits immediately after closeAllGroups() in the service
		assertThat(service.capturedSpyAggregator.getSkippedLinesMessageCallCount)
				.as("getSkippedLinesMessage (called right after closeAllGroups) must be called exactly once")
				.isEqualTo(1);
	}

	// -----------------------------------------------------------------------
	// Test (d): When getSkippedLinesMessage returns non-empty, an AdempiereException is thrown
	// -----------------------------------------------------------------------

	@Test
	void createDropshipPOForSO_throwsExceptionWhenSkippedLinesMessagePresent()
	{
		// Given
		final I_C_Order salesOrder = createSalesOrder();
		final String skippedMsg = "SO-12345-10, SO-12345-20";
		service.skippedLinesMessageToReturn = Optional.of(skippedMsg);

		// When / Then
		assertThatThrownBy(() -> service.createDropshipPOForSO(salesOrder))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(skippedMsg);
	}

	// -----------------------------------------------------------------------
	// Test (e): IDocumentBL.processEx is called once for a DR purchase order
	// -----------------------------------------------------------------------

	/**
	 * Verifies that {@link IDocumentBL#processEx} is called exactly once with
	 * {@code ACTION_Complete} / {@code STATUS_Completed} for a purchase order that
	 * was created (DocStatus=DR) and linked back to the sales order.
	 *
	 * <p>The test creates a minimal in-memory PO record with DocStatus=DR and
	 * Link_Order_ID pointing to the sales order, which is exactly what the aggregator
	 * produces. The service's post-aggregation query should find it and complete it.</p>
	 */
	@Test
	void createDropshipPOForSO_callsProcessExOnDraftedPO()
	{
		// Given: a sales order
		final I_C_Order salesOrder = createSalesOrder();

		// and a draft PO linked to it (simulates what the aggregator produces)
		final I_C_Order draftPO = newInstance(I_C_Order.class);
		draftPO.setIsSOTrx(false);
		draftPO.setLink_Order_ID(salesOrder.getC_Order_ID());
		draftPO.setDocStatus(IDocument.STATUS_Drafted);
		saveRecord(draftPO);

		// When
		service.createDropshipPOForSO(salesOrder);

		// Then: processEx was called exactly once on the draft PO
		verify(documentBL).processEx(eq(draftPO), eq(IDocument.ACTION_Complete), eq(IDocument.STATUS_Completed));
	}

	/**
	 * Verifies that {@link IDocumentBL#processEx} is NOT called when skipped lines
	 * are detected — the exception path must not attempt auto-completion.
	 */
	@Test
	void createDropshipPOForSO_doesNotCallProcessExWhenSkippedLinesPresent()
	{
		// Given
		final I_C_Order salesOrder = createSalesOrder();
		service.skippedLinesMessageToReturn = Optional.of("SO-line-10");

		// When / Then: exception is thrown
		assertThatThrownBy(() -> service.createDropshipPOForSO(salesOrder))
				.isInstanceOf(AdempiereException.class);

		// and processEx was never called
		verify(documentBL, never()).processEx(any(), anyString(), anyString());
	}

	// -----------------------------------------------------------------------
	// Testable subclass + spy aggregator
	// -----------------------------------------------------------------------

	/**
	 * Minimal testable subclass that intercepts the protected factory methods in
	 * {@link DropshipPOFromSOService} to capture constructor arguments and inject spy aggregators.
	 */
	static class TestableDropshipPOFromSOService extends DropshipPOFromSOService
	{
		/** The purchase type passed to the aggregator constructor (test a). */
		PurchaseTypeEnum capturedPurchaseType;
		/** The isVendorInOrderLinesRequired flag passed to the key builder (test b). */
		boolean capturedIsVendorInOrderLinesRequired;
		/** Reference to the spy aggregator created for the current test run (tests c, d). */
		SpyAggregator capturedSpyAggregator;

		/** Controls what getSkippedLinesMessage() returns. Default: empty (no skipped lines). */
		Optional<String> skippedLinesMessageToReturn = Optional.empty();

		@Override
		protected CreatePOFromSOsAggregator createAggregator(
				final IContextAware ctxAware,
				final String purchaseQtySource,
				final PurchaseTypeEnum purchaseType)
		{
			capturedPurchaseType = purchaseType;
			capturedSpyAggregator = new SpyAggregator(ctxAware, purchaseQtySource, purchaseType, skippedLinesMessageToReturn);
			return capturedSpyAggregator;
		}

		@Override
		protected CreatePOFromSOsAggregationKeyBuilder createKeyBuilder(
				final IContextAware ctxAware,
				final boolean isVendorInOrderLinesRequired)
		{
			capturedIsVendorInOrderLinesRequired = isVendorInOrderLinesRequired;
			return super.createKeyBuilder(ctxAware, isVendorInOrderLinesRequired);
		}
	}

	/**
	 * Subclass of {@link CreatePOFromSOsAggregator} that overrides the non-final
	 * {@code getSkippedLinesMessage()} to inject a test-controlled value and count invocations.
	 *
	 * <p>Note: {@code closeAllGroups()} is {@code final} on {@link de.metas.util.collections.MapReduceAggregator}
	 * and cannot be overridden here. Instead, call-counts on {@code getSkippedLinesMessage()} serve as the
	 * indirect proxy for "closeAllGroups was called".</p>
	 */
	static class SpyAggregator extends CreatePOFromSOsAggregator
	{
		int getSkippedLinesMessageCallCount = 0;
		private final Optional<String> overrideMessage;

		SpyAggregator(
				final IContextAware ctxAware,
				final String purchaseQtySource,
				final PurchaseTypeEnum purchaseType,
				final Optional<String> overrideMessage)
		{
			super(ctxAware, purchaseQtySource, purchaseType);
			this.overrideMessage = overrideMessage;
		}

		@Override
		public Optional<String> getSkippedLinesMessage()
		{
			getSkippedLinesMessageCallCount++;
			return overrideMessage;
		}
	}
}
