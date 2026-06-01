package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Delays invoice notification until all carrier-advised shipment schedules of the invoice's
 * shipments have their advising resolved — controlled by SysConfig
 * {@link #SYSCONFIG_DelayUntilCarrierConfirmed}.
 *
 * <p>A delay is issued when ANY linked {@code M_ShipmentSchedule} has a shipper assigned
 * ({@code M_Shipper_ID > 0}) AND its {@code Carrier_Advising_Status} is one of
 * {@code NR} (NotRequested), {@code R} (Requested), or {@code IP} (InProgress).
 * Statuses {@code CO} (Completed), {@code FA} (Failed), and {@code MAN} (Manual)
 * — as well as schedules with no shipper — do NOT cause a delay.</p>
 *
 * <p>This approach correctly catches the "carrier advising requested / in-progress but not yet
 * done" window that the previous parcel-existence check was blind to.</p>
 */
@Component
public class InvoiceNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	public static final String SYSCONFIG_DelayUntilCarrierConfirmed = "delayNotificationUntilShipmentConfirmedByCarrier";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log log)
	{
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_DelayUntilCarrierConfirmed, false, ClientAndOrgId.ofClientAndOrg(log.getAD_Client_ID(), log.getAD_Org_ID())))
		{
			return false;
		}

		final int invoiceId = log.getRecord_ID();
		if (invoiceId <= 0)
		{
			return false;
		}

		// Step 1: collect distinct M_InOutLine_IDs from the invoice lines
		final List<I_C_InvoiceLine> invoiceLines = queryBL
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addCompareFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, CompareQueryFilter.Operator.GREATER, 0)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final Set<Integer> inOutLineIds = new HashSet<Integer>();
		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			final int inOutLineId = invoiceLine.getM_InOutLine_ID();
			if (inOutLineId > 0)
			{
				inOutLineIds.add(inOutLineId);
			}
		}

		if (inOutLineIds.isEmpty())
		{
			// no shipment-linked lines → nothing to wait for
			return false;
		}

		// Step 2: collect distinct M_ShipmentSchedule_IDs via QtyPicked join
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final Set<Integer> scheduleIds = new HashSet<Integer>();
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedRecords)
		{
			final int scheduleId = qtyPicked.getM_ShipmentSchedule_ID();
			if (scheduleId > 0)
			{
				scheduleIds.add(scheduleId);
			}
		}

		if (scheduleIds.isEmpty())
		{
			// no linked schedules → nothing to wait for
			return false;
		}

		// Step 3: load the schedules and check Carrier_Advising_Status
		final List<I_M_ShipmentSchedule> schedules = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		for (final I_M_ShipmentSchedule schedule : schedules)
		{
			if (schedule.getM_Shipper_ID() <= 0)
			{
				// no shipper assigned → this schedule is not carrier-advised, skip
				continue;
			}
			final String advisingStatus = schedule.getCarrier_Advising_Status();
			if (X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_NotRequested.equals(advisingStatus)
					|| X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_Requested.equals(advisingStatus)
					|| X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_InProgress.equals(advisingStatus))
			{
				// carrier advising has not resolved yet → delay the notification
				return true;
			}
		}

		// carrier advising resolved (Completed/Failed/Manual) or not applicable
		return false;
	}
}
