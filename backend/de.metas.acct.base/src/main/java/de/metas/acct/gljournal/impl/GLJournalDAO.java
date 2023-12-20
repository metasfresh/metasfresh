package de.metas.acct.gljournal.impl;

import de.metas.acct.gljournal.IGLJournalDAO;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
				.addInArrayOrAllFilter(I_GL_Journal.COLUMNNAME_DocStatus, IDocument.STATUS_Closed, IDocument.STATUS_Completed); // DocStatus in ('CO', 'CL')

		// Exclude the entries that don't have either Credit or Debit amounts. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_GL_Journal> nonZeroFilter = queryBL.createCompositeQueryFilter(I_GL_Journal.class).setJoinOr()
				.addNotEqualsFilter(I_GL_Journal.COLUMNNAME_TotalCr, BigDecimal.ZERO)
				.addNotEqualsFilter(I_GL_Journal.COLUMNNAME_TotalDr, BigDecimal.ZERO);

		queryBuilder.filter(nonZeroFilter);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_GL_Journal.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_GL_Journal.class));

		queryBuilder
				.addNotInSubQueryFilter(I_GL_Journal.COLUMNNAME_GL_Journal_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.create()) // has no accounting
		;

		return queryBuilder
				.create()
				.list();
	}
}
