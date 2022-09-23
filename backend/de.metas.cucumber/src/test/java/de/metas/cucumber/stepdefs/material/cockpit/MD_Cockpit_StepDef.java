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

package de.metas.cucumber.stepdefs.material.cockpit;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class MD_Cockpit_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Cockpit_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public MD_Cockpit_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.productTable = productTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@And("^after not more than (.*)s, metasfresh has this MD_Cockpit data$")
	public void metasfresh_has_this_md_cockpit_data(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateCockpitRecord(timeoutSec, tableRow);
		}
	}

	private void validateCockpitRecord(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final ExpectedResults expectedResults = buildExpectedResults(tableRow);

		final I_MD_Cockpit mdCockpitRecord = getCockpitByProductIdAttributesKeyAndDateGeneral(timeoutSec, expectedResults);

		final Supplier<Boolean> mdCockpitIsValid = () -> {
			InterfaceWrapperHelper.refresh(mdCockpitRecord);

			return validateCockpitRecord(expectedResults, mdCockpitRecord);
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, mdCockpitIsValid, () -> logCurrentContext(expectedResults));
	}

	@NonNull
	private ExpectedResults buildExpectedResults(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final ProductId productId = ProductId.ofRepoId(productTable.get(productIdentifier).getM_Product_ID());

		final LocalDate dateGeneral = DataTableUtil.extractLocalDateForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_DateGeneral);
		final BigDecimal qtyDemandSum = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate);
		final BigDecimal qtyDemandSalesOrder = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate);
		final BigDecimal qtyStockCurrent = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate);
		final BigDecimal qtyExpectedSurplus = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate);
		final BigDecimal qtyInventoryCount = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate);
		final BigDecimal qtySupplyPurchaseOrder = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate);
		final BigDecimal qtySupplySum = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate);

		return ExpectedResults.builder()
				.productId(productId)
				.storageAttributesKey(getAttributesKey(tableRow))
				.dateGeneral(dateGeneral)
				.qtyDemandSumAtDate(qtyDemandSum)
				.qtyDemandSalesOrderAtDate(qtyDemandSalesOrder)
				.qtyStockCurrentAtDate(qtyStockCurrent)
				.qtyExpectedSurplusAtDate(qtyExpectedSurplus)
				.qtyInventoryCountAtDate(qtyInventoryCount)
				.qtySupplyPurchaseOrderAtDate(qtySupplyPurchaseOrder)
				.qtySupplySumAtDate(qtySupplySum)
				.build();
	}

	@NonNull
	private AttributesKey getAttributesKey(@NonNull final Map<String, String> tableRow)
	{
		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_AttributesKey + "." + TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(asiIdentifier);

			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSetInstance_ID());
			return AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(asiId)
					.orElse(AttributesKey.NONE);
		}
		else
		{
			return AttributesKey.NONE;
		}
	}

	private boolean validateCockpitRecord(
			@NonNull final ExpectedResults expectedResults,
			@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final BigDecimal qtyDemandSum = expectedResults.getQtyDemandSumAtDate();
		if (qtyDemandSum != null && !cockpitRecord.getQtyDemandSum_AtDate().equals(qtyDemandSum))
		{
			return false;
		}

		final BigDecimal qtyDemandSalesOrder = expectedResults.getQtyDemandSalesOrderAtDate();
		if (qtyDemandSalesOrder != null && !cockpitRecord.getQtyDemand_SalesOrder_AtDate().equals(qtyDemandSalesOrder))
		{
			return false;
		}

		final BigDecimal qtyStockCurrent = expectedResults.getQtyStockCurrentAtDate();
		if (qtyStockCurrent != null && !cockpitRecord.getQtyStockCurrent_AtDate().equals(qtyStockCurrent))
		{
			return false;
		}

		final BigDecimal qtyExpectedSurplus = expectedResults.getQtyExpectedSurplusAtDate();
		if (qtyExpectedSurplus != null && !cockpitRecord.getQtyExpectedSurplus_AtDate().equals(qtyExpectedSurplus))
		{
			return false;
		}

		final BigDecimal qtyInventoryCount = expectedResults.getQtyInventoryCountAtDate();
		if (qtyInventoryCount != null && !cockpitRecord.getQtyInventoryCount_AtDate().equals(qtyInventoryCount))
		{
			return false;
		}

		final BigDecimal qtySupplyPurchaseOrder = expectedResults.getQtySupplyPurchaseOrderAtDate();
		if (qtySupplyPurchaseOrder != null && !cockpitRecord.getQtySupply_PurchaseOrder_AtDate().equals(qtySupplyPurchaseOrder))
		{
			return false;
		}

		final BigDecimal qtySupplySum = expectedResults.getQtySupplySumAtDate();
		if (qtySupplySum != null && !cockpitRecord.getQtySupplySum_AtDate().equals(qtySupplySum))
		{
			return false;
		}

		return true;
	}

	@NonNull
	private I_MD_Cockpit getCockpitByProductIdAttributesKeyAndDateGeneral(
			final int timeoutSec,
			@NonNull final ExpectedResults expectedResults) throws InterruptedException
	{
		final ProductId productId = expectedResults.getProductId();
		final AttributesKey storageAttributesKey = expectedResults.getStorageAttributesKey();

		final Supplier<Boolean> mdCockpitIsFound = () -> getQueryBuilderBy(productId, storageAttributesKey)
				.create()
				.stream()
				.anyMatch(record -> record.getDateGeneral().toLocalDateTime().toLocalDate().equals(expectedResults.getDateGeneral()));

		StepDefUtil.tryAndWait(timeoutSec, 500, mdCockpitIsFound, () -> logCurrentContext(expectedResults));

		return getQueryBuilderBy(productId, storageAttributesKey)
				.create()
				.stream()
				.filter(record -> record.getDateGeneral().toLocalDateTime().toLocalDate().equals(expectedResults.getDateGeneral()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Record not found for ExpectedResult: " + expectedResults));
	}

	private void logCurrentContext(@NonNull final ExpectedResults expectedResults)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_M_Product_ID).append(" : ").append(expectedResults.getProductId()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_AttributesKey).append(" : ").append(expectedResults.getStorageAttributesKey().getAsString()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_DateGeneral).append(" : ").append(expectedResults.getDateGeneral()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate).append(" : ").append(expectedResults.getQtyDemandSumAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate).append(" : ").append(expectedResults.getQtyDemandSalesOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate).append(" : ").append(expectedResults.getQtyStockCurrentAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate).append(" : ").append(expectedResults.getQtyExpectedSurplusAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate).append(" : ").append(expectedResults.getQtyInventoryCountAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate).append(" : ").append(expectedResults.getQtySupplyPurchaseOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).append(" : ").append(expectedResults.getQtySupplySumAtDate()).append("\n");

		message.append("MD_Cockpit records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.create()
				.stream(I_MD_Cockpit.class)
				.forEach(cockpitEntry -> message
						.append(I_MD_Cockpit.COLUMNNAME_M_Product_ID).append(" : ").append(cockpitEntry.getM_Product_ID()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID).append(" : ").append(cockpitEntry.getM_Warehouse_ID()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_AttributesKey).append(" : ").append(cockpitEntry.getAttributesKey()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_DateGeneral).append(" : ").append(cockpitEntry.getDateGeneral()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate).append(" : ").append(cockpitEntry.getQtyDemand_SalesOrder_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate).append(" : ").append(cockpitEntry.getQtyDemandSum_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate).append(" : ").append(cockpitEntry.getQtyStockCurrent_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate).append(" : ").append(cockpitEntry.getQtyExpectedSurplus_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate).append(" : ").append(cockpitEntry.getQtyInventoryCount_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate).append(" : ").append(cockpitEntry.getQtySupply_PurchaseOrder_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).append(" : ").append(cockpitEntry.getQtySupplySum_AtDate()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Cockpit records, see current context: \n" + message);
	}

	@NonNull
	private IQueryBuilder<I_MD_Cockpit> getQueryBuilderBy(
			@NonNull final ProductId productId,
			@NonNull final AttributesKey attributesKey)
	{
		return queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_AttributesKey, attributesKey.getAsString());
	}

	@Builder
	@Value
	private static class ExpectedResults
	{
		@NonNull
		ProductId productId;

		@NonNull
		AttributesKey storageAttributesKey;

		@NonNull
		LocalDate dateGeneral;

		@Nullable
		BigDecimal qtyDemandSumAtDate;

		@Nullable
		BigDecimal qtyDemandSalesOrderAtDate;

		@Nullable
		BigDecimal qtyStockCurrentAtDate;

		@Nullable
		BigDecimal qtyExpectedSurplusAtDate;

		@Nullable
		BigDecimal qtyInventoryCountAtDate;

		@Nullable
		BigDecimal qtySupplyPurchaseOrderAtDate;

		@Nullable
		BigDecimal qtySupplySumAtDate;
	}
}
