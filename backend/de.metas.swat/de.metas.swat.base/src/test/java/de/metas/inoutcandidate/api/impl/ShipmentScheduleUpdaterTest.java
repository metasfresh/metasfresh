package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryGroupCandidateGroupId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidResult;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.process.PInstanceId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
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

public class ShipmentScheduleUpdaterTest
{
	private static final ShipperId SHIPPER_ID = ShipperId.ofRepoId(20);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(35);

	private ShipmentScheduleUpdater shipmentScheduleUpdater;
	private IShipmentSchedulePA shipmentSchedulePA;
	private IShipmentScheduleInvalidateRepository invalidSchedulesRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// mocked so the limitReached-derivation tests below can control the "does more backlog remain"
		// signal independently of how many schedules retrieveInvalid actually returns (see CLAUDE.md-level
		// note in the tests: the tag unit is a whole PRODUCT, so updatedCount can exceed maxToProcess --
		// limitReached must NOT be derived from updatedCount).
		shipmentSchedulePA = mock(IShipmentSchedulePA.class);
		Services.registerService(IShipmentSchedulePA.class, shipmentSchedulePA);

		invalidSchedulesRepo = mock(IShipmentScheduleInvalidateRepository.class);
		Services.registerService(IShipmentScheduleInvalidateRepository.class, invalidSchedulesRepo);

		this.shipmentScheduleUpdater = ShipmentScheduleUpdater.newInstanceForUnitTesting();
	}

	private ShipmentScheduleUpdateInvalidRequest.ShipmentScheduleUpdateInvalidRequestBuilder requestBuilder(final QueryLimit maxToProcess)
	{
		return ShipmentScheduleUpdateInvalidRequest.builder()
				.ctx(Env.getCtx())
				.selectionId(PInstanceId.ofRepoId(123))
				.createMissingShipmentSchedules(false)
				.maxToProcess(maxToProcess);
	}

	/**
	 * Proves the CORRECT {@code limitReached} signal: it must be derived from whether MORE untagged
	 * recompute markers remain after the pass -- NOT from {@code updatedCount >= maxToProcess}. The tagging
	 * unit is a whole PRODUCT (see {@code ShipmentScheduleInvalidateRepositoryTest}), so a bounded pass can
	 * retrieve fewer, exactly as many, or MORE schedules than {@code maxToProcess}; only the "backlog still
	 * pending" signal is reliable. Here {@code retrieveInvalid} returns an EMPTY list (updatedCount=0, well
	 * under maxToProcess=3) yet the repository reports more untagged markers exist -- a naive
	 * updatedCount-based check would wrongly report false; the correct signal reports true.
	 */
	@Test
	public void updateShipmentSchedules_limited_backlogRemains_limitReachedTrue()
	{
		when(shipmentSchedulePA.retrieveInvalid(any(), any())).thenReturn(ImmutableList.of());
		when(invalidSchedulesRepo.existsUntaggedRecomputeMarkers()).thenReturn(true);

		final ShipmentScheduleUpdateInvalidResult result = shipmentScheduleUpdater.updateShipmentSchedules(
				requestBuilder(QueryLimit.ofInt(3)).build());

		assertThat(result.getUpdatedCount()).isEqualTo(0);
		assertThat(result.isLimitReached())
				.as("more untagged markers remain after this pass -> a follow-up run is needed")
				.isTrue();
	}

	/**
	 * Mirror of the above: when the repository reports NO untagged markers remain (backlog drained), the
	 * result must report limitReached=false so the re-enqueue chain terminates -- regardless of updatedCount.
	 */
	@Test
	public void updateShipmentSchedules_limited_backlogDrained_limitReachedFalse()
	{
		when(shipmentSchedulePA.retrieveInvalid(any(), any())).thenReturn(ImmutableList.of());
		when(invalidSchedulesRepo.existsUntaggedRecomputeMarkers()).thenReturn(false);

		final ShipmentScheduleUpdateInvalidResult result = shipmentScheduleUpdater.updateShipmentSchedules(
				requestBuilder(QueryLimit.ofInt(3)).build());

		assertThat(result.isLimitReached())
				.as("no untagged markers remain -> the backlog is drained, no follow-up needed")
				.isFalse();
	}

	/**
	 * NO_LIMIT (the manual {@code M_ShipmentSchedule_Update} process path) must NEVER report limitReached=true,
	 * even if the "more untagged markers remain" signal is (incorrectly, e.g. due to a race with a concurrent
	 * invalidation) true -- this hardcodes the manual-path-stays-single-shot invariant instead of relying
	 * on the repository signal alone.
	 */
	@Test
	public void updateShipmentSchedules_noLimit_neverReportsLimitReached_regardlessOfBacklogSignal()
	{
		when(shipmentSchedulePA.retrieveInvalid(any(), any())).thenReturn(ImmutableList.of());
		when(invalidSchedulesRepo.existsUntaggedRecomputeMarkers()).thenReturn(true);

		final ShipmentScheduleUpdateInvalidResult result = shipmentScheduleUpdater.updateShipmentSchedules(
				requestBuilder(QueryLimit.NO_LIMIT).build());

		assertThat(result.isLimitReached())
				.as("NO_LIMIT must never trigger a follow-up run")
				.isFalse();
	}

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void updateSchedules_emptyList()
	{
		final List<OlAndSched> olAndScheds = new ArrayList<>();

		shipmentScheduleUpdater.updateSchedules(Env.getCtx(), olAndScheds);
	}

	@Test
	public void createGroup()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setBPartnerAddress_Override("bPartnerAddress");
		sched.setM_Warehouse_Override_ID(WAREHOUSE_ID.getRepoId());
		// save(sched); // not needed

		final TableRecordReference orderRef = TableRecordReference.of(I_C_Order.Table_Name, 10);
		final ShipmentScheduleReferencedLine scheduleSourceDoc = ShipmentScheduleReferencedLine.builder()
				.recordRef(orderRef)
				.shipperId(ShipperId.optionalOfRepoId(SHIPPER_ID.getRepoId()))
				.warehouseId(WarehouseId.ofRepoId(30)) // different from the sched's effective WH
				.documentLineDescriptor(OrderLineDescriptor.builder().build()) // documentLineDescriptor is not relevant for this test
				.build();

		// invoke method under test
		final DeliveryGroupCandidate result = shipmentScheduleUpdater.createGroup(scheduleSourceDoc, sched);

		assertThat(result.getGroupId()).isEqualTo(DeliveryGroupCandidateGroupId.of(orderRef));
		assertThat(result.getShipperId().get()).isEqualTo(SHIPPER_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getBPartnerAddress()).isEqualTo("bPartnerAddress");
	}

	@Test
	public void updateProcessedFlag()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setQtyReserved(BigDecimal.TEN);

		shipmentScheduleUpdater.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isFalse();

		sched.setIsClosed(true);
		shipmentScheduleUpdater.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isTrue();
	}
}
