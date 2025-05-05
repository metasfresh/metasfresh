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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_DocStatus;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_M_Product_ID;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_MovementQty;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID;

public class PP_Cost_Collector_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final PP_Order_StepDefData ppOrderTable;
	private final PP_Cost_Collector_StepDefData ppCostCollectorTable;
	private final M_Product_StepDefData productTable;
	private final PP_Order_BOMLine_StepDefData bomLineTable;

	public PP_Cost_Collector_StepDef(
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final PP_Cost_Collector_StepDefData ppCostCollectorTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Order_BOMLine_StepDefData bomLineTable)
	{
		this.ppOrderTable = ppOrderTable;
		this.ppCostCollectorTable = ppCostCollectorTable;
		this.productTable = productTable;
		this.bomLineTable = bomLineTable;
	}

	@And("^after not more than (.*)s, PP_Cost_Collector are found:$")
	public void load_PP_Cost_Collector(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadPPCostCollector(tableRow));

			final String ppCostCollectorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Cost_Collector_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Cost_Collector ppCostCollector = ppCostCollectorTable.get(ppCostCollectorIdentifier);
			assertThat(ppCostCollector).isNotNull();

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_MovementQty);
			final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

			assertThat(ppCostCollector.getMovementQty()).isEqualTo(movementQty);
			assertThat(ppCostCollector.getDocStatus()).isEqualTo(status);
		}
	}

	@NonNull
	private boolean loadPPCostCollector(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_M_Product_ID, ppOrder.getM_Product_ID())
				.addEqualsFilter(COLUMNNAME_DocStatus, status);

		StringUtils.trimBlankToOptional(tableRow.get(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType))
				.map(CostCollectorType::ofNullableCode)
				.ifPresent(costCollectorType -> queryBuilder.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, costCollectorType));

		final IQuery<I_PP_Cost_Collector> query = queryBuilder.create();
		final List<I_PP_Cost_Collector> ppCostCollectors = query.list();
		if (ppCostCollectors.isEmpty())
		{
			return false;
		}
		else if (ppCostCollectors.size() > 1)
		{
			throw new AdempiereException("More than one cost collector found for `" + tableRow)
					.appendParametersToMessage()
					.setParameter("ppCostCollectors", ppCostCollectors)
					.setParameter("query", query);
		}

		final I_PP_Cost_Collector ppCostCollector = ppCostCollectors.get(0);
		final String ppCostCollectorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Cost_Collector_ID + "." + TABLECOLUMN_IDENTIFIER);
		ppCostCollectorTable.put(ppCostCollectorIdentifier, ppCostCollector);

		return true;
	}

	@And("validate I_PP_Cost_Collector")
	public void validate_cost_collector(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order order = ppOrderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_MovementQty);

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = bomLineIdentifier != null ? bomLineTable.get(bomLineIdentifier) : null;

			final I_PP_Cost_Collector costCollector = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
					.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.orderBy(I_PP_Cost_Collector.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(costCollector).isNotNull();
			assertThat(costCollector.isProcessed()).isEqualTo(true);
			assertThat(costCollector.getMovementQty()).isEqualTo(movementQty);
			if (bomLine != null)
			{
				assertThat(costCollector.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			}
		}
	}
}
