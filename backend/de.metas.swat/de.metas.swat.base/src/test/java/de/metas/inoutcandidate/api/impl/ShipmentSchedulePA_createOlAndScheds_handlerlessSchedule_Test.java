package de.metas.inoutcandidate.api.impl;

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

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.util.Services;

/**
 * A single {@link I_M_ShipmentSchedule} whose {@code AD_Table_ID} has no registered
 * {@link ShipmentScheduleHandler} must NOT abort the whole recompute batch:
 * {@link ShipmentSchedulePA#createOlAndScheds(List)} must skip the handler-less schedule (surfaced as an
 * {@code AD_Issue}) and still process the resolvable schedules in the same batch, rather than letting the
 * "No shipment schedule handler defined for ..." exception propagate and roll back the entire
 * {@code UpdateInvalidShipmentSchedules} batch (which would jam every other schedule in the recompute queue).
 */
class ShipmentSchedulePA_createOlAndScheds_handlerlessSchedule_Test
{
	private ShipmentSchedulePA shipmentSchedulePA;
	private ShipmentScheduleHandlerBL shipmentScheduleHandlerBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		shipmentScheduleHandlerBL = new ShipmentScheduleHandlerBL();
		Services.registerService(IShipmentScheduleHandlerBL.class, shipmentScheduleHandlerBL);

		shipmentSchedulePA = new ShipmentSchedulePA();
	}

	private int createTable(final String tableName)
	{
		final I_AD_Table table = newInstance(I_AD_Table.class);
		table.setTableName(tableName);
		table.setName(tableName);
		table.setEntityType("D");
		saveRecord(table);
		return table.getAD_Table_ID();
	}

	private I_M_ShipmentSchedule createScheduleFor(final int adTableId)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setAD_Table_ID(adTableId);
		saveRecord(sched);
		// self-referential Record_ID, like the recompute-batching seed fixture (not read by createOlAndScheds)
		sched.setRecord_ID(sched.getM_ShipmentSchedule_ID());
		saveRecord(sched);
		return sched;
	}

	@Test
	void createOlAndScheds_skipsHandlerlessSchedule_insteadOfAbortingTheBatch()
	{
		// resolvable: a table WITH a registered handler
		final int resolvableTableId = createTable(I_C_OrderLine.Table_Name);
		shipmentScheduleHandlerBL.registerHandler(new StubHandler(I_C_OrderLine.Table_Name));
		final I_M_ShipmentSchedule resolvable = createScheduleFor(resolvableTableId);

		// handler-less: a self-referential M_ShipmentSchedule table id with NO registered handler
		// (mirrors the seedShipmentSchedulesWithUntaggedRecomputeMarker poison row)
		final int handlerlessTableId = createTable(I_M_ShipmentSchedule.Table_Name);
		final I_M_ShipmentSchedule handlerless = createScheduleFor(handlerlessTableId);

		// handler-less schedule first, so the old code aborts before ever reaching the resolvable one
		final List<OlAndSched> result = shipmentSchedulePA.createOlAndScheds(
				ImmutableList.of(handlerless, resolvable));

		assertThat(result)
				.as("the handler-less schedule is skipped; the resolvable one is still processed (batch not aborted)")
				.hasSize(1);
		assertThat(result.get(0).getShipmentScheduleId().getRepoId())
				.isEqualTo(resolvable.getM_ShipmentSchedule_ID());
	}

	/** Minimal handler: resolves for its source table and returns a trivial deliver-request. */
	private static class StubHandler extends ShipmentScheduleHandler
	{
		private final String sourceTable;

		StubHandler(final String sourceTable)
		{
			this.sourceTable = sourceTable;
		}

		@Override
		public String getSourceTable()
		{
			return sourceTable;
		}

		@Override
		public IDeliverRequest createDeliverRequest(final I_M_ShipmentSchedule sched, final I_C_OrderLine salesOrderLine)
		{
			return () -> BigDecimal.ZERO;
		}

		@Override
		public Iterator<?> retrieveModelsWithMissingCandidates(final Properties ctx, final String trxName, final QueryLimit limit)
		{
			throw new UnsupportedOperationException("not exercised by this test");
		}

		@Override
		public List<I_M_ShipmentSchedule> createCandidatesFor(final Object model)
		{
			throw new UnsupportedOperationException("not exercised by this test");
		}

		@Override
		public void updateShipmentScheduleFromReferencedRecord(final I_M_ShipmentSchedule shipmentSchedule)
		{
			// not exercised by this test
		}

		@Override
		public void invalidateCandidatesFor(final Object model)
		{
			// not exercised by this test
		}
	}
}
