package de.metas.inventory.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InventoryLine;

import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class M_InventoryLine_MarkAsCounted extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		getSelectedInventoryLines()
				.forEach(inventoryLine -> {

					if (!inventoryLine.isCounted())
					{
						inventoryLine.setIsCounted(true);
						save(inventoryLine);
					}
				});

		return MSG_OK;
	}

	private Iterable<I_M_InventoryLine> getSelectedInventoryLines()
	{
		final IQueryFilter<I_M_InventoryLine> selectedPartners = getProcessInfo().getQueryFilter();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_InventoryLine> queryBuilder = queryBL.createQueryBuilder(I_M_InventoryLine.class, getCtx(), ITrx.TRXNAME_ThreadInherited);

		final Iterator<I_M_InventoryLine> it = queryBuilder
				.filter(selectedPartners)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_M_InventoryLine.COLUMNNAME_M_Locator_ID).endOrderBy() // to make it easier for the user to browse the logging.
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_M_InventoryLine.class);

		return () -> it;
	}
}
