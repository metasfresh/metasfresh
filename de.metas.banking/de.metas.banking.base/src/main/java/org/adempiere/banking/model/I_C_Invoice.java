package org.adempiere.banking.model;

/*
 * #%L
 * de.metas.banking.base
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

import de.metas.banking.model.I_C_RecurrentPaymentLine;


public interface I_C_Invoice extends org.compiere.model.I_C_Invoice
{
    /** Column name C_RecurrentPaymentLine_ID */
    public static final String COLUMNNAME_C_RecurrentPaymentLine_ID = "C_RecurrentPaymentLine_ID";

	/** Set Recurrent Payment Line	  */
	public void setC_RecurrentPaymentLine_ID (int C_RecurrentPaymentLine_ID);

	/** Get Recurrent Payment Line	  */
	public int getC_RecurrentPaymentLine_ID();

	public I_C_RecurrentPaymentLine getC_RecurrentPaymentLine() throws RuntimeException;
}
