/**
 * 
 */
package de.metas.contracts.flatrate.impexp;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_I_Flatrate_Term;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.contracts
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
 * cg
 *
 */
@UtilityClass
public class FlatrateTermImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(FlatrateTermImportTableSqlUpdater.class);

	public void updateFlatrateTermImportTable(@NonNull final String sqlImportWhereClause)
	{
		dbUpdateFlatrateTerm(sqlImportWhereClause);
		validateUpdatedFlaterateTerm(sqlImportWhereClause);
	}
	
	private void dbUpdateFlatrateTerm(@NonNull final String sqlImportWhereClause)
	{
		dbUpdateBPartnerIds(sqlImportWhereClause);
		dbUpdateDropshipPartnerIds(sqlImportWhereClause);
		dbUpdateC_Flatrate_Conditions_IDs(sqlImportWhereClause);
		dbUpdateProductIdsByValue(sqlImportWhereClause);
		dbUpdateProductIdsByName(sqlImportWhereClause);
	}
	
	private void validateUpdatedFlaterateTerm(@NonNull final String sqlImportWhereClause)
	{
		markAsError("BPartner not found", I_I_Flatrate_Term.COLUMNNAME_C_BPartner_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
		markAsError("DropShip BPartner not found",
				I_I_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID + " IS NULL"
						+ "\n AND " + I_I_Flatrate_Term.COLUMNNAME_DropShip_BPartner_Value + " IS NOT NULL" // only where the we have a key to match
						+ "\n AND " + sqlImportWhereClause);
		markAsError("Flatrate conditions not found", I_I_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
		markAsError("Product not found", I_I_Flatrate_Term.COLUMNNAME_M_Product_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private void dbUpdateBPartnerIds(final String sqlImportWhereClause)
	{
		final String sqlSelectByValue = "select MIN(bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + ")"
				+ " from " + I_C_BPartner.Table_Name + " bp "
				+ " where bp." + I_C_BPartner.COLUMNNAME_Value + "=i." + I_I_Flatrate_Term.COLUMNNAME_BPartnerValue
				+ " and bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + "=i." + I_I_Flatrate_Term.COLUMNNAME_AD_Client_ID;
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name + " i "
				+ "\n SET " + I_I_Flatrate_Term.COLUMNNAME_C_BPartner_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Flatrate_Term.COLUMNNAME_C_BPartner_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set C_BPartner_ID for {} records", no);
	}

	private void dbUpdateDropshipPartnerIds(final String sqlImportWhereClause)
	{
		final String sqlSelectByValue = "select MIN(bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + ")"
				+ " from " + I_C_BPartner.Table_Name + " bp "
				+ " where bp." + I_C_BPartner.COLUMNNAME_Value + "=i." + I_I_Flatrate_Term.COLUMNNAME_DropShip_BPartner_Value
				+ " and bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + "=i." + I_I_Flatrate_Term.COLUMNNAME_AD_Client_ID;
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name + " i "
				+ "\n SET " + I_I_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set DropShip_BPartner_ID for {} records", no);
	}

	private void dbUpdateC_Flatrate_Conditions_IDs(final String sqlImportWhereClause)
	{
		final String sqlSelectByValue = "select MIN(fc." + I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID + ")"
				+ " from " + I_C_Flatrate_Conditions.Table_Name + " fc "
				+ " where fc." + I_C_Flatrate_Conditions.COLUMNNAME_Name + "=i." + I_I_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_Value
				+ " and fc." + I_C_Flatrate_Conditions.COLUMNNAME_AD_Client_ID + "=i." + I_I_Flatrate_Term.COLUMNNAME_AD_Client_ID;
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name + " i "
				+ "\n SET " + I_I_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set C_Flatrate_Conditions_ID for {} records", no);
	}

	private void dbUpdateProductIdsByValue(final String sqlImportWhereClause)
	{
		final String sqlSelectByValue = "select MIN(bp." + I_M_Product.COLUMNNAME_M_Product_ID + ")"
				+ " from " + I_M_Product.Table_Name + " bp "
				+ " where bp." + I_M_Product.COLUMNNAME_Value + "=i." + I_I_Flatrate_Term.COLUMNNAME_ProductValue
				+ " and bp." + I_M_Product.COLUMNNAME_AD_Client_ID + "=i." + I_I_Flatrate_Term.COLUMNNAME_AD_Client_ID;
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name + " i "
				+ "\n SET " + I_I_Flatrate_Term.COLUMNNAME_M_Product_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Flatrate_Term.COLUMNNAME_M_Product_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set M_Product_ID for {} records (by Value)", no);
	}

	private void dbUpdateProductIdsByName(final String sqlImportWhereClause)
	{
		final String sqlSelectByValue = "select MIN(bp." + I_M_Product.COLUMNNAME_M_Product_ID + ")"
				+ " from " + I_M_Product.Table_Name + " bp "
				+ " where bp." + I_M_Product.COLUMNNAME_Name + "=i." + I_I_Flatrate_Term.COLUMNNAME_ProductValue
				+ " and bp." + I_M_Product.COLUMNNAME_AD_Client_ID + "=i." + I_I_Flatrate_Term.COLUMNNAME_AD_Client_ID;
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name + " i "
				+ "\n SET " + I_I_Flatrate_Term.COLUMNNAME_M_Product_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Flatrate_Term.COLUMNNAME_M_Product_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set M_Product_ID for {} records (by Name)", no);
	}

	private final void markAsError(final String errorMsg, final String sqlWhereClause)
	{
		final String sql = "UPDATE " + I_I_Flatrate_Term.Table_Name
				+ "\n SET " + COLUMNNAME_I_IsImported + "=?, " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||? "
				+ "\n WHERE " + sqlWhereClause;
		final Object[] sqlParams = new Object[] { X_I_Flatrate_Term.I_ISIMPORTED_ImportFailed, errorMsg + "; " };
		DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_ThreadInherited);

	}
}
