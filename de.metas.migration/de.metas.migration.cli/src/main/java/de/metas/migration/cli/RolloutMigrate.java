package de.metas.migration.cli;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.impl.ConsoleScriptsApplierListener;
import de.metas.migration.applier.impl.NullScriptsApplierListener;
import de.metas.migration.cli.Config.ConfigBuilder;

public final class RolloutMigrate
{
	private static final transient Logger logger = LoggerFactory.getLogger(RolloutMigrate.class);

	private static final String OPTION_Help = "h";
	private static final String OPTION_RolloutDirectory = "d";
	private static final String DEFAULT_RolloutDirectory = "./..";

	private static final String OPTION_ScriptFile = "f";
	private static final String OPTION_SettingsFile = "s";
	private static final String OPTION_JustMarkScriptAsExecuted = "r";
	private static final String OPTION_CreateNewDB = "n";
	private static final String OPTION_Interactive = "a"; // "a" (from ask)
	private static final String OPTION_DoNotCheckVersions = "v";

	private static final String OPTION_DoNotFailIfRolloutIsGreaterThanDB = "i";

	private final Options options;
	// private Properties settings;
	// private File rolloutDir;

	private Config config;

	/**
	 * By default we will check the versions
	 */
	// private boolean doNotCheckVersions = false;
	//
	// private boolean doNotFailIfRolloutIsGreaterThanDB = true;
	//
	// private String newDBName;
	// private String templateDBName;
	private IScriptsApplierListener scriptsApplierListener = NullScriptsApplierListener.instance;

	public RolloutMigrate()
	{
		options = RolloutMigrate.createOptions();
	}

	private static final Options createOptions()
	{
		final Options options = new Options();
		// Help
		{
			final Option option = new Option(RolloutMigrate.OPTION_Help, "Print this (h)elp message and exit");
			option.setArgs(0);
			option.setArgName("Help");
			option.setRequired(false);
			options.addOption(option);
		}
		// Rollout Directory
		{
			final Option option = new Option(RolloutMigrate.OPTION_RolloutDirectory,
					"The (d)irectory that contains the rollout package. The tool assumes that the actual SQL scripts are in a folder structure within <RolloutDirectory>/sql/. "
							+ "If omitted, then '" + RolloutMigrate.DEFAULT_RolloutDirectory + "' (i.e. " + new File(RolloutMigrate.DEFAULT_RolloutDirectory).getAbsolutePath() + ") will be used");
			option.setArgs(1);
			option.setArgName("Rollout-Directory");
			option.setRequired(false);
			options.addOption(option);
		}
		// File
		{
			final Option option = new Option(RolloutMigrate.OPTION_ScriptFile, "Only process the given (f)ile in the rollout directory");
			option.setArgs(1);
			option.setArgName("File");
			option.setRequired(false);
			options.addOption(option);
		}
		// Settings
		{
			final Option option = new Option(RolloutMigrate.OPTION_SettingsFile,
					"Name of the (s)ettings file (e.g. settings_<hostname>.properties) *within the Rollout-Directory*. If ommitted, then "
							+ System.getProperty("user.home") + "/" + Config.DEFAULT_SETTINGS_FILENAME + " will be used instead, where " + System.getProperty("user.home") + " is the current user's home directory");
			option.setArgs(1);
			option.setArgName("Settings file");
			option.setRequired(false);
			options.addOption(option);
		}
		// Only record script
		{
			final Option option = new Option(RolloutMigrate.OPTION_JustMarkScriptAsExecuted, "Only (r)ecord script, but don't actually execute. WARNING: Only use if you know what you are doing!");
			option.setArgs(0);
			option.setRequired(false);
			options.addOption(option);
		}
		// create a new DB from template and run the migration there
		{
			final Option option = new Option(RolloutMigrate.OPTION_CreateNewDB, "Create a (n)ew Database from a template, and do the migration on that new DB. Arguments <templateDBName> <newDBName>");
			option.setArgs(2);
			option.setRequired(false);
			options.addOption(option);
		}
		//
		{
			final Option option = new Option(OPTION_Interactive, "In case of script errors, (a)sk what to do");
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
		return options;
	}

	private void log(final String msg)
	{
		final Throwable e = null;
		log(msg, e);
	}

	private void log(final String msg, final Throwable e)
	{
		logger.info(msg, e);
	}

	private final boolean init(final String[] args)
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

		final boolean printHelp = cmd.hasOption(RolloutMigrate.OPTION_Help);
		if (printHelp)
		{
			printHelp(System.out);
			return false;
		}

		final ConfigBuilder configBuilder = Config.builder();

		final String rolloutDir = cmd.getOptionValue(RolloutMigrate.OPTION_RolloutDirectory, RolloutMigrate.DEFAULT_RolloutDirectory);
		configBuilder.rolloutDirName(rolloutDir);

		final String settingsFile = cmd.getOptionValue(RolloutMigrate.OPTION_SettingsFile);
		configBuilder.settingsFileName(settingsFile);

		final String scriptFile = cmd.getOptionValue(RolloutMigrate.OPTION_ScriptFile);
		configBuilder.scriptFileName(scriptFile);

		final boolean justMarkScriptAsExecuted = cmd.hasOption(RolloutMigrate.OPTION_JustMarkScriptAsExecuted);
		configBuilder.justMarkScriptAsExecuted(justMarkScriptAsExecuted);

		final String[] optionValues = cmd.getOptionValues(RolloutMigrate.OPTION_CreateNewDB);
		if (optionValues != null)
		{
			configBuilder.templateDBName(optionValues[0]);
			configBuilder.newDBName(optionValues[1]);
		}

		config = configBuilder.build();
		log("config=" + config);

		if (cmd.hasOption(OPTION_Interactive))
		{
			log("Interactive mode: true");
			scriptsApplierListener = ConsoleScriptsApplierListener.instance;
		}

		if (cmd.hasOption(OPTION_DoNotCheckVersions))
		{
			log("Will not compare DB Versions");
			configBuilder.doNotCheckVersions(true);
		}
		if (cmd.hasOption(OPTION_DoNotFailIfRolloutIsGreaterThanDB))
		{
			configBuilder.doNotFailIfRolloutIsGreaterThanDB(true);
		}

		return true;
	}

