package de.metas.ordercandidate.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;

import de.metas.ordercandidate.api.IOLCandUpdateBL;
import de.metas.ordercandidate.api.OLCandUpdateResult;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.JavaProcess;

public class OLCandSetOverrideValues extends JavaProcess
{
	private IParams params = null;

	@Override
	protected void prepare()
	{
		this.params = getParameterAsIParams();
	}

	@Override
	protected String doIt() throws Exception
	{
		final OLCandUpdateResult result = Services.get(IOLCandUpdateBL.class).updateOLCands(getCtx(), createIterator(), params);

		return "@Success@: " + result.getUpdated() + " @Processed@, " + result.getSkipped() + " @Skipped@";
	}

	private Iterator<I_C_OLCand> createIterator()
	{
		final IQueryFilter<I_C_OLCand> queryFilter = getProcessInfo().getQueryFilter();
		
		final IQueryBuilder<I_C_OLCand> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.filter(queryFilter)
				.filter(ActiveRecordQueryFilter.getInstance(I_C_OLCand.class));
		
		queryBuilder.orderBy()
				.addColumn(I_C_OLCand.COLUMNNAME_C_OLCand_ID);

		return queryBuilder.create()
				.setApplyAccessFilterRW(false) // 04471: enqueue only those records on which user has access to
				.iterate(I_C_OLCand.class);
	}
}
