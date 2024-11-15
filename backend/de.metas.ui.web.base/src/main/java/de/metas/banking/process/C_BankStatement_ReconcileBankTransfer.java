/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.process;

import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.service.ReconcileAsBankTransferRequest;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

public class C_BankStatement_ReconcileBankTransfer extends BankStatementBasedProcess
{
	@Param(parameterName = "Counterpart_BankStatement_ID", mandatory = true)
	private BankStatementId counterpartBankStatementId;

	@Param(parameterName = "Counterpart_BankStatementLine_ID", mandatory = true)
	private BankStatementLineId counterpartBankStatementLineId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return checkBankStatementIsDraftOrInProcessOrCompleted(context)
				.and(() -> checkSingleLineSelectedWhichIsNotReconciled(context));
	}

	@Override
	protected String doIt()
	{
		bankStatementBL.reconcileAsBankTransfer(
				ReconcileAsBankTransferRequest.builder()
						.bankStatementId(getSelectedBankStatementId())
						.bankStatementLineId(getSingleSelectedBankStatementLineId())
						.counterpartBankStatementId(counterpartBankStatementId)
						.counterpartBankStatementLineId(counterpartBankStatementLineId)
						.build());

		return MSG_OK;
	}
}
