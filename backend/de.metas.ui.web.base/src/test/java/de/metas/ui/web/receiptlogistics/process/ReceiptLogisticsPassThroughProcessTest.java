/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptlogistics.process;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleActions;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.quickinput.inout.EmptiesQuickInputDescriptorFactory;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_RV_ReceiptLogistics;
import org.compiere.model.X_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What the five PASS-THROUGH actions must do with the row they were given: act on <b>that row's receipt
 * schedule</b>.
 * <p>
 * That is the whole of the adapters' own job, and it is the one thing that can silently go wrong. On this window
 * a process' record resolves as {@code RV_ReceiptLogistics}, not as {@code M_ReceiptSchedule} - which is why
 * these classes exist at all - so an adapter that resolved its record any other way (through
 * {@code getRecord_ID()}, through the row id, through the planning) would compile, would look right, and would
 * attach the photo to, print, reverse or return empties for the WRONG record. Each test below therefore pins the
 * receipt schedule that actually reaches the shared {@link ReceiptScheduleActions}, on both row shapes.
 * <p>
 * The action bodies themselves are NOT re-tested here: they are the receipt-schedule window's, unchanged and
 * shared - {@code ReceiptScheduleActions} is the single copy both windows call.
 */
class ReceiptLogisticsPassThroughProcessTest
{
	private static final WindowId WINDOW_ID = WindowId.of(542190);
	private static final int RECEIPT_SCHEDULE_REPO_ID = 540010;
	private static final int OTHER_RECEIPT_SCHEDULE_REPO_ID = 540011;
	private static final int DELIVERY_PLANNING_REPO_ID = 540020;

