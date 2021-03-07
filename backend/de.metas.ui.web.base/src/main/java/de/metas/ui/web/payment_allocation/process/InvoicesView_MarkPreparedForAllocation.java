/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.payment_allocation.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.InvoicesViewFactory;

public class InvoicesView_MarkPreparedForAllocation extends InvoicesViewBasedProcess implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!InvoicesViewFactory.isEnablePreparedForAllocationFlag())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Feature not enabled");
		}

		if (!hasEligibleRows())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No eligible rows selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getView().markPreparedForAllocation(getSelectedRowIds());
		return MSG_OK;
	}

	public boolean hasEligibleRows()
	{
		return getView()
				.streamByIds(getSelectedRowIds())
				.anyMatch(invoiceRow -> !invoiceRow.isPreparedForAllocation());
	}
}
