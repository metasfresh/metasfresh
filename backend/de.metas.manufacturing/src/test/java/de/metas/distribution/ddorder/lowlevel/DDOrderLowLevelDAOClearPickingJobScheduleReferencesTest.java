package de.metas.distribution.ddorder.lowlevel;

import de.metas.picking.api.PickingJobScheduleId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins that a departing workstation assignment leaves no DD_Order or DD_OrderLine pointing at it — the deferrable
 * constraints mpickingjobschedule_ddorder / _ddorderline are checked at commit of the assignment's delete.
 */
class DDOrderLowLevelDAOClearPickingJobScheduleReferencesTest
{
	private static final PickingJobScheduleId DEPARTING = PickingJobScheduleId.ofRepoId(1_000_001);
	private static final PickingJobScheduleId OTHER = PickingJobScheduleId.ofRepoId(1_000_002);

	private DDOrderLowLevelDAO ddOrderLowLevelDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
	}

	@Test
	void clearsBothHeaderAndLineOfTheDepartingAssignment()
	{
		final I_DD_Order ddOrder = createDDOrder(DEPARTING);
		final I_DD_OrderLine ddOrderLine = createDDOrderLine(ddOrder, DEPARTING);

		ddOrderLowLevelDAO.clearPickingJobScheduleReferences(DEPARTING);

		assertThat(refresh(ddOrder).getM_Picking_Job_Schedule_ID()).isLessThanOrEqualTo(0);
		assertThat(refresh(ddOrderLine).getM_Picking_Job_Schedule_ID()).isLessThanOrEqualTo(0);
	}

	@Test
	void leavesAnotherAssignmentsReferencesAlone()
	{
		final I_DD_Order ddOrder = createDDOrder(OTHER);
		final I_DD_OrderLine ddOrderLine = createDDOrderLine(ddOrder, OTHER);

		ddOrderLowLevelDAO.clearPickingJobScheduleReferences(DEPARTING);

		assertThat(refresh(ddOrder).getM_Picking_Job_Schedule_ID()).isEqualTo(OTHER.getRepoId());
		assertThat(refresh(ddOrderLine).getM_Picking_Job_Schedule_ID()).isEqualTo(OTHER.getRepoId());
	}

	private static I_DD_Order createDDOrder(final PickingJobScheduleId pickingJobScheduleId)
	{
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setM_Picking_Job_Schedule_ID(pickingJobScheduleId.getRepoId());
		InterfaceWrapperHelper.saveRecord(ddOrder);
		return ddOrder;
	}

	private static I_DD_OrderLine createDDOrderLine(final I_DD_Order ddOrder, final PickingJobScheduleId pickingJobScheduleId)
	{
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setM_Picking_Job_Schedule_ID(pickingJobScheduleId.getRepoId());
		InterfaceWrapperHelper.saveRecord(ddOrderLine);
		return ddOrderLine;
	}

	private static <T> T refresh(final T record)
	{
		InterfaceWrapperHelper.refresh(record);
		return record;
	}
}
