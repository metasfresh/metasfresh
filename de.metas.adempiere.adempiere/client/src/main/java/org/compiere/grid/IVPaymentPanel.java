package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;

public interface IVPaymentPanel
{
	public static final String ACTION_Online = IVPaymentPanel.class.getName() + ".ActionOnline";

	Object getComponent();

	void onActivate();

	void setFrom(I_C_Payment payment);

	/**
	 * Legacy cash line support
	 * 
	 * @param cashline
	 */
	void setFrom(I_C_CashLine cashline);

	void setEnabled(boolean enabled);

	boolean isEnabled();

	public void setReadOnly(boolean readOnly);

	public boolean isReadOnly();

	void setAmount(BigDecimal amount);

	BigDecimal getAmount();

	void setDate(Timestamp date);

	Timestamp getDate();

	int getC_Currency_ID();

	void setC_Currency_ID(int C_Currency_ID);

	void setFrom(IPayableDocument doc);

	/**
	 * 
	 * @param isInTrx
	 *            specifies that the validation runs into a db transaction so user interaction is not allowed
	 * @return true if not validated; if isInTrx is true this method should ALWAYS return true or throw an exception
	 */
	boolean validate(boolean isInTrx);

	boolean isAllowProcessing();

	void updatePayment(ProcessingCtx pctx, MPayment payment);

	@Deprecated
	int getC_CashBook_ID();

	/**
	 * 
	 * @return true if the target document is C_Payment, false if is C_CashLine
	 */
	String getTargetTableName();

	void addPropertyChangeListener(PropertyChangeListener listener);
}
