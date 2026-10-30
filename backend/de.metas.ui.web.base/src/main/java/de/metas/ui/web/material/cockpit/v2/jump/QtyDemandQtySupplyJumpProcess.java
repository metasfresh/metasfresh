/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.material.cockpit.v2.jump;

import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.SqlViewFactory;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * Common base for the Material Cockpit v2 "jump to ..." actions on {@code QtyDemand_QtySupply_V}.
 * <p>
 * The jump inputs (product, warehouse, org, attributesKey) are read from the CLICKED view row, never looked up by id:
 * the row is already on the view, so an id lookup would be an extra DB round trip per click and per precondition probe.
 * Only those four fields are read, because they are the only ones a v2 view row is guaranteed to expose.
 * <p>
 * Each subclass's {@link #checkPreconditionsApplicable()} runs the same query as {@link #doIt()}, as a cheap
 * first-match probe, and rejects with {@link SqlViewFactory#MSG_NO_RELATED_DOCS_FOUND} when the probe finds nothing,
 * instead of silently opening an empty target grid.
 */
public abstract class QtyDemandQtySupplyJumpProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@NonNull protected final QtyDemandQtySupplyJumpService jumpService = SpringContextHolder.instance.getBean(QtyDemandQtySupplyJumpService.class);

	@Override
	protected final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!hasRecordsToOpen(toJumpRow(getSingleSelectedRow())))
		{
			return ProcessPreconditionsResolution.reject(SqlViewFactory.MSG_NO_RELATED_DOCS_FOUND);
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		getResult().setRecordsToOpen(findRecordsToOpen(toJumpRow(getSingleSelectedRow())));
		return MSG_OK;
	}

	/** Reads ONLY the four fields a v2 view row exposes and a jump needs. */
	private static QtyDemandQtySupply toJumpRow(@NonNull final IViewRow row)
	{
		final Object attributesKey = row.getFieldNameAndJsonValues().get(I_QtyDemand_QtySupply_V.COLUMNNAME_AttributesKey);
		return QtyDemandQtySupply.builder()
				.productId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId))
				.warehouseId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoId))
				.orgId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_AD_Org_ID, OrgId::ofRepoId))
				.attributesKey(attributesKey instanceof AttributesKey ? (AttributesKey)attributesKey : AttributesKey.ofString(attributesKey != null ? attributesKey.toString() : null))
				.build();
	}

	/** True when {@link #findRecordsToOpen(QtyDemandQtySupply)} would return at least one record. Must use the SAME query. */
	protected abstract boolean hasRecordsToOpen(@NonNull QtyDemandQtySupply row);

	protected abstract List<TableRecordReference> findRecordsToOpen(@NonNull QtyDemandQtySupply row);
}
