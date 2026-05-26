package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Repository;

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
	 * Returns {@code true} iff at least one {@link I_M_Picking_Job_Line} row shares the same
	 * {@code M_ShipmentSchedule_ID} as the given DD_Order — i.e. a picker is actively working
	 * on the shipment-schedule this DD_Order was created for.
	 */
	public boolean existsPickingJobLineForDDOrder(@NonNull final DDOrderId ddOrderId)
	{
		final org.compiere.model.IQuery<I_DD_Order> ddOrderSubQuery = queryBL
				.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderId.getRepoId())
				.create();

		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addInSubQueryFilter(
						I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID,
						I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID,
						ddOrderSubQuery)
				.create()
				.anyMatch();
	}
}
