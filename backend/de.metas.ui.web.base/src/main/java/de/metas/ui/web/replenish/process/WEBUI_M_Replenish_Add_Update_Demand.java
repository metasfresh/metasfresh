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

import de.metas.material.replenish.ReplenishInfo;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.replenishment.I_M_Material_Needs_Planner_V;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class WEBUI_M_Replenish_Add_Update_Demand extends ViewBasedProcessTemplate implements IProcessDefaultParametersProvider, IProcessPrecondition
{
	@NonNull private final ReplenishInfoRepository replenishInfoRepository = SpringContextHolder.instance.getBean(ReplenishInfoRepository.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	private static final String PARAM_M_Product_ID = I_M_Material_Needs_Planner_V.COLUMNNAME_M_Product_ID;
	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private ProductId productId;

	private static final String PARAM_M_Warehouse_ID = I_M_Material_Needs_Planner_V.COLUMNNAME_M_Warehouse_ID;
	@Param(parameterName = PARAM_M_Warehouse_ID, mandatory = true)
	private WarehouseId warehouseId;

	private static final String PARAM_Level_Min = I_M_Material_Needs_Planner_V.COLUMNNAME_Level_Min;
	@Param(parameterName = PARAM_Level_Min, mandatory = true)
	private BigDecimal levelMin;

	private static final String PARAM_Level_Max = I_M_Material_Needs_Planner_V.COLUMNNAME_Level_Max;
	@Param(parameterName = PARAM_Level_Max, mandatory = true)
	private BigDecimal levelMax;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final UomId uomId = productBL.getStockUOMId(productId);
		final StockQtyAndUOMQty levelMin = StockQtyAndUOMQtys.createConvert(this.levelMin, productId, uomId);
		final StockQtyAndUOMQty levelMax = StockQtyAndUOMQtys.createConvert(this.levelMax, productId, uomId);

		replenishInfoRepository.save(ReplenishInfo.builder()
				.identifier(ReplenishInfo.Identifier.builder()
						.productId(productId)
						.warehouseId(warehouseId)
						.build())
				.min(levelMin)
				.max(levelMax)
				.build());

		return MSG_OK;
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final MaterialNeedsPlannerRow materialNeedsPlannerRow = MaterialNeedsPlannerRow.ofRow(getSingleSelectedRow());

		if (PARAM_M_Product_ID.equals(parameter.getColumnName()))
		{
			return materialNeedsPlannerRow.getProductId();
		}
		else if (PARAM_M_Warehouse_ID.equals(parameter.getColumnName()))
		{
			return materialNeedsPlannerRow.getWarehouseId();
		}
		else if (PARAM_Level_Min.equals(parameter.getColumnName()))
		{
			return materialNeedsPlannerRow.getLevelMin();
		}
		else if (PARAM_Level_Max.equals(parameter.getColumnName()))
		{
			return materialNeedsPlannerRow.getLevelMax();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected void postProcess(final boolean success)
	{
		getView().invalidateSelection();
	}
}
