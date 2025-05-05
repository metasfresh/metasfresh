/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.process;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceCandidateService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import java.util.Properties;
import java.util.Set;

public class InterimContract_GenerateInterimInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final InterimInvoiceCandidateService interimInvoiceCandidateService = SpringContextHolder.instance.getBean(InterimInvoiceCandidateService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!interimContractWasSelected(context.getQueryFilter(I_C_Flatrate_Term.class)))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No interim contract selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = flatrateDAO.createInterimContractQuery(getProcessInfo().getQueryFilterOrElseFalse())
				.stream()
				.map(interimInvoiceCandidateService::createInterimInvoiceCandidatesFor)
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());

		return prepareAndEnqueue(invoiceCandidateIds);
	}

	private String prepareAndEnqueue(@NonNull final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds)
	{
		final String trxName = getTrxName();
		final Properties ctx = getCtx();
		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, trxName);

		//this needs to happen manually, because the auto-creation of InvoiceEnqueueingWorkpackageProcessor happens after the Trx is committed,
		//which is waaay too late
		// so we're invoking this manually to give the handler a chance to fix the ICs before they're enqueued
		invoiceCandBL.updateInvalid()
				.setContext(ctx, trxName)
				.setOnlyInvoiceCandidateIds(InvoiceCandidateIdsSelection.ofSelectionId(invoiceCandidatesSelectionId))
				.update();

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setContext(ctx)
				.setInvoicingParams(getInvoicingParams())
				.setFailIfNothingEnqueued(true) // If no workpackages were created, display error message that no selection was made (07666)
				.prepareAndEnqueueSelection(invoiceCandidatesSelectionId);

		return enqueueResult.getSummaryTranslated(ctx);
	}

	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameterAsIParams());
	}

	private boolean interimContractWasSelected(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		return flatrateDAO.createInterimContractQuery(filter).anyMatch();
	}

}
