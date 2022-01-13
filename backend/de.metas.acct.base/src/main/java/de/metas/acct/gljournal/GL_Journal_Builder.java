package de.metas.acct.gljournal;

import de.metas.acct.process.GLJournalRequest;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GL_Category;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.MGLCategory;
import org.compiere.model.X_GL_Category;
import org.compiere.util.TimeUtil;

import java.util.ArrayList;
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
	private final IGLJournalBL glJournalBL = Services.get(IGLJournalBL.class);

	private final I_GL_Journal glJournal;

	private final List<GL_JournalLine_Builder> glJournalLineBuilders = new ArrayList<>();

	private GL_Journal_Builder(final GLJournalRequest request)
	{
		super();
		glJournal = InterfaceWrapperHelper.newInstance(I_GL_Journal.class);

		glJournal.setC_AcctSchema_ID(request.getAcctSchemaId().getRepoId());
		glJournal.setAD_Org_ID(request.getOrgId().getRepoId());
		glJournal.setDateAcct(TimeUtil.asTimestamp(request.getDateAcct()));
		glJournal.setDateDoc(TimeUtil.asTimestamp(request.getDateDoc()));
		glJournal.setPostingType(request.getPostingType());
		glJournal.setDescription(request.getDescription());

		final DocTypeId docTypeId = glJournalBL.getDocTypeGLJournal(request.getClientId(), request.getOrgId());
		glJournal.setC_DocType_ID(docTypeId.getRepoId());

		glJournal.setC_Currency_ID(request.getCurrencyId().getRepoId());

		final CurrencyConversionTypeId conversionTypeDefaultId = getConversionTypeDefaultId(request);
		glJournal.setC_ConversionType_ID(conversionTypeDefaultId.getRepoId());

		final GLCategoryId glCategoryId = request.getGlCategoryId();

		if (glCategoryId != null)
		{
			glJournal.setGL_Category_ID(glCategoryId.getRepoId());
		}
		else
		{
			final GLCategoryId glCategoryIdForCategoryType = getGLCategoryIdForCategoryType(X_GL_Category.CATEGORYTYPE_Manual);
			glJournal.setGL_Category_ID(GLCategoryId.toRepoId(glCategoryIdForCategoryType));
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

	public CurrencyConversionTypeId getConversionTypeDefaultId(@NonNull GLJournalRequest request)
	{
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getDefaultConversionTypeId(
				request.getClientId(),
				request.getOrgId(),
				TimeUtil.asLocalDate(request.getDateAcct()));

		return conversionTypeId;
	}

	public GLCategoryId getGLCategoryIdForCategoryType(final String categoryType)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(glJournal);
		final I_GL_Category glCategory = MGLCategory.getDefault(ctx, categoryType);
		return GLCategoryId.ofRepoIdOrNull(glCategory.getGL_Category_ID());
	}
}
