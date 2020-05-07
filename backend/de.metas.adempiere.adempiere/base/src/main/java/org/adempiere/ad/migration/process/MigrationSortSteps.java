package org.adempiere.ad.migration.process;

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


import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.process.JavaProcess;

/**
 * Sort migration steps by Created
 * 
 * @author Teo Sarca
 * 
 */
public class MigrationSortSteps extends JavaProcess
{
	private I_AD_Migration migration;

	@Override
	protected void prepare()
	{
		if (I_AD_Migration.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			this.migration = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_AD_Migration.class, get_TrxName());
		}
		if (I_AD_MigrationStep.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			final I_AD_MigrationStep step = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_AD_MigrationStep.class, get_TrxName());
			migration = step.getAD_Migration();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (migration == null || migration.getAD_Migration_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Migration_ID@");
		}

		Services.get(IMigrationBL.class).sortStepsByCreated(migration);

		return "OK";
	}

}
