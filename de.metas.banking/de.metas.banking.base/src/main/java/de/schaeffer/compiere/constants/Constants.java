/*
 * Created on 29.06.2005
 */

package de.schaeffer.compiere.constants;

import org.adempiere.ad.element.api.AdWindowId;

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



/**
 * Definiert Konstanten f�r die Verwendung in den Schaeffer-Erweiterungen
 * 
 * @author Karsten Thiemann, kt@schaeffer-ag.de
 * 
 */
public final class Constants {

	private Constants() {

	}

	/** id of bankstatement window */
	public static final AdWindowId BANKSTATEMENT_WINDOW_ID = AdWindowId.ofRepoId(194);

	/** system config entry for recurrent payment invoice document type */
	public static final String RECURRENT_PAYMENT_INVOICE_DOCUMENTTYPE_ID = "RecurrentPaymentInvoice_DocumentTypeID";

	public static final String DIRECT_DEBIT_ACCOUNT_NO = "DIRECT_DEBIT_ACCOUNT_NO";

	public static final String DIRECT_DEBIT_ROUTING_NO = "DIRECT_DEBIT_ROUTING_NO";


	public static final String C_Invoice_C_RecurrentPaymentLine_ID = "C_RecurrentPaymentLine_ID";

	public static final String LOCATION_NAME = null;

	public static final String SAG = null;

	public static final String TEST_MAILADDRESS = null;

	public static final String CCPAYMENT_REVIEW_MAILS = null;

	public static final String LIVESYSTEM_SERVERNAME = null;

	public static final String TIME_BEFORE_PROMISED_CC = null;

	public static final String CC_PAYMENT_BANKACCOUNT_ID = null;

	public static final String UPS_ACCESS_LICENSE_NUMBER = null;

	public static final String UPS_USER_ID = null;

	public static final String UPS_PASSWORD = null;
	
}
