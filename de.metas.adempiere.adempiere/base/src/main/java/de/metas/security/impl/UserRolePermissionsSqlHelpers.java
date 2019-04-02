package de.metas.security.impl;

import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.model.I_AD_Private_Access;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.security.impl.ParsedSql.SqlSelect;
import de.metas.security.impl.ParsedSql.TableNameAndAlias;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class UserRolePermissionsSqlHelpers
{
	private static final Logger logger = LogManager.getLogger(UserRolePermissionsSqlHelpers.class);

	private final UserRolePermissions role;
	private final TablesAccessInfo tablesAccessInfo = TablesAccessInfo.instance;

	UserRolePermissionsSqlHelpers(@NonNull final UserRolePermissions role)
	{
		this.role = role;
	}

	public String addAccessSQL(
			final String sql,
			final String tableNameIn,
			final boolean fullyQualified,
			final Access access)
	{
		// Cut off last ORDER BY clause

		// contains "SELECT .. FROM .. WHERE .." without ORDER BY
		final String sqlSelectFromWhere;

		final String sqlOrderByAndOthers;
		final int idxOrderBy = sql.lastIndexOf(" ORDER BY ");
		if (idxOrderBy >= 0)
		{
			sqlSelectFromWhere = sql.substring(0, idxOrderBy);
			sqlOrderByAndOthers = "\n" + sql.substring(idxOrderBy);
		}
		else
		{
			sqlSelectFromWhere = sql;
			sqlOrderByAndOthers = null;
		}

		final String sqlAccessSqlWhereClause = buildAccessSQL(sqlSelectFromWhere, tableNameIn, fullyQualified, access);
		if (Check.isEmpty(sqlAccessSqlWhereClause, true))
		{
			logger.trace("Final SQL (no access sql applied): {}", sql);
			return sql;
		}

		final String sqlFinal;
		if (sqlOrderByAndOthers == null)
		{
			sqlFinal = sqlSelectFromWhere + " " + sqlAccessSqlWhereClause;
		}
		else
		{
			sqlFinal = sqlSelectFromWhere + " " + sqlAccessSqlWhereClause + sqlOrderByAndOthers;
		}

		logger.trace("Final SQL: {}", sqlFinal);
		return sqlFinal;
	}	// addAccessSQL

	private final String buildAccessSQL(
			final String sqlSelectFromWhere,
			final String tableNameIn,
			final boolean fullyQualified,
			final Access access)
	{
		final StringBuilder sqlAcessSqlWhereClause = new StringBuilder();

		// Parse SQL
		final ParsedSql parsedSql = ParsedSql.parse(sqlSelectFromWhere);
		final SqlSelect mainSqlSelect = parsedSql.getMainSqlSelect();

		// Do we have to add WHERE or AND
		if (!mainSqlSelect.hasWhereClause())
		{
			sqlAcessSqlWhereClause.append(" WHERE ");
		}
		else
		{
			sqlAcessSqlWhereClause.append(" AND ");
		}

		String mainTableName = mainSqlSelect.getFirstTableAliasOrTableName();
		if (!mainTableName.equals(tableNameIn))
		{
			logger.warn("First tableName/alias is not matching TableNameIn={}. Considering the TableNameIn. \nmainSqlSelect: {}", tableNameIn, mainSqlSelect);
			mainTableName = tableNameIn;
		}

		if (!I_AD_PInstance_Log.Table_Name.equals(mainTableName))
		{
			// Client Access
			final String tableAlias = fullyQualified ? mainTableName : null;
			sqlAcessSqlWhereClause.append("\n /* security-client */ ");
			sqlAcessSqlWhereClause.append(role.getClientWhere(mainTableName, tableAlias, access));

			// Org Access
			if (!role.isAccessAllOrgs())
			{
				sqlAcessSqlWhereClause.append("\n /* security-org */ ");
				sqlAcessSqlWhereClause.append(" AND ");
				if (fullyQualified)
				{
					sqlAcessSqlWhereClause.append(mainTableName).append(".");
				}
				sqlAcessSqlWhereClause.append(role.getOrgWhere(mainTableName, access));
			}
		}
		else
		{
			sqlAcessSqlWhereClause.append("\n /* no security */ 1=1");
		}

		// ** Data Access **
		for (final TableNameAndAlias tableNameAndAlias : mainSqlSelect.getTableNameAndAliases())
		{
			if(tableNameAndAlias.isTrlTable())
			{
				continue;
			}
			
			final String tableName = tableNameAndAlias.getTableName();
			if (tablesAccessInfo.isView(tableName))
			{
				continue;
			}

			// Data Table Access
			final int adTableId = tablesAccessInfo.getAD_Table_ID(tableName);
			if (adTableId > 0 && !role.isTableAccess(adTableId, access))
			{
				sqlAcessSqlWhereClause.append("\n /* security-tableAccess-NO */ AND 1=3"); // prevent access at all
				logger.debug("No access to AD_Table_ID={} - {} - {}", adTableId, tableName, sqlAcessSqlWhereClause);
				break;	// no need to check further
			}

			//
			final String keyColumnName = tablesAccessInfo.getIdColumnName(tableName);
			if (keyColumnName == null)
			{
				continue;
			}
			//
			final String keyColumnNameFQ;
			if (fullyQualified)
			{
				keyColumnNameFQ = tableNameAndAlias.getAliasOrTableName() + "." + keyColumnName;
			}
			else
			{
				keyColumnNameFQ = keyColumnName;
			}

			final String recordWhere = getRecordWhere(adTableId, keyColumnNameFQ, access);
			if (!recordWhere.isEmpty())
			{
				sqlAcessSqlWhereClause.append("\n /* security-record */ AND ").append(recordWhere);
				logger.trace("Record access: {}", recordWhere);
			}
		} // for all tables

		// Dependent Records (only for main SQL)
		role.getRecordPermissions().addRecordDependentAccessSql(sqlAcessSqlWhereClause, mainSqlSelect, mainTableName, access);

		return sqlAcessSqlWhereClause.toString();
	}

	/**
	 * Return Where clause for Record Access
	 *
	 * @param adTableId table
	 * @param keyColumnNameFQ (fully qualified) key column name
	 * @param access required access
	 * @return where clause or ""
	 */
	private String getRecordWhere(final int adTableId, final String keyColumnNameFQ, final Access access)
	{
		final StringBuilder sb = role.getRecordPermissions().getRecordWhere(adTableId, keyColumnNameFQ, access);

		// Don't ignore Privacy Access
		if (!role.isPersonalAccess())
		{
			final String lockedIds = " NOT IN ( SELECT Record_ID FROM " + I_AD_Private_Access.Table_Name
					+ " WHERE AD_Table_ID = " + adTableId
					+ " AND AD_User_ID <> " + UserId.toRepoId(role.getUserId())
					+ " AND IsActive = 'Y' )";
			if (sb.length() > 0)
			{
				sb.append(" AND ");
			}
			sb.append(keyColumnNameFQ).append(lockedIds);
		}
		//
		return sb.toString();
	}	// getRecordWhere
}
