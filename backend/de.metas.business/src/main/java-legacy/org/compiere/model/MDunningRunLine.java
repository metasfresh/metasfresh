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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.service.ClientId;
import org.compiere.util.DB;

import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;

/**
 * Dunning Run Line Model
 * 
 * @author Jorg Janke
 * @version $Id: MDunningRunLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MDunningRunLine extends X_C_DunningRunLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6329441027195611155L;

	/**
	 * Standarc Constructor
	 * 
	 * @param ctx ctx
	 * @param C_DunningRunLine_ID id
	 * @param trxName transaction
	 */
	public MDunningRunLine(Properties ctx, int C_DunningRunLine_ID, String trxName)
	{
		super(ctx, C_DunningRunLine_ID, trxName);
		if (C_DunningRunLine_ID == 0)
		{
			setAmt(BigDecimal.ZERO);
			setOpenAmt(BigDecimal.ZERO);
			setConvertedAmt(BigDecimal.ZERO);
			setFeeAmt(BigDecimal.ZERO);
			setInterestAmt(BigDecimal.ZERO);
			setTotalAmt(BigDecimal.ZERO);
			setDaysDue(0);
			setTimesDunned(0);
			setIsInDispute(false);
			setProcessed(false);
		}
	}	// MDunningRunLine

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDunningRunLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDunningRunLine

	/**
	 * Parent Constructor
	 * 
	 * @param parent parent
	 */
	public MDunningRunLine(MDunningRunEntry parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setC_DunningRunEntry_ID(parent.getC_DunningRunEntry_ID());
		//
		m_parent = parent;
		m_C_CurrencyTo_ID = parent.getC_Currency_ID();
	}	// MDunningRunLine

	private MDunningRunEntry m_parent = null;
	private MInvoice m_invoice = null;
	private MPayment m_payment = null;
	private int m_C_CurrencyFrom_ID = 0;
	private int m_C_CurrencyTo_ID = 0;

	/**
	 * Get Parent
	 * 
	 * @return parent
	 */
	public MDunningRunEntry getParent()
	{
		if (m_parent == null)
		{
			m_parent = new MDunningRunEntry(getCtx(), getC_DunningRunEntry_ID(), get_TrxName());
		}
		return m_parent;
	}	// getParent

	/**
	 * Get Invoice
	 * 
	 * @return Returns the invoice.
	 */
	public MInvoice getInvoice()
	{
		if (getC_Invoice_ID() == 0)
		{
			m_invoice = null;
		}
		else if (m_invoice == null)
		{
			m_invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		}
		return m_invoice;
	}	// getInvoice

	/**
	 * Set Invoice
	 * 
	 * @param invoice The invoice to set.
	 */
	public void setInvoice(MInvoice invoice)
	{
		m_invoice = invoice;
		if (invoice != null)
		{
			m_C_CurrencyFrom_ID = invoice.getC_Currency_ID();
			setAmt(invoice.getGrandTotal());
			setOpenAmt(getAmt());	// not correct
			setConvertedAmt(computeConvertedAmt());
		}
		else
		{
			m_C_CurrencyFrom_ID = 0;
			setAmt(BigDecimal.ZERO);
			setOpenAmt(BigDecimal.ZERO);
			setConvertedAmt(BigDecimal.ZERO);
		}
	}	// setInvoice

	/**
	 * Set Invoice
	 * 
	 * @param C_Invoice_ID
	 * @param C_Currency_ID
	 * @param GrandTotal
	 * @param Open
	 * @param FeeAmount
	 * @param DaysDue
	 * @param IsInDispute
	 * @param TimesDunned
	 * @param DaysAfterLast not used
	 */
	public void setInvoice(
			int C_Invoice_ID,
			int C_Currency_ID,
			BigDecimal GrandTotal, BigDecimal Open,
			BigDecimal FeeAmount,
			int DaysDue, 
			boolean IsInDispute,
			int TimesDunned, 
			int DaysAfterLast)
	{
		setC_Invoice_ID(C_Invoice_ID);
		m_C_CurrencyFrom_ID = C_Currency_ID;
		setAmt(GrandTotal);
		setOpenAmt(Open);
		setFeeAmt(FeeAmount);
		setConvertedAmt(Services.get(ICurrencyBL.class).convert(
				getOpenAmt(),
				CurrencyId.ofRepoId(C_Currency_ID), 
				CurrencyId.ofRepoId(getC_CurrencyTo_ID()), 
				ClientId.ofRepoId(getAD_Client_ID()), 
				OrgId.ofRepoId(getAD_Org_ID())));
		setIsInDispute(IsInDispute);
		setDaysDue(DaysDue);
		setTimesDunned(TimesDunned);
	}	// setInvoice

	/**
	 * Set Fee
	 * 
	 * @param C_Currency_ID
	 * @param FeeAmount
	 */
	public void setFee(int C_Currency_ID,
			BigDecimal FeeAmount)
	{
		m_C_CurrencyFrom_ID = C_Currency_ID;
		setAmt(FeeAmount);
		setOpenAmt(FeeAmount);
		setFeeAmt(FeeAmount);
		setConvertedAmt(Services.get(ICurrencyBL.class).convert(
				getOpenAmt(),
				CurrencyId.ofRepoId(C_Currency_ID), 
				CurrencyId.ofRepoId(getC_CurrencyTo_ID()), 
				ClientId.ofRepoId(getAD_Client_ID()), 
				OrgId.ofRepoId(getAD_Org_ID())));
	}	// setInvoice

	/**
	 * Get Payment
	 * 
	 * @return Returns the payment.
	 */
	public MPayment getPayment()
	{
		if (getC_Payment_ID() == 0)
		{
			m_payment = null;
		}
		else if (m_payment == null)
		{
			m_payment = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
		}
		return m_payment;
	}	// getPayment

	/**
	 * Set Payment
	 *
	 * public void setPayment (MPayment payment)
	 * {
	 * m_payment = payment;
	 * if (payment != null)
	 * {
	 * m_C_CurrencyFrom_ID = payment.getC_Currency_ID();
	 * setAmt(payment.getPayAmt()); // need to reverse
	 * setOpenAmt(getAmt()); // not correct
	 * setConvertedAmt (MConversionRate.convert(getCtx(), getOpenAmt(),
	 * getC_CurrencyFrom_ID(), getC_CurrencyTo_ID(), getAD_Client_ID(), getAD_Org_ID()));
	 * }
	 * else
	 * {
	 * m_C_CurrencyFrom_ID = 0;
	 * setAmt(BigDecimal.ZERO);
	 * setConvertedAmt(BigDecimal.ZERO);
	 * }
	 * } // setPayment
	 * 
	 * /**
	 * Set Payment
	 * 
	 * @param C_Payment_ID
	 * @param C_Currency_ID
	 * @param PayAmt
	 * @param OpenAmt
	 */
	public void setPayment(int C_Payment_ID, int C_Currency_ID,
			BigDecimal PayAmt, BigDecimal OpenAmt)
	{
		setC_Payment_ID(C_Payment_ID);
		m_C_CurrencyFrom_ID = C_Currency_ID;
		setAmt(PayAmt);
		setOpenAmt(OpenAmt);
		setConvertedAmt(Services.get(ICurrencyBL.class).convert(
				getOpenAmt(),
				CurrencyId.ofRepoId(C_Currency_ID), 
				CurrencyId.ofRepoId(getC_CurrencyTo_ID()), 
				ClientId.ofRepoId(getAD_Client_ID()), 
				OrgId.ofRepoId(getAD_Org_ID())));
	}	// setPayment

	/**
	 * Get Currency From (Invoice/Payment)
	 * 
	 * @return Returns the Currency From
	 */
	public int getC_CurrencyFrom_ID()
	{
		if (m_C_CurrencyFrom_ID == 0)
		{
			if (getC_Invoice_ID() != 0)
			{
				m_C_CurrencyFrom_ID = getInvoice().getC_Currency_ID();
			}
			else if (getC_Payment_ID() != 0)
			{
				m_C_CurrencyFrom_ID = getPayment().getC_Currency_ID();
			}
		}
		return m_C_CurrencyFrom_ID;
	}	// getC_CurrencyFrom_ID

	/**
	 * Get Currency To from Parent
	 * 
	 * @return Returns the Currency To
	 */
	public int getC_CurrencyTo_ID()
	{
		if (m_C_CurrencyTo_ID == 0)
		{
			m_C_CurrencyTo_ID = getParent().getC_Currency_ID();
		}
		return m_C_CurrencyTo_ID;
	}	// getC_CurrencyTo_ID

	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Set Amt
		if (getC_Invoice_ID() == 0 && getC_Payment_ID() == 0)
		{
			setAmt(BigDecimal.ZERO);
			setOpenAmt(BigDecimal.ZERO);
		}
		// Converted Amt
		if (BigDecimal.ZERO.compareTo(getOpenAmt()) == 0)
		{
			setConvertedAmt(BigDecimal.ZERO);
		}
		else if (BigDecimal.ZERO.compareTo(getConvertedAmt()) == 0)
		{
			setConvertedAmt(computeConvertedAmt());
		}
		// Total
		setTotalAmt(getConvertedAmt().add(getFeeAmt()).add(getInterestAmt()));
		// Set Collection Status
		if (isProcessed() && getInvoice() != null)
		{
			I_C_DunningLevel level = getParent().getC_DunningLevel();
			if (level != null)
			{
				getInvoice().setC_DunningLevel_ID(level.getC_DunningLevel_ID());
				if (level.getInvoiceCollectionType() != null)
				{
					getInvoice().setInvoiceCollectionType(level.getInvoiceCollectionType());
				}
				else
				{
					if (!level.isStatement())
					{
						getInvoice().setInvoiceCollectionType(MInvoice.INVOICECOLLECTIONTYPE_Dunning);
					}
				}
				getInvoice().saveEx();
			}
		}

		return true;
	}	// beforeSave

	private BigDecimal computeConvertedAmt()
	{
		return Services.get(ICurrencyBL.class).convert(
				getOpenAmt(),
				CurrencyId.ofRepoId(getC_CurrencyFrom_ID()), 
				CurrencyId.ofRepoId(getC_CurrencyTo_ID()), 
				ClientId.ofRepoId(getAD_Client_ID()), 
				OrgId.ofRepoId(getAD_Org_ID()));
	}

	/**
	 * After Save
	 * 
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		updateEntry();
		return success;
	}	// afterSave

	/**
	 * After Delete
	 * 
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterDelete(boolean success)
	{
		updateEntry();
		return success;
	}	// afterDelete

	/**
	 * Update Entry.
	 * Calculate/update Amt/Qty
	 */
	private void updateEntry()
	{
		// we do not count the fee line as an item, but it sum it up.
		String sql = "UPDATE C_DunningRunEntry e "
				+ "SET Amt=COALESCE((SELECT SUM(ConvertedAmt)+SUM(FeeAmt)+SUM(InterestAmt)"
				+ " FROM C_DunningRunLine l "
				+ "WHERE e.C_DunningRunEntry_ID=l.C_DunningRunEntry_ID), 0), "
				+ "QTY=(SELECT COUNT(*)"
				+ " FROM C_DunningRunLine l "
				+ "WHERE e.C_DunningRunEntry_ID=l.C_DunningRunEntry_ID "
				+ " AND (NOT C_Invoice_ID IS NULL OR NOT C_Payment_ID IS NULL))"
				+ " WHERE C_DunningRunEntry_ID=" + getC_DunningRunEntry_ID();

		DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
	}	// updateEntry

}	// MDunningRunLine
