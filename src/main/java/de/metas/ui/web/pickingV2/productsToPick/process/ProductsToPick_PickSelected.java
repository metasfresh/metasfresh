package de.metas.ui.web.pickingV2.productsToPick.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ProductsToPick_PickSelected extends ProductsToPickViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getSelectedRows().forEach(this::pick);

		return MSG_OK;
	}

	private void pick(final ProductsToPickRow row)
	{
		final PickingCandidateId pickingCandidateId = row.getPickingCandidateId();
		final HuId huId = row.getHuId();
		final ShipmentScheduleId shipmentScheduleId = row.getShipmentScheduleId();

		final Quantity qty = row.getQty();
		// TODO Auto-generated method stub

	}
}
