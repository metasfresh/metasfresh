package de.metas.handlingunits.shipmentschedule.api;

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
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.LocalDate;
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
	 * Add QtyPicked to current QtyPicked of given shipment schedule.
	 * <p>
	 * Also update the given <code>hu</code>'s (and therefore its childrens') <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> to the given <code>sched</code>'s effective values.<br>
	 * And finally update the given {@code tuOrVHU}'s status to "Picked".
	 *
	 * @param tuOrVHU                   TU or VirtualHU to link on
	 * @param anonymousHuPickedOnTheFly true if the HU was picked on the fly for the shipment process
	 * @return qtyPicked record for this addition
	 */
	ShipmentScheduleWithHU addQtyPickedAndUpdateHU(I_M_ShipmentSchedule sched, StockQtyAndUOMQty qtyPicked, I_M_HU tuOrVHU, IHUContext huContext, final boolean anonymousHuPickedOnTheFly);

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

	I_M_ShipmentSchedule getShipmentScheduleOrNull(I_M_HU hu);

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

	void deleteByTopLevelHUAndShipmentScheduleId(
			@NonNull HuId topLevelHUId,
			@NonNull ShipmentScheduleId shipmentScheduleId);
}
