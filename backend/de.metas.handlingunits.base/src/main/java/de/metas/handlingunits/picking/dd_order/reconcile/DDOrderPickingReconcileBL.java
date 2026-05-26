package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.inout.ShipmentScheduleId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.compiere.model.I_M_Warehouse;

public interface DDOrderPickingReconcileBL
{
	/** Sync guard from M_ShipmentSchedule.beforeSave. Throws AdempiereException if picker is busy on an existing live DD_Order for this schedule. No-op otherwise. */
	void assertCanChange(I_M_ShipmentSchedule schedule);

	/** Sync registration from M_ShipmentSchedule.afterSave. Coalesces and publishes a single reconcile event per distinct schedule id after the current transaction commits. No-op when warehouse is not a packing warehouse. */
	void scheduleReconcileAfterCommit(I_M_ShipmentSchedule schedule);

	/**
	 * Async handler entry point. Re-reads schedule, classifies the action (NONE/CREATE/RECREATE/VOID), executes it.
	 *
	 * <p><b>No transaction boundary.</b> The RECREATE branch voids the existing DD_Order then creates a fresh one;
	 * this is only atomic if the caller wraps the call in a transaction. The caller (the async event handler, T17)
	 * MUST invoke this via {@code trxManager.runInNewTrx(() -> bl.reconcile(scheduleId))} so a create-failure rolls
	 * back the void — never call {@code reconcile()} bare.</p>
	 */
	void reconcile(ShipmentScheduleId scheduleId);

	/** Drift watchdog. Scans packing-warehouse schedules with no live DD_Order and republishes events. Called by the DD_Order_Picking_Rebuild JavaProcess + the hourly AD_Scheduler. */
	void rebuildDrift();

	/** Throws AdempiereException if the warehouse has IsPackingWarehouse=Y but no DD_NetworkDistribution_ID set. */
	void assertWarehouseConfigurationIsValid(I_M_Warehouse warehouse);

	/** Shared picker-busy check: returns true iff a PickingJobLine references the DD_Order's M_Movement. */
	boolean isPickerBusy(DDOrderId ddOrderId);
}
