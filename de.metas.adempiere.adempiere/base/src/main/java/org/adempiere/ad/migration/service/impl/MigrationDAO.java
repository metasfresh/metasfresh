package org.adempiere.ad.migration.service.impl;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.service.IMigrationDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.util.DB;

public class MigrationDAO implements IMigrationDAO
{
	@Override
	public I_AD_Migration retrieveMigrationOrNull(Properties ctx, final int migrationId)
	{
		return new TypedSqlQuery<I_AD_Migration>(ctx, I_AD_Migration.class, I_AD_Migration.COLUMNNAME_AD_Migration_ID + "=?", ITrx.TRXNAME_None)
				.setParameters(migrationId)
				.firstOnly();
	}

	@Override
	public boolean existsMigration(Properties ctx, String name, int seqNo, String entityType, String trxName)
	{
		final String where = I_AD_Migration.COLUMNNAME_Name + " = ?"
				+ " AND " + I_AD_Migration.COLUMNNAME_SeqNo + " = ?"
				+ " AND " + I_AD_Migration.COLUMNNAME_EntityType + "= ?";
		final Object[] params = new Object[] { name, seqNo, entityType };
		final boolean match = new TypedSqlQuery<I_AD_Migration>(ctx, I_AD_Migration.class, where, trxName)
				.setParameters(params)
				.match();
		return match;
	}

	@Override
	public int countMigrationSteps(final I_AD_Migration migration, final String statusCode)
	{
		final StringBuilder whereClause = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		whereClause.append(I_AD_MigrationStep.COLUMNNAME_AD_Migration_ID).append("=?");
		params.add(migration.getAD_Migration_ID());

		if (statusCode != null)
		{
			whereClause.append(" AND ").append(I_AD_MigrationStep.COLUMNNAME_StatusCode).append("=?");
			params.add(statusCode);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(migration);
		final String trxName = InterfaceWrapperHelper.getTrxName(migration);

		final int count = new TypedSqlQuery<I_AD_MigrationStep>(ctx, I_AD_MigrationStep.class, whereClause.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.count();

		return count;
	}

	@Override
	public List<I_AD_MigrationStep> retrieveSteps(I_AD_Migration migration, boolean sortAsc)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(migration);
		final String trxName = InterfaceWrapperHelper.getTrxName(migration);

		final String where = I_AD_MigrationStep.COLUMNNAME_AD_Migration_ID + "=?";
		final String orderBy = I_AD_MigrationStep.COLUMNNAME_SeqNo + (sortAsc ? " ASC" : " DESC");
		return new TypedSqlQuery<I_AD_MigrationStep>(ctx, I_AD_MigrationStep.class, where, trxName)
				.setParameters(migration.getAD_Migration_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_AD_MigrationStep.class);
	}

	@Override
	public void mergeMigration(final I_AD_Migration to, final I_AD_Migration from)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(from);

		int lastSeq = DB.getSQLValueEx(trxName,
				"SELECT COALESCE(MAX(SeqNo),0) FROM AD_MigrationStep WHERE AD_Migration_ID = ?", to.getAD_Migration_ID());

		final String updateSql = "UPDATE AD_MigrationStep SET AD_Migration_ID = ?, SeqNo = SeqNo + ? WHERE AD_Migration_ID = ? ";
		final Object[] params = new Object[] { to.getAD_Migration_ID(), lastSeq, from.getAD_Migration_ID() };
		DB.executeUpdateEx(updateSql, params, trxName);
	}

	@Override
	public int getMigrationLastSeqNo(final I_AD_Migration migration)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(migration);
		final String trxName = InterfaceWrapperHelper.getTrxName(migration);
		final BigDecimal maxSeqNo = new TypedSqlQuery<I_AD_Migration>(ctx, I_AD_Migration.class, null, trxName)
				.aggregate(I_AD_Migration.COLUMNNAME_SeqNo, Aggregate.MAX);

		return maxSeqNo.intValue();
	}

	@Override
	public int getMigrationStepLastSeqNo(final I_AD_MigrationStep step)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(step);
		final String trxName = InterfaceWrapperHelper.getTrxName(step);
		final String whereClause = I_AD_MigrationStep.COLUMNNAME_AD_Migration_ID + "=?";
		final BigDecimal maxSeqNo = new TypedSqlQuery<I_AD_MigrationStep>(ctx, I_AD_MigrationStep.class, whereClause, trxName)
				.setParameters(step.getAD_Migration_ID())
				.aggregate(I_AD_MigrationStep.COLUMNNAME_SeqNo, Aggregate.MAX);
		return maxSeqNo.intValue();
	}

	@Override
	public List<I_AD_MigrationData> retrieveMigrationData(final I_AD_MigrationStep step)
	{
		if (step == null || step.getAD_MigrationStep_ID() <= 0)
		{
			return new ArrayList<I_AD_MigrationData>();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(step);
		final String trxName = InterfaceWrapperHelper.getTrxName(step);
		final String where = I_AD_MigrationData.COLUMNNAME_AD_MigrationStep_ID + "=?";
		final List<I_AD_MigrationData> result = new TypedSqlQuery<I_AD_MigrationData>(ctx, I_AD_MigrationData.class, where, trxName)
				.setParameters(step.getAD_MigrationStep_ID())
				.setOrderBy(I_AD_MigrationData.COLUMNNAME_AD_MigrationData_ID)
				.setOnlyActiveRecords(true)
				.list(I_AD_MigrationData.class);
		return result;
	}
}
