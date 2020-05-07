package de.metas.printing.process;

/*
 * #%L
 * de.metas.printing.base
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
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;

import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;

public class C_Printing_Queue_ResetAggregationKeys extends JavaProcess
{
	@Override
	protected void prepare()
	{
		// nothing to prepare here
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Printing_Queue> queryFilter = getProcessInfo().getQueryFilter();

		final Iterator<I_C_Printing_Queue> iterator = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Printing_Queue.class, getCtx(), getTrxName())
				.filter(queryFilter)
				.orderBy().addColumn(I_C_Printing_Queue.COLUMN_C_Printing_Queue_ID)
				.endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_C_Printing_Queue.class);

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		
		for(final I_C_Printing_Queue item: IteratorUtils.asIterable(iterator))
		{
			printingQueueBL.setItemAggregationKey(item);
			InterfaceWrapperHelper.save(item);
		}
		return "@Success@";
	}
}
