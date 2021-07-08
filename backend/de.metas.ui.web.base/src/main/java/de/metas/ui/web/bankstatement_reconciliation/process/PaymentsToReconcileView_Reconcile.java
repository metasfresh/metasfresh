package de.metas.ui.web.bankstatement_reconciliation.process;

import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.bankstatement_reconciliation.actions.ReconcilePaymentsCommand;
import de.metas.ui.web.bankstatement_reconciliation.actions.ReconcilePaymentsRequest;
import de.metas.util.Services;

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

public class PaymentsToReconcileView_Reconcile extends PaymentsToReconcileViewBasedProcess
{
	private final IBankStatementPaymentBL bankStatmentPaymentBL = Services.get(IBankStatementPaymentBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return reconcilePayments().checkCanExecute();
	}

	@Override
	protected String doIt()
	{
		reconcilePayments().execute();

		// NOTE: usually this would not be needed but it seems frontend has some problems to refresh the left side view
		invalidateBankStatementReconciliationView();

		return MSG_OK;
	}

	private ReconcilePaymentsCommand reconcilePayments()
	{
		return ReconcilePaymentsCommand.builder()
				.msgBL(msgBL)
				.bankStatmentPaymentBL(bankStatmentPaymentBL)
				//
				.request(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(getSingleSelectedBankStatementRowOrNull())
						.selectedPaymentsToReconcile(getSelectedPaymentToReconcileRows())
						.build())
				//
				.build();
	}
}
