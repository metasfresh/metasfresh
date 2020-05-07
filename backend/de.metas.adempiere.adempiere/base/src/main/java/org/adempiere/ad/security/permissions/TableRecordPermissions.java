package org.adempiere.ad.security.permissions;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.security.impl.TablesAccessInfo;
import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;
import org.compiere.model.AccessSqlParser;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

public class TableRecordPermissions extends AbstractPermissions<TableRecordPermission>
{
	private static final transient Logger logger = LogManager.getLogger(TableRecordPermissions.class);

	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableSet<TableRecordPermission> _dependentRecordPermissions;

	private TableRecordPermissions(final Builder builder)
	{
		super(builder);

		this._dependentRecordPermissions = builder.getDependentPermissions();
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder();
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	public static class Builder extends PermissionsBuilder<TableRecordPermission, TableRecordPermissions>
	{
		@Override
		protected TableRecordPermissions createPermissionsInstance()
		{
			return new TableRecordPermissions(this);
		}

		public final ImmutableSet<TableRecordPermission> getDependentPermissions()
		{
			final ImmutableSet.Builder<TableRecordPermission> dependentRecordPermissionsBuilder = ImmutableSet.builder();
			for (final TableRecordPermission perm : getPermissionsInternalMap().values())
			{
				dependentRecordPermissionsBuilder.add(perm);
			}
			return dependentRecordPermissionsBuilder.build();
		}
	}

	public final Set<TableRecordPermission> getDependentRecordPermissionsList()
	{
		return _dependentRecordPermissions;
	}

	/**
	 * Access to Record (no check of table)
	 *
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param ro read only
	 * @return boolean
	 */
	public boolean isRecordAccess(final int AD_Table_ID, final int Record_ID, final boolean ro)
	{
		boolean negativeList = true;
		for (final TableRecordPermission ra : getPermissionsList())
		{
			if (ra.getAD_Table_ID() != AD_Table_ID)
			{
				continue;
			}

			if (ra.isExclude())		// Exclude
			// If you Exclude Access to a column and select Read Only,
			// you can only read data (otherwise no access).
			{
				if (ra.getRecord_ID() == Record_ID)
				{
					if (ro)
					{
						return ra.isReadOnly();
					}
					else
					{
						return false;
					}
				}
			}
			else
			// Include
			// If you Include Access to a column and select Read Only,
			// you can only read data (otherwise full access).
			{
				negativeList = false;	// has to be defined
				if (ra.getRecord_ID() == Record_ID)
				{
					if (!ro)
					{
						return !ra.isReadOnly();
					}
					else
					{
						// ro
						return true;
					}
				}
			}
		}	// for all Table Access
		return negativeList;
	}	// isRecordAccess

	public void addRecordDependentAccessSql(final StringBuilder retSQL, final AccessSqlParser asp, final String tableName, final boolean rw)
	{
		final Set<TableRecordPermission> dependentRecordPermissionsList = getDependentRecordPermissionsList();
		if(dependentRecordPermissionsList.isEmpty())
		{
			return;
		}
		
		final String mainSql = asp.getMainSql();

		int AD_Table_ID = 0;
		String whereColumnName = null;
		final List<Integer> includes = new ArrayList<>();
		final List<Integer> excludes = new ArrayList<>();
		for (final TableRecordPermission recordDependentAccess : dependentRecordPermissionsList)
		{
			final String columnName = recordDependentAccess.getKeyColumnName(asp.getTableInfo(asp.getMainSqlIndex()));
			if (columnName == null)
			{
				continue;	// no key column
			}

			if (mainSql.toUpperCase().startsWith("SELECT COUNT(*) FROM "))
			{
				// globalqss - Carlos Ruiz - [ 1965744 ] Dependent entities access problem
				// this is the count select, it doesn't have the column but needs to be filtered

				if (!TablesAccessInfo.instance.isPhysicalColumn(tableName, columnName))
				{
					continue;
				}
			}
			else
			{
				final int posColumn = mainSql.indexOf(columnName);
				if (posColumn == -1)
				{
					continue;
				}
				// we found the column name - make sure it's a column name
				char charCheck = mainSql.charAt(posColumn - 1);	// before
				if (!(charCheck == ',' || charCheck == '.' || charCheck == ' ' || charCheck == '('))
				{
					continue;
				}
				charCheck = mainSql.charAt(posColumn + columnName.length());	// after
				if (!(charCheck == ',' || charCheck == ' ' || charCheck == ')'))
				{
					continue;
				}
			}

			if (AD_Table_ID != 0 && AD_Table_ID != recordDependentAccess.getAD_Table_ID())
			{
				retSQL.append(getDependentAccess(whereColumnName, includes, excludes));
			}

			AD_Table_ID = recordDependentAccess.getAD_Table_ID();
			// *** we found the column in the main query
			if (recordDependentAccess.isExclude())
			{
				excludes.add(recordDependentAccess.getRecord_ID());
				logger.debug("Exclude " + columnName + " - " + recordDependentAccess);
			}
			else if (!rw || !recordDependentAccess.isReadOnly())
			{
				includes.add(recordDependentAccess.getRecord_ID());
				logger.debug("Include " + columnName + " - " + recordDependentAccess);
			}
			whereColumnName = getDependentRecordWhereColumn(mainSql, columnName);
		}	// for all dependent records
		
		retSQL.append(getDependentAccess(whereColumnName, includes, excludes));
	}

	/**
	 * Get Dependent Access
	 *
	 * @param whereColumnName column
	 * @param includes ids to include
	 * @param excludes ids to exclude
	 * @return where clause starting with AND or ""
	 */
	private static final String getDependentAccess(final String whereColumnName, final List<Integer> includes, final List<Integer> excludes)
	{
		if (includes.size() == 0 && excludes.size() == 0)
		{
			return "";
		}
		if (includes.size() != 0 && excludes.size() != 0)
		{
			logger.warn("Mixing Include and Excluse rules - Will not return values");
		}

		final StringBuilder where = new StringBuilder(" AND ");
		if (includes.size() == 1)
		{
			where.append(whereColumnName).append("=").append(includes.get(0));
		}
		else if (includes.size() > 1)
		{
			where.append(whereColumnName).append(" IN (");
			for (int ii = 0; ii < includes.size(); ii++)
			{
				if (ii > 0)
				{
					where.append(",");
				}
				where.append(includes.get(ii));
			}
			where.append(")");
		}
		else if (excludes.size() == 1)
		{
			where.append(whereColumnName).append("<>").append(excludes.get(0));
		}
		else if (excludes.size() > 1)
		{
			where.append(whereColumnName).append(" NOT IN (");
			for (int ii = 0; ii < excludes.size(); ii++)
			{
				if (ii > 0)
				{
					where.append(",");
				}
				where.append(excludes.get(ii));
			}
			where.append(")");
		}
		return where.toString();
	}	// getDependentAccess

	/**
	 * Get Dependent Record Where clause
	 *
	 * @param mainSql sql to examine
	 * @param columnName columnName
	 * @return where clause column "x.columnName"
	 */
	private static final String getDependentRecordWhereColumn(final String mainSql, final String columnName)
	{
		final String retValue = columnName;	// if nothing else found
		final int index = mainSql.indexOf(columnName);
		if (index == -1)
		{
			return retValue;
		}
		// see if there are table synonym
		int offset = index - 1;
		char c = mainSql.charAt(offset);
		if (c == '.')
		{
			final StringBuilder sb = new StringBuilder();
			while (c != ' ' && c != ',' && c != '(')	// delimeter
			{
				sb.insert(0, c);
				c = mainSql.charAt(--offset);
			}
			sb.append(columnName);
			return sb.toString();
		}
		return retValue;
	}	// getDependentRecordWhereColumn

	/**
	 * Return Where clause for Record Access
	 *
	 * @param AD_Table_ID table
	 * @param keyColumnName (fully qualified) key column name
	 * @param rw true if read write
	 * @return where clause or ""
	 */
	public StringBuilder getRecordWhere(final int AD_Table_ID, final String keyColumnName, final boolean rw)
	{
		// loadRecordAccess(false);
		//
		final StringBuilder sbInclude = new StringBuilder();
		final StringBuilder sbExclude = new StringBuilder();
		// Role Access
		for (final TableRecordPermission recordAccess : getPermissionsList())
		{
			if (recordAccess.getAD_Table_ID() == AD_Table_ID)
			{
				// NOT IN (x)
				if (recordAccess.isExclude())
				{
					if (sbExclude.length() == 0)
					{
						sbExclude.append(keyColumnName).append(" NOT IN (");
					}
					else
					{
						sbExclude.append(",");
					}
					sbExclude.append(recordAccess.getRecord_ID());
				}
				// IN (x)
				else if (!rw || !recordAccess.isReadOnly())	// include
				{
					if (sbInclude.length() == 0)
					{
						sbInclude.append(keyColumnName).append(" IN (");
					}
					else
					{
						sbInclude.append(",");
					}
					sbInclude.append(recordAccess.getRecord_ID());
				}
			}
		}	// for all Table Access

		final StringBuilder sb = new StringBuilder();
		if (sbExclude.length() > 0)
		{
			sb.append(sbExclude).append(")");
		}
		if (sbInclude.length() > 0)
		{
			if (sb.length() > 0)
			{
				sb.append(" AND ");
			}
			sb.append(sbInclude).append(")");
		}

		return sb;
	}	// getRecordWhere

}
