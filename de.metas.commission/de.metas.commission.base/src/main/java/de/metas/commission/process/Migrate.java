/**
 * 
 */
package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.commission.util.MigrationHelper;

/**
 * @author tsa
 * 
 */
public class Migrate extends SvrProcess
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
			addLog("This was just a test. Rolling back");
			rollback();
		}
		return "Migrated to version " + migration.getVersion();
	}
}
