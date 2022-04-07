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

package de.metas.cucumber.stepdefs.contract.callorder;

import de.metas.contracts.model.I_C_CallOrderSummary;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;

public class C_CallOrderSummary_StepDef
{
	private final C_CallOrderSummary_StepDefData summaryTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public C_CallOrderSummary_StepDef(
			@NonNull final C_CallOrderSummary_StepDefData summaryTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.summaryTable = summaryTable;
		this.contractTable = contractTable;
		this.orderLineTable = orderLineTable;
		this.orderTable = orderTable;
		this.productTable = productTable;
	}

	@And("^validate (created|updated) C_CallOrderSummary:$")
	public void validate_call_order_summary(@NonNull final String action_UNUSED, @NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String callOrderContractIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Term callOrderContract = contractTable.get(callOrderContractIdentifier);
		assertThat(callOrderContract).isNotNull();

		final I_C_CallOrderSummary callOrderSummary = queryBL.createQueryBuilder(I_C_CallOrderSummary.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CallOrderSummary.COLUMNNAME_C_Flatrate_Term_ID, callOrderContract.getC_Flatrate_Term_ID())
				.create()
				.firstOnlyNotNull(I_C_CallOrderSummary.class);

		validateCallOrderSummary(callOrderSummary, row);

		final String summaryIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_C_CallOrderSummary_ID + "." + TABLECOLUMN_IDENTIFIER);
		summaryTable.putOrReplace(summaryIdentifier, callOrderSummary);
	}

	private void validateCallOrderSummary(@NonNull final I_C_CallOrderSummary summary, @NonNull final Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
		assertThat(summary.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order order = orderTable.get(orderIdentifier);
		assertThat(summary.getC_Order_ID()).isEqualTo(order.getC_Order_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();
		assertThat(summary.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_QtyEntered);
		assertThat(summary.getQtyEntered()).isEqualTo(qtyEntered);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
		assertThat(summary.getC_UOM_ID()).isEqualTo(uomId.getRepoId());

		final String contractIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);
		assertThat(contract).isNotNull();
		assertThat(summary.getC_Flatrate_Term_ID()).isEqualTo(contract.getC_Flatrate_Term_ID());

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_CallOrderSummary.COLUMNNAME_QtyDeliveredInUOM);
		if (qtyDelivered != null)
		{
			assertThat(summary.getQtyDeliveredInUOM()).isEqualTo(qtyDelivered);
		}

		final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_CallOrderSummary.COLUMNNAME_QtyInvoicedInUOM);
		if (qtyInvoiced != null)
		{
			assertThat(summary.getQtyInvoicedInUOM()).isEqualTo(qtyInvoiced);
		}

		final boolean soTrx = DataTableUtil.extractBooleanForColumnName(row, I_C_CallOrderSummary.COLUMNNAME_IsSOTrx);
		assertThat(summary.isSOTrx()).isEqualTo(soTrx);
	}
}
