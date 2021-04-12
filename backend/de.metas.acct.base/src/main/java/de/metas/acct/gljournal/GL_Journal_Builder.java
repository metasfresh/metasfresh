package de.metas.acct.gljournal;

import de.metas.acct.process.GLJournalRequest;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Category;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.MGLCategory;
import org.compiere.model.X_GL_Category;
import org.compiere.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class GL_Journal_Builder
{
	public static final GL_Journal_Builder newBuilder(final GLJournalRequest glJournalRequest)
	{
		return new GL_Journal_Builder(glJournalRequest);
	}

	// services
	private final transient ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IGLJournalDAO glJournalDAO = Services.get(IGLJournalDAO.class);

	private final I_GL_Journal glJournal;

	private final List<GL_JournalLine_Builder> glJournalLineBuilders = new ArrayList<>();

	private GL_Journal_Builder(final GLJournalRequest request)
	{
		super();
		glJournal = InterfaceWrapperHelper.newInstance(I_GL_Journal.class);

		glJournal.setAD_Org_ID(request.getOrgId().getRepoId());
		glJournal.setDateAcct(TimeUtil.asTimestamp(request.getDateAcct()));
		glJournal.setDateDoc(TimeUtil.asTimestamp(request.getDateDoc()));
		glJournal.setPostingType(request.getPostingType());
		final DocTypeId docTypeId = glJournalDAO.retrieveDocTypeGLJournal(request.getClientId().getRepoId(),
																		  request.getOrgId().getRepoId());
		glJournal.setC_DocType_ID(docTypeId.getRepoId());

		glJournal.setC_Currency_ID(request.getCurrencyId().getRepoId());

		final GLCategoryId glCategoryId = request.getGlCategoryId();

		if (glCategoryId != null)
		{
			glJournal.setGL_Category_ID(glCategoryId.getRepoId());
		}
		else
		{
			setGL_Category_ForCategoryType(X_GL_Category.CATEGORYTYPE_Manual);
		}
	}

	public I_GL_Journal build()
	{
		InterfaceWrapperHelper.save(glJournal);

		for (final GL_JournalLine_Builder glJournalLineBuilder : glJournalLineBuilders)
		{
			glJournalLineBuilder.build();
		}

		return glJournal;
	}

	public GL_JournalLine_Builder newLine()
	{
		final GL_JournalLine_Builder glJournalLineBuilder = new GL_JournalLine_Builder(this);
		glJournalLineBuilders.add(glJournalLineBuilder);
		return glJournalLineBuilder;
	}

	I_GL_Journal getGL_Journal()
	{
		return glJournal;
	}

	public GL_Journal_Builder setDateAcct(final Date dateAcct)
	{
		glJournal.setDateAcct(TimeUtil.asTimestamp(dateAcct));
		return this;
	}

	public GL_Journal_Builder setDateDoc(final Date dateDoc)
	{
		glJournal.setDateDoc(TimeUtil.asTimestamp(dateDoc));
		return this;
	}

	public GL_Journal_Builder setC_AcctSchema_ID(final int acctSchemaId)
	{
		glJournal.setC_AcctSchema_ID(acctSchemaId);
		return this;
	}

	public GL_Journal_Builder setPostingType(final String postingType)
	{
		glJournal.setPostingType(postingType);
		return this;
	}

	public GL_Journal_Builder setC_Currency_ID(final int currencyId)
	{
		glJournal.setC_Currency_ID(currencyId);
		return this;
	}

	public GL_Journal_Builder setC_ConversionType_ID(final int C_ConversionType_ID)
	{
		glJournal.setC_ConversionType_ID(C_ConversionType_ID);
		return this;
	}

	public GL_Journal_Builder setC_ConversionType_Default()
	{
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getDefaultConversionTypeId(
				ClientId.ofRepoId(glJournal.getAD_Client_ID()),
				OrgId.ofRepoId(glJournal.getAD_Org_ID()),
				TimeUtil.asLocalDate(glJournal.getDateAcct()));

		return setC_ConversionType_ID(conversionTypeId.getRepoId());
	}

	public GL_Journal_Builder setGL_Category_ForCategoryType(final String categoryType)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(glJournal);
		final I_GL_Category glCategory = MGLCategory.getDefault(ctx, categoryType);
		glJournal.setGL_Category(glCategory);
		return this;
	}

	public GL_Journal_Builder setDescription(final String description)
	{
		glJournal.setDescription(description);
		return this;
	}
}
