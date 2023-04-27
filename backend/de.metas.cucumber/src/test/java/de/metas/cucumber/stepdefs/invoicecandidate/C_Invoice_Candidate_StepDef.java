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
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.TableRecordReference_StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.rest_api.v2.invoice.impl.InvoiceService;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LogbackLoggable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_IsInEffect;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Record_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class C_Invoice_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final M_Product_StepDefData productTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_InOutLine_StepDefData inoutLineTable;
	private final TableRecordReference_StepDefUtil tableRecordReferenceStepDefUtil;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_InOutLine_StepDefData inoutLineTable,
			@NonNull final TableRecordReference_StepDefUtil tableRecordReferenceStepDefUtil)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.productTable = productTable;
		this.orderLineTable = orderLineTable;
		this.inoutLineTable = inoutLineTable;
		this.tableRecordReferenceStepDefUtil = tableRecordReferenceStepDefUtil;
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

				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
				boolean isError = false;
				try
				{
					invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
				}
				catch (final Exception e)
				{
					isError = true;
				}

				assumeThat(isError).isTrue();
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

			final Boolean approvalForInvoicing = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_ApprovalForInvoicing);
			if (approvalForInvoicing != null)
			{
				invoiceCandidate.setApprovalForInvoicing(approvalForInvoicing);
			}

			saveRecord(invoiceCandidate);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
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

				final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
				assertThat(updatedInvoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);

				final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
				if (qtyOrdered != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
				}

				final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
				if (qtyDelivered != null)
				{
					assertThat(updatedInvoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
				}

				final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(productIdentifier))
				{
					final I_M_Product product = productTable.get(productIdentifier);
					assertThat(updatedInvoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
				}

				final String recordIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
				final String tableName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.TableName");

				if (Check.isNotBlank(recordIdentifier) && Check.isNotBlank(tableName))
				{
					final TableRecordReference tableRecordReference = tableRecordReferenceStepDefUtil.getTableRecordReferenceFromIdentifier(recordIdentifier, tableName);

					assertThat(updatedInvoiceCandidate.getRecord_ID()).isEqualTo(tableRecordReference.getRecord_ID());
					assertThat(updatedInvoiceCandidate.getAD_Table_ID()).isEqualTo(tableRecordReference.getAD_Table_ID());
				}

				final Boolean isInEffect = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsInEffect, null);
				if (isInEffect != null)
				{
					assertThat(updatedInvoiceCandidate.isInEffect()).isEqualTo(isInEffect);
				}
			}
			catch (final Throwable e)
			{
				wrapInvoiceCandidateRelatedException(e, invoiceCandidateRecord, invoiceCandidateIdentifier);
			}
		}
	}

	private boolean load_C_Invoice_Candidate(@NonNull final Map<String, String> row)
	{
		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, COLUMNNAME_QtyToInvoice);

		final IQueryBuilder<I_C_Invoice_Candidate> invCandQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(COLUMNNAME_QtyToInvoice, qtyToInvoice);

		final String shipmentLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (shipmentLineIdentifier != null)
		{
			final I_M_InOutLine shipmentLine = inoutLineTable.get(shipmentLineIdentifier);

			final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
					.addEqualsFilter(COLUMNNAME_M_InOutLine_ID, shipmentLine.getM_InOutLine_ID())
					.create()
					.firstOnlyOrNull(I_C_InvoiceCandidate_InOutLine.class);

			if (invoiceCandidateInOutLine == null)
			{
				return false;
			}

			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateInOutLine.getC_Invoice_Candidate_ID());
		}

		final String orderLineIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
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

		final Boolean isInEffect = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsInEffect, null);
		if (isInEffect != null)
		{
			invCandQueryBuilder.addEqualsFilter(COLUMNNAME_IsInEffect, isInEffect);
		}

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = invCandQueryBuilder
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

	private void manuallyRecomputeInvoiceCandidate(
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

		invoiceCandDAO.invalidateCand(invoiceCandidate.get());

		invoiceCandBL.updateInvalid()
				.setContext(Env.getCtx(), ITrx.TRXNAME_None)
				.setTaggedWithAnyTag()
				.setOnlyC_Invoice_Candidates(ImmutableList.of(invoiceCandidate.get()))
				.update();
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
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
}