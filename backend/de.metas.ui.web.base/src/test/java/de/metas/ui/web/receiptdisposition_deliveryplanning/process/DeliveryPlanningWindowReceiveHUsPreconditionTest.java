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
import de.metas.deliveryplanning.DeliveryPlanning;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.organization.OrgId;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.TransportDirection;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleActions;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;
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
 * The two "receive HUs" actions asked for on the DELIVERY-PLANNING window (541632) rather than the
 * receipt-disposition one: there a selected row is an {@code M_Delivery_Planning}, and an OUTGOING planning has
 * no receipt schedule at all.
 * <p>
 * Asked through {@link ProcessPreconditionChecker} by classname because that is the only way production asks -
 * and because the checker turns any THROWN exception into
 * {@code ProcessPreconditionsResolution#rejectWithInternalReason}. So "the action is not offered" is equally true
 * when the guard refuses and when the row-to-ids extraction blows up on the missing {@code M_ReceiptSchedule_ID};
 * only the reject REASON tells the two apart, which is why every assertion below reads it.
 */
class DeliveryPlanningWindowReceiveHUsPreconditionTest
{
	/** The delivery-planning window ("Lieferplanung"), whose grid rows are {@code M_Delivery_Planning} records. */
	private static final WindowId WINDOW_ID = WindowId.of(541632);

	private static final DeliveryPlanningId PLANNING_ID = DeliveryPlanningId.ofRepoId(540030);

	private int receiptScheduleRepoId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, Mockito.mock(DeliveryPlanningService.class));
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, Mockito.mock(IViewsRepository.class));
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, Mockito.mock(ReceiptFromReceiptScheduleService.class));
		SpringContextHolder.registerJUnitBean(ReceiptScheduleActions.class, Mockito.mock(ReceiptScheduleActions.class));
		SpringContextHolder.registerJUnitBean(PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));

		Mockito.when(deliveryPlanningService().getReceiveRejectionReason(Mockito.any())).thenReturn(Optional.empty());

		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		InterfaceWrapperHelper.save(receiptSchedule);
		receiptScheduleRepoId = receiptSchedule.getM_ReceiptSchedule_ID();
	}

	private static DeliveryPlanningService deliveryPlanningService()
	{
		return SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	}

	/**
	 * ONE delivery-planning grid row selected, of the given direction. The row's {@code M_ReceiptSchedule_ID} is
	 * set exactly when that direction has one on {@code M_Delivery_Planning} - measured on
	 * deep_tundra_release: all 1919 Outgoing plannings have it NULL, all 2631 Incoming/Dropship ones have it set.
	 */
	private ViewAsPreconditionsContext singleRowSelected(@NonNull final TransportDirection transportDirection)
	{
		Mockito.when(deliveryPlanningService().getProcessedStatePlannings(Mockito.any()))
				.thenReturn(DeliveryPlanningList.of(DeliveryPlanning.builder()
						.id(PLANNING_ID)
						.orgId(OrgId.ANY)
						.transportDirection(transportDirection)
						.processed(false)
						.build()));

		final IViewRow row = ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(PLANNING_ID.getRepoId()))
				.putFieldValue(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, PLANNING_ID.getRepoId())
				.putFieldValue(I_M_Delivery_Planning.COLUMNNAME_M_ReceiptSchedule_ID,
						transportDirection.isIncomingOrDropship() ? receiptScheduleRepoId : null)
				.build();

		final ViewId viewId = ViewId.random(WINDOW_ID);
		final IView view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);
		Mockito.when(view.streamByIds(Mockito.any())).thenAnswer(invocation -> Stream.of(row));

		return ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(ViewRowIdsSelection.of(viewId, DocumentIdsSelection.of(ImmutableList.of(row.getId()))))
				.build();
	}

	private static ProcessPreconditionsResolution askThePlatform(
			@NonNull final Class<?> processClass,
			@NonNull final ViewAsPreconditionsContext context)
	{
		return ProcessPreconditionChecker.newInstance()
				.setProcess(processClass.getName())
				.setPreconditionsContext(context)
				.checkApplies();
	}

	@ParameterizedTest
	@ValueSource(classes = {
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingDefaults.class,
			WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingConfig.class,
	})
	@DisplayName("an OUTGOING delivery planning does not offer the action, and says why - the receipt-schedule read is never reached")
	void outgoingPlanningIsRefusedForItsDirection(final Class<?> processClass)
	{
		final ProcessPreconditionsResolution resolution =
				askThePlatform(processClass, singleRowSelected(TransportDirection.Outgoing));

		assertThat(resolution.isRejected()).isTrue();
		assertThat(resolution.getRejectReason().translate("en_US"))
				.as("the guard's own refusal, NOT ProcessPreconditionChecker catching the M_ReceiptSchedule_ID "
						+ "assumption failure of extractReceiptScheduleAndPlanningId")
				.contains("outgoing")
				.doesNotContain("M_ReceiptSchedule_ID");
	}

	@Test
	@DisplayName("an INCOMING delivery planning DOES offer \"HUs annehmen\" - the guard discriminates instead of refusing every row")
	void incomingPlanningIsAccepted()
	{
		final ProcessPreconditionsResolution resolution = askThePlatform(
				WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingConfig.class,
				singleRowSelected(TransportDirection.Incoming));

		assertThat(resolution.isAccepted())
				.as("rejected with: %s", resolution)
				.isTrue();
	}

	@Test
	@DisplayName("a DROPSHIP delivery planning offers it too - its goods are received, they just never enter our warehouse")
	void dropshipPlanningIsAccepted()
	{
		final ProcessPreconditionsResolution resolution = askThePlatform(
				WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingConfig.class,
				singleRowSelected(TransportDirection.Dropship));

		assertThat(resolution.isAccepted())
				.as("rejected with: %s", resolution)
				.isTrue();
	}

	/**
	 * "HUs annehmen Voreinst." is refused on this bare fixture, but for its OWN reason - a packing default that
	 * does not resolve - which is what keeps {@link #outgoingPlanningIsRefusedForItsDirection}'s reason assertion
	 * meaningful rather than accidentally true for every row.
	 */
	@Test
	@DisplayName("the one-click default's refusal on an incoming row names the packing default, not the direction")
	void incomingPlanningIsNotRefusedForItsDirection()
	{
		final ProcessPreconditionsResolution resolution = askThePlatform(
				WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingDefaults.class,
				singleRowSelected(TransportDirection.Incoming));

		assertThat(resolution.isRejected()).isTrue();
		assertThat(resolution.getRejectReason().translate("en_US")).doesNotContain("outgoing");
	}
}
