/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.olcand;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

public class C_OLCandAggAndOrder_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("update C_OLCandAggAndOrder:")
	public void update_C_OLCandAggAndOrder(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int olCandProcessorId = DataTableUtil.extractIntForColumnName(row, I_C_OLCandAggAndOrder.COLUMNNAME_C_OLCandProcessor_ID);
			final int olCandColumnId = DataTableUtil.extractIntForColumnName(row, I_C_OLCandAggAndOrder.COLUMNNAME_AD_Column_OLCand_ID);
			final Boolean splitOrder = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_OLCandAggAndOrder.COLUMNNAME_SplitOrder, null);

			final I_C_OLCandAggAndOrder aggAndOrder = queryBL.createQueryBuilder(I_C_OLCandAggAndOrder.class)
					.addEqualsFilter(I_C_OLCandAggAndOrder.COLUMNNAME_C_OLCandProcessor_ID, olCandProcessorId)
					.addEqualsFilter(I_C_OLCandAggAndOrder.COLUMNNAME_AD_Column_OLCand_ID, olCandColumnId)
					.orderBy(I_C_OLCandAggAndOrder.COLUMN_C_OLCandAggAndOrder_ID)
					.create()
					.firstNotNull(I_C_OLCandAggAndOrder.class);

			if (splitOrder != null)
			{
				aggAndOrder.setSplitOrder(splitOrder);
			}

			InterfaceWrapperHelper.save(aggAndOrder);
		}
	}
}
