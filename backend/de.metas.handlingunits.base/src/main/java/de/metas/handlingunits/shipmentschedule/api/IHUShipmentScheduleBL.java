package de.metas.handlingunits.shipmentschedule.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.impl.TULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.ShipmentScheduleLoadingCache;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IHUShipmentScheduleBL extends ISingletonService
{
	I_M_ShipmentSchedule getById(ShipmentScheduleId id);

	LocatorId getDefaultLocatorId(I_M_ShipmentSchedule shipmentSchedule);

	Map<ShipmentScheduleId, de.metas.handlingunits.model.I_M_ShipmentSchedule> getByIds(@NonNull Set<ShipmentScheduleId> ids);

	BPartnerLocationId getBPartnerLocationId(I_M_ShipmentSchedule shipmentSchedule);

	void closeShipmentSchedule(I_M_ShipmentSchedule shipmentSchedule);

	void closeShipmentSchedules(Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Add QtyPicked to the current QtyPicked of given shipment schedule.
	 * <p>
	 * Also update the given <code>hu</code>'s (and therefore its childrens') <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> to the given <code>sched</code>'s effective values.<br>
	 * And finally update the given {@code tuOrVHU}'s status to "Picked".
	 */
	ShipmentScheduleWithHU addQtyPickedAndUpdateHU(AddQtyPickedRequest request);

	/**
	 * Try to add the request's qty into a single existing un-shipped {@link I_M_ShipmentSchedule_QtyPicked}
	 * row for the same {@code (M_ShipmentSchedule_ID, VHU_ID)} pair instead of creating a new row.
	 * <p>
	 * Scope is intentionally narrow — only HU-trx-listener-shaped picks are eligible:
	 * the request must have no {@code PickingJobScheduleId}, must not be marked as
	 * {@code anonymousHuPickedOnTheFly}, and the HU must be a virtual HU. The candidate
	 * row is also filtered to {@code M_Picking_Job_Schedule_ID IS NULL} and
	 * {@code IsAnonymousHuPickedOnTheFly = N} so that genuine multi-job picks on the same
	 * VHU are never collapsed.
	 * <p>
	 * Used by {@code ShipmentScheduleHUTrxListener.trxLineProcessed} to defuse the duplicate-row
	 * pattern produced when an aggregate HU's snapshot is replayed (one VHU node receives
	 * multiple HU-trx lines in one transaction).
	 *
	 * @return {@code true} if a matching row was found and the qty was merged into it (caller
	 *         should skip the regular new-row path); {@code false} otherwise (caller should fall
	 *         through to {@link #addQtyPickedAndUpdateHU(AddQtyPickedRequest)}).
	 */
	boolean tryMergeQtyPickedIntoExistingForVHU(AddQtyPickedRequest request);

	/**
	 * Shipment-reverse safety net. Given the (now-deactivated) consolidated {@code M_ShipmentSchedule_QtyPicked}
	 * rows that were assigned to a just-reversed shipment, ensure each {@code (M_ShipmentSchedule_ID, VHU_ID)}
	 * still has an active, not-yet-shipped picked row — re-creating one (a direct copy of the reversed
	 * allocation, with the VHU set back to {@code Picked}) when none survives.
	 * <p>
	 * The picking-job reopen ({@code PickingJobReopenCommand}) only restores picked qty for a <b>Completed</b>
	 * job. When a shipment was recreated via "Generate Shipments" the job is left <b>Drafted</b>, so a subsequent
	 * reverse restores nothing and the shipment can no longer be recreated (me03#29561). This net closes that gap
	 * without step-replay (so no {@code tryMerge} qty inflation) and only fires when nothing was restored, so the
	 * Completed-job path and unrelated in-progress jobs are unaffected.
	 */
	void restoreUnshippedQtyPickedIfMissing(Collection<I_M_ShipmentSchedule_QtyPicked> reversedAllocations);

	/**
	 * Creates a producer which will create shipments ({@link I_M_InOut}) from {@link ShipmentScheduleWithHU}s.
	 */
	IInOutProducerFromShipmentScheduleWithHU createInOutProducerFromShipmentSchedule();

	/**
	 * @param movementDate shipment's movement date (used to filter only if we have an consolidation period set)
	 * @return shipment which is still open for the shipment schedule (first) and it's HU specifications (shipper transportation) or null if none is found
	 */
	@Nullable
	I_M_InOut getOpenShipmentOrNull(@NonNull ShipmentScheduleWithHU candidate, @NonNull LocalDate movementDate);

	/**
	 * Update all allocations from given TU and call {@link I_M_ShipmentSchedule_QtyPicked#setM_LU_HU(I_M_HU)} by setting the current TU's LU.
	 */
	void updateAllocationLUForTU(I_M_HU tuHU);

	void updateAllocationLUAndTUForCU(I_M_HU cuHU);

	/**
	 * Unassigns the given <code>shipmmentSchedule</code> from the given <code>tuHU</code> by inactivating existing {@link I_M_ShipmentSchedule_QtyPicked} records.<br>
	 * Also updates the given <code>tuHU</code> 's (and therefore its childrens') <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> to <code>null</code>.<br>
	 * If there is no assignment, then the method just returns.
	 *
	 * @throws AdempiereException if the Qty of the existing active {@link I_M_ShipmentSchedule_QtyPicked} records sums up to a number greater than zero.
	 */
	void unallocateTU(I_M_ShipmentSchedule shipmentSchedule, I_M_HU tuHU, String trxName);

	/**
	 * Gets {@link I_M_HU_PI_Item_Product} by checking:
	 * <ul>
	 * <li>{@link de.metas.handlingunits.model.I_M_ShipmentSchedule#getM_HU_PI_Item_Product_Override_ID()}
	 * <li>{@link de.metas.handlingunits.model.I_M_ShipmentSchedule#getM_HU_PI_Item_Product_ID()}
	 * <li>{@link de.metas.handlingunits.model.I_C_OrderLine#getM_HU_PI_Item_Product_ID()}
	 * </ul>
	 * <p>
	 * If no PI item product was found, null will be returned.
	 *
	 * @return PI item product or null.
	 */
	HUPIItemProductId getEffectivePackingMaterialId(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * @see #getEffectivePackingMaterialId(I_M_ShipmentSchedule)
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_IgnoringPickedHUs(I_M_ShipmentSchedule shipmentSchedule);

	Optional<TULoader> createTULoader(de.metas.handlingunits.model.I_M_ShipmentSchedule schedule);

	I_M_HU_LUTU_Configuration deriveM_HU_LUTU_Configuration(I_M_ShipmentSchedule schedule);

	/**
	 * Creates a new aggregation key builder which can be used to decide if the HUs of two given shipment schedules can go into the same shipment.
	 */
	IAggregationKeyBuilder<ShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder();

	boolean isHUAllocation(I_M_ShipmentSchedule_QtyPicked qtyPickedRecord);

	/**
	 * Set QtyTU and M_HU_PI_Item_Product values based on override: If override not null, set it in the effective.<br>
	 * Otherwise, replace effective with original values.<br>
	 * Also, set the correct packing description based on the current value of M_HU_PI_Item_Product
	 */
	void updateEffectiveValues(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Initialize the qtys and HU PI Item product in shipment schedule
	 */
	void updateHURelatedValuesFromOrderLine(I_M_ShipmentSchedule shipmentSchedule);

	void updateExternalLineIdFromOrderLine(I_M_ShipmentSchedule shipmentSchedule);

	void deleteByTopLevelHUAndShipmentScheduleId(
			@NonNull HuId topLevelHUId,
			@NonNull ShipmentScheduleId shipmentScheduleId);

	void deleteByTopLevelHUsAndShipmentScheduleId(@NonNull Collection<I_M_HU> topLevelHUs, @NonNull ShipmentScheduleId shipmentScheduleId);

	/**
	 * Reduces the {@link I_M_ShipmentSchedule_QtyPicked} row(s) keyed to the given pick-to TU (i.e.
	 * {@code M_TU_HU_ID = pickToTuId}) by {@code qtyToReduce}, walking the rows newest-first (highest
	 * {@code M_ShipmentSchedule_QtyPicked_ID} first). A row fully consumed by the reduction is deleted;
	 * the last touched row (if any qty remains after it) is partially reduced instead. Use this — instead of
	 * {@link #deleteByTopLevelHUsAndShipmentScheduleId}/{@link #deleteByTopLevelHUAndShipmentScheduleId} —
	 * for a partial unpick-to-floor of a CU picked into a bare TU, where the picked-to row is keyed to the
	 * TU (not the leaf CU) and a blind delete would also erase the still-picked remainder.
	 */
	void reduceQtyPickedForPickToTU(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull HuId pickToTuId,
			@NonNull Quantity qtyToReduce);

	WarehouseId getWarehouseId(@NonNull I_M_ShipmentSchedule schedule);

	BPartnerId getBPartnerId(@NonNull I_M_ShipmentSchedule schedule);

	Quantity getQtyToDeliver(I_M_ShipmentSchedule schedule);

	Quantity getQtyScheduledForPicking(@NonNull I_M_ShipmentSchedule shipmentScheduleRecord);

	Quantity getQtyRemainingToScheduleForPicking(@NonNull I_M_ShipmentSchedule shipmentScheduleRecord);

	void flagForRecompute(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds);

	ShipmentScheduleLoadingCache<de.metas.handlingunits.model.I_M_ShipmentSchedule> newLoadingCache();

	/**
	 * Extracts a common projectId from the given shipment schedules. Null projectIds are not considered distinct values. So that:
	 * (G1, null) => G1
	 * (G1, G2) => null
	 * (null, null) => null
	 */
	@Nullable
	ProjectId extractSingleProjectIdOrNull(@NonNull List<ShipmentScheduleWithHU> candidates);

	/**
	 * Creates shipment candidates for a QtyPicked record, expanding mixed-origin TUs into
	 * per-VHU or per-COO-group candidates so each gets its own InOutLine with the correct ASI.
	 */
	List<ShipmentScheduleWithHU> createCandidatesForQtyPicked(
			@NonNull I_M_ShipmentSchedule_QtyPicked qtyPicked,
			@NonNull IHUContext huContext,
			@NonNull M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse);

	/**
	 * Retrieves all undelivered shipment schedule candidates for a list of top-level HUs,
	 * expanding any mixed-origin TUs into per-COO candidates.
	 * Each HU in the list must be a top-level HU.
	 */
	List<ShipmentScheduleWithHU> retrieveShipmentSchedulesWithHUsFromHUs(@NonNull List<I_M_HU> hus);
}
