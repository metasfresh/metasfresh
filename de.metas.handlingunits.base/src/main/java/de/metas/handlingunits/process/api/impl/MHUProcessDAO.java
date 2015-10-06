package de.metas.handlingunits.process.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;

import de.metas.handlingunits.model.I_M_HU_Process;
import de.metas.handlingunits.process.api.IMHUProcessDAO;

public class MHUProcessDAO implements IMHUProcessDAO
{

	@Override
	public I_M_HU_Process retrieveHUProcess(final I_AD_Process adProcess)
	{
		final IQueryBuilder<I_M_HU_Process> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Process.class)
				.setContext(adProcess);

		final ICompositeQueryFilter<I_M_HU_Process> filter = queryBuilder.getFilters();
		filter.addOnlyActiveRecordsFilter();
		filter.addFilter(new EqualsQueryFilter<I_M_HU_Process>(I_M_HU_Process.COLUMNNAME_AD_Process_ID, adProcess.getAD_Process_ID()));

		// There should be only one M_HU_Process active entry for an AD_Process_ID
		return queryBuilder.create()
				.firstOnly(I_M_HU_Process.class);
	}

}
