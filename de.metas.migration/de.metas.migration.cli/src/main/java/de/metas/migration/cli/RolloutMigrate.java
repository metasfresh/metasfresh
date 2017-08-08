package de.metas.migration.cli;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import lombok.NonNull;

public final class RolloutMigrate
{
	private static final transient Logger logger = LoggerFactory.getLogger(RolloutMigrate.class);

	private void log(final String msg)
	{
		final Throwable e = null;
		log(msg, e);
	}

	private void log(final String msg, final Throwable e)
	{
		logger.info(msg, e);
	}

	public final void run(@NonNull final Config config)
	{
		final long ts = System.currentTimeMillis();
		try
		{
			run0(config);
		}
		finally
		{
			final long ts2 = System.currentTimeMillis();
			log("Duration: " + (ts2 - ts) + "ms (" + new Date(ts2) + ")");
			log("Done.");
		}
	}

	private void run0(@NonNull final Config config)
	{
		final DirectoryChecker directoryChecker = new DirectoryChecker();
		final PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader(directoryChecker);

		final Settings settings = new SettingsLoader(config, directoryChecker, propertiesFileLoader).loadSettings();
		log("Settings=" + settings);

		final DBConnectionMaker dbConnectionMaker = new DBConnectionMaker(settings);

		final RolloutVersionLoader rolloutVersionLoader = new RolloutVersionLoader(propertiesFileLoader, config.getRolloutDirName());

		final boolean copyTemplateToNewDB = config.getNewDBName() != null && !config.getNewDBName().isEmpty();
		//
		// check versions if that's wanted
		if (config.isCheckVersions())
		{
			final String rolloutVersionString = rolloutVersionLoader.loadRolloutVersionString();

			// if we are asked to copy, then we check the version of the original (a.k.a. template) DB
			final String dbName = copyTemplateToNewDB ? config.getTemplateDBName() : settings.getDbName();

			final boolean dbNeedsMigration = VersionChecker.builder()
					.dbConnection(dbConnectionMaker.createDummyDatabase(dbName))
					.rolloutVersionStr(rolloutVersionString)
					.failIfRolloutIsGreaterThanDB(config.isFailIfRolloutIsGreaterThanDB())
					.build()
					.dbNeedsMigration();
			if (!dbNeedsMigration)
			{
				return; // nuzzing to do
			}
		}

		//
		// create a DB copy if that's wanted
		if (copyTemplateToNewDB)
		{
			final String dbName = "postgres"; // connecting to the "maintainance-DB, since we can't be connected to the DB we want to clone
			DBCopyMaker.builder()
					.dbConnection(dbConnectionMaker.createDummyDatabase(dbName))
					.orgiginalDbName(config.getTemplateDBName())
					.copyDbName(config.getNewDBName())
					.copyDbOwner(settings.getDbUser())
					.build()
					.prepareNewDBCopy();
		}

		//
		// peform our "core" job, i.e. apply migration scripts
		final String dbName = copyTemplateToNewDB ? config.getNewDBName() : settings.getDbName();
		final IDatabase db = dbConnectionMaker.createDb(dbName);

		//
		// update the DB's version info if that's wanted
		if (config.isStoreVersion())
		{
			final String rolloutVersionString = rolloutVersionLoader.loadRolloutVersionString();
			new VersionSetter(db, rolloutVersionString + "+inProgress")
					.setVersion();
		}

		MigrationScriptApplier.builder()
				.db(db)
				.listener(config.getScriptsApplierListener())
				.justMarkScriptAsExecuted(config.isJustMarkScriptAsExecuted())
				.rolloutDirName(config.getRolloutDirName())
				.scriptFileName(config.getScriptFileName())
				.directoryChecker(directoryChecker)
				.build()
				.applyMigrationScripts();

		//
		// update the DB's version info if that's wanted
		if (config.isStoreVersion())
		{
			final String rolloutVersionString = rolloutVersionLoader.loadRolloutVersionString();
			new VersionSetter(db, rolloutVersionString)
					.setVersion();
		}
	}

	public static final void main(final String[] args)
	{
		logger.info("RolloutMigrate (" + BinaryVersion.instance + ")");

		final RolloutMigrate main = new RolloutMigrate();

		final CommandlineParams commandlineParams = new CommandlineParams();
		final Config config = commandlineParams.init(args);

		if (config.isCanRun())
		{
			main.run(config);
		}
	}
}
