package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

/** DAO for the DD_Order picking-reconcile flow. Methods added per-task as the BL evolves. */
@Repository
public class DDOrderPickingReconcileRepository
{
	private final IQueryBL queryBL;
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public DDOrderPickingReconcileRepository(@NonNull final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	/**
	 * Returns a stream of shipment schedule IDs that are active, on a packing warehouse
	 * ({@code M_Warehouse.IsAutoDistributionOrder='Y'}), and have NO live (non-voided) DD_Order linked.
	 *
	 * <p>These are the "drifted" schedules that need to be re-reconciled by the watchdog scan.</p>
	 */
	public Stream<ShipmentScheduleId> streamSchedulesNeedingDDOrder()
	{
		// Sub-query: packing warehouse IDs
		final IQuery<I_M_Warehouse> autoDistributionOrderSubQuery = queryBL
				.createQueryBuilder(I_M_Warehouse.class)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsAutoDistributionOrder, true)
				.addOnlyActiveRecordsFilter()
				.create();

		// Sub-query: live (non-voided) DD_Orders
		final IQuery<I_DD_Order> liveDDOrderSubQuery = queryBL
				.createQueryBuilder(I_DD_Order.class)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.addOnlyActiveRecordsFilter()
				.create();

		// Main query: active schedules on a packing warehouse with no live DD_Order.
		//
		// NOTE on "active": this definition (IsActive='Y', no Processed/IsClosed filter) intentionally
		// matches DDOrderPickingReconcileService#classifyAction — the watchdog only republishes; the BL
		// re-decides per schedule. If one changes, change both.
		final IQueryBuilder<I_M_ShipmentSchedule> scheduleQueryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter();

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
				.addInSubQueryFilter(
						I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID,
						I_M_Warehouse.COLUMNNAME_M_Warehouse_ID,
						autoDistributionOrderSubQuery);

		// branch 2: Override not set → base warehouse decides
		effectivePackingFilter.addCompositeQueryFilter()
				.setJoinAnd()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, null)
				.addInSubQueryFilter(
						I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID,
						I_M_Warehouse.COLUMNNAME_M_Warehouse_ID,
						autoDistributionOrderSubQuery);

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
	 * Returns {@code true} iff at least one {@link I_M_Picking_Job_Line} row shares the same
	 * {@code M_ShipmentSchedule_ID} as the given DD_Order — i.e. a picker is actively working
	 * on the shipment-schedule this DD_Order was created for.
	 */
	public boolean existsPickingJobLineForDDOrder(@NonNull final DDOrderId ddOrderId)
	{
		final IQuery<I_DD_Order> ddOrderSubQuery = queryBL
				.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderId)
				.create();

		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addInSubQueryFilter(
						I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID,
						I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID,
						ddOrderSubQuery)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	/**
	 * Voids the given DD_Order via the document engine (DocStatus → Voided).
	 */
	public void voidDDOrder(@NonNull final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = InterfaceWrapperHelper.load(ddOrderId.getRepoId(), I_DD_Order.class);
		documentBL.processEx(ddOrder, IDocument.ACTION_Void, IDocument.STATUS_Voided);
	}

	/**
	 * Builds exactly one {@link I_DD_Order} (with a single {@link I_DD_OrderLine}) moving the requested
	 * product/qty from the source warehouse's default locator to the target (packing) warehouse's default
	 * locator, links both header and line to the shipment schedule via {@code M_ShipmentSchedule_ID},
	 * and completes the document via {@link IDocumentBL}.
	 *
	 * <p>Note on intentionally-omitted fields: {@code C_BPartner_Location_ID} and {@code PP_Plant_ID} are NOT set.
	 * This is an internal pick-to-packing move, so neither the partner-location nor the manufacturing-plant context
	 * applies. (If a dt204 packing warehouse ever turns out to have a PP_Plant that MRP needs, resolve it via
	 * {@code warehouseBL.getPlantId(targetWarehouseId)} — not expected.)</p>
	 *
	 * @return the ID of the newly created, completed DD_Order
	 */
	public DDOrderId createCompletedDDOrder(@NonNull final CreateDDOrderRequest request)
	{
		final OrgId orgId = request.getOrgId();
		final LocatorId locatorFromId = warehouseBL.getOrCreateDefaultLocatorId(request.getSourceWarehouseId());
		final LocatorId locatorToId = warehouseBL.getOrCreateDefaultLocatorId(request.getTargetWarehouseId());

		// Mirror HUs2DDOrderProducer: the DD_Order header warehouse is the IN-TRANSIT warehouse;
		// the source/target warehouses live on the line's locators (M_Warehouse_From/To on the header).
		final WarehouseId inTransitWarehouseId = warehouseBL.getInTransitWarehouseId(orgId);

		// Mirror HUs2DDOrderProducer: resolve the Distribution Order document type — required by completeIt.
		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(Env.getAD_Client_ID())
						.adOrgId(orgId.getRepoId())
						.build());
		// Fail with a clear config-time error rather than letting a -1 doc-type surface during completeIt.
		Check.assumeNotNull(docTypeId, "Distribution Order doc-type must exist for orgId={}", orgId);

		//
		// Header
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		if (request.getBpartnerId() != null)
		{
			ddOrder.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		}
		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(docTypeId));
		ddOrder.setM_Warehouse_ID(inTransitWarehouseId.getRepoId());
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
		ddOrderLine.setM_Locator_ID(locatorFromId.getRepoId());
		ddOrderLine.setM_LocatorTo_ID(locatorToId.getRepoId());
		ddOrderLine.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrderLine.setIsInvoiced(false);
		InterfaceWrapperHelper.save(ddOrderLine);

		//
		// Complete via the document engine (DocStatus -> Completed)
		documentBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
	}
}
