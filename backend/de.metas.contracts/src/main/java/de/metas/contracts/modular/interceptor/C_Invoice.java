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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.log.LogsRecomputationService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REVERSED;

@Component
@RequiredArgsConstructor
@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@NonNull private final ModularContractService contractService;
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry;
	@NonNull private final LogsRecomputationService logsRecomputationService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_C_Invoice invoiceRecord)
	{
		invokeHandlerForEachLine(invoiceRecord, COMPLETED);
		contractService.updateIsFinalInvoiced(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()), true);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_VOID })
	public void afterReverse(@NonNull final I_C_Invoice invoiceRecord)
	{
		contractService.afterInvoiceReverse(invoiceRecord, logHandlerRegistry);
		invokeHandlerForEachLine(invoiceRecord, REVERSED);

		if(invoiceBL.isFinalInvoiceOrFinalCreditMemo(invoiceRecord) && !invoiceRecord.isSOTrx())
		{
			final FlatrateTermId flatrateTermId = contractService.getFlatrateTermIdByInvoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));
			logsRecomputationService.recomputeAllFinalInvoiceRelatedLogsLinkedTo(flatrateTermId);
		}
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final ModelAction modelAction)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		invoiceBL.getLines(invoiceId).forEach(line -> contractService.scheduleLogCreation(
				DocStatusChangedEvent.builder()
						.tableRecordReference(TableRecordReference.of(line))
						.modelAction(modelAction)
						.userInChargeId(Env.getLoggedUserId())
						.build()));
	}
}
