/**
 * 
 */
package de.metas.commission.process;

import org.adempiere.exceptions.AdempiereException;

import de.metas.commission.util.MigrationHelper;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 * 
 */
public class Migrate extends JavaProcess
{
	private boolean p_IsTest = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final MigrationHelper migration = new MigrationHelper(getCtx(), get_TrxName());
		migration.setLogger(this);
		migration.setIsTest(p_IsTest);
		migration.migrate();
		if (p_IsTest)
		{
			throw new AdempiereException("This was just a test. Rolling back");
		}
		return "Migrated to version " + migration.getVersion();
	}
}
