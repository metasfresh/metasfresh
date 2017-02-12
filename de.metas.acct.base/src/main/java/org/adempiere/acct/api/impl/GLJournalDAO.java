package org.adempiere.acct.api.impl;

import java.util.Date;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IGLJournalDAO;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

public class GLJournalDAO implements IGLJournalDAO
{

	@Override
	public List<I_GL_Journal> retrieveJournalsForBatch(final I_GL_JournalBatch batch)
	{
		final List<I_GL_Journal> journals = Services.get(IQueryBL.class).createQueryBuilder(I_GL_Journal.class, batch)
				.filter(new EqualsQueryFilter<I_GL_Journal>(I_GL_Journal.COLUMNNAME_GL_JournalBatch_ID, batch.getGL_JournalBatch_ID()))
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_GL_Journal.class);

		return journals;
	}

	@Override
	public List<I_GL_Journal> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQueryBuilder<I_GL_Journal> queryBuilder = queryBL.createQueryBuilder(I_GL_Journal.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		queryBuilder
				.addEqualsFilter(I_GL_Journal.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_GL_Journal.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_GL_Journal.COLUMNNAME_DocStatus, DocAction.STATUS_Closed, DocAction.STATUS_Completed); // DocStatus in ('CO', 'CL')

		// Exclude the entries that don't have either Credit or Debit amounts. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_GL_Journal> nonZeroFilter = queryBL.createCompositeQueryFilter(I_GL_Journal.class).setJoinOr()
				.addNotEqualsFilter(I_GL_Journal.COLUMNNAME_TotalCr, Env.ZERO)
				.addNotEqualsFilter(I_GL_Journal.COLUMNNAME_TotalDr, Env.ZERO);

		queryBuilder.filter(nonZeroFilter);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_GL_Journal.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_GL_Journal.class));

		queryBuilder
				.addNotInSubQueryFilter(I_GL_Journal.COLUMNNAME_GL_Journal_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.create()) // has no accounting
				;

		return queryBuilder
				.create()
				.list();
	}
}
