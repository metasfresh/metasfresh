package org.adempiere.process;

/*
 * #%L
 * ADempiere ERP - Base
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

import org.compiere.Adempiere;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author al
 */
public class RunDatabaseMigration
{
	private static CLogger logger = CLogger.getCLogger(SignDatabaseBuild.class);

	public static void main(String[] args)
	{
		Adempiere.startupEnvironment(false);
		CLogMgt.setLevel(Level.FINE);

		logger.info("Running Database Migration...");

		if (!DB.isConnected())
		{
			logger.info("No DB Connection");
			System.exit(1);
		}

		// Migration Loader will be called via shell script.
		MigrationLoader loader = new MigrationLoader();
		loader.load(Env.getCtx());
	}
}
