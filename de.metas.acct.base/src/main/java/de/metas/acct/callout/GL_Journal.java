package de.metas.acct.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_GL_Journal;

import de.metas.currency.ICurrencyBL;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_GL_Journal.class)
public class GL_Journal
{
	@CalloutMethod(columnNames = I_GL_Journal.COLUMNNAME_C_DocType_ID)
	public void onC_DocType_ID(final I_GL_Journal glJournal)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(glJournal.getC_DocType())
				.setOldDocumentNo(glJournal.getDocumentNo())
				.setDocumentModel(glJournal)
				.buildOrNull();
		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			glJournal.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@CalloutMethod(columnNames = I_GL_Journal.COLUMNNAME_DateDoc)
	public void onDateDoc(final I_GL_Journal glJournal)
	{
		final Timestamp dateDoc = glJournal.getDateDoc();
		if (dateDoc == null)
		{
			return;
		}

		glJournal.setDateAcct(dateDoc);
	}

	@CalloutMethod(columnNames = { I_GL_Journal.COLUMNNAME_DateAcct, I_GL_Journal.COLUMNNAME_C_Currency_ID, I_GL_Journal.COLUMNNAME_C_ConversionType_ID })
	public void updateCurrencyRate(final I_GL_Journal glJournal)
	{
		//
		// Extract data from source Journal
		final int currencyId = glJournal.getC_Currency_ID();
		final int conversionTypeId = glJournal.getC_ConversionType_ID();
		Timestamp dateAcct = glJournal.getDateAcct();
		if (dateAcct == null)
		{
			dateAcct = SystemTime.asDayTimestamp();
		}
		final int adClientId = glJournal.getAD_Client_ID();
		final int adOrgId = glJournal.getAD_Org_ID();
		final I_C_AcctSchema acctSchema = glJournal.getC_AcctSchema();

		//
		// Calculate currency rate
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
		if (currencyRate == null)
		{
			currencyRate = BigDecimal.ZERO;
		}

		//
		glJournal.setCurrencyRate(currencyRate);
	}

	// Old/missing callouts
	// "GL_Journal";"C_Period_ID";"org.compiere.model.CalloutGLJournal.period"
	// "GL_Journal";"DateAcct";"org.compiere.model.CalloutGLJournal.period"

}
