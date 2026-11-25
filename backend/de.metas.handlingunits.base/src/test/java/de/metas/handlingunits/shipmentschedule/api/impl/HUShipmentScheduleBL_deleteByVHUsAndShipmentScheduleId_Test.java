package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link IHUShipmentScheduleBL#deleteByVHUsAndShipmentScheduleId(java.util.Collection, ShipmentScheduleId)}
 * and the batch query {@link IHUShipmentScheduleDAO#retrieveByVHUsAndShipmentScheduleId(java.util.Collection, ShipmentScheduleId)}.
 */
class HUShipmentScheduleBL_deleteByVHUsAndShipmentScheduleId_Test
{
	private static final int VHU_ID_1 = 1_111_111;
	private static final int VHU_ID_2 = 2_222_222;
	private static final int VHU_ID_3 = 3_333_333;

	private IHUShipmentScheduleBL bl;
	private IHUShipmentScheduleDAO dao;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		bl = Services.get(IHUShipmentScheduleBL.class);
		dao = Services.get(IHUShipmentScheduleDAO.class);
	}

	@Test
	void query_returnsActiveRowsOfTheGivenVHUsAndScheduleOnly()
	{
		final ShipmentScheduleId schedule = createSchedule();
		final ShipmentScheduleId otherSchedule = createSchedule();
		final int row1 = newRow(schedule, VHU_ID_1, true).getM_ShipmentSchedule_QtyPicked_ID();
		final int row2 = newRow(schedule, VHU_ID_2, true).getM_ShipmentSchedule_QtyPicked_ID();
		newRow(schedule, VHU_ID_3, true);
		newRow(schedule, VHU_ID_1, false);
		newRow(otherSchedule, VHU_ID_1, true);

		assertThat(dao.retrieveByVHUsAndShipmentScheduleId(ImmutableSet.of(HuId.ofRepoId(VHU_ID_1), HuId.ofRepoId(VHU_ID_2)), schedule))
				.extracting(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID)
				.containsExactly(row1, row2);
	}

	@Test
	void deletesTheRowsOfAllGivenVHUs_keepsTheOthers()
	{
		final ShipmentScheduleId schedule = createSchedule();
		newRow(schedule, VHU_ID_1, true);
		newRow(schedule, VHU_ID_2, true);
		final int keptRow = newRow(schedule, VHU_ID_3, true).getM_ShipmentSchedule_QtyPicked_ID();

		bl.deleteByVHUsAndShipmentScheduleId(ImmutableSet.of(HuId.ofRepoId(VHU_ID_1), HuId.ofRepoId(VHU_ID_2)), schedule);

		assertThat(dao.retrieveByVHUsAndShipmentScheduleId(ImmutableSet.of(HuId.ofRepoId(VHU_ID_1), HuId.ofRepoId(VHU_ID_2)), schedule)).isEmpty();
		assertThat(dao.retrieveByVHUsAndShipmentScheduleId(ImmutableSet.of(HuId.ofRepoId(VHU_ID_3)), schedule))
				.extracting(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID)
				.containsExactly(keptRow);
	}

	@Test
	void emptyVHUs_isNoop()
	{
		final ShipmentScheduleId schedule = createSchedule();
		newRow(schedule, VHU_ID_1, true);

		bl.deleteByVHUsAndShipmentScheduleId(ImmutableSet.of(), schedule);

		assertThat(dao.retrieveByVHUsAndShipmentScheduleId(ImmutableSet.of(HuId.ofRepoId(VHU_ID_1)), schedule)).hasSize(1);
	}

	@Test
	void alreadyShippedRow_isRefused_andNothingIsDeleted()
	{
		final ShipmentScheduleId schedule = createSchedule();
		newRow(schedule, VHU_ID_1, true);
		final I_M_ShipmentSchedule_QtyPicked shipped = newRow(schedule, VHU_ID_2, true);
		shipped.setM_InOutLine_ID(createInOutLine().getM_InOutLine_ID());
		InterfaceWrapperHelper.saveRecord(shipped);

		final ImmutableSet<HuId> vhuIds = ImmutableSet.of(HuId.ofRepoId(VHU_ID_1), HuId.ofRepoId(VHU_ID_2));
		assertThatThrownBy(() -> bl.deleteByVHUsAndShipmentScheduleId(vhuIds, schedule)).isInstanceOf(AdempiereException.class);

		assertThat(dao.retrieveByVHUsAndShipmentScheduleId(vhuIds, schedule)).hasSize(2);
	}

	private I_M_InOutLine createInOutLine()
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inOut.setDocumentNo("SHIP-1");
		InterfaceWrapperHelper.saveRecord(inOut);
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		InterfaceWrapperHelper.saveRecord(inOutLine);
		return inOutLine;
	}

	private ShipmentScheduleId createSchedule()
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		InterfaceWrapperHelper.saveRecord(sched);
		return ShipmentScheduleId.ofRepoId(sched.getM_ShipmentSchedule_ID());
	}

	private I_M_ShipmentSchedule_QtyPicked newRow(final ShipmentScheduleId scheduleId, final int vhuId, final boolean active)
	{
		final I_M_ShipmentSchedule_QtyPicked row = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		row.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		row.setVHU_ID(vhuId);
		row.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(row);
		return row;
	}
}
