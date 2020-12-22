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

import lombok.Getter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class MetasfreshDBSupport
{
	private static GenericContainer db;

	@Getter
	private static String host;

	@Getter
	private static Integer port;

	public void startMetasfreshDB()
	{
		db = new GenericContainer(DockerImageName.parse("metasfresh/metasfresh-db:latest"))
				.withEnv("POSTGRES_PASSWORD", "password")
				.withStartupTimeout(Duration.ofMinutes(3)) // the DB needs to be populated
				.withExposedPorts(5432);
		db.start();

		host = db.getHost();
		port = db.getFirstMappedPort();
	}
}
