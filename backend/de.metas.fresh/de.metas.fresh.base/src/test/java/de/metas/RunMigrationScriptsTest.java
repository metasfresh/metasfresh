/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas;

import com.google.common.base.Stopwatch;
import de.metas.logging.LogManager;
import de.metas.migration.cli.workspace_migrate.DirectoryProperties;
import de.metas.migration.cli.workspace_migrate.WorkspaceMigrateConfig;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RunMigrationScriptsTest
{
	private static final Logger logger = LogManager.getLogger(RunMigrationScriptsTest.class);

	public static final String ENV_SKIP_MIGRATION_SCRIPTS_TEST = "SKIP_MIGRATION_SCRIPTS_TEST";

	public static final String ENV_WORKSPACE_DIR = "WORKSPACE_DIR";
	public static final String ENV_DB_IMAGE_NAME = "DB_IMAGE_NAME";
	private static final PropertyValue<DockerImageName> DB_IMAGE_NAME_DEFAULT = PropertyValue.of(
			DockerImageName.parse("metasfresh/metasfresh-db:5.175.2_559_release"),
			"default");

	@Test
	void test()
	{
		Assumptions.assumeFalse(isSkipTest(), "`" + ENV_SKIP_MIGRATION_SCRIPTS_TEST + "` system property/env is not set");

		final Config config = getConfig();
		logger.info("Using configuration: {}", config);

		try (final GenericContainer<?> db = startDatabaseContainer(config.getDbImageName().getValue()))
		{
			runMigrationScripts(config.getWorkspaceDir().getValue(), db);
		}
	}

	@NonNull
	private static GenericContainer<?> startDatabaseContainer(final DockerImageName imageName)
	{
		logger.info("Starting dockerized metasfresh database {}...", imageName);
		final Stopwatch stopwatch = Stopwatch.createStarted();

		//noinspection resource
		final GenericContainer<?> db = new GenericContainer<>(imageName)
				//.withImagePullPolicy(PullPolicy.alwaysPull()) // needed then going with e.g. "latest"
				.withEnv("POSTGRES_PASSWORD", "password")
				.withStartupTimeout(Duration.ofMinutes(3)) // the DB needs to be populated
				.withExposedPorts(5432);
		db.start();

		stopwatch.stop();
		logger.info("Started `{}` in {}", imageName, stopwatch);

		return db;
	}

	private static void runMigrationScripts(final File workspaceDir, final GenericContainer<?> db)
	{
		final String dbHost = db.getHost();
		final Integer dbPort = db.getFirstMappedPort();

		final WorkspaceMigrateConfig migrateConfig = WorkspaceMigrateConfig.builder()
				.workspaceDir(workspaceDir)
				.onScriptFailure(WorkspaceMigrateConfig.OnScriptFailure.FAIL)
				.dbUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/metasfresh")
				.build();

		de.metas.migration.cli.workspace_migrate.Main.main(migrateConfig);
	}

	private static boolean isSkipTest()
	{
		//
		// Check System properties
		{
			final String value = StringUtils.trimBlankToNull(System.getProperty(ENV_SKIP_MIGRATION_SCRIPTS_TEST));
			if (value != null)
			{
				return StringUtils.toBoolean(value);
			}
		}

		//
		// Check System env
		{
			final String value = StringUtils.trimBlankToNull(System.getenv(ENV_SKIP_MIGRATION_SCRIPTS_TEST));
			if (value != null)
			{
				return StringUtils.toBoolean(value);
			}
		}

		//
		// Default: don't skip
		return false;
	}

	private static Config getConfig()
	{
		try
		{
			return Config.builder()
					.workspaceDir(getWorkspaceDir())
					.dbImageName(getDbImageName())
					.build();
		}
		catch (AdempiereException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed retrieving config", ex);
		}

	}

	private static PropertyValue<DockerImageName> getDbImageName()
	{
		final String envDBImageName = StringUtils.trimBlankToNull(System.getenv(ENV_DB_IMAGE_NAME));
		if (envDBImageName != null)
		{
			return PropertyValue.of(DockerImageName.parse(envDBImageName), "env " + ENV_DB_IMAGE_NAME);
		}

		return DB_IMAGE_NAME_DEFAULT;
	}

	private static PropertyValue<File> getWorkspaceDir() throws IOException
	{
		//
		// Check environment
		{
			final String workspaceDirStr = StringUtils.trimBlankToNull(System.getenv(ENV_WORKSPACE_DIR));
			if (workspaceDirStr != null)
			{
				final File workspaceDir = new File(workspaceDirStr).getCanonicalFile();
				return PropertyValue.of(workspaceDir, "env " + ENV_WORKSPACE_DIR);
			}
		}

		//
		// Auto-detect workspace directory
		//
		final File cwd = new File(".").getCanonicalFile();
		{
			File workspaceDir = null;
			for (File dir = cwd; dir != null; dir = dir.getParentFile())
			{

				final File propertiesFile = new File(dir, DirectoryProperties.PROPERTIES_FILENAME);
				if (propertiesFile.exists())
				{
					workspaceDir = dir;
				}
			}

			if (workspaceDir != null)
			{
				return PropertyValue.of(workspaceDir, "auto-detected");
			}
		}

		throw new AdempiereException("Cannot detect workspace directory."
				+ "\n Option 1. Pls make sure one of the ancestor of " + cwd + " contains `" + DirectoryProperties.PROPERTIES_FILENAME + "` file."
				+ " That directory will be considered the workspace dir"
				+ "\n Option 2. Set `" + ENV_WORKSPACE_DIR + "` environment variable."
		);
	}

	//
	//
	//
	//
	//

	@Value(staticConstructor = "of")
	private static class PropertyValue<T>
	{
		@NonNull T value;
		@Nullable String sourceName;

		@Override
		public String toString()
		{
			String str = "" + value;
			if (sourceName != null)
			{
				str += " (from " + sourceName + ")";
			}
			return str;
		}
	}

	@Value
	@Builder
	private static class Config
	{
		@NonNull PropertyValue<File> workspaceDir;
		@NonNull PropertyValue<DockerImageName> dbImageName;
	}
}
