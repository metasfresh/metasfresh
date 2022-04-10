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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_Tax_StepDefData taxTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_Tax_StepDefData taxTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.invoiceTable = invoiceTable;
		this.bPartnerTable = bPartnerTable;
		this.productTable = productTable;
		this.orderLineTable = orderLineTable;
		this.taxTable = taxTable;
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

			final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
			if (netAmtToInvoice != null)
			{
				recompute = invoiceCandidate.getNetAmtToInvoice().compareTo(netAmtToInvoice) != 0;
			}

			if (recompute)
			{
				final InvoiceCandidateIdsSelection onlyInvoiceCandidateIds = InvoiceCandidateIdsSelection.ofIdsSet(
						ImmutableSet.of(InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID())));

				Services.get(IInvoiceCandBL.class)
						.updateInvalid()
						.setContext(Env.getCtx(), null)
						.setTaggedWithAnyTag()
						.setOnlyInvoiceCandidateIds(onlyInvoiceCandidateIds)
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
			invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
		}
	}

	@And("invoice candidates are not billable")
	public void check_not_billable(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

			ImmutableSet<InvoiceId> invoiceIds = ImmutableSet.of();
			try
			{
				invoiceIds = invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
			}
			catch (final AdempiereException adempiereException)
			{
				assertThat(adempiereException.getMessage()).contains("Es wurden keine fakturierbaren Datensätze ausgewählt.");
			}

			assertThat(invoiceIds.size()).isEqualTo(0);
		}
	}

	@And("^after not more than (.*)s locate invoice candidates by order line:$")
	public void locate_invoice_candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			findInvoiceCandidateByOrderLine(timeoutSec, row);
		}
	}

	@And("^after not more than (.*)s, locate C_Invoice_Candidates by externalHeaderId$")
	public void locate_invoice_candidate_by_externalHeaderId(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadInvoiceCandidatesByExternalHeaderId(tableRow));
		}
	}

	@And("^after not more than (.*)s, C_Invoice_Candidates are not marked as 'to recompute'$")
	public void check_not_marked_as_to_recompute(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> checkNotMarkedAsToRecompute(tableRow));
		}
	}

	@And("update invoice candidates")
	public void update_invoice_candidates(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			updateInvoiceCandidates(tableRow);
		}
	}

	private void updateInvoiceCandidates(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice_Override);
		final BigDecimal priceEnteredOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_PriceEntered_Override);
		final BigDecimal discountOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Discount_Override);
		final Timestamp dateToInvoiceOverride = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_DateToInvoice_Override);
		final String invoiceRuleOverride = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule_Override);
		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal qualityDiscountOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QualityDiscountPercent_Override);
		final String docTypeInvoiceName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_DocTypeInvoice_ID + "." + I_C_DocType.COLUMNNAME_Name);

		if (qtyToInvoiceOverride != null)
		{
			invoiceCandidateRecord.setQtyToInvoice_Override(qtyToInvoiceOverride);
		}

		if (priceEnteredOverride != null)
		{
			invoiceCandidateRecord.setPriceEntered_Override(priceEnteredOverride);
		}

		if (discountOverride != null)
		{
			invoiceCandidateRecord.setDiscount_Override(discountOverride);
		}

		if (dateToInvoiceOverride != null)
		{
			invoiceCandidateRecord.setDateToInvoice_Override(dateToInvoiceOverride);
		}

		if (Check.isNotBlank(invoiceRuleOverride))
		{
			invoiceCandidateRecord.setInvoiceRule_Override(invoiceRuleOverride);
		}

		if (Check.isNotBlank(taxIdentifier))
		{
			final I_C_Tax taxRecord = taxTable.get(taxIdentifier);

			invoiceCandidateRecord.setC_Tax_Override_ID(taxRecord.getC_Tax_ID());
		}

		if (qualityDiscountOverride != null)
		{
			invoiceCandidateRecord.setQualityDiscountPercent_Override(qualityDiscountOverride);
		}

		if (Check.isNotBlank(docTypeInvoiceName))
		{
			final DocTypeId docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
					.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, docTypeInvoiceName)
					.create()
					.firstId(DocTypeId::ofRepoIdOrNull);

			assertThat(docTypeId).isNotNull();
			invoiceCandidateRecord.setC_DocTypeInvoice_ID(docTypeId.getRepoId());
		}

		saveRecord(invoiceCandidateRecord);
	}

	private void findInvoiceCandidateByOrderLine(final int timeoutSec, @NonNull final Map<String, String> row) throws InterruptedException
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> invoiceCandDAO
				.retrieveInvoiceCandidatesForOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())).size() > 0);

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO
				.retrieveInvoiceCandidatesForOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));

		assertThat(invoiceCandidates.size()).isEqualTo(1);

		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

		invoiceCandTable.put(invoiceCandidateIdentifier, invoiceCandidates.get(0));
	}

	@NonNull
	private Boolean loadInvoiceCandidatesByExternalHeaderId(@NonNull final Map<String, String> row)
	{
		final String externalHeaderId = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId);

		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ImmutableList<String> invoiceCandidateIdentifiers = StepDefUtil.extractIdentifiers(invoiceCandidateIdentifier);

		final List<I_C_Invoice_Candidate> invoiceCandidates = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId, externalHeaderId)
				.create()
				.list(I_C_Invoice_Candidate.class);

		if (EmptyUtil.isEmpty(invoiceCandidates))
		{
			return false;
		}

		if (invoiceCandidates.size() != invoiceCandidateIdentifiers.size())
		{
			return false;
		}

		for (int invoiceCandidateIndex = 0; invoiceCandidateIndex < invoiceCandidates.size(); invoiceCandidateIndex++)
		{
			invoiceCandTable.putOrReplace(invoiceCandidateIdentifiers.get(invoiceCandidateIndex), invoiceCandidates.get(invoiceCandidateIndex));
		}

		return true;
	}

	@NonNull
	private Boolean checkNotMarkedAsToRecompute(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		return !invoiceCandDAO.isToRecompute(invoiceCandidateRecord);
	}

	@And("^after not more than (.*)s, C_Invoice_Candidates are found:$")
	public void thereAreInvoiceCandidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> retrieveInvoiceCandidate(row));
		}
	}

	private boolean retrieveInvoiceCandidate(@NonNull final Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final IQueryBuilder<I_C_Invoice_Candidate> candQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderLine.getC_Order_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
		if (qtyToInvoice != null)
		{
			candQueryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, qtyToInvoice);
		}

		final I_C_Invoice_Candidate invoiceCandidate = candQueryBuilder.create()
				.firstOnlyOrNull(I_C_Invoice_Candidate.class);

		if (invoiceCandidate == null)
		{
			return false;
		}

		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		return true;
	}
}