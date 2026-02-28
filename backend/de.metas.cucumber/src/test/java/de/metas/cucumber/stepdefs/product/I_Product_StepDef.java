/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.product;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.pricing.M_PriceList_Version_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_ProductPrice_StepDefData;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportRecordsRequest;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for I_Product (product import staging table)
 * and ProductImportProcess execution.
 */
@RequiredArgsConstructor
public class I_Product_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_PriceList_Version_StepDefData priceListVersionTable;
	@NonNull private final M_ProductPrice_StepDefData productPriceTable;
	@NonNull private final I_Product_StepDefData importProductTable;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);

	/**
	 * Inserts records into the I_Product import staging table.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns Identifier (required), Value (required), Name (required),
	 * M_Product_ID (optional, identifier ref to existing product),
	 * M_PriceList_Version_ID (required, identifier ref),
	 * C_TaxCategory_ID.InternalName (required),
	 * C_UOM_ID.X12DE355 (required), PriceStd (required),
	 * PriceList (optional), PriceLimit (optional),
	 * InvoicableQtyBasedOn (optional, CatchWeight or Nominal),
	 * ProductType (optional, default Item)
	 */
	@And("metasfresh contains I_Product:")
	public void metasfresh_contains_i_product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createI_Product);
	}

	private void createI_Product(@NonNull final DataTableRow row)
	{
		final I_I_Product importRecord = InterfaceWrapperHelper.newInstance(I_I_Product.class);
		importRecord.setAD_Org_ID(ORG_ID);

		importRecord.setValue(row.getAsString(I_I_Product.COLUMNNAME_Value));
		importRecord.setName(row.getAsString(I_I_Product.COLUMNNAME_Name));

		// Link to existing product if specified
		row.getAsOptionalIdentifier(I_I_Product.COLUMNNAME_M_Product_ID)
				.ifPresent(productIdentifier -> {
					final I_M_Product product = productTable.get(productIdentifier);
					importRecord.setM_Product_ID(product.getM_Product_ID());
				});

		// Price list version
		final StepDefDataIdentifier plvIdentifier = row.getAsIdentifier(I_I_Product.COLUMNNAME_M_PriceList_Version_ID);
		final I_M_PriceList_Version plv = priceListVersionTable.get(plvIdentifier);
		importRecord.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());

		// Tax category
		final String taxCategoryInternalName = row.getAsString("C_TaxCategory_ID.InternalName");
		final TaxCategoryId taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName)
				.orElseThrow(() -> new RuntimeException("No tax category found for internalName: " + taxCategoryInternalName));
		importRecord.setC_TaxCategory_ID(taxCategoryId.getRepoId());

		// UOM
		final UomId uomId = uomDAO.getUomIdByX12DE355(row.getAsUOMCode("C_UOM_ID"));
		importRecord.setC_UOM_ID(uomId.getRepoId());

		// Prices
		importRecord.setPriceStd(row.getAsBigDecimal(I_I_Product.COLUMNNAME_PriceStd));
		row.getAsOptionalBigDecimal(I_I_Product.COLUMNNAME_PriceList).ifPresent(importRecord::setPriceList);
		row.getAsOptionalBigDecimal(I_I_Product.COLUMNNAME_PriceLimit).ifPresent(importRecord::setPriceLimit);

		// InvoicableQtyBasedOn
		row.getAsOptionalString(I_I_Product.COLUMNNAME_InvoicableQtyBasedOn)
				.ifPresent(importRecord::setInvoicableQtyBasedOn);

		// ProductType (defaults to Item in the process, but set it explicitly)
		row.getAsOptionalString(I_I_Product.COLUMNNAME_ProductType)
				.ifPresent(importRecord::setProductType);

		// Product category
		row.getAsOptionalString(I_I_Product.COLUMNNAME_ProductCategory_Value)
				.ifPresent(importRecord::setProductCategory_Value);

		saveRecord(importRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(id -> importProductTable.putOrReplace(id, importRecord));
	}

	/**
	 * Runs the ProductImportProcess on all unprocessed I_Product records.
	 */
	@When("the ProductImportProcess is run")
	public void run_product_import_process()
	{
		importProcessFactory.newImportProcessForTableName(I_I_Product.Table_Name)
				.validateAndImport(ImportRecordsRequest.builder()
						.importTableName(I_I_Product.Table_Name)
						.completeDocuments(false)
						.build());
	}

	/**
	 * Verifies that M_ProductPrice records were created/updated with the expected InvoicableQtyBasedOn value.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns Identifier (optional), M_Product_ID (required, identifier ref),
	 * M_PriceList_Version_ID (required, identifier ref),
	 * InvoicableQtyBasedOn (required, CatchWeight or Nominal),
	 * PriceStd (optional)
	 */
	@Then("M_ProductPrice is found:")
	public void m_productprice_is_found(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyM_ProductPrice);
	}

	private void verifyM_ProductPrice(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_Product_ID);
		final I_M_Product product = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final StepDefDataIdentifier plvIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID);
		final I_M_PriceList_Version plv = priceListVersionTable.get(plvIdentifier);
		final PriceListVersionId plvId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());

		final I_M_ProductPrice productPrice = queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plvId)
				.create()
				.firstOnlyNotNull(I_M_ProductPrice.class);

		// Verify InvoicableQtyBasedOn
		final String expectedInvoicableQtyBasedOn = row.getAsString(I_M_ProductPrice.COLUMNNAME_InvoicableQtyBasedOn);
		assertThat(productPrice.getInvoicableQtyBasedOn())
				.as("M_ProductPrice.InvoicableQtyBasedOn for product %s", productIdentifier)
				.isEqualTo(expectedInvoicableQtyBasedOn);

		// Verify PriceStd if specified
		row.getAsOptionalBigDecimal(I_M_ProductPrice.COLUMNNAME_PriceStd)
				.ifPresent(expectedPriceStd -> assertThat(productPrice.getPriceStd())
						.as("M_ProductPrice.PriceStd for product %s", productIdentifier)
						.isEqualByComparingTo(expectedPriceStd));

		// Store in StepDefData
		row.getAsOptionalIdentifier()
				.ifPresent(id -> productPriceTable.putOrReplace(id, productPrice));
	}
}
