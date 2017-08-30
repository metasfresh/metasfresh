package de.metas.migration.cli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.applier.impl.ConsoleScriptsApplierListener;
import de.metas.migration.cli.Config.ConfigBuilder;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CommandlineParams
{
	private static final transient Logger logger = LoggerFactory.getLogger(CommandlineParams.class);

	private static final String OPTION_Help = "h";
	private static final String OPTION_RolloutDirectory = "d";
	private static final String DEFAULT_RolloutDirectory = "./..";

	private static final String OPTION_ScriptFile = "f";
	private static final String OPTION_SettingsFile = "s";
	private static final String OPTION_JustMarkScriptAsExecuted = "r";
	private static final String OPTION_CreateNewDB = "n";
	private static final String OPTION_Interactive = "a"; // "a" (from ask)
	private static final String OPTION_DoNotCheckVersions = "v";
	private static final String OPTION_DoNotStoreVersion = "u";

	public static final String OPTION_DoNotFailIfRolloutIsGreaterThanDB = "i";

	private final Options options;

	public CommandlineParams()
	{
		options = createOptions();
	}

	private final Options createOptions()
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
		// Rollout Directory
		{
			final Option option = new Option(OPTION_RolloutDirectory,
					"The (d)irectory that contains the rollout package. The tool assumes that the actual SQL scripts are in a folder structure within <RolloutDirectory>/sql/. "
							+ "If omitted, then '" + DEFAULT_RolloutDirectory + "' (i.e. " + new File(DEFAULT_RolloutDirectory).getAbsolutePath() + ") will be used");
			option.setArgs(1);
			option.setArgName("Rollout-Directory");
			option.setRequired(false);
			options.addOption(option);
		}
		// File
		{
			final Option option = new Option(OPTION_ScriptFile, "Only process the given (f)ile in the rollout directory");
			option.setArgs(1);
			option.setArgName("File");
			option.setRequired(false);
			options.addOption(option);
		}
		// Settings
		{
			final Option option = new Option(OPTION_SettingsFile,
					"Name of the (s)ettings file (e.g. settings_<hostname>.properties) *within the Rollout-Directory*. If ommitted, then "
							+ System.getProperty("user.home") + "/" + Config.DEFAULT_SETTINGS_FILENAME + " will be used instead, where " + System.getProperty("user.home") + " is the current user's home directory");
			option.setArgs(1);
			option.setArgName("Settings file");
			option.setRequired(false);
			options.addOption(option);
		}
		// Only record script
		{
			final Option option = new Option(OPTION_JustMarkScriptAsExecuted, "Only (r)ecord script, but don't actually execute. WARNING: Only use if you know what you are doing!");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}
		// create a new DB from template and run the migration there
		{
			final Option option = new Option(OPTION_CreateNewDB, "Create a (n)ew Database from a template, and do the migration on that new DB. Arguments <templateDBName> <newDBName>");
			option.setArgs(2);
			option.setRequired(false);
			options.addOption(option);
		}
		//
		{
			final Option option = new Option(OPTION_Interactive, "In case of script errors, (a)sk what to do.");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option(OPTION_DoNotCheckVersions, "By default we compare this package's (v)ersion with AD_System.DBVersion. This parameter disables that check.");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option(OPTION_DoNotFailIfRolloutIsGreaterThanDB, "If the version was checked and the DB's version is already ahead"
					+ " of whatever we could rollout here, then an error is thrown. However, with this parameter set, the problem is (i)gnored");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}

		{
			final Option option = new Option(OPTION_DoNotStoreVersion, "By default, the tool will work with the DB and update AD_System.DBVersion.\n"
					+ "First, before applying the migration scripts, the tool will update AD_System.DBVersion to <former-value>-" + RolloutMigrate.UPDATE_IN_PROGRESS_VERSION_SUFFIX + "\n"
					+ "Then, if the migration ran successfully, the tool will (u)pdate the DB's AD_System.DBVersion value with this package's version."
					+ "With this parameter set, the tool won't do any of that.");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}
		return options;
	}

	public final Config init(final String[] args)
	{
		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd;
		try
		{
			cmd = parser.parse(options, args);
		}
		catch (final Exception e)
		{
			logger.error(e.getLocalizedMessage()
					+ "\n\n" + printHelpToString());

			throw new RuntimeException(e);
		}

		final boolean printHelp = cmd.hasOption(OPTION_Help);
		if (printHelp)
		{
			printHelp(System.out);
			return Config.builder().rolloutDirName("not relevant").canRun(false).build();
		}

		final ConfigBuilder configBuilder = Config.builder();

		final String rolloutDir = cmd.getOptionValue(OPTION_RolloutDirectory, DEFAULT_RolloutDirectory);
		configBuilder.rolloutDirName(rolloutDir);

		final String settingsFile = cmd.getOptionValue(OPTION_SettingsFile);
		configBuilder.settingsFileName(settingsFile);

		final String scriptFile = cmd.getOptionValue(OPTION_ScriptFile);
		configBuilder.scriptFileName(scriptFile);

		final boolean justMarkScriptAsExecuted = cmd.hasOption(OPTION_JustMarkScriptAsExecuted);
		configBuilder.justMarkScriptAsExecuted(justMarkScriptAsExecuted);

		final String[] optionValues = cmd.getOptionValues(OPTION_CreateNewDB);
		if (optionValues != null)
		{
			configBuilder.templateDBName(optionValues[0]);
			configBuilder.newDBName(optionValues[1]);
		}

		if (cmd.hasOption(OPTION_Interactive))
		{
			logger.info("Interactive mode: true");
			configBuilder.scriptsApplierListener(ConsoleScriptsApplierListener.instance);
		}

		if (cmd.hasOption(OPTION_DoNotCheckVersions))
		{
			logger.info("Will *not* attempt to compare DB Versions");
			configBuilder.checkVersions(false);
		}
		if (cmd.hasOption(OPTION_DoNotFailIfRolloutIsGreaterThanDB))
		{
			logger.info("Will *not* fail if the DB's version is greater than our own version");
			configBuilder.failIfRolloutIsGreaterThanDB(false);
		}
		if (cmd.hasOption(OPTION_DoNotStoreVersion))
		{
			logger.info("Will *not* attempt to update the DB's version after rollout");
			configBuilder.storeVersion(false);
		}

		final Config config = configBuilder.canRun(true).build();
		logger.info("config=" + config);

		return config;
	}

	public final void printHelp(final PrintStream out)
	{
		final PrintWriter writer = new PrintWriter(out);
		final String commandName = "RolloutMigrate";
		final String header = "Util to apply metasfresh migration scripts to a POstgresSQL database. The database settings are read from a settings (properties) file.\n"
				+ "The tool will by default only run if its own version (as set in in the " + RolloutVersionLoader.PROP_VERSION + " property of its " + RolloutVersionLoader.BUILD_INFO_FILENAME + ")"
				+ " is higher than the version selected from the DB (AD_System.DBVersion).";
		final String footer = "\nHints: "
				+ "* For each individual migration file the tool checks against the Table AD_MigrationScript if the migration file was already been applied"
				+ "* The migration files are ordered by their filenames \"globally\", no matter in thich directory they are";

		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(
				writer,   // output
				200,   // width,
				commandName,   // cmdLineSyntax
				header,   // header,
				options,   // options
				4,   // leftPad,
				4,   // descPad,
				footer,   // footer,
				true // autoUsage
		);

		writer.flush();
	}

	public final String printHelpToString()
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream out = new PrintStream(baos);
		printHelp(out);

		final String content = baos.toString();
		return content;
	}
}
