package de.metas.acct.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.IQuery;
import org.compiere.util.DB;

import de.metas.acct.IFactAcctLogDAO;
import de.metas.acct.IFactAcctLogIterable;
import de.metas.acct.IFactAcctSummaryKey;
import de.metas.acct.model.I_Fact_Acct_EndingBalance;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.acct.model.I_Fact_Acct_Summary;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class FactAcctLogDAO implements IFactAcctLogDAO
{
	/** Function used to check {@link I_Fact_Acct_Log}s for a given tag and update {@link I_Fact_Acct_EndingBalance} */
	private static final String DB_FUNC_Fact_Acct_EndingBalance_UpdateForTag = IFactAcctDAO.DB_SCHEMA + ".Fact_Acct_EndingBalance_UpdateForTag";

	@Override
	public IFactAcctLogIterable tagAndRetrieve(final Properties ctx, final int limit)
	{
		final String processingTag = UUID.randomUUID().toString();
		updateProcessingTag(ctx, PROCESSINGTAG_NULL, processingTag, limit);

		return new FactAcctLogIterable(ctx, processingTag);
	}

	private final int releaseTag(final Properties ctx, final String processingTag)
	{
		final int limit = IQuery.NO_LIMIT;
		return updateProcessingTag(ctx, processingTag, PROCESSINGTAG_NULL, limit);
	}

	private final int updateProcessingTag(final Properties ctx, final String processingTagOld, final String processingTagNew, final int limit)
	{
		return retrieveForTagQuery(ctx, processingTagOld)
				.setLimit(limit)
				//
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct_Log.COLUMNNAME_ProcessingTag, processingTagNew)
				.execute();
	}

	@Override
	public boolean hasLogs(final Properties ctx, final String processingTag)
	{
		return retrieveForTagQuery(ctx, processingTag)
				.create()
				.match();
	}

	private final IQueryBuilder<I_Fact_Acct_Log> retrieveForTagQuery(final Properties ctx, final String processingTag)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct_Log.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_Fact_Acct_Log.COLUMN_ProcessingTag, processingTag);
	}

	private final Iterator<I_Fact_Acct_Log> retrieveForTag(final Properties ctx, final String processingTag)
	{
		return retrieveForTagQuery(ctx, processingTag)
				//
				.orderBy()
				.addColumn(I_Fact_Acct_Log.COLUMN_AD_Client_ID)
				.addColumn(I_Fact_Acct_Log.COLUMN_AD_Org_ID)
				.addColumn(I_Fact_Acct_Log.COLUMN_C_ElementValue_ID)
				.addColumn(I_Fact_Acct_Log.COLUMN_C_AcctSchema_ID)
				.addColumn(I_Fact_Acct_Log.COLUMN_PostingType)
				.addColumn(I_Fact_Acct_Log.COLUMN_DateAcct)
				.addColumn(I_Fact_Acct_Log.COLUMN_Fact_Acct_ID)
				.endOrderBy()
				//
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.iterate(I_Fact_Acct_Log.class);
	}

	private int deleteAllForTag(final Properties ctx, final String processingTag)
	{
		return retrieveForTagQuery(ctx, processingTag)
				//
				.create()
				.deleteDirectly();
	}

	public final IQueryBuilder<I_Fact_Acct_Summary> createFactAcctSummaryQueryForKeyNoDateAcct(final Properties ctx, final IFactAcctSummaryKey key)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct_Summary.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_Account_ID, key.getC_ElementValue_ID())
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_C_AcctSchema_ID, key.getC_AcctSchema_ID())
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_PostingType, key.getPostingType())
				// .addEqualsFilter(I_Fact_Acct_Summary.COLUMN_C_Period_ID, key.getC_Period_ID()) // note: don't enforce it because we are not enfocing the dates
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_AD_Client_ID, key.getAD_Client_ID())
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_AD_Org_ID, key.getAD_Org_ID())
				.addEqualsFilter(I_Fact_Acct_Summary.COLUMN_PA_ReportCube_ID, key.getPA_ReportCube_ID() <= 0 ? null : key.getPA_ReportCube_ID())
		//
		;
	}

	@Override
	public I_Fact_Acct_Summary retrieveLastMatchingFactAcctSummary(final Properties ctx, final IFactAcctSummaryKey key)
	{
		final I_Fact_Acct_Summary factAcctSummaryExisting = createFactAcctSummaryQueryForKeyNoDateAcct(ctx, key)
				.addCompareFilter(I_Fact_Acct_Summary.COLUMN_DateAcct, Operator.LESS_OR_EQUAL, key.getDateAcct())
				//
				.orderBy()
				.addColumn(I_Fact_Acct_Summary.COLUMN_DateAcct, Direction.Descending, Nulls.Last)
				.endOrderBy()
				//
				.create()
				.first(I_Fact_Acct_Summary.class);
		return factAcctSummaryExisting;
	}

	@Override
	public IQueryBuilder<I_Fact_Acct_Summary> retrieveCurrentAndNextMatchingFactAcctSummaryQuery(final Properties ctx, final IFactAcctSummaryKey key)
	{
		return createFactAcctSummaryQueryForKeyNoDateAcct(ctx, key)
				.addCompareFilter(I_Fact_Acct_Summary.COLUMN_DateAcct, Operator.GREATER_OR_EQUAL, key.getDateAcct());
	}

	@Override
	public void updateFactAcctEndingBalanceForTag(final String processingTag)
	{
		final String sql = "SELECT " + DB_FUNC_Fact_Acct_EndingBalance_UpdateForTag + "(?)";
		final Object[] sqlParams = new Object[] { processingTag };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final String resultStr = rs.getString(1);
				Loggables.get().addLog(resultStr);
			}

		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private final class FactAcctLogIterable implements IFactAcctLogIterable
	{
		@ToStringBuilder(skip = true)
		private final Properties ctx;
		private final String processingTag;

		public FactAcctLogIterable(final Properties ctx, final String processingTag)
		{
			super();
			this.ctx = ctx;
			this.processingTag = processingTag;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getProcessingTag()
		{
			return processingTag;
		}

		@Override
		public void close()
		{
			releaseTag(ctx, processingTag);
		}

		@Override
		public Iterator<I_Fact_Acct_Log> iterator()
		{
			return retrieveForTag(ctx, processingTag);
		}

		@Override
		public void deleteAll()
		{
			deleteAllForTag(ctx, processingTag);
		}
	}
}
