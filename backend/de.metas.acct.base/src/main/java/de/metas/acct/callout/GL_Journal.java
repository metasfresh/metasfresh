package de.metas.acct.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRate;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Journal;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;

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

	@CalloutMethod(columnNames = {
			I_GL_Journal.COLUMNNAME_DateAcct,
			I_GL_Journal.COLUMNNAME_C_Currency_ID,
			I_GL_Journal.COLUMNNAME_C_ConversionType_ID })
	public void updateCurrencyRate(final I_GL_Journal glJournal)
	{
		//
		// Extract data from source Journal
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournal.getC_Currency_ID());
		if (currencyId == null)
		{
			// not set yet
			return;
		}

		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(glJournal.getC_ConversionType_ID());
		LocalDate dateAcct = TimeUtil.asLocalDate(glJournal.getDateAcct());
		if (dateAcct == null)
		{
			dateAcct = SystemTime.asLocalDate();
		}
		final ClientId adClientId = ClientId.ofRepoId(glJournal.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(glJournal.getAD_Org_ID());
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(glJournal.getC_AcctSchema_ID());
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);

		//
		// Calculate currency rate
		final BigDecimal currencyRate;
		if (acctSchema != null)
		{
			currencyRate = Services.get(ICurrencyBL.class).getCurrencyRateIfExists(
					currencyId,
					acctSchema.getCurrencyId(),
					dateAcct,
					conversionTypeId,
					adClientId,
					adOrgId)
					.map(CurrencyRate::getConversionRate)
					.orElse(BigDecimal.ZERO);
		}
		else
		{
			currencyRate = BigDecimal.ONE;
		}

		//
		glJournal.setCurrencyRate(currencyRate);
	}

	// Old/missing callouts
	// "GL_Journal";"C_Period_ID";"org.compiere.model.CalloutGLJournal.period"
	// "GL_Journal";"DateAcct";"org.compiere.model.CalloutGLJournal.period"

}
