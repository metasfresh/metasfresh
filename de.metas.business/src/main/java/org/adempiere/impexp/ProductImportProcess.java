package org.adempiere.impexp;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MProduct;
import org.compiere.model.X_I_Product;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_M_Product;

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
		StringBuilder sql = null;
		int no = 0;
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String whereClause = getWhereClause();
		final int adClientId = getAD_Client_ID();

		// Set Optional BPartner
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("BPartner=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid BPartner,' "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Invalid BPartner=" + no);
		}

		// **** Find Product
		// EAN/UPC
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Product Existing UPC=" + no);

		// Value
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Product Existing Value=" + no);

		// BP ProdNo
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product_po p"
				+ " WHERE i.C_BPartner_ID=p.C_BPartner_ID"
				+ " AND i.VendorProductNo=p.VendorProductNo AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Product Existing Vendor ProductNo=" + no);

		// Set Product Category
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET ProductCategory_Value=(SELECT MAX(Value) FROM M_Product_Category"
				+ " WHERE IsDefault='Y' AND AD_Client_ID=").append(adClientId).append(") "
				+ "WHERE ProductCategory_Value IS NULL AND M_Product_Category_ID IS NULL"
				+ " AND M_Product_ID IS NULL"	// set category only if product not found
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Category Default Value=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c"
				+ " WHERE i.ProductCategory_Value=c.Value AND i.AD_Client_ID=c.AD_Client_ID) "
				+ "WHERE ProductCategory_Value IS NOT NULL AND M_Product_Category_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Set Category=" + no);

		// Copy From Product if Import does not have value
		final String[] strFields = new String[] { "Value", "Name", "Description", "DocumentNote", "Help",
				"UPC", "SKU", "Classification", "ProductType",
				"Discontinued", "DiscontinuedBy", "ImageURL", "DescriptionURL" };
		for (int i = 0; i < strFields.length; i++)
		{
			sql = new StringBuilder("UPDATE I_Product i "
					+ "SET ").append(strFields[i]).append(" = (SELECT ").append(strFields[i]).append(" FROM M_Product p"
					+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
					+ "WHERE M_Product_ID IS NOT NULL"
					+ " AND ").append(strFields[i]).append(" IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), trxName);
			if (no != 0)
			{
				log.debug(strFields[i] + " - default from existing Product=" + no);
			}
		}
		final String[] numFields = new String[] { "C_UOM_ID", "M_Product_Category_ID",
				"Volume", "Weight", "ShelfWidth", "ShelfHeight", "ShelfDepth", "UnitsPerPallet" };
		for (int i = 0; i < numFields.length; i++)
		{
			sql = new StringBuilder("UPDATE I_PRODUCT i "
					+ "SET ").append(numFields[i]).append(" = (SELECT ").append(numFields[i]).append(" FROM M_Product p"
					+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
					+ "WHERE M_Product_ID IS NOT NULL"
					+ " AND (").append(numFields[i]).append(" IS NULL OR ").append(numFields[i]).append("=0)"
					+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), trxName);
			if (no != 0)
			{
				log.debug(numFields[i] + " default from existing Product=" + no);
			}
		}

		// Copy From Product_PO if Import does not have value
		final String[] strFieldsPO = new String[] { "UPC",
				"PriceEffective", "VendorProductNo", "VendorCategory", "Manufacturer",
				"Discontinued", "DiscontinuedBy" };
		for (int i = 0; i < strFieldsPO.length; i++)
		{
			sql = new StringBuilder("UPDATE I_PRODUCT i "
					+ "SET ").append(strFieldsPO[i]).append(" = (SELECT ").append(strFieldsPO[i])
					.append(" FROM M_Product_PO p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.C_BPartner_ID=p.C_BPartner_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL AND C_BPartner_ID IS NOT NULL"
							+ " AND ").append(strFieldsPO[i]).append(" IS NULL"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), trxName);
			if (no != 0)
			{
				log.debug(strFieldsPO[i] + " default from existing Product PO=" + no);
			}
		}
		final String[] numFieldsPO = new String[] { "C_UOM_ID", "C_Currency_ID",
				"PriceList", "PricePO", "RoyaltyAmt",
				"Order_Min", "Order_Pack", "CostPerOrder", "DeliveryTime_Promised" };
		for (int i = 0; i < numFieldsPO.length; i++)
		{
			sql = new StringBuilder("UPDATE I_PRODUCT i "
					+ "SET ").append(numFieldsPO[i]).append(" = (SELECT ").append(numFieldsPO[i])
					.append(" FROM M_Product_PO p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.C_BPartner_ID=p.C_BPartner_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL AND C_BPartner_ID IS NOT NULL"
							+ " AND (").append(numFieldsPO[i]).append(" IS NULL OR ").append(numFieldsPO[i]).append("=0)"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), trxName);
			if (no != 0)
			{
				log.debug(numFieldsPO[i] + " default from existing Product PO=" + no);
			}
		}

		// Invalid Category
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProdCategory,' "
				+ "WHERE M_Product_Category_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Invalid Category=" + no);
		}

		// Set UOM (System/own)
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET X12DE355 = "
				+ "(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE X12DE355 IS NULL AND C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set UOM Default=" + no);
		//
		sql = new StringBuilder(
				"UPDATE I_Product i "
						+ "SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=i.X12DE355 AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) "
						+ "WHERE C_UOM_ID IS NULL"
						+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Set UOM=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM, ' "
				+ "WHERE C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Invalid UOM=" + no);
		}

		// Set Currency
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET ISO_Code=(SELECT ISO_Code FROM C_Currency c"
				+ " INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)"
				+ " INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)"
				+ " WHERE ci.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Currency Default=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("doIt- Set Currency=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Currency,' "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Invalid Currency=" + no);
		}

		// Verify ProductType
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProductType,' "
				+ "WHERE ProductType NOT IN ('E','I','R','S')"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Invalid ProductType=" + no);
		}

		// Unique UPC/Value
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND Value IN (SELECT Value FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY Value HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Not Unique Value=" + no);
		}
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=UPC not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND UPC IN (SELECT UPC FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY UPC HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Not Unique UPC=" + no);
		}

		// Mandatory Value
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No Mandatory Value,' "
				+ "WHERE Value IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("No Mandatory Value=" + no);
		}

		// Vendor Product No
		// sql = new StringBuilder ("UPDATE I_Product i "
		// + "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No Mandatory VendorProductNo,' "
		// + "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
		// + " AND VendorProductNo IS NULL AND (C_BPartner_ID IS NOT NULL OR BPartner_Value IS NOT NULL)").append(whereClause);
		// no = DB.executeUpdateEx(sql.toString(), trxName);
		// log.info(log.l3_Util, "No Mandatory VendorProductNo=" + no);
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET VendorProductNo=Value "
				+ "WHERE C_BPartner_ID IS NOT NULL AND VendorProductNo IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("VendorProductNo Set to Value=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=VendorProductNo not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND C_BPartner_ID IS NOT NULL"
				+ " AND (C_BPartner_ID, VendorProductNo) IN "
				+ " (SELECT C_BPartner_ID, VendorProductNo FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY C_BPartner_ID, VendorProductNo HAVING COUNT(*) > 1)")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Not Unique VendorProductNo=" + no);
		}

		//
		// Tax Category
		{
			// Resolve C_TaxCategory_Name -> C_TaxCategory_ID
			{
				final String sqlTC = "UPDATE I_Product i set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.Name=i.C_TaxCategory_Name and tc.AD_Client_ID=" + adClientId
						+ " and tc.IsActive='Y' order by tc.C_TaxCategory_ID limit 1)"
						+ " where true"
						+ " and " + COLUMNNAME_I_IsImported + "<>'Y'"
						+ " and i.C_TaxCategory_Name is not null"
						+ whereClause;
				DB.executeUpdateEx(sqlTC, trxName);
			}

			// Set default C_TaxCategory_ID where it was not set
			{
				final String sqlTC = "UPDATE I_Product i set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.IsDefault='Y' and tc.AD_Client_ID=" + adClientId
						+ " and tc.IsActive='Y' order by tc.C_TaxCategory_ID limit 1)"
						+ " where true"
						+ " and " + COLUMNNAME_I_IsImported + "<>'Y'"
						+ " and i.C_TaxCategory_ID is null"
						+ whereClause;
				DB.executeUpdateEx(sqlTC, trxName);
			}
		}

		//
		// Resolve M_PriceList_Version_Name -> M_PriceList_Version_ID
		{
			final String sqlPLV = "UPDATE I_Product i set M_PriceList_Version_ID=(select plv.M_PriceList_Version_ID from M_PriceList_Version plv where plv.Name=i.M_PriceList_Version_Name and plv.AD_Client_ID="
					+ adClientId + " and plv.IsActive='Y' order by plv.M_PriceList_Version_ID limit 1)"
					+ " where true"
					+ " and " + COLUMNNAME_I_IsImported + "<>'Y'"
					+ " and i.M_PriceList_Version_Name is not null"
					+ whereClause;
			DB.executeUpdateEx(sqlPLV, trxName);
		}
	}

	@Override
	protected String getImportOrderBySql()
	{
		return null; // nop
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
			// product.setC_TaxCategory_ID(C_TaxCategory_ID); // metas 05129
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
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,Updated,UpdatedBy)= "
					+ "(SELECT Value,Name,Description,DocumentNote,Help,"
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,now(),UpdatedBy"
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
		final I_M_ProductPrice pp = ProductPriceQuery.retrieveMainProductPriceIfExists(plv, productId)
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
