package de.metas.inoutcandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.qty_reservation.qty_delivered_update.UpdateQtyDeliveredDispatcher;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut_Shipment
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL;
	@NonNull private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;
	@NonNull private final UpdateQtyDeliveredDispatcher updateQtyDeliveredDispatcher;

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_COMPLETE })
	public void invalidateShipmentSchedulesForLines(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			// Only if it's a shipment
			if (!inoutRecord.isSOTrx())
			{
				return;
			}
			// we only need to invalidate for the respective lines, because basically we only need to shift the qty from QtyPicked to QtyDelivered.
			// No other shipment schedule will have anything more or less after that.
			trxManager.runAfterCommit(() -> shipmentScheduleInvalidateBL.invalidateJustForLines(inoutRecord));
		}
	}

	/**
	 * Note: a deletion of an InOut in the GUI doesn't cause M_InOutLine's model validator to be fired
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			if (!inoutRecord.isSOTrx())
			{
				return;
			}

			shipmentScheduleInvalidateBL.invalidateJustForLines(inoutRecord); // make sure that at least the lines themselves are invalidated
			shipmentScheduleInvalidateBL.notifySegmentsChangedForShipment(inoutRecord);
		}
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, // note: on AFTER_NEW, there can't be any M_ShipmentSchedule_QtyPicked records to update yet, so we don't have to fire
			ifColumnsChanged = I_M_InOut.COLUMNNAME_Processed)
	public void updateM_ShipmentSchedule_QtyPicked_Processed(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			if (!inoutRecord.isSOTrx())
			{
				return;
			}

			shipmentScheduleAllocDAO.updateM_ShipmentSchedule_QtyPicked_ProcessedForShipment(inoutRecord);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void closePartiallyShipped_ShipmentSchedules(@NonNull final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			shipmentScheduleBL.closePartiallyShipped_ShipmentSchedules(inoutRecord);
		}
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REACTIVATE })
	public void updateQtyReservationDelivered(@NonNull final I_M_InOut inoutRecord)
	{
		if (!inoutRecord.isSOTrx()) {return;}

		final InOutId shipmentId = InOutId.ofRepoId(inoutRecord.getM_InOut_ID());
		updateQtyDeliveredDispatcher.fireShipmentChanged(shipmentId);
	}
}
