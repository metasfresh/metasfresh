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

package de.metas;

import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javax.annotation.Nullable;

@UtilityClass
public class CommandLineParser
{
	public CommandLineOptions parse(@Nullable final String[] args)
	{
		final Options options = createOptions();

		final org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
		try
		{
			final CommandLine cmd = parser.parse(options, args);
			final String dbHost = cmd.getOptionValue("dbHost");
			final String dbPort = cmd.getOptionValue("dbPort");

			return CommandLineOptions.builder()
					.dbHost(dbHost)
					.dbPort(NumberUtils.asInteger(dbPort, null))
					.dbName(cmd.getOptionValue("dbName"))
					.dbUser(cmd.getOptionValue("dbUser"))
					.dbPassword(cmd.getOptionValue("dbPassword"))

					.rabbitHost(cmd.getOptionValue("rabbitHost"))
					.rabbitPort(NumberUtils.asInteger(cmd.getOptionValue("rabbitPort"), null))
					.rabbitUser(cmd.getOptionValue("rabbitUser"))
					.rabbitPassword(cmd.getOptionValue("rabbitPassword"))
					.build();
		}
		catch (final ParseException e)
		{
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("You can invoke the main method with these parameters", options);

			throw new RuntimeException(e);
		}
	}

	private static Options createOptions()
	{
		final Options options = new Options();
		// Help
		{
			final Option option = new Option("h", "Print this (h)elp message and exit");
			option.setArgs(0);
			option.setArgName("Help");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbHost", "");
			option.setArgs(1);
			option.setArgName("Database host name");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbPort", "");
			option.setArgs(1);
			option.setArgName("Database port number");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbName", "");
			option.setArgs(1);
			option.setArgName("Database name");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbUser",
					"");
			option.setArgs(1);
			option.setArgName("Database user name");
			option.setRequired(false);
			options.addOption(option);
		}
		{
			final Option option = new Option("dbPassword", "");
			option.setArgs(1);
			option.setArgName("Database password");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("rabbitHost", "");
			option.setArgs(1);
			option.setArgName("RabbitMQ host name");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("rabbitPort", "");
			option.setArgs(1);
			option.setArgName("RabbitMQ port number");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("rabbitUser",
					"");
			option.setArgs(1);
			option.setArgName("RabbitMQ user name");
			option.setRequired(false);
			options.addOption(option);
		}
		{
			final Option option = new Option("rabbitPassword", "");
			option.setArgs(1);
			option.setArgName("RabbitMQ password");
			option.setRequired(false);
			options.addOption(option);
		}

		return options;
	}

	@Builder
	@Value
	@ToString(exclude = { "dbPassword" })
	public static class CommandLineOptions
	{
		@Nullable
		String dbHost;

		@Nullable
		Integer dbPort;

		@Nullable
		String dbName;

		@Nullable
		String dbUser;

		@Nullable
		String dbPassword;

		@Nullable
		String rabbitHost;

		@Nullable
		Integer rabbitPort;

		@Nullable
		String rabbitUser;

		@Nullable
		String rabbitPassword;
	}
}
