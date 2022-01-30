/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.logging.LogManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class InfrastructureSupport
{
	private final static transient Logger logger = LogManager.getLogger(InfrastructureSupport.class);

	/**
	 * Can be changed to {@code false} when running/developing cucumber-tests locally.
	 * If {@code false}, then cucumber runs against your local DB, and not an ephemeral DB-image.
	 * The benefits are:
	 * - cucmber startup time is reduced drastically
	 * - it's easier to inspect the local DB. In fact you can start the webapi (not ServerRoot aka app-server) and the frontend, and inspect everything in the UI.
	 * 
	 * The drawback is that your DB is probably polluted which might be an additional reason for possible test failures.
	 * To always run your cucumber-tests on an "unpolluted" DB, you can use templates as follows:
	 * 
	 * Reset your local infrastructure-DB
	 * Apply the local migration scripts
	 * Make sure there is no open connection to the DB (otherwise there will be an error)
	 * Turn the up2date-vanilla-DB into a template:
	 * <pre>
	 * docker exec -it infrastructure_db_1  psql -U postgres -c "alter database metasfresh rename to metasfresh_template_master_integration;" && \
	 * docker exec -it infrastructure_db_1  psql -U postgres -c "alter database metasfresh_template_master_integration is_template true;"
	 * </pre>
	 * 
	 * Now, you can reset your local DB after each cucumbrer run like this:  
	 * <pre>
	 * # drop the current metasfresh-DB and recreate it from the template
	 * docker exec -it infrastructure_db_1  psql -U postgres -c "drop database if exists metasfresh;" && \
	 * docker exec -it infrastructure_db_1  psql -U postgres -c "create database metasfresh template metasfresh_template_master_integration;"
	 * </pre>
	 */
	@Getter
	private final boolean runAgainstDockerizedDatabase = false;

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
			final String fullImageName = "metasfresh/metasfresh-db:5.172.2_380_release";
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
			dbPort = 5432;
			logger.info("Assume metasfresh-db already runs at {}:{}", dbHost, dbPort);
		}
		started = true;
	}
}
