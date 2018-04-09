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
import org.adempiere.util.Check;
import org.adempiere.util.Services;
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
import de.metas.pricing.ProductPrices;
import de.metas.product.IProductPlanningSchemaBL;

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
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_Product importRecord) throws Exception
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final int I_Product_ID = importRecord.getI_Product_ID();
		int M_Product_ID = importRecord.getM_Product_ID();
		final boolean newProduct = M_Product_ID <= 0;
		log.debug("I_Product_ID=" + I_Product_ID + ", M_Product_ID=" + M_Product_ID);

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
		// M_ProductPO
		createUpdateProductPO(importRecord, newProduct);

		//
		// Price List
		createUpdateProductPrice(importRecord);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

		// #3404 Create default product planning
		Services.get(IProductPlanningSchemaBL.class).createDefaultProductPlanningsForAllProducts();
		return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private final void createUpdateProductPO(final I_I_Product imp, final boolean newProduct)
	{
		// Do we have Product PO Info
		final int C_BPartner_ID = imp.getC_BPartner_ID();
		if (C_BPartner_ID <= 0)
		{
			return;
		}

		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final int M_Product_ID = imp.getM_Product_ID();
		final int I_Product_ID = imp.getI_Product_ID();

		int updateCount_ProductPO = 0;
		// If Product existed, Try to Update first
		if (!newProduct)
		{
			final String sqlt = DB.convertSqlToNative("UPDATE M_Product_PO "
					+ "SET (IsCurrentVendor,C_UOM_ID,C_Currency_ID,UPC,"
					+ "PriceList,PricePO,RoyaltyAmt,PriceEffective,"
					+ "VendorProductNo,VendorCategory,Manufacturer,"
					+ "Discontinued,DiscontinuedBy,Order_Min,Order_Pack,"
					+ "CostPerOrder,DeliveryTime_Promised,Updated,UpdatedBy)= "
					+ "(SELECT CAST('Y' AS CHAR),C_UOM_ID,C_Currency_ID,UPC,"    // jz fix EDB unknown datatype error
					+ "PriceList,PricePO,RoyaltyAmt,PriceEffective,"
					+ "VendorProductNo,VendorCategory,Manufacturer,"
					+ "Discontinued,DiscontinuedBy,Order_Min,Order_Pack,"
					+ "CostPerOrder,DeliveryTime_Promised,now(),UpdatedBy"
					+ " FROM I_Product"
					+ " WHERE I_Product_ID=" + I_Product_ID + ") "
					+ "WHERE M_Product_ID=" + M_Product_ID + " AND C_BPartner_ID=" + C_BPartner_ID);
			PreparedStatement pstmt_updateProductPO = null;
			try
			{
				pstmt_updateProductPO = DB.prepareStatement(sqlt, trxName);
				updateCount_ProductPO = pstmt_updateProductPO.executeUpdate();
				log.trace("Update Product_PO = " + updateCount_ProductPO);
			}
			catch (final SQLException ex)
			{
				throw new DBException("Update Product_PO: " + ex.toString(), ex);
			}
			finally
			{
				DB.close(pstmt_updateProductPO);
			}
			// noUpdatePO++;
		}
		if (updateCount_ProductPO == 0)		// Insert PO
		{
			PreparedStatement pstmt_insertProductPO = null;
			try
			{
				pstmt_insertProductPO = DB.prepareStatement("INSERT INTO M_Product_PO (M_Product_ID,C_BPartner_ID, "
						+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
						+ "IsCurrentVendor,C_UOM_ID,C_Currency_ID,UPC,"
						+ "PriceList,PricePO,RoyaltyAmt,PriceEffective,"
						+ "VendorProductNo,VendorCategory,Manufacturer,"
						+ "Discontinued,DiscontinuedBy,Order_Min,Order_Pack,"
						+ "CostPerOrder,DeliveryTime_Promised) "
						+ "SELECT ?,?, "
						+ "AD_Client_ID,AD_Org_ID,'Y',now(),CreatedBy,now(),UpdatedBy,"
						+ "'Y',C_UOM_ID,C_Currency_ID,UPC,"
						+ "PriceList,PricePO,RoyaltyAmt,PriceEffective,"
						+ "VendorProductNo,VendorCategory,Manufacturer,"
						+ "Discontinued,DiscontinuedBy,Order_Min,Order_Pack,"
						+ "CostPerOrder,DeliveryTime_Promised "
						+ "FROM I_Product "
						+ "WHERE I_Product_ID=?", trxName);
				pstmt_insertProductPO.setInt(1, M_Product_ID);
				pstmt_insertProductPO.setInt(2, C_BPartner_ID);
				pstmt_insertProductPO.setInt(3, I_Product_ID);
				updateCount_ProductPO = pstmt_insertProductPO.executeUpdate();
				log.trace("Insert Product_PO = " + updateCount_ProductPO);
			}
			catch (final SQLException ex)
			{
				throw new DBException("Insert Product_PO: " + ex.toString(), ex);
			}
			finally
			{
				DB.close(pstmt_insertProductPO);
			}
			// noInsertPO++;
		}
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
		final int productId = imp.getM_Product_ID();
		Check.assume(productId > 0, "M_Product shall be set in {}", imp);

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
				.ofNullable(
						ProductPrices.retrieveMainProductPriceOrNull(plv, productId))
				.orElseGet(() -> InterfaceWrapperHelper.create(getCtx(), I_M_ProductPrice.class, ITrx.TRXNAME_ThreadInherited));
		pp.setM_PriceList_Version(plv);	// FK
		pp.setM_Product_ID(productId); // FK
		pp.setPriceLimit(PriceLimit);
		pp.setPriceList(PriceList);
		pp.setPriceStd(PriceStd);
		pp.setC_TaxCategory_ID(taxCategoryId);
		pp.setC_UOM_ID(uomId);
		InterfaceWrapperHelper.save(pp);
	}
}
