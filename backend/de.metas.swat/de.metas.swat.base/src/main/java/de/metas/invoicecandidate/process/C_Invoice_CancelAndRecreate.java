/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.process;

import de.metas.invoicecandidate.async.spi.impl.RecreateInvoiceEnqueuer;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_Invoice;

public class C_Invoice_CancelAndRecreate extends JavaProcess implements IProcessPrecondition
{
	private final RecreateInvoiceEnqueuer enqueuer = SpringContextHolder.instance.getBean(RecreateInvoiceEnqueuer.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
		final IQueryFilter<I_C_Invoice> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);

		if (userSelectionFilter == null)
		{
			return;
		}

		final int selectionCount = queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.filter(userSelectionFilter)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocStatus, X_C_Invoice.DOCSTATUS_Completed)
				.addOnlyActiveRecordsFilter()
				.create()
				.createSelection(getPinstanceId());

		if (selectionCount <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		enqueuer.enqueueSelection(getProcessInfo().getPinstanceId());

		return MSG_OK;
	}
}