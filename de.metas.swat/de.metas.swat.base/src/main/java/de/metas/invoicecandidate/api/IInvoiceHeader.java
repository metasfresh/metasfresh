package de.metas.invoicecandidate.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.I_C_DocType;

/**
 * Invoice predecessor returned by {@link IAggregationBL#aggregate()}.
 *
 * @author tsa
 *
 */
public interface IInvoiceHeader
{
	String getDocBaseType();

	String getPOReference();

	Timestamp getDateInvoiced();

	/**
	 * @task 08437
	 */
	Timestamp getDateAcct();

	/**
	 * Note: when creating an C_Invoice, this value take precedence over the org of the order specified by {@link #getC_Order_ID()} (if >0).
	 *
	 * @return
	 */
	int getAD_Org_ID();

	int getC_Order_ID();

	int getM_PriceList_ID();

	int getBill_Location_ID();

	int getBill_BPartner_ID();

	int getBill_User_ID();

	// 03805 : add getter for C_Currency_ID
	int getC_Currency_ID();

	/**
	 * Returns a mapping from invoice candidates to the invoice line predecessor(s) into which the respective invoice candidate has been aggregated.
	 *
	 * @return
	 */
	List<IInvoiceCandAggregate> getLines();

	// 04258: add header and footer
	String getDescription();

	String getDescriptionBottom();

	boolean isSOTrx();

	int getM_InOut_ID();

	I_C_DocType getC_DocTypeInvoice();

	boolean isTaxIncluded();
}
