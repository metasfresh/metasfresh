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

package de.metas.cucumber.stepdefs.interiminvoice.flatrateterm;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm_Line;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID;
import static org.compiere.model.I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Interim_Invoice_Candidate_ID;
import static org.compiere.model.I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Invoice_Candidate_Withholding_ID;
import static org.compiere.model.I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_QtyDeliveredInUOM;
import static org.compiere.model.I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_QtyInvoiced;

public class C_InterimInvoice_FlatrateTerm_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_InterimInvoice_FlatrateTerm_StepDefData interimInvoiceTermTable;
	private final M_InOut_StepDefData inoutTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final C_InterimInvoice_FlatrateTerm_Line_StepDefData interimInvoiceTermLineTable;

	public C_InterimInvoice_FlatrateTerm_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_InterimInvoice_FlatrateTerm_StepDefData interimInvoiceTermTable,
			@NonNull final M_InOut_StepDefData inoutTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_InvoiceLine_StepDefData invoiceLineTable,
			@NonNull final C_InterimInvoice_FlatrateTerm_Line_StepDefData interimInvoiceTermLineTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.interimInvoiceTermTable = interimInvoiceTermTable;
		this.inoutTable = inoutTable;
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
		this.interimInvoiceTermLineTable = interimInvoiceTermLineTable;
	}

	@And("validate C_InterimInvoice_FlatrateTerm:")
	public void validate_C_InterimInvoice_FlatrateTerm(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String interimInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Interim_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate interimInvoiceCand = invoiceCandTable.get(interimInvoiceCandIdentifier);

			final String withholdingInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Invoice_Candidate_Withholding_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate withholdingInvoiceCand = invoiceCandTable.get(withholdingInvoiceCandIdentifier);

			final I_C_InterimInvoice_FlatrateTerm interimInvoiceFlatrateTerm = queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm.class)
					.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Interim_Invoice_Candidate_ID, interimInvoiceCand.getC_Invoice_Candidate_ID())
					.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Invoice_Candidate_Withholding_ID, withholdingInvoiceCand.getC_Invoice_Candidate_ID())
					.create()
					.firstOnly(I_C_InterimInvoice_FlatrateTerm.class);

			assertThat(interimInvoiceFlatrateTerm).isNotNull();

			final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyDeliveredInUOM);
			final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyInvoiced);

			assertThat(interimInvoiceFlatrateTerm.getQtyDeliveredInUOM()).isEqualTo(qtyDelivered);
			assertThat(interimInvoiceFlatrateTerm.getQtyInvoiced()).isEqualTo(qtyToInvoice);

			final String interimInvoiceTermIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID + "." + TABLECOLUMN_IDENTIFIER);
			interimInvoiceTermTable.putOrReplace(interimInvoiceTermIdentifier, interimInvoiceFlatrateTerm);
		}
	}

	@And("validate C_InterimInvoice_FlatrateTerm_Line:")
	public void validate_C_InterimInvoice_FlatrateTerm_Line(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String interimInvoiceTermIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_InterimInvoice_FlatrateTerm interimInvoiceTerm = interimInvoiceTermTable.get(interimInvoiceTermIdentifier);

			final String inoutIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut inout = inoutTable.get(inoutIdentifier);

			final I_C_InterimInvoice_FlatrateTerm_Line interimInvoiceTermLine = queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm_Line.class)
					.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, interimInvoiceTerm.getC_InterimInvoice_FlatrateTerm_ID())
					.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID())
					.create()
					.firstOnlyNotNull(I_C_InterimInvoice_FlatrateTerm_Line.class);

			final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InvoiceLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_InvoiceLine invoiceLine = invoiceLineTable.get(invoiceLineIdentifier);

			assertThat(interimInvoiceTermLine.getC_InvoiceLine_ID()).isEqualTo(invoiceLine.getC_InvoiceLine_ID());

			final String interimInvoiceTermLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID + "." + TABLECOLUMN_IDENTIFIER);
			interimInvoiceTermLineTable.put(interimInvoiceTermLineIdentifier, interimInvoiceTermLine);
		}
	}
}
