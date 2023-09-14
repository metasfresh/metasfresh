package org.adempiere.ad.service.impl;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * Developer Model BL Implementation
 *
 * @author tsa
 * See http://dewiki908/mediawiki/index.php/02664:_Introduce_ADempiere_Developer_Mode_%282012040510000121%29
 */
public class DeveloperModeBL implements IDeveloperModeBL
{
	public static final DeveloperModeBL instance = new DeveloperModeBL();

	private static final String SYSCONFIG_DeveloperMode = "de.metas.adempiere.debug";
	private static final String SYSCONFIG_DevelopmentWorkspaceDir = "developmentWorkspaceDir";

	private static final Logger logger = LogManager.getLogger(DeveloperModeBL.class);

	protected DeveloperModeBL()
	{
	}

	@Override
	public boolean isEnabled()
	{
		//
		// Get the DeveloperMode sysconfig
		// NOTE: this method shall be as less disruptive as possible, so
		// if there is no database connection or retrieving the sysconfig fails for some reason simply return false,
		// but never ever fail.
		try
		{
			if (Adempiere.isUnitTestMode())
			{
				return true;
			}

			if (!DB.isConnected())
			{
				return false;
			}
			return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DeveloperMode, false);
		}
		catch (Exception e)
		{
			logger.warn("Failed retrieving the DeveloperMode sysconfig. Considering not enabled.", e);
			return false;
		}
	}

	@Override
	public Optional<File> getDevelopmentWorkspaceDir()
	{
		final String dirStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_DevelopmentWorkspaceDir);
		if (dirStr == null || Check.isBlank(dirStr) || "-".equals(dirStr.trim()))
		{
			return Optional.empty();
		}

		final File dir = new File(dirStr.trim());
		return Optional.of(dir);
	}

	@Override
	public void executeAsSystem(final ContextRunnable runnable)
	{
		final Properties sysCtx = Env.createSysContext(Env.getCtx());

		DB.saveConstraints();
		try (final IAutoCloseable ignored = Env.switchContext(sysCtx);
				final IAutoCloseable ignored1 = MigrationScriptFileLoggerHolder.temporaryEnabledLoggingToNewFile())
		{
			DB.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);
			DB.getConstraints().incMaxTrx(1);

			runnable.run(sysCtx);
		}
		finally
		{
			DB.restoreConstraints();
		}
	}
}
