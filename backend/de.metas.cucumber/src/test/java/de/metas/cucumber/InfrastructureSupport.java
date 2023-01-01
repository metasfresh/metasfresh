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

import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;

public class InfrastructureSupport
{
	private final static transient Logger logger = LogManager.getLogger(InfrastructureSupport.class);
	
	/**
	 * Can be set when running/developing cucumber-tests locally.
	 * If set, then cucumber runs against your local DB, and not an ephemeral DB-image.
	 * The benefits are:
	 * - cucumber startup time is reduced drastically
	 * - it's easier to inspect the local DB. In fact you can start the webapi (not ServerRoot aka app-server) and the frontend, and inspect everything in the UI.
	 * <p>
	 * The drawback is that your DB is probably polluted which might be an additional reason for possible test failures.
	 * To always run your cucumber-tests on an "unpolluted" DB, 
	 * you can use (with git bash) the three shell scripts from {@code backend/de.metas.cucumber/dev-support}.
	 */
	public static final String ENV_DB_PORT_OF_EXTERNALLY_RUNNING_POSTGRESQL = "CUCUMBER_DB_PORT_OF_EXTERNALLY_RUNNING_POSTGRESQL";

	@Getter
	private boolean runAgainstDockerizedDatabase = true;
	
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
	private boolean started = false;

	public void start()
	{
		assertThat(started).isFalse(); // guard

		cucumberIsUsingProvidedInfrastructure = StringUtils.toBoolean(System.getenv("CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE"), false);

		// note that this will only matter if CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE is false
		final int dbPortFromEnvVar = StringUtils.toIntegerOrZero(System.getenv(ENV_DB_PORT_OF_EXTERNALLY_RUNNING_POSTGRESQL));
		runAgainstDockerizedDatabase = dbPortFromEnvVar == 0; // if a DB port was provided, it means that we want to run against an externally provided DB

		dbPort = CoalesceUtil.firstGreaterThanZero(
				dbPortFromEnvVar,
				5432);

		// TODO replace runAgainstDockerizedDatabase and cucumberIsUsingProvidedInfrastructure with an enum
		if (cucumberIsUsingProvidedInfrastructure)
		{
			logger.info("using provided infrastructure, not starting any containers");

			dbHost = "db";
			// dbPort = 5432; was already set
			rabbitHost = "rabbitmq";
			rabbitPort = 5672;
			rabbitUser = "metasfresh";
			rabbitPassword = "metasfresh";

			started = true;
			return;
		}

		final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.7.4");
		rabbitMQContainer.start();

		rabbitPort = rabbitMQContainer.getAmqpPort();
		rabbitHost = rabbitMQContainer.getHost();
		rabbitUser = rabbitMQContainer.getAdminUsername();
		rabbitPassword = rabbitMQContainer.getAdminPassword();

		if (runAgainstDockerizedDatabase)
		{
			// this image is from release-branch 2021-09-15. it is failrly old, 
			// such that our local miration-scripts will be applied and no later scripts from other branches are already in this image 
			final String fullImageName = "metasfresh/metasfresh-db:5.174.2_461_release";
			logger.info("Start dockerized metasfresh-db {}", fullImageName);

			// the DB needs to be populated
			final GenericContainer<?> db = new GenericContainer<>(DockerImageName.parse(fullImageName))
					//.withImagePullPolicy(PullPolicy.alwaysPull()) // needed then going with e.g. "latest"
					.withEnv("POSTGRES_PASSWORD", "password")
					.withStartupTimeout(Duration.ofMinutes(3)) // the DB needs to be populated
					.withExposedPorts(5432);
			db.start();

			dbHost = db.getHost();
			dbPort = db.getFirstMappedPort();
			logger.info("dockerized metasfresh-db {} runs at {}:{}", fullImageName, dbHost, dbPort);
		}
		else
		{
			dbHost = "localhost";
			// dbPort = 5432; was already set
			logger.info("Assume metasfresh-db already runs at {}:{}", dbHost, dbPort);
		}
		started = true;
	}
}
