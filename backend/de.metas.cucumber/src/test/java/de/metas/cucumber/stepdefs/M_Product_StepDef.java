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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_Product_ID;

public class M_Product_StepDef
{
	public static final ProductCategoryId PRODUCT_CATEGORY_ID = ProductCategoryId.ofRepoId(1000000);

	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_M_Product_Category> productCategoryTable;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_Product_StepDef(
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_M_Product_Category> productCategoryTable)
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
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

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
		final String productValue = CoalesceUtil.coalesce(tableRow.get("Value"), productName);
		final Boolean isStocked = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_M_Product.COLUMNNAME_IsStocked, true);

		final String productType = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_M_Product.COLUMNNAME_ProductType);

		final I_M_Product productRecord = CoalesceUtil.coalesceSuppliers(
				() -> productDAO.retrieveProductByValue(productValue),
				() -> newInstanceOutOfTrx(I_M_Product.class));
		productRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		productRecord.setValue(productValue);
		productRecord.setName(productName);

		final String uomSymbol = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_UOM.COLUMNNAME_UOMSymbol);
		if (Check.isNotBlank(uomSymbol))
		{
			final UomId uomId = queryBL.createQueryBuilder(I_C_UOM.class)
					.addEqualsFilter(I_C_UOM.COLUMNNAME_UOMSymbol, uomSymbol)
					.create()
					.firstIdOnly(UomId::ofRepoId);

			productRecord.setC_UOM_ID(UomId.toRepoId(uomId));
		}
		else
		{
			productRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		}

		productRecord.setProductType(CoalesceUtil.coalesceNotNull(productType, ProductType.Item.getCode()));
		productRecord.setIsStocked(isStocked);

		final String productCategoryIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productCategoryIdentifier))
		{
			final I_M_Product_Category productCategory = productCategoryTable.get(productCategoryIdentifier);
			productCategory.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		}
		else
		{
			productRecord.setM_Product_Category_ID(PRODUCT_CATEGORY_ID.getRepoId());
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Product.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			productRecord.setDescription(description);
		}

		InterfaceWrapperHelper.saveRecord(productRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "M_Product");
		productTable.putOrReplace(recordIdentifier, productRecord);
	}
}