	public final void run()
	{
		final long ts = System.currentTimeMillis();
		try
		{
			run0();
		}
		finally
		{
			final long ts2 = System.currentTimeMillis();
			log("Duration: " + (ts2 - ts) + "ms (" + new Date(ts2) + ")");
			log("Done.");
		}
	}

	private void run0()
	{
		final DirectoryChecker directoryChecker = new DirectoryChecker();
		final PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader(directoryChecker);

		final Settings settings = new SettingsLoader(config, directoryChecker, propertiesFileLoader).loadConfig();

		final DBConnectionMaker dbConnectionMaker = new DBConnectionMaker(settings);

		final RolloutVersionLoader rolloutVersionLoader = new RolloutVersionLoader(propertiesFileLoader.loadFromFile(config.getRolloutDirName(), "build-info.properties"));
		final String rolloutVersionString = rolloutVersionLoader.getRolloutVersionString();

		if (!config.isDoNotCheckVersions())
		{
			final String dbName = settings.getDbName();

			final boolean dbNeedsMigration = VersionChecker.builder()
					.dbConnection(dbConnectionMaker.createDummyDatabase(dbName))
					.rolloutVersionStr(rolloutVersionString)
					.doNotFailIfRolloutIsGreaterThanDB(config.isDoNotFailIfRolloutIsGreaterThanDB())
					.build()
					.dbNeedsMigration();
			if (!dbNeedsMigration)
			{
				return; // nuzzing to do
			}
		}
		final boolean useNewDBName = config.getNewDBName() != null && !config.getNewDBName().isEmpty();
		if (useNewDBName)
		{
			final String dbName = "postgres"; // connecting to the "maintainance-DB, since we can't be connected to the DB we want to clone
			DBCopyMaker.builder()
					.dbConnection(dbConnectionMaker.createDummyDatabase(dbName))
					.orgiginalDbNamme(config.getTemplateDBName())
					.copyDbName(config.getNewDBName())
					.copyDbOwner(settings.getDbUser())
					.build()
					.prepareNewDBCopy();
		}

		final String dbName = useNewDBName ? config.getNewDBName() : settings.getDbName();
		final IDatabase db = dbConnectionMaker.createDb(dbName);

		MigrationScriptApplier.builder()
				.db(db)
				.listener(scriptsApplierListener)
				.justMarkScriptAsExecuted(config.isJustMarkScriptAsExecuted())
				.build()
				.applyMigrationScripts();

		new VersionSetter(db, rolloutVersionString)
				.setVersion();
	}

	public final void printHelp(final PrintStream out)
	{
		final PrintWriter writer = new PrintWriter(out);
		final String commandName = "RolloutMigrate";
		final String header = "Util to apply metasfresh migration scripts to a POstgresSQL database. The database settings are read from a settings (properties) file.";
		final String footer = "\nHint: The tool checks if a script has already been applied";

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

	public static final void main(final String[] args)
	{
		logger.info("RolloutMigrate (" + BinaryVersion.instance + ")");

		final RolloutMigrate main = new RolloutMigrate();

		if (main.init(args))
		{
			main.run();
		}
	}
}
