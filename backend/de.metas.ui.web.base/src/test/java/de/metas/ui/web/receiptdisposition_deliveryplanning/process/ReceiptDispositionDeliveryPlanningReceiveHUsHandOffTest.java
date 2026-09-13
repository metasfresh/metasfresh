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
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.receiptSchedule.HUsToReceiveViewFactory;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What "HUs annehmen" on the receipt-disposition delivery-planning window does with the HUs it generated: it hands
 * them to the HU EDITOR, exactly as the receipt-schedule window's "HUs annehmen" does
 * ({@code ReceiptScheduleBasedProcess#openHUsToReceive}), instead of booking them straight away.
 * <p>
 * The gesture is deliberately TWO steps, and that is the point of this test: between generating the planning HUs
 * and confirming the receipt the operator repacks, weighs, splits and fills in attributes - the whole reason the
 * editor exists. An action that books immediately gives the operator a toast and no editor, and the quantity
 * booked can then never differ from the quantity generated.
 */
class ReceiptDispositionDeliveryPlanningReceiveHUsHandOffTest
{
	private static final ReceiptScheduleId RECEIPT_SCHEDULE_ID = ReceiptScheduleId.ofRepoId(540010);
	private static final DeliveryPlanningId DELIVERY_PLANNING_ID = DeliveryPlanningId.ofRepoId(540020);

	/** "Wareneingangsdisposition inkl. Lieferplanung" - the window the action is started on. */
	private static final ViewId LAUNCHING_VIEW_ID = ViewId.random(WindowId.of(542190));

	private ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService;
	private I_M_ReceiptSchedule receiptSchedule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// ProcessInfo.builder() reads the logged-in user, and there is none in a plain unit test.
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		InterfaceWrapperHelper.save(receiptSchedule);

		receiptFromReceiptScheduleService = Mockito.mock(ReceiptFromReceiptScheduleService.class);

		// The two-argument form is required for a Mockito mock: the single-argument one registers under
		// bean.getClass(), which for a mock is the generated subclass and never matches the lookup type.
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, receiptFromReceiptScheduleService);
		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, Mockito.mock(DeliveryPlanningService.class));
		final IView launchingView = Mockito.mock(IView.class);
		Mockito.doReturn(LAUNCHING_VIEW_ID).when(launchingView).getViewId();
		final IViewsRepository viewsRepository = Mockito.mock(IViewsRepository.class);
		Mockito.doReturn(launchingView).when(viewsRepository).getView(LAUNCHING_VIEW_ID.getViewId());
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, viewsRepository);

		final IHUReceiptScheduleBL huReceiptScheduleBL = Mockito.mock(IHUReceiptScheduleBL.class);
		Mockito.doReturn(receiptSchedule).when(huReceiptScheduleBL).getById(RECEIPT_SCHEDULE_ID);
		Services.registerService(IHUReceiptScheduleBL.class, huReceiptScheduleBL);
	}

	private I_M_HU createPlanningHU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	/**
	 * The action with its HU generation stubbed out - the generator itself needs an HU context and a saved LU/TU
	 * configuration, and is not what this test is about.
	 */
	private static class GeneratingTestProcess extends ReceiptDispositionDeliveryPlanningReceiveHUsProcess
	{
		private final List<I_M_HU> generatedHUs;

		private GeneratingTestProcess(final List<I_M_HU> generatedHUs)
		{
			this.generatedHUs = generatedHUs;
		}

		@Override
		protected List<I_M_HU> generatePlanningHUs(
				@NonNull final I_M_ReceiptSchedule receiptSchedule,
				@Nullable final DeliveryPlanningId deliveryPlanningId)
		{
			return generatedHUs;
		}

		@Override
		protected I_M_HU_LUTU_Configuration createLUTUConfiguration(
				@NonNull final I_M_HU_LUTU_Configuration template,
				@NonNull final I_M_ReceiptSchedule receiptSchedule)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean isUpdateReceiptScheduleDefaultConfiguration() {return false;}

		@Override
		protected boolean isQtyToReceiveOperatorStated() {return false;}
	}

	private GeneratingTestProcess processFor(final List<I_M_HU> generatedHUs)
	{
		final GeneratingTestProcess process = new GeneratingTestProcess(generatedHUs);
		// The two internal parameters every view-started process is handed - ViewBasedProcessTemplate insists on
		// them, and the literals are what the platform puts in ($WEBUI_ViewId is package-visible there).
		process.init(ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setRecord(I_M_ReceiptSchedule.Table_Name, receiptSchedule.getM_ReceiptSchedule_ID())
				// Without the classname ProcessClassInfo is NULL, no @Param field is populated at all, and the
				// two internal view parameters below arrive nowhere.
				.setClassname(GeneratingTestProcess.class.getName())
				.addParameter("$WEBUI_ViewId", LAUNCHING_VIEW_ID.toJson())
				.addParameter("$WEBUI_ViewSelectedIds", "540099")
				.build());
		return process;
	}

	@Test
	@DisplayName("the receive ends with the HU EDITOR open on exactly the generated HUs")
	void receiveOpensTheHUEditor()
	{
		final ImmutableList<I_M_HU> generatedHUs = ImmutableList.of(createPlanningHU(), createPlanningHU());
		final GeneratingTestProcess process = processFor(generatedHUs);

		process.receive(ReceiptScheduleAndDeliveryPlanningId.of(RECEIPT_SCHEDULE_ID, DELIVERY_PLANNING_ID));

		final RecordsToOpen recordsToOpen = process.getResult().getRecordsToOpen();
		assertThat(recordsToOpen)
				.as("without this the operator gets a plain toast and no editor")
				.isNotNull();
		assertThat(recordsToOpen.getRecords())
				.containsExactlyElementsOf(TableRecordReference.ofCollection(generatedHUs));
		assertThat(recordsToOpen.getWindowIdString())
				.as("the HU editor's own WebUI window, the same one the receipt-schedule window opens")
				.isEqualTo(HUsToReceiveViewFactory.WINDOW_ID_STRING);
	}

	@Test
	@DisplayName("the receive books NOTHING yet - the confirm inside the editor does that")
	void receiveBooksNothing()
	{
		final GeneratingTestProcess process = processFor(ImmutableList.of(createPlanningHU()));

		process.receive(ReceiptScheduleAndDeliveryPlanningId.of(RECEIPT_SCHEDULE_ID, DELIVERY_PLANNING_ID));

		// Booking here would make the editor a read-only after-the-fact view of a receipt that already exists,
		// and every repack the operator then does would have no effect on what was booked.
		Mockito.verify(receiptFromReceiptScheduleService, Mockito.never()).createReceipt(Mockito.any());
	}

	@Test
	@DisplayName("the generated HUs still get their receipt-schedule attributes, as they do on the receipt-schedule window")
	void generatedHUsCarryTheirAttributes()
	{
		final ImmutableList<I_M_HU> generatedHUs = ImmutableList.of(createPlanningHU());
		final GeneratingTestProcess process = processFor(generatedHUs);

		process.receive(ReceiptScheduleAndDeliveryPlanningId.of(RECEIPT_SCHEDULE_ID, DELIVERY_PLANNING_ID));

		// Lot number, best-before date and vendor are set BEFORE the editor opens, because the editor's confirm
		// refuses a row whose mandatory attributes are empty.
		Mockito.verify(receiptFromReceiptScheduleService).updatePlanningHUAttributes(generatedHUs, receiptSchedule);
	}
}
