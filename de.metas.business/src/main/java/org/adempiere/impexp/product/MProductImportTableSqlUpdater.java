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

	@Builder(buildMethodName = "updateIProduct")
	private void updateMProductImportTable(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		dbUpdateBPartners(whereClause);

		dbUpdateProducts(whereClause);

		dbUpdateProductCategories(whereClause, ctx);

		dbUpdateIProductFromProduct(whereClause);

		dbUpdateIProductFromProductPO(whereClause);

		dbUpdateUOM(whereClause);

		dbUpdatePackageUOM(whereClause);

		dbUpdateCurrency(whereClause);

		dbUpdateVendorProductNo(whereClause);

		dbUpdateTaxCategories(whereClause, ctx);

		dbUpdatePriceListVersion(whereClause, ctx);

		dbDosageForm(whereClause);

		dbIndication(whereClause);

		dbUpdateErrorMessages(whereClause);
	}

	private void dbUpdateBPartners(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid BPartner,' "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid BPartner=" + no);
	}

	private void dbUpdateProducts(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing UPC=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing Value=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product_po p"
				+ " WHERE i.C_BPartner_ID=p.C_BPartner_ID"
				+ " AND i.VendorProductNo=p.VendorProductNo AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing Vendor ProductNo=" + no);
	}

	private void dbUpdateProductCategories(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		int no;
		final int adClientId = Env.getAD_Client_ID(ctx);

		sql = new StringBuilder("UPDATE I_Product "
				+ "SET ProductCategory_Value=(SELECT MAX(Value) FROM M_Product_Category"
				+ " WHERE IsDefault='Y' AND AD_Client_ID=").append(adClientId).append(") "
						+ "WHERE ProductCategory_Value IS NULL AND M_Product_Category_ID IS NULL"
						+ " AND M_Product_ID IS NULL"	// set category only if product not found
						+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Category Default Value=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c"
				+ " WHERE i.ProductCategory_Value=c.Value AND i.AD_Client_ID=c.AD_Client_ID) "
				+ "WHERE ProductCategory_Value IS NOT NULL AND M_Product_Category_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
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
		for (int i = 0; i < strFields.length; i++)
		{
			sql = new StringBuilder("UPDATE I_Product i "
					+ "SET ").append(strFields[i]).append(" = (SELECT ").append(strFields[i]).append(" FROM M_Product p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL"
							+ " AND ").append(strFields[i]).append(" IS NULL"
									+ " AND " + COLUMNNAME_I_IsImported + "='N'")
							.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug(strFields[i] + " - default from existing Product=" + no);
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
									+ " AND " + COLUMNNAME_I_IsImported + "='N'")
							.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug(numFields[i] + " default from existing Product=" + no);
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
		for (int i = 0; i < strFieldsPO.length; i++)
		{
			sql = new StringBuilder("UPDATE I_PRODUCT i "
					+ "SET ").append(strFieldsPO[i]).append(" = (SELECT ").append(strFieldsPO[i])
							.append(" FROM M_Product_PO p"
									+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.C_BPartner_ID=p.C_BPartner_ID AND i.AD_Client_ID=p.AD_Client_ID) "
									+ "WHERE M_Product_ID IS NOT NULL AND C_BPartner_ID IS NOT NULL"
									+ " AND ")
							.append(strFieldsPO[i]).append(" IS NULL"
									+ " AND " + COLUMNNAME_I_IsImported + "='N'")
							.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			logger.debug(strFieldsPO[i] + " default from existing Product PO=" + no);
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
									+ " AND (")
							.append(numFieldsPO[i]).append(" IS NULL OR ").append(numFieldsPO[i]).append("=0)"
									+ " AND " + COLUMNNAME_I_IsImported + "='N'")
							.append(whereClause);
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			logger.debug(numFieldsPO[i] + " default from existing Product PO=" + no);
		}
	}

	private void dbUpdateUOM(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		// Set UOM (System/own)
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET X12DE355 = "
				+ "(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE X12DE355 IS NULL AND C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set UOM Default=" + no);
		//
		sql = new StringBuilder(
				"UPDATE I_Product i "
						+ "SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=i.X12DE355 AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) "
						+ "WHERE C_UOM_ID IS NULL"
						+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set UOM=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM, ' "
				+ "WHERE C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid UOM=" + no);
	}

	private void dbUpdatePackageUOM(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		// Set UOM (System/own)
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET Package_UOM_Name = "
				+ "(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE Package_UOM_Name IS NULL AND Package_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set UOM Default=" + no);
		//
		sql = new StringBuilder(
				"UPDATE I_Product i "
						+ "SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=i.Package_UOM_Name AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) "
						+ "WHERE Package_UOM_ID IS NULL"
						+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Package_UOM =" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Package_UOM , ' "
				+ "WHERE Package_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid UOM=" + no);
	}

	private void dbUpdateCurrency(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET ISO_Code=(SELECT ISO_Code FROM C_Currency c"
				+ " INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)"
				+ " INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)"
				+ " WHERE ci.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Currency Default=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("doIt- Set Currency=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Currency,' "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid Currency=" + no);
	}

	private void dbUpdateVendorProductNo(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_Product "
				+ "SET VendorProductNo=Value "
				+ "WHERE C_BPartner_ID IS NOT NULL AND VendorProductNo IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("VendorProductNo Set to Value=" + no);
	}

	private void dbUpdateTaxCategories(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		final int adClientId = Env.getAD_Client_ID(ctx);
		// Resolve C_TaxCategory_Name -> C_TaxCategory_ID
		{
			sql = new StringBuilder("UPDATE I_Product i set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.Name=i.C_TaxCategory_Name and tc.AD_Client_ID=")
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
			sql = new StringBuilder("UPDATE I_Product i set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc where tc.IsDefault='Y' and tc.AD_Client_ID=")
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
		final StringBuilder sql = new StringBuilder("UPDATE I_Product i set M_PriceList_Version_ID=(select plv.M_PriceList_Version_ID from M_PriceList_Version plv ")
				.append(" where plv.Name=i.M_PriceList_Version_Name and plv.AD_Client_ID=")
				.append(adClientId)
				.append(" and plv.IsActive='Y' order by plv.M_PriceList_Version_ID limit 1)")
				.append(" where true")
				.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" and i.M_PriceList_Version_Name is not null")
				.append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbDosageForm(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_DosageForm_ID =(SELECT Name FROM M_DosageForm d"
				+ " WHERE d.AD_Client_ID=i.AD_Client_ID AND i.M_DosageForm_Name = d.Name) "
				+ "WHERE M_DosageForm_ID IS NULL AND M_DosageForm_Name IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbIndication(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_Product i "
				+ "SET M_Indication_ID =(SELECT Name FROM M_Indication ind"
				+ " WHERE ind.AD_Client_ID=i.AD_Client_ID AND i.M_Indication_Name = ind.Name) "
				+ "WHERE M_Indication_ID IS NULL AND M_Indication_Name IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessages(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProdCategory,' "
				+ "WHERE M_Product_Category_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid Category=" + no);

		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProductType,' "
				+ "WHERE ProductType NOT IN ('E','I','R','S')"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Invalid ProductType=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND Value IN (SELECT Value FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY Value HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique Value=" + no);
		//
		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=UPC not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND UPC IN (SELECT UPC FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY UPC HAVING COUNT(*) > 1)").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique UPC=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No Mandatory Value,' "
				+ "WHERE Value IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("No Mandatory Value=" + no);

		sql = new StringBuilder("UPDATE I_Product i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=VendorProductNo not unique,' "
				+ "WHERE " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND C_BPartner_ID IS NOT NULL"
				+ " AND (C_BPartner_ID, VendorProductNo) IN "
				+ " (SELECT C_BPartner_ID, VendorProductNo FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY C_BPartner_ID, VendorProductNo HAVING COUNT(*) > 1)")
						.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.warn("Not Unique VendorProductNo=" + no);

	}
}
