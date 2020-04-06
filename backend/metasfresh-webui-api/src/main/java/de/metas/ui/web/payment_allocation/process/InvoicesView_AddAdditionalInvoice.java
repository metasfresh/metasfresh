package de.metas.ui.web.payment_allocation.process;

import de.metas.invoice.InvoiceId;
import de.metas.process.Param;
import de.metas.ui.web.payment_allocation.InvoicesView;

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

public class InvoicesView_AddAdditionalInvoice extends InvoicesViewBasedProcess
{
	@Param(parameterName = "C_Invoice_ID", mandatory = true)
	private InvoiceId invoiceId;

	@Override
	protected String doIt()
	{
		final InvoicesView view = getView();
		view.addInvoice(invoiceId);
		invalidateView(view);
		return MSG_OK;
	}
}
