package org.adempiere.impexp.product;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * A helper class for {@link ProductImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_Product} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class MProductImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(MProductImportTableSqlUpdater.class);

	private String targetTableName = I_I_Product.Table_Name;
	private String valueColumnName = I_I_Product.COLUMNNAME_Value;

	final private String priceName_KAEP = "KAEP";
	final private String priceName_APU = "APU";
	final private String priceName_AEP = "AEP";
	final private String priceName_AVP = "AVP";
	final private String priceName_UVP = "UVP";
	final private String priceName_ZBV = "ZBV";

	@Builder(buildMethodName = "updateIProduct")
	private void updateMProductImportTable(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		dbUpdateBPartners(whereClause);

		dbUpdateProducts(whereClause);

		dbUpdateProductCategories(whereClause, ctx);

		dbUpdatePharmaProductCategory(whereClause);

		dbUpdateIProductFromProduct(whereClause);

		dbUpdateIProductFromProductPO(whereClause);

		dbUpdateUOM(whereClause);

		dbUpdatePackageUOM(whereClause);

		dbUpdateCurrency(whereClause);

		dbUpdateVendorProductNo(whereClause);

		dbUpdateTaxCategories(whereClause, ctx);

		dbUpdatePriceListVersion(whereClause, ctx);

		dbUpdatePriceLists(whereClause, ctx, priceName_APU);

		dbUpdatePriceLists(whereClause, ctx, priceName_AEP);

		dbUpdateDosageForm(whereClause);

		dbUpdateIndication(whereClause);

		dbUpdateErrorMessages(whereClause);
	}

	@Builder(buildMethodName = "updateIPharmaProduct")
	private void updatePharmaProductImportTable(@NonNull final String whereClause, @NonNull final Properties ctx, @NonNull final String tableName, @NonNull final String valueName)
	{
		targetTableName = tableName;
		valueColumnName = valueName;

		dbUpdateProductsByValue(whereClause);
		dbUpdateProductCategoryForIFAProduct(whereClause);

		dbUpdatePackageUOM(whereClause);

		dbUpdateDosageForm(whereClause);

		dbUpdatePriceLists(whereClause, ctx, priceName_KAEP);
		dbUpdatePriceLists(whereClause, ctx, priceName_APU);
		dbUpdatePriceLists(whereClause, ctx, priceName_AEP);
		dbUpdatePriceLists(whereClause, ctx, priceName_AVP);
		dbUpdatePriceLists(whereClause, ctx, priceName_UVP);
		dbUpdatePriceLists(whereClause, ctx, priceName_ZBV);
	}

	private void dbUpdateBPartners(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid BPartner,' ")
				.append("WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid BPartner=" + no);
	}

	private void dbUpdateProducts(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing UPC=" + no);

		dbUpdateProductsByValue(whereClause);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product_po p")
				.append(" WHERE i.C_BPartner_ID=p.C_BPartner_ID")
				.append(" AND i.VendorProductNo=p.VendorProductNo AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing Vendor ProductNo=" + no);
	}

	private void dbUpdateProductsByValue(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE i.")
				.append(valueColumnName)
				.append("=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		final int no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing Value=" + no);
	}

	private void dbUpdateProductCategories(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		int no;
		final int adClientId = Env.getAD_Client_ID(ctx);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET ProductCategory_Value=(SELECT MAX(Value) FROM M_Product_Category")
				.append(" WHERE IsDefault='Y' AND AD_Client_ID=").append(adClientId).append(") ")
				.append("WHERE ProductCategory_Value IS NULL AND M_Product_Category_ID IS NULL")
				.append(" AND M_Product_ID IS NULL")	// set category only if product not found
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Category Default Value=" + no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c")
				.append(" WHERE i.ProductCategory_Value=c.Value AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE ProductCategory_Value IS NOT NULL AND M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Category=" + no);
	}

	private void dbUpdateIProductFromProduct(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		// Copy From Product if Import does not have value
		final String[] strFields = new String[] { "Value", "Name", "Description", "DocumentNote", "Help",
				"UPC", "SKU", "Classification", "ProductType",
				"Discontinued", "DiscontinuedBy", "ImageURL", "DescriptionURL" };
		for (final String strField : strFields)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(strField).append(" = (SELECT ").append(strField).append(" FROM M_Product p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL"
							+ " AND ")
					.append(strField).append(" IS NULL"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug(strField + " - default from existing Product=" + no);
			}
		}
		final String[] numFields = new String[] { "C_UOM_ID", "M_Product_Category_ID",
				"Volume", "Weight", "ShelfWidth", "ShelfHeight", "ShelfDepth", "UnitsPerPallet" };
		for (final String numField : numFields)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(numField).append(" = (SELECT ").append(numField).append(" FROM M_Product p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL"
							+ " AND (")
					.append(numField).append(" IS NULL OR ").append(numField).append("=0)"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug(numField + " default from existing Product=" + no);
			}
		}
	}

	private void dbUpdateIProductFromProductPO(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		// Copy From Product_PO if Import does not have value
		final String[] strFieldsPO = new String[] { "UPC",
				"PriceEffective", "VendorProductNo", "VendorCategory", "Manufacturer",
				"Discontinued", "DiscontinuedBy" };
		for (final String element : strFieldsPO)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(element).append(" = (SELECT ").append(element)
					.append(" FROM M_Product_PO p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.C_BPartner_ID=p.C_BPartner_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL AND C_BPartner_ID IS NOT NULL"
							+ " AND ")
					.append(element).append(" IS NULL"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			logger.debug(element + " default from existing Product PO=" + no);
		}

		final String[] numFieldsPO = new String[] { "C_UOM_ID", "C_Currency_ID",
				"PriceList", "PricePO", "RoyaltyAmt",
				"Order_Min", "Order_Pack", "CostPerOrder", "DeliveryTime_Promised" };

		for (final String element : numFieldsPO)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(element).append(" = (SELECT ").append(element)
					.append(" FROM M_Product_PO p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.C_BPartner_ID=p.C_BPartner_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL AND C_BPartner_ID IS NOT NULL"
							+ " AND (")
					.append(element).append(" IS NULL OR ").append(element).append("=0)"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			logger.debug(element + " default from existing Product PO=" + no);
		}
	}

	private void dbUpdateUOM(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		// Set UOM (System/own)
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET X12DE355 = ")
				.append("(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,i.AD_Client_ID)) ")
				.append("WHERE X12DE355 IS NULL AND C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set UOM Default=" + no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=i.X12DE355 AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) ")
				.append("WHERE C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set UOM=" + no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM, ' ")
				.append("WHERE C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid UOM=" + no);
	}

	private void dbUpdatePackageUOM(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET Package_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=i.A00PGEINH AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) ")
				.append("WHERE Package_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Package_UOM =" + no);
	}

	private void dbUpdateCurrency(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET ISO_Code=(SELECT ISO_Code FROM C_Currency c")
				.append(" INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)")
				.append(" INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)")
				.append(" WHERE ci.AD_Client_ID=i.AD_Client_ID) ")
				.append("WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Currency Default=" + no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c")
				.append(" WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) ")
				.append("WHERE C_Currency_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("doIt- Set Currency=" + no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Currency,' ")
				.append("WHERE C_Currency_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid Currency=" + no);
	}

	private void dbUpdateVendorProductNo(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET VendorProductNo=Value ")
				.append("WHERE C_BPartner_ID IS NOT NULL AND VendorProductNo IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("VendorProductNo Set to Value=" + no);
	}

	private void dbUpdateTaxCategories(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		final int adClientId = Env.getAD_Client_ID(ctx);
		// Resolve C_TaxCategory_Name -> C_TaxCategory_ID
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i ")
					.append(" set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.Name=i.C_TaxCategory_Name and tc.AD_Client_ID=")
					.append(adClientId)
					.append(" and tc.IsActive='Y' order by tc.C_TaxCategory_ID limit 1)")
					.append(" where true")
					.append(" and ").append(COLUMNNAME_I_IsImported).append("<>'Y'")
					.append(" and i.C_TaxCategory_Name is not null")
					.append(whereClause);
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		// Set default C_TaxCategory_ID where it was not set
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i ")
					.append(" set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.IsDefault='Y' and tc.AD_Client_ID=")
					.append(adClientId)
					.append(" and tc.IsActive='Y' order by tc.C_TaxCategory_ID limit 1)")
					.append(" where true")
					.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
					.append(" and i.C_TaxCategory_ID is null")
					.append(whereClause);
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}
	}

	private void dbUpdatePriceListVersion(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" set M_PriceList_Version_ID=(select plv.M_PriceList_Version_ID from M_PriceList_Version plv ")
				.append(" where plv.Name=i.M_PriceList_Version_Name and plv.AD_Client_ID=")
				.append(adClientId)
				.append(" and plv.IsActive='Y' order by plv.M_PriceList_Version_ID limit 1)")
				.append(" where true")
				.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" and i.M_PriceList_Version_Name is not null")
				.append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateDosageForm(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_DosageForm_ID =(SELECT M_DosageForm_ID FROM M_DosageForm d")
				.append(" WHERE d.AD_Client_ID=i.AD_Client_ID AND i.A00DARFO = d.Name) ")
				.append("WHERE M_DosageForm_ID IS NULL AND A00DARFO IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePharmaProductCategory(@NonNull final String whereClause)
	{

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_PharmaProductCategory_ID=(SELECT M_PharmaProductCategory_ID FROM M_PharmaProductCategory c")
				.append(" WHERE i.PharmaProductCategory_Value=c.Name AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE PharmaProductCategory_Value IS NOT NULL AND M_PharmaProductCategory_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProductCategoryForIFAProduct(@NonNull final String whereClause)
	{

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c")
				.append(" WHERE i.A00WGA=c.Value AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE A00WGA IS NOT NULL AND M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}


	private void dbUpdateIndication(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Indication_ID =(SELECT M_Indication_ID FROM M_Indication ind")
				.append(" WHERE ind.AD_Client_ID=i.AD_Client_ID AND i.M_Indication_Name = ind.Name) ")
				.append("WHERE M_Indication_ID IS NULL AND M_Indication_Name IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePriceLists(@NonNull final String whereClause, @NonNull final Properties ctx, @NonNull final String nameToMatch)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i set ")
				.append(nameToMatch)
				.append("_Price_List_ID=(select pl.M_PriceList_ID from M_PriceList pl ")
				.append(" where pl.InternalName=?")
				.append(" and pl.AD_Client_ID=?")
				.append(" and pl.IsActive='Y' order by pl.M_PriceList_ID limit 1)")
				.append(" where true")
				.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(whereClause);
		final Object[] params = new Object[]{nameToMatch, adClientId};
		DB.executeUpdateEx(sql.toString(), params, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessages(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProdCategory,' ")
				.append("WHERE M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid Category=" + no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProductType,' ")
				.append("WHERE ProductType NOT IN ('E','I','R','S')")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid ProductType=" + no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND Value IN (SELECT Value FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY Value HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique Value=" + no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=UPC not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND UPC IN (SELECT UPC FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY UPC HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique UPC=" + no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No Mandatory Value,' ")
				.append("WHERE Value IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("No Mandatory Value=" + no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=VendorProductNo not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND C_BPartner_ID IS NOT NULL")
				.append(" AND (C_BPartner_ID, VendorProductNo) IN ")
				.append(" (SELECT C_BPartner_ID, VendorProductNo FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY C_BPartner_ID, VendorProductNo HAVING COUNT(*) > 1)")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique VendorProductNo=" + no);
	}
}
