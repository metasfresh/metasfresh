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

import com.google.common.base.Stopwatch;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.ui.web.kpi.TimeRange;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

class SQLKPIDataLoader
{
	private static final Logger logger = LogManager.getLogger(SQLKPIDataLoader.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	@NonNull private final KPIField valueField;
	@Nullable private final KPIField uomField;
	@NonNull private final ITranslatableString filterCaption;

	@NonNull private final TimeRange timeRange;
	@NonNull final KPIDataContext context;

	@NonNull private final SQLDatasourceDescriptor sqlDatasource;

	@Builder
	private SQLKPIDataLoader(
			@NonNull final KPI kpi,
			@NonNull final TimeRange timeRange,
			@NonNull final KPIDataContext context)
	{
		uomField = extractUOMField(kpi.getFields());
		valueField = extractValueField(kpi.getFields(), uomField);
		filterCaption = kpi.getCaption();

		this.timeRange = timeRange;
		this.context = context.retainOnlyRequiredParameters(kpi.getRequiredContextParameters());

		this.sqlDatasource = Check.assumeNotNull(kpi.getSqlDatasource(), "Not an SQL data source: {}", kpi);
	}

	@Nullable
	private static KPIField extractUOMField(final List<KPIField> fields)
	{
		if (fields.size() < 2)
		{
			return null;
		}

		for (final KPIField field : fields)
		{
			final String fieldName = field.getFieldName();
			if ("Currency".equalsIgnoreCase(fieldName)
					|| "CurrencyCode".equalsIgnoreCase(fieldName)
					|| "UOMSymbol".equalsIgnoreCase(fieldName)
					|| "UOM".equalsIgnoreCase(fieldName))
			{
				return field;
			}
		}

		return null;
	}

	private static KPIField extractValueField(
			final List<KPIField> fields,
			final KPIField... excludeFields)
	{
		final List<KPIField> excludeFieldsList = Arrays.asList(excludeFields);

		for (final KPIField field : fields)
		{
			if (!excludeFieldsList.contains(field))
			{
				return field;
			}
		}

		throw new AdempiereException("Cannot determine value field: " + fields);
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

		return data
				.datasetsComputeDuration(duration.elapsed())
				.build();
	}

	private String createSelectSql()
	{
		final Evaluatee evalCtx = createEvaluationContext();
		String sql = sqlDatasource
				.getSqlSelect()
				.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve);

		if (sqlDatasource.isApplySecuritySettings())
		{
			final IUserRolePermissions permissions = getUserRolePermissions();
			sql = permissions.addAccessSQL(sql, sqlDatasource.getSourceTableName(), true, Access.READ);
		}

		return sql;
	}

	private IUserRolePermissions getUserRolePermissions()
	{
		// assume all these context values are set
		// see de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor.PERMISSION_REQUIRED_PARAMS
		final RoleId roleId = context.getRoleId();
		final UserId userId = context.getUserId();
		final ClientId clientId = context.getClientId();
		if (roleId == null || userId == null || clientId == null)
		{
			throw new AdempiereException("Cannot extract role permissions from context: " + context);
		}

		return userRolePermissionsDAO.getUserRolePermissions(
				roleId,
				userId,
				clientId,
				SystemTime.asLocalDate());
	}

	private Evaluatee createEvaluationContext()
	{
		return Evaluatees.mapBuilder()
				.put("MainFromMillis", DB.TO_DATE(timeRange.getFrom()))
				.put("MainToMillis", DB.TO_DATE(timeRange.getTo()))
				.put("FromMillis", DB.TO_DATE(timeRange.getFrom()))
				.put("ToMillis", DB.TO_DATE(timeRange.getTo()))
				.put("AD_User_ID", UserId.toRepoId(context.getUserId()))
				.put("AD_Role_ID", RoleId.toRepoId(context.getRoleId()))
				.put("AD_Client_ID", ClientId.toRepoId(context.getClientId()))
				.put("AD_Org_ID", OrgId.toRepoId(context.getOrgId()))
				.put("#Date", DB.TO_DATE(SystemTime.asZonedDateTime()))
				.build();
	}

	private void loadKPIDataSetValuesMap(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final ResultSet rs) throws SQLException
	{
		final KPIDataValue value = retrieveValue(rs, valueField);

		String unit = null;
		if (uomField != null)
		{
			unit = rs.getString(uomField.getFieldName());
		}

		data.dataSet(valueField.getFieldName())
				.unit(unit)
				.dataSetValue(KPIDataSetValuesAggregationKey.NO_KEY)
				.put(valueField.getFieldName(), value);
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

	public KPIZoomIntoDetailsInfo getKPIZoomIntoDetailsInfo()
	{
		final String sqlWhereClause = sqlDatasource
				.getSqlDetailsWhereClause()
				.evaluate(createEvaluationContext(), IExpressionEvaluator.OnVariableNotFound.Fail);

		return KPIZoomIntoDetailsInfo.builder()
				.filterCaption(filterCaption)
				.targetWindowId(sqlDatasource.getTargetWindowId())
				.tableName(sqlDatasource.getSourceTableName())
				.sqlWhereClause(sqlWhereClause)
				.build();
	}
}

