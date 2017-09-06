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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyBL;

/**
 * GL Journal Callout
 * 
 * @author Jorg Janke
 * @version $Id: CalloutGLJournal.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutGLJournal extends CalloutEngine
{

	/**
	 * Journal/Line - rate.
	 * Set CurrencyRate from DateAcct, C_ConversionType_ID, C_Currency_ID
	 */
	@Override
	public String rate(final ICalloutField field)
	{
		if (field.getValue() == null)
		{
			return NO_ERROR;
		}

		//
		// Extract data from source Journal/Line
		final String tableName = field.getTableName();
		final int currencyId;
		final int conversionTypeId;
		Timestamp dateAcct;
		final int adClientId;
		final int adOrgId;
		final I_C_AcctSchema acctSchema;
		if (I_GL_Journal.Table_Name.equals(tableName))
		{
			final I_GL_Journal journal = field.getModel(I_GL_Journal.class);
			currencyId = journal.getC_Currency_ID();
			conversionTypeId = journal.getC_ConversionType_ID();
			dateAcct = journal.getDateAcct();
			//
			adClientId = journal.getAD_Client_ID();
			adOrgId = journal.getAD_Org_ID();
			acctSchema = journal.getC_AcctSchema();
		}
		else if (I_GL_JournalLine.Table_Name.equals(tableName))
		{
			final I_GL_JournalLine journalLine = field.getModel(I_GL_JournalLine.class);
			currencyId = journalLine.getC_Currency_ID();
			conversionTypeId = journalLine.getC_ConversionType_ID();
			dateAcct = journalLine.getDateAcct();
			//
			adClientId = journalLine.getAD_Client_ID();
			adOrgId = journalLine.getAD_Org_ID();
			acctSchema = journalLine.getGL_Journal().getC_AcctSchema();
		}
		else
		{
			throw new AdempiereException("Table not supported: " + tableName);
		}

		if (dateAcct == null)
			dateAcct = SystemTime.asDayTimestamp();

		BigDecimal currencyRate;
		if (acctSchema != null)
		{
			currencyRate = Services.get(ICurrencyBL.class).getRate(currencyId, acctSchema.getC_Currency_ID(),
					dateAcct, conversionTypeId, adClientId, adOrgId);
		}
		else
		{
			currencyRate = BigDecimal.ONE;
		}
		log.debug("rate = {}", currencyRate);
		if (currencyRate == null)
			currencyRate = BigDecimal.ZERO;

		if (I_GL_Journal.Table_Name.equals(tableName))
		{
			final I_GL_Journal journal = field.getModel(I_GL_Journal.class);
			journal.setCurrencyRate(currencyRate);
		}
		else if (I_GL_JournalLine.Table_Name.equals(tableName))
		{
			final I_GL_JournalLine journalLine = field.getModel(I_GL_JournalLine.class);
			journalLine.setCurrencyRate(currencyRate);
		}

		return NO_ERROR;
	}	// rate

	private static final MAcctSchema getC_AcctSchema(final ICalloutField field)
	{
		final Properties ctx = field.getCtx();
		final int WindowNo = field.getWindowNo();
		final int C_AcctSchema_ID = Env.getContextAsInt(ctx, WindowNo, "C_AcctSchema_ID");
		final I_C_AcctSchema as = InterfaceWrapperHelper.create(ctx, C_AcctSchema_ID, I_C_AcctSchema.class, ITrx.TRXNAME_None);
		return LegacyAdapters.convertToPO(as);
	}

	/**
	 * JournalLine - Amt.
	 * Convert the source amount to accounted amount (AmtAcctDr/Cr)
	 * Called when source amount (AmtSourceCr/Dr) or rate changes
	 * 
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String amt(final ICalloutField field)
	{
		final Object value = field.getValue();
		if (value == null || isCalloutActive())
			return NO_ERROR;

		final I_GL_JournalLine journalLine = field.getModel(I_GL_JournalLine.class);
		// Get Target Currency & Precision from C_AcctSchema.C_Currency_ID
		final MAcctSchema as = getC_AcctSchema(field);
		int Precision = as.getStdPrecision();

		BigDecimal CurrencyRate = journalLine.getCurrencyRate();
		if (CurrencyRate == null)
		{
			CurrencyRate = BigDecimal.ONE;
			journalLine.setCurrencyRate(CurrencyRate);
		}

		// AmtAcct = AmtSource * CurrencyRate ==> Precision
		BigDecimal AmtSourceDr = journalLine.getAmtSourceDr();
		if (AmtSourceDr == null)
			AmtSourceDr = BigDecimal.ZERO;
		BigDecimal AmtSourceCr = journalLine.getAmtSourceCr();
		if (AmtSourceCr == null)
			AmtSourceCr = BigDecimal.ZERO;

		BigDecimal AmtAcctDr = AmtSourceDr.multiply(CurrencyRate);
		AmtAcctDr = AmtAcctDr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		journalLine.setAmtAcctDr(AmtAcctDr);
		BigDecimal AmtAcctCr = AmtSourceCr.multiply(CurrencyRate);
		AmtAcctCr = AmtAcctCr.setScale(Precision, BigDecimal.ROUND_HALF_UP);
		journalLine.setAmtAcctCr(AmtAcctCr);

		return NO_ERROR;
	}   // amt

	/**
	 * set precision
	 * 
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	// metas: 02476
	public String precision(final ICalloutField field)
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
	}   // precision

}	// CalloutGLJournal
