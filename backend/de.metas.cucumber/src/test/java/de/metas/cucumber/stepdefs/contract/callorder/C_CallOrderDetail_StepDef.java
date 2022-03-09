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

import de.metas.contracts.model.I_C_CallOrderDetail;
import de.metas.contracts.model.I_C_CallOrderSummary;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_CallOrderDetail_StepDef
{
	private final StepDefData<I_C_CallOrderSummary> callOrderSummaryTable;
	private final StepDefData<I_C_CallOrderDetail> callOrderDetailTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_OrderLine> orderLineTable;
	private final StepDefData<I_M_InOut> inOutTable;
	private final StepDefData<I_M_InOutLine> inOutLineTable;
	private final StepDefData<I_C_Invoice> invoiceTable;
	private final StepDefData<I_C_InvoiceLine> invoiceLineTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public C_CallOrderDetail_StepDef(
			@NonNull final StepDefData<I_C_CallOrderSummary> callOrderSummaryTable,
			@NonNull final StepDefData<I_C_CallOrderDetail> callOrderDetailTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_OrderLine> orderLineTable,
			@NonNull final StepDefData<I_M_InOut> inOutTable,
			@NonNull final StepDefData<I_M_InOutLine> inOutLineTable,
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final StepDefData<I_C_InvoiceLine> invoiceLineTable)
	{
		this.callOrderSummaryTable = callOrderSummaryTable;
		this.callOrderDetailTable = callOrderDetailTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.inOutTable = inOutTable;
		this.inOutLineTable = inOutLineTable;
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
	}

	@And("^validate C_CallOrderDetail for (.*):$")
	public void validate_C_CallOrderDetail(final @NonNull String summaryIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_CallOrderSummary callOrderSummary = callOrderSummaryTable.get(summaryIdentifier);
		assertThat(callOrderSummary).isNotNull();

		final List<I_C_CallOrderDetail> callOrderDetails = queryBL.createQueryBuilder(I_C_CallOrderDetail.class)
				.addInArrayFilter(I_C_CallOrderDetail.COLUMNNAME_C_CallOrderSummary_ID, callOrderSummary.getC_CallOrderSummary_ID())
				.orderBy(I_C_CallOrderDetail.COLUMNNAME_Created)
				.create()
				.list();

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		assertThat(callOrderDetails.size()).isEqualTo(tableRows.size());

		for (int callOrderDetailsIndex = 0; callOrderDetailsIndex < callOrderDetails.size(); callOrderDetailsIndex++)
		{
			final I_C_CallOrderDetail actualCallOrderDetail = callOrderDetails.get(callOrderDetailsIndex);
			final Map<String, String> expectedCallOrderDetail = tableRows.get(callOrderDetailsIndex);

			final String x12de355UOMCode = DataTableUtil.extractStringForColumnName(expectedCallOrderDetail, I_C_CallOrderDetail.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355UOMCode));
			assertThat(actualCallOrderDetail.getC_UOM_ID()).isEqualTo(uomId.getRepoId());

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(actualCallOrderDetail.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				assertThat(actualCallOrderDetail.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_QtyEntered);
			if (qtyEntered != null)
			{
				assertThat(actualCallOrderDetail.getQtyEntered()).isEqualTo(qtyEntered);
			}

			final String inOutIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(inOutIdentifier))
			{
				final I_M_InOut inOut = inOutTable.get(inOutIdentifier);
				assertThat(actualCallOrderDetail.getM_InOut_ID()).isEqualTo(inOut.getM_InOut_ID());
			}

			final String inOutLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(inOutLineIdentifier))
			{
				final I_M_InOutLine inOutLine = inOutLineTable.get(inOutLineIdentifier);
				assertThat(actualCallOrderDetail.getM_InOutLine_ID()).isEqualTo(inOutLine.getM_InOutLine_ID());
			}

			final BigDecimal qtyDeliveredInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_QtyDeliveredInUOM);
			if (qtyDeliveredInUOM != null)
			{
				assertThat(actualCallOrderDetail.getQtyDeliveredInUOM()).isEqualTo(qtyDeliveredInUOM);
			}

			final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(invoiceIdentifier))
			{
				final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
				assertThat(actualCallOrderDetail.getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
			}

			final String invoiceLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_C_InvoiceLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(invoiceLineIdentifier))
			{
				final I_C_InvoiceLine invoiceLine = invoiceLineTable.get(invoiceLineIdentifier);
				assertThat(actualCallOrderDetail.getC_InvoiceLine_ID()).isEqualTo(invoiceLine.getC_InvoiceLine_ID());
			}

			final BigDecimal qtyInvoicedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(expectedCallOrderDetail, "OPT." + I_C_CallOrderDetail.COLUMNNAME_QtyInvoicedInUOM);
			if (qtyInvoicedInUOM != null)
			{
				assertThat(actualCallOrderDetail.getQtyInvoicedInUOM()).isEqualTo(qtyInvoicedInUOM);
			}

			final String detailIdentifier = DataTableUtil.extractStringForColumnName(expectedCallOrderDetail, I_C_CallOrderDetail.COLUMNNAME_C_CallOrderDetail_ID + "." + TABLECOLUMN_IDENTIFIER);
			callOrderDetailTable.putOrReplace(detailIdentifier, actualCallOrderDetail);
		}
	}
}
