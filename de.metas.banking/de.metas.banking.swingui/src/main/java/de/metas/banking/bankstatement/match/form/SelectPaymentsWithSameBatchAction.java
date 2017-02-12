package de.metas.banking.bankstatement.match.form;

import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.adempiere.util.Check;
import org.compiere.swing.table.AnnotatedTableModel;

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
 * Popup action used to select the {@link IPayment}s which have the same {@link IPaymentBatch} like the one on which user right-clicked.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class SelectPaymentsWithSameBatchAction extends AbstractPaymentTableAction
{
	private static final long serialVersionUID = -2940896565698328307L;
	private static final String MSG = SelectPaymentsWithSameBatchAction.class.getName();

	public SelectPaymentsWithSameBatchAction()
	{
		super(MSG);
	}
	
	@Override
	public void updateBeforeDisplaying()
	{
		final IPayment selectedPayment = getSelectedPayment();
		setEnabled(selectedPayment != null);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final IPayment selectedPayment = getSelectedPayment();
		if (selectedPayment == null)
		{
			return;
		}

		final JTable table = getTable();
		final IPaymentBatch paymentBatch = selectedPayment.getPaymentBatch();

		final ListSelectionModel selectionModel = table.getSelectionModel();
		final AnnotatedTableModel<IPayment> paymentsModel = getPaymentsModel();
		for (int rowIndexModel = 0; rowIndexModel < paymentsModel.getRowCount(); rowIndexModel++)
		{
			final IPayment payment = paymentsModel.getRow(rowIndexModel);
			if (!Check.equals(paymentBatch, payment.getPaymentBatch()))
			{
				continue;
			}

			final int rowIndexView = table.convertRowIndexToView(rowIndexModel);
			selectionModel.addSelectionInterval(rowIndexView, rowIndexView);
		}
	}
}
