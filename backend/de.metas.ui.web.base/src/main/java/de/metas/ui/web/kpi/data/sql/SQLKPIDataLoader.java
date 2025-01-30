/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi.data.sql;

import com.google.common.base.Stopwatch;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui.web.kpi.TimeRange;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIPermissionsProvider;
import de.metas.ui.web.kpi.data.KPIZoomIntoDetailsInfo;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIChartType;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLKPIDataLoader
{
	private static final Logger logger = LogManager.getLogger(SQLKPIDataLoader.class);
	@NonNull private final KPIPermissionsProvider permissionsProvider;
	@NonNull private final ITranslatableString kpiCaption;
	@NonNull private final TimeRange timeRange;
	@NonNull private final KPIDataContext context;
	@NonNull private final SQLDatasourceDescriptor datasource;
	@NonNull private final SQLRowLoader rowLoader;

	@Builder
	private SQLKPIDataLoader(
			@NonNull final KPIPermissionsProvider permissionsProvider,
			@NonNull final KPI kpi,
			@NonNull final TimeRange timeRange,
			@NonNull final KPIDataContext context)
	{
		this.permissionsProvider = permissionsProvider;
		this.kpiCaption = kpi.getCaption();
		this.timeRange = timeRange;
		this.context = context.retainOnlyRequiredParameters(kpi.getRequiredContextParameters());
		this.datasource = kpi.getSqlDatasourceNotNull();
		this.rowLoader = createSQLRowLoader(kpi);
	}

	@NonNull
	private static SQLRowLoader createSQLRowLoader(final @NonNull KPI kpi)
	{
		final KPIChartType chartType = kpi.getChartType();
		if (KPIChartType.URLs.equals(chartType))
		{
			return new URLsSQLRowLoader(kpi.getFields());
		}
		else
		{
			return new ValueAndUomSQLRowLoader(kpi.getFields());
		}
	}

	public KPIDataResult retrieveData()
	{
		final Stopwatch duration = Stopwatch.createStarted();

		logger.trace("Loading data for {}", timeRange);

		final KPIDataResult.Builder data = KPIDataResult.builder()
				.range(timeRange);

		final String sql = createSelectSql();
		logger.trace("Running SQL: {}", sql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rowLoader.loadRowToResult(data, rs);
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return data
				.datasetsComputeDuration(duration.elapsed())
				.build();
	}

	private String createSelectSql()
	{
		final Evaluatee evalCtx = createEvaluationContext();
		String sql = datasource
				.getSqlSelect()
				.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve);

		if (datasource.isApplySecuritySettings())
		{
			sql = permissionsProvider.addAccessSQL(sql, datasource.getSourceTableName(), context);
		}

		return sql;
	}

	private Evaluatee createEvaluationContext()
	{
		return Evaluatees.mapBuilder()
				.put("MainFromMillis", DB.TO_DATE(timeRange.getFrom()))
				.put("MainToMillis", DB.TO_DATE(timeRange.getTo()))
				.put("FromMillis", DB.TO_DATE(timeRange.getFrom()))
				.put("ToMillis", DB.TO_DATE(timeRange.getTo()))
				.put(KPIDataContext.CTXNAME_AD_User_ID, UserId.toRepoId(context.getUserId()))
				.put(KPIDataContext.CTXNAME_AD_Role_ID, RoleId.toRepoId(context.getRoleId()))
				.put(KPIDataContext.CTXNAME_AD_Client_ID, ClientId.toRepoId(context.getClientId()))
				.put(KPIDataContext.CTXNAME_AD_Org_ID, OrgId.toRepoId(context.getOrgId()))
				.put("#Date", DB.TO_DATE(SystemTime.asZonedDateTime()))
				.build();
	}

	public KPIZoomIntoDetailsInfo getKPIZoomIntoDetailsInfo()
	{
		final String sqlWhereClause = datasource
				.getSqlDetailsWhereClause()
				.evaluate(createEvaluationContext(), IExpressionEvaluator.OnVariableNotFound.Fail);

		return KPIZoomIntoDetailsInfo.builder()
				.filterCaption(kpiCaption)
				.targetWindowId(datasource.getTargetWindowId())
				.tableName(datasource.getSourceTableName())
				.sqlWhereClause(sqlWhereClause)
				.build();
	}
}

