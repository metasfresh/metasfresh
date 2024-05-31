/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.definitive.invoicecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.finalinvoice.invoicecandidate.FlatrateTermModular_FinalHandler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModCntrInvoiceType;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.NotNull;

import static de.metas.contracts.modular.ModCntrInvoiceType.Definitive;
import static de.metas.contracts.modular.ModCntrInvoiceType.Final;
import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES_AND_INVOICES;
import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.DONT;

public class FlatrateTermModular_DefinitiveHandler extends FlatrateTermModular_FinalHandler
{
	private final ModularContractLogDAO modularContractLogDAO = SpringContextHolder.instance.getBean(ModularContractLogDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ModularContractLogService modularContractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);

	@Override
	@NonNull
	public IInvoiceCandidateHandler.CandidatesAutoCreateMode isMissingInvoiceCandidate(@NotNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (!flatrateTerm.isReadyForDefinitiveInvoice())
		{
			return DONT;
		}
		final boolean finalInvoiceBillableLogsExist = modularContractLogDAO.anyMatch(ModularContractLogQuery.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.computingMethodTypes(Final.getComputingMethodTypes())
				.processed(false)
				.billable(true)
				.build());
		if (finalInvoiceBillableLogsExist)
		{
			return DONT;
		}

		final boolean definitiveInvoiceBillableLogsExist = modularContractLogDAO.anyMatch(ModularContractLogQuery.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.computingMethodTypes(getModCntrInvoiceType().getComputingMethodTypes())
				.processed(false)
				.billable(true)
				.isComputingMethodTypeActive(false)
				.build());

		return definitiveInvoiceBillableLogsExist ? CREATE_CANDIDATES_AND_INVOICES
				: DONT;
	}

	@Override
	protected void processModCntrLogs(final ComputingResponse computingResponse, final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (!computingResponse.getIds().isEmpty())
		{
			trxManager.runAfterCommit(() -> modularContractLogService.setDefinitiveICLogsProcessed(ModularContractLogQuery.ofEntryIds(computingResponse.getIds()),
					InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID())));
		}
	}

	@Override
	@NonNull
	public ImmutableList<Object> getRecordsToLock(@NonNull final I_C_Flatrate_Term term)
	{
		return ImmutableList.builder()
				.add(term)
				.addAll(modularContractLogDAO.list(ModularContractLogQuery.builder()
						.flatrateTermId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()))
						.computingMethodTypes(getModCntrInvoiceType().getComputingMethodTypes())
						.isComputingMethodTypeActive(false)
						.processed(false)
						.billable(true)
						.build()))
				.build();
	}

	@Override
	protected @NonNull ModCntrInvoiceType getModCntrInvoiceType()
	{
		return Definitive;
	}

	@Override
	public boolean isHandlerFor(@NonNull final I_C_Flatrate_Term term)
	{
		return term.isReadyForDefinitiveInvoice();
	}
}
