package org.adempiere.impexp.product;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
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

	public void updateBPartnerImtortTable(@NonNull final String whereClause)
	{
		dbUpdateBPartners(whereClause);

		dbUpdateProducts(whereClause);

		dbUpdateProductCategories(whereClause);

		dbUpdateErrorMessages(whereClause);
	}

	private void dbUpdateBPartners(final String whereClause)
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

	private void dbUpdateProducts(final String whereClause)
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

	private void dbUpdateProductCategories(final String whereClause)
	{
		StringBuilder sql;
		int no;
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());

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



	private void dbUpdateErrorMessages(final String whereClause)
	{

		StringBuilder sql;
		sql = new StringBuilder("UPDATE I_Product "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value is mandatory, ' "
				+ "WHERE Value IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}
}