	private ReceiptScheduleActions actions;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		actions = Mockito.mock(ReceiptScheduleActions.class);

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, Mockito.mock(DeliveryPlanningService.class));
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, Mockito.mock(IViewsRepository.class));

		// Pulled in when the process is constructed, by the shared base that turns the row's receipt schedule id
		// back into a record; its production implementation drags in a slice of the shipping graph.
		SpringContextHolder.registerJUnitBean(PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));

		createReceiptScheduleRecord(RECEIPT_SCHEDULE_REPO_ID);
		createReceiptScheduleRecord(OTHER_RECEIPT_SCHEDULE_REPO_ID);
	}

	private static void createReceiptScheduleRecord(final int repoId)
	{
		final I_M_ReceiptSchedule record = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		record.setM_ReceiptSchedule_ID(repoId);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * One grid row as the WebUI hands it to a process - the planned branch when a planning id is given.
	 * <p>
	 * The row ID follows {@code RV_ReceiptLogistics}'s own synthetic key and is therefore deliberately NOT the
	 * receipt schedule id: the planned branch is keyed by the planning, the unplanned one by
	 * {@code 1000000000 + M_ReceiptSchedule_ID} so the two stay disjoint. That is what makes these tests able to
	 * catch an adapter that resolved its record from the ROW rather than from the row's
	 * {@code M_ReceiptSchedule_ID} column - which is exactly what the platform's default record resolution does,
	 * and the whole reason these adapter classes exist.
	 */
	private static IViewRow row(final int receiptScheduleRepoId, @Nullable final Integer deliveryPlanningRepoId)
	{
		final int syntheticRowId = deliveryPlanningRepoId != null
				? deliveryPlanningRepoId
				: 1000000000 + receiptScheduleRepoId;

		return ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(syntheticRowId))
				.putFieldValue(I_RV_ReceiptLogistics.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleRepoId)
				.putFieldValue(I_RV_ReceiptLogistics.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningRepoId)
				.build();
	}

	/**
	 * Puts the given rows in front of the process exactly as the platform does: a view holding them, and a
	 * selection naming them. Anything less would let a test pass while the adapter read its record from
	 * somewhere other than the selected row - the very defect these tests exist for.
	 */
	private static <T extends ReceiptLogisticsPassThroughProcess> T withSelection(
			final T process,
			final IViewRow... rows)
	{
		final ViewId viewId = ViewId.random(WINDOW_ID);
		final IView view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);
		Mockito.when(view.streamByIds(Mockito.any())).thenAnswer(invocation -> Stream.of(rows));

		final DocumentIdsSelection rowIds = DocumentIdsSelection.of(
				Stream.of(rows).map(IViewRow::getId).collect(com.google.common.collect.ImmutableList.toImmutableList()));

		process.initForTesting(ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(ViewRowIdsSelection.of(viewId, rowIds))
				.build());

		return process;
	}

	private I_M_ReceiptSchedule capturedSchedule(final ArgumentCaptor<I_M_ReceiptSchedule> captor)
	{
		return captor.getValue();
	}

	// -------------------------------------------------------------------------------------------------
	// "Foto"
	// -------------------------------------------------------------------------------------------------

	@Test
	@DisplayName("\"Foto\" attaches to the selected ROW's receipt schedule - planned row")
	void attachPhotoUsesTheRowsSchedule_plannedRow()
	{
		final WEBUI_RV_ReceiptLogistics_AttachPhoto process =
				withSelection(new WEBUI_RV_ReceiptLogistics_AttachPhoto(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
		process.actions = actions;
		process.p_AD_Image_ID = 4711;

		process.doIt();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).attachPhoto(Mockito.any(Properties.class), captor.capture(), Mockito.eq(4711));
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(RECEIPT_SCHEDULE_REPO_ID);
	}

	@Test
	@DisplayName("\"Foto\" attaches to the selected ROW's receipt schedule - unplanned row, and the OTHER schedule is not touched")
	void attachPhotoUsesTheRowsSchedule_unplannedRow()
	{
		final WEBUI_RV_ReceiptLogistics_AttachPhoto process =
				withSelection(new WEBUI_RV_ReceiptLogistics_AttachPhoto(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
		process.actions = actions;
		process.p_AD_Image_ID = 4711;

		process.doIt();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).attachPhoto(Mockito.any(Properties.class), captor.capture(), Mockito.eq(4711));
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(OTHER_RECEIPT_SCHEDULE_REPO_ID);
	}

	// -------------------------------------------------------------------------------------------------
	// "Drucken Produktanlieferung"
	// -------------------------------------------------------------------------------------------------

	@Test
	@DisplayName("\"Drucken Produktanlieferung\" prints for the selected ROW's receipt schedule")
	void jasperUsesTheRowsSchedule()
	{
		final WEBUI_RV_ReceiptLogistics_RunMaterialReceiptJasper process =
				withSelection(new WEBUI_RV_ReceiptLogistics_RunMaterialReceiptJasper(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
		process.actions = actions;

		process.doIt();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).runMaterialReceiptJasper(captor.capture());
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(OTHER_RECEIPT_SCHEDULE_REPO_ID);
	}

	// -------------------------------------------------------------------------------------------------
	// "Korrektur"
	// -------------------------------------------------------------------------------------------------

	@Test
	@DisplayName("\"Korrektur\" asks the shared eligibility rule about the selected ROW's receipt schedule")
	void reverseAsksAboutTheRowsSchedule()
	{
		final WEBUI_RV_ReceiptLogistics_SelectHUsToReverse process =
				withSelection(new WEBUI_RV_ReceiptLogistics_SelectHUsToReverse(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
		process.actions = actions;

		process.checkPreconditionsApplicable();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).checkHUsToReverseApplicable(captor.capture());
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(RECEIPT_SCHEDULE_REPO_ID);
	}

	@Test
	@DisplayName("\"Korrektur\" does NOT apply the receive actions' processed-planning guard - a received row is exactly what it is for")
	void reverseDoesNotAskTheProcessedGuard()
	{
		final WEBUI_RV_ReceiptLogistics_SelectHUsToReverse process =
				withSelection(new WEBUI_RV_ReceiptLogistics_SelectHUsToReverse(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
		process.actions = actions;

		process.checkPreconditionsApplicable();

		Mockito.verifyZeroInteractions(SpringContextHolder.instance.getBean(DeliveryPlanningService.class));
	}

	// -------------------------------------------------------------------------------------------------
	// "Leergut Ausgabe" / "Leergut Rücknahme"
	// -------------------------------------------------------------------------------------------------

	@Test
	@DisplayName("\"Leergut Ausgabe\" creates a VENDOR return from the selected ROW's receipt schedule")
	void emptiesToVendorUsesTheRowsSchedule()
	{
		final WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor process =
				withSelection(new WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
		process.actions = actions;

		process.doIt();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).createEmptiesReturns(
				Mockito.any(Properties.class),
				captor.capture(),
				Mockito.eq(X_M_InOut.MOVEMENTTYPE_VendorReturns),
				Mockito.eq(EmptiesQuickInputDescriptorFactory.VendorReturns_Window_ID));
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(RECEIPT_SCHEDULE_REPO_ID);
	}

	@Test
	@DisplayName("\"Leergut Rücknahme\" creates a CUSTOMER return from the selected ROW's receipt schedule")
	void emptiesFromCustomerUsesTheRowsSchedule()
	{
		final WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsFromCustomer process =
				withSelection(new WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsFromCustomer(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
		process.actions = actions;

		process.doIt();

		final ArgumentCaptor<I_M_ReceiptSchedule> captor = ArgumentCaptor.forClass(I_M_ReceiptSchedule.class);
		Mockito.verify(actions).createEmptiesReturns(
				Mockito.any(Properties.class),
				captor.capture(),
				Mockito.eq(X_M_InOut.MOVEMENTTYPE_CustomerReturns),
				Mockito.eq(EmptiesQuickInputDescriptorFactory.CustomerReturns_Window_ID));
		assertThat(capturedSchedule(captor).getM_ReceiptSchedule_ID()).isEqualTo(OTHER_RECEIPT_SCHEDULE_REPO_ID);
	}

	@Test
	@DisplayName("\"Leergut Ausgabe\" with NOTHING selected creates the empty draft, not a schedule-derived one")
	void emptiesWithNoSelectionPassesNoSchedule()
	{
		final WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor process =
				withSelection(new WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor());
		process.actions = actions;

		assertThat(process.checkPreconditionsApplicable().isAccepted()).isTrue();

		process.doIt();

		Mockito.verify(actions).createEmptiesReturns(
				Mockito.any(Properties.class),
				Mockito.isNull(),
				Mockito.eq(X_M_InOut.MOVEMENTTYPE_VendorReturns),
				Mockito.eq(EmptiesQuickInputDescriptorFactory.VendorReturns_Window_ID));
	}
}
