/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNEcleanupSS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.product;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.rest_api.v2.product.ProductRestService;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.PRODUCT_CATEGORY_STANDARD_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID;

public class M_Product_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_Category_StepDefData productCategoryTable;

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ProductRestService productRestService = SpringContextHolder.instance.getBean(ProductRestService.class);

	public M_Product_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.productTable = productTable;
		this.bpartnerTable = bpartnerTable;
		this.productCategoryTable = productCategoryTable;
	}

	@Given("metasfresh contains M_Products:")
	public void metasfresh_contains_m_product(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createM_Product(tableRow);
		}
	}

	@And("no product with value {string} exists")
	public void noProductWithCodeCodeExists(final String value)

	{
		final Optional<I_M_Product> product = Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOptional(I_M_Product.class);

		if (product.isPresent())
		{
			Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Product.class)
					.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.get().getM_Product_ID())
					.create()
					.delete();

			Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
					.create()
					.delete();
		}
	}

	@And("metasfresh contains C_BPartner_Products:")
	public void metasfreshContainsC_BPartner_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_C_BPartner_Product bPartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
			bPartnerProduct.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			bPartnerProduct.setM_Product_ID(productTable.get(productIdentifier).getM_Product_ID());
			bPartnerProduct.setC_BPartner_ID(bpartnerTable.get(bpartnerIdentifier).getC_BPartner_ID());
			bPartnerProduct.setIsCurrentVendor(true);
			bPartnerProduct.setUsedForVendor(true);
			bPartnerProduct.setUsedForCustomer(true);
			bPartnerProduct.setShelfLifeMinPct(0);
			bPartnerProduct.setShelfLifeMinDays(0);

			InterfaceWrapperHelper.saveRecord(bPartnerProduct);
		}
	}

	@And("locate product by external identifier")
	public void locate_product_by_external_identifier(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			locate_product_by_external_identifier(tableRow);
		}
	}

	@And("verify product info")
	public void verify_product_info(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			verify_product_info(tableRow);
		}
	}

	@And("taxCategory {string} is updated to work with all productTypes")
	public void update_tax_category(@NonNull final String taxCategoryInternalName)
	{
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.loadOutOfTrx(taxCategoryId.get(), I_C_TaxCategory.class);
		taxCategory.setProductType(null);

		InterfaceWrapperHelper.saveRecord(taxCategory);
	}

	private void createM_Product(@NonNull final Map<String, String> tableRow)
	{
		final String productName = tableRow.get("Name");
		final String productValue = CoalesceUtil.coalesceNotNull(tableRow.get("Value"), productName);
		final boolean isStocked = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_M_Product.COLUMNNAME_IsStocked, true);

		final String productType = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_M_Product.COLUMNNAME_ProductType);

		final I_M_Product productRecord = CoalesceUtil.coalesceSuppliers(
				() -> productDAO.retrieveProductByValue(productValue),
				() -> newInstanceOutOfTrx(I_M_Product.class));

		productRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		productRecord.setValue(productValue);
		productRecord.setName(productName);

		final String uomX12DE355 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_UOM.COLUMNNAME_X12DE355);
		if (Check.isNotBlank(uomX12DE355))
		{
			final UomId uomId = queryBL.createQueryBuilder(I_C_UOM.class)
					.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, uomX12DE355)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstIdOnly(UomId::ofRepoIdOrNull);
			assertThat(uomId).as("Found no C_UOM with X12DE355=%s", uomX12DE355).isNotNull();
			productRecord.setC_UOM_ID(UomId.toRepoId(uomId));
		}
		else
		{
			productRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		}

		productRecord.setProductType(CoalesceUtil.coalesceNotNull(productType, ProductType.Item.getCode()));
		productRecord.setIsStocked(isStocked);

		final String productCategoryIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productCategoryIdentifier))
		{
			final I_M_Product_Category productCategory = productCategoryTable.get(productCategoryIdentifier);
			assertThat(productCategory).isNotNull();
			productRecord.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		}
		else
		{
			productRecord.setM_Product_Category_ID(PRODUCT_CATEGORY_STANDARD_ID.getRepoId());
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Product.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			productRecord.setDescription(description);
		}

		final boolean isSold = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_Product.COLUMNNAME_IsSold, true);
		final boolean isPurchased = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_Product.COLUMNNAME_IsPurchased, true);

		productRecord.setIsSold(isSold);
		productRecord.setIsPurchased(isPurchased);

		InterfaceWrapperHelper.saveRecord(productRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "M_Product");
		productTable.putOrReplace(recordIdentifier, productRecord);
	}

	private void locate_product_by_external_identifier(@NonNull final Map<String, String> tableRow)
	{
		final String externalIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "externalIdentifier");

		final Optional<ProductId> productIdOptional = productRestService.resolveProductExternalIdentifier(ExternalIdentifier.of(externalIdentifier), ORG_ID);

		assertThat(productIdOptional).isPresent();

		final I_M_Product productRecord = productDAO.getById(productIdOptional.get());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		productTable.putOrReplace(productIdentifier, productRecord);
	}

	private void verify_product_info(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String value = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_Value);
		final String name = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_Name);
		final String productType = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_ProductType);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String ean = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_UPC);
		final String gtin = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_GTIN);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_Description);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, I_M_Product.COLUMNNAME_IsActive);

		final I_M_Product productRecord = productTable.get(productIdentifier);

		assertThat(productRecord.getValue()).isEqualTo(value);
		assertThat(productRecord.getName()).isEqualTo(name);
		assertThat(productRecord.getProductType()).isEqualTo(getProductType(productType));
		assertThat(productRecord.getC_UOM_ID()).isEqualTo(productUomId.getRepoId());
		assertThat(productRecord.getUPC()).isEqualTo(ean);
		assertThat(productRecord.getGTIN()).isEqualTo(gtin);
		assertThat(productRecord.getDescription()).isEqualTo(description);
		assertThat(productRecord.isActive()).isEqualTo(isActive);
	}

	@NonNull
	private String getProductType(@NonNull final String type)
	{
		final String productType;
		switch (type)
		{
			case "SERVICE":
				productType = X_M_Product.PRODUCTTYPE_Service;
				break;
			case "ITEM":
				productType = X_M_Product.PRODUCTTYPE_Item;
				break;
			default:
				throw Check.fail("Unexpected type={}", type);
		}
		return productType;
	}
}
