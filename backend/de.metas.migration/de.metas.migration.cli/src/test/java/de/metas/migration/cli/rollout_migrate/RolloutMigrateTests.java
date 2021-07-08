package de.metas.migration.cli.rollout_migrate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.migration.cli.rollout_migrate.RolloutMigrationConfig.RolloutMigrationConfigBuilder;
import lombok.NonNull;

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
	private RolloutMigrationConfigBuilder configBuilder;

	@BeforeEach
	public void beforeEach()
	{
		configBuilder = RolloutMigrationConfig.builder()
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
	@Test
	public void testRollout0InconsistenVersion()
	{
		final String dbVersion = "1.1.1-25+master";
		final boolean expectApplyMigrationScripts = false;

		assertThatThrownBy(() -> performTestWithDefaultconfig(dbVersion, expectApplyMigrationScripts))
				.isInstanceOf(VersionChecker.InconsistentVersionsException.class);
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
		final RolloutMigrationConfig config = configBuilder.build();
		performTest(config, dbVersion, expectApplyMigrationScripts);
	}

	private void performTest(
			@NonNull final RolloutMigrationConfig config,
			@NonNull final String dbVersion,
			final boolean expectApplyMigrationScripts)
	{
		final Properties properties = new Properties();
		final String dbName = "dbName";
		properties.setProperty(DBConnectionSettingProperties.PROP_DB_NAME, dbName);
		final DBConnectionSettingProperties settings = new DBConnectionSettingProperties(properties);

		final DirectoryChecker directoryChecker = new DirectoryChecker();

		final SettingsLoader settingsLoader = Mockito.mock(SettingsLoader.class);
		Mockito.doReturn(settings).when(settingsLoader).loadSettings(config);

		final RolloutVersionLoader rolloutVersionLoader = Mockito.mock(RolloutVersionLoader.class);
		Mockito.doReturn("1.1.1-24+master").when(rolloutVersionLoader).loadRolloutVersionString(config.getRolloutDirName());

		final DBVersionGetter dbVersionGetter = Mockito.mock(DBVersionGetter.class);
		Mockito.doReturn(dbVersion).when(dbVersionGetter).retrieveDBVersion(settings.toDBConnectionSettings(), dbName);

		final MigrationScriptApplier migrationScriptApplier = Mockito.mock(MigrationScriptApplier.class);

		final PropertiesFileLoader propertiesFileLoader = Mockito.mock(PropertiesFileLoader.class);

		final DBConnectionMaker dbConnectionMaker = Mockito.mock(DBConnectionMaker.class);

		final RolloutMigrate rolloutMigrate = new RolloutMigrate(
				directoryChecker,
				propertiesFileLoader,
				settingsLoader,
				rolloutVersionLoader,
				dbConnectionMaker,
				dbVersionGetter,
				migrationScriptApplier)
		{
			protected void updateDbVersion(de.metas.migration.IDatabase db, String versionStr, String additionalMetaDataSuffix)
			{
				// do nothing
			}
		};

		rolloutMigrate.run0(config);

		Mockito.verify(migrationScriptApplier, Mockito.times(expectApplyMigrationScripts ? 1 : 0))
				.applyMigrationScripts(config, settings.toDBConnectionSettings(), dbName);

	}
}
