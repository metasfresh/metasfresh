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

import de.metas.allocation.api.IAllocationDAO;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;

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
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

	final C_Invoice_StepDefData invoiceTable;
	final C_Payment_StepDefData paymentTable;
	final C_AllocationHdr_StepDefData allocationHdrTable;

	public C_AllocationLine_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Payment_StepDefData paymentTable,
			@NonNull final C_AllocationHdr_StepDefData allocationHdrTable
	)
	{
		this.invoiceTable = invoiceTable;
		this.paymentTable = paymentTable;
		this.allocationHdrTable = allocationHdrTable;
	}

	@And("validate C_AllocationLines")
	public void validate_C_AllocationLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer invoiceId = Optional.ofNullable(invoiceIdentifier)
					.map(identifier -> invoiceTable.get(identifier).getC_Invoice_ID())
					.orElse(null);

			final String paymentIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer paymentId = Optional.ofNullable(paymentIdentifier)
					.map(identifier -> paymentTable.get(paymentIdentifier).getC_Payment_ID())
					.orElse(null);

			final I_C_AllocationLine singleAllocationLine = queryBL.createQueryBuilder(I_C_AllocationLine.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoiceId)
					.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, paymentId)
					.create()
					.firstOnlyNotNull(I_C_AllocationLine.class);

			final BigDecimal amount = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_Amount);
			final BigDecimal overUnderAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_OverUnderAmt);
			final BigDecimal writeOffAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_WriteOffAmt);
			final BigDecimal discountAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_DiscountAmt);

			if (amount != null)
			{
				assertThat(singleAllocationLine.getAmount()).isEqualTo(amount);
			}

			if (overUnderAmt != null)
			{
				assertThat(singleAllocationLine.getOverUnderAmt()).isEqualTo(overUnderAmt);
			}

			if (writeOffAmt != null)
			{
				assertThat(singleAllocationLine.getWriteOffAmt()).isEqualTo(writeOffAmt);
			}
			if (discountAmt != null)
			{
				assertThat(singleAllocationLine.getDiscountAmt()).isEqualTo(discountAmt);
			}

			// Store C_AllocationHdr if identifier is provided
			final String allocHdrIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (allocHdrIdentifier != null)
			{
				final I_C_AllocationHdr hdr = InterfaceWrapperHelper.load(singleAllocationLine.getC_AllocationHdr_ID(), I_C_AllocationHdr.class);
				allocationHdrTable.putOrReplace(StepDefDataIdentifier.ofString(allocHdrIdentifier), hdr);
			}
			else
			{
				final String allocHdrIdentifier2 = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID);
				if (allocHdrIdentifier2 != null)
				{
					final I_C_AllocationHdr hdr = InterfaceWrapperHelper.load(singleAllocationLine.getC_AllocationHdr_ID(), I_C_AllocationHdr.class);
					allocationHdrTable.putOrReplace(StepDefDataIdentifier.ofString(allocHdrIdentifier2), hdr);
				}
			}
		}
	}

	@And("^validate C_AllocationLines for invoice (.*)$")
	public void validate_C_AllocationLines_for_invoice(
			@NonNull final String invoiceIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
		final List<I_C_AllocationLine> allocLines = new java.util.ArrayList<>(queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.create()
				.list());

		DataTableRows.of(dataTable).forEach(row -> {
			final BigDecimal expectedAmount = row.getAsOptionalBigDecimal("Amount").orElse(null);

			final I_C_AllocationLine matchingLine = allocLines.stream()
					.filter(line -> expectedAmount == null || line.getAmount().compareTo(expectedAmount) == 0)
					.findFirst()
					.orElseThrow(() -> new AssertionError("No C_AllocationLine found for invoice " + invoiceIdentifier
							+ " with Amount=" + expectedAmount + ". Existing lines: " + allocLines));

			allocLines.remove(matchingLine);

			row.getAsOptionalIdentifier("C_AllocationHdr_ID").ifPresent(allocHdrId -> {
				final I_C_AllocationHdr hdr = InterfaceWrapperHelper.load(matchingLine.getC_AllocationHdr_ID(), I_C_AllocationHdr.class);
				allocationHdrTable.putOrReplace(allocHdrId, hdr);
			});
		});
	}

	@And("there are no allocation lines for invoice")
	public void invoices_are_not_allocated(@NonNull final DataTable table)
	{
		final List<Map<String, String>> rows = table.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

			final List<I_C_AllocationLine> allocationLines = allocationDAO.retrieveAllocationLines(invoice);

			assertThat(allocationLines.isEmpty()).isEqualTo(true);
		}
	}
}
