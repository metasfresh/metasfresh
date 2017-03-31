package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IScript;
import de.metas.migration.IScriptsRegistry;

public class SQLDatabaseScriptsRegistry implements IScriptsRegistry
{
	private static final transient Logger logger = LoggerFactory.getLogger(SQLDatabaseScriptsRegistry.class.getName());

	private final SQLHelper sqlHelper;

	public SQLDatabaseScriptsRegistry(final SQLDatabase database)
	{
		sqlHelper = new SQLHelper(database);
	}

	private String getName(final IScript script)
	{
		final String scriptName = script.getFileName();
		final String projectName = script.getProjectName();
		if (projectName == null)
		{
			return scriptName;
		}

		final String name = projectName + "->" + scriptName;
		return name;
	}

	@Override
	public boolean isApplied(final IScript script)
	{
		final String projectName = script.getProjectName();
		if (projectName == null)
		{
			// no project name. we consider that the script was not applied
			return false;
		}

		final String name = getName(script);

		final String sql = "SELECT COUNT(*) FROM AD_MigrationScript WHERE ProjectName=? AND Name=?";
		final int count = sqlHelper.getSQLValueInt(sql, projectName, name);
		if (count == 0)
		{
			logger.debug("Script was not yet executed: " + script);
			return false;
		}
		else if (count == 1)
		{
			logger.debug("Script was already executed; projectname={}; name={}; script={}", projectName, name, script);
			return true;
		}
		else
		// count > 1
		{
			// TODO: Shall we handle cnt > 1 because it could be an error?
			logger.warn("Executed (" + count + " times): " + script);
			return true;
		}
	}

	@Override
	public void markApplied(final IScript script)
	{
		final boolean ignored = false;
		dbInsert(script, ignored);
	}

	@Override
	public void markIgnored(final IScript script)
	{
		final boolean ignored = true;
		dbInsert(script, ignored);
	}

	private void dbInsert(final IScript script, final boolean ignored)
	{
		final String projectName = script.getProjectName();
		if (projectName == null)
		{
			throw new IllegalArgumentException("No projectName was set for " + script);
		}

		final String name = getName(script);
		final String developerName = null;
		final String filename = script.getFileName();

		final String description;
		if (ignored)
		{
			description = "Ignored by " + getClass().getCanonicalName();
		}
		else
		{
			description = "Applied by " + getClass().getCanonicalName();
		}

		//
		final String sql = "INSERT INTO AD_MigrationScript("
				// + " AD_MigrationScript_ID, "
				+ " AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy"
				+ ", Description"
				+ ", DeveloperName"
				+ ", IsActive, ReleaseNo, Status"
				+ ", IsApply"
				+ ", FileName"
				+ ", Name"
				+ ", ProjectName"
				+ ") VALUES ("
				// + "nextIdFunc(?, 'N'), "
				+ "0, 0, now(), 100, now(), 100" // AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy
				+ ",?" // description
				+ ",?" // developername
				+ ",'Y' , 1, 'CO'" // IsActive, ReleaseNo, Status
				+ ",?" // IsApply
				+ ",?" // FileName
				+ ",?" // Name
				+ ",?" // ProjectName
				+ ")";
		;

		sqlHelper.executeUpdate(sql, description, developerName, ignored, filename, name, projectName);
	}
}
