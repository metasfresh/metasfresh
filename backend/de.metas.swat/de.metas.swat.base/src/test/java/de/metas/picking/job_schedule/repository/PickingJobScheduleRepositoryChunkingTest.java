package de.metas.picking.job_schedule.repository;

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

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the {@code M_ShipmentSchedule_ID IN (...)} bind-parameter overflow fix: {@link PickingJobScheduleRepository}
 * must not fold an unbounded {@code onlyShipmentScheduleIds} set into a single {@code IN (?,?,...)}, which overflows
 * the PostgreSQL/JDBC 2-byte bind-parameter limit (max 32767) once the set exceeds that many entries. The chunking
 * overload {@code stream(query, maxIdsPerChunk)} partitions the set to stay under the cap; this test drives that
 * overload directly (with a tiny cap) rather than reproducing >32767 rows.
 */
class PickingJobScheduleRepositoryChunkingTest
{
	private static final BigDecimal QTY_TO_PICK = new BigDecimal("10");

	private UomId uomId;
	private int workplaceRepoId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = BusinessTestHelper.createUOM("Ea");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_C_Workplace workplace = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		workplace.setName("TestWorkplace");
		InterfaceWrapperHelper.saveRecord(workplace);
		workplaceRepoId = workplace.getC_Workplace_ID();
	}

	private I_M_Picking_Job_Schedule createJobScheduleRecord(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_Picking_Job_Schedule record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Schedule.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		record.setC_Workplace_ID(workplaceRepoId);
		record.setC_UOM_ID(uomId.getRepoId());
		record.setQtyToPick(QTY_TO_PICK);
		record.setProcessed(false);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	private static Map<ShipmentScheduleId, Set<PickingJobSchedule>> groupByShipmentScheduleId(@NonNull final List<PickingJobSchedule> schedules)
	{
		return schedules.stream()
				.collect(Collectors.groupingBy(PickingJobSchedule::getShipmentScheduleId, Collectors.toSet()));
	}

	/**
	 * ~5 shipment-schedule ids, one of them with 2 job-schedule rows (the rest with 1 each) — the
	 * chunked stream (chunk size 2, well below the 5-id/6-row total) must return exactly the same rows and the
	 * same per-shipment-schedule grouping as an oversized single-chunk call (10 000): no loss, no duplication.
	 */
	@Test
	void stream_chunked_isEquivalentToUnchunked()
	{
		final ImmutableList<ShipmentScheduleId> shipmentScheduleIds = ImmutableList.of(
				ShipmentScheduleId.ofRepoId(500001),
				ShipmentScheduleId.ofRepoId(500002),
				ShipmentScheduleId.ofRepoId(500003),
				ShipmentScheduleId.ofRepoId(500004),
				ShipmentScheduleId.ofRepoId(500005));

		// first shipment schedule gets two job-schedule rows, to also exercise multi-row grouping
		createJobScheduleRecord(shipmentScheduleIds.get(0));
		createJobScheduleRecord(shipmentScheduleIds.get(0));
		for (int i = 1; i < shipmentScheduleIds.size(); i++)
		{
			createJobScheduleRecord(shipmentScheduleIds.get(i));
		}

		final PickingJobScheduleQuery query = PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(shipmentScheduleIds)
				.build();

		final PickingJobScheduleRepository repo = PickingJobScheduleRepository.newInstanceForUnitTesting();

		final List<PickingJobSchedule> chunked = repo.stream(query, 2).collect(Collectors.toList());
		final List<PickingJobSchedule> unchunked = repo.stream(query, 10_000).collect(Collectors.toList());

		final Set<PickingJobScheduleId> chunkedIds = chunked.stream().map(PickingJobSchedule::getId).collect(Collectors.toSet());
		final Set<PickingJobScheduleId> unchunkedIds = unchunked.stream().map(PickingJobSchedule::getId).collect(Collectors.toSet());

		assertThat(chunked).as("no duplication in the chunked result").hasSize(chunkedIds.size());
		assertThat(unchunked).as("no duplication in the unchunked result").hasSize(unchunkedIds.size());
		assertThat(chunkedIds).as("chunked result must contain exactly the same rows as the unchunked result (no loss, no duplication)").isEqualTo(unchunkedIds);

		assertThat(groupByShipmentScheduleId(chunked))
				.as("grouping by shipment-schedule id must match between chunked and unchunked results")
				.isEqualTo(groupByShipmentScheduleId(unchunked));
	}

	/** The chunk-size cap must stay strictly positive and within the JDBC 2-byte bind-param limit. */
	@Test
	void maxShipmentScheduleIdsPerQuery_staysUnderJdbc2ByteParamLimit()
	{
		assertThat(PickingJobScheduleRepository.MAX_SHIPMENT_SCHEDULE_IDS_PER_QUERY)
				.isGreaterThan(0)
				.isLessThanOrEqualTo(32767);
	}

	/** A shipment-schedule-id set at or below the cap takes the fast path — identical to the plain (unchunked) query. */
	@Test
	void stream_withSetSizeAtOrBelowCap_isEquivalentToPlainQuery()
	{
		final ImmutableList<ShipmentScheduleId> shipmentScheduleIds = ImmutableList.of(
				ShipmentScheduleId.ofRepoId(600001),
				ShipmentScheduleId.ofRepoId(600002),
				ShipmentScheduleId.ofRepoId(600003));

		for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
		{
			createJobScheduleRecord(shipmentScheduleId);
		}

		final PickingJobScheduleQuery query = PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(shipmentScheduleIds)
				.build();

		final PickingJobScheduleRepository repo = PickingJobScheduleRepository.newInstanceForUnitTesting();

		final List<PickingJobSchedule> capped = repo.stream(query, 10_000).collect(Collectors.toList());
		final List<PickingJobSchedule> plain = repo.stream(query).collect(Collectors.toList());

		assertThat(capped).as("fast-path equivalence: cap far above set size behaves like the plain query").containsExactlyElementsOf(plain);
	}
}
