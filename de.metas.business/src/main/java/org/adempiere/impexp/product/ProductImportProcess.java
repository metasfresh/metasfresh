package org.adempiere.impexp.product;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Import {@link I_I_Product} to {@link I_M_Product}.
 *
 * @author tsa
 */
public class ProductImportProcess extends AbstractImportProcess<I_I_Product>
{
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
		final String whereClause = getWhereClause();
		MProductImportTableSqlUpdater.builder()
				.whereClause(whereClause)
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
	protected ImportRecordResult importRecord(final IMutable<Object> state,
			final I_I_Product importRecord,
			final boolean isInsertOnly) throws Exception
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
			final MProduct product = new MProduct(importRecord);
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
					+ "Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,Updated,UpdatedBy"
					+ ", " + I_I_Product.COLUMNNAME_M_ProductPlanningSchema_Selector // #3406
					+ ")= "
					+ "(SELECT Value,coalesce(I_Product.Name, I_Product.Value),Description,DocumentNote,Help,"
					+ "Package_UOM_ID, PackageSize, IsSold, IsStocked, "
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,now(),UpdatedBy"
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

		ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

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
			priceListVersionId = getParameters().getParameterAsInt(PARAM_M_PriceList_Version_ID);
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
		pp.setM_PriceList_Version(plv);	// FK
		pp.setM_Product_ID(productId.getRepoId()); // FK
		pp.setPriceLimit(PriceLimit);
		pp.setPriceList(PriceList);
		pp.setPriceStd(PriceStd);
		pp.setC_TaxCategory_ID(taxCategoryId);
		pp.setC_UOM_ID(uomId);
		InterfaceWrapperHelper.save(pp);
	}
}
