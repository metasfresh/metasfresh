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
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.logging.LogbackLoggable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Table;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Async_Batch_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Effective_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInDispute;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInterimInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PaymentRule;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Processed;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDeliveredInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyEntered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoicedInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyPicked;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyPickedInUOM;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceBeforeDiscount;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM_Calc;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Record_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final I_Invoice_Candidate_StepDefData iInvoiceCandidatetable;
	private final AD_User_StepDefData contactTable;
	private final C_DocType_StepDefData docTypeTable;
	private final C_UOM_StepDefData uomTable;
	private final AD_Org_StepDefData orgTable;
	private final StepDefData<I_C_Tax> taxTable;
	private final StepDefData<I_M_InOutLine> inoutLineTable;
	private final StepDefData<I_C_Flatrate_Term> contractTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_UOM_StepDefData uomTable,
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final StepDefData<I_C_Tax> taxTable,
			@NonNull final StepDefData<I_M_InOutLine> inoutLineTable,
			@NonNull final StepDefData<I_C_Flatrate_Term> contractTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.iInvoiceCandidatetable = iInvoiceCandidateTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
		this.orgTable = orgTable;
		this.taxTable = taxTable;
		this.inoutLineTable = inoutLineTable;
		this.contractTable = contractTable;
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
				manuallyRecomputeInvoiceCandidate_outDated(exception, row, timeoutSec);
				StepDefUtil.tryAndWait(5, 1000, () -> load_C_Invoice_Candidate(row));
			}
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

			final BigDecimal priceEnteredOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_PriceEntered_Override);
			if (priceEnteredOverride != null)
			{
				invoiceCandidate.setPriceEntered_Override(priceEnteredOverride);
			}

			final String invoiceRuleOverride = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule_Override);
			if (Check.isNotBlank(invoiceRuleOverride))
			{
				invoiceCandidate.setInvoiceRule_Override(invoiceRuleOverride);
			}

			saveRecord(invoiceCandidate);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
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
				final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
				invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
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
				manuallyRecomputeInvoiceCandidate_outDated(e, row, maxSecondsToWait);
				updatedInvoiceCandidate = null;
			}

			try
			{
				if (updatedInvoiceCandidate == null)
				{
					updatedInvoiceCandidate = StepDefUtil.tryAndWaitForItem(5, 1000, () -> isInvoiceCandidateUpdated(row));
				}

				final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, COLUMNNAME_QtyToInvoice);
				assertThat(updatedInvoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);

				final BigDecimal qtyToInvoiceInUomCalc = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoiceInUOM_Calc);
				if (qtyToInvoiceInUomCalc != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyToInvoiceInUOM_Calc()).isEqualTo(qtyToInvoiceInUomCalc);
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

				final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyEntered);
				if (qtyEntered != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyEntered()).isEqualTo(qtyEntered);
				}

				final BigDecimal qtyToInvoiceBeforeDiscount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoiceBeforeDiscount);
				if (qtyToInvoiceBeforeDiscount != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyToInvoiceBeforeDiscount()).isEqualTo(qtyToInvoiceBeforeDiscount);
				}

				final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyPicked);
				if (qtyPicked != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyPicked()).isEqualTo(qtyPicked);
				}

				final BigDecimal qtyPickedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyPickedInUOM);
				if (qtyPickedInUOM != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyPickedInUOM()).isEqualTo(qtyPickedInUOM);
				}

				final BigDecimal qtyDeliveredInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDeliveredInUOM);
				if (qtyPickedInUOM != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyDeliveredInUOM()).isEqualTo(qtyDeliveredInUOM);
				}

				final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice_Override);
				if (qtyToInvoiceOverride != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
				}

				final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoiced);
				if (qtyInvoiced != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyInvoiced()).isEqualTo(qtyInvoiced);
				}

				final BigDecimal qtyInvoicedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInUOM);
				if (qtyInvoicedInUOM != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyInvoicedInUOM()).isEqualTo(qtyInvoicedInUOM);
				}

				final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
				if (netAmtToInvoice != null)
				{
					assertThat(updatedInvoiceCandidate.getNetAmtToInvoice()).isEqualTo(netAmtToInvoice);
				}

				final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderIdentifier))
				{
					final I_C_Order order = orderTable.get(orderIdentifier);
					assertThat(updatedInvoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
				}

				final String orderLineIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(orderLineIdentifier))
				{
					final String orderLineIdentifierValue = DataTableUtil.nullToken2Null(orderLineIdentifier);
					if (orderLineIdentifierValue != null)
					{
						final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
						assertThat(updatedInvoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
					}
					else
					{
						assertThat(updatedInvoiceCandidate.getC_OrderLine_ID()).isEqualTo(0);
					}
				}

				final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_PaymentRule);

				if (Check.isNotBlank(paymentRule))
				{
					assertThat(updatedInvoiceCandidate.getPaymentRule()).isEqualTo(paymentRule);
				}

				final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(productIdentifier))
				{
					final I_M_Product product = productTable.get(productIdentifier);
					assertThat(updatedInvoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
				}

				// final Boolean isDeliveryClosed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsDeliveryClosed, null);
				//
				// if (isDeliveryClosed != null)
				// {
				// 	assertThat(updatedInvoiceCandidate.isDeliveryClosed()).isEqualTo(isDeliveryClosed);
				// }

				final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_Processed, false);
				assertThat(updatedInvoiceCandidate.isProcessed()).isEqualTo(processed);

				final String taxEffectiveIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Effective_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(taxEffectiveIdentifier))
				{
					final I_C_Tax taxEffective = taxTable.get(taxEffectiveIdentifier);
					assertThat(updatedInvoiceCandidate.getC_Tax_Effective_ID()).isEqualTo(taxEffective.getC_Tax_ID());
				}
			}
			catch (final Throwable e)
			{
				wrapInvoiceCandidateRelatedException(e, invoiceCandidateRecord, invoiceCandidateIdentifier);
			}
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

			final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
			invoicingParams.setIgnoreInvoiceSchedule(false);
			invoicingParams.setSupplementMissingPaymentTermIds(true);
			invoicingParams.setUpdateLocationAndContactForInvoice(isUpdateLocationAndContactForInvoice);

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> checkNotMarkedAsToRecompute(invoiceCandidate));

			try
			{
				processInvoiceCandidates(ImmutableSet.of(invoiceCandidate), invoicingParams);
			}
			catch (final AdempiereException adempiereException)
			{
				logCurrentContext(invoiceCandidate, row);
				throw adempiereException;
			}

			//wait for the invoice to be created
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> isInvoiceCandidateProcessed(invoiceCandidate, row), () -> logCurrentContext(invoiceCandidate, row));
		}
	}

	@And("^process invoice candidates batch and wait (.*)s for each C_Invoice_Candidate to be processed$")
	public void process_invoice_cand_batch(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		final Map<I_C_Invoice_Candidate, Map<String, String>> invoiceCandidatesWithRows = new HashMap<>();

		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);
			InterfaceWrapperHelper.refresh(invoiceCandidate);

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> checkNotMarkedAsToRecompute(invoiceCandidate));

			invoiceCandidatesWithRows.put(invoiceCandidate, row);
		}

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(true);

		try
		{
			processInvoiceCandidates(invoiceCandidatesWithRows.keySet(), invoicingParams);
		}
		catch (final AdempiereException adempiereException)
		{
			invoiceCandidatesWithRows.forEach(this::logCurrentContext);
			throw adempiereException;
		}

		invoiceCandidatesWithRows.forEach((invoiceCandidate, row) -> {
			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 500, () -> isInvoiceCandidateProcessed(invoiceCandidate, row), () -> logCurrentContext(invoiceCandidate, row));
			}
			catch (final InterruptedException e)
			{
				throw new RuntimeException(e);
			}
		});
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

	@And("validate invoice candidates by record reference:")
	public void locate_invoice_candidate_by_record_id(@NonNull final DataTable dataTable)
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

	private void processInvoiceCandidates(@NonNull final Set<I_C_Invoice_Candidate> invoiceCandidates, @NonNull final PlainInvoicingParams invoicingParams)
	{
		final Set<InvoiceCandidateId> invoiceCandidateIds = invoiceCandidates.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, Trx.TRXNAME_None);

		invoiceCandBL.enqueueForInvoicing()
				.setContext(Env.getCtx())
				.setFailIfNothingEnqueued(true)
				.setInvoicingParams(invoicingParams)
				.enqueueSelection(invoiceCandidatesSelectionId);

		DB.deleteT_Selection(invoiceCandidatesSelectionId, Trx.TRXNAME_None);
	}

	private boolean load_C_Invoice_Candidate(@NonNull final Map<String, String> row)
	{
		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, COLUMNNAME_QtyToInvoice);

		final IQueryBuilder<I_C_Invoice_Candidate> invCandQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(COLUMNNAME_QtyToInvoice, qtyToInvoice);

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

		final String orderLineIdentifier = DataTableUtil.extractNullableStringForColumnName(row, COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderLineIdentifier))
		{
			final String orderLineIdentifierValue = DataTableUtil.nullToken2Null(orderLineIdentifier);
			if (orderLineIdentifierValue != null)
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				invCandQueryBuilder.addEqualsFilter(COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
			}
			else
			{
				invCandQueryBuilder.addEqualsFilter(COLUMNNAME_C_OrderLine_ID, null);
			}
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_QtyDelivered, qtyDelivered);
		}

		final String referencedTableName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
		if (Check.isNotBlank(referencedTableName))
		{
			final I_AD_Table referencedTable = queryBL.createQueryBuilder(I_AD_Table.class)
					.addEqualsFilter(I_AD_Table.COLUMNNAME_TableName, referencedTableName)
					.create()
					.firstOnlyNotNull(I_AD_Table.class);

			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_AD_Table_ID, referencedTable.getAD_Table_ID());
		}

		final String referencedRecordIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(referencedRecordIdentifier))
		{
			contractTable.getOptional(referencedRecordIdentifier).ifPresent(contract -> invCandQueryBuilder.addEqualsFilter(COLUMNNAME_Record_ID, contract.getC_Flatrate_Term_ID()));
		}

		final String isInterimInvoice = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_IsInterimInvoice);
		if (Check.isNotBlank(isInterimInvoice))
		{
			final String isInterimInvoiceValue = DataTableUtil.nullToken2Null(isInterimInvoice);
			if (isInterimInvoiceValue != null)
			{
				invCandQueryBuilder.addEqualsFilter(COLUMNNAME_IsInterimInvoice, StringUtils.toBoolean(isInterimInvoiceValue));
			}
			else
			{
				invCandQueryBuilder.addEqualsFilter(COLUMNNAME_IsInterimInvoice, null);
			}
		}

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = invCandQueryBuilder.create()
				.firstOnlyOptional(I_C_Invoice_Candidate.class);

		if (!invoiceCandidate.isPresent())
		{
			return false;
		}

		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate.get());

		return true;
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);

		if (qtyDelivered != null)
		{
			if (invoiceCandidateRecord.getQtyDelivered().compareTo(qtyDelivered) == 0)
			{
				return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
			}
			else
			{
				return ItemProvider.ProviderResult
						.resultWasNotFound("C_Invoice_Candidate_ID={0}; Expecting QtyDelivered={1} but actual is {2}",
										   invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyDelivered, invoiceCandidateRecord.getQtyDelivered());
			}
		}

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);

		if (qtyToInvoice != null)
		{
			if (invoiceCandidateRecord.getQtyToInvoice().compareTo(qtyToInvoice) == 0)
			{
				return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
			}
			else
			{
				return ItemProvider.ProviderResult
						.resultWasNotFound("C_Invoice_Candidate_ID={0}; Expecting QtyToInvoice={1} but actual is {2}",
										   invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyToInvoice, invoiceCandidateRecord.getQtyToInvoice());
			}
		}

		if (!invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
		}

		return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
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

	private void wrapInvoiceCandidateRelatedException(
			@NonNull final Throwable e,
			@NonNull final I_C_Invoice_Candidate invCandidate,
			@NonNull final String invoiceCandidateIdentifier)
	{
		final String rawSQLQuery = "select * from c_invoice_candidate where c_invoice_candidate_id = " + invCandidate.getC_Invoice_Candidate_ID();

		final List<String> invCandidateDetailList = DB.retrieveRows(rawSQLQuery,
																	new ArrayList<>(),
																	(resultSet) -> this.getInvoiceCandidateExceptionDetails(invCandidate, resultSet, invoiceCandidateIdentifier));

		//query by id
		final String invCandDetails = invCandidateDetailList.get(0);

		throw AdempiereException.wrapIfNeeded(e)
				.appendParametersToMessage()
				.setParameter("InvoiceCandidateDetails", invCandDetails);
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
				.append("]");

		return detailsBuilder.toString();
	}

	@Deprecated
	private void manuallyRecomputeInvoiceCandidate_outDated(
			@NonNull final Throwable throwable,
			@NonNull final Map<String, String> row,
			final int timeoutSec) throws Throwable
	{
		logger.warn("*** C_Invoice_Candidate was not found within {} seconds, manually invalidate and try again if possible. "
							+ "Error message: {}", timeoutSec, throwable.getMessage());

		final String invoiceCandIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Invoice_Candidate invoiceCandidate = Optional
				.ofNullable(invoiceCandIdentifier)
				.map(invoiceCandTable::get)
				.orElse(null);

		if (invoiceCandidate == null)
		{
			logger.warn("*** C_Invoice_Candidate was not previously loaded => cannot invalidate!");
			throw throwable;
		}

		invoiceCandDAO.invalidateCand(invoiceCandidate);

		// final InvoiceCandidateIdsSelection onlyInvoiceCandidateIds = InvoiceCandidateIdsSelection.ofIdsSet(
		// 		ImmutableSet.of(InvoiceCandidateId.ofRepoId(invoiceCandidate.get().getC_Invoice_Candidate_ID())));

		invoiceCandBL.updateInvalid()
				.setContext(Env.getCtx(), ITrx.TRXNAME_None)
				.setTaggedWithAnyTag()
				.setOnlyC_Invoice_Candidates(ImmutableList.of(invoiceCandidate))
				.update();
	}
}
