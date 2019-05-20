package de.metas.vertical.creditscore.creditpass.process;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.process
 * %%
 * Copyright (C) 2018 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.process.*;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.service.CreditPassTransactionService;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CS_Creditpass_TransactionFrom_C_BPartner extends JavaProcess implements IProcessPrecondition
{
	final private CreditPassTransactionService creditPassTransactionService = Adempiere.getBean(CreditPassTransactionService.class);

	@Param(parameterName = CreditPassConstants.PROCESS_PAYMENT_RULE_PARAM)
	private String paymentRule;

	@Override protected String doIt() throws Exception
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(getProcessInfo().getRecord_ID());
		final List<TransactionResult> transactionResults = creditPassTransactionService
				.getAndSaveCreditScore(Optional.ofNullable(paymentRule).orElse(StringUtils.EMPTY), bPartnerId);
		List<Integer> tableRecordReferences = transactionResults.stream()
				.map(tr -> tr.getTransactionResultId().getRepoId())
				.collect(Collectors.toList());
		getResult().setRecordsToOpen(I_CS_Transaction_Result.Table_Name, tableRecordReferences, null);
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_BPartner partner = context.getSelectedModel(I_C_BPartner.class);
		if (!partner.isCustomer())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Business partner is not a customer");
		}

		if (!creditPassTransactionService.hasConfigForPartnerId(BPartnerId.ofRepoId(partner.getC_BPartner_ID())))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Business partner has no associated creditPass config");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
