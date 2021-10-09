/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.product.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Import {@link I_I_Product} to {@link I_M_Product}.
 *
 * @author tsa
 */
public class ProductImportProcess extends SimpleImportProcessTemplate<I_I_Product>
{
	private static final Logger log = LogManager.getLogger(ProductImportProcess.class);

	private static final String PARAM_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	@Override
	public Class<I_I_Product> getImportModelClass()
	{
		return I_I_Product.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Product.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_M_Product.Table_Name;
	}

	@Override
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.<String, Object> builder()
				.put(I_I_Product.COLUMNNAME_ProductType, X_I_Product.PRODUCTTYPE_Item)
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MProductImportTableSqlUpdater.builder()
				.selection(selection)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Product.COLUMNNAME_Value)
				.updateIProduct();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Product.COLUMNNAME_ProductCategory_Value;
	}

	@Override
	protected I_I_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> state_NOTUSED,
			final @NonNull I_I_Product importRecord,
			final boolean isInsertOnly)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final int I_Product_ID = importRecord.getI_Product_ID();
		int M_Product_ID = importRecord.getM_Product_ID();
		final boolean newProduct = M_Product_ID <= 0;
		log.debug("I_Product_ID=" + I_Product_ID + ", M_Product_ID=" + M_Product_ID);

		if (!newProduct && isInsertOnly)
		{
			// #4994 do not update
			return ImportRecordResult.Nothing;
		}
		// Product
		if (newProduct)			// Insert new Product
		{
			final I_M_Product product = createMProduct(importRecord);
			if (Check.isEmpty(product.getName()))
			{
				product.setName(product.getValue());
			}
			InterfaceWrapperHelper.save(product);
			M_Product_ID = product.getM_Product_ID();
			importRecord.setM_Product_ID(M_Product_ID);
			log.trace("Insert Product");
		}
		else
		// Update Product
		{
			final String sqlt = DB.convertSqlToNative("UPDATE M_PRODUCT "
					+ "SET (Value,Name,Description,DocumentNote,Help,"
					+ "Package_UOM_ID, PackageSize, IsSold, IsStocked, "
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,NetWeight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,Updated,UpdatedBy, "
					+ "RawMaterialOrigin_ID, M_CustomsTariff_ID"
					+ ", " + I_I_Product.COLUMNNAME_M_ProductPlanningSchema_Selector // #3406
					+ ")= "
					+ "(SELECT Value,coalesce(I_Product.Name, I_Product.Value),Description,DocumentNote,Help,"
					+ "Package_UOM_ID, PackageSize, IsSold, IsStocked, "
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,NetWeight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,now(),UpdatedBy, "
					+ "RawMaterialOrigin_ID, M_CustomsTariff_ID"
					+ ", " + I_M_Product.COLUMNNAME_M_ProductPlanningSchema_Selector // #3406
					+ " FROM I_Product WHERE I_Product_ID=" + I_Product_ID + ") "
					+ "WHERE M_Product_ID=" + M_Product_ID);
			PreparedStatement pstmt_updateProduct = null;
			try
			{
				pstmt_updateProduct = DB.prepareStatement(sqlt, trxName);
				final int no = pstmt_updateProduct.executeUpdate();
				log.trace("Update Product = " + no);
			}
			catch (final SQLException ex)
			{
				throw new DBException("Update Product: " + ex.toString(), ex);
			}
			finally
			{
				DB.close(pstmt_updateProduct);
			}
		}

		//
		// Price List
		createUpdateProductPrice(importRecord);

		final I_M_Product productRecord = load(importRecord.getM_Product_ID(), I_M_Product.class);
		ModelValidationEngine.get().fireImportValidate(this, importRecord, productRecord, IImportInterceptor.TIMING_AFTER_IMPORT);

		// #3404 Create default product planning
		Services.get(IProductPlanningSchemaBL.class).createDefaultProductPlanningsForAllProducts();
		return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private final void createUpdateProductPrice(final I_I_Product imp)
	{
		//
		// Get M_PriceList_Version_ID
		int priceListVersionId = imp.getM_PriceList_Version_ID();
		if (priceListVersionId <= 0)
		{
			priceListVersionId = getParameters().getParameterAsInt(PARAM_M_PriceList_Version_ID, -1);
		}
		if (priceListVersionId <= 0)
		{
			return;
		}

		//
		// Get C_TaxCategory
		final int taxCategoryId = imp.getC_TaxCategory_ID();
		if (taxCategoryId <= 0)
		{
			return;
		}

		//
		// Get UOM
		final int uomId = imp.getC_UOM_ID();
		if (uomId <= 0)
		{
			return;
		}

		//
		// Get M_Product_ID
		final ProductId productId = ProductId.ofRepoIdOrNull(imp.getM_Product_ID());
		Check.assumeNotNull(productId, "M_Product shall be set in {}", imp);

		//
		// Get prices
		final BigDecimal PriceList = imp.getPriceList();
		final BigDecimal PriceStd = imp.getPriceStd();
		final BigDecimal PriceLimit = imp.getPriceLimit();
		if (PriceStd.signum() == 0 && PriceLimit.signum() == 0 && PriceList.signum() == 0)
		{
			return;
		}

		//
		// Get/Create Product Price record
		final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(getCtx(), priceListVersionId, I_M_PriceList_Version.class, ITrx.TRXNAME_ThreadInherited);
		final I_M_ProductPrice pp = Optional
				.ofNullable(ProductPrices.retrieveMainProductPriceOrNull(plv, productId))
				.orElseGet(() -> InterfaceWrapperHelper.create(getCtx(), I_M_ProductPrice.class, ITrx.TRXNAME_ThreadInherited));
		pp.setM_PriceList_Version_ID(priceListVersionId);	// FK
		pp.setM_Product_ID(productId.getRepoId()); // FK
		pp.setPriceLimit(PriceLimit);
		pp.setPriceList(PriceList);
		pp.setPriceStd(PriceStd);
		pp.setC_TaxCategory_ID(taxCategoryId);
		pp.setC_UOM_ID(uomId);
		InterfaceWrapperHelper.save(pp);
	}

	private I_M_Product createMProduct(@NonNull final I_I_Product importRecord)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		product.setValue(importRecord.getValue());
		product.setName(importRecord.getName());
		product.setDescription(importRecord.getDescription());
		product.setDocumentNote(importRecord.getDocumentNote());
		product.setHelp(importRecord.getHelp());
		product.setUPC(importRecord.getUPC());
		product.setExternalId(importRecord.getExternalId());
		product.setSKU(importRecord.getSKU());
		product.setC_UOM_ID(importRecord.getC_UOM_ID());
		product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		product.setPackageSize(importRecord.getPackageSize());
		product.setManufacturer_ID(importRecord.getManufacturer_ID());
		product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());
		product.setProductType(importRecord.getProductType());
		product.setImageURL(importRecord.getImageURL());
		product.setDescriptionURL(importRecord.getDescriptionURL());
		product.setIsSold(importRecord.isSold());
		product.setIsStocked(importRecord.isStocked());
		product.setNetWeight(importRecord.getNetWeight());
		product.setM_CustomsTariff_ID(importRecord.getM_CustomsTariff_ID());
		product.setRawMaterialOrigin_ID(importRecord.getRawMaterialOrigin_ID());
		product.setWeight(importRecord.getWeight());
		product.setM_ProductPlanningSchema_Selector(importRecord.getM_ProductPlanningSchema_Selector()); // #3406

		return product;
	}	// MProduct
}
