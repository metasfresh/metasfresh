package de.metas.banking.bankstatement.match.form;

import java.awt.event.ActionEvent;

import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.adempiere.form.IClientUI;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;

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
 * Opens the {@link IPaymentBatch} related window for currently selected {@link IPayment}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class OpenPaymentBatchWindowAction extends AbstractPaymentTableAction
{
	private static final long serialVersionUID = -2940896565698328307L;
	private static final String MSG = "C_PaymentBatch_ID";

	public OpenPaymentBatchWindowAction()
	{
		super(MSG);
	}

	@Override
	public void updateBeforeDisplaying()
	{
		final ITableRecordReference paymentBatchRecord = getPaymentBatchRecord();
		setEnabled(paymentBatchRecord != null);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final ITableRecordReference paymentBatchRecord = getPaymentBatchRecord();
		if (paymentBatchRecord == null)
		{
			return;
		}

		Services.get(IClientUI.class).showWindow(paymentBatchRecord);
	}

	private final ITableRecordReference getPaymentBatchRecord()
	{
		final IPayment selectedPayment = getSelectedPayment();
		if (selectedPayment == null)
		{
			return null;
		}

		final IPaymentBatch paymentBatch = selectedPayment.getPaymentBatch();
		if (paymentBatch == null)
		{
			return null;
		}

		final ITableRecordReference paymentBatchRecord = paymentBatch.getRecord();
		return paymentBatchRecord;
	}
}
