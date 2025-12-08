package de.metas.inoutcandidate.api.impl;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link ShipmentScheduleAllocDAO}.
 *
 * @author tsa
 */
public class ShipmentScheduleAllocDAOTest
{
	private ShipmentScheduleAllocDAO dao;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		dao = (ShipmentScheduleAllocDAO)Services.get(IShipmentScheduleAllocDAO.class);
	}

	/**
	 * Some simple tests on
	 * <ul>
	 * <li>{@link IShipmentScheduleAllocDAO#retrieveNotOnShipmentLineRecords(ShipmentScheduleId, Class)}
	 * <li>{@link IShipmentScheduleAllocDAO#retrieveOnShipmentLineRecordsQuery(ShipmentScheduleId)}
	 * </ul>
	 * <p>
	 * to make sure:
	 * <ul>
	 * <li>are working correctly
	 * <li>are "delivered" and "not delivered" methods are complementary
	 * </ul>
	 */
	@Test
	public void test_retrievePickedNotDeliveredRecords()
	{
		final ShipmentScheduleId ss = createShipmentSchedule();
		final I_M_ShipmentSchedule_QtyPicked qp1 = createShipmentScheduleQtyPickedRecord(ss, 0);
		final I_M_ShipmentSchedule_QtyPicked qp2 = createShipmentScheduleQtyPickedRecord(ss, 1);
		final I_M_ShipmentSchedule_QtyPicked qp3 = createShipmentScheduleQtyPickedRecord(ss, 0);
		final I_M_ShipmentSchedule_QtyPicked qp4 = createShipmentScheduleQtyPickedRecord(ss, 2);

		assertThat(dao.retrieveNotOnShipmentLineRecords(ss, I_M_ShipmentSchedule_QtyPicked.class))
				.as("Expected picked but not delivered")
				.containsExactly(qp1, qp3);

		assertThat(dao.retrieveOnShipmentLineRecordsQuery(ss).create().list())
				.as("Expected picked AND delivered")
				.containsExactly(qp2, qp4);
	}

	private ShipmentScheduleId createShipmentSchedule()
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		InterfaceWrapperHelper.saveRecord(sched);
		return ShipmentScheduleId.ofRepoId(sched.getM_ShipmentSchedule_ID());
	}

	private I_M_ShipmentSchedule_QtyPicked createShipmentScheduleQtyPickedRecord(final ShipmentScheduleId shipmentScheduleId, final int inoutLineId)
	{
		final I_M_ShipmentSchedule_QtyPicked record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		record.setM_InOutLine_ID(inoutLineId);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}
}
