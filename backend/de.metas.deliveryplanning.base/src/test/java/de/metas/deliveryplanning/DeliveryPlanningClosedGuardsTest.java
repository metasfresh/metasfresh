/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * "Closed means finished, nothing touches it": {@code Close} / {@code ReOpen} throw the AD_Message their
 * precondition rejects with, naming the offending planning, instead of silently doing nothing - and
 * {@code Cancel} refuses a closed row per-row rather than aborting the whole selection.
 */
class DeliveryPlanningClosedGuardsTest
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());
	}

	// ------------------------------------------------------------------ helpers

	private I_M_Delivery_Planning deliveryPlanning(final boolean closed)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setIsClosed(closed);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** A planning that {@code Cancel} can see: it needs a {@code ReleaseNo}, same as a real allocated planning has. */
	private I_M_Delivery_Planning deliveryPlanningWithReleaseNo(final boolean closed, final String releaseNo)
	{
		final I_M_Delivery_Planning record = deliveryPlanning(closed);
		record.setReleaseNo(releaseNo);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private static DeliveryPlanningId idOf(final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	private IQueryFilter<I_M_Delivery_Planning> selectionOf(final I_M_Delivery_Planning... records)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(
						I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID,
						Arrays.stream(records).map(DeliveryPlanningClosedGuardsTest::idOf).collect(ImmutableList.toImmutableList()));
	}

	@Nested
	@DisplayName("close")
	class Close
	{
		@Test
		@DisplayName("an open planning is closed")
		void openPlanningIsClosed()
		{
			final I_M_Delivery_Planning open = deliveryPlanning(false);

			deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(open));

			final I_M_Delivery_Planning reloaded = reload(open);
			assertThat(reloaded.isClosed()).isTrue();
			assertThat(reloaded.isProcessed()).isTrue();
		}

		@Test
		@DisplayName("an already-closed planning errors instead of doing nothing, naming the planning")
		void alreadyClosedPlanningErrors()
		{
			final I_M_Delivery_Planning alreadyClosed = deliveryPlanning(true);

			assertThatThrownBy(() -> deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(alreadyClosed)))
					.isInstanceOf(AdempiereException.class)
					// the backstop raises the same AD_Message the precondition rejects with, with the offending
					// planning as its parameter - never a developer token carrying the record's toString()
					.hasMessageContaining(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message())
					.hasMessageContaining(String.valueOf(idOf(alreadyClosed).getRepoId()));
		}

		@Test
		@DisplayName("a mixed selection is refused wholesale - nothing is closed")
		void mixedSelectionIsAllOrNothing()
		{
			final I_M_Delivery_Planning open = deliveryPlanning(false);
			final I_M_Delivery_Planning alreadyClosed = deliveryPlanning(true);

			assertThatThrownBy(() -> deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(open, alreadyClosed)))
					.isInstanceOf(AdempiereException.class);

			assertThat(reload(open).isClosed()).as("the open one must not have been closed either").isFalse();
		}
	}

	@Nested
	@DisplayName("reopen")
	class ReOpen
	{
		@Test
		@DisplayName("a closed planning is reopened")
		void closedPlanningIsReopened()
		{
			final I_M_Delivery_Planning closed = deliveryPlanning(true);

			deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(closed));

			final I_M_Delivery_Planning reloaded = reload(closed);
			assertThat(reloaded.isClosed()).isFalse();
			assertThat(reloaded.isProcessed()).isFalse();
		}

		@Test
		@DisplayName("an open planning errors instead of doing nothing, naming the planning")
		void openPlanningErrors()
		{
			final I_M_Delivery_Planning open = deliveryPlanning(false);

			assertThatThrownBy(() -> deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(open)))
					.isInstanceOf(AdempiereException.class)
					// the backstop raises the same AD_Message the precondition rejects with, with the offending
					// planning as its parameter - never a developer token carrying the record's toString()
					.hasMessageContaining(DeliveryPlanningService.MSG_M_Delivery_Planning_Open.toAD_Message())
					.hasMessageContaining(String.valueOf(idOf(open).getRepoId()));
		}

		@Test
		@DisplayName("a mixed selection is refused wholesale - nothing is reopened")
		void mixedSelectionIsAllOrNothing()
		{
			final I_M_Delivery_Planning open = deliveryPlanning(false);
			final I_M_Delivery_Planning alreadyClosed = deliveryPlanning(true);

			assertThatThrownBy(() -> deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(open, alreadyClosed)))
					.isInstanceOf(AdempiereException.class);

			assertThat(reload(alreadyClosed).isClosed()).as("the closed one must not have been reopened either").isTrue();
		}
	}

	@Nested
	@DisplayName("cancel")
	class Cancel
	{
		@Test
		@DisplayName("a mixed selection processes the open one and reports the closed one, rather than aborting")
		void mixedSelectionProcessesOpenAndReportsClosed()
		{
			final I_M_Delivery_Planning open = deliveryPlanningWithReleaseNo(false, "REL-OPEN");
			final I_M_Delivery_Planning closed = deliveryPlanningWithReleaseNo(true, "REL-CLOSED");

			final DeliveryPlanningCancelResult result = deliveryPlanningService.cancelDelivery(selectionOf(open, closed));

			assertThat(result.getCancelledIds()).containsExactly(idOf(open));
			assertThat(result.getSkippedClosedIds()).containsExactly(idOf(closed));

			final I_M_Delivery_Planning reloadedOpen = reload(open);
			assertThat(reloadedOpen.isClosed()).as("the open one is now closed - cancelling closes it").isTrue();
			assertThat(reloadedOpen.getOrderStatus()).isEqualTo(X_M_Delivery_Planning.ORDERSTATUS_Canceled);
			assertThat(reloadedOpen.getPlannedLoadedQuantity()).isEqualByComparingTo(BigDecimal.ZERO);

			final I_M_Delivery_Planning reloadedClosed = reload(closed);
			assertThat(reloadedClosed.getOrderStatus())
					.as("the closed one was skipped, not cancelled")
					.isNotEqualTo(X_M_Delivery_Planning.ORDERSTATUS_Canceled);
			assertThat(reloadedClosed.getReleaseNo()).as("left exactly as it was").isEqualTo("REL-CLOSED");
		}

		@Test
		@DisplayName("a selection of only closed plannings cancels none and reports all of them")
		void allClosedSelectionCancelsNone()
		{
			final I_M_Delivery_Planning closedOne = deliveryPlanningWithReleaseNo(true, "REL-1");
			final I_M_Delivery_Planning closedTwo = deliveryPlanningWithReleaseNo(true, "REL-2");

			final DeliveryPlanningCancelResult result = deliveryPlanningService.cancelDelivery(selectionOf(closedOne, closedTwo));

			assertThat(result.getCancelledIds()).isEmpty();
			assertThat(result.getSkippedClosedIds()).containsExactlyInAnyOrder(idOf(closedOne), idOf(closedTwo));
		}
	}
}
