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

package de.metas.contracts.modular.interim.logImpl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

@Component
public class PurchaseInvoiceLineInterimHandler implements IModularContractTypeHandler<I_C_InvoiceLine>
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final ModularContractLogService contractLogService;

	public PurchaseInvoiceLineInterimHandler(@NonNull final ModularContractLogService contractLogService)
	{
		this.contractLogService = contractLogService;
	}

	@Override
	public @NonNull Class<I_C_InvoiceLine> getType()
	{
		return I_C_InvoiceLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_InvoiceLine invoiceLineRecord)
	{
		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLineRecord.getC_Invoice_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceRecord.isSOTrx());

		return soTrx.isPurchase() && invoiceBL.isDownPayment(invoiceRecord);
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isInterimContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_InvoiceLine invoiceLineRecord)
	{
		if (invoiceLineRecord.getC_Flatrate_Term_ID() > 0)
		{
			return Stream.of(FlatrateTermId.ofRepoId(invoiceLineRecord.getC_Flatrate_Term_ID()));
		}
		else
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);
			if (invoiceCandidates.size() != 1)
			{
				return Stream.empty();
			}

			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandidates.get(0);
			final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
			if (invoiceCandidateRecord.getAD_Table_ID() != flatrateTermTableId)
			{
				return Stream.empty();
			}

			return Stream.of(FlatrateTermId.ofRepoId(invoiceCandidateRecord.getRecord_ID()));
		}
	}

	@Override
	public void validateAction(final @NonNull I_C_InvoiceLine invoiceLineRecord, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED ->
			{
			}
			case RECREATE_LOGS -> contractLogService.throwErrorIfProcessedLogsExistForRecord(TableRecordReference.of(invoiceLineRecord),
																							 MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
