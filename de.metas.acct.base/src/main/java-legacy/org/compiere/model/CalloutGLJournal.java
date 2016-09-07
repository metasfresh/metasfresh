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
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
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
	 */
	@Override
	public String rate (final ICalloutField field)
	{
		final Object value = field.getValue();
		if (value == null)
			return NO_ERROR;
		
		//  Source info
		final I_GL_JournalLine journalLine = field.getModel(I_GL_JournalLine.class);
		final int C_Currency_ID = journalLine.getC_Currency_ID();
		final int C_ConversionType_ID = journalLine.getC_ConversionType_ID();
		//int C_ConversionType_ID = ConversionType_ID.intValue();
		Timestamp DateAcct = journalLine.getDateAcct();
		if (DateAcct == null)
			DateAcct = new Timestamp(System.currentTimeMillis());
		//
		final I_C_AcctSchema as = getC_AcctSchema(field);
		final int AD_Client_ID = journalLine.getAD_Client_ID();
		final int AD_Org_ID = journalLine.getAD_Org_ID();

		BigDecimal CurrencyRate = Services.get(ICurrencyBL.class).getRate(C_Currency_ID, as.getC_Currency_ID(), 
			DateAcct, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
		log.debug("rate = " + CurrencyRate);
		if (CurrencyRate == null)
			CurrencyRate = Env.ZERO;
		journalLine.setCurrencyRate(CurrencyRate);

		return NO_ERROR;
	}	//	rate
	
	private static final MAcctSchema getC_AcctSchema(final ICalloutField field)
	{
		final Properties ctx = field.getCtx();
		final int WindowNo = field.getWindowNo(); 
		final int C_AcctSchema_ID = Env.getContextAsInt(ctx, WindowNo, "C_AcctSchema_ID");
		final I_C_AcctSchema as = InterfaceWrapperHelper.create(ctx, C_AcctSchema_ID, I_C_AcctSchema.class, ITrx.TRXNAME_None);
		return LegacyAdapters.convertToPO(as);
	}
	
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
	public String amt (final ICalloutField field)
	{
		final Object value = field.getValue();
		if (value == null || isCalloutActive())
			return NO_ERROR;

		final I_GL_JournalLine journalLine = field.getModel(I_GL_JournalLine.class);
		//  Get Target Currency & Precision from C_AcctSchema.C_Currency_ID
		final MAcctSchema as = getC_AcctSchema(field);
		int Precision = as.getStdPrecision();

		BigDecimal CurrencyRate = journalLine.getCurrencyRate();
		if (CurrencyRate == null)
		{
			CurrencyRate = Env.ONE;
			journalLine.setCurrencyRate(CurrencyRate);
		}

		//  AmtAcct = AmtSource * CurrencyRate  ==> Precision
		BigDecimal AmtSourceDr = journalLine.getAmtSourceDr();
		if (AmtSourceDr == null)
			AmtSourceDr = Env.ZERO;
		BigDecimal AmtSourceCr = journalLine.getAmtSourceCr();
		if (AmtSourceCr == null)
			AmtSourceCr = Env.ZERO;

		BigDecimal AmtAcctDr = AmtSourceDr.multiply(CurrencyRate);
		AmtAcctDr = AmtAcctDr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		journalLine.setAmtAcctDr(AmtAcctDr);
		BigDecimal AmtAcctCr = AmtSourceCr.multiply(CurrencyRate);
		AmtAcctCr = AmtAcctCr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		journalLine.setAmtAcctCr(AmtAcctCr);

		return NO_ERROR;
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
	public String precision (final ICalloutField field)
	{
		final Object value = field.getValue();
		if (value == null || isCalloutActive())
			return NO_ERROR;

		final String tableName = field.getTableName();
		if (I_GL_JournalBatch.Table_Name.equals(tableName))
		{
			// we can not enforce the precision for GL_JournalBatch.ControlAmt because 
			// the batch can consist from journals with different accounting schemas
		}
		else if (I_GL_Journal.Table_Name.equals(tableName))
		{
			final I_GL_Journal journal = field.getModel(I_GL_Journal.class);
			MJournal.setAmtPrecision(journal);
		}
		else if (I_GL_JournalLine.Table_Name.equals(tableName))
		{
			final I_GL_JournalLine jl = field.getModel(I_GL_JournalLine.class);
			Services.get(IGLJournalLineBL.class).setAmtSourcePrecision(jl);
		}
		
		return NO_ERROR;
	}   //  precision
	
}	//	CalloutGLJournal
