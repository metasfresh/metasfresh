package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.QueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentSchedulePA_RecordsWithUnprocessedSchedules_Test
{
	private ScheduleQueryCountingQueryBL queryBL;
	private IShipmentSchedulePA shipmentSchedulePA;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// ShipmentSchedulePA gets its IQueryBL when constructed, so register the replacement first
		queryBL = new ScheduleQueryCountingQueryBL();
		Services.registerService(IQueryBL.class, queryBL);
		shipmentSchedulePA = new ShipmentSchedulePA();
	}

	private static TableRecordReference orderLine(final int recordId)
	{
		return TableRecordReference.of(I_C_OrderLine.Table_Name, recordId);
	}

	private static TableRecordReference order(final int recordId)
	{
		return TableRecordReference.of(I_C_Order.Table_Name, recordId);
	}

	private void schedule(final TableRecordReference recordRef, final boolean processed, final boolean active)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setAD_Table_ID(recordRef.getAD_Table_ID());
		schedule.setRecord_ID(recordRef.getRecord_ID());
		schedule.setProcessed(processed);
		schedule.setIsActive(active);
		saveRecord(schedule);
	}

	@Test
	public void onlyRecordsWithActiveUnprocessedSchedule()
	{
		schedule(orderLine(1), false, true); // open
		schedule(orderLine(2), true, true); // processed only
		schedule(orderLine(3), false, false); // inactive only
		// orderLine(4): no schedule
		schedule(order(5), false, true); // open, but other table

		assertThat(shipmentSchedulePA.retrieveRecordsWithUnprocessedSchedules(ImmutableList.of(orderLine(1), orderLine(2), orderLine(3), orderLine(4), orderLine(5))))
				.containsExactlyInAnyOrder(orderLine(1));
	}

	@Test
	public void recordsOfSeveralTables()
	{
		schedule(orderLine(1), false, true);
		schedule(order(1), false, true);
		schedule(order(2), true, true);

		assertThat(shipmentSchedulePA.retrieveRecordsWithUnprocessedSchedules(ImmutableList.of(orderLine(1), order(1), order(2))))
				.containsExactlyInAnyOrder(orderLine(1), order(1));
		assertThat(queryBL.scheduleQueryCount.get()).as("one query per table").isEqualTo(2);
	}

	@Test
	public void recordWithOpenAndProcessedSchedule_isReturned()
	{
		schedule(orderLine(6), true, true);
		schedule(orderLine(6), false, true);

		assertThat(shipmentSchedulePA.retrieveRecordsWithUnprocessedSchedules(ImmutableList.of(orderLine(6))))
				.containsExactlyInAnyOrder(orderLine(6));
	}

	@Test
	public void emptyInput_returnsEmptyWithoutQuerying()
	{
		assertThat(shipmentSchedulePA.retrieveRecordsWithUnprocessedSchedules(ImmutableList.of())).isEmpty();
		assertThat(queryBL.scheduleQueryCount.get()).as("no query").isZero();
	}

	/**
	 * Counts the {@link I_M_ShipmentSchedule} query builders it creates.
	 */
	private static class ScheduleQueryCountingQueryBL extends QueryBL
	{
		final AtomicInteger scheduleQueryCount = new AtomicInteger();

		@Override
		public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass, final Properties ctx, @Nullable final String trxName)
		{
			if (I_M_ShipmentSchedule.class.equals(modelClass))
			{
				scheduleQueryCount.incrementAndGet();
			}
			return super.createQueryBuilder(modelClass, ctx, trxName);
		}
	}
}
