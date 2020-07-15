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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Journal Line Model
 *
 * @author Jorg Janke
 * @author Cristina Ghita <li>BF [ 2855807 ] AD_Org_ID from account https://sourceforge.net/tracker/?func=detail&aid=2855807&group_id=176962&atid=879332
 * @version $Id: MJournalLine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MJournalLine extends X_GL_JournalLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4997168098022675233L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param GL_JournalLine_ID id
	 * @param trxName transaction
	 */
	public MJournalLine(Properties ctx, int GL_JournalLine_ID, String trxName)
	{
		super(ctx, GL_JournalLine_ID, trxName);
		if (GL_JournalLine_ID == 0)
		{
			// setGL_JournalLine_ID (0); // PK
			// setGL_Journal_ID (0); // Parent
			// setC_Currency_ID (0);
			// setC_ValidCombination_ID (0);
			setLine(0);
			setAmtAcctCr(BigDecimal.ZERO);
			setAmtAcctDr(BigDecimal.ZERO);
			setAmtSourceCr(BigDecimal.ZERO);
			setAmtSourceDr(BigDecimal.ZERO);
			setCurrencyRate(BigDecimal.ONE);
			// setC_ConversionType_ID (0);
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			setIsGenerated(true);
		}
	}	// MJournalLine

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MJournalLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MJournalLine

	/**
	 * Parent Constructor
	 *
	 * @param parent journal
	 */
	public MJournalLine(MJournal parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setGL_Journal_ID(parent.getGL_Journal_ID());
		setC_Currency_ID(parent.getC_Currency_ID());
		setC_ConversionType_ID(parent.getC_ConversionType_ID());
		setDateAcct(parent.getDateAcct());

	}	// MJournalLine

	/**
	 * Set Currency Info
	 *
	 * @param C_Currency_ID currency
	 * @param C_ConversionType_ID type
	 * @param CurrencyRate rate
	 */
	public void setCurrency(int C_Currency_ID, int C_ConversionType_ID, BigDecimal CurrencyRate)
	{
		setC_Currency_ID(C_Currency_ID);
		if (C_ConversionType_ID > 0)
		{
			setC_ConversionType_ID(C_ConversionType_ID);
		}
		if (CurrencyRate != null && CurrencyRate.signum() == 0)
		{
			setCurrencyRate(CurrencyRate);
		}
	}	// setCurrency

	/**
	 * Set Currency Rate
	 *
	 * @param CurrencyRate check for null (->one)
	 */
	@Override
	public void setCurrencyRate(BigDecimal CurrencyRate)
	{
		if (CurrencyRate == null)
		{
			log.warn("was NULL - set to 1");
			super.setCurrencyRate(BigDecimal.ONE);
		}
		else if (CurrencyRate.signum() < 0)
		{
			log.warn("negative - " + CurrencyRate + " - set to 1");
			super.setCurrencyRate(BigDecimal.ONE);
		}
		else
			super.setCurrencyRate(CurrencyRate);
	}	// setCurrencyRate

	/**
	 * Set Accounted Amounts only if not 0. Amounts overwritten in beforeSave - set conversion rate
	 *
	 * @param AmtAcctDr Dr
	 * @param AmtAcctCr Cr
	 */
	public void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr)
	{
		// setConversion
		double rateDR = 0;
		if (AmtAcctDr != null && AmtAcctDr.signum() != 0)
		{
			rateDR = AmtAcctDr.doubleValue() / getAmtSourceDr().doubleValue();
			super.setAmtAcctDr(AmtAcctDr);
		}
		double rateCR = 0;
		if (AmtAcctCr != null && AmtAcctCr.signum() != 0)
		{
			rateCR = AmtAcctCr.doubleValue() / getAmtSourceCr().doubleValue();
			super.setAmtAcctCr(AmtAcctCr);
		}
		if (rateDR != 0 && rateCR != 0 && rateDR != rateCR)
		{
			log.warn("Rates Different DR=" + rateDR + "(used) <> CR=" + rateCR + "(ignored)");
			rateCR = 0;
		}
		if (rateDR < 0 || Double.isInfinite(rateDR) || Double.isNaN(rateDR))
		{
			log.warn("DR Rate ignored - " + rateDR);
			return;
		}
		if (rateCR < 0 || Double.isInfinite(rateCR) || Double.isNaN(rateCR))
		{
			log.warn("CR Rate ignored - " + rateCR);
			return;
		}

		if (rateDR != 0)
			setCurrencyRate(new BigDecimal(rateDR));
		if (rateCR != 0)
			setCurrencyRate(new BigDecimal(rateCR));
	}	// setAmtAcct
}	// MJournalLine
