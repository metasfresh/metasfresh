/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.migration.cli.workspace_migrate;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.migration.cli.workspace_migrate.WorkspaceMigrateConfig.OnScriptFailure;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.ZonedDateTime;

public class Main
{
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static final String PROP_WORKSPACE = "workspace";

	public static void main(final String[] args)
	{
		final WorkspaceMigrateConfig config = getConfig();
		main(config);
	}

	public static void main(@NonNull final WorkspaceMigrateConfig config)
	{
		System.out.println("WorkspaceMigrateConfig: " + config);

		final WorkspaceScriptsApplier applier = new WorkspaceScriptsApplier(config);

		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			applier.run();
		}
		finally
		{
			stopwatch.stop();
			logger.info("Done in {} at {}", stopwatch, ZonedDateTime.now());
		}
	}

	private static WorkspaceMigrateConfig getConfig()
	{
		return WorkspaceMigrateConfig.builder()
				.workspaceDir(getWorkspaceDir())
				.dbUrl(getMandatoryProperty("db.url", WorkspaceMigrateConfig.PROP_DB_URL_DEFAULT))
				.dbUsername(getMandatoryProperty("db.username", WorkspaceMigrateConfig.PROP_DB_USERNAME_DEFAULT))
				.dbPassword(getMandatoryProperty("db.password", WorkspaceMigrateConfig.PROP_DB_PASSWORD_DEFAULT))
				.dryRunMode(getBooleanProperty("dryRunMode", false))
				.skipExecutingAfterScripts(getBooleanProperty("skipExecutingAfterScripts", false))
				.onScriptFailure(OnScriptFailure.valueOf(getMandatoryProperty("onScriptFailure", OnScriptFailure.ASK.toString())))
				.labels(getLabels())
				.build();
	}

	private static File getWorkspaceDir()
	{
		final String workspaceDirname = System.getProperty(PROP_WORKSPACE);
		if (!isBlank(workspaceDirname))
		{
			return new File(workspaceDirname);
		}

		try
		{
			final File cwd = new File(".").getCanonicalFile();
			logger.warn("Using current working directory as workspace folder: {}. Run JVM with '-D{}=...' to override it.", cwd, PROP_WORKSPACE);
			return cwd;
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed getting CWD", ex);
		}
	}

	private static ImmutableSet<Label> getLabels()
	{
		final String labelsStr = getMandatoryProperty("labels", WorkspaceMigrateConfig.PROP_LABELS_DEFAULT);
		return Label.ofCommaSeparatedString(labelsStr);
	}

	private static String getMandatoryProperty(final String name, final String defaultValue)
	{
		final String systemPropertyValue = System.getProperty(name);
		if (!isBlank(systemPropertyValue))
		{
			return systemPropertyValue;
		}

		final String evnVarValue = System.getenv(name);
		if (!isBlank(evnVarValue))
		{
			return evnVarValue;
		}

		if (!isBlank(defaultValue))
		{
			logger.info("Considering default config: {}={}. To override it start JVM with '-D{}=...' OR set environment-variable '{}=...'.", name, defaultValue, name, name);
			return defaultValue;
		}

		throw new RuntimeException("Property '" + name + "' was not set. "
										   + "\n Please start JVM with '-D" + name + "=...'" 
										   + " OR set environment-variable '" + name + "=...'.");
	}

	private static boolean getBooleanProperty(final String name, final boolean defaultValue)
	{
		final String systemPropertyValue = System.getProperty(name);
		if (!isBlank(systemPropertyValue))
		{
			return Boolean.parseBoolean(systemPropertyValue.trim());
		}

		final String evnVarValue = System.getenv(name);
		if (!isBlank(evnVarValue))
		{
			return Boolean.parseBoolean(evnVarValue);
		}

		logger.info("Considering default config: {}={}. To override it start JVM with '-D{}=...' OR set environment-variable '{}=...'.", name, defaultValue, name, name);
		return defaultValue;
	}

	private static boolean isBlank(final String str)
	{
		return str == null || str.trim().isEmpty();
	}
}
