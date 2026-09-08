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

import com.google.common.collect.ImmutableList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.i18n.TranslatableStrings;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleActions;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * That the window's precondition guards are REACHED AT ALL - the platform seam every other test in this package
 * takes for granted.
 * <p>
 * <b>The defect this exists for.</b> {@link ProcessPreconditionChecker#setProcess(String)} evaluates a process'
 * preconditions only when its class {@code implements} {@link IProcessPrecondition}
 * ({@code ProcessPreconditionChecker:146}); with the interface absent, {@code resolution} stays {@code null} and
 * {@code checkApplies()} falls through to {@link ProcessPreconditionsResolution#accept()}. So an action can
 * override {@code checkPreconditionsApplicable()}, be byte-for-byte correct, be covered by unit tests that call
 * that override directly - and still be offered and accepted on every row, because nothing ever calls it. That
 * is what happened here: the shared base extended {@code ViewBasedProcessTemplate} without the interface, and
 * ALL ten actions' guards - not-single-selection, receipt-schedule eligibility, at-most-one-receipt-per-planning,
 * "no default LU/TU configuration", HUs-to-reverse - were dead on window 542190. Measured against a live stack:
 * with BOTH grid rows selected, {@code ReceiveHUs_UsingDefaults} and {@code SelectHUsToReverse} were served
 * ACCEPTED in 22-38 microseconds, i.e. without a single database read.
 * <p>
 * <b>Why this test is shaped the way it is.</b> It goes through {@link ProcessPreconditionChecker} <b>by
 * classname</b>, which is exactly and only what {@link ProcessDescriptor#checkPreconditionsApplicable} does in
 * production - it never touches the process objects itself. Constructing a process and calling its
 * {@code checkPreconditionsApplicable()} (or supplying a resolution to a
 * {@code WebuiRelatedProcessDescriptor}, as {@code ReceiptDispositionDeliveryPlanningQuickActionDefaultTest}
 * does) proves the guard's LOGIC while bypassing the very lookup that was broken; both stayed green through the
 * whole defect. The classname detour is the seam, so the test has to take it.
 * <p>
 * A two-row selection is the lever because it is the one condition every single-row action refuses without
 * reading anything - no receipt schedule, no planning, no HU. A guard that is never invoked cannot refuse it.
 */
class ReceiptDispositionDeliveryPlanningPreconditionSeamTest
{
	/** The receipt-disposition delivery-planning window ("Wareneingangsdisposition inkl. Lieferplanung"). */
	private static final WindowId WINDOW_ID = WindowId.of(542190);

	private static final int RECEIPT_SCHEDULE_REPO_ID = 540010;
	private static final int OTHER_RECEIPT_SCHEDULE_REPO_ID = 540011;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// The checker INSTANTIATES the process class it found (ProcessPreconditionChecker#createProcessPreconditions),
		// so every bean the constructor chain resolves has to be present - otherwise the checker's catch-all would
		// turn the missing bean into a rejection and this test would pass without the guard ever running.
		final DeliveryPlanningService deliveryPlanningService = Mockito.mock(DeliveryPlanningService.class);
		Mockito.when(deliveryPlanningService.getReceiveRejectionReason(Mockito.any())).thenReturn(Optional.empty());
		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, deliveryPlanningService);

		SpringContextHolder.registerJUnitBean(IViewsRepository.class, Mockito.mock(IViewsRepository.class));
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, Mockito.mock(ReceiptFromReceiptScheduleService.class));
		SpringContextHolder.registerJUnitBean(ReceiptScheduleActions.class, Mockito.mock(ReceiptScheduleActions.class));

		// Pulled in when any action on the shared base is constructed (the IHUReceiptScheduleBL field); its
		// production implementation drags in a slice of the shipping graph.
		SpringContextHolder.registerJUnitBean(PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	/**
	 * Asks the platform whether the action applies, the ONE way the WebUI asks it: by AD_Process classname,
	 * through {@link ProcessPreconditionChecker}. Mirrors {@link ProcessDescriptor#checkPreconditionsApplicable}
	 * line for line - nothing here reaches into the process object, because production cannot either.
	 */
	private static ProcessPreconditionsResolution askThePlatform(final Class<?> processClass, final ViewAsPreconditionsContext context)
	{
		return ProcessPreconditionChecker.newInstance()
				.setProcess(processClass.getName())
				.setPreconditionsContext(context)
				.checkApplies();
	}

	/** One grid row as the WebUI hands it to a process; {@code null} for the planning id is the view's UNPLANNED branch. */
	private static IViewRow row(final int receiptScheduleRepoId)
	{
		return ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(1000000000 + receiptScheduleRepoId))
				.putFieldValue(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleRepoId)
				.putFieldValue(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_Delivery_Planning_ID, null)
				.build();
	}

	/** The selection the live-stack measurement used: BOTH grid rows selected. */
	private static ViewAsPreconditionsContext bothRowsSelected()
	{
		final IViewRow first = row(RECEIPT_SCHEDULE_REPO_ID);
		final IViewRow second = row(OTHER_RECEIPT_SCHEDULE_REPO_ID);

		final ViewId viewId = ViewId.random(WINDOW_ID);
		final IView view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);
		Mockito.when(view.streamByIds(Mockito.any())).thenAnswer(invocation -> Stream.of(first, second));

		return ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(ViewRowIdsSelection.of(viewId,
						DocumentIdsSelection.of(ImmutableList.of(first.getId(), second.getId()))))
				.build();
	}

	@ParameterizedTest
	@ValueSource(classes = {
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_AttachPhoto.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsFromCustomer.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_CreateEmptiesReturnsToVendor.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs_WithParam.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingConfig.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingDefaults.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_RunMaterialReceiptJasper.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse.class,
	})
	@DisplayName("every single-row action is REFUSED on a two-row selection, asked the way the WebUI asks")
	void singleRowActionsAreRefusedOnATwoRowSelection(final Class<?> processClass)
	{
		final ProcessPreconditionsResolution resolution = askThePlatform(processClass, bothRowsSelected());

		assertThat(resolution.isRejected())
				.as("%s overrides checkPreconditionsApplicable() to refuse a multi-row selection; if the platform "
						+ "accepts it anyway, that override is never called - the class does not implement IProcessPrecondition",
						processClass.getSimpleName())
				.isTrue();
	}

	@Test
	@DisplayName("the multi-row receive is still ACCEPTED on a two-row selection - the guard is reached, not merely switched on")
	void theMultiRowReceiveIsStillAcceptedOnATwoRowSelection()
	{
		final ProcessPreconditionsResolution resolution =
				askThePlatform(WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts.class, bothRowsSelected());

		assertThat(resolution.isAccepted())
				.as("\"Wareneingangsdispo zu Wareneingang\" is the batch receive - a selection of several rows is "
						+ "precisely what it is for, so evaluating preconditions must not start refusing it")
				.isTrue();
	}

	@Test
	@DisplayName("with nothing selected, the single-row actions are refused too - the guard's other branch is reached as well")
	void nothingSelectedIsRefused()
	{
		final ViewId viewId = ViewId.random(WINDOW_ID);
		final IView view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);
		Mockito.when(view.streamByIds(Mockito.any())).thenAnswer(invocation -> Stream.empty());

		final ViewAsPreconditionsContext nothingSelected = ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(ViewRowIdsSelection.of(viewId, DocumentIdsSelection.EMPTY))
				.build();

		assertThat(askThePlatform(WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs.class, nothingSelected).isRejected())
				.as("rejectBecauseNoSelection() must reach the frontend")
				.isTrue();
	}

	@Test
	@DisplayName("the at-most-one-receipt-per-planning refusal reaches the frontend, not just the runtime backstop")
	void theProcessedPlanningRefusalReachesTheFrontend()
	{
		final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
		Mockito.when(deliveryPlanningService.getReceiveRejectionReason(Mockito.any()))
				.thenReturn(Optional.of(TranslatableStrings.parse("delivery planning 540020 is already processed")));

		final ProcessPreconditionsResolution resolution =
				askThePlatform(WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts.class, bothRowsSelected());

		assertThat(resolution.isRejected())
				.as("checkNoneProcessed's refusal is what disables the button; without it a planner only finds out "
						+ "from the exception assertNoneProcessed throws after pressing")
				.isTrue();
		assertThat(resolution.getRejectReason().translate("en_US")).contains("540020");
	}
}
