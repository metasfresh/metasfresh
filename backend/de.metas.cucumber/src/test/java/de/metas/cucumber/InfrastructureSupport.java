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
import de.metas.util.StringUtils;
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

	// TODO replace runAgainstDockerizedDatabase and cucumberIsUsingProvidedInfrastructure with an enum
        if (cucumberIsUsingProvidedInfrastructure) {
            logger.info("using provided infrasstructure, not starting any containers");

            runAgainstDockerizedDatabase = false;

            dbHost = "db";
            dbPort = 5432;
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
