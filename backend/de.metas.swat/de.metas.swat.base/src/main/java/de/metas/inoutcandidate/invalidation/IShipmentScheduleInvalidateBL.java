package de.metas.inoutcandidate.invalidation;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

public interface IShipmentScheduleInvalidateBL extends ISingletonService
{
	/**
	 * SysConfig (or system property): when {@code Y}, a recompute flag written outside its record's or data's transaction throws instead of only logging a warning.
	 */
	String SYSCONFIG_FailOnTrxMismatch = "de.metas.inoutcandidate.invalidation.FailOnTrxMismatch";

	boolean isFlaggedForRecompute(ShipmentScheduleId shipmentScheduleId);

	void flagForRecompute(ShipmentScheduleId shipmentScheduleId);

	void flagForRecompute(Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Same as {@link #flagForRecompute(Set)}, but first verifies that the caller's data (the changes that make the flags necessary) is in the
	 * thread-inherited transaction, which is where the flags are written. A mismatch means the flags would not commit or roll back with the data:
	 * it throws in unit-test mode or with SysConfig FailOnTrxMismatch=Y, otherwise it only warns (and it only warns inside transaction listener callbacks).
	 *
	 * @param dataTrxName the transaction the caller's data change is in (or will be written in); {@code null} for none
	 */
	void flagForRecompute(Set<ShipmentScheduleId> shipmentScheduleIds, @Nullable String dataTrxName);

	/**
	 * Same as {@link #flagSegmentForRecompute(Collection)} for one segment.
	 */
	void flagForRecomputeStorageSegment(IShipmentScheduleSegment storageSegment);

	/**
	 * Flags the shipment schedules of the given storage segments for recompute.
	 * <p>
	 * Routes by the thread-inherited transaction: if it is open for writes (active, or created but not yet started - transactions start lazily on
	 * their first DB access), the flags are written in it (they commit or roll back with the caller's change);
	 * otherwise (none, or committed and running its after-commit/rollback/close listeners without having been restarted) they are written out of transaction.
	 * Callers that must not write into the current transaction use {@link #flagSegmentsForRecompute(Collection, PInstanceId)}.
	 */
	void flagSegmentForRecompute(Collection<IShipmentScheduleSegment> storageSegments);

	/**
	 * Invalidates shipment schedules for the given storage segments.
	 * <p>
	 * <b>IMPORTANT:</b> won't invalidate any processed schedules.
	 * <p>
	 * Always writes out of transaction (a separate, immediately committed one), regardless of the thread-inherited transaction.
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

	/**
	 * Deletes the schedule's {@code M_ShipmentSchedule_Recompute} markers, tagged included.
	 */
	void deleteRecomputeMarkers(ShipmentScheduleId shipmentScheduleId);
}
