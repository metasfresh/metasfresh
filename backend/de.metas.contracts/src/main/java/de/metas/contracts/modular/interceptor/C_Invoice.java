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
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.ModularContractSettingsId;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettingsService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Check;
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

import static de.metas.contracts.modular.ComputingMethodType.AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS;
import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REVERSED;

@Component
@RequiredArgsConstructor
@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull private final ModularContractService contractService;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModularContractSettingsService modularContractSettingsService;
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_C_Invoice invoiceRecord)
	{
		invokeHandlerForEachLine(invoiceRecord, COMPLETED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_VOID })
	public void afterReverse(@NonNull final I_C_Invoice invoiceRecord)
	{
		invokeHandlerForEachLine(invoiceRecord, REVERSED);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		modularContractLogService.unprocessModularContractLogs(invoiceId, DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoIdOrNull(invoiceRecord.getModCntr_Settings_ID());
		if(modularContractSettingsId == null)
		{
			return;
		}
		final FlatrateTermId flatrateTermId = Check.assumePresent(flatrateBL.getIdByInvoiceId(invoiceId), "FlatrateTermId should be present");

		modularContractSettingsService.getById(modularContractSettingsId).getModuleConfigs().stream()
				.filter(config -> config.isMatchingAnyOf(AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS))
				.forEach(config -> modularContractLogService.updateAverageContractSpecificPrice(config, flatrateTermId, logHandlerRegistry));
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
