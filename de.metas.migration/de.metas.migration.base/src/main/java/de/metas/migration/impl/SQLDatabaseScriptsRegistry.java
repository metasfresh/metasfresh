package de.metas.migration.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.base.Suppliers;

import de.metas.migration.IScript;
import de.metas.migration.IScriptsRegistry;
import lombok.Value;

public class SQLDatabaseScriptsRegistry implements IScriptsRegistry
{
	private static final String ENV_UseInMemoryScriptsRegistry = "UseInMemoryScriptsRegistry";

	private static final transient Logger logger = LoggerFactory.getLogger(SQLDatabaseScriptsRegistry.class.getName());
	private final SQLHelper sqlHelper;

	private final boolean useInMemoryDatabase;
	private final Supplier<Collection<ScriptName>> inMemoryDatabaseSupplier = Suppliers.memoize(this::dbRetrieveAll);

	public SQLDatabaseScriptsRegistry(final SQLDatabase database)
	{
		sqlHelper = new SQLHelper(database);
		useInMemoryDatabase = Boolean.parseBoolean(System.getProperty(ENV_UseInMemoryScriptsRegistry, "true"));
		if (useInMemoryDatabase)
		{
			logger.info("Scripts registry is using in memory database. To switch it of set '-D{}=false'", ENV_UseInMemoryScriptsRegistry);
		}
	}

	@Override
	public boolean isApplied(final IScript script)
	{
		if (script.getProjectName() == null)
		{
			// no project name. we consider that the script was not applied
			return false;
		}
		final ScriptName scriptName = ScriptName.of(script);

		if (useInMemoryDatabase)
		{
			return inMemoryDatabaseSupplier.get().contains(scriptName);
		}
		else
		{
			return dbIsApplied(scriptName);
		}
	}

	@Override
	public void markApplied(final IScript script)
	{
		final boolean ignored = false;
		addToRegistry(script, ignored);
	}

	@Override
	public void markIgnored(final IScript script)
	{
		final boolean ignored = true;
		addToRegistry(script, ignored);
	}

	public void addToRegistry(final IScript script, final boolean ignored)
	{
		if (useInMemoryDatabase)
		{
			final ScriptName scriptName = ScriptName.of(script);
			inMemoryDatabaseSupplier.get().add(scriptName);
		}

		dbInsert(script, ignored);
	}

	private boolean dbIsApplied(final ScriptName scriptName)
	{
		final String sql = "SELECT COUNT(1) FROM AD_MigrationScript WHERE ProjectName=? AND Name=?";
		final int count = sqlHelper.getSQLValueInt(sql, scriptName.getProjectName(), scriptName.getName());
		if (count == 0)
		{
			logger.debug("Script was not yet executed: {}", scriptName);
			return false;
		}
		else if (count == 1)
		{
			logger.debug("Script was already executed: {}", scriptName);
			return true;
		}
		else
		// count > 1
		{
			logger.warn("Executed ({} times): {}", count, scriptName);
			return true;
		}
	}

	private void dbInsert(final IScript script, final boolean ignored)
	{
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

		final long durationMillis = script.getLastDurationMillis();

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
				+ ", DurationMillis"
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
				+ ",?" // DurationMillis
				+ ")";

		final ScriptName scriptName = ScriptName.of(script);
		final String name = scriptName.getName();
		final String projectName = scriptName.getProjectName();
		sqlHelper.executeUpdate(sql, description, developerName, ignored, filename, name, projectName, durationMillis);
	}

	private Collection<ScriptName> dbRetrieveAll()
	{

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Collection<ScriptName> scriptNames = sqlHelper.<ScriptName> retrieveRecords()
				.sql("SELECT ProjectName, Name FROM AD_MigrationScript")
				.collectionFactory(HashSet::new)
				.rowLoader(rs -> ScriptName.ofProjectNameAndName(rs.getString("ProjectName"), rs.getString("Name")))
				.execute();
		stopwatch.stop();

		logger.info("Loaded {} registry entries from database in {}", scriptNames.size(), stopwatch);
		return scriptNames;
	}

	@Value
	private static class ScriptName
	{
		public static ScriptName of(final IScript script)
		{
			final String projectName = script.getProjectName();
			if (projectName == null)
			{
				throw new IllegalArgumentException("No projectName was set for " + script);
			}

			final String fileName = script.getFileName();
			final String name = projectName + "->" + fileName;

			return new ScriptName(projectName, name);
		}

		public static ScriptName ofProjectNameAndName(final String projectName, final String name)
		{
			return new ScriptName(projectName, name);
		}

		String projectName;
		String name;

		private ScriptName(final String projectName, final String name)
		{
			this.projectName = projectName;
			this.name = name;
		}

	}
}
