package de.metas.ui.web.payment_allocation.process;

import de.metas.payment.PaymentId;
import de.metas.process.Param;
import de.metas.ui.web.payment_allocation.PaymentsView;

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

public class PaymentsView_AddAdditionalPayment extends PaymentsViewBasedProcess
{
	@Param(parameterName = "C_Payment_ID", mandatory = true)
	private PaymentId paymentId;

	@Override
	protected String doIt()
	{
		final PaymentsView view = getView();
		view.addPayment(paymentId);
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			invalidateView();
		}
	}
}
