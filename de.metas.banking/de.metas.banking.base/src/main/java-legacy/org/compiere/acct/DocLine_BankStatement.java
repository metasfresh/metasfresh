/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPeriod;

import com.google.common.collect.ImmutableList;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyDAO;

/**
 * Bank Statement Line
 *
 * @author Jorg Janke
 * @version $Id: DocLine_Bank.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
class DocLine_BankStatement extends DocLine<Doc_BankStatement>
{
	// services
	private final transient IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final transient ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final transient ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	/**
	 * Constructor
	 * 
	 * @param line statement line
	 * @param doc header
	 */
	public DocLine_BankStatement(final I_C_BankStatementLine line, final Doc_BankStatement doc)
	{
		super(InterfaceWrapperHelper.getPO(line), doc);

		final I_C_Payment payment = line.getC_Payment();
		if (payment == null || payment.getC_Payment_ID() <= 0)
		{
			this._payment = null;
		}
		else
		{
			this._payment = payment;
		}
		m_IsReversal = line.isReversal();
		//
		m_StmtAmt = line.getStmtAmt();
		m_InterestAmt = line.getInterestAmt();
		m_TrxAmt = line.getTrxAmt();
		//
		setDateDoc(line.getValutaDate());
		setC_BPartner_ID(line.getC_BPartner_ID());

		this._bankStatementLineReferences = ImmutableList.copyOf(bankStatementDAO.retrieveLineReferences(line));

		//
		// Period
		final MPeriod period = MPeriod.get(getCtx(), line.getDateAcct(), line.getAD_Org_ID());
		if (period != null && period.isOpen(Doc.DOCTYPE_BankStatement, line.getDateAcct(), line.getAD_Org_ID()))
		{
			setC_Period_ID(period.getC_Period_ID());
		}

	}   // DocLine_Bank

	private final List<I_C_BankStatementLine_Ref> _bankStatementLineReferences;
	/** Reversal Flag */
	private final boolean m_IsReversal;
	private final I_C_Payment _payment;

	private final BigDecimal m_TrxAmt;
	private final BigDecimal m_StmtAmt;
	private final BigDecimal m_InterestAmt;

	public final List<I_C_BankStatementLine_Ref> getReferences()
	{
		return _bankStatementLineReferences;
	}

	/**
	 * Get Payment
	 * 
	 * @return {@link I_C_Payment} or <code>null</code>
	 */
	private final I_C_Payment getC_Payment()
	{
		if (_payment == null)
		{
			return null;
		}
		InterfaceWrapperHelper.setTrxName(_payment, getTrxName());
		return _payment;
	}

	/** @return payment org (if exists) or line's org */
	public int getPaymentOrg_ID()
	{
		final I_C_Payment paymentToUse = getC_Payment();
		return getPaymentOrg_ID(paymentToUse);
	}	// getAD_Org_ID

	/** @return C_Payment.AD_Org_ID (if any); fallback to {@link #getAD_Org_ID()} */
	public final int getPaymentOrg_ID(final I_C_Payment paymentToUseOrNull)
	{
		if (paymentToUseOrNull != null)
		{
			return paymentToUseOrNull.getAD_Org_ID();
		}
		return super.getAD_Org_ID();

	}

	/**
	 * Is Reversal
	 * 
	 * @return true if reversal
	 */
	public boolean isReversal()
	{
		return m_IsReversal;
	}   // isReversal

	/**
	 * Get Interest
	 * 
	 * @return InterestAmount
	 */
	public BigDecimal getInterestAmt()
	{
		return m_InterestAmt;
	}   // getInterestAmt

	/**
	 * Get Statement
	 * 
	 * @return Starement Amount
	 */
	public BigDecimal getStmtAmt()
	{
		return m_StmtAmt;
	}   // getStrmtAmt

	/**
	 * Get Transaction
	 * 
	 * @return transaction amount
	 */
	public BigDecimal getTrxAmt()
	{
		return m_TrxAmt;
	}   // getTrxAmt

	/**
	 * @return
	 *         <ul>
	 *         <li>true if this line is an inbound transaction (i.e. we received money in our bank account)
	 *         <li>false if this line is an outbound transaction (i.e. we paid money from our bank account)
	 *         </ul>
	 */
	public boolean isInboundTrx()
	{
		return getStmtAmt().signum() >= 0;
	}

	private final I_C_BankStatementLine getC_BankStatementLine()
	{
		return getModel(I_C_BankStatementLine.class);
	}

	/**
	 * @return the currency conversion used for bank transfer (i.e. Spot)
	 */
	public ICurrencyConversionContext getBankTransferCurrencyConversionCtx()
	{
		return getCurrencyConversionCtx(ConversionType.Spot);
	}

	private final ICurrencyConversionContext getCurrencyConversionCtx(final ConversionType type)
	{
		final int conversionTypeId = currencyDAO.retrieveConversionType(getCtx(), type).getC_ConversionType_ID();
		return getCurrencyConversionCtx(conversionTypeId);
	}

	private final ICurrencyConversionContext getCurrencyConversionCtx(final int conversionTypeId)
	{
		return currencyConversionBL.createCurrencyConversionContext(
				getDateAcct(),
				conversionTypeId,
				getAD_Client_ID(),
				getAD_Org_ID());
	}

	public boolean isBankTransfer()
	{
		final I_C_BankStatementLine bsl = getC_BankStatementLine();
		if (bsl.getC_BP_BankAccountTo_ID() <= 0 && bsl.getLink_BankStatementLine_ID() <= 0)
		{
			return false;
		}

		return true;
	}
}
