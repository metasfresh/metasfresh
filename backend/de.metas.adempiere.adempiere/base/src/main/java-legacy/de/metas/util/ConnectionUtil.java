/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.util;

import de.metas.CommandLineParser.CommandLineOptions;
import de.metas.logging.LogManager;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.compiere.db.CConnection;
import org.compiere.db.CConnectionAttributes;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesce;

@UtilityClass
public class ConnectionUtil
{
	private final static transient Logger logger = LogManager.getLogger(ConnectionUtil.class);

	/**
	 * Can set up the database and rabbitmq connection at an early state, from commandline parameters.
	 * It's assumed that if this method does not set up the connection, it is done later (currently via {@link CConnection#createInstance(CConnectionAttributes)}).
	 *
	 * Background: we use this to start metasfresh without a {@code }metasfresh.properties} file.
	 */
	public ConfigureConnectionsResult configureConnectionsIfArgsProvided(@Nullable final CommandLineOptions commandLineOptions)
	{
		if (commandLineOptions == null)
		{
			return new ConfigureConnectionsResult(false);
		}

		// CConnection
		ConfigureConnectionsResult result;
		if (Check.isNotBlank(commandLineOptions.getDbHost()) || commandLineOptions.getDbPort() != null)
		{
			final CConnectionAttributes connectionAttributes = CConnectionAttributes.builder()
					.dbHost(commandLineOptions.getDbHost())
					.dbPort(commandLineOptions.getDbPort())
					.dbName(coalesce(commandLineOptions.getDbName(), "metasfresh"))
					.dbUid(coalesce(commandLineOptions.getDbUser(), "metasfresh"))
					.dbPwd(coalesce(commandLineOptions.getDbPassword(), "metasfresh"))
					.build();

			logger.info("!!!!!!!!!!!!!!!!\n"
					+ "!! dbHost and/or dbPort were set from cmdline; -> will ignore DB-Settings from metasfresh.properties and connect to DB with {}\n"
					+ "!!!!!!!!!!!!!!!!", connectionAttributes);
			CConnection.createInstance(connectionAttributes);

			result = new ConfigureConnectionsResult(true);
		}
		else
		{
			result = new ConfigureConnectionsResult(false);
		}

		// RabbitMQ
		if (Check.isNotBlank(commandLineOptions.getRabbitHost()))
		{
			System.setProperty("spring.rabbitmq.host", commandLineOptions.getRabbitHost());
		}
		if (commandLineOptions.getRabbitPort() != null)
		{
			System.setProperty("spring.rabbitmq.port", Integer.toString(commandLineOptions.getRabbitPort()));
		}
		if (Check.isNotBlank(commandLineOptions.getRabbitUser()))
		{
			System.setProperty("spring.rabbitmq.username", commandLineOptions.getRabbitUser());
		}
		if (Check.isNotBlank(commandLineOptions.getRabbitPassword()))
		{
			System.setProperty("spring.rabbitmq.password", commandLineOptions.getRabbitPassword());
		}
		return result;
	}

	@Value
	public static class ConfigureConnectionsResult
	{
		boolean cconnectionConfigured;
	}
}
