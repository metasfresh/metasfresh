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
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LogbackLoggable;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Effective_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInDispute;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsToClear;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Processed;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDeliveredInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyEntered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoicedInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyPickedInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM_Calc;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyWithIssues_Effective;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_Tax_StepDefData taxTable;
	private final M_InOutLine_StepDefData inoutLineTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final M_InOutLine_StepDefData inoutLineTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.invoiceTable = invoiceTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.taxTable = taxTable;
		this.inoutLineTable = inoutLineTable;
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
	public void find_C_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws Throwable
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 1000, () -> load_C_Invoice_Candidate(row));
			}
			catch (final Throwable exception)
			{
				manuallyRecomputeInvoiceCandidate(exception, row, timeoutSec);

				StepDefUtil.tryAndWait(5, 1000, () -> load_C_Invoice_Candidate(row));
			}
		}
	}

	@And("validate C_Invoice_Candidates does not exist")
	public void validate_no_created_C_Invoice_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final I_C_Invoice_Candidate invoiceCandidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
					.addEqualsFilter(COLUMNNAME_C_Order_ID, order.getC_Order_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.create()
					.firstOnlyOrNull(I_C_Invoice_Candidate.class);

			assertThat(invoiceCandidate).isNull();
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
	public void validate_invoice_candidate(@NonNull final DataTable dataTable) throws Throwable
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

			final int maxSecondsToWait = 120;
			I_C_Invoice_Candidate updatedInvoiceCandidate;
			try
			{
				updatedInvoiceCandidate = StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> isInvoiceCandidateUpdated(row));
			}
			catch (final Throwable e)
			{
				manuallyRecomputeInvoiceCandidate(e, row, maxSecondsToWait);
				updatedInvoiceCandidate = null;
			}

			try
			{
				if (updatedInvoiceCandidate == null)
				{
					updatedInvoiceCandidate = StepDefUtil.tryAndWaitForItem(5, 1000, () -> isInvoiceCandidateUpdated(row));
				}

				InterfaceWrapperHelper.refresh(updatedInvoiceCandidate);

				final String billBPIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(billBPIdentifier))
				{
					final I_C_BPartner billBPartner = bPartnerTable.get(billBPIdentifier);
					assertThat(updatedInvoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());
				}

				final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(productIdentifier))
				{
					final I_M_Product product = productTable.get(productIdentifier);
					assertThat(updatedInvoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
				}

				final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderIdentifier))
				{
					final I_C_Order order = orderTable.get(orderIdentifier);
					assertThat(updatedInvoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
				}

				final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderLineIdentifier))
				{
					final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
					assertThat(updatedInvoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
				}

				final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyOrdered);
				if (qtyOrdered != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
				}

				final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);
				if (qtyDelivered != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
				}

				final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice);
				if (qtyToInvoice != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);
				}

				final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice_Override);
				if (qtyToInvoiceOverride != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
				}

				final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
				if (netAmtToInvoice != null)
				{
					assertThat(updatedInvoiceCandidate.getNetAmtToInvoice()).isEqualTo(netAmtToInvoice);
				}
				final BigDecimal netAmountInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtInvoiced);
				if (netAmountInvoiced != null)
				{
					assertThat(updatedInvoiceCandidate.getNetAmtInvoiced()).isEqualTo(netAmountInvoiced);
				}

				final Boolean isSoTrx = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsSOTrx, null);
				if (isSoTrx != null)
				{
					assertThat(updatedInvoiceCandidate.isSOTrx()).isEqualTo(isSoTrx);
				}

				final String internalName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName);
				if (Check.isNotBlank(internalName))
				{
					final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
					assertThat(updatedInvoiceCandidate.getAD_InputDataSource_ID()).isEqualTo(dataSource.getAD_InputDataSource_ID());
				}

				final BigDecimal qtyWithIssuesEffective = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyWithIssues_Effective);
				if (qtyWithIssuesEffective != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyWithIssues_Effective()).isEqualTo(qtyWithIssuesEffective);
				}
			}
			catch (final Throwable e)
			{
				wrapInvoiceCandidateRelatedException(e, invoiceCandidateRecord, invoiceCandidateIdentifier);
			}
		}
	}

	@And("validate C_Invoice_Candidate:")
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable) throws Throwable
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

			final int maxSecondsToWait = 120;
			I_C_Invoice_Candidate updatedInvoiceCandidate;
			try
			{
				updatedInvoiceCandidate = StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> isInvoiceCandidateUpdated(row));
			}
			catch (final Throwable e)
			{
				manuallyRecomputeInvoiceCandidate(e, row, maxSecondsToWait);
				updatedInvoiceCandidate = null;
			}

			try
			{
				if (updatedInvoiceCandidate == null)
				{
					updatedInvoiceCandidate = StepDefUtil.tryAndWaitForItem(5, 1000, () -> isInvoiceCandidateUpdated(row));
				}

				final SoftAssertions softly = new SoftAssertions();
				
				final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
				softly.assertThat(updatedInvoiceCandidate.getQtyToInvoice()).as("QtyToInvoice").isEqualTo(qtyToInvoice);

				final BigDecimal qtyToInvoiceInUomCalc = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoiceInUOM_Calc);
				if (qtyToInvoiceInUomCalc != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyToInvoiceInUOM_Calc()).as("QtyToInvoiceInUOM_Calc").isEqualTo(qtyToInvoiceInUomCalc);
				}

				final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
				if (qtyOrdered != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyOrdered()).as("QtyOrdered").isEqualTo(qtyOrdered);
				}

				final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
				if (qtyDelivered != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyDelivered()).as("QtyDelivered").isEqualTo(qtyDelivered);
				}

				final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyEntered);
				if (qtyEntered != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyEntered()).as("QtyEntered").isEqualTo(qtyEntered);
				}

				final BigDecimal qtyToInvoiceBeforeDiscount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceBeforeDiscount);
				if (qtyToInvoiceBeforeDiscount != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyToInvoiceBeforeDiscount()).as("QtyToInvoiceBeforeDiscount").isEqualTo(qtyToInvoiceBeforeDiscount);
				}

				final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyPicked);
				if (qtyPicked != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyPicked()).as("QtyPicked").isEqualTo(qtyPicked);
				}

				final BigDecimal qtyPickedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyPickedInUOM);
				if (qtyPickedInUOM != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyPickedInUOM()).as("QtyPickedInUOM").isEqualTo(qtyPickedInUOM);
				}

				final BigDecimal qtyDeliveredInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDeliveredInUOM);
				if (qtyPickedInUOM != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyDeliveredInUOM()).as("QtyDeliveredInUOM").isEqualTo(qtyDeliveredInUOM);
				}

				final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
				if (qtyToInvoiceOverride != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyToInvoice_Override()).as("QtyToInvoice_Override").isEqualTo(qtyToInvoiceOverride);
				}

				final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoiced);
				if (qtyInvoiced != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyInvoiced()).as("QtyInvoiced").isEqualTo(qtyInvoiced);
				}

				final BigDecimal qtyInvoicedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInUOM);
				if (qtyInvoicedInUOM != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getQtyInvoicedInUOM()).as("QtyInvoicedInUOM").isEqualTo(qtyInvoicedInUOM);
				}

				final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
				if (netAmtToInvoice != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getNetAmtToInvoice()).as("NetAmtToInvoice").isEqualTo(netAmtToInvoice);
				}

				final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderIdentifier))
				{
					final I_C_Order order = orderTable.get(orderIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Order_ID()).as("C_Order_ID").isEqualTo(order.getC_Order_ID());
				}

				final String orderLineIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderLineIdentifier))
				{
					final String orderLineIdentifierValue = DataTableUtil.nullToken2Null(orderLineIdentifier);
					if (orderLineIdentifierValue != null)
					{
						final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
						softly.assertThat(updatedInvoiceCandidate.getC_OrderLine_ID()).as("C_OrderLine_ID").isEqualTo(orderLine.getC_OrderLine_ID());
					}
					else
					{
						softly.assertThat(updatedInvoiceCandidate.getC_OrderLine_ID()).as("C_OrderLine_ID").isEqualTo(0);
					}
				}

				final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_PaymentRule);

				if (Check.isNotBlank(paymentRule))
				{
					softly.assertThat(updatedInvoiceCandidate.getPaymentRule()).as("PaymentRule").isEqualTo(paymentRule);
				}

				final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(productIdentifier))
				{
					final I_M_Product product = productTable.get(productIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getM_Product_ID()).as("M_Product_ID").isEqualTo(product.getM_Product_ID());
				}

				final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_Processed, false);
				assertThat(updatedInvoiceCandidate.isProcessed()).isEqualTo(processed);

				final String taxEffectiveIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Effective_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(taxEffectiveIdentifier))
				{
					final I_C_Tax taxEffective = taxTable.get(taxEffectiveIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Tax_Effective_ID()).as("C_Tax_Effective_ID").isEqualTo(taxEffective.getC_Tax_ID());
				}

				final Boolean isToClear = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT" + COLUMNNAME_IsToClear);
				if (isToClear != null)
				{
					softly.assertThat(updatedInvoiceCandidate.isToClear()).as("IsToClear").isEqualTo(isToClear);
				}

			}
			catch (final Throwable e)
			{
				wrapInvoiceCandidateRelatedException(e, invoiceCandidateRecord, invoiceCandidateIdentifier);
			}
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
			StepDefUtil.tryAndWaitForItem(timeoutSec, 500,
					() -> isInvoiceCandidateUpdated(tableRow));
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

			final Boolean isUpdateLocationAndContactForInvoice = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsUpdateLocationAndContactForInvoice", false);

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

			final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateId.getRepoId()), Trx.TRXNAME_None);

			final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
			invoicingParams.setIgnoreInvoiceSchedule(false);
			invoicingParams.setSupplementMissingPaymentTermIds(true);
			invoicingParams.setUpdateLocationAndContactForInvoice(isUpdateLocationAndContactForInvoice);

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> checkNotMarkedAsToRecompute(invoiceCandidate));

			try
			{
				invoiceCandBL.enqueueForInvoicing()
						.setContext(Env.getCtx())
						.setFailIfNothingEnqueued(true)
						.setInvoicingParams(invoicingParams)
						.prepareAndEnqueueSelection(invoiceCandidatesSelectionId);
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

	@And("^there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: (.*)$")
	public void validate_no_C_InvoiceCandidate_InOutLine_for_M_InOut(@NonNull final String shipmentLineIdentifier)
	{
		final I_M_InOutLine shipmentLine = inoutLineTable.get(shipmentLineIdentifier);

		final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLine.getM_InOutLine_ID())
				.create()
				.firstOnlyOrNull(I_C_InvoiceCandidate_InOutLine.class);

		assertThat(invoiceCandidateInOutLine).isNull();
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

			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 500,
					() -> retrieveInvoiceCandidate(row, candidatesQuery));

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
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final IQueryBuilder<I_C_Invoice_Candidate> candQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderLine.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

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
		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);

		final IQueryBuilder<I_C_Invoice_Candidate> invCandQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, qtyToInvoice);

		final String shipmentLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (shipmentLineIdentifier != null)
		{
			final I_M_InOutLine shipmentLine = inoutLineTable.get(shipmentLineIdentifier);

			final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
					.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLine.getM_InOutLine_ID())
					.create()
					.firstOnlyOrNull(I_C_InvoiceCandidate_InOutLine.class);

			if (invoiceCandidateInOutLine == null)
			{
				return false;
			}

			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateInOutLine.getC_Invoice_Candidate_ID());
		}

		final String orderLineIdentifier = DataTableUtil.extractNullableStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderLineIdentifier))
		{
			final String orderLineIdentifierValue = DataTableUtil.nullToken2Null(orderLineIdentifier);
			if (orderLineIdentifierValue != null)
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				invCandQueryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
			}
			else
			{
				invCandQueryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, null);
			}
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_QtyDelivered, qtyDelivered);
		}

		final IQuery<I_C_Invoice_Candidate> invCandQuery = invCandQueryBuilder.create();
		final List<I_C_Invoice_Candidate> invoiceCandidates = invCandQuery.list(I_C_Invoice_Candidate.class);
		if (invoiceCandidates.isEmpty())
		{
			return false;
		}
		else if (invoiceCandidates.size() == 1)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidates.get(0));

			return true;
		}
		else
		{
			throw new AdempiereException("Got more than one invoice candidate")
					.appendParametersToMessage()
					.setParameter("query", invCandQuery)
					.setParameter("candidates found", invoiceCandidates);
		}
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

		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final ImmutableList.Builder<String> errorCollectors = ImmutableList.builder();

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);

		if (qtyDelivered != null)
		{
			if (invoiceCandidateRecord.getQtyDelivered().compareTo(qtyDelivered) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting QtyDelivered={1} but actual is {2}",
						invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyDelivered, invoiceCandidateRecord.getQtyDelivered()));
			}
		}

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);

		if (qtyToInvoice != null)
		{
			if (invoiceCandidateRecord.getQtyToInvoice().compareTo(qtyToInvoice) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting QtyToInvoice={1} but actual is {2}",
						invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyToInvoice, invoiceCandidateRecord.getQtyToInvoice()));
			}
		}

		final BigDecimal qtyWithIssuesEffective = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyWithIssues_Effective);
		if (qtyWithIssuesEffective != null)
		{
			if (invoiceCandidateRecord.getQtyWithIssues_Effective().compareTo(qtyWithIssuesEffective) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting QtyDelivered={1} but actual is {2}",
														 invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyDelivered, invoiceCandidateRecord.getQtyDelivered()));
			}
		}

		if (invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			errorCollectors.add("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
		}

		final List<String> errors = errorCollectors.build();

		if (errors.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errors);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
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

	private void wrapInvoiceCandidateRelatedException(
			@NonNull final Throwable e,
			@NonNull final I_C_Invoice_Candidate invCandidate,
			@NonNull final String invoiceCandidateIdentifier)
	{
		final BigDecimal orderLineQtyDelivered = Optional
				.of(TableRecordReference.of(invCandidate.getAD_Table_ID(), invCandidate.getRecord_ID()))
				.filter(tableRecordReference -> I_C_OrderLine.Table_Name.equals(tableRecordReference.getTableName()))
				.map(tableRecordReference -> tableRecordReference.getModel(I_C_OrderLine.class))
				.map(I_C_OrderLine::getQtyDelivered)
				.orElse(null);

		final StringBuilder invoiceCandidateInOutLineBindings = new StringBuilder("The following C_InvoiceCandidate_InOutLine records exist for c_invoice_candidate_id=")
				.append(invCandidate.getC_Invoice_Candidate_ID())
				.append("\n");

		queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, invCandidate.getC_Invoice_Candidate_ID())
				.create()
				.stream()
				.forEach(record -> invoiceCandidateInOutLineBindings
						.append("QtyDelivered=").append(record.getQtyDelivered()).append(";")
						.append("M_InOutLine_ID=").append(record.getM_InOutLine_ID()).append("\n"));

		final String rawSQLQuery = "select * from c_invoice_candidate where c_invoice_candidate_id = " + invCandidate.getC_Invoice_Candidate_ID();

		final List<String> invCandidateDetailList = DB.retrieveRows(rawSQLQuery,
																	new ArrayList<>(),
																	(resultSet) -> this.getInvoiceCandidateExceptionDetails(invCandidate, resultSet, invoiceCandidateIdentifier));

		//query by id
		final String invCandDetails = invCandidateDetailList.get(0);

		throw AdempiereException.wrapIfNeeded(e)
				.appendParametersToMessage()
				.setParameter("InvoiceCandidateDetails", invCandDetails)
				.setParameter("OrderLineQtyDelivered", orderLineQtyDelivered)
				.setParameter("C_InvoiceCandidate_InOutLines", invoiceCandidateInOutLineBindings.toString());
	}

	@NonNull
	private String getInvoiceCandidateExceptionDetails(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final ResultSet resultSet,
			@NonNull final String invoiceCandIdentifier) throws SQLException
	{
		final StringBuilder detailsBuilder = new StringBuilder();

		detailsBuilder.append("[")
				.append("C_Invoice_Candidate_ID:").append(invoiceCandidate.getC_Invoice_Candidate_ID()).append(" - Identifier->").append(invoiceCandIdentifier)
				.append(", ")
				.append(COLUMNNAME_QtyToInvoice).append(":").append("I_->").append(invoiceCandidate.getQtyToInvoice()).append(" - ResultSet->").append(resultSet.getBigDecimal(COLUMNNAME_QtyToInvoice))
				.append(", ")
				.append(COLUMNNAME_QtyDelivered).append(":").append("I_->").append(invoiceCandidate.getQtyDelivered()).append(" - ResultSet->").append(resultSet.getBigDecimal(COLUMNNAME_QtyDelivered))
				.append(", ")
				.append(COLUMNNAME_QtyWithIssues_Effective).append(":").append("I_->").append(invoiceCandidate.getQtyWithIssues_Effective()).append(" - ResultSet->").append(resultSet.getBigDecimal(COLUMNNAME_QtyWithIssues_Effective))
				.append("]");

		return detailsBuilder.toString();
	}

	public void manuallyRecomputeInvoiceCandidate(
			@NonNull final Throwable throwable,
			@NonNull final Map<String, String> row,
			final int timeoutSec) throws Throwable
	{
		logger.warn("*** C_Invoice_Candidate was not found within {} seconds, manually invalidate and try again if possible. "
							+ "Error message: {}", timeoutSec, throwable.getMessage());

		final String invoiceCandIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = Optional
				.ofNullable(invoiceCandIdentifier)
				.flatMap(invoiceCandTable::getOptional);

		if (!invoiceCandidate.isPresent())
		{
			logger.warn("*** C_Invoice_Candidate was not previously loaded => cannot invalidate!");
			throw throwable;
		}

		final int noOfInvalidatedCandidates = invoiceCandDAO.invalidateCand(invoiceCandidate.get());

		if (noOfInvalidatedCandidates != 1)
		{
			throw new AdempiereException("Invoice candidate has not been invalidated !")
					.appendParametersToMessage()
					.setParameter("InvoiceCandidateId", invoiceCandidate.get().getC_Invoice_Candidate_ID());
		}

		final Supplier<Boolean> isInvoiceCandidateValidated = () -> queryBL.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, invoiceCandidate.get().getC_Invoice_Candidate_ID())
				.create()
				.count() == 0;

		StepDefUtil.tryAndWait(timeoutSec, 500, isInvoiceCandidateValidated);
	}
}
