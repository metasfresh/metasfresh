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

package de.metas.ui.web.kpi.data;

import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui.web.kpi.TimeRange;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

class SQLKPIDataLoader
{
	private static final Logger logger = LogManager.getLogger(SQLKPIDataLoader.class);
	@NonNull private final KPI kpi;
	@NonNull private final TimeRange timeRange;
	@NonNull final KPIDataContext context;

	@Builder
	private SQLKPIDataLoader(
			@NonNull final KPI kpi,
			@NonNull final TimeRange timeRange,
			@NonNull final KPIDataContext context)
	{
		this.kpi = kpi;
		this.timeRange = timeRange;
		this.context = context;
	}

	public KPIDataResult execute()
	{
		logger.trace("Loading data for {}", timeRange);

		final KPIDataResult.Builder data = KPIDataResult.builder()
				.setRange(timeRange);

		//
		// Create query evaluation context
		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put("MainFromMillis", DB.TO_DATE(data.getRange().getFrom()))
				.put("MainToMillis", DB.TO_DATE(data.getRange().getTo()))
				.put("FromMillis", DB.TO_DATE(timeRange.getFrom()))
				.put("ToMillis", DB.TO_DATE(timeRange.getTo()))
				.put("AD_User_ID", UserId.toRepoId(context.getUserId()))
				.put("AD_Role_ID", RoleId.toRepoId(context.getRoleId()))
				.put("AD_Client_ID", ClientId.toRepoId(context.getClientId()))
				.put("AD_Org_ID", OrgId.toRepoId(context.getOrgId()))
				.put("#Date", DB.TO_DATE(SystemTime.asZonedDateTime()))
				.build()
				// Fallback to user context
				.andComposeWith(Evaluatees.ofCtx(Env.getCtx()));

		final IStringExpression sqlExpr = kpi.getSqlDatasource().getSqlSelect();
		final String sql = sqlExpr.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				loadKPIDataSetValuesMap(data, rs);
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

		return data.build();
	}

	private void loadKPIDataSetValuesMap(final KPIDataResult.Builder data, final ResultSet rs) throws SQLException
	{
		for (final KPIField field : kpi.getFields())
		{
			final String fieldName = field.getFieldName();
			final KPIDataValue value = retrieveValue(rs, field);
			data.dataSet(fieldName)
					.dataSetValue(KPIDataSetValuesAggregationKey.NO_KEY)
					.put(fieldName, value);
		}
	}

	private KPIDataValue retrieveValue(final ResultSet rs, final KPIField field) throws SQLException
	{
		final String fieldName = field.getFieldName();
		final KPIFieldValueType valueType = field.getValueType();

		if (valueType == KPIFieldValueType.String)
		{
			final String valueStr = rs.getString(fieldName);
			return KPIDataValue.ofValueAndField(valueStr, field);
		}
		else if (valueType == KPIFieldValueType.Number)
		{
			final BigDecimal valueBD = rs.getBigDecimal(fieldName);
			return KPIDataValue.ofValueAndField(valueBD, field);
		}
		else if (valueType == KPIFieldValueType.Date
				|| valueType == KPIFieldValueType.DateTime)
		{
			final Timestamp valueTS = rs.getTimestamp(fieldName);
			return KPIDataValue.ofValueAndField(valueTS != null ? valueTS.toInstant() : null, field);
		}
		else
		{
			throw new AdempiereException("Unknown value type `" + valueType + "` for " + field);
		}
	}

}

