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
import de.metas.util.Check;
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

import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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
		final List<Map<String, String>> productTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : productTableList)
		{
			createBPartnerProduct(dataTableRow);
		}
	}

	private void locateBPartnerProductByProductAndBPartner(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_Product productRecord = productTable.get(productIdentifier);
		final I_C_BPartner bPartnerRecord = bPartnerTable.get(bpartnerIdentifier);

		final BPartnerProduct bPartnerProduct = productRepository.getByIdOrNull(ProductId.ofRepoId(productRecord.getM_Product_ID()), BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID()));
		assertThat(bPartnerProduct).isNotNull();

		final String bpartnerProductIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		bpartnerProductTable.put(bpartnerProductIdentifier, bPartnerProduct);
	}

	private void verifyBPartnerProductInfo(@NonNull final Map<String, String> row)
	{
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, I_C_BPartner_Product.COLUMNNAME_IsActive);
		final Integer seqNo = DataTableUtil.extractIntegerOrNullForColumnName(row, I_C_BPartner_Product.COLUMNNAME_SeqNo);
		final String productNo = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_ProductNo);
		final String description = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_Description);
		final String ean = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_EAN_CU);
		final String gtin = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_GTIN);
		final String customerLabelName = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_CustomerLabelName);
		final String ingredients = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_Ingredients);
		final boolean isExcludedFromSale = DataTableUtil.extractBooleanForColumnName(row, I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale);
		final String exclusionFromSaleReason = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BPartner_Product.COLUMNNAME_ExclusionFromSaleReason);
		final boolean isExcludedFromPurchase = DataTableUtil.extractBooleanForColumnName(row, I_C_BPartner_Product.COLUMNNAME_IsExcludedFromPurchase);
		final String exclusionFromPurchaseReason = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BPartner_Product.COLUMNNAME_ExclusionFromPurchaseReason);

		final String bpartnerProductIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

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

	private void createBPartnerProduct(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerProductIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer bPartnerId = bPartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final I_C_BPartner_Product bPartnerProductRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);

		bPartnerProductRecord.setM_Product_ID(productId);
		bPartnerProductRecord.setC_BPartner_ID(bPartnerId);

		final String gtin = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_GTIN);

		if (Check.isNotBlank(gtin))
		{
			bPartnerProductRecord.setGTIN(gtin);
		}

		final String eanCu = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_EAN_CU);

		if (Check.isNotBlank(eanCu))
		{
			bPartnerProductRecord.setEAN_CU(eanCu);
		}

		bPartnerProductRecord.setShelfLifeMinDays(0);
		bPartnerProductRecord.setShelfLifeMinPct(0);

		saveRecord(bPartnerProductRecord);

		bpartnerProductTable.put(bPartnerProductIdentifier, ProductRepository.ofBPartnerProductRecord(bPartnerProductRecord));
	}
}