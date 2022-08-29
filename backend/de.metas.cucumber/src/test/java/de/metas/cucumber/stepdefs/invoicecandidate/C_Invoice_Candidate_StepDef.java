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

package de.metas.cucumber.stepdefs.invoicecandidate;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.iinvoicecandidate.I_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LogbackLoggable;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Async_Batch_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInDispute;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Processed;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyEntered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_Tax_StepDefData taxTable;
	private final I_Invoice_Candidate_StepDefData iInvoiceCandidatetable;
	private final AD_User_StepDefData contactTable;
	private final C_DocType_StepDefData docTypeTable;
	private final C_UOM_StepDefData uomTable;
	private final AD_Org_StepDefData orgTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_UOM_StepDefData uomTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.invoiceTable = invoiceTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.taxTable = taxTable;
		this.iInvoiceCandidatetable = iInvoiceCandidateTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
		this.orgTable = orgTable;
	}

	@And("^locate invoice candidates for invoice: (.*)$")
	public void locate_invoice_candidates_for_invoice(@NonNull final String invoiceIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidates(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

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

	@And("^after not more than (.*)s, C_Invoice_Candidate are found:$")
	public void find_C_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> load_C_Invoice_Candidate(row));
		}
	}

	@And("update C_Invoice_Candidate:")
	public void update_C_Invoice_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				invoiceCandidate.setQtyToInvoice_Override(qtyToInvoiceOverride);
			}

			final String invoiceRuleOverride = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule_Override);
			if (Check.isNotBlank(invoiceRuleOverride))
			{
				invoiceCandidate.setInvoiceRule_Override(invoiceRuleOverride);
			}

			InterfaceWrapperHelper.saveRecord(invoiceCandidate);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	@And("^there is no C_Invoice_Candidate for C_Order (.*)$")
	public void validate_no_C_Invoice_Candidate_created(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		final I_C_Invoice_Candidate candidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		Assertions.assertThat(candidate).isNull();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(order);
		Assertions.assertThat(invoiceCandidates).isEmpty();
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

			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final String billBPIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer billBPartnerId = bPartnerTable.getOptional(billBPIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(billBPIdentifier));
			assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartnerId);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer productId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));
			assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(productId);

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
						.setContext(Env.getCtx(), ITrx.TRXNAME_None)
						.setTaggedWithAnyTag()
						.setOnlyInvoiceCandidateIds(onlyInvoiceCandidateIds)
						.update();
			}
		}
	}

	@Then("validate invoice candidate")
	public void validate_invoice_candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(30, 500, () -> isInvoiceCandidateUpdated(row));

			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final String billBPIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(billBPIdentifier))
			{
				final I_C_BPartner billBPartner = bPartnerTable.get(billBPIdentifier);
				assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());
			}

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			}

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(invoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				assertThat(invoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
			if (qtyOrdered != null)
			{
				assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
			}

			final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
			if (qtyDelivered != null)
			{
				assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
			}

			final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
			if (qtyToInvoice != null)
			{
				assertThat(invoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);
			}

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				assertThat(invoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
			}

			final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
			if (netAmtToInvoice != null)
			{
				assertThat(invoiceCandidate.getNetAmtToInvoice()).isEqualTo(netAmtToInvoice);
			}
			final BigDecimal netAmountInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtInvoiced);
			if (netAmountInvoiced != null)
			{
				assertThat(invoiceCandidate.getNetAmtInvoiced()).isEqualTo(netAmountInvoiced);
			}

			final Boolean isSoTrx = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsSOTrx, null);
			if (isSoTrx != null)
			{
				assertThat(invoiceCandidate.isSOTrx()).isEqualTo(isSoTrx);
			}
		}
	}

	@And("validate C_Invoice_Candidate:")
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(30, 500, () -> isInvoiceCandidateUpdated(row));

			validate_C_invoice_Candidate(invoiceCandidate, row);
		}
	}

	@And("process invoice candidates")
	public void process_invoice_cand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			try (final IAutoCloseable ignore = Loggables.temporarySetLoggable(new LogbackLoggable(logger, Level.INFO)))
			{
				final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
				invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
			}
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

	@And("^after not more than (.*)s locate invoice candidates by order id:$")
	public void locate_invoice_candidate_by_order_id(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			findInvoiceCandidateByOrderId(timeoutSec, row);
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
			final Runnable logContext = () -> logger.error("C_Invoice_Candidate not found\n"
																   + "**tableRow:**\n{}\n"
																   + "**all candidates:**\n{}",
														   tableRow, Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class).create().list());
			StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> isInvoiceCandidateUpdated(tableRow), logContext);
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

	@And("^process invoice candidates and wait (.*)s for C_Invoice_Candidate to be processed$")
	public void process_invoice_cand(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);
			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

			final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateId.getRepoId()), Trx.TRXNAME_None);

			final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
			invoicingParams.setIgnoreInvoiceSchedule(false);
			invoicingParams.setSupplementMissingPaymentTermIds(true);

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> checkNotMarkedAsToRecompute(invoiceCandidate));

			try
			{
				invoiceCandBL.enqueueForInvoicing()
						.setContext(Env.getCtx())
						.setFailIfNothingEnqueued(true)
						.setInvoicingParams(invoicingParams)
						.enqueueSelection(invoiceCandidatesSelectionId);
			}
			catch (final AdempiereException adempiereException)
			{
				logCurrentContext(invoiceCandidate, row);
				throw adempiereException;
			}

			//wait for the invoice to be created
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> isInvoiceCandidateProcessed(invoiceCandidate, row), () -> logCurrentContext(invoiceCandidate, row));

			DB.deleteT_Selection(invoiceCandidatesSelectionId, Trx.TRXNAME_None);
		}
	}

	@And("validate invoice candidates by record reference:")
	public void locate_invoice_candidate_by_record_id(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadAndValidateInvoiceCandidateByRecordId(row);
		}
	}

	@And("^after not more than (.*)s C_Invoice_Candidate matches:$")
	public void wait_for_candidate_to_match(final int maxSecondsToWait, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> getInvoiceCandidateIfMatches(row));

			assertThat(invoiceCandidate).isNotNull();
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

	private void findInvoiceCandidateByOrderId(final int timeoutSec, @NonNull final Map<String, String> row) throws InterruptedException
	{
		final String invoiceCandidateIdentifierCandidate = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final ImmutableList<String> invoiceCandidateIdentifiers = StepDefUtil.extractIdentifiers(invoiceCandidateIdentifierCandidate);

		if (invoiceCandidateIdentifiers.isEmpty())
		{
			throw new RuntimeException("No invoice candidate identifier present for column: " + COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		}

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer orderId = orderTable.getOptional(orderIdentifier)
				.map(I_C_Order::getC_Order_ID)
				.orElseGet(() -> Integer.parseInt(orderIdentifier));

		final Supplier<ImmutableList<I_C_Invoice_Candidate>> candidatesSupplier = () -> queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.listImmutable(I_C_Invoice_Candidate.class);

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> candidatesSupplier.get().size() == invoiceCandidateIdentifiers.size(), () -> logCurrentContext(candidatesSupplier, orderId));

		final ImmutableList<I_C_Invoice_Candidate> invoiceCandidates = candidatesSupplier.get();

		for (int invoiceCandidateIndex = 0; invoiceCandidateIndex < invoiceCandidates.size(); invoiceCandidateIndex++)
		{
			invoiceCandTable.putOrReplace(invoiceCandidateIdentifiers.get(invoiceCandidateIndex), invoiceCandidates.get(invoiceCandidateIndex));
		}
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

	@And("^after not more than (.*)s, C_Invoice_Candidates are found:$")
	public void thereAreInvoiceCandidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final IQuery<I_C_Invoice_Candidate> candidatesQuery = createInvoiceCandidateQuery(row);
			final Runnable logContext = () -> logger.error("C_Invoice_Candidate not found\n"
																   + "**tableRow:**\n{}\n" + "**candidatesQuery:**\n{}\n"
																   + "**query result:**\n{}\n"
																   + "**all candidates:**\n{}",
														   row, candidatesQuery,
														   candidatesQuery.list(),
														   Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class).create().list());
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> retrieveInvoiceCandidate(row, candidatesQuery), logContext);

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> retrieveInvoiceCandidate(
			final @NonNull Map<String, String> row,
			final @NonNull IQuery<I_C_Invoice_Candidate> candidateIQuery)
	{
		final I_C_Invoice_Candidate invoiceCandidate = candidateIQuery.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		if (invoiceCandidate == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No invoice candidate found for query " + candidateIQuery);
		}
		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidate);
	}

	private IQuery<I_C_Invoice_Candidate> createInvoiceCandidateQuery(final @NonNull Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final IQueryBuilder<I_C_Invoice_Candidate> candQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderLine.getC_Order_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
			candQueryBuilder.addEqualsFilter(COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID());
		}

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpartnerLocationIdentifier))
		{
			final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpartnerLocationIdentifier);
			candQueryBuilder.addEqualsFilter(COLUMNNAME_Bill_Location_ID, bPartnerLocation.getC_BPartner_Location_ID());
		}

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
		if (qtyToInvoice != null)
		{
			candQueryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, qtyToInvoice);
		}

		return candQueryBuilder.create();
	}

	private boolean load_C_Invoice_Candidate(@NonNull final Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, qtyToInvoice)
				.create()
				.firstOnlyOptional(I_C_Invoice_Candidate.class);

		if (!invoiceCandidate.isPresent())
		{
			return false;
		}

		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate.get());

		return true;
	}

	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateProcessed(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InterfaceWrapperHelper.refresh(invoiceCandidate);
		if (invoiceCandidate.isProcessed())
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidate);
		}
		return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidate.getC_Invoice_Candidate_ID() + " has Processed='N'");
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		if (invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
		}
		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
	}

	private boolean isInvoiceCandidateProcessed(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final Map<String, String> row)
	{
		InterfaceWrapperHelper.refresh(invoiceCandidate);

		final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);

		if (qtyInvoiced != null)
		{
			return invoiceCandidate.getQtyInvoiced().equals(qtyInvoiced);
		}
		else
		{
			return invoiceCandidate.isProcessed();
		}
	}

	private void logCurrentContext(
			@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord,
			@NonNull final Map<String, String> row)
	{
		final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);

		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_Invoice_Candidate_ID).append(" : ").append(invoiceCandidateRecord.getC_Invoice_Candidate_ID()).append("\n");

		if (qtyInvoiced != null)
		{
			message.append(COLUMNNAME_QtyInvoiced).append(" : ").append(qtyInvoiced).append("\n")
					.append("C_Invoice_Candidate record:").append("\n");
		}
		else
		{
			message.append(COLUMNNAME_Processed).append(" : ").append("TRUE");

		}

		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		message.append(COLUMNNAME_Processed).append(" : ").append(invoiceCandidateRecord.isProcessed()).append(" ; ")
				.append(COLUMNNAME_QtyInvoiced).append(" : ").append(invoiceCandidateRecord.getQtyInvoiced()).append(" ; ")
				.append(COLUMNNAME_M_Product_ID).append(" : ").append(invoiceCandidateRecord.getM_Product_ID()).append(" ; ")
				.append(COLUMNNAME_QtyOrdered).append(" : ").append(invoiceCandidateRecord.getQtyOrdered()).append(" ; ")
				.append(COLUMNNAME_QtyEntered).append(" : ").append(invoiceCandidateRecord.getQtyEntered()).append(" ; ")
				.append(COLUMNNAME_QtyToInvoice).append(" : ").append(invoiceCandidateRecord.getQtyToInvoice()).append(" ; ")
				.append(COLUMNNAME_QtyDelivered).append(" : ").append(invoiceCandidateRecord.getQtyDelivered()).append(" ; ")
				.append(COLUMNNAME_QtyToInvoice_Override).append(" : ").append(invoiceCandidateRecord.getQtyToInvoice_Override()).append(" ; ")
				.append(COLUMNNAME_InvoiceRule_Override).append(" : ").append(invoiceCandidateRecord.getInvoiceRule_Override()).append(" ; ")
				.append(COLUMNNAME_LineNetAmt).append(" : ").append(invoiceCandidateRecord.getLineNetAmt()).append(" ; ")
				.append(COLUMNNAME_NetAmtToInvoice).append(" : ").append(invoiceCandidateRecord.getNetAmtToInvoice()).append(" ; ")
				.append(COLUMNNAME_NetAmtInvoiced).append(" : ").append(invoiceCandidateRecord.getNetAmtInvoiced()).append(" ; ")
				.append(COLUMNNAME_IsSOTrx).append(" : ").append(invoiceCandidateRecord.isSOTrx()).append(" ; ")
				.append(COLUMNNAME_IsInDispute).append(" : ").append(invoiceCandidateRecord.isInDispute()).append(" ; ")
				.append(COLUMNNAME_ApprovalForInvoicing).append(" : ").append(invoiceCandidateRecord.isApprovalForInvoicing()).append(" ; ")
				.append(COLUMNNAME_C_Async_Batch_ID).append(" : ").append(invoiceCandidateRecord.getC_Async_Batch_ID()).append(" ; ")
				.append(COLUMNNAME_InvoiceRule).append(" : ").append(invoiceCandidateRecord.getInvoiceRule()).append(" ; ");

		logger.error("*** Error while looking for C_Invoice_Candidate record, see current context: \n" + message);
	}

	@NonNull
	private Boolean checkNotMarkedAsToRecompute(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return !invoiceCandDAO.isToRecompute(invoiceCandidate);
	}

	private void logCurrentContext(
			@NonNull final Supplier<ImmutableList<I_C_Invoice_Candidate>> candidatesSupplier,
			@NonNull final Integer orderId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_Order_ID).append(" : ").append(orderId).append("\n");

		message.append("C_Invoice_Candidate records:").append("\n");

		candidatesSupplier.get()
				.forEach(eventLogEntry -> message
						.append(COLUMNNAME_C_Invoice_Candidate_ID).append(" : ").append(eventLogEntry.getC_Invoice_Candidate_ID()).append(" ; ")
						.append(COLUMNNAME_M_Product_ID).append(" : ").append(eventLogEntry.getM_Product_ID()).append(" ; ")
						.append(COLUMNNAME_QtyEntered).append(" : ").append(eventLogEntry.getQtyEntered()).append(" ; ")
						.append(COLUMNNAME_QtyInvoiced).append(" : ").append(eventLogEntry.getQtyInvoiced()).append(" ; ")
						.append(COLUMNNAME_QtyOrdered).append(" : ").append(eventLogEntry.getQtyOrdered()).append(" ; ")
						.append(COLUMNNAME_QtyDelivered).append(" : ").append(eventLogEntry.getQtyDelivered()).append(" ; ")
						.append(COLUMNNAME_Processed).append(" : ").append(eventLogEntry.isProcessed()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for C_Invoice_Candidate records, see current context: \n" + message);
	}

	private void validate_C_invoice_Candidate(@NonNull final I_C_Invoice_Candidate invoiceCandidate, @NonNull final Map<String, String> row)
	{
		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
		if (qtyToInvoice != null)
		{
			assertThat(invoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);
		}

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null)
		{
			assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
		}

		final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
		if (qtyToInvoiceOverride != null)
		{
			assertThat(invoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
		}

		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderIdentifier))
		{
			final I_C_Order order = orderTable.get(orderIdentifier);
			assertThat(invoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
		}

		final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderLineIdentifier))
		{
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
			assertThat(invoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_PaymentRule);

		if (Check.isNotBlank(paymentRule))
		{
			assertThat(invoiceCandidate.getPaymentRule()).isEqualTo(paymentRule);
		}

		final String billBPartnerContactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billBPartnerContactIdentifier))
		{
			final I_AD_User contact = contactTable.get(billBPartnerContactIdentifier);
			assertThat(contact).isNotNull();
			assertThat(invoiceCandidate.getBill_User_ID()).isEqualTo(contact.getAD_User_ID());
		}

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
		}

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID()));
		final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
		if (dateOrdered != null)
		{
			assertThat(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), zoneId)).isEqualTo(TimeUtil.asLocalDate(dateOrdered, zoneId));
		}

		final Timestamp presetDateInvoiced = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
		if (presetDateInvoiced != null)
		{
			assertThat(TimeUtil.asLocalDate(invoiceCandidate.getPresetDateInvoiced(), zoneId)).isEqualTo(TimeUtil.asLocalDate(presetDateInvoiced, zoneId));
		}

		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);
		if (Check.isNotBlank(poReference))
		{
			assertThat(invoiceCandidate.getPOReference()).isEqualTo(poReference);
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			assertThat(invoiceCandidate.getDescription()).isEqualTo(description);
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docType = docTypeTable.get(docTypeIdentifier);
			assertThat(docType).isNotNull();
			assertThat(invoiceCandidate.getC_DocTypeInvoice_ID()).isEqualTo(docType.getC_DocType_ID());
		}

		final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(uomIdentifier))
		{
			final I_C_UOM uom = uomTable.get(uomIdentifier);
			assertThat(uom).isNotNull();
			assertThat(invoiceCandidate.getC_UOM_ID()).isEqualTo(uom.getC_UOM_ID());
		}
	}

	private void validate_C_Invoice_Candidate_mandatory_fields(@NonNull final I_C_Invoice_Candidate invoiceCandidate, @NonNull final Map<String, String> row)
	{
		final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner billBPartner = bPartnerTable.get(billBPartnerIdentifier);
		assertThat(billBPartner).isNotNull();
		assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());

		final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
		assertThat(bPartnerLocation).isNotNull();
		assertThat(invoiceCandidate.getBill_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

		final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Org org = orgTable.get(orgIdentifier);
		assertThat(org).isNotNull();
		assertThat(invoiceCandidate.getAD_Org_ID()).isEqualTo(org.getAD_Org_ID());

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(invoiceRule);

		final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);
		assertThat(invoiceCandidate.isSOTrx()).isEqualTo(isSOTrx);
	}

	private void loadAndValidateInvoiceCandidateByRecordId(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, "TableName");

		if (tableName.equals(I_I_Invoice_Candidate.Table_Name))
		{
			final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_I_Invoice_Candidate iIInvoiceCandidate = iInvoiceCandidatetable.get(iInvoiceCandIdentifier);
			assertThat(iIInvoiceCandidate).isNotNull();

			final int tableId = tableDAO.retrieveTableId(tableName);
			final TableRecordReference recordReference = TableRecordReference.of(tableId, iIInvoiceCandidate.getI_Invoice_Candidate_ID());

			final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.retrieveReferencing(recordReference);

			validate_C_invoice_Candidate(invoiceCandidateRecords.get(0), row);
			validate_C_Invoice_Candidate_mandatory_fields(invoiceCandidateRecords.get(0), row);

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidateRecords.get(0));
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> getInvoiceCandidateIfMatches(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);
		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice);

		if (qtyToInvoice == null)
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
		}
		else if (qtyToInvoice.compareTo(invoiceCandidateRecord.getQtyToInvoice()) == 0)
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
		}

		return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not matching criteria yet");
	}
}
