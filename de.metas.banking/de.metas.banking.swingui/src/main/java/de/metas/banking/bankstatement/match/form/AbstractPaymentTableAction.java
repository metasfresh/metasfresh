package de.metas.banking.bankstatement.match.form;

import javax.swing.JTable;

import org.adempiere.util.Services;
import org.compiere.swing.table.AnnotatedTableAction;
import org.compiere.swing.table.AnnotatedTableModel;
import org.compiere.util.Env;

import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.i18n.IMsgBL;

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

abstract class AbstractPaymentTableAction extends AnnotatedTableAction
{
	private static final long serialVersionUID = 1L;
	public AbstractPaymentTableAction(final String nameADMessage)
	{
		super(Services.get(IMsgBL.class).translate(Env.getCtx(), nameADMessage));
	}

	@SuppressWarnings("unchecked")
	protected final AnnotatedTableModel<IPayment> getPaymentsModel()
	{
		return (AnnotatedTableModel<IPayment>)getTableModelOrNull();
	}

	protected final IPayment getSelectedPayment()
	{
		final JTable table = getTable();
		final int rowIndexView = table.getSelectionModel().getMinSelectionIndex();
		if (rowIndexView < 0)
		{
			return null;
		}

		final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
		return getPaymentsModel().getRow(rowIndexModel);
	}

}
