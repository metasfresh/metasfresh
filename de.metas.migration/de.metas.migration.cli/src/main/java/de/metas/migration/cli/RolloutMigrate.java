package de.metas.migration.cli;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

import de.metas.migration.IDatabase;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * This is the too's"main" class. To learn about the tool's command line parameters etc, check out {@link CommandlineParams}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public final class RolloutMigrate
{
	/**
	 * May contain only characters. No underscores, dots etc.
	 */
	public static final String UPDATE_IN_PROGRESS_VERSION_SUFFIX = "UpdateInProgress";

	private static final transient Logger logger = LoggerFactory.getLogger(RolloutMigrate.class);

	@NonNull
	private final DirectoryChecker directoryChecker;

	@NonNull
	private final PropertiesFileLoader propertiesFileLoader;

	@NonNull
	private final SettingsLoader settingsLoader;

	@NonNull
	private final RolloutVersionLoader rolloutVersionLoader;

	@NonNull
	private final DBConnectionMaker dbConnectionMaker;

	@NonNull
	private final DBVersionGetter dbVersionGetter;

	@NonNull
	private final MigrationScriptApplier migrationScriptApplier;

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
			logger.info("Duration: " + (ts2 - ts) + "ms (" + new Date(ts2) + ")");
			logger.info("Done.");
		}
	}

	@VisibleForTesting
	void run0(@NonNull final Config config)
	{
		final Settings settings = settingsLoader.loadSettings(config);
		logger.info("Settings=" + settings);

		final boolean copyTemplateToNewDB = config.getNewDBName() != null && !config.getNewDBName().isEmpty();
		//
		// check versions if that's wanted
		if (config.isCheckVersions())
		{
			final String rolloutVersionString = rolloutVersionLoader.loadRolloutVersionString(config.getRolloutDirName());

			// if we are asked to copy, then we check the version of the original (a.k.a. template) DB
			final String dbName = copyTemplateToNewDB ? config.getTemplateDBName() : settings.getDbName();

			final String dbVersionStr = dbVersionGetter.retrieveDBVersion(settings, dbName);

			final boolean dbNeedsMigration = VersionChecker.builder()
					.dbVersionStr(dbVersionStr)
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
					.dbConnection(dbConnectionMaker.createDummyDatabase(settings, dbName))
					.orgiginalDbName(config.getTemplateDBName())
					.copyDbName(config.getNewDBName())
					.copyDbOwner(settings.getDbUser())
					.build()
					.prepareNewDBCopy();
		}

		//
		// peform our "core" job, i.e. apply migration scripts
		final String dbName = copyTemplateToNewDB ? config.getNewDBName() : settings.getDbName();
		final IDatabase db = dbConnectionMaker.createDb(settings, dbName);

		//
		// update the DB's version info if that's wanted
		if (config.isStoreVersion())
		{
			final String dBVersion = dbVersionGetter.retrieveDBVersion(settings, dbName);
			updateDbVersion(db, dBVersion, UPDATE_IN_PROGRESS_VERSION_SUFFIX);
		}

		migrationScriptApplier.applyMigrationScripts(config, settings, dbName);

		//
		// update the DB's version info if that's wanted
		if (config.isStoreVersion())
		{
			final String rolloutVersionString = rolloutVersionLoader.loadRolloutVersionString(config.getRolloutDirName());
			updateDbVersion(db, rolloutVersionString, ""); // update with just the rollout version; no suffix
		}
	}

	private void updateDbVersion(
			@NonNull final IDatabase db,
			@NonNull final String versionStr,
			final String additionalMetaDataSuffix)
	{
		DBVersionSetter.builder()
				.db(db)
				.newVersion(versionStr)
				.additionalMetaDataSuffix(additionalMetaDataSuffix)
				.build().setVersion();
	}
}
