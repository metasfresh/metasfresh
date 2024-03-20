/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.availableforsales;

import de.metas.cucumber.stepdefs.AD_EventLog_Entry_StepDef;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_MD_Available_For_Sales;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class MD_Available_For_Sales_StepDef
{
	private final static Logger logger = LogManager.getLogger(AD_EventLog_Entry_StepDef.class);

	private final AvailableForSalesRepository availableForSalesRepository = SpringContextHolder.instance.getBean(AvailableForSalesRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public MD_Available_For_Sales_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.productTable = productTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@And("^after not more than (.*)s, MD_Available_For_Sales table contains only the following records:$")
	public void thereAreAvailableForSales(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();

		final Supplier<Boolean> supplier = () -> rows.stream().allMatch(this::matchingMDAvailableForSalesFound);

		StepDefUtil.tryAndWait(timeoutSec, 500, supplier, () -> logCurrentContext(rows));

		final Set<ProductId> productIds = rows
				.stream()
				.map(this::getProductIdFromRow)
				.map(ProductId::ofRepoId)
				.collect(Collectors.toSet());

		final Supplier<Boolean> noOfRecordsSupplier = () -> rows.size() == getAvailableForSalesNoOfRecords(productIds);

		StepDefUtil.tryAndWait(timeoutSec, 500, noOfRecordsSupplier, () -> logCurrentNoOfRecords(rows.size()));
	}

	@And("^after not more than (.*)s, MD_Available_For_Sales table is empty for product: (.*)$")
	public void validateEmptyTable(final int timeoutSec, final String productIdentifier) throws InterruptedException
	{
		final ProductId productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.map(ProductId::ofRepoId)
				.orElseGet(() -> ProductId.ofRepoId(Integer.parseInt(productIdentifier)));

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> getAvailableForSalesNoOfRecords(ImmutableSet.of(productId)) == 0, () -> logCurrentNoOfRecords(0));
	}

	@Given("metasfresh initially has no MD_Available_For_Sales data")
	public void setupMD_Candidate_Data()
	{
		truncateMDAvailableForSalesData();
	}

	private int getAvailableForSalesNoOfRecords(@NonNull final Set<ProductId> productIds)
	{
		return queryBL.createQueryBuilder(I_MD_Available_For_Sales.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID, productIds)
				.create()
				.count();
	}

	private void truncateMDAvailableForSalesData()
	{
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM MD_Available_For_Sales", ITrx.TRXNAME_None);
	}

	private boolean matchingMDAvailableForSalesFound(@NonNull final Map<String, String> row)
	{
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_QtyOnHandStock);
		final BigDecimal qtyToBeShipped = DataTableUtil.extractBigDecimalForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_QtyToBeShipped);

		final int productId = getProductIdFromRow(row);

		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);

		final String storageAttributesKeyIdentifier = DataTableUtil.extractStringForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey + "." + TABLECOLUMN_IDENTIFIER);
		final AttributesKey storageAttributesKey = attributeSetInstanceTable.getOptional(storageAttributesKeyIdentifier)
				.map(I_M_AttributeSetInstance::getM_AttributeSetInstance_ID)
				.map(AttributeSetInstanceId::ofRepoId)
				.flatMap(AttributesKeys::createAttributesKeyFromASIStorageAttributes)
				.orElse(AttributesKey.NONE);

		final I_MD_Available_For_Sales availableForSales = availableForSalesRepository.getByUniqueKey(ProductId.ofRepoId(productId),
																									  storageAttributesKey,
																									  OrgId.ofRepoId(product.getAD_Org_ID()));

		return availableForSales != null &&
				availableForSales.getQtyOnHandStock().equals(qtyOnHand) &&
				availableForSales.getQtyToBeShipped().equals(qtyToBeShipped);
	}

	private void logCurrentContext(@NonNull final List<Map<String, String>> rows)
	{
		final StringBuilder message = new StringBuilder();
		message.append("Looking for instances with:").append("\n");

		for (final Map<String, String> row : rows)
		{
			final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_QtyOnHandStock);
			final BigDecimal qtyToBeShipped = DataTableUtil.extractBigDecimalForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_QtyToBeShipped);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int productId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			final String storageAttributesKeyIdentifier = DataTableUtil.extractStringForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey + "." + TABLECOLUMN_IDENTIFIER);
			final AttributesKey storageAttributesKey = attributeSetInstanceTable.getOptional(storageAttributesKeyIdentifier)
					.map(I_M_AttributeSetInstance::getM_AttributeSetInstance_ID)
					.map(AttributeSetInstanceId::ofRepoId)
					.flatMap(AttributesKeys::createAttributesKeyFromASIStorageAttributes)
					.orElse(AttributesKey.NONE);

			message.append(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID).append(" : ").append(productId).append("\n")
					.append(I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey).append(" : ").append(storageAttributesKey).append("\n")
					.append(I_MD_Available_For_Sales.COLUMNNAME_QtyOnHandStock).append(" : ").append(qtyOnHand).append("\n")
					.append(I_MD_Available_For_Sales.COLUMNNAME_QtyToBeShipped).append(" : ").append(qtyToBeShipped).append("\n")
					.append("-------------------------------------------------------");
		}

		message.append("MD_Available_For_Sales records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Available_For_Sales.class)
				.create()
				.stream(I_MD_Available_For_Sales.class)
				.forEach(availableForSalesRecord -> message
						.append(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID).append(" : ").append(availableForSalesRecord.getM_Product_ID()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey).append(" : ").append(availableForSalesRecord.getStorageAttributesKey()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_QtyOnHandStock).append(" : ").append(availableForSalesRecord.getQtyOnHandStock()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_QtyToBeShipped).append(" : ").append(availableForSalesRecord.getQtyToBeShipped()).append("\n")
						.append("-------------------------------------------------------"));

		logger.error("*** Error while looking for MD_Available_For_Sales records, see current context: \n" + message);
	}

	private void logCurrentNoOfRecords(final int noOfRecords)
	{
		final StringBuilder message = new StringBuilder();
		message.append("Looking for ").append(noOfRecords).append(" records").append("\n")
				.append("MD_Available_For_Sales records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Available_For_Sales.class)
				.create()
				.stream(I_MD_Available_For_Sales.class)
				.forEach(availableForSalesRecord -> message
						.append(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID).append(" : ").append(availableForSalesRecord.getM_Product_ID()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey).append(" : ").append(availableForSalesRecord.getStorageAttributesKey()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_QtyOnHandStock).append(" : ").append(availableForSalesRecord.getQtyOnHandStock()).append("\n")
						.append(I_MD_Available_For_Sales.COLUMNNAME_QtyToBeShipped).append(" : ").append(availableForSalesRecord.getQtyToBeShipped()).append("\n")
						.append("-------------------------------------------------------"));

		logger.error("*** Error while looking for MD_Available_For_Sales records, see current context: \n" + message);
	}

	private int getProductIdFromRow(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		return productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));
	}
}
