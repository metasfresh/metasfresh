package org.eevolution.api.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCloseResult;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class PPOrderBLTest
{
	private PPOrderBL ppOrderBL;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		ppOrderBL = (PPOrderBL)Services.get(IPPOrderBL.class);
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	@Builder(builderMethodName = "manufacturingOrder", builderClassName = "ManufacturingOrderBuilder")
	private I_PP_Order createManufacturingOrder(
			@Nullable final APIExportStatus exportStatus)
	{
		final I_PP_Order order = newInstance(I_PP_Order.class);
		if (exportStatus != null)
		{
			order.setExportStatus(exportStatus.getCode());
		}

		saveRecord(order);

		return order;
	}

	@Nested
	public class updateCanBeExportedAfter
	{
		private ZonedDateTime now;

		@BeforeEach
		public void beforeEach()
		{
			now = ZonedDateTime.now();
			SystemTime.setFixedTimeSource(now);
		}

		private void setCanBeExportedAfterSeconds(int seconds)
		{
			sysConfigBL.setValue(
					PPOrderBL.SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
					seconds,
					ClientId.SYSTEM,
					OrgId.ANY);
		}

		@Test
		public void exportStatusPending_canBeExportedAfter_0seconds()
		{
			final I_PP_Order order = manufacturingOrder().exportStatus(APIExportStatus.Pending).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(TimeUtil.asTimestamp(now));
		}

		@Test
		public void exportStatusPending_canBeExportedAfter_15seconds()
		{
			setCanBeExportedAfterSeconds(15);
			final I_PP_Order order = manufacturingOrder().exportStatus(APIExportStatus.Pending).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(TimeUtil.asTimestamp(now.plusSeconds(15)));
		}

		@ParameterizedTest
		@EnumSource(mode = EnumSource.Mode.EXCLUDE, names = "Pending")
		public void exportStatus_NotPending(final APIExportStatus exportStatus)
		{
			setCanBeExportedAfterSeconds(15);
			final I_PP_Order order = manufacturingOrder().exportStatus(exportStatus).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(Env.MAX_DATE);
		}

	}

	/**
	 * Closing a selection of manufacturing orders closes each order in its own transaction and keeps going when one of
	 * them fails. The all-orders-close path is covered end-to-end by the cucumber scenario that runs the
	 * {@code PP_Order_CloseSelection} process; the failure split is only reachable here, because the per-order outcome
	 * is carried by the returned {@link PPOrderCloseResult} and never surfaces as document state.
	 *
	 * <p>The in-memory framework commits every save at once, so what these tests pin about a failed order is that the
	 * loop writes nothing for it - rolling back a half-applied close is the transaction manager's job and out of reach
	 * from here.
	 */
	@Nested
	public class closeOrdersInSelection
	{
		private static final String CLOSE_FAILURE_MESSAGE = "Cannot issue less than 5 PCE";

		/** The close outcome to play back per order, keyed by DocumentNo. */
		private final Map<String, Consumer<I_PP_Order>> closeOutcomeByDocumentNo = new HashMap<>();

		private PPOrderBL ppOrderBLUnderTest;

		@BeforeEach
		public void beforeEach()
		{
			final IDocumentBL documentBL = Mockito.mock(IDocumentBL.class);

			// PPOrderBL calls the two-argument processEx, i.e. expectedDocStatus=null: the document engine is told to
			// close and is never asked to confirm that it did. Stubbing that exact overload is what makes a close which
			// quietly does nothing reproducible here.
			Mockito.doAnswer(invocation -> {
				final I_PP_Order ppOrder = invocation.getArgument(0);
				closeOutcomeByDocumentNo.get(ppOrder.getDocumentNo()).accept(ppOrder);
				return null;
			}).when(documentBL).processEx(ArgumentMatchers.any(), ArgumentMatchers.eq(X_PP_Order.DOCACTION_Close));

			Services.registerService(IDocumentBL.class, documentBL);

			// constructed after the service is registered, so that it picks up the mock
			ppOrderBLUnderTest = new PPOrderBL();
		}

		@Test
		public void oneOrderFails_theOtherOneIsStillClosed()
		{
			final I_PP_Order closeable = completedOrder("MO-1");
			final I_PP_Order notCloseable = completedOrder("MO-2");
			final I_PP_Order outsideTheSelection = completedOrder("MO-3");
			closeOutcomeByDocumentNo.put("MO-1", this::closes);
			closeOutcomeByDocumentNo.put("MO-2", this::failsToClose);
			closeOutcomeByDocumentNo.put("MO-3", this::closes);

			final Map<String, Object> notCloseableBefore = persistedValuesOf(notCloseable);

			final PPOrderCloseResult result = ppOrderBLUnderTest.closeOrdersInSelection(selectionOf(closeable, notCloseable));

			assertThat(persistedDocStatusOf(closeable)).isEqualTo(DocStatus.Closed);
			assertThat(persistedDocStatusOf(notCloseable)).isEqualTo(DocStatus.Completed);
			assertThat(persistedValuesOf(notCloseable)).as("failed order is left exactly as it was").isEqualTo(notCloseableBefore);
			assertThat(persistedDocStatusOf(outsideTheSelection)).as("orders outside the selection are not touched").isEqualTo(DocStatus.Completed);

			assertThat(result.getCountClosed()).isEqualTo(1);
			assertThat(result.getCountFailed()).isEqualTo(1);
			assertThat(result.getFirstFailureMessage()).startsWith("MO-2: ").contains(CLOSE_FAILURE_MESSAGE);
		}

		@Test
		public void closeThatSilentlyDidNotTakeEffect_countsAsAFailure()
		{
			final I_PP_Order closeable = completedOrder("MO-1");
			final I_PP_Order silentlyNotClosed = completedOrder("MO-2");
			closeOutcomeByDocumentNo.put("MO-1", this::closes);
			closeOutcomeByDocumentNo.put("MO-2", this::doesNothing);

			final PPOrderCloseResult result = ppOrderBLUnderTest.closeOrdersInSelection(selectionOf(closeable, silentlyNotClosed));

			assertThat(persistedDocStatusOf(closeable)).isEqualTo(DocStatus.Closed);
			assertThat(persistedDocStatusOf(silentlyNotClosed)).isEqualTo(DocStatus.Completed);

			assertThat(result.getCountClosed()).isEqualTo(1);
			assertThat(result.getCountFailed()).isEqualTo(1);
			assertThat(result.getFirstFailureMessage()).startsWith("MO-2: ");
		}

		@Test
		public void firstFailureMessage_namesTheFailingOrderWithTheLowestId()
		{
			final I_PP_Order firstFailure = completedOrder("MO-1");
			final I_PP_Order secondFailure = completedOrder("MO-2");
			closeOutcomeByDocumentNo.put("MO-1", this::failsToClose);
			closeOutcomeByDocumentNo.put("MO-2", this::failsToClose);
			assertThat(firstFailure.getPP_Order_ID()).isLessThan(secondFailure.getPP_Order_ID());

			// selection listed the other way round, so only the query's ordering can decide which failure is reported
			final PPOrderCloseResult result = ppOrderBLUnderTest.closeOrdersInSelection(selectionOf(secondFailure, firstFailure));

			assertThat(result.getCountClosed()).isZero();
			assertThat(result.getCountFailed()).isEqualTo(2);
			assertThat(result.getFirstFailureMessage()).startsWith("MO-1: ");
		}

		private I_PP_Order completedOrder(final String documentNo)
		{
			final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
			ppOrder.setDocumentNo(documentNo);
			ppOrder.setDocStatus(DocStatus.Completed.getCode());
			ppOrder.setDocAction(IDocument.ACTION_Close);
			ppOrder.setProcessed(true);
			saveRecord(ppOrder);

			return ppOrder;
		}

		private PInstanceId selectionOf(final I_PP_Order... ppOrders)
		{
			return POJOLookupMap.get().createSelectionFromModels(ppOrders);
		}

		/** What the document engine does to an order it closes. */
		private void closes(final I_PP_Order ppOrder)
		{
			ppOrder.setDocStatus(DocStatus.Closed.getCode());
			ppOrder.setDocAction(IDocument.ACTION_None);
			saveRecord(ppOrder);
		}

		/**
		 * A close that fails the way a real one does - an order whose components were issued short of what the BOM
		 * line's issuing tolerance allows cannot be closed.
		 */
		private void failsToClose(final I_PP_Order ppOrder)
		{
			throw new AdempiereException(CLOSE_FAILURE_MESSAGE);
		}

		/** A close that comes back without an error and without having closed anything. */
		private void doesNothing(final I_PP_Order ppOrder)
		{
		}

		private DocStatus persistedDocStatusOf(final I_PP_Order ppOrder)
		{
			return DocStatus.ofNullableCodeOrUnknown(reload(ppOrder).getDocStatus());
		}

		private Map<String, Object> persistedValuesOf(final I_PP_Order ppOrder)
		{
			return new HashMap<>(POJOWrapper.getWrapper(reload(ppOrder)).getValuesMap());
		}

		private I_PP_Order reload(final I_PP_Order ppOrder)
		{
			return InterfaceWrapperHelper.load(ppOrder.getPP_Order_ID(), I_PP_Order.class);
		}
	}
}
