package de.metas.inoutcandidate.invalidation;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.util.Collection;
import java.util.Set;

public interface IShipmentScheduleInvalidateBL extends ISingletonService
{
	boolean isFlaggedForRecompute(ShipmentScheduleId shipmentScheduleId);

	void flagForRecompute(ShipmentScheduleId shipmentScheduleId);

	void flagForRecompute(Set<ShipmentScheduleId> shipmentScheduleIds);

	void flagForRecomputeStorageSegment(IShipmentScheduleSegment storageSegment);

	void flagSegmentForRecompute(Collection<IShipmentScheduleSegment> storageSegments);

	/**
	 * Invalidates shipment schedules for the given storage segments.
	 * <p>
	 * <b>IMPORTANT:</b> won't invalidate any processed schedules.
	 *
	 * @param addToSelectionId if not null will add the invalidated records to given selection
	 */
	void flagSegmentsForRecompute(Collection<IShipmentScheduleSegment> storageSegments, PInstanceId addToSelectionId);

	/**
	 * Invalidate just the shipment schedules that directly reference the given <code>shipment</code>'s lines.<br>
	 * Use this method if you know that no re-allocation of on-hand-qtys is required, but just the affected schedules
	 * need to be updated (e.g. QtyPicked => QtyDelivered, if an InOut is completed).
	 */
	void invalidateJustForLines(I_M_InOut shipment);

	/**
	 * See {@link #invalidateJustForLines(I_M_InOut)}.
	 */
	void flagForRecompute(I_M_InOutLine shipmentLine);

	/**
	 * See {@link #notifySegmentChangedForShipmentLine(I_M_InOutLine)}.
	 */
	void notifySegmentsChangedForShipment(I_M_InOut shipment);

	/**
	 * Invalidates all shipment schedules that have the same product, bPartner, ASI and locator as the given line<br>
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 * Notes:
	 * <ul>
	 * <li>the set of such schedules is usually relatively small, compared with the set of all schedules that have the same product.
	 * <li>As stated, do not invalidate scheds with delivery rule force, because to get their QtyToDeliver, they don't need to care about other schedules anyways. That means that a dev might need to
	 * call {@link #invalidateJustForLines(I_M_InOut)} in addition to this method.
	 * <ul>
	 */
	void notifySegmentChangedForShipmentLine(I_M_InOutLine shipmentLine);

	/**
	 * For the given <code>schedule</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 */
	void notifySegmentChangedForShipmentSchedule(I_M_ShipmentSchedule schedule);

	/**
	 * Like {@link #notifySegmentChangedForShipmentSchedule(I_M_ShipmentSchedule)}, but always also include the given sched, even if it has delivery-rule "force"
	 * 
	 */
	void notifySegmentChangedForShipmentScheduleInclSched(I_M_ShipmentSchedule schedule);
	
	/**
	 * For the given <code>orderLine</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 */
	void notifySegmentChangedForOrderLine(I_C_OrderLine orderLine);

	/**
	 * Invalidate the shipment schedule referencing the given <code>orderLine</code>.
	 */
	void invalidateJustForOrderLine(I_C_OrderLine orderLine);
	
	/**
	 * Invalidates all shipment schedule entries whose order line has the given product id.
	 *
	 * @deprecated please be more selective with the invalidation, using storage segments
	 */
	@Deprecated
	void flagForRecompute(ProductId productId);
	
	/**
	 * Invalidates all shipment schedules which have one of the given <code>headerAggregationKeys</code>.
	 */
	void flagHeaderAggregationKeysForRecompute(Set<String> headerAggregationKeys);
	
	void notifySegmentChanged(IShipmentScheduleSegment storageSegment);

	/**
	 * Notify the registered listeners that a a bunch of segments changed. Maybe they can gain a performance benefit from processing them all at once.
	 */
	void notifySegmentsChanged(Collection<IShipmentScheduleSegment> storageSegments);
}
