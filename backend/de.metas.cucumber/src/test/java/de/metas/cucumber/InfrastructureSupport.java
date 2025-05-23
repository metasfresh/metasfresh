/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber;

import ch.qos.logback.classic.Level;
import com.google.common.base.Stopwatch;
import de.metas.logging.LogManager;
import de.metas.migration.cli.workspace_migrate.WorkspaceMigrateConfig;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InfrastructureSupport
{
	private final static ILoggable loggable = Loggables.withLogger(
			Loggables.console("InfrastructureSupport"),
			LogManager.getLogger(InfrastructureSupport.class),
			Level.INFO);

	// keep in sync when moving cucumber OR the file {@code backend/.workspace-sql-scripts.properties}
	public static final String RELATIVE_PATH_TO_METASFRESH_ROOT = "../..";

	/**
	 * Can be set when running/developing cucumber-tests locally.
	 * If set, then cucumber runs against your local DB, and not an ephemeral DB-image.
	 * The benefits are:
	 * - cucumber startup time is reduced drastically
	 * - it's easier to inspect the local DB. In fact you can start the webapi (not ServerRoot aka app-server) and the frontend, and inspect everything in the UI.
	 * <p>
	 * The drawback is that your DB is probably polluted which might be an additional reason for possible test failures.
	 * To always run your cucumber-tests on an "unpolluted" DB,
	 * you can use (with git bash) the shell scripts from {@code misc/dev-support/docker/infrastructure/scripts}.
	 */
	public static final String ENV_EXTERNALLY_RUNNING_POSTGRESQL_HOST = "CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_HOST";
	public static final String ENV_EXTERNALLY_RUNNING_POSTGRESQL_PORT = "CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_PORT";

	public static final String ENV_EXTERNALLY_RUNNING_POSTGREST_HOST = "CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_HOST";
	public static final String ENV_EXTERNALLY_RUNNING_POSTGREST_PORT = "CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_PORT";

	public static final String ENV_EXTERNALLY_RUNNING_RABBITMQ_HOST = "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_HOST";
	public static final String ENV_EXTERNALLY_RUNNING_RABBITMQ_PORT = "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_PORT";
	public static final String ENV_EXTERNALLY_RUNNING_RABBITMQ_USER = "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_USER";
	public static final String ENV_EXTERNALLY_RUNNING_RABBITMQ_PASS = "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_PASS";

	@Getter
	private boolean cucumberIsUsingProvidedInfrastructure;

	@Getter
	private String dbHost;

	@Getter
	private Integer dbPort;

	@Getter
	private String rabbitHost;

	@Getter
	private Integer rabbitPort;

	@Getter
	private String rabbitUser;

	@Getter
	private String rabbitPassword;

	@Getter
	private String postgRESTHost;

	@Getter
	private Integer postgRESTPort;

	@Getter
	private boolean started = false;

	public void start()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		assertThat(started).isFalse(); // guard

		cucumberIsUsingProvidedInfrastructure = StringUtils.toBoolean(getEnvOpt("CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE").orElse("false"));

		startRabbitMQ();
		startDatabase();
		startPostgREST();

		Loggables.setDebuggingLoggable(CucumberLoggable.instance);

		started = true;

		stopwatch.stop();
		loggable.addLog("Cucumber infrastructure started in {}", stopwatch);
	}

	private void startRabbitMQ()
	{
		if (cucumberIsUsingProvidedInfrastructure)
		{
			rabbitHost = getEnv(ENV_EXTERNALLY_RUNNING_RABBITMQ_HOST);
			rabbitPort = getEnvInt(ENV_EXTERNALLY_RUNNING_RABBITMQ_PORT);
			rabbitUser = getEnv(ENV_EXTERNALLY_RUNNING_RABBITMQ_USER);
			rabbitPassword = getEnv(ENV_EXTERNALLY_RUNNING_RABBITMQ_PASS);
			loggable.addLog("Using provided RabbitMQ: host={}, port={} user={}", rabbitHost, rabbitPort, rabbitUser, rabbitPassword);
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		//noinspection resource
		final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.7.4");
		rabbitMQContainer.start();

		rabbitPort = rabbitMQContainer.getAmqpPort();
		rabbitHost = rabbitMQContainer.getHost();
		rabbitUser = rabbitMQContainer.getAdminUsername();
		rabbitPassword = rabbitMQContainer.getAdminPassword();

		stopwatch.stop();
		loggable.addLog("Started RabbitMQ in {}", stopwatch);
	}

	private void startDatabase()
	{
		if (cucumberIsUsingProvidedInfrastructure)
		{
			dbHost = getEnv(ENV_EXTERNALLY_RUNNING_POSTGRESQL_HOST);
			dbPort = getEnvInt(ENV_EXTERNALLY_RUNNING_POSTGRESQL_PORT);
			loggable.addLog("Using provided postgresql: host={}, port={}", dbHost, dbPort);
			return;
		}

		startDBAndApplyMigrationScripts();
	}

	private void startDBAndApplyMigrationScripts()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		// this image is from release-branch 2021-09-15. it is fairly old,
		// such that our local miration-scripts will be applied and no later scripts from other branches are already in this image
		final String fullImageName = "metasfresh/metasfresh-db:5.175.2_559_release";
		loggable.addLog("Start dockerized metasfresh-db {}", fullImageName);

		// the DB needs to be populated
		//noinspection resource
		final GenericContainer<?> db = new GenericContainer<>(DockerImageName.parse(fullImageName))
				//.withImagePullPolicy(PullPolicy.alwaysPull()) // needed then going with e.g. "latest"
				.withEnv("POSTGRES_PASSWORD", "password")
				.withStartupTimeout(Duration.ofMinutes(3)) // the DB needs to be populated
				.withExposedPorts(5432);
		db.start();

		dbHost = db.getHost();
		dbPort = db.getFirstMappedPort();
		loggable.addLog("dockerized metasfresh-db {} runs at {}:{} (took {})", fullImageName, dbHost, dbPort, stopwatch);

		stopwatch.reset();
		// apply our local migration scripts to get our DB up to date
		de.metas.migration.cli.workspace_migrate.Main.main(
				WorkspaceMigrateConfig.builder()
						.workspaceDir(new File(RELATIVE_PATH_TO_METASFRESH_ROOT))
						.onScriptFailure(WorkspaceMigrateConfig.OnScriptFailure.FAIL)
						.dbUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/metasfresh")
						.build());
		loggable.addLog("Applied migration scripts (took {})", stopwatch);

		// apply our local migration scripts to get our DB up to date
		final File workspaceDir = new File(RELATIVE_PATH_TO_METASFRESH_ROOT);
		final WorkspaceMigrateConfig migrateConfig = WorkspaceMigrateConfig.builder()
				.workspaceDir(workspaceDir)
				.onScriptFailure(WorkspaceMigrateConfig.OnScriptFailure.FAIL)
				.dbUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/metasfresh")
				.build();
		de.metas.migration.cli.workspace_migrate.Main.main(migrateConfig);
	}

	private void startPostgREST()
	{
		if (cucumberIsUsingProvidedInfrastructure)
		{
			postgRESTHost = getEnv(ENV_EXTERNALLY_RUNNING_POSTGREST_HOST);
			postgRESTPort = getEnvInt(ENV_EXTERNALLY_RUNNING_POSTGREST_PORT);
			loggable.addLog("Using provided postgREST: host={}, port={}", postgRESTHost, postgRESTPort);
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final String fullImageName = "postgrest/postgrest:latest";

		final GenericContainer<?> postgREST = new GenericContainer<>(DockerImageName.parse(fullImageName))
				.withImagePullPolicy(PullPolicy.alwaysPull()) // needed since we go with "latest"
				.withEnv("PGRST_DB_URI", "postgres://metasfresh:metasfresh@" + dbHost + ":" + dbPort + "/metasfresh")
				.withEnv("PGRST_DB_SCHEMA", "public")
				.withEnv("PGRST_DB_ANON_ROLE", "metasfresh")
				.withStartupTimeout(Duration.ofMinutes(1))
				.withExposedPorts(3000);

		postgRESTHost = postgREST.getHost();
		postgRESTPort = postgREST.getFirstMappedPort();
		loggable.addLog("dockerized postgREST {} runs at {}:{} (took {})", fullImageName, dbHost, dbPort, stopwatch);
	}

	private static Integer getEnvInt(@NonNull final String variableName)
	{
		return Integer.parseInt(getEnv(variableName));
	}

	private static String getEnv(@NonNull final String variableName)
	{
		return getEnvOpt(variableName).orElseThrow(() -> new RuntimeException("Missing environment variable " + variableName + " for cucumber-tests!"));
	}

	private static Optional<String> getEnvOpt(@NonNull final String variableName)
	{
		return StringUtils.trimBlankToOptional(System.getenv(variableName));
	}
}
