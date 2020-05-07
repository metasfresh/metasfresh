package de.metas.banking.model;

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


import java.math.BigDecimal;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

public interface IBankStatementLineOrRef
{
	//@formatter:off
	int getAD_Client_ID();
	int getAD_Org_ID();
	//@formatter:on

	//@formatter:off
	void setTrxAmt(BigDecimal trxAmt);
	BigDecimal getTrxAmt();
	//@formatter:on

	//@formatter:off
	void setDiscountAmt(BigDecimal discountAmt);
	BigDecimal getDiscountAmt();
	//@formatter:on

	//@formatter:off
	void setWriteOffAmt(BigDecimal writeOffAmt);
	BigDecimal getWriteOffAmt();
	//@formatter:on

	//@formatter:off
	void setOverUnderAmt(BigDecimal overUnderAmt);
	BigDecimal getOverUnderAmt();
	//@formatter:on

	//@formatter:off
	void setIsOverUnderPayment(boolean overUnderPayment);
	boolean isOverUnderPayment();
	//@formatter:on

	//@formatter:off
	void setC_BPartner_ID (int C_BPartner_ID);
	int getC_BPartner_ID();
	I_C_BPartner getC_BPartner();
	//@formatter:on

	//@formatter:off
	void setC_Currency_ID (int C_Currency_ID);
	int getC_Currency_ID();
	//@formatter:on

	//@formatter:off
	void setC_Invoice_ID (int C_Invoice_ID);
	void setC_Invoice(I_C_Invoice invoice);
	int getC_Invoice_ID();
	I_C_Invoice getC_Invoice();
	//@formatter:on

	//@formatter:off
	void setC_Payment_ID (int C_Payment_ID);
	void setC_Payment(I_C_Payment payment);
	int getC_Payment_ID();
	I_C_Payment getC_Payment();
	//@formatter:on

	//@formatter:off
	void setReferenceNo (java.lang.String ReferenceNo);
	java.lang.String getReferenceNo();
	//@formatter:on
}
