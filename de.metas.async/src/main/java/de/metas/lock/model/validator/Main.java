package de.metas.lock.model.validator;

/*
 * #%L
 * de.metas.async
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


import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;

import de.metas.lock.housekeeping.spi.impl.ClearLocks;
import de.metas.lock.model.I_T_Lock;
import de.metas.util.Services;

/**
 * Lock Module Activator
 * 
 * @author tsa
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(IModelValidationEngine engine, I_AD_Client client)
	{
		super.onInit(engine, client);

		// task 06295
		Services.get(IHouseKeepingBL.class).registerStartupHouseKeepingTask(new ClearLocks());

		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTableToIgnoreList(I_T_Lock.Table_Name);
	}
}
