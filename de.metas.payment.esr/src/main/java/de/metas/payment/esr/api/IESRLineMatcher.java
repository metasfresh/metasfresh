package de.metas.payment.esr.api;

/*
 * #%L
 * de.metas.payment.esr
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


import org.adempiere.util.ISingletonService;

import de.metas.payment.esr.model.I_ESR_ImportLine;

public interface IESRLineMatcher extends ISingletonService
{
	public static final String ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P = "de.metas.payment.esr.EsrDoesNotBelongToInvoice";

	public static final String ERR_NO_ESR_NO_FOUND_IN_DB_1P = "de.metas.payment.esr.NoEsrNoFoundInDB";

	public static final String ERR_WRONG_REGULAR_LINE_LENGTH = "ESR_Wrong_Regular_Line_Length";

	public static final String ERR_WRONG_POST_BANK_ACCOUNT = "ESR_Wrong_Post_Bank_Account";

	public static final String ERR_WRONG_NUMBER_FORMAT_AMOUNT = "ESR_Wrong_Number_Format_Amount";

	public static final String ERR_WRONG_PAYMENT_DATE = "ESR_Wrong_Payment_Date";

	public static final String ERR_WRONG_ACCOUNT_DATE = "ESR_Wrong_Account_Date";

	public static final String ERR_INVOICE_ALREADY_PAID = "ESR_Invoice_Already_Paid";

	public static final String ERR_UNFIT_BPARTNER_VALUES = "ESR_Unfit_BPartner_Values";

	public static final String ERR_UNFIT_DOCUMENT_NOS = "ESR_Unfit_DocumentNo";

	public static final String ESR_UNFIT_INVOICE_ORG = "ESR_Unfit_Invoice_Org";

	public static final String ESR_UNFIT_BPARTNER_ORG = "ESR_Unfit_BPartner_Org";

	void match(I_ESR_ImportLine importLine);
}
