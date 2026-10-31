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

package de.metas.inoutcandidate.modelvalidator;

import de.metas.document.engine.DocStatus;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the {@code TYPE_BEFORE_DELETE} guarded cascade of {@link C_OrderLine_ShipmentSchedule} onto
 * {@link I_M_ShipmentSchedule} records: a schedule with no real shipment yet is deleted along with its sales order
 * line, while a schedule with an active allocation to a non-voided/reversed inout blocks the delete.
 */
public class C_OrderLine_ShipmentScheduleTest
{
	private C_OrderLine_ShipmentSchedule c_OrderLine_ShipmentSchedule;

	private I_C_OrderLine salesOrderLineRecord;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		c_OrderLine_ShipmentSchedule = new C_OrderLine_ShipmentSchedule();

		final I_C_Order salesOrderRecord = newInstance(I_C_Order.class);
		salesOrderRecord.setIsSOTrx(true);
		saveRecord(salesOrderRecord);

		salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		saveRecord(salesOrderLineRecord);
	}

	private I_M_ShipmentSchedule createShipmentScheduleFor(final I_C_OrderLine orderLine)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		sched.setRecord_ID(orderLine.getC_OrderLine_ID());
		saveRecord(sched);
		return sched;
	}

	/**
	 * Creates an {@code M_InOut} + {@code M_InOutLine} + an active {@code M_ShipmentSchedule_QtyPicked} allocation
	 * linking the given schedule to that inout line -- i.e. a "real shipment" for the schedule.
	 */
	private void createActiveAllocation(final I_M_ShipmentSchedule sched, final String inOutDocStatus)
	{
		final I_M_InOut inOutRecord = newInstance(I_M_InOut.class);
		inOutRecord.setDocStatus(inOutDocStatus);
		saveRecord(inOutRecord);

		final I_M_InOutLine inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setM_InOut_ID(inOutRecord.getM_InOut_ID());
		saveRecord(inOutLineRecord);

		final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		qtyPickedRecord.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
		qtyPickedRecord.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());
		saveRecord(qtyPickedRecord);
	}

	private boolean shipmentScheduleStillExists(final I_M_ShipmentSchedule sched)
	{
		return POJOLookupMap.get().getRecords(I_M_ShipmentSchedule.class)
				.stream()
				.anyMatch(record -> record.getM_ShipmentSchedule_ID() == sched.getM_ShipmentSchedule_ID());
	}

	@Test
	public void schedule_withNoAlloc_isDeleted_onOrderLineDelete()
	{
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(salesOrderLineRecord);

		c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(salesOrderLineRecord);

		assertThat(shipmentScheduleStillExists(sched)).isFalse();
	}

	@Test
	public void schedule_withActiveAllocToNonVoidedInOut_blocksDelete_onOrderLineDelete()
	{
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(salesOrderLineRecord);
		createActiveAllocation(sched, DocStatus.Completed.getCode());

		assertThatThrownBy(() -> c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(salesOrderLineRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("SalesOrderLine_CannotDelete_HasCompletedDocs");

		assertThat(shipmentScheduleStillExists(sched)).isTrue();
	}

	@Test
	public void schedule_withAllocOnlyToVoidedInOut_isDeleted_onOrderLineDelete()
	{
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(salesOrderLineRecord);
		createActiveAllocation(sched, DocStatus.Voided.getCode());

		c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(salesOrderLineRecord); // must not throw

		assertThat(shipmentScheduleStillExists(sched)).isFalse();
	}

	@Test
	public void schedule_withAllocOnlyToReversedInOut_isDeleted_onOrderLineDelete()
	{
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(salesOrderLineRecord);
		createActiveAllocation(sched, DocStatus.Reversed.getCode());

		c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(salesOrderLineRecord); // must not throw

		assertThat(shipmentScheduleStillExists(sched)).isFalse();
	}

	/**
	 * Creates an active {@code M_InOut} + {@code M_InOutLine} whose {@code C_OrderLine_ID} points directly at the
	 * given order line, with NO {@code M_ShipmentSchedule_QtyPicked} row -- i.e. the manually-created-shipment case.
	 */
	private void createDirectOrderLineLink(final I_C_OrderLine orderLine, final String inOutDocStatus)
	{
		final I_M_InOut inOutRecord = newInstance(I_M_InOut.class);
		inOutRecord.setDocStatus(inOutDocStatus);
		saveRecord(inOutRecord);

		final I_M_InOutLine inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setM_InOut_ID(inOutRecord.getM_InOut_ID());
		inOutLineRecord.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		saveRecord(inOutLineRecord);
	}

	@Test
	public void schedule_withDirectInOutLineLinkToNonVoidedInOut_blocksDelete_onOrderLineDelete()
	{
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(salesOrderLineRecord);
		createDirectOrderLineLink(salesOrderLineRecord, DocStatus.Completed.getCode());

		assertThatThrownBy(() -> c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(salesOrderLineRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("SalesOrderLine_CannotDelete_HasCompletedDocs");

		assertThat(shipmentScheduleStillExists(sched)).isTrue();
	}

	@Test
	public void purchaseOrderLine_isNeverGuardedOrCascaded()
	{
		final I_C_Order purchaseOrderRecord = newInstance(I_C_Order.class);
		purchaseOrderRecord.setIsSOTrx(false);
		saveRecord(purchaseOrderRecord);

		final I_C_OrderLine purchaseOrderLineRecord = newInstance(I_C_OrderLine.class);
		purchaseOrderLineRecord.setC_Order(purchaseOrderRecord);
		saveRecord(purchaseOrderLineRecord);

		// the schedule genuinely references the *purchase* order line under test and has an active alloc to a
		// non-voided inout -- if the isSOTrx gate were removed, this would block the delete
		final I_M_ShipmentSchedule sched = createShipmentScheduleFor(purchaseOrderLineRecord);
		createActiveAllocation(sched, DocStatus.Completed.getCode());

		c_OrderLine_ShipmentSchedule.deleteOrGuardShipmentSchedules(purchaseOrderLineRecord); // must not throw

		assertThat(shipmentScheduleStillExists(sched)).isTrue();
	}
}
