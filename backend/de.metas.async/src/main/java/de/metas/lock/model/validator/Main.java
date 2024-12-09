package de.metas.lock.model.validator;

<<<<<<< HEAD
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;

import de.metas.lock.model.I_T_Lock;
import de.metas.util.Services;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.lock.model.I_T_Lock;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;

import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Lock Module Activator
 * 
 * @author tsa
<<<<<<< HEAD
 *
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
<<<<<<< HEAD
	protected void onAfterInit()
	{
		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTableToIgnoreList(I_T_Lock.Table_Name);
=======
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(I_T_Lock.Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
