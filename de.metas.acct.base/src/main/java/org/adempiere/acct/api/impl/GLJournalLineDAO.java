package org.adempiere.acct.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.adempiere.acct.api.IGLJournalLineDAO;
import org.adempiere.acct.api.IGLJournalLineGroup;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.util.DB;

public class GLJournalLineDAO implements IGLJournalLineDAO
{
	@Override
	public IGLJournalLineGroup retrieveFirstUnballancedJournalLineGroup(final I_GL_Journal glJournal)
	{
		Check.assumeNotNull(glJournal, "glJournal not null");

		final String trxName = InterfaceWrapperHelper.getTrxName(glJournal);
		final int glJournalId = glJournal.getGL_Journal_ID();

		final String sqlAmtAcctGroupDr = "COALESCE(SUM(" + I_GL_JournalLine.COLUMNNAME_AmtAcctDr + "), 0)";
		final String sqlAmtAcctGroupCr = "COALESCE(SUM(" + I_GL_JournalLine.COLUMNNAME_AmtAcctCr + "), 0)";
		final String sql = "SELECT "
				+ " " + sqlAmtAcctGroupDr + " AS " + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupDr
				+ "," + sqlAmtAcctGroupCr + " AS " + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupCr
				+ ", " + I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group
				+ " FROM " + I_GL_JournalLine.Table_Name
				+ " WHERE " + I_GL_JournalLine.COLUMNNAME_GL_Journal_ID + "=?" // #1
				+ " GROUP BY " + I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group
				+ " HAVING "
				+ " " + sqlAmtAcctGroupDr + "<>" + sqlAmtAcctGroupCr
				+ " ORDER BY " + I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group
		//
		;
		final List<Object> sqlParams = Arrays.<Object> asList(glJournalId);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final int groupNo = rs.getInt(I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group);
				final BigDecimal amtAcctGroupDr = rs.getBigDecimal(I_GL_JournalLine.COLUMNNAME_AmtAcctGroupDr);
				final BigDecimal amtAcctGroupCr = rs.getBigDecimal(I_GL_JournalLine.COLUMNNAME_AmtAcctGroupCr);
				final IGLJournalLineGroup group = new GLJournalLineGroup(groupNo, amtAcctGroupDr, amtAcctGroupCr);
				return group;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return null;
	}

	@Override
	public int retrieveLastGroupNo(final I_GL_Journal glJournal)
	{
		Check.assumeNotNull(glJournal, "glJournal not null");

		final Integer lastGroupNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_JournalLine.class, glJournal)
				.addEqualsFilter(I_GL_JournalLine.COLUMN_GL_Journal_ID, glJournal.getGL_Journal_ID())
				.create()
				.aggregate(I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group, Aggregate.MAX, Integer.class);

		if (lastGroupNo == null)
		{
			return 0;
		}
		return lastGroupNo;
	}

	@Override
	public List<I_GL_JournalLine> retrieveLines(final I_GL_Journal glJournal)
	{
		Check.assumeNotNull(glJournal, "glJournal not null");

		final IQueryBuilder<I_GL_JournalLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_JournalLine.class, glJournal)
				.addEqualsFilter(I_GL_JournalLine.COLUMN_GL_Journal_ID, glJournal.getGL_Journal_ID())
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group)
				.addColumn(I_GL_JournalLine.COLUMNNAME_Line);

		return queryBuilder
				.create()
				.list();

	}

	@Override
	public int retrieveLastLineNo(final I_GL_Journal glJournal)
	{
		Check.assumeNotNull(glJournal, "glJournal not null");

		final Integer lastLineNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_JournalLine.class, glJournal)
				.addEqualsFilter(I_GL_JournalLine.COLUMN_GL_Journal_ID, glJournal.getGL_Journal_ID())
				.create()
				.aggregate(I_GL_JournalLine.COLUMNNAME_Line, Aggregate.MAX, Integer.class);

		if (lastLineNo == null)
		{
			return 0;
		}
		return lastLineNo;

	}
}
