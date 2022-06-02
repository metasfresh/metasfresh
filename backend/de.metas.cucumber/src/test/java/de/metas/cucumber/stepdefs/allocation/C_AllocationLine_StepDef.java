/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.allocation;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;

public class C_AllocationLine_StepDef
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final C_Invoice_StepDefData invoiceTable;
	final C_Payment_StepDefData paymentTable;

	public C_AllocationLine_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Payment_StepDefData paymentTable
	)
	{
		this.invoiceTable = invoiceTable;
		this.paymentTable = paymentTable;
	}

	@And("validate C_AllocationLines")
	public void validate_C_AllocationLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			final IQueryBuilder<I_C_AllocationLine> allocationLineQueryBL = queryBL.createQueryBuilder(I_C_AllocationLine.class);

			final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (invoiceIdentifier != null)
			{
				final Optional<I_C_Invoice> invoiceOpt = invoiceTable.getOptional(invoiceIdentifier);
				invoiceOpt.ifPresent(invoice -> allocationLineQueryBL.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID()));
			}
			else
			{
				allocationLineQueryBL.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, null);
			}

			final String paymentIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (paymentIdentifier != null)
			{
				final Optional<I_C_Payment> paymentOpt = paymentTable.getOptional(paymentIdentifier);
				paymentOpt.ifPresent(payment -> allocationLineQueryBL.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID()));
			}
			else
			{
				allocationLineQueryBL.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, null);
			}

			final I_C_AllocationLine foundAllocationLine = allocationLineQueryBL.create().firstOnlyNotNull(I_C_AllocationLine.class);

			final BigDecimal amount = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_Amount);
			final BigDecimal overUnderAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_OverUnderAmt);
			final BigDecimal writeOffAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_WriteOffAmt);
			final BigDecimal discountAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_DiscountAmt);

			if (amount != null)
			{
				assertThat(foundAllocationLine.getAmount()).isEqualTo(amount);
			}

			if (overUnderAmt != null)
			{
				assertThat(foundAllocationLine.getOverUnderAmt()).isEqualTo(overUnderAmt);
			}

			if (writeOffAmt != null)
			{
				assertThat(foundAllocationLine.getWriteOffAmt()).isEqualTo(writeOffAmt);
			}
			if (discountAmt != null)
			{
				assertThat(foundAllocationLine.getDiscountAmt()).isEqualTo(discountAmt);
			}

		}
	}

}
