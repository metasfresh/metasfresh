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

package de.metas.cucumber.stepdefs;

import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_DocStatus;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_M_Product_ID;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_MovementQty;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID;

public class PP_Cost_Collector_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_PP_Order> ppOrderTable;
	private final StepDefData<I_PP_Cost_Collector> ppCostCollectorTable;
	private final StepDefData<I_M_Product> productTable;

	public PP_Cost_Collector_StepDef(
			@NonNull final StepDefData<I_PP_Order> ppOrderTable,
			@NonNull final StepDefData<I_PP_Cost_Collector> ppCostCollectorTable,
			@NonNull final StepDefData<I_M_Product> productTable)
	{
		this.ppOrderTable = ppOrderTable;
		this.ppCostCollectorTable = ppCostCollectorTable;
		this.productTable = productTable;
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

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_MovementQty);
			final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

			assertThat(ppCostCollector.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(ppCostCollector.getMovementQty()).isEqualTo(movementQty);
			assertThat(ppCostCollector.getDocStatus()).isEqualTo(status);
		}
	}

	@NonNull
	private Boolean loadPPCostCollector(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

		final Optional<I_PP_Cost_Collector> ppCostCollector = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(COLUMNNAME_DocStatus, status)
				.create()
				.firstOnlyOptional(I_PP_Cost_Collector.class);

		if (!ppCostCollector.isPresent())
		{
			return false;
		}

		final String ppCostCollectorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Cost_Collector_ID + "." + TABLECOLUMN_IDENTIFIER);
		ppCostCollectorTable.put(ppCostCollectorIdentifier, ppCostCollector.get());

		return true;
	}
}
