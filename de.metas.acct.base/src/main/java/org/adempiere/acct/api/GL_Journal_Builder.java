package org.adempiere.acct.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_GL_Category;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.MGLCategory;
import org.compiere.model.X_GL_Category;
import org.compiere.util.TimeUtil;

import de.metas.currency.ICurrencyDAO;

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

/**
 * {@link I_GL_Journal} and {@link I_GL_JournalLine} builder.
 * 
 * To create a new builder instance please use {@link #newBuilder(I_GL_JournalBatch)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GL_Journal_Builder
{
	public static final GL_Journal_Builder newBuilder(final I_GL_JournalBatch glJournalBatch)
	{
		return new GL_Journal_Builder(glJournalBatch);
	}

	// services
	private final transient ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	private final I_GL_JournalBatch glJournalBatch;
	private final I_GL_Journal glJournal;

	private final List<GL_JournalLine_Builder> glJournalLineBuilders = new ArrayList<>();

	private GL_Journal_Builder(final I_GL_JournalBatch glJournalBatch)
	{
		super();

		Check.assumeNotNull(glJournalBatch, "glJournalBatch not null");
		this.glJournalBatch = glJournalBatch;

		glJournal = InterfaceWrapperHelper.newInstance(I_GL_Journal.class, glJournalBatch);
		glJournal.setGL_JournalBatch(glJournalBatch);
		glJournal.setAD_Org_ID(glJournalBatch.getAD_Org_ID());
		glJournal.setGL_JournalBatch(glJournalBatch);
		glJournal.setDateAcct(glJournalBatch.getDateAcct());
		glJournal.setDateDoc(glJournalBatch.getDateDoc());
		glJournal.setPostingType(glJournalBatch.getPostingType());
		glJournal.setC_DocType_ID(glJournalBatch.getC_DocType_ID());

		glJournal.setC_Currency_ID(glJournalBatch.getC_Currency_ID());
		
		if (glJournalBatch.getGL_Category_ID() > 0)
		{
			glJournal.setGL_Category_ID(glJournalBatch.getGL_Category_ID());
		}
		else
		{
			setGL_Category_ForCategoryType(X_GL_Category.CATEGORYTYPE_Manual);
		}
	}

	public I_GL_Journal build()
	{
		glJournal.setGL_JournalBatch(glJournalBatch);
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
	
	public GL_Journal_Builder setC_ConversionType(final I_C_ConversionType conversionType)
	{
		glJournal.setC_ConversionType(conversionType);
		return this;
	}
	
	public GL_Journal_Builder setC_ConversionType_Default()
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(glJournal);
		final I_C_ConversionType conversionType = currencyDAO.retrieveDefaultConversionType(ctx, glJournal.getAD_Client_ID(), glJournal.getAD_Org_ID(), glJournal.getDateAcct());
		setC_ConversionType(conversionType);
		return this;
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
