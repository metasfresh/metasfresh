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

package de.metas.cucumber.stepdefs.purchasecandidate;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.purchasecandidate.v2.CreatePurchaseCandidate_StepDef;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_C_OrderLineSO_ID;
import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_C_OrderSO_ID;
import static de.metas.purchasecandidate.model.I_C_PurchaseCandidate.COLUMNNAME_M_Product_ID;
import static org.assertj.core.api.Assertions.*;

public class C_PurchaseCandidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(CreatePurchaseCandidate_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IdentifierIds_StepDefData identifierIdsTable;
	private final C_PurchaseCandidate_StepDefData purchaseCandidateTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;

	public C_PurchaseCandidate_StepDef(
			@NonNull final IdentifierIds_StepDefData identifierIdsTable,
			@NonNull final C_PurchaseCandidate_StepDefData purchaseCandidateTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable)
	{
		this.identifierIdsTable = identifierIdsTable;
		this.purchaseCandidateTable = purchaseCandidateTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
	}

	@And("^no C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_no_C_PurchaseCandidate_found(@NonNull final String orderLineIdentifier)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		try
		{
			assertThat(getQueryByOrderLineId(orderLineId).count() == 0).isTrue();
		}
		catch (final Throwable throwable)
		{
			logCurrentContextExpectedNoRecords(orderLineId);
		}
	}

	@And("^after not more than (.*)s, C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_C_PurchaseCandidate_found_for_OrderLine(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		final Supplier<Optional<I_C_PurchaseCandidate>> recordFound = () -> Optional.ofNullable(getQueryByOrderLineId(orderLineId).first());

		final I_C_PurchaseCandidate purchaseCandidateRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, recordFound);

		final String purchaseCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, StepDefConstants.TABLECOLUMN_IDENTIFIER);
		purchaseCandidateTable.putOrReplace(purchaseCandidateIdentifier, purchaseCandidateRecord);
	}

	@And("^after not more than (.*)s, C_PurchaseCandidates are found$")
	public void find_purchase_candidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			findPurchaseCandidate(timeoutSec, row);
		}
	}

	@And("C_PurchaseCandidates are validated")
	public void validate_purchase_candidates(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePurchaseCandidate(row);
		}
	}

	@NonNull
	private IQuery<I_C_PurchaseCandidate> getQueryByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create();
	}

	private void logCurrentContextExpectedNoRecords(@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Validating no records found for orderLineID :").append("\n")
				.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineId).append("\n");

		message.append("C_PurchaseCandidate records:").append("\n");

		getQueryByOrderLineId(orderLineId)
				.stream(I_C_PurchaseCandidate.class)
				.forEach(purchaseCandidate -> message
						.append(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID).append(" : ").append(purchaseCandidate.getC_PurchaseCandidate_ID()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while validating no C_PurchaseCandidate found for orderLineId: " + orderLineId + ", see found records: \n" + message);
	}

	@Nullable
	private OrderLineId getOrderLineIdByIdentifier(@NonNull final String orderLineIdentifier)
	{
		return OrderLineId.ofRepoIdOrNull(identifierIdsTable.getOptional(orderLineIdentifier)
												  .orElseGet(() -> orderLineTable.get(orderLineIdentifier).getC_OrderLine_ID()));
	}

	@NonNull
	private String logCurrentContext(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + ".Identifier");
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderSO_ID + ".Identifier");
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderLineSO_ID + ".Identifier");

		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		final I_C_OrderLine orderLineRecord = orderLineTable.get(orderLineIdentifier);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_C_OrderSO_ID).append(" : ").append(orderRecord.getC_Order_ID()).append("\n")
				.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineRecord.getC_OrderLine_ID()).append("\n")
				.append(COLUMNNAME_M_Product_ID).append(" : ").append(productRecord.getM_Product_ID()).append("\n");

		message.append("C_PurchaseCandidate records:").append("\n");

		queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.forEach(purchaseCandidateRecord -> message
						.append(COLUMNNAME_C_OrderSO_ID).append(" : ").append(purchaseCandidateRecord.getC_OrderSO_ID()).append(" ; ")
						.append(COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(purchaseCandidateRecord.getC_OrderLineSO_ID()).append(" ; ")
						.append(COLUMNNAME_M_Product_ID).append(" : ").append(purchaseCandidateRecord.getM_Product_ID()).append(" ; ")
						.append("\n"));

		return "see current context: \n" + message;
	}

	private void validatePurchaseCandidate(@NonNull final Map<String, String> row)
	{
		final String purchaseCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID + ".Identifier");

		final I_C_PurchaseCandidate purchaseCandidateRecord = purchaseCandidateTable.get(purchaseCandidateIdentifier);

		final BigDecimal qtyToPurchase = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_PurchaseCandidate.COLUMNNAME_QtyToPurchase);

		if (qtyToPurchase != null)
		{
			assertThat(purchaseCandidateRecord.getQtyToPurchase()).isEqualTo(qtyToPurchase);
		}
	}

	private void findPurchaseCandidate(final int timeoutSec, @NonNull final Map<String, String> row) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + ".Identifier");
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderSO_ID + ".Identifier");
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OrderLineSO_ID + ".Identifier");

		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		final I_C_OrderLine orderLineRecord = orderLineTable.get(orderLineIdentifier);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final I_C_PurchaseCandidate purchaseCandidateRecord = StepDefUtil
				.tryAndWaitForItem(timeoutSec, 500,
								   () -> getPurchaseCandidate(orderRecord, orderLineRecord, productRecord),
								   () -> logCurrentContext(row));

		purchaseCandidateTable.putOrReplace(DataTableUtil.extractRecordIdentifier(row, I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID), purchaseCandidateRecord);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_PurchaseCandidate> getPurchaseCandidate(
			@NonNull final I_C_Order orderRecord,
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final I_M_Product productRecord)
	{
		return queryBL
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_C_OrderSO_ID, orderRecord.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_C_OrderLineSO_ID, orderLineRecord.getC_OrderLine_ID())
				.addEqualsFilter(COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.create()
				.firstOnlyOptional(I_C_PurchaseCandidate.class)
				.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound("Couldn't find any C_PurchaseCandidate querying by"
																					   + " C_OrderSO_ID=" + orderRecord.getC_Order_ID()
																					   + " C_OrderLineSO_ID=" + orderLineRecord.getC_OrderLine_ID()
																					   + " M_Product_ID=" + productRecord.getM_Product_ID()));
	}
}
