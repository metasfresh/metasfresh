package de.metas.ui.web.bankstatement_reconciliation.process;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.bankstatement_reconciliation.BankStatementLineRow;
import de.metas.ui.web.bankstatement_reconciliation.BankStatementReconciliationView;
import de.metas.ui.web.bankstatement_reconciliation.PaymentToReconcileRow;
import de.metas.ui.web.bankstatement_reconciliation.PaymentsToReconcileView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsSelection;

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

abstract class PaymentsToReconcileViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	@Override
	protected final PaymentsToReconcileView getView()
	{
		return PaymentsToReconcileView.cast(super.getView());
	}

	@Override
	protected final PaymentToReconcileRow getSingleSelectedRow()
	{
		return PaymentToReconcileRow.cast(super.getSingleSelectedRow());
	}

	@Override
	protected final Stream<PaymentToReconcileRow> streamSelectedRows()
	{
		return super.streamSelectedRows()
				.map(PaymentToReconcileRow::cast);
	}

	protected final List<PaymentToReconcileRow> getSelectedPaymentToReconcileRows()
	{
		return streamSelectedRows()
				.collect(ImmutableList.toImmutableList());
	}

	protected ViewId getBanksStatementReconciliationViewId()
	{
		return getView().getBankStatementViewId();
	}

	protected final BankStatementReconciliationView getBanksStatementReconciliationView()
	{
		final ViewId bankStatementViewId = getBanksStatementReconciliationViewId();

		final IViewsRepository viewsRepo = getViewsRepo();
		return BankStatementReconciliationView.cast(viewsRepo.getView(bankStatementViewId));
	}

	protected final void invalidateBankStatementReconciliationView()
	{
		invalidateView(getBanksStatementReconciliationViewId());
	}

	protected final BankStatementLineRow getSingleSelectedBankStatementRowOrNull()
	{
		final ViewRowIdsSelection selection = getParentViewRowIdsSelection();
		if (selection == null || selection.isEmpty())
		{
			return null;
		}

		final ImmutableList<BankStatementLineRow> rows = getBanksStatementReconciliationView()
				.streamByIds(selection)
				.collect(ImmutableList.toImmutableList());

		return rows.size() == 1 ? rows.get(0) : null;
	}
}
