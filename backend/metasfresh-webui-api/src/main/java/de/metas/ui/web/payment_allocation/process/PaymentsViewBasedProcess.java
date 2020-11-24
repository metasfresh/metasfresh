package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.InvoicesView;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.payment_allocation.PaymentsView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

import java.util.List;

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

	protected final PaymentRow getSingleSelectedPaymentRowOrNull()
	{
		final DocumentIdsSelection selectedPaymentRowIds = getSelectedPaymentRowIdsIncludingDefaultRow();
		if (selectedPaymentRowIds.isSingleDocumentId()
				&& selectedPaymentRowIds.getSingleDocumentId().toInt() != PaymentRow.DEFAULT_PAYMENT_ROW.getPaymentId().getRepoId()
		)
		{
			final DocumentId rowId = selectedPaymentRowIds.getSingleDocumentId();
			return getPaymentsView().getById(rowId);
		}
		else
		{
			return null;
		}
	}

	protected final List<InvoiceRow> getSelectedInvoiceRows()
	{
		final DocumentIdsSelection invoiceRowIds = getSelectedInvoiceRowIds();

		return getInvoicesView()
				.streamByIds(invoiceRowIds)
				.collect(ImmutableList.toImmutableList());
	}

	protected final void invalidatePaymentsAndInvoicesViews()
	{
		invalidateView(getInvoicesView());
		invalidateView(getPaymentsView());
	}
}
