package org.adempiere.db.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.model.MLanguage;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * Code that needs to be executed after database rollout, but right before ADempiere server is started.
 * 
 * At this moment this class is executed from /adempiereTrunkMvcForms/utils/RUN_Post_Rollout_Processes.sh
 * 
 * @author tsa
 * 
 */
public class AfterMigration implements Runnable
{
	private static final transient CLogger logger = CLogger.getCLogger(AfterMigration.class);

	public static void main(final String[] args)
	{
		final AfterMigration migration = new AfterMigration();
		migration.init();
		
		logger.info("-----------------------------------------------------------------------------------------------");
		migration.run();
	}

	private final void init()
	{
		AdempiereToolsHelper.getInstance().startupMinimal();
		if (!CLogMgt.isLevel(Level.INFO))
		{
			CLogMgt.setLevel(Level.INFO);
		}
	}

	@Override
	public void run()
	{
		addMissingTranslations();
	}

	/**
	 * Add missing translation records
	 * 
	 * @task http://dewiki908/mediawiki/index.php/04577_Login_bug_on_UAT_%282013071510000087%29
	 */
	private void addMissingTranslations()
	{
		logger.info("Adding missing translation records");
		MLanguage.maintain(Env.getCtx());
	}

}
