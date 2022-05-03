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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

public class C_Invoice_StepDef
{
	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;

	public C_Invoice_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable)
	{
		this.invoiceTable = invoiceTable;
		this.bpartnerTable = bpartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
	}

	@And("validate created invoices")
	public void validate_created_invoices(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, "Invoice.Identifier");

			final I_C_Invoice invoice = invoiceTable.get(identifier);

			validateInvoice(invoice, row);
		}
	}

	private void validateInvoice(@NonNull final I_C_Invoice invoice, @NonNull final Map<String, String> row)
	{
		InterfaceWrapperHelper.refresh(invoice);

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerLocationId = bPartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));

		final String poReference = DataTableUtil.extractStringForColumnName(row, "poReference");
		final String paymentTerm = DataTableUtil.extractStringForColumnName(row, "paymentTerm");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
		final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

		assertThat(invoice.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		assertThat(invoice.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
		assertThat(invoice.getPOReference()).isEqualTo(poReference);
		assertThat(invoice.isProcessed()).isEqualTo(processed);
		assertThat(invoice.getDocStatus()).isEqualTo(docStatus);

		final PaymentTermQuery query = PaymentTermQuery.builder()
				.orgId(StepDefConstants.ORG_ID)
				.value(paymentTerm)
				.build();
		final PaymentTermId paymentTermId = paymentTermRepo.retrievePaymentTermId(query)
				.orElse(null);

		assertThat(paymentTermId).isNotNull();
		assertThat(invoice.getC_PaymentTerm_ID()).isEqualTo(paymentTermId.getRepoId());
	}
}
