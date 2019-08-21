package de.metas.lock.model.validator;

import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;

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

		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTableToIgnoreList(I_T_Lock.Table_Name);
	}
}
