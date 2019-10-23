package de.metas.security.impl;

import java.util.Set;

import org.compiere.Adempiere;
import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.model.I_AD_Private_Access;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.security.RoleId;
import de.metas.security.impl.ParsedSql.SqlSelect;
import de.metas.security.impl.ParsedSql.TableNameAndAlias;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
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

	private final UserRolePermissions _role;
	private final TablesAccessInfo _tablesAccessInfo = TablesAccessInfo.instance;
	private RecordAccessService _userGroupRecordAccessService; // lazy

	private Set<UserGroupId> _userGroupIds; // lazy

	UserRolePermissionsSqlHelpers(@NonNull final UserRolePermissions role)
	{
		_role = role;
	}

	private RecordAccessService getUserGroupRecordAccessService()
	{
		RecordAccessService result = _userGroupRecordAccessService;
		if (result == null)
		{
			result = _userGroupRecordAccessService = Adempiere.getBean(RecordAccessService.class);
		}
		return result;
	}

	private UserId getUserId()
	{
		return _role.getUserId();
	}

	private RoleId getRoleId()
	{
		return _role.getRoleId();
	}

	private boolean hasAccessAllOrgs()
	{
		return _role.isAccessAllOrgs();
	}

	private Set<UserGroupId> getUserGroupIds()
	{
		Set<UserGroupId> userGroupIds = this._userGroupIds;
		if (userGroupIds == null)
		{
			this._userGroupIds = userGroupIds = Adempiere.getBean(UserGroupRepository.class).getAssignedGroupIdsByUserId(getUserId());
		}
		return userGroupIds;
	}

	private boolean hasAccessToPersonalDataOfOtherUsers()
	{
		return _role.isPersonalAccess();
	}

	private boolean hasTableAccess(final int adTableId, final Access access)
	{
		return adTableId > 0 && _role.isTableAccess(adTableId, access);
	}

	private String getClientWhere(final String tableName, final String tableAlias, final Access access)
	{
		return _role.getClientWhere(tableName, tableAlias, access);
	}

	private String getOrgWhere(final String tableName, final Access access)
	{
		return _role.getOrgWhere(tableName, access);
	}

	private boolean isView(final TableNameAndAlias tableNameAndAlias)
	{
		return _tablesAccessInfo.isView(tableNameAndAlias.getTableName());
	}

	private int getAdTableId(final TableNameAndAlias tableNameAndAlias)
	{
		return _tablesAccessInfo.getAdTableId(tableNameAndAlias.getTableName());
	}

	private String getSingleKeyColumnNameOrNull(final TableNameAndAlias tableNameAndAlias)
	{
		return _tablesAccessInfo.getSingleKeyColumnNameOrNull(tableNameAndAlias.getTableName());
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
		final ParsedSql parsedSql = ParsedSql.parse(sqlSelectFromWhere);
		final SqlSelect mainSqlSelect = parsedSql.getMainSqlSelect();

		String mainTableName = mainSqlSelect.getFirstTableAliasOrTableName();
		if (!mainTableName.equals(tableNameIn))
		{
			logger.warn("First tableName/alias is not matching TableNameIn={}. Considering the TableNameIn. \nmainSqlSelect: {}", tableNameIn, mainSqlSelect);
			mainTableName = tableNameIn;
		}

		final StringBuilder sqlAcessSqlWhereClause = new StringBuilder();

		// Do we have to add WHERE or AND
		if (!mainSqlSelect.hasWhereClause())
		{
			sqlAcessSqlWhereClause.append(" WHERE ");
		}
		else
		{
			sqlAcessSqlWhereClause.append(" AND ");
		}

		//
		// Client/Org Access
		if (isApplyClientAndOrgAccess(mainTableName))
		{
			// Client Access
			final String tableAlias = fullyQualified ? mainTableName : null;
			sqlAcessSqlWhereClause.append("\n /* security-client */ ");
			sqlAcessSqlWhereClause.append(getClientWhere(mainTableName, tableAlias, access));

			// Org Access
			if (!hasAccessAllOrgs())
			{
				sqlAcessSqlWhereClause.append("\n /* security-org */ ");
				sqlAcessSqlWhereClause.append(" AND ");
				if (fullyQualified)
				{
					sqlAcessSqlWhereClause.append(mainTableName).append(".");
				}
				sqlAcessSqlWhereClause.append(getOrgWhere(mainTableName, access));
			}
		}
		else
		{
			sqlAcessSqlWhereClause.append("\n /* security-client-org-NO */ 1=1");
		}

		//
		// Data Access
		for (final TableNameAndAlias tableNameAndAlias : mainSqlSelect.getTableNameAndAliases())
		{
			if (tableNameAndAlias.isTrlTable())
			{
				continue;
			}

			if (isView(tableNameAndAlias))
			{
				continue;
			}

			// Data Table Access
			final int adTableId = getAdTableId(tableNameAndAlias);
			if (!hasTableAccess(adTableId, access))
			{
				sqlAcessSqlWhereClause.append("\n /* security-tableAccess-NO */ AND 1=3"); // prevent access at all
				logger.debug("No access to AD_Table_ID={} - {} - {}", adTableId, tableNameAndAlias, sqlAcessSqlWhereClause);
				break;	// no need to check further
			}

			//
			final String keyColumnName = getSingleKeyColumnNameOrNull(tableNameAndAlias);
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

			final String recordWhere = getRecordWhere(tableNameAndAlias, adTableId, keyColumnNameFQ, access);
			if (!recordWhere.isEmpty())
			{
				sqlAcessSqlWhereClause.append("\n /* security-record */ AND ").append(recordWhere);
				logger.trace("Record access: {}", recordWhere);
			}
		} // for all tables

		//
		return sqlAcessSqlWhereClause.toString();
	}

	private boolean isApplyClientAndOrgAccess(final String mainTableName)
	{
		return !I_AD_PInstance_Log.Table_Name.equals(mainTableName);
	}

	/**
	 * Return Where clause for Record Access
	 *
	 * @return record access where clause or empty
	 */
	private String getRecordWhere(
			final TableNameAndAlias tableNameAndAlias,
			final int adTableId,
			final String keyColumnNameFQ,
			final Access access)
	{
		final StringBuilder sqlWhereFinal = new StringBuilder();

		//
		// Private data record access
		final UserId userId = getUserId();
		if (!hasAccessToPersonalDataOfOtherUsers())
		{
			final String sqlWhere = buildPersonalDataRecordAccessSqlWhereClause(adTableId, keyColumnNameFQ, userId);
			if (sqlWhereFinal.length() > 0)
			{
				sqlWhereFinal.append(" AND ");
			}
			sqlWhereFinal.append(sqlWhere);
		}

		//
		// User/Group record access
		{
			final String sqlWhere = getUserGroupRecordAccessService().buildUserGroupRecordAccessSqlWhereClause(
					tableNameAndAlias.getTableName(),
					adTableId,
					keyColumnNameFQ,
					userId,
					getUserGroupIds(),
					getRoleId());

			if (!Check.isEmpty(sqlWhere))
			{
				if (sqlWhereFinal.length() > 0)
				{
					sqlWhereFinal.append(" AND ");
				}
				sqlWhereFinal.append(sqlWhere);
			}
		}

		//
		return sqlWhereFinal.toString();
	}	// getRecordWhere

	private String buildPersonalDataRecordAccessSqlWhereClause(
			final int adTableId,
			@NonNull final String keyColumnNameFQ,
			@NonNull final UserId userId)
	{
		final StringBuilder sql = new StringBuilder(" NOT EXISTS ( SELECT Record_ID FROM " + I_AD_Private_Access.Table_Name
				+ " WHERE"
				+ " IsActive='Y'"
				+ " AND AD_Table_ID=" + adTableId
				+ " AND Record_ID=" + keyColumnNameFQ);

		//
		// User
		sql.append(" AND AD_User_ID <> " + userId.getRepoId());

		//
		// User Group
		final Set<UserGroupId> userGroupIds = getUserGroupIds();
		if (!userGroupIds.isEmpty())
		{
			sql.append(" AND NOT (").append(DB.buildSqlList("AD_UserGroup_ID", userGroupIds)).append(")");
		}

		//
		sql.append(")");
		return sql.toString();
	}
}
