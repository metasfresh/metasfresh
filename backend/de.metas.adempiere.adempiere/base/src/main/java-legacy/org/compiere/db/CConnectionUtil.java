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

package org.compiere.db;

import de.metas.logging.LogManager;
import lombok.experimental.UtilityClass;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;

import javax.annotation.Nullable;

@UtilityClass
public class CConnectionUtil
{
	private final static transient Logger logger = LogManager.getLogger(CConnectionUtil.class);

	private static final String OPTION_Help = "h";

	/**
	 * Can set up the database connection at an early state.
	 * It's assumed that if this method does not set up the connection, it is done later (currently via {@link CConnection#createInstance(CConnectionAttributes)}).
	 */
	public void createInstanceFromArgs(@Nullable final String[] args)
	{
		if (args == null)
		{
			return;
		}

		final CommandLineParser parser = new DefaultParser();
		final Options options = createOptions();
		try
		{
			final CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("dbHost") && cmd.hasOption("dbPort"))
			{
				final CConnectionAttributes connectionAttributes = CConnectionAttributes.builder()
						.dbHost(cmd.getOptionValue("dbHost"))
						.dbPort((Integer)cmd.getParsedOptionValue("dbPort"))
						.dbName(cmd.getOptionValue("dbName", "metasfresh"))
						.dbUid(cmd.getOptionValue("dbUser", "metasfresh"))
						.dbPwd(cmd.getOptionValue("dbPassword", "metasfresh"))
						.build();
				CConnection.createInstance(connectionAttributes);
			}
		}
		catch (final Exception e)
		{
			//logger.error(e);
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("", options);

			throw new RuntimeException(e);
		}
	}

	private Options createOptions()
	{
		final Options options = new Options();
		// Help
		{
			final Option option = new Option(OPTION_Help, "Print this (h)elp message and exit");
			option.setArgs(0);
			option.setArgName("Help");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbHost",
					"");
			option.setArgs(1);
			option.setArgName("Database host name");
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option("dbPort", "");
			option.setArgs(1);
			option.setArgName("Database port number");
			option.setType(Integer.class);
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

		return options;
	}
}
