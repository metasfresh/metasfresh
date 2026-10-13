package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentSchedulePA_RecordIdsWithUnprocessedSchedules_Test
{
	private IShipmentSchedulePA shipmentSchedulePA;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	}

	private void schedule(final int adTableId, final int recordId, final boolean processed, final boolean active)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setAD_Table_ID(adTableId);
		schedule.setRecord_ID(recordId);
		schedule.setProcessed(processed);
		schedule.setIsActive(active);
		saveRecord(schedule);
	}

	@Test
	public void onlyRecordsWithActiveUnprocessedSchedule()
	{
		schedule(100, 1, false, true); // open
		schedule(100, 2, true, true); // processed only
		schedule(100, 3, false, false); // inactive only
		// record 4: no schedule
		schedule(200, 5, false, true); // open, but other table

		assertThat(shipmentSchedulePA.retrieveRecordIdsWithUnprocessedSchedules(100, ImmutableList.of(1, 2, 3, 4, 5)))
				.containsExactlyInAnyOrder(1);
	}

	@Test
	public void recordWithOpenAndProcessedSchedule_isReturned()
	{
		schedule(100, 6, true, true);
		schedule(100, 6, false, true);

		assertThat(shipmentSchedulePA.retrieveRecordIdsWithUnprocessedSchedules(100, ImmutableList.of(6)))
				.containsExactlyInAnyOrder(6);
	}

	@Test
	public void emptyInput_returnsEmpty()
	{
		assertThat(shipmentSchedulePA.retrieveRecordIdsWithUnprocessedSchedules(100, ImmutableList.of())).isEmpty();
	}
}
