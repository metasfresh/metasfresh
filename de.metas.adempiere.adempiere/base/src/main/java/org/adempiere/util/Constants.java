package org.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;

import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

public final class Constants
{

	public static final String ADV_DONT_CHANGE_RESULT = "Adv_Dont_Change_Result";

	final static public BigDecimal HUNDRET = new BigDecimal("100");

	/**
	 * AD_Column_ID of column {@link I_M_InOut#COLUMNNAME_M_InOut_ID}.
	 */
	public static final int AD_COLUMN_ID_M_INOUT_M_INOUT_ID = 3521;

	/**
	 * AD_Column_ID of column {@link I_M_Product#COLUMNNAME_M_Product_ID}.
	 */
	public static final int AD_COLUMN_ID_M_PRODUCT_M_PRODUCT_ID = 1402;

	public static final int AD_REFERENCE_ID_DOCUMENT_ACTION = 135;

	public static final int C_CHARGE_ID_GESAMTAUFTRAGSRABATT = 501671;

	public static final int C_GREETING_ID_FRAU = 501635;

	public static final int C_GREETING_ID_HERR = 501634;

	public static final int C_GREETING_ID_FIRMA = 500069;

	public static final String REPLENISHTYPE_EnsureFutureQty = "7";

	/*
	 * metas 00135: changed from "AEI" to "AEC" to fit the convention of the view C_Invoice_v where the DocBaseType
	 * of negative outgoing Invoices (e.g Credit Memo) end with 'C'.
	 * 
	 * metas-ts: Changed back to 'AEI', because this is an *incoming* invoice, therefore the view C_Invoice_v must return a
	 * positive amount
	 */
	public static final String DOCBASETYPE_AEInvoice = "AEI";

	public static final String DOCBASETYPE_AVIinvoice = "AVI";
}
