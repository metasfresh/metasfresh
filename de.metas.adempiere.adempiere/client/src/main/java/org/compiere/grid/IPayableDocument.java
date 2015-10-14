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


import java.math.BigDecimal;
import java.sql.Timestamp;

public interface IPayableDocument
{

	String getDocStatus();

	int getC_BPartner_ID();

	BigDecimal getGrandTotal();

	int getC_Currency_ID();

	int getAD_Client_ID();

	int getAD_Org_ID();

	String getPaymentRule();

	Timestamp getDateAcct();

	int getC_PaymentTerm_ID();

	int getC_Payment_ID();

	int getC_CashLine_ID();

	void setPaymentRule(String newPaymentRule);

	void setDateAcct(Timestamp newDateAcct);

	void setC_PaymentTerm_ID(int newCPaymentTermID);

	void setC_Payment_ID(Object object);

	void setC_CashLine_ID(Object object);
	
	boolean isSOTrx();

	public String get_TableName();
}
