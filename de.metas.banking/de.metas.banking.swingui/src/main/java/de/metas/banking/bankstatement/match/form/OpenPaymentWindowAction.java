package de.metas.banking.bankstatement.match.form;

import java.awt.event.ActionEvent;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.form.IClientUI;
import de.metas.banking.bankstatement.match.model.IPayment;

/*
 * #%L
 * de.metas.banking.swingui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Opens the payment window for currently selected payment.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OpenPaymentWindowAction extends AbstractPaymentTableAction
{
	private static final long serialVersionUID = 1L;
	private static final String MSG = "C_Payment_ID";

	public OpenPaymentWindowAction()
	{
		super(MSG);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final IPayment payment = getSelectedPayment();
		if (payment == null)
		{
			return;
		}

		final TableRecordReference paymentRecord = new TableRecordReference(I_C_Payment.Table_Name, payment.getC_Payment_ID());
		Services.get(IClientUI.class).showWindow(paymentRecord);
	}

	@Override
	public void updateBeforeDisplaying()
	{
		setEnabled(getSelectedPayment() != null);
	}
}
