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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MD_Cockpit_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	@NonNull private final MD_Cockpit_StepDefData cockpitTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	@And("^after not more than (.*)s, metasfresh has this MD_Cockpit data$")
	public void metasfresh_has_this_md_cockpit_data(
			final int timeoutSec,
			@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> validateCockpitRecord(timeoutSec, row));
	}

	private void validateCockpitRecord(
			final int timeoutSec,
			@NonNull final DataTableRow tableRow) throws InterruptedException
	{
		final StepDefDataIdentifier identifier = tableRow.getAsIdentifier();
		final ExpectedResults expectedResults = toExpectedResults(tableRow);
		SharedTestContext.put("expectedResults", expectedResults);

		final CockpitQuery cockpitQuery = CockpitQuery.builder()
				.productId(expectedResults.getProductId())
				.dateGeneral(expectedResults.getDateGeneral())
				.warehouseId(expectedResults.getWarehouseId())
				.storageAttributesKey(expectedResults.getStorageAttributesKey())
				.build();

		final I_MD_Cockpit mdCockpitRecord = StepDefUtil.tryAndWaitForItem(toSqlQuery(cockpitQuery))
				.logContext(() -> logCurrentContext(expectedResults))
				.maxWaitSeconds(timeoutSec)
				.execute();

		final I_MD_Cockpit mdCockpitRecordValidated = StepDefUtil.<I_MD_Cockpit>tryAndWaitForItem()
				.worker(() -> {
					InterfaceWrapperHelper.refresh(mdCockpitRecord);
					return validateCockpitRecord(identifier, expectedResults, mdCockpitRecord);
				})
				.logContext(() -> logCurrentContext(expectedResults))
				.maxWaitSeconds(timeoutSec)
				.execute();

		cockpitTable.putOrReplace(identifier, mdCockpitRecordValidated);
	}

	@NonNull
	private ExpectedResults toExpectedResults(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_MD_Cockpit.COLUMNNAME_M_Product_ID);
		final ProductId productId = productTable.getId(productIdentifier);

		final ZoneId timeZone = orgDAO.getTimeZone(Env.getOrgId());
		final Instant dateGeneral = row.getAsLocalDate(I_MD_Cockpit.COLUMNNAME_DateGeneral).atStartOfDay(timeZone).toInstant();
		final BigDecimal qtyDemandSumAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate).orElse(null);
		final BigDecimal qtyDemandSalesOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate).orElse(null);
		final BigDecimal qtyStockCurrentAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate).orElse(null);
		final BigDecimal qtyExpectedSurplusAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate).orElse(null);
		final BigDecimal qtyInventoryCountAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate).orElse(null);
		final BigDecimal qtySupplyPurchaseOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate).orElse(null);
		final BigDecimal qtySupplySumAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).orElse(null);
		final BigDecimal qtySupplyRequiredAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate).orElse(null);
		final BigDecimal qtySupplyToScheduleAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate).orElse(null);
		final BigDecimal mdCandidateQtyStockAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate).orElse(null);
		final BigDecimal qtyStockChange = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyStockChange).orElse(null);
		final BigDecimal qtyDemandPPOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate).orElse(null);
		final BigDecimal qtySupplyPPOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate).orElse(null);
		final BigDecimal qtySupplyDDOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate).orElse(null);
		final BigDecimal qtyDemandDDOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate).orElse(null);
		final BigDecimal qtyOrderedPurchaseOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate).orElse(null);
		final BigDecimal qtyOrderedSalesOrderAtDate = row.getAsOptionalBigDecimal(I_MD_Cockpit.COLUMNNAME_QtyOrdered_SalesOrder_AtDate).orElse(null);

		final AttributesKey attributeStorageKey = row.getAsOptionalIdentifier(I_MD_Cockpit.COLUMNNAME_AttributesKey).map(this::getAttributesKey).orElse(AttributesKey.NONE);

		return ExpectedResults.builder()
				.identifier(row.getAsOptionalIdentifier().orElse(null))
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
				.qtyOrderedSalesOrderAtDate(qtyOrderedSalesOrderAtDate)
				.warehouseId(row.getAsOptionalIdentifier(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID).map(warehouseTable::getId).orElse(null))
				.build();
	}

	@NonNull
	private AttributesKey getAttributesKey(@NonNull final StepDefDataIdentifier asiIdentifier)
	{
		final AttributeSetInstanceId asiId = attributeSetInstanceTable.getId(asiIdentifier);
		return AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_MD_Cockpit> validateCockpitRecord(
			@NonNull final StepDefDataIdentifier cockpitIdentifier,
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

	private String logCurrentContext(@NonNull final ExpectedResults expectedResults)
	{
		final MDCockpitToTabularStringConverter tabularConverter = newTabularConverter();

		final ProductId productId = expectedResults.getProductId();
		final List<I_MD_Cockpit> records = queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, productId)
				.create()
				.list();

		return "\nExpected:\n"
				+ tabularConverter.toTableFromExpectedResults(expectedResults).toPrint().ident(1)
				+ "\nActual MD_Cockpit records filtered by product `" + tabularConverter.toString(productId) + "`:" + "\n"
				+ tabularConverter.toTableFromRecords(records).toPrint().ident(1);
	}

	@NonNull
	private IQuery<I_MD_Cockpit> toSqlQuery(@NonNull final CockpitQuery cockpitQuery)
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

		return queryBuilder.create();
	}

	private MDCockpitToTabularStringConverter newTabularConverter()
	{
		return MDCockpitToTabularStringConverter.builder()
				.productTable(productTable)
				.cockpitTable(cockpitTable)
				.warehouseTable(warehouseTable)
				.build();
	}

	@Builder
	@Value
	private static class CockpitQuery
	{
		@NonNull ProductId productId;
		@NonNull AttributesKey storageAttributesKey;
		@NonNull Instant dateGeneral;
		@Nullable WarehouseId warehouseId;
	}
}
