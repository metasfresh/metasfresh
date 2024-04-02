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
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractId;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class InterimContract_GenerateInterimInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);
	private final InterimInvoiceCandidateService interimInvoiceCandidateService = SpringContextHolder.instance.getBean(InterimInvoiceCandidateService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final List<I_C_Flatrate_Term> flatrateTermIds = streamFlatrateTermIds(context.getQueryFilter(I_C_Flatrate_Term.class));
		if (flatrateTermIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No interim contract selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = streamFlatrateTermIds(getProcessInfo().getQueryFilterOrElseFalse())
				.stream()
				.map(flatrateTermRecord -> interimInvoiceCandidateService.createInterimInvoiceCandidatesFor(flatrateTermRecord))
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

	private List<I_C_Flatrate_Term> streamFlatrateTermIds(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(filter)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.class)
				.addStringLikeFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, TypeConditions.INTERIM_INVOICE.getCode(), false)
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, I_C_Flatrate_Term.class)
				.create()
				.list();
	}

}
