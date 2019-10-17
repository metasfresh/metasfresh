package de.metas.payment.paypal.ui.process;

import org.compiere.SpringContextHolder;

import de.metas.payment.paypal.PayPal;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.payment.paypal
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

public class PayPal_Order_UpdateFromServer extends JavaProcess
{
	private final PayPal paypal = SpringContextHolder.instance.getBean(PayPal.class);

	@Override
	protected String doIt()
	{
		final PayPalOrderId paypalOrderId = PayPalOrderId.ofRepoId(getRecord_ID());
		paypal.updatePayPalOrderFromAPI(paypalOrderId);

		return MSG_OK;
	}

}
