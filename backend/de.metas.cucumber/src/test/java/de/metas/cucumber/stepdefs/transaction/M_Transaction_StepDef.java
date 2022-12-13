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

package de.metas.cucumber.stepdefs.transaction;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.inventory.InventoryLineId;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class M_Transaction_StepDef
{
	private final static Logger logger = LogManager.getLogger(M_Transaction_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_Locator_StepDefData locatorTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;

	public M_Transaction_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable)
	{
		this.productTable = productTable;
		this.locatorTable = locatorTable;
		this.inventoryLineTable = inventoryLineTable;
	}

	@And("^after not more than (.*)s, metasfresh has this M_Transaction data$")
	public void metasfresh_has_this_md_cockpit_ddOrder_Detail(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateTransaction(timeoutSec, tableRow);
		}
	}

	private void validateTransaction(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Transaction.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		softly.assertThat(productRecord).isNotNull();

		final String locatorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Transaction.COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Locator locatorRecord = locatorTable.get(locatorIdentifier);
		softly.assertThat(locatorRecord).isNotNull();

		final String inventoryLineIdentifier = DataTableUtil.extractNullableStringForColumnName(tableRow, I_M_Transaction.COLUMNNAME_M_InventoryLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_InventoryLine inventoryLineRecord;
		if (Check.isNotBlank(inventoryLineIdentifier) && null != DataTableUtil.nullToken2Null(inventoryLineIdentifier))
		{
			inventoryLineRecord = inventoryLineTable.get(inventoryLineIdentifier);
			softly.assertThat(inventoryLineRecord).isNotNull();
		}
		else
		{
			inventoryLineRecord = null;
		}

		softly.assertAll();

		final M_Transaction_StepDef.TransactionQuery transactionQuery = TransactionQuery.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.locatorId(LocatorId.ofRepoId(WarehouseId.ofRepoId(locatorRecord.getM_Warehouse_ID()), locatorRecord.getM_Locator_ID()))
				.inventoryLineId(inventoryLineRecord != null
										 ? InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID())
										 : null)
				.build();

		final I_M_Transaction transactionRecord = getTransactionBy(timeoutSec, transactionQuery);

		final ItemProvider<I_M_Transaction> getValidTransaction = () -> {
			InterfaceWrapperHelper.refresh(transactionRecord);

			return validateTransactionRecord(tableRow, transactionRecord);
		};

		StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getValidTransaction, () -> logCurrentContext(transactionQuery));
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_Transaction> validateTransactionRecord(
			@NonNull final Map<String, String> tableRow,
			@NonNull final I_M_Transaction transactionRecord)
	{
		final List<String> errorCollector = new ArrayList<>();

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Transaction.COLUMNNAME_M_Transaction_ID + "." + TABLECOLUMN_IDENTIFIER);

		final BigDecimal movementQty = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_Transaction.COLUMNNAME_MovementQty);
		if (movementQty != null && movementQty.compareTo(transactionRecord.getMovementQty()) != 0)
		{
			errorCollector.add(MessageFormat.format("M_Transaction.Identifier={0}; Expecting MovementQty={1} but actual is {2}",
													identifier, movementQty, transactionRecord.getMovementQty()));
		}

		final String movementType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Transaction.COLUMNNAME_MovementType);
		if (Check.isNotBlank(movementType) && !movementType.equals(transactionRecord.getMovementType()))
		{
			errorCollector.add(MessageFormat.format("M_Transaction.Identifier={0}; Expecting MovementType={1} but actual is {2}",
													identifier, movementType, transactionRecord.getMovementType()));
		}

		if (errorCollector.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errorCollector);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(transactionRecord);
	}

	@NonNull
	private I_M_Transaction getTransactionBy(
			final int timeoutSec,
			@NonNull final M_Transaction_StepDef.TransactionQuery transactionQuery) throws InterruptedException
	{
		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getTransactionSupplier(transactionQuery), () -> logCurrentContext(transactionQuery));
	}

	@NonNull
	private Supplier<Optional<I_M_Transaction>> getTransactionSupplier(
			@NonNull final M_Transaction_StepDef.TransactionQuery transactionQuery)
	{
		final IQueryBuilder<I_M_Transaction> queryBuilder = queryBL.createQueryBuilder(I_M_Transaction.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_Locator_ID, transactionQuery.getLocatorId())
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_Product_ID, transactionQuery.getProductId());

		if (transactionQuery.getInventoryLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_InventoryLine_ID, transactionQuery.getInventoryLineId());
		}
		else
		{
			queryBuilder.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_InventoryLine_ID, null);
		}

		return () -> queryBuilder
				.create()
				.firstOnlyOptional(I_M_Transaction.class);
	}

	private void logCurrentContext(@NonNull final M_Transaction_StepDef.TransactionQuery transactionQuery)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_M_Transaction.COLUMNNAME_M_Locator_ID).append(" : ").append(transactionQuery.getLocatorId().getRepoId()).append("\n")
				.append(I_M_Transaction.COLUMNNAME_M_Product_ID).append(" : ").append(transactionQuery.getProductId().getRepoId()).append("\n");

		if (transactionQuery.getInventoryLineId() != null)
		{
			message
					.append(I_M_Transaction.COLUMNNAME_M_InventoryLine_ID).append(" : ").append(transactionQuery.getInventoryLineId().getRepoId()).append("\n");
		}

		message.append("M_Transaction records:").append("\n");

		queryBL.createQueryBuilder(I_M_Transaction.class)
				.create()
				.stream(I_M_Transaction.class)
				.forEach(transactionRecord -> message
						.append(I_M_Transaction.COLUMNNAME_M_Locator_ID).append(" : ").append(transactionRecord.getM_Locator_ID()).append(" ; ")
						.append(I_M_Transaction.COLUMNNAME_M_Product_ID).append(" : ").append(transactionRecord.getM_Product_ID()).append(" ; ")
						.append(I_M_Transaction.COLUMNNAME_M_InventoryLine_ID).append(" : ").append(transactionRecord.getM_InventoryLine_ID()).append(" ; ")
						.append(I_M_Transaction.COLUMNNAME_MovementQty).append(" : ").append(transactionRecord.getMovementQty()).append(" ; ")
						.append(I_M_Transaction.COLUMNNAME_MovementType).append(" : ").append(transactionRecord.getMovementType()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for M_Transaction records, see current context: \n" + message);
	}

	@Builder
	@Value
	private static class TransactionQuery
	{
		@NonNull
		ProductId productId;

		@NonNull
		LocatorId locatorId;

		@Nullable
		InventoryLineId inventoryLineId;
	}
}
