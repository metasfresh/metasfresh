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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

public class C_AggregationItem_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("update C_AggregationItem:")
	public void update_C_AggregationItem(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int aggregationId = DataTableUtil.extractIntForColumnName(row, I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID);
			final int columnId = DataTableUtil.extractIntForColumnName(row, I_C_AggregationItem.COLUMNNAME_AD_Column_ID);
			final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_AggregationItem.COLUMNNAME_IsActive, null);

			final I_C_AggregationItem aggItem = queryBL.createQueryBuilder(I_C_AggregationItem.class)
					.addEqualsFilter(I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID, aggregationId)
					.addEqualsFilter(I_C_AggregationItem.COLUMNNAME_AD_Column_ID, columnId)
					.orderBy(I_C_AggregationItem.COLUMN_C_AggregationItem_ID)
					.create()
					.firstNotNull(I_C_AggregationItem.class);

			if (isActive != null)
			{
				aggItem.setIsActive(isActive);
			}

			InterfaceWrapperHelper.save(aggItem);
		}
	}
}
