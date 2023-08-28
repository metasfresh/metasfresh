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

import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.document.engine.DocStatus;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModularContractService.ModelAction.COMPLETED;

@Interceptor(I_C_Flatrate_Term.class)
@Component
public class C_Flatrate_Term
{
	private final BPartnerInterimContractService bPartnerInterimContractService;
	private final ModularContractService modularContractService;
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final static String SYS_CONFIG_INTERIM_CONTRACT_AUTO_CREATE = "de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete";

	public C_Flatrate_Term(@NonNull final BPartnerInterimContractService bPartnerInterimContractService, final ModularContractService modularContractService)
	{
		this.bPartnerInterimContractService = bPartnerInterimContractService;
		this.modularContractService = modularContractService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createInterimContractIfNeeded(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!sysConfigBL.getBooleanValue(SYS_CONFIG_INTERIM_CONTRACT_AUTO_CREATE, true))
		{
			return;
		}

		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType()
				|| !bPartnerInterimContractService.isBpartnerInterimInvoice(flatrateTermRecord))
		{
			return;
		}

		Check.assumeNotNull(flatrateTermRecord.getEndDate(), "End Date shouldn't be null");
		interimInvoiceFlatrateTermBL.create(flatrateTermRecord, flatrateTermRecord.getStartDate(), flatrateTermRecord.getEndDate());
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createInterimContractLogs(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType())
		{
			return;
		}

		modularContractService.invokeWithModel(flatrateTermRecord, COMPLETED, LogEntryContractType.INTERIM);

		Check.assumeNotNull(flatrateTermRecord.getEndDate(), "End Date shouldn't be null");
		queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_M_InOut.COLUMNNAME_MovementDate, flatrateTermRecord.getStartDate(), flatrateTermRecord.getEndDate())
				.addEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, DocStatus.Completed)
				.andCollectChildren(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermRecord.getModular_Flatrate_Term_ID())
				.create()
				.stream()
				.forEach(this::invokeHandlerForInOutLine);
	}

	private void invokeHandlerForInOutLine(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		modularContractService.invokeWithModel(inOutLineRecord, COMPLETED, LogEntryContractType.INTERIM);
	}
}
