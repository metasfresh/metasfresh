package de.metas.handlingunits.ddorder.replenishment;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/** DAO for the DD_Order picking-reconcile flow. Methods added per-task as the BL evolves. */
@Repository
public class DDOrderPickingReplenishmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Returns a stream of shipment schedule IDs that are active, on a packing warehouse
	 * (one of the given {@code autoDistributionWarehouseIds}), and have NO live (non-voided) DD_Order linked.
	 *
	 * <p>These are the "drifted" schedules that need to be re-reconciled by the watchdog scan.</p>
	 *
	 * <p>The caller is responsible for resolving the set of auto-distribution warehouse IDs before calling this method.</p>
	 *
	 * <p>Note: this repository reads {@link I_DD_Order} directly (a foreign table owned by {@code de.metas.manufacturing}).
	 * {@code DDOrderLowLevelDAO} lives in {@code de.metas.manufacturing}, which is not a dependency of
	 * {@code de.metas.handlingunits.base}; keeping the read-only demand query here avoids an unwanted cross-module dep.</p>
	 */
	public Stream<ShipmentScheduleId> streamSchedulesNeedingDDOrder(@NonNull final Set<WarehouseId> autoDistributionWarehouseIds)
	{
		if (autoDistributionWarehouseIds.isEmpty())
		{
			return Stream.empty();
		}

		final Set<Integer> warehouseRepoIds = WarehouseId.toRepoIds(autoDistributionWarehouseIds);

		// Sub-query: live (non-voided) DD_Orders
		final IQuery<I_DD_Order> liveDDOrderSubQuery = queryBL
				.createQueryBuilder(I_DD_Order.class)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.addOnlyActiveRecordsFilter()
				.create();

		// Main query: active + not processed + not closed schedules on a packing warehouse with no live DD_Order.
		//
		// NOTE: active + not processed + not closed — matches DDOrderPickingReplenishmentService#classifyAction.
		// If one changes, change both.
		final IQueryBuilder<I_M_ShipmentSchedule> scheduleQueryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_IsClosed, false);

		// Schedule must be on a packing EFFECTIVE warehouse, mirroring IShipmentScheduleEffectiveBL#getWarehouseId
		// (Override-takes-priority) used by the BL. A plain OR over base/Override would wrongly include a schedule
		// whose base warehouse is packing but whose Override points to a non-packing warehouse, generating a spurious
		// watchdog event that the BL then no-ops via classifyAction=NONE.
		//
		//   (M_Warehouse_Override_ID IS NOT NULL AND M_Warehouse_Override_ID IN packing)
		//   OR
		//   (M_Warehouse_Override_ID IS NULL     AND M_Warehouse_ID          IN packing)
		final ICompositeQueryFilter<I_M_ShipmentSchedule> effectivePackingFilter = scheduleQueryBuilder
				.addCompositeQueryFilter()
				.setJoinOr();

		// branch 1: Override set and pointing at a packing warehouse
		effectivePackingFilter.addCompositeQueryFilter()
				.setJoinAnd()
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, null)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, warehouseRepoIds);

		// branch 2: Override not set → base warehouse decides
		effectivePackingFilter.addCompositeQueryFilter()
				.setJoinAnd()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, null)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID, warehouseRepoIds);

		// schedule must have NO live DD_Order
		scheduleQueryBuilder.addNotInSubQueryFilter(
				I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
				I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID,
				liveDDOrderSubQuery);

		return scheduleQueryBuilder
				.create()
				.stream()
				.map(schedule -> ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()));
	}

	/**
	 * Returns the ID of the first active (non-voided) DD_Order linked to the given shipment schedule,
	 * or empty if none exists.
	 */
	public Optional<DDOrderId> findActiveDDOrderForSchedule(@NonNull final ShipmentScheduleId scheduleId)
	{
		return queryBL
				.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.create()
				.firstOptional(I_DD_Order.class)
				.map(ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	/**
	 * Returns {@code true} iff at least one active {@link I_M_Picking_Job_Line} row references
	 * the given shipment schedule — i.e. a picker is actively working on it.
	 *
	 * <p>Note: this repository reads {@link I_M_Picking_Job_Line} directly (a foreign table from the picking domain).
	 * The picking DAO lives in {@code de.metas.handlingunits.base} but {@code DDOrderPickingReplenishmentRepository}
	 * owns this read-only demand query to avoid scattering picker-busy knowledge across modules.</p>
	 */
	public boolean existsPickingJobLineForSchedule(@NonNull final ShipmentScheduleId scheduleId)
	{
		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addEqualsFilter(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	/**
	 * Builds exactly one {@link I_DD_Order} (with a single {@link I_DD_OrderLine}) for the picking-reconcile flow,
	 * saves both records to the database, and returns the saved (Drafted) {@link I_DD_Order}.
	 *
	 * <p>This method is pure data-access: it only persists the records. All non-DAO work — resolving locators,
	 * the in-transit warehouse, the doc-type, and completing the document via {@link de.metas.document.engine.IDocumentBL}
	 * — is performed by the caller ({@link DDOrderPickingReplenishmentService}).
	 *
	 * <p>Note on intentionally-omitted fields: {@code C_BPartner_Location_ID} and {@code PP_Plant_ID} are NOT set.
	 * This is an internal pick-to-packing move, so neither the partner-location nor the manufacturing-plant context
	 * applies. (If a dt204 packing warehouse ever turns out to have a PP_Plant that MRP needs, resolve it via
	 * {@code warehouseBL.getPlantId(targetWarehouseId)} — not expected.)</p>
	 *
	 * @return the saved (Drafted) {@link I_DD_Order} — the caller is responsible for completing it
	 */
	public I_DD_Order saveDraftDDOrder(@NonNull final CreateDDOrderReplenishmentRequest request)
	{
		final OrgId orgId = request.getOrgId();

		//
		// Header
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		if (request.getBpartnerId() != null)
		{
			ddOrder.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		}
		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(request.getDocTypeId()));
		ddOrder.setM_Warehouse_ID(request.getInTransitWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_From_ID(request.getSourceWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_To_ID(request.getTargetWarehouseId().getRepoId());
		ddOrder.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrder.setDateOrdered(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setIsSOTrx(false);
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		InterfaceWrapperHelper.save(ddOrder);

		//
		// Line
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setAD_Org_ID(orgId.getRepoId());
		ddOrderLine.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderLine.setDatePromised(ddOrder.getDatePromised());
		ddOrderLine.setM_Product_ID(request.getProductId().getRepoId());
		ddOrderLine.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		// This flow operates entirely in the product's stock UOM (internal pick-to-packing move):
		// QtyToDeliver is always stock UOM and source UOM == stock UOM, so QtyEntered == QtyOrdered == TargetQty
		// intentionally. (HUs2DDOrderProducer distinguishes QtyEntered=sourceUOM vs QtyOrdered/TargetQty=stockUOM;
		// revisit only if a real source-UOM != stock-UOM case arises here.)
		ddOrderLine.setQtyEntered(request.getQty().toBigDecimal());
		ddOrderLine.setQtyOrdered(request.getQty().toBigDecimal());
		ddOrderLine.setTargetQty(request.getQty().toBigDecimal());
		ddOrderLine.setM_Locator_ID(request.getLocatorFromId().getRepoId());
		ddOrderLine.setM_LocatorTo_ID(request.getLocatorToId().getRepoId());
		ddOrderLine.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrderLine.setIsInvoiced(false);
		InterfaceWrapperHelper.save(ddOrderLine);

		return ddOrder;
	}
}
