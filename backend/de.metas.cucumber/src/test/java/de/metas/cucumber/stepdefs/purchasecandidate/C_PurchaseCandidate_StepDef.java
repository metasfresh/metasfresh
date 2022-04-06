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
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

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
	private final static Logger logger = LogManager.getLogger(C_PurchaseCandidate_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_PurchaseCandidate_StepDefData purchaseCandidateTable;

	public C_PurchaseCandidate_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_PurchaseCandidate_StepDefData purchaseCandidateTable)
	{
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.purchaseCandidateTable = purchaseCandidateTable;
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

		final Supplier<Optional<I_C_PurchaseCandidate>> isPurchaseCandidateCreated = () -> queryBL
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_C_OrderSO_ID, orderRecord.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_C_OrderLineSO_ID, orderLineRecord.getC_OrderLine_ID())
				.addEqualsFilter(COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.create()
				.firstOnlyOptional(I_C_PurchaseCandidate.class);

		final I_C_PurchaseCandidate purchaseCandidateRecord = StepDefUtil
				.tryAndWaitForItem(timeoutSec, 500, isPurchaseCandidateCreated, () -> logCurrentContext(row));

		purchaseCandidateTable.putOrReplace(DataTableUtil.extractRecordIdentifier(row, I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID), purchaseCandidateRecord);
	}

	private void logCurrentContext(@NonNull final Map<String, String> row)
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

		logger.error("*** Error while looking for purchase candidate records, see current context: \n" + message);
	}
}