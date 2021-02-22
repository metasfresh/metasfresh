package de.metas.banking.process;

import java.util.Set;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.payment.PaymentId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class C_BankStatement_ReconcileWithPaySelection extends BankStatementBasedProcess
{
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	@Param(parameterName = "C_PaySelection_ID", mandatory = true)
	private PaySelectionId paySelectionId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return checkBankStatementIsDraftOrInProcessOrCompleted(context)
				.and(() -> checkSingleLineSelectedWhichIsNotReconciled(context));
	}

	@Override
	protected String doIt()
	{
		final Set<PaymentId> paymentIds = paySelectionBL.getPaymentIds(paySelectionId);
		openBankStatementReconciliationView(paymentIds);
		return MSG_OK;
	}
}
