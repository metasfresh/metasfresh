package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** DAO for the DD_Order picking-reconcile flow. Methods added per-task as the BL evolves. */
@Repository
public class DDOrderPickingReconcileRepository
{
	private final IQueryBL queryBL;

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
}
