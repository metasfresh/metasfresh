package de.metas.inoutcandidate.api;

import java.util.Set;

import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.ISingletonService;

public interface IShipmentScheduleInvalidateBL extends ISingletonService
{
	boolean isInvalid(ShipmentScheduleId shipmentScheduleId);

	void invalidateShipmentSchedule(ShipmentScheduleId shipmentScheduleId);

	void invalidateShipmentSchedules(Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Invalidate just the shipment schedules that directly reference the given <code>shipment</code>'s lines.<br>
	 * Use this method if you know that no re-allocation of on-hand-qtys is required, but just the affected schedules
	 * need to be updated (e.g. QtyPicked => QtyDelivered, if an InOut is completed).
	 */
	void invalidateJustForLines(I_M_InOut shipment);

	/**
	 * See {@link #invalidateJustForLines(I_M_InOut)}.
	 */
	void invalidateJustForLine(I_M_InOutLine shipmentLine);

	/**
	 * See {@link #invalidateSegmentForLine(I_M_InOutLine)}.
	 */
	void invalidateSegmentsForLines(I_M_InOut shipment);

	/**
	 * Invalidates all shipment schedules that have the same product, bPartner, ASI and locator as the given line<br>
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 * Notes:
	 * <ul>
	 * <li>the set of such schedules is usually relatively small, compared with the set of all schedules that have the same product.
	 * <li>As stated, do not invalidate scheds with delivery rule force, because to get their QtyToDeliver, they don't need to care about other schedules anyways. That means that a dev might need to
	 * call {@link #invalidateJustForLines(I_M_InOut)} in addition to this method.
	 * <ul>
	 *
	 * @param shipmentLine
	 * @see IShipmentSchedulePA#invalidateStorageSegments(java.util.Collection)
	 */
	void invalidateSegmentForLine(I_M_InOutLine shipmentLine);

	/**
	 * For the given <code>schedule</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 */
	void invalidateSegmentForShipmentSchedule(I_M_ShipmentSchedule schedule);

	/**
	 * For the given <code>orderLine</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 */
	void invalidateSegmentForOrderLine(I_C_OrderLine orderLine);

	/**
	 * Invalidate the shipment schedule referencing the given <code>orderLine</code>.
	 *
	 * @param orderLine
	 */
	void invalidateJustForOrderLine(I_C_OrderLine orderLine);
}
