package de.metas.migration.cli;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.metas.migration.cli.Config.ConfigBuilder;
import lombok.NonNull;
import mockit.Expectations;
import mockit.Injectable;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class RolloutMigrateTests
{

	@Injectable
	private DirectoryChecker directoryChecker;

	@Injectable
	private PropertiesFileLoader propertiesFileLoader;

	@Injectable
	private SettingsLoader settingsLoader;

	@Injectable
	private RolloutVersionLoader rolloutVersionLoader;

	@Injectable
	private DBConnectionMaker dbConnectionMaker;

	@Injectable
	private DBVersionGetter dbVersionGetter;

	@Injectable
	private VersionChecker versionChecker;

	@Injectable
	private MigrationScriptApplier migrationScriptApplier;

	private ConfigBuilder configBuilder;

	@Before
	public void init()
	{
		configBuilder = Config.builder()
				.canRun(true)
				.rolloutDirName("rolloutDirName");
	}

	/**
	 * Run with dbVersion < rolloutVersion; expect the {@link MigrationScriptApplier}to be run.
	 */
	@Test
	public void testRollout0ExpectScriptsApplied()
	{
		final String dbVersion = "1.1.1-23+master";
		final boolean expectApplyMigrationScripts = true;

		performTestWithDefaultconfig(dbVersion, expectApplyMigrationScripts);
	}

	/**
	 * Run with dbVersion == rolloutVersion; expect the {@link MigrationScriptApplier} <b>not</b> to be run.
	 */
	@Test
	public void testRollout0ExpectScriptsNotApplied()
	{
		final String dbVersion = "1.1.1-24+master";
		final boolean expectApplyMigrationScripts = false;

		performTestWithDefaultconfig(dbVersion, expectApplyMigrationScripts);
	}

	/**
	 * Run with dbVersion > rolloutVersion; expect an exception.
	 */
	@Test(expected = VersionChecker.InconsistentVersionsException.class)
	public void testRollout0InconsistenVersion()
	{
		final String dbVersion = "1.1.1-25+master";
		final boolean expectApplyMigrationScripts = false;

		performTestWithDefaultconfig(dbVersion, expectApplyMigrationScripts);
	}

	/**
	 * Run with dbVersion > rolloutVersion but the config is set to ignore the error and act as if dbVersion == rolloutVersion.
	 */
	@Test
	public void testRollout0InconsistenVersionNoError()
	{
		final String dbVersion = "1.1.1-25+master";
		final boolean expectApplyMigrationScripts = false;

		configBuilder.failIfRolloutIsGreaterThanDB(false);

		performTest(configBuilder.build(),
				dbVersion,
				expectApplyMigrationScripts);
	}

	/**
	 * Run with dbVersion == rolloutVersion set the config to not check the dbVersion and therefore run {@link MigrationScriptApplier}.
	 */
	@Test
	public void testRolloutNoVersionCheck()
	{
		final String dbVersion = "1.1.1-24+master";
		final boolean expectApplyMigrationScripts = true;

		configBuilder.checkVersions(false);

		performTest(configBuilder.build(),
				dbVersion,
				expectApplyMigrationScripts);
	}

	private void performTestWithDefaultconfig(
			@NonNull final String dbVersion,
			final boolean expectApplyMigrationScripts)
	{
		final Config config = configBuilder.build();
		performTest(config, dbVersion, expectApplyMigrationScripts);
	}

	private void performTest(
			@NonNull final Config config,
			@NonNull final String dbVersion,
			final boolean expectApplyMigrationScripts)
	{

		final Properties properties = new Properties();
		final String dbName = "dbName";
		properties.setProperty(Settings.PROP_DB_NAME, dbName);
		final Settings settings = new Settings(properties);

		// @formatter:off
		new Expectations()
		{{
			settingsLoader.loadSettings(config); result = settings;
			rolloutVersionLoader.loadRolloutVersionString(config.getRolloutDirName()); result = "1.1.1-24+master";
			dbVersionGetter.retrieveDBVersion(settings, dbName); result = dbVersion;
			migrationScriptApplier.applyMigrationScripts(config, settings, dbName); times = (expectApplyMigrationScripts ? 1 : 0);
		}};
		// @formatter:on

		new RolloutMigrate(
				directoryChecker,
				propertiesFileLoader,
				settingsLoader,
				rolloutVersionLoader,
				dbConnectionMaker,
				dbVersionGetter,
				migrationScriptApplier)
						.run0(config);
	}
}
