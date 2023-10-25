package de.metas.lock.model.validator;

import com.google.common.collect.ImmutableSet;
import de.metas.lock.model.I_T_Lock;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;

import java.util.Set;

/**
 * Lock Module Activator
 *
 * @author tsa
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(I_T_Lock.Table_Name);
	}
}
