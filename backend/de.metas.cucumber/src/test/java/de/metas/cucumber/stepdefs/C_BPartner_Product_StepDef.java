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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_CustomerLabelName;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_Description;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_EAN_CU;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_ExclusionFromPurchaseReason;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_ExclusionFromSaleReason;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_GTIN;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_Ingredients;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsActive;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsExcludedFromPurchase;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_ProductNo;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_SeqNo;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_UPC;
import static org.compiere.model.I_C_BPartner_Product.COLUMNNAME_UsedForVendor;

public class C_BPartner_Product_StepDef
{
	private final M_Product_StepDefData productTable;
	private final BPartnerProductStepDefData bpartnerProductTable;
	private final C_BPartner_StepDefData bPartnerTable;

	private final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);

	public C_BPartner_Product_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final BPartnerProductStepDefData bpartnerProductTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.productTable = productTable;
		this.bpartnerProductTable = bpartnerProductTable;
		this.bPartnerTable = bPartnerTable;
	}

	@And("locate bpartner product by product and bpartner")
	public void locate_bpartnerProduct_by_product_and_bpartner(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			locateBPartnerProductByProductAndBPartner(tableRow);
		}
	}

	@Then("verify bpartner product info")
	public void verify_bpartner_product_info(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> productTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : productTableList)
		{
			verifyBPartnerProductInfo(dataTableRow);
		}
	}

	@Given("metasfresh contains C_BPartner_Product")
	public void create_C_BPartner_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createBPartnerProduct);
	}

	private void locateBPartnerProductByProductAndBPartner(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_Product productRecord = productTable.get(productIdentifier);
		final I_C_BPartner bPartnerRecord = bPartnerTable.get(bpartnerIdentifier);

		final BPartnerProduct bPartnerProduct = productRepository.getByIdOrNull(ProductId.ofRepoId(productRecord.getM_Product_ID()), BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID()));
		assertThat(bPartnerProduct).isNotNull();

		final String bpartnerProductIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		bpartnerProductTable.put(bpartnerProductIdentifier, bPartnerProduct);
	}

	private void verifyBPartnerProductInfo(@NonNull final Map<String, String> row)
	{
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsActive);
		final Integer seqNo = DataTableUtil.extractIntegerOrNullForColumnName(row, COLUMNNAME_SeqNo);
		final String productNo = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ProductNo);
		final String description = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Description);
		final String ean = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_EAN_CU);
		final String gtin = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_GTIN);
		final String customerLabelName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_CustomerLabelName);
		final String ingredients = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Ingredients);
		final boolean isExcludedFromSale = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsExcludedFromSale);
		final String exclusionFromSaleReason = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_ExclusionFromSaleReason);
		final boolean isExcludedFromPurchase = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsExcludedFromPurchase);
		final String exclusionFromPurchaseReason = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_ExclusionFromPurchaseReason);

		final String bpartnerProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final BPartnerProduct bPartnerProduct = bpartnerProductTable.get(bpartnerProductIdentifier);

		assertThat(bPartnerProduct.getActive()).isEqualTo(isActive);
		assertThat(bPartnerProduct.getSeqNo()).isEqualTo(seqNo);
		assertThat(bPartnerProduct.getProductNo()).isEqualTo(productNo);
		assertThat(bPartnerProduct.getDescription()).isEqualTo(description);
		assertThat(bPartnerProduct.getCuEAN()).isEqualTo(ean);
		assertThat(bPartnerProduct.getGtin()).isEqualTo(gtin);
		assertThat(bPartnerProduct.getCustomerLabelName()).isEqualTo(customerLabelName);
		assertThat(bPartnerProduct.getIngredients()).isEqualTo(ingredients);
		assertThat(bPartnerProduct.getIsExcludedFromSales()).isEqualTo(isExcludedFromSale);
		assertThat(bPartnerProduct.getExclusionFromSalesReason()).isEqualTo(exclusionFromSaleReason);
		assertThat(bPartnerProduct.getIsExcludedFromPurchase()).isEqualTo(isExcludedFromPurchase);
		assertThat(bPartnerProduct.getExclusionFromPurchaseReason()).isEqualTo(exclusionFromPurchaseReason);
	}

	private void createBPartnerProduct(@NonNull final DataTableRow tableRow)
	{
		final I_C_BPartner_Product bPartnerProductRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bPartnerProductRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		bPartnerProductRecord.setUsedForCustomer(true);
		bPartnerProductRecord.setShelfLifeMinDays(0);
		bPartnerProductRecord.setShelfLifeMinPct(0);

		final ProductId productId = tableRow.getAsIdentifier(COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
		bPartnerProductRecord.setM_Product_ID(productId.getRepoId());

		final StepDefDataIdentifier bpartnerIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_ID);
		final BPartnerId bpartnerId = bPartnerTable.getIdOptional(bpartnerIdentifier)
				.orElseGet(() -> bpartnerIdentifier.getAsId(BPartnerId.class));
		
		bPartnerProductRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		
		bPartnerProductRecord.setUsedForVendor(tableRow.getAsOptionalBoolean(COLUMNNAME_UsedForVendor).orElse(true));
		bPartnerProductRecord.setIsExcludedFromSale(tableRow.getAsOptionalBoolean(COLUMNNAME_IsExcludedFromSale).orElse(false));
		tableRow.getAsOptionalString(COLUMNNAME_ExclusionFromSaleReason).ifPresent(bPartnerProductRecord::setExclusionFromSaleReason);
		bPartnerProductRecord.setIsExcludedFromPurchase(tableRow.getAsOptionalBoolean(COLUMNNAME_IsExcludedFromPurchase).orElse(false));
		tableRow.getAsOptionalString(COLUMNNAME_ExclusionFromPurchaseReason).ifPresent(bPartnerProductRecord::setExclusionFromPurchaseReason);
		tableRow.getAsOptionalString(COLUMNNAME_ProductNo).ifPresent(bPartnerProductRecord::setProductNo);
		tableRow.getAsOptionalString(COLUMNNAME_UPC).ifPresent(bPartnerProductRecord::setUPC);

		bPartnerProductRecord.setIsCurrentVendor(tableRow.getAsOptionalBoolean(COLUMNNAME_IsCurrentVendor).orElse(true));
		tableRow.getAsOptionalString(COLUMNNAME_GTIN).ifPresent(bPartnerProductRecord::setGTIN);
		tableRow.getAsOptionalString(COLUMNNAME_EAN_CU).ifPresent(bPartnerProductRecord::setEAN_CU);

		saveRecord(bPartnerProductRecord);

		tableRow.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_Product_ID)
				.ifPresent(i -> bpartnerProductTable.putOrReplace(i, ProductRepository.fromRecord(bPartnerProductRecord)));
	}
}