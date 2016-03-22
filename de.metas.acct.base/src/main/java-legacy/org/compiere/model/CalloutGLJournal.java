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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyBL;

/**
 *	GL Journal Callout
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutGLJournal.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutGLJournal extends CalloutEngine
{

	/**
	 * 	Journal/Line - rate.
	 * 	Set CurrencyRate from DateAcct, C_ConversionType_ID, C_Currency_ID
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	@Override
	public String rate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null)
			return "";
		
		//  Source info
		Integer Currency_ID = (Integer)mTab.getValue("C_Currency_ID");
		int C_Currency_ID = Currency_ID.intValue();
		Integer ConversionType_ID = (Integer)mTab.getValue("C_ConversionType_ID");
		int C_ConversionType_ID = ConversionType_ID.intValue();
		Timestamp DateAcct = (Timestamp)mTab.getValue("DateAcct");
		if (DateAcct == null)
			DateAcct = new Timestamp(System.currentTimeMillis());
		//
		int C_AcctSchema_ID = Env.getContextAsInt(ctx, WindowNo, "C_AcctSchema_ID");
		MAcctSchema as = MAcctSchema.get (ctx, C_AcctSchema_ID);
		int AD_Client_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");

		BigDecimal CurrencyRate = Services.get(ICurrencyBL.class).getRate(C_Currency_ID, as.getC_Currency_ID(), 
			DateAcct, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
		log.debug("rate = " + CurrencyRate);
		if (CurrencyRate == null)
			CurrencyRate = Env.ZERO;
		mTab.setValue("CurrencyRate", CurrencyRate);

		return "";
	}	//	rate
	
	/**
	 *  JournalLine - Amt.
	 *  Convert the source amount to accounted amount (AmtAcctDr/Cr)
	 *  Called when source amount (AmtSourceCr/Dr) or rate changes
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String amt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null || isCalloutActive())
			return "";

		//  Get Target Currency & Precision from C_AcctSchema.C_Currency_ID
		int C_AcctSchema_ID = Env.getContextAsInt(ctx, WindowNo, "C_AcctSchema_ID");
		MAcctSchema as = MAcctSchema.get(ctx, C_AcctSchema_ID);
		int Precision = as.getStdPrecision();

		BigDecimal CurrencyRate = (BigDecimal)mTab.getValue("CurrencyRate");
		if (CurrencyRate == null)
		{
			CurrencyRate = Env.ONE;
			mTab.setValue("CurrencyRate", CurrencyRate);
		}

		//  AmtAcct = AmtSource * CurrencyRate  ==> Precision
		BigDecimal AmtSourceDr = (BigDecimal)mTab.getValue("AmtSourceDr");
		if (AmtSourceDr == null)
			AmtSourceDr = Env.ZERO;
		BigDecimal AmtSourceCr = (BigDecimal)mTab.getValue("AmtSourceCr");
		if (AmtSourceCr == null)
			AmtSourceCr = Env.ZERO;

		BigDecimal AmtAcctDr = AmtSourceDr.multiply(CurrencyRate);
		AmtAcctDr = AmtAcctDr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		mTab.setValue("AmtAcctDr", AmtAcctDr);
		BigDecimal AmtAcctCr = AmtSourceCr.multiply(CurrencyRate);
		AmtAcctCr = AmtAcctCr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		mTab.setValue("AmtAcctCr", AmtAcctCr);

		return "";
	}   //  amt
	
	
	/**
	 *  set precision
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	// metas: 02476
	public String precision (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null || isCalloutActive())
			return "";
			
		if (I_GL_JournalBatch.Table_Name.equals(mTab.getTableName()))
		{
			// we can not enforce the precision for GL_JournalBatch.ControlAmt because 
			// the batch can consist from journals with different accounting schemas
		}
		else if (I_GL_Journal.Table_Name.equals(mTab.getTableName()))
		{
			final I_GL_Journal journal = GridTabWrapper.create(mTab, I_GL_Journal.class);
			MJournal.setAmtPrecision(journal);
		}
		else if (I_GL_JournalLine.Table_Name.equals(mTab.getTableName()))
		{
			final I_GL_JournalLine jl = GridTabWrapper.create(mTab, I_GL_JournalLine.class);
			Services.get(IGLJournalLineBL.class).setAmtSourcePrecision(jl);
		}
		
		return "";
	}   //  precision
	
}	//	CalloutGLJournal
