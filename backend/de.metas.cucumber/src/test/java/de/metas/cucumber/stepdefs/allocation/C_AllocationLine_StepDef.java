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

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;

@RequiredArgsConstructor
public class C_AllocationLine_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationHdrTable;

	@And("validate C_AllocationLines")
	public void validate_C_AllocationLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validate_C_AllocationLine);
	}

	private void validate_C_AllocationLine(final DataTableRow row)
	{
		final InvoiceId invoiceId = row.getAsOptionalIdentifier(COLUMNNAME_C_Invoice_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(invoiceTable::getId)
				.orElse(null);
		final PaymentId paymentId = row.getAsOptionalIdentifier(COLUMNNAME_C_Payment_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(paymentTable::getId)
				.orElse(null);

		final I_C_AllocationLine singleAllocationLine = queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.firstOnlyNotNull(I_C_AllocationLine.class);

		validateAllocationLine(singleAllocationLine, row);
	}

	@And("^validate C_AllocationLines for invoice (.*)$")
	public void validate_C_AllocationLines_for_invoice(
			@NonNull final String invoiceIdentifier,
			@NonNull final DataTable dataTable)
	{
		final Integer invoiceId = invoiceTable.get(invoiceIdentifier).getC_Invoice_ID();

		final ImmutableList<I_C_AllocationLine> allocationLines = queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.orderBy(I_C_AllocationLine.COLUMN_C_AllocationLine_ID)
				.create()
				.listImmutable(I_C_AllocationLine.class);

		assertThat(allocationLines).hasSameSizeAs(dataTable.asMaps());

		DataTableRows.of(dataTable)
				.forEach((row, index) -> validateAllocationLine(allocationLines.get(index), row));
	}

	@And("there are no allocation lines for invoice")
	public void invoices_are_not_allocated(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final I_C_Invoice invoice = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);
			final List<I_C_AllocationLine> allocationLines = allocationDAO.retrieveAllocationLines(invoice);
			assertThat(allocationLines).isEmpty();
		});
	}

	private void validateAllocationLine(
			@NonNull final I_C_AllocationLine allocationLine,
			@NonNull final DataTableRow row)
	{
		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal(I_C_AllocationLine.COLUMNNAME_Amount)
				.ifPresent(amount -> softly.assertThat(allocationLine.getAmount()).as("Amount").isEqualTo(amount));
		row.getAsOptionalBigDecimal(I_C_AllocationLine.COLUMNNAME_DiscountAmt)
				.ifPresent(amount -> softly.assertThat(allocationLine.getDiscountAmt()).as("DiscountAmt").isEqualTo(amount));
		row.getAsOptionalBigDecimal(I_C_AllocationLine.COLUMNNAME_WriteOffAmt)
				.ifPresent(amount -> softly.assertThat(allocationLine.getWriteOffAmt()).as("WriteOffAmt").isEqualTo(amount));
		row.getAsOptionalBigDecimal(I_C_AllocationLine.COLUMNNAME_OverUnderAmt)
				.ifPresent(amount -> softly.assertThat(allocationLine.getOverUnderAmt()).as("OverUnderAmt").isEqualTo(amount));

		softly.assertAll();

		row.getAsOptionalIdentifier(I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID)
				.ifPresent(allocationIdentifier -> allocationHdrTable.putOrReplaceIfSameId(allocationIdentifier, allocationLine.getC_AllocationHdr()));

	}
}
