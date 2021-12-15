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

package de.metas.cucumber.stepdefs.invoicecandidate;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.v2.invoice.impl.InvoiceService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);

	private final StepDefData<I_C_Invoice_Candidate> invoiceCandTable;
	private final StepDefData<I_C_Invoice> invoiceTable;
	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final StepDefData<I_M_Product> productTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final StepDefData<I_C_Invoice_Candidate> invoiceCandTable,
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final StepDefData<I_C_BPartner> bPartnerTable,
			@NonNull final StepDefData<I_M_Product> productTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.invoiceTable = invoiceTable;
		this.bPartnerTable = bPartnerTable;
		this.productTable = productTable;
	}

	@And("^locate invoice candidates for invoice: (.*)$")
	public void locate_invoice_candidates_for_invoice(@NonNull final String invoiceIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final List<I_C_Invoice_Candidate> invoiceCandidates = Services.get(IInvoiceCandDAO.class)
				.retrieveInvoiceCandidates(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();

			final I_C_Invoice_Candidate matchingCandidate = invoiceCandidates.stream()
					.filter(candidate -> candidate.getM_Product_ID() == product.getM_Product_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No invoice candidate found for C_Invoice_ID " + invoice.getC_Invoice_ID() + " and m_product_id: " + product.getM_Product_ID()));

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.put(invoiceCandIdentifier, matchingCandidate);
		}
	}

	@And("recompute invoice candidates if required")
	public void recompute_invoice_candidates_if_required(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			boolean recompute = false;

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);
			assertThat(invoiceCandidate).isNotNull();

			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final String billBPIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner billBPartner = bPartnerTable.get(billBPIdentifier);
			assertThat(billBPartner).isNotNull();
			assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

			final BigDecimal netAmountInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtInvoiced);
			if (netAmountInvoiced != null)
			{
				recompute = invoiceCandidate.getNetAmtInvoiced().compareTo(netAmountInvoiced) != 0;
			}

			if (recompute)
			{
				Services.get(IInvoiceCandBL.class)
						.updateInvalid()
						.setContext(Env.getCtx(), null)
						.setTaggedWithAnyTag()
						.setOnlyC_Invoice_Candidates(ImmutableSet.of(invoiceCandidate))
						.update();
			}
		}
	}

	@And("validate invoice candidate")
	public void validate_invoice_candidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);
			assertThat(invoiceCandidate).isNotNull();

			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final String billBPIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner billBPartner = bPartnerTable.get(billBPIdentifier);
			assertThat(billBPartner).isNotNull();
			assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

			final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_NetAmtToInvoice);
			assertThat(invoiceCandidate.getNetAmtToInvoice()).isEqualTo(netAmtToInvoice);

			final BigDecimal netAmountInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtInvoiced);
			if (netAmountInvoiced != null)
			{
				assertThat(invoiceCandidate.getNetAmtInvoiced()).isEqualTo(netAmountInvoiced);
			}

			final boolean isSoTrx = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsSOTrx);
			assertThat(invoiceCandidate.isSOTrx()).isEqualTo(isSoTrx);

		}
	}

	@And("process invoice candidates")
	public void process_invoice_cand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
			invoiceService.processInvoiceCandidates(ImmutableSet.of(invoiceCandidateId));
		}
	}
}
