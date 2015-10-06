/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MBankStatementLine;

import com.google.common.collect.ImmutableList;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementDAO;

/**
 * Bank Statement Line
 *
 * @author Jorg Janke
 * @version $Id: DocLine_Bank.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
class DocLine_BankStatement extends DocLine
{
	/**
	 * Constructor
	 * 
	 * @param line statement line
	 * @param doc header
	 */
	public DocLine_BankStatement(final MBankStatementLine line, final Doc_BankStatement doc)
	{
		super(line, doc);

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

		this._bankStatementLineReferences = ImmutableList.copyOf(Services.get(IBankStatementDAO.class).retrieveLineReferences(line));
	}   // DocLine_Bank

	private final List<I_C_BankStatementLine_Ref> _bankStatementLineReferences;
	/** Reversal Flag */
	private final boolean m_IsReversal;
	// /** Payment */
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
	
	/**
	 * Get AD_Org_ID
	 * 
	 * @param usePaymentOrg if true get Org from payment
	 * @return org
	 */
	public int getAD_Org_ID(final boolean usePaymentOrg)
	{
		final I_C_Payment paymentToUse = usePaymentOrg ? getC_Payment() : null;
		return getAD_Org_ID(paymentToUse);
	}	// getAD_Org_ID
	
	/**
	 * Gets AD_Org_ID
	 * @param paymentToUseOrNull if not null the C_Payment.AD_Org_ID will be used
	 * @return C_Payment.AD_Org_ID (if any); fallback to {@link #getAD_Org_ID()}
	 */
	public final int getAD_Org_ID(final I_C_Payment paymentToUseOrNull)
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
}   // DocLine_Bank
