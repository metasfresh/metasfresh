package de.metas.inventory.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_M_InventoryLine;

import de.metas.process.JavaProcess;
import de.metas.util.Services;

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
				.stream()
				.forEach(inventoryLine -> markAsCounted(inventoryLine));

		return MSG_OK;
	}

	private void markAsCounted(final I_M_InventoryLine inventoryLine)
	{
		if (!inventoryLine.isCounted())
		{
			inventoryLine.setIsCounted(true);
			save(inventoryLine);
		}
	}

	private List<I_M_InventoryLine> getSelectedInventoryLines()
	{
		final IQueryFilter<I_M_InventoryLine> selectedInventoryLines = getProcessInfo().getQueryFilterOrElseFalse();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_InventoryLine> queryBuilder = queryBL.createQueryBuilder(I_M_InventoryLine.class);

		return queryBuilder
				.filter(selectedInventoryLines)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_M_InventoryLine.COLUMNNAME_M_Locator_ID).endOrderBy()
				.create()
				.list(I_M_InventoryLine.class);
	}
}
