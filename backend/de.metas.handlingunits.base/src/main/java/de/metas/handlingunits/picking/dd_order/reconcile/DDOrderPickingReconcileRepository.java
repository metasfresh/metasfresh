package de.metas.handlingunits.picking.dd_order.reconcile;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;
import lombok.NonNull;

/** DAO for the DD_Order picking-reconcile flow. Methods added per-task as the BL evolves. */
@Repository
public class DDOrderPickingReconcileRepository
{
	private final IQueryBL queryBL;

	public DDOrderPickingReconcileRepository(@NonNull final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}
}
