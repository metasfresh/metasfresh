/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.process;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceVerificationSetId;
import de.metas.invoice.service.IInvoiceVerificationBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Verification_Set;

import java.util.List;

public class C_Invoice_AddTo_VerificationSet extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceVerificationBL invoiceVerificationDAO = Services.get(IInvoiceVerificationBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = I_C_Invoice_Verification_Set.COLUMNNAME_C_Invoice_Verification_Set_ID, mandatory = true)
	private int verificationSetId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return context.isNoSelection() ?
				ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal() :
				ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InvoiceVerificationSetId invoiceVerificationSetId = InvoiceVerificationSetId.ofRepoId(verificationSetId);
		invoiceVerificationDAO.createVerificationSetLines(invoiceVerificationSetId, getSelectedInvoiceIds());
		return MSG_OK;
	}

	protected final List<InvoiceId> getSelectedInvoiceIds()
	{
		final IQueryFilter<I_C_Invoice> queryFilter = getProcessInfo().getQueryFilterOrElseFalse();
		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.filter(queryFilter)
				.create()
				.stream()
				.map(I_C_Invoice::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}
}
