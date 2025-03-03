/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ui.web.replenish.process;

import de.metas.product.ProductId;
import de.metas.replenishment.I_M_Material_Needs_Planner_V;
import de.metas.ui.web.view.IViewRow;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class MaterialNeedsPlannerRow
{
	@Nullable WarehouseId warehouseId;
	@NonNull ProductId productId;
	@NonNull BigDecimal levelMin;
	@NonNull BigDecimal levelMax;

	@NonNull
	public static MaterialNeedsPlannerRow ofRow(@NonNull final IViewRow row)
	{
		return MaterialNeedsPlannerRow.builder()
				.warehouseId(row.getFieldValueAsNullableRepoId(I_M_Material_Needs_Planner_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoIdOrNull))
				.productId(row.getFieldValueAsRepoId(I_M_Material_Needs_Planner_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId))
				.levelMin(row.getFieldValueAsBigDecimal(I_M_Material_Needs_Planner_V.COLUMNNAME_Level_Min, BigDecimal.ZERO))
				.levelMax(row.getFieldValueAsBigDecimal(I_M_Material_Needs_Planner_V.COLUMNNAME_Level_Max, BigDecimal.ZERO))
				.build();
	}

	public boolean isDemandFilled()
	{
		if (getWarehouseId() == null)
		{
			return false;
		}

		return !BigDecimal.ZERO.equals(getLevelMin());
	}
}
