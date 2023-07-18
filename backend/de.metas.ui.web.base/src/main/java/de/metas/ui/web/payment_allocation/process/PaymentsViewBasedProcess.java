package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.InvoicesView;
import de.metas.ui.web.payment_allocation.InvoicesViewFactory;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.payment_allocation.PaymentsView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

abstract class PaymentsViewBasedProcess extends ViewBasedProcessTemplate
{
	private transient ImmutableList<PaymentRow> _paymentRowsSelectedForAllocation;
	private transient ImmutableList<InvoiceRow> _invoiceRowsSelectedForAllocation;

	@Override
	protected final PaymentsView getView()
	{
		return PaymentsView.cast(super.getView());
	}

	protected final PaymentsView getPaymentsView()
	{
		return getView();
	}

	protected final DocumentIdsSelection getSelectedPaymentRowIdsIncludingDefaultRow()
	{
		return getSelectedRowIds();
	}

	protected final InvoicesView getInvoicesView()
	{
		return getPaymentsView().getInvoicesView();
	}

	private DocumentIdsSelection getSelectedInvoiceRowIds()
	{
		return getChildViewSelectedRowIds();
	}

	//
	//
	//

	protected final ImmutableList<PaymentRow> getPaymentRowsSelectedForAllocation()
	{
		ImmutableList<PaymentRow> paymentRowsSelectedForAllocation = this._paymentRowsSelectedForAllocation;
		if (paymentRowsSelectedForAllocation == null)
		{
			paymentRowsSelectedForAllocation = this._paymentRowsSelectedForAllocation = computePaymentRowsSelectedForAllocation();
		}
		return paymentRowsSelectedForAllocation;
	}

	private ImmutableList<PaymentRow> computePaymentRowsSelectedForAllocation()
	{
		final DocumentIdsSelection selectedPaymentRowIds = getSelectedPaymentRowIdsIncludingDefaultRow();

		return getPaymentsView().streamByIds(selectedPaymentRowIds)
				.filter(row -> !row.equals(PaymentRow.DEFAULT_PAYMENT_ROW))
				.collect(ImmutableList.toImmutableList());
	}

	protected final ImmutableList<InvoiceRow> getInvoiceRowsSelectedForAllocation()
	{
		ImmutableList<InvoiceRow> invoiceRowsSelectedForAllocation = this._invoiceRowsSelectedForAllocation;
		if (invoiceRowsSelectedForAllocation == null)
		{
			invoiceRowsSelectedForAllocation = this._invoiceRowsSelectedForAllocation = computeInvoiceRowsSelectedForAllocation();
		}
		return invoiceRowsSelectedForAllocation;
	}

	private ImmutableList<InvoiceRow> computeInvoiceRowsSelectedForAllocation()
	{
		final InvoicesView invoicesView = getInvoicesView();
		if (InvoicesViewFactory.isEnablePreparedForAllocationFlag())
		{
			return invoicesView
					.streamByIds(DocumentIdsSelection.ALL)
					.filter(InvoiceRow::isPreparedForAllocation)
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return invoicesView
					.streamByIds(getSelectedInvoiceRowIds())
					.collect(ImmutableList.toImmutableList());
		}
	}

	protected final void invalidatePaymentsAndInvoicesViews()
	{
		final InvoicesView invoicesView = getInvoicesView();

		invoicesView.unmarkPreparedForAllocation(DocumentIdsSelection.ALL);

		invalidateView(invoicesView);
		invalidateView(getPaymentsView());
	}
}
