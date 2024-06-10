/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.common.util.EmptyUtil;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.TableRecordReference_StepDefUtil;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.aggregation.C_Aggregation_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.country.C_Country_StepDefData;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.iinvoicecandidate.I_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.serviceIssue.S_Issue_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.edi.model.I_M_InOut;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_InvoiceCandidate_Processing;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Departure_Country_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_Effective_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInDispute;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInEffect;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsToClear;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceActual;
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
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Record_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

public class C_Invoice_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_InOut_StepDefData shipmentTable;
	private final C_DocType_StepDefData docTypeTable;
	private final C_Tax_StepDefData taxTable;
	private final M_InOutLine_StepDefData inoutLineTable;
	private final I_Invoice_Candidate_StepDefData iInvoiceCandidatetable;
	private final AD_User_StepDefData contactTable;
	private final AD_Org_StepDefData orgTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final TableRecordReference_StepDefUtil tableRecordReferenceStepDefUtil;
	private final S_Issue_StepDefData issueTable;
	private final C_Project_StepDefData projectTable;
	private final C_Activity_StepDefData activityTable;
	private final C_Invoice_Candidate_List_StepDefData invoiceCandidateListTable;
	private final C_Country_StepDefData countryTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;
	private final C_Aggregation_StepDefData aggregationTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final M_InOutLine_StepDefData inoutLineTable,
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final TableRecordReference_StepDefUtil tableRecordReferenceStepDefUtil,
			@NonNull final S_Issue_StepDefData issueTable,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_Activity_StepDefData activityTable,
			@NonNull final C_Invoice_Candidate_List_StepDefData invoiceCandidateListTable,
			@NonNull final C_Country_StepDefData countryTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final C_Aggregation_StepDefData aggregationTable)
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
		this.iInvoiceCandidatetable = iInvoiceCandidateTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.orgTable = orgTable;
		this.contractTable = contractTable;
		this.tableRecordReferenceStepDefUtil = tableRecordReferenceStepDefUtil;
		this.issueTable = issueTable;
		this.projectTable = projectTable;
		this.activityTable = activityTable;
		this.shipmentTable = shipmentTable;
		this.invoiceCandidateListTable = invoiceCandidateListTable;
		this.countryTable = countryTable;
		this.warehouseTable = warehouseTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
		this.aggregationTable = aggregationTable;
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

	// TODO give it a better name or merge it with "C_Invoice_Candidates are found:" step
	@And("^after not more than (.*)s, C_Invoice_Candidate are found:$")
	public void find_C_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws Throwable
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 1000, () -> load_C_Invoice_Candidate(row));
			}
			catch (final Throwable exception)
			{
				manuallyRecomputeInvoiceCandidate(exception, row, timeoutSec);

				StepDefUtil.tryAndWait(5, 1000, () -> load_C_Invoice_Candidate(row));
			}
		});
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

			final Boolean approvalForInvoicing = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_ApprovalForInvoicing);
			if (approvalForInvoicing != null)
			{
				invoiceCandidate.setApprovalForInvoicing(approvalForInvoicing);
			}

			final String taxDepartureCountryIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Departure_Country_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(taxDepartureCountryIdentifier))
			{
				final I_C_Country taxDepartureCountry = countryTable.get(taxDepartureCountryIdentifier);
				invoiceCandidate.setC_Tax_Departure_Country_ID(taxDepartureCountry.getC_Country_ID());
			}

			saveRecord(invoiceCandidate);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	@And("^there is no C_Invoice_Candidate for C_Order (.*)$")
	public void validate_no_C_Invoice_Candidate_created(@NonNull final String orderIdentifier) throws InterruptedException
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		// give IC handlers some time
		Thread.sleep(1000);

		final I_C_Invoice_Candidate candidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		Assertions.assertThat(candidate).isNull();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(order);
		Assertions.assertThat(invoiceCandidates).isEmpty();
	}

	@And("^there is no C_Invoice_Candidate for M_InOutLine (.*)$")
	public void validate_no_C_Invoice_Candidate_created_for_InOutLine(@NonNull final String inOutLineIdentifier) throws InterruptedException
	{
		final I_M_InOutLine inOutLineRecord = inoutLineTable.get(inOutLineIdentifier);

		// give IC handlers some time
		Thread.sleep(1000);

		final I_C_Invoice_Candidate candidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_InOut_ID, inOutLineRecord.getM_InOut_ID())
				.create()
				.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		Assertions.assertThat(candidate).isNull();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(inOutLineRecord);
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
		DataTableRows.of(dataTable).forEach((rowObj) -> {
			final Map<String, String> row = rowObj.asMap();
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
				manuallyRecomputeInvoiceCandidate(e, rowObj, maxSecondsToWait);
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
		});
	}

	@And("^after not more than (.*)s, credit memo candidates are found:$")
	public void find_credit_memo_candidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadCreditMemoCandidate(row));
		}
	}

	@And("validate C_Invoice_Candidate:")
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable) throws Throwable
	{
		DataTableRows.of(dataTable).forEach((rowObj) -> {
			final Map<String, String> row = rowObj.asMap();
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
				manuallyRecomputeInvoiceCandidate(e, rowObj, maxSecondsToWait);
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

				final Boolean isDeliveryClosed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_IsDeliveryClosed, null);

				if (isDeliveryClosed != null)
				{
					softly.assertThat(updatedInvoiceCandidate.isDeliveryClosed()).as("DeliveryClosed").isEqualTo(isDeliveryClosed);
				}

				final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_Processed, false);
				softly.assertThat(updatedInvoiceCandidate.isProcessed()).as("Processed").isEqualTo(processed);

				final String taxEffectiveIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Effective_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(taxEffectiveIdentifier))
				{
					final I_C_Tax taxEffective = taxTable.get(taxEffectiveIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Tax_Effective_ID()).as("C_Tax_Effective_ID").isEqualTo(taxEffective.getC_Tax_ID());
				}

				final String recordIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
				final String tableName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.TableName");

				if (Check.isNotBlank(recordIdentifier) && Check.isNotBlank(tableName))
				{
					final TableRecordReference tableRecordReference = tableRecordReferenceStepDefUtil.getTableRecordReferenceFromIdentifier(recordIdentifier, tableName);

					softly.assertThat(updatedInvoiceCandidate.getRecord_ID()).as("Record_ID").isEqualTo(tableRecordReference.getRecord_ID());
					softly.assertThat(updatedInvoiceCandidate.getAD_Table_ID()).as("AD_Table_ID").isEqualTo(tableRecordReference.getAD_Table_ID());
				}

				final Boolean isInEffect = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsInEffect, null);
				if (isInEffect != null)
				{
					softly.assertThat(updatedInvoiceCandidate.isInEffect()).as("IsInEffect").isEqualTo(isInEffect);
				}

				final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (de.metas.util.Check.isNotBlank(bPartnerIdentifier))
				{
					final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getBill_BPartner_ID()).as("C_BPartner_ID").isEqualTo(bPartner.getC_BPartner_ID());
				}

				final String bPartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (de.metas.util.Check.isNotBlank(bPartnerLocationIdentifier))
				{
					final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getBill_Location_ID()).as("C_BPartner_Location_ID").isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
				}

				final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (de.metas.util.Check.isNotBlank(userIdentifier))
				{
					final I_AD_User user = contactTable.get(userIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getBill_User_ID()).as("Bill_User_ID").isEqualTo(user.getAD_User_ID());
				}

				final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule);
				if (Check.isNotBlank(invoiceRule))
				{
					softly.assertThat(updatedInvoiceCandidate.getInvoiceRule()).as("InvoiceRule").isEqualTo(invoiceRule);
				}

				final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(projectIdentifier))
				{
					final I_C_Project project = projectTable.get(projectIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Project_ID()).as("C_Project_ID").isEqualTo(project.getC_Project_ID());
				}

				final String costCenterIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(costCenterIdentifier))
				{
					final I_C_Activity activity = activityTable.get(costCenterIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Activity_ID()).as("C_Activity_ID").isEqualTo(activity.getC_Activity_ID());
				}

				final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_Description);
				if (Check.isNotBlank(description))
				{
					softly.assertThat(updatedInvoiceCandidate.getDescription()).as("Description").isEqualTo(description);
				}

				final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + org.compiere.model.I_M_InOut.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(docTypeIdentifier))
				{
					final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
					softly.assertThat(docTypeRecord).as("C_DocType for Identifier=%s", docTypeIdentifier).isNotNull();
					if (docTypeRecord != null)
					{
						softly.assertThat(updatedInvoiceCandidate.getC_DocTypeInvoice_ID()).as("C_DocTypeInvoice_ID").isEqualTo(docTypeRecord.getC_DocType_ID());
					}
				}

				final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(warehouseIdentifier))
				{
					final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getM_Warehouse_ID()).as("M_Warehouse_ID").isEqualTo(warehouseRecord.getM_Warehouse_ID());
				}

				final String harvestingCalendarIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(harvestingCalendarIdentifier))
				{
					final I_C_Calendar harvestingCalendarRecord = calendarTable.get(harvestingCalendarIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getC_Harvesting_Calendar_ID()).as("C_Harvesting_Calendar_ID").isEqualTo(harvestingCalendarRecord.getC_Calendar_ID());
				}

				final String harvestingYearIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(harvestingYearIdentifier))
				{
					final I_C_Year harvestingYearRecord = yearTable.get(harvestingYearIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getHarvesting_Year_ID()).as("Harvesting_Year_ID").isEqualTo(harvestingYearRecord.getC_Year_ID());
				}

				final String aggregationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKeyBuilder_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(aggregationIdentifier))
				{
					final I_C_Aggregation aggregationRecord = aggregationTable.get(aggregationIdentifier);
					softly.assertThat(updatedInvoiceCandidate.getHeaderAggregationKeyBuilder_ID()).as("HeaderAggregationKeyBuilder_ID").isEqualTo(aggregationRecord.getC_Aggregation_ID());
				}

				final Boolean soTrx = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_IsSOTrx);
				if (soTrx != null)
				{
					softly.assertThat(updatedInvoiceCandidate.isSOTrx()).as("IsSOTrx").isEqualTo(soTrx);
				}

				final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_PriceActual);

				if (priceActual != null)
				{
					softly.assertThat(updatedInvoiceCandidate.getPriceActual()).as(COLUMNNAME_PriceActual).isEqualByComparingTo(priceActual);
				}

				final Boolean isToClear = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT" + COLUMNNAME_IsToClear);
				if (isToClear != null)
				{
					softly.assertThat(updatedInvoiceCandidate.isToClear()).as("IsToClear").isEqualTo(isToClear);
				}
				
				softly.assertAll();
			}
			catch (final Throwable e)
			{
				wrapInvoiceCandidateRelatedException(e, invoiceCandidateRecord, invoiceCandidateIdentifier);
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
				final String invoiceCandIdentifiers = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

				final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = StepDefUtil.splitIdentifiers(invoiceCandIdentifiers)
						.stream()
						.map(invoiceCandTable::get)
						.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
						.map(InvoiceCandidateId::ofRepoId)
						.collect(ImmutableSet.toImmutableSet());

				final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_InvoiceCandidate_Processing);

				invoiceService.generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds, asyncBatchId);
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
				final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_InvoiceCandidate_Processing);

				invoiceIds = invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId), asyncBatchId);
			}
			catch (final AdempiereException adempiereException)
			{
				assertThat(adempiereException.getMessage()).contains("Es wurden keine fakturierbaren Datensätze ausgewählt.");
			}

			assertThat(invoiceIds.size()).isEqualTo(0);
		}
	}

	@And("^after not more than (.*)s locate up2date invoice candidates by order line:$")
	public void locate_invoice_candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			findInvoiceCandidateByOrderLine(timeoutSec, row);
		}
	}

	@And("^after not more than (.*)s locate invoice candidates of order identified by (.*)$")
	public void loadInvoiceCandidatesByOrderId(
			final int timeoutSec,
			@NonNull final String orderIdentifierStr,
			@NonNull final DataTable dataTable) throws Throwable
	{
		final StepDefDataIdentifier orderIdentifier = StepDefDataIdentifier.ofString(orderIdentifierStr);
		final OrderId orderId = orderTable.getIdOptional(orderIdentifier).orElseGet(() -> orderIdentifier.getAsId(OrderId.class));
		final DataTableRows rows = DataTableRows.of(dataTable);

		SharedTestContext.run(() -> {
			SharedTestContext.put("orderId", orderId);
			SharedTestContext.put("rows", () -> rows.stream().toList());

			final ImmutableMap<StepDefDataIdentifier, I_C_Invoice_Candidate> records = StepDefUtil.tryAndWaitForItem(
					timeoutSec,
					500,
					() -> loadInvoiceCandidatesByOrderIdNow(orderId, rows)
			);

			records.forEach(invoiceCandTable::putOrReplace);
		});
	}

	private ProviderResult<ImmutableMap<StepDefDataIdentifier, I_C_Invoice_Candidate>> loadInvoiceCandidatesByOrderIdNow(final OrderId orderId, final DataTableRows rows)
	{
		final List<I_C_Invoice_Candidate> records = invoiceCandDAO.retrieveInvoiceCandidatesForOrderId(orderId);
		SharedTestContext.put("records", records, C_Invoice_Candidate_StepDef::toString);

		if (records.size() != rows.size())
		{
			return ProviderResult.resultWasNotFound("Records count does not match rows count");
		}

		final ArrayList<I_C_Invoice_Candidate> recordsToAllocate = records.stream()
				.sorted(Comparator.comparing(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID))
				.collect(GuavaCollectors.toArrayList());

		final LinkedHashMap<StepDefDataIdentifier, I_C_Invoice_Candidate> allocations = new LinkedHashMap<>();

		rows.forEach((row) -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier("C_Invoice_Candidate_ID");
			if (allocations.containsKey(rowIdentifier))
			{
				throw new AdempiereException("Identifier `" + rowIdentifier + "` is used in more than one row");
			}

			final ProductId rowProductId = row.getAsOptionalIdentifier("M_Product_ID")
					.map(productIdentifier -> productTable.getIdOptional(productIdentifier).orElseGet(() -> productIdentifier.getAsId(ProductId.class)))
					.orElse(null);

			for (final Iterator<I_C_Invoice_Candidate> recordsIterator = recordsToAllocate.iterator(); recordsIterator.hasNext(); )
			{
				final I_C_Invoice_Candidate record = recordsIterator.next();

				//
				// Skip if product is not matching
				final ProductId recordProductId = ProductId.ofRepoId(record.getM_Product_ID());
				if (rowProductId != null && !ProductId.equals(rowProductId, recordProductId))
				{
					continue;
				}

				//
				// We found a match, allocate it
				allocations.put(rowIdentifier, record);
				recordsIterator.remove();
				break;
			}
		});

		if (allocations.size() != rows.size())
		{
			SharedTestContext.put("allocations", allocations);
			return ProviderResult.resultWasNotFound("Not all records are matching the rows");
		}

		return ProviderResult.resultWasFound(ImmutableMap.copyOf(allocations));
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
			final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

			updateInvoiceCandidates(tableRow, invoiceCandidateRecord);
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

			final InvoicingParams invoicingParams = InvoicingParams.builder()
					.ignoreInvoiceSchedule(false)
					.updateLocationAndContactForInvoice(DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsUpdateLocationAndContactForInvoice", false))
					.completeInvoices(DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + InvoicingParams.PARA_IsCompleteInvoices, true))
					.build();

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

	private void updateInvoiceCandidates(@NonNull final Map<String, String> row, @NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{

		final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice_Override);
		final BigDecimal priceEnteredOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_PriceEntered_Override);
		final BigDecimal discountOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Discount_Override);
		final Timestamp dateToInvoiceOverride = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_DateToInvoice_Override);
		final String invoiceRuleOverride = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule_Override);
		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal qualityDiscountOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QualityDiscountPercent_Override);
		final String docTypeInvoiceName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_DocTypeInvoice_ID + "." + I_C_DocType.COLUMNNAME_Name);
		final String externalHeaderId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ExternalHeaderId);

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

		if (Check.isNotBlank(externalHeaderId))
		{
			invoiceCandidateRecord.setExternalHeaderId(externalHeaderId);
		}

		saveRecord(invoiceCandidateRecord);
	}

	/**
	 * Does not just find the IC, but also makes sure the IC is up2date.
	 */
	private void findInvoiceCandidateByOrderLine(final int timeoutSec, @NonNull final Map<String, String> row) throws InterruptedException
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final ItemProvider<List<I_C_Invoice_Candidate>> provider = () -> {

			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
			if (invoiceCandidates.isEmpty())
			{
				return ProviderResult.resultWasNotFound("No C_Invoice_Candidates (yet) for C_OrderLine_ID={0} (C_OrderLine_ID.Identifier={1}", orderLine.getC_OrderLine_ID(), orderLineIdentifier);
			}
			final ImmutableList<InvoiceCandidateId> invoiceCandidateIds = invoiceCandidates.stream().map(ic -> InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID())).collect(ImmutableList.toImmutableList());
			if (invoiceCandDAO.hasInvalidInvoiceCandidates(invoiceCandidateIds))
			{
				return ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID={0} is not (yet) updated; C_OrderLine_ID={0} (C_OrderLine_ID.Identifier={1}", invoiceCandidateIds, orderLine.getC_OrderLine_ID(), orderLineIdentifier);
			}
			return ProviderResult.resultWasFound(invoiceCandidates);
		};
		final List<I_C_Invoice_Candidate> invoiceCandidates = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, provider);

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

	// TODO give it a better name or merge it with "C_Invoice_Candidates are found:" step
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
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> retrieveInvoiceCandidate(candidatesQuery), logContext);

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	@And("process invoice candidate expecting error")
	public void process_invoice_cand_expecting_error(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			try (final IAutoCloseable ignore = Loggables.temporarySetLoggable(new LogbackLoggable(logger, Level.INFO)))
			{
				final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

				final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_InvoiceCandidate_Processing);
				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
				boolean isError = false;
				try
				{
					invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId), asyncBatchId);
				}
				catch (final Exception e)
				{
					isError = true;
				}

				assumeThat(isError).isTrue();
			}
		}
	}

	@And("there is no C_Invoice_Candidate for:")
	public void validate_no_C_Invoice_Candidate_created_for_record(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final Object icReferencedRecord = getICReferencedRecord(row);

			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(icReferencedRecord);
			assertThat(invoiceCandidates.isEmpty()).isTrue();
		}
	}

	@And("update invoice candidate list with unique external header identifier")
	public void update_invoice_candidate_external_header_identifier_list(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String invoiceCandidateListIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);

			final List<I_C_Invoice_Candidate> invoiceCandidateList = invoiceCandidateListTable.get(invoiceCandidateListIdentifier);

			final List<I_C_Invoice_Candidate> updatedList = new ArrayList<>();

			for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidateList)
			{
				invoiceCandidate.setExternalHeaderId(UUID.randomUUID().toString());

				InterfaceWrapperHelper.saveRecord(invoiceCandidate);

				updatedList.add(invoiceCandidate);
			}

			invoiceCandidateListTable.putOrReplace(invoiceCandidateListIdentifier, updatedList);
		}
	}

	@And("count invoice candidates by: external header id")
	public void count_invoice_candidates_matching_external_header_id(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String externalHeaderId = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_ExternalHeaderId);
			final int numberOfInvoiceCandidates = DataTableUtil.extractIntForColumnName(tableRow, "NumberOfCandidates");

			final int foundCandidates = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
					.addEqualsFilter(COLUMNNAME_ExternalHeaderId, externalHeaderId)
					.create()
					.count();

			assertThat(foundCandidates).as("Actual number of candidates").isEqualTo(numberOfInvoiceCandidates);
		}
	}

	private ProviderResult<I_C_Invoice_Candidate> retrieveInvoiceCandidate(
			final @NonNull IQuery<I_C_Invoice_Candidate> candidateIQuery)
	{
		final I_C_Invoice_Candidate invoiceCandidate = candidateIQuery.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		if (invoiceCandidate == null)
		{
			return ProviderResult.resultWasNotFound("No invoice candidate found for query " + candidateIQuery);
		}
		return ProviderResult.resultWasFound(invoiceCandidate);
	}

	private IQuery<I_C_Invoice_Candidate> createInvoiceCandidateQuery(final @NonNull Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final IQueryBuilder<I_C_Invoice_Candidate> candQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(COLUMNNAME_C_Order_ID, orderLine.getC_Order_ID())
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

	private boolean load_C_Invoice_Candidate(@NonNull final DataTableRow rowObj)
	{
		final Map<String, String> row = rowObj.asMap();

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

		final String orderLineIdentifier = Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER))
				.orElseGet(() -> DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER));

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

		addTableRecordReferenceFiltersForInvoiceCandidate(row, invCandQueryBuilder);

		final Boolean isInEffect = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsInEffect, null);
		if (isInEffect != null)
		{
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_IsInEffect, isInEffect);
		}

		final String taxDepartureCountryIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Departure_Country_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(taxDepartureCountryIdentifier))
		{
			final I_C_Country taxDepartureCountry = countryTable.get(taxDepartureCountryIdentifier);
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_C_Tax_Departure_Country_ID, taxDepartureCountry.getC_Country_ID());
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

	private boolean loadCreditMemoCandidate(@NonNull final Map<String, String> row)
	{
		final String customerReturnIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int customerReturnId = shipmentTable.get(customerReturnIdentifier).getM_InOut_ID();

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_InOut_ID, customerReturnId)
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

	private ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateProcessed(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InterfaceWrapperHelper.refresh(invoiceCandidate);
		if (invoiceCandidate.isProcessed())
		{
			return ProviderResult.resultWasFound(invoiceCandidate);
		}
		return ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidate.getC_Invoice_Candidate_ID() + " has Processed='N'");
	}

	@NonNull
	private ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
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

		final Boolean isInEffect = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsInEffect, null);
		if (isInEffect != null)
		{
			if (isInEffect != invoiceCandidateRecord.isInEffect())
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting IsInEffect={1} but actual is {2}",
						invoiceCandidateRecord.getC_Invoice_Candidate_ID(), isInEffect, invoiceCandidateRecord.isInEffect()));
			}

		}

		final BigDecimal qtyWithIssuesEffective = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyWithIssues_Effective);
		if (qtyWithIssuesEffective != null)
		{
			if (invoiceCandidateRecord.getQtyWithIssues_Effective().compareTo(qtyWithIssuesEffective) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting QtyWithIssues_Effective={1} but actual is {2}",
						invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyWithIssuesEffective, invoiceCandidateRecord.getQtyWithIssues_Effective()));
			}
		}

		final BigDecimal netAmtToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_NetAmtToInvoice);
		if (netAmtToInvoice != null)
		{
			if (invoiceCandidateRecord.getNetAmtToInvoice().compareTo(netAmtToInvoice) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting NetAmtToInvoice={1} but actual is {2}",
						invoiceCandidateRecord.getC_Invoice_Candidate_ID(), netAmtToInvoice, invoiceCandidateRecord.getNetAmtToInvoice()));
			}
		}

		if (invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			errorCollectors.add("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
		}

		final List<String> errors = errorCollectors.build();

		if (!errors.isEmpty())
		{
			final String errorMessages = String.join(" && \n", errors);
			return ProviderResult.resultWasNotFound(errorMessages);
		}

		return ProviderResult.resultWasFound(invoiceCandidateRecord);
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

	private void validate_C_invoice_Candidate(@NonNull final I_C_Invoice_Candidate updatedInvoiceCandidate, @NonNull final Map<String, String> row)
	{
		final SoftAssertions softly = new SoftAssertions();

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
		if (qtyToInvoice != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyToInvoice())
					.as("QtyToInvoice")
					.isEqualTo(qtyToInvoice);
		}

		final BigDecimal qtyToInvoiceInUomCalc = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoiceInUOM_Calc);
		if (qtyToInvoiceInUomCalc != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyToInvoiceInUOM_Calc())
					.as("QtyToInvoiceInUOM_Calc")
					.isEqualTo(qtyToInvoiceInUomCalc);
		}

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyOrdered())
					.as("QtyOrdered")
					.isEqualTo(qtyOrdered);
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyDelivered())
					.as("qtyDelivered")
					.isEqualTo(qtyDelivered);
		}

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyEntered())
					.as("QtyEntered")
					.isEqualTo(qtyEntered);
		}

		final BigDecimal qtyToInvoiceBeforeDiscount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceBeforeDiscount);
		if (qtyToInvoiceBeforeDiscount != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyToInvoiceBeforeDiscount())
					.as("QtyToInvoiceBeforeDiscount")
					.isEqualTo(qtyToInvoiceBeforeDiscount);
		}

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyPicked);
		if (qtyPicked != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyPicked())
					.as("QtyPicked")
					.isEqualTo(qtyPicked);
		}

		final BigDecimal qtyPickedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyPickedInUOM);
		if (qtyPickedInUOM != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyPickedInUOM())
					.as("QtyPickedInUOM")
					.isEqualTo(qtyPickedInUOM);
		}

		final BigDecimal qtyDeliveredInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDeliveredInUOM);
		if (qtyPickedInUOM != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyDeliveredInUOM())
					.as("QtyDeliveredInUOM")
					.isEqualTo(qtyDeliveredInUOM);
		}

		final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
		if (qtyToInvoiceOverride != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyToInvoice_Override())
					.as("QtyToInvoice_Override")
					.isEqualTo(qtyToInvoiceOverride);
		}

		final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoiced);
		if (qtyInvoiced != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyInvoiced())
					.as("QtyInvoiced")
					.isEqualTo(qtyInvoiced);
		}

		final BigDecimal qtyInvoicedInUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInUOM);
		if (qtyInvoicedInUOM != null)
		{
			softly.assertThat(updatedInvoiceCandidate.getQtyInvoicedInUOM())
					.as("QtyInvoicedInUOM")
					.isEqualTo(qtyInvoicedInUOM);
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
		softly.assertThat(updatedInvoiceCandidate.isProcessed()).as("Processed").isEqualTo(processed);

		final String taxEffectiveIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_Effective_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(taxEffectiveIdentifier))
		{
			final I_C_Tax taxEffective = taxTable.get(taxEffectiveIdentifier);
			softly.assertThat(updatedInvoiceCandidate.getC_Tax_Effective_ID()).as("C_Tax_Effective_ID").isEqualTo(taxEffective.getC_Tax_ID());
		}

		final String activityIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(activityIdentifier))
		{
			final I_C_Activity activity = activityTable.get(activityIdentifier);
			softly.assertThat(updatedInvoiceCandidate.getC_Activity_ID()).as("C_Activity_ID").isEqualTo(activity.getC_Activity_ID());
		}

		softly.assertAll();
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

			validate_C_Invoice_Candidate_mandatory_fields(invoiceCandidateRecords.get(0), row);
			validate_C_invoice_Candidate(invoiceCandidateRecords.get(0), row);

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidateRecords.get(0));
		}
	}

	@NonNull
	private ProviderResult<I_C_Invoice_Candidate> getInvoiceCandidateIfMatches(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);
		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice);

		if (qtyToInvoice == null)
		{
			return ProviderResult.resultWasFound(invoiceCandidateRecord);
		}
		else if (qtyToInvoice.compareTo(invoiceCandidateRecord.getQtyToInvoice()) == 0)
		{
			return ProviderResult.resultWasFound(invoiceCandidateRecord);
		}
		return ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not matching criteria yet");
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
			@NonNull final OrderId orderId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_Order_ID).append(" : ").append(orderId).append("\n");

		message.append("C_Invoice_Candidate records:").append("\n");
		candidatesSupplier.get().forEach(record -> message.append(toString(record)).append("\n"));

		logger.error("*** Error while looking for C_Invoice_Candidate records, see current context: \n" + message);
	}

	private static String toString(final I_C_Invoice_Candidate record)
	{
		return COLUMNNAME_C_Invoice_Candidate_ID + " : " + record.getC_Invoice_Candidate_ID() + " ; "
				+ COLUMNNAME_M_Product_ID + " : " + record.getM_Product_ID() + " ; "
				+ COLUMNNAME_QtyEntered + " : " + record.getQtyEntered() + " ; "
				+ COLUMNNAME_QtyInvoiced + " : " + record.getQtyInvoiced() + " ; "
				+ COLUMNNAME_QtyOrdered + " : " + record.getQtyOrdered() + " ; "
				+ COLUMNNAME_QtyDelivered + " : " + record.getQtyDelivered() + " ; "
				+ COLUMNNAME_Processed + " : " + record.isProcessed() + " ; ";
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

	private void addTableRecordReferenceFiltersForInvoiceCandidate(@NonNull final Map<String, String> row, @NonNull final IQueryBuilder<I_C_Invoice_Candidate> invCandQueryBuilder)
	{
		final String referencedTableName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
		final String referencedRecordIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(referencedTableName) && Check.isNotBlank(referencedRecordIdentifier))
		{
			final int recordId;
			switch (referencedTableName)
			{
				case I_C_Flatrate_Term.Table_Name:
					recordId = contractTable.get(referencedRecordIdentifier).getC_Flatrate_Term_ID();
					break;
				case I_S_Issue.Table_Name:
					recordId = issueTable.get(referencedRecordIdentifier).getS_Issue_ID();
					break;
				default:
					throw new AdempiereException("Unsupported TableName=" + referencedTableName);
			}
			final TableRecordReference tableRecordReference = TableRecordReference.of(referencedTableName, recordId);

			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID());
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID());
		}
	}

	public void manuallyRecomputeInvoiceCandidate(
			@NonNull final Throwable throwable,
			@NonNull final DataTableRow row,
			final int timeoutSec) throws Throwable
	{
		logger.warn("*** C_Invoice_Candidate was not found within {} seconds, manually invalidate and try again if possible. "
				+ "Error message: {}", timeoutSec, throwable.getMessage());

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = row.getAsOptionalIdentifier(COLUMNNAME_C_Invoice_Candidate_ID)
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

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.get().getC_Invoice_Candidate_ID());

		final Supplier<Boolean> isInvoiceCandidateValidated = () -> !invoiceCandDAO.hasInvalidInvoiceCandidates(ImmutableList.of(invoiceCandidateId));
		StepDefUtil.tryAndWait(timeoutSec, 500, isInvoiceCandidateValidated);
	}

	@NonNull
	private Object getICReferencedRecord(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);

		switch (tableName)
		{
			case I_S_Issue.Table_Name:
				final I_S_Issue issue = issueTable.get(recordIdentifier);
				return issue;
			default:
				throw new AdempiereException("Table not supported! TableName:" + tableName);
		}
	}
}