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
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.IOrgDAO;
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
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

public class MD_Cockpit_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Cockpit_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final M_Product_StepDefData productTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final MD_Cockpit_StepDefData cockpitTable;
	private final M_Warehouse_StepDefData warehouseTable;

	public MD_Cockpit_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final MD_Cockpit_StepDefData cockpitTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable)
	{
		this.productTable = productTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.cockpitTable = cockpitTable;
		this.warehouseTable = warehouseTable;
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
		final String identifier = DataTableUtil.extractRecordIdentifier(tableRow, I_MD_Cockpit.Table_Name);
		final ExpectedResults expectedResults = buildExpectedResults(tableRow);
		final CockpitQuery cockpitQuery = CockpitQuery.builder()
				.productId(expectedResults.getProductId())
				.dateGeneral(expectedResults.getDateGeneral())
				.warehouseId(expectedResults.getWarehouseId())
				.storageAttributesKey(expectedResults.getStorageAttributesKey())
				.build();

		final I_MD_Cockpit mdCockpitRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> getCockpitByQuery(cockpitQuery), () -> logCurrentContext(expectedResults));

		final ItemProvider<I_MD_Cockpit> getValidCockpit = () -> {
			InterfaceWrapperHelper.refresh(mdCockpitRecord);

			return validateCockpitRecord(identifier, expectedResults, mdCockpitRecord);
		};

		final I_MD_Cockpit cockpitRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getValidCockpit, () -> logCurrentContext(expectedResults));

		cockpitTable.putOrReplace(identifier, cockpitRecord);
	}

	@NonNull
	private ExpectedResults buildExpectedResults(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final ProductId productId = ProductId.ofRepoId(productTable.get(productIdentifier).getM_Product_ID());

		final ZoneId timeZone = orgDAO.getTimeZone(Env.getOrgId());
		final Instant dateGeneral = DataTableUtil.extractLocalDateForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_DateGeneral).atStartOfDay(timeZone).toInstant();
		final BigDecimal qtyDemandSumAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate);
		final BigDecimal qtyDemandSalesOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate);
		final BigDecimal qtyStockCurrentAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate);
		final BigDecimal qtyExpectedSurplusAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate);
		final BigDecimal qtyInventoryCountAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate);
		final BigDecimal qtySupplyPurchaseOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate);
		final BigDecimal qtySupplySumAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate);
		final BigDecimal qtySupplyRequiredAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate);
		final BigDecimal qtySupplyToScheduleAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate);
		final BigDecimal mdCandidateQtyStockAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate);
		final BigDecimal qtyStockChange = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyStockChange);
		final BigDecimal qtyDemandPPOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate);
		final BigDecimal qtySupplyPPOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate);
		final BigDecimal qtySupplyDDOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate);
		final BigDecimal qtyDemandDDOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate);
		final BigDecimal qtyOrderedPurchaseOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate);
		final BigDecimal qtyOrderedSalesOrderAtDate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyOrdered_SalesOrder_AtDate);

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_AttributesKey + "." + TABLECOLUMN_IDENTIFIER);
		final AttributesKey attributeStorageKey = getAttributesKey(asiIdentifier);

		final ExpectedResults.ExpectedResultsBuilder expectedResultsBuilder = ExpectedResults.builder()
				.productId(productId)
				.storageAttributesKey(attributeStorageKey)
				.dateGeneral(dateGeneral)
				.qtyDemandSumAtDate(qtyDemandSumAtDate)
				.qtyDemandSalesOrderAtDate(qtyDemandSalesOrderAtDate)
				.qtyStockCurrentAtDate(qtyStockCurrentAtDate)
				.qtyExpectedSurplusAtDate(qtyExpectedSurplusAtDate)
				.qtyInventoryCountAtDate(qtyInventoryCountAtDate)
				.qtySupplyPurchaseOrderAtDate(qtySupplyPurchaseOrderAtDate)
				.qtySupplySumAtDate(qtySupplySumAtDate)
				.qtySupplyRequiredAtDate(qtySupplyRequiredAtDate)
				.qtySupplyToScheduleAtDate(qtySupplyToScheduleAtDate)
				.mdCandidateQtyStockAtDate(mdCandidateQtyStockAtDate)
				.qtyStockChange(qtyStockChange)
				.qtyDemandPPOrderAtDate(qtyDemandPPOrderAtDate)
				.qtySupplyPPOrderAtDate(qtySupplyPPOrderAtDate)
				.qtySupplyDDOrderAtDate(qtySupplyDDOrderAtDate)
				.qtyDemandDDOrderAtDate(qtyDemandDDOrderAtDate)
				.qtyOrderedPurchaseOrderAtDate(qtyOrderedPurchaseOrderAtDate)
				.qtyOrderedSalesOrderAtDate(qtyOrderedSalesOrderAtDate);

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
			assertThat(warehouseRecord).isNotNull();

			final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());
			expectedResultsBuilder.warehouseId(warehouseId);
		}

		return expectedResultsBuilder.build();
	}

	@NonNull
	private AttributesKey getAttributesKey(@Nullable final String asiIdentifier)
	{
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

	@NonNull
	private ItemProvider.ProviderResult<I_MD_Cockpit> validateCockpitRecord(
			@NonNull final String cockpitIdentifier,
			@NonNull final ExpectedResults expectedResults,
			@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final List<String> errorCollector = new ArrayList<>();

		final BigDecimal qtyDemandSumAtDate = expectedResults.getQtyDemandSumAtDate();
		if (qtyDemandSumAtDate != null && !cockpitRecord.getQtyDemandSum_AtDate().equals(qtyDemandSumAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyDemandSumAtDate={0} but actual is {1}",
													qtyDemandSumAtDate, cockpitRecord.getQtyDemandSum_AtDate()));
		}

		final BigDecimal qtyDemandSalesOrderAtDate = expectedResults.getQtyDemandSalesOrderAtDate();
		if (qtyDemandSalesOrderAtDate != null && !cockpitRecord.getQtyDemand_SalesOrder_AtDate().equals(qtyDemandSalesOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyDemandSalesOrderAtDate={0} but actual is {1}",
													qtyDemandSalesOrderAtDate, cockpitRecord.getQtyDemand_SalesOrder_AtDate()));
		}

		final BigDecimal qtyStockCurrentAtDate = expectedResults.getQtyStockCurrentAtDate();
		if (qtyStockCurrentAtDate != null && !cockpitRecord.getQtyStockCurrent_AtDate().equals(qtyStockCurrentAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyStockCurrentAtDate={0} but actual is {1}",
													qtyStockCurrentAtDate, cockpitRecord.getQtyStockCurrent_AtDate()));
		}

		final BigDecimal qtyExpectedSurplusAtDate = expectedResults.getQtyExpectedSurplusAtDate();
		if (qtyExpectedSurplusAtDate != null && !cockpitRecord.getQtyExpectedSurplus_AtDate().equals(qtyExpectedSurplusAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyExpectedSurplusAtDate={0} but actual is {1}",
													qtyExpectedSurplusAtDate, cockpitRecord.getQtyExpectedSurplus_AtDate()));
		}

		final BigDecimal qtyInventoryCountAtDate = expectedResults.getQtyInventoryCountAtDate();
		if (qtyInventoryCountAtDate != null && !cockpitRecord.getQtyInventoryCount_AtDate().equals(qtyInventoryCountAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyInventoryCountAtDate={0} but actual is {1}",
													qtyInventoryCountAtDate, cockpitRecord.getQtyInventoryCount_AtDate()));
		}

		final BigDecimal qtySupplyPurchaseOrderAtDate = expectedResults.getQtySupplyPurchaseOrderAtDate();
		if (qtySupplyPurchaseOrderAtDate != null && !cockpitRecord.getQtySupply_PurchaseOrder_AtDate().equals(qtySupplyPurchaseOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplyPurchaseOrderAtDate={0} but actual is {1}",
													qtySupplyPurchaseOrderAtDate, cockpitRecord.getQtySupply_PurchaseOrder_AtDate()));
		}

		final BigDecimal qtySupplySumAtDate = expectedResults.getQtySupplySumAtDate();
		if (qtySupplySumAtDate != null && !cockpitRecord.getQtySupplySum_AtDate().equals(qtySupplySumAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplySumAtDate={0} but actual is {1}",
													qtySupplySumAtDate, cockpitRecord.getQtySupplySum_AtDate()));
		}

		final BigDecimal qtySupplyRequiredAtDate = expectedResults.getQtySupplyRequiredAtDate();
		if (qtySupplyRequiredAtDate != null && !cockpitRecord.getQtySupplyRequired_AtDate().equals(qtySupplyRequiredAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplyRequiredAtDate={0} but actual is {1}",
													qtySupplyRequiredAtDate, cockpitRecord.getQtySupplyRequired_AtDate()));
		}

		final BigDecimal qtySupplyToScheduleAtDate = expectedResults.getQtySupplyToScheduleAtDate();
		if (qtySupplyToScheduleAtDate != null && !cockpitRecord.getQtySupplyToSchedule_AtDate().equals(qtySupplyToScheduleAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplyToScheduleAtDate={0} but actual is {1}",
													qtySupplyToScheduleAtDate, cockpitRecord.getQtySupplyToSchedule_AtDate()));
		}

		final BigDecimal mdCandidateQtyStockAtDate = expectedResults.getMdCandidateQtyStockAtDate();
		if (mdCandidateQtyStockAtDate != null && !cockpitRecord.getMDCandidateQtyStock_AtDate().equals(mdCandidateQtyStockAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting MDCandidateQtyStockAtDate={0} but actual is {1}",
													mdCandidateQtyStockAtDate, cockpitRecord.getMDCandidateQtyStock_AtDate()));
		}

		final BigDecimal qtyStockChange = expectedResults.getQtyStockChange();
		if (qtyStockChange != null && !cockpitRecord.getQtyStockChange().equals(qtyStockChange))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyStockChange={0} but actual is {1}",
													qtyStockChange, cockpitRecord.getQtyStockChange()));
		}

		final BigDecimal qtyDemandPPOrderAtDate = expectedResults.getQtyDemandPPOrderAtDate();
		if (qtyDemandPPOrderAtDate != null && !cockpitRecord.getQtyDemand_PP_Order_AtDate().equals(qtyDemandPPOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyDemandPPOrderAtDate={0} but actual is {1}",
													qtyDemandPPOrderAtDate, cockpitRecord.getQtyDemand_PP_Order_AtDate()));
		}

		final BigDecimal qtySupplyPPOrderAtDate = expectedResults.getQtySupplyPPOrderAtDate();
		if (qtySupplyPPOrderAtDate != null && !cockpitRecord.getQtySupply_PP_Order_AtDate().equals(qtySupplyPPOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplyPPOrderAtDate={0} but actual is {1}",
													qtySupplyPPOrderAtDate, cockpitRecord.getQtySupply_PP_Order_AtDate()));
		}

		final BigDecimal qtyDemandDDOrderAtDate = expectedResults.getQtyDemandDDOrderAtDate();
		if (qtyDemandDDOrderAtDate != null && !cockpitRecord.getQtyDemand_DD_Order_AtDate().equals(qtyDemandDDOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyDemandDDOrderAtDate={0} but actual is {1}",
													qtyDemandDDOrderAtDate, cockpitRecord.getQtyDemand_DD_Order_AtDate()));
		}

		final BigDecimal qtySupplyDDOrderAtDate = expectedResults.getQtySupplyDDOrderAtDate();
		if (qtySupplyDDOrderAtDate != null && !cockpitRecord.getQtySupply_DD_Order_AtDate().equals(qtySupplyDDOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtySupplyDDOrderAtDate={0} but actual is {1}",
													qtySupplyDDOrderAtDate, cockpitRecord.getQtySupply_DD_Order_AtDate()));
		}

		final BigDecimal qtyOrderedPurchaseOrderAtDate = expectedResults.getQtyOrderedPurchaseOrderAtDate();
		if (qtyOrderedPurchaseOrderAtDate != null && !cockpitRecord.getQtyOrdered_PurchaseOrder_AtDate().equals(qtyOrderedPurchaseOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyOrderedPurchaseOrderAtDate={0} but actual is {1}",
													qtyOrderedPurchaseOrderAtDate, cockpitRecord.getQtyOrdered_PurchaseOrder_AtDate()));
		}

		final BigDecimal qtyOrderedSalesOrderAtDate = expectedResults.getQtyOrderedSalesOrderAtDate();
		if (qtyOrderedSalesOrderAtDate != null && !cockpitRecord.getQtyOrdered_SalesOrder_AtDate().equals(qtyOrderedSalesOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("Expecting QtyOrderedSalesOrderAtDate={0} but actual is {1}",
													qtyOrderedSalesOrderAtDate, cockpitRecord.getQtyOrdered_SalesOrder_AtDate()));
		}

		if (!errorCollector.isEmpty())
		{
			final String errorMessages = MessageFormat.format("MD_Cockpit.Identifier={0}, MD_Cockpit_ID={1}:", cockpitIdentifier, cockpitRecord.getMD_Cockpit_ID())
					+ "\n *"
					+ String.join("\n *", errorCollector);

			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(cockpitRecord);
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
				.append(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).append(" : ").append(expectedResults.getQtySupplySumAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate).append(" : ").append(expectedResults.getQtySupplyRequiredAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate).append(" : ").append(expectedResults.getQtySupplyToScheduleAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate).append(" : ").append(expectedResults.getMdCandidateQtyStockAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyStockChange).append(" : ").append(expectedResults.getQtyStockChange()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate).append(" : ").append(expectedResults.getQtyDemandPPOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate).append(" : ").append(expectedResults.getQtySupplyPPOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate).append(" : ").append(expectedResults.getQtyDemandDDOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate).append(" : ").append(expectedResults.getQtySupplyDDOrderAtDate()).append("\n");

		message.append("MD_Cockpit records filtered by product:").append("\n");

		queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, expectedResults.getProductId())
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
						.append(I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate).append(" : ").append(cockpitEntry.getQtySupplyRequired_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate).append(" : ").append(cockpitEntry.getQtySupplyToSchedule_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate).append(" : ").append(cockpitEntry.getMDCandidateQtyStock_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyStockChange).append(" : ").append(cockpitEntry.getQtyStockChange()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate).append(" : ").append(cockpitEntry.getQtyDemand_PP_Order_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate).append(" : ").append(cockpitEntry.getQtySupply_PP_Order_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate).append(" : ").append(cockpitEntry.getQtyDemand_DD_Order_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate).append(" : ").append(cockpitEntry.getQtySupply_DD_Order_AtDate()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Cockpit records, see current context: \n" + message);
	}

	@NonNull
	private Optional<I_MD_Cockpit> getCockpitByQuery(@NonNull final CockpitQuery cockpitQuery)
	{
		final IQueryBuilder<I_MD_Cockpit> queryBuilder = queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_DateGeneral, TimeUtil.asTimestamp(cockpitQuery.getDateGeneral()))
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, cockpitQuery.getProductId())
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_AttributesKey, cockpitQuery.getStorageAttributesKey().getAsString());

		if (cockpitQuery.getWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID, cockpitQuery.getWarehouseId());
		}

		return queryBuilder
				.create()
				.firstOnlyOptional(I_MD_Cockpit.class);
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
		Instant dateGeneral;

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

		@Nullable
		BigDecimal qtySupplyRequiredAtDate;

		@Nullable
		BigDecimal qtySupplyToScheduleAtDate;

		@Nullable
		BigDecimal mdCandidateQtyStockAtDate;

		@Nullable
		BigDecimal qtyStockChange;

		@Nullable
		BigDecimal qtyDemandPPOrderAtDate;

		@Nullable
		BigDecimal qtySupplyPPOrderAtDate;

		@Nullable
		BigDecimal qtyDemandDDOrderAtDate;

		@Nullable
		BigDecimal qtySupplyDDOrderAtDate;

		@Nullable
		WarehouseId warehouseId;

		@Nullable
		BigDecimal qtyOrderedPurchaseOrderAtDate;

		@Nullable
		BigDecimal qtyOrderedSalesOrderAtDate;

	}

	@Builder
	@Value
	private static class CockpitQuery
	{
		@NonNull
		ProductId productId;

		@NonNull
		AttributesKey storageAttributesKey;

		@NonNull
		Instant dateGeneral;

		@Nullable
		WarehouseId warehouseId;
	}
}
