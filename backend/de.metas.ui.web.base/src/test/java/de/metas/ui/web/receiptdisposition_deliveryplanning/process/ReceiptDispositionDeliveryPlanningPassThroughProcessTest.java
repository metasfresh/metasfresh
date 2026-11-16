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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

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
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;
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
 * What the five PASS-THROUGH actions must do with the row they were given: act on THAT row's receipt schedule.
 * <p>
 * On this window a process' record resolves as {@code RV_ReceiptDisposition_DeliveryPlanning}, not as
 * {@code M_ReceiptSchedule}, so an adapter that resolved its record any other way (through
 * {@code getRecord_ID()}, the row id, the planning) would compile, would look right, and would act on the WRONG
 * record. Each test pins the receipt schedule that actually reaches {@link ReceiptScheduleActions}.
 */
class ReceiptDispositionDeliveryPlanningPassThroughProcessTest
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

		// The process' own actions field resolves ReceiptScheduleActions from the spring context when the process
		// is constructed. Registering the very mock every test below asserts on keeps the real service - and the
		// DocumentCollection bean it is injected with - out of this test entirely.
		SpringContextHolder.registerJUnitBean(ReceiptScheduleActions.class, actions);

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
	 * The row ID follows {@code RV_ReceiptDisposition_DeliveryPlanning}'s own synthetic key and is therefore
	 * deliberately NOT the receipt schedule id (the unplanned branch is keyed {@code 1000000000 +
	 * M_ReceiptSchedule_ID}) - which is what makes these tests able to catch an adapter that resolved its record
	 * from the ROW, i.e. what the platform's default record resolution does.
	 */
	private static IViewRow row(final int receiptScheduleRepoId, @Nullable final Integer deliveryPlanningRepoId)
	{
		final int syntheticRowId = deliveryPlanningRepoId != null
				? deliveryPlanningRepoId
				: 1000000000 + receiptScheduleRepoId;

		return ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(syntheticRowId))
				.putFieldValue(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleRepoId)
				.putFieldValue(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningRepoId)
				.build();
	}

	/**
	 * Puts the given rows in front of the process exactly as the platform does - a view holding them and a
	 * selection naming them. Anything less lets a test pass while the adapter reads its record from elsewhere.
	 */
	private static <T extends ReceiptDispositionDeliveryPlanningPassThroughProcess> T withSelection(
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_AttachPhoto process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_AttachPhoto(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_AttachPhoto process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_AttachPhoto(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_RunMaterialReceiptJasper process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_RunMaterialReceiptJasper(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsToVendor process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsToVendor(), row(RECEIPT_SCHEDULE_REPO_ID, DELIVERY_PLANNING_REPO_ID));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsFromCustomer process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsFromCustomer(), row(OTHER_RECEIPT_SCHEDULE_REPO_ID, null));
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
		final WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsToVendor process =
				withSelection(new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsToVendor());
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
