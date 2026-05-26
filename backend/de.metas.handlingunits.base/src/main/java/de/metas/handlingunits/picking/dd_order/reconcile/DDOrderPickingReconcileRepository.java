package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/** DAO for the DD_Order picking-reconcile flow. Methods added per-task as the BL evolves. */
@Repository
public class DDOrderPickingReconcileRepository
{
	private final IQueryBL queryBL;
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	public DDOrderPickingReconcileRepository(@NonNull final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
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
		final org.compiere.model.IQuery<I_DD_Order> ddOrderSubQuery = queryBL
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
	 * Builds exactly one {@link I_DD_Order} (with a single {@link I_DD_OrderLine}) moving the requested
	 * product/qty from the source warehouse's default locator to the target (packing) warehouse's default
	 * locator, links both header and line to the shipment schedule via {@code M_ShipmentSchedule_ID},
	 * and completes the document via {@link IDocumentBL}.
	 *
	 * @return the ID of the newly created, completed DD_Order
	 */
	public DDOrderId createCompletedDDOrder(@NonNull final CreateDDOrderRequest request)
	{
		final LocatorId locatorFromId = warehouseBL.getOrCreateDefaultLocatorId(request.getSourceWarehouseId());
		final LocatorId locatorToId = warehouseBL.getOrCreateDefaultLocatorId(request.getTargetWarehouseId());
		final Instant now = de.metas.common.util.time.SystemTime.asInstant();

		//
		// Header
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(request.getOrgId().getRepoId());
		if (request.getBpartnerId() != null)
		{
			ddOrder.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		}
		ddOrder.setM_Warehouse_From_ID(request.getSourceWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_To_ID(request.getTargetWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_ID(request.getTargetWarehouseId().getRepoId());
		ddOrder.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrder.setDateOrdered(TimeUtil.asTimestamp(now));
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setIsSOTrx(false);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		InterfaceWrapperHelper.save(ddOrder);

		//
		// Line
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setAD_Org_ID(request.getOrgId().getRepoId());
		ddOrderLine.setM_Product_ID(request.getProductId().getRepoId());
		ddOrderLine.setQtyEntered(request.getQty());
		ddOrderLine.setQtyOrdered(request.getQty());
		ddOrderLine.setTargetQty(request.getQty());
		ddOrderLine.setM_Locator_ID(locatorFromId.getRepoId());
		ddOrderLine.setM_LocatorTo_ID(locatorToId.getRepoId());
		ddOrderLine.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		InterfaceWrapperHelper.save(ddOrderLine);

		//
		// Complete via the document engine (DocStatus -> Completed)
		documentBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
	}
}
