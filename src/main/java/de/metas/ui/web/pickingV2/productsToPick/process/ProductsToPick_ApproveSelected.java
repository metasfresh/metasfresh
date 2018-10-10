package de.metas.ui.web.pickingV2.productsToPick.process;

import java.util.List;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickRow;

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

public class ProductsToPick_ApproveSelected extends ProductsToPickViewBasedProcess
{

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<ProductsToPickRow> selectedRows = getSelectedRows();
		if (selectedRows.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!selectedRows.stream().allMatch(ProductsToPickRow::isWaitingApproval))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select only rows that can be approved");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		getSelectedRows()
				.stream()
				.filter(ProductsToPickRow::isWaitingApproval)
				.forEach(this::approveRow);

		invalidateView();

		return MSG_OK;
	}

	private void approveRow(final ProductsToPickRow row)
	{
		// TODO: update/persist picking candidate

		getView().changeRow(row.getId(), ProductsToPickRow::withApprovalStatusApproved);
	}
}
