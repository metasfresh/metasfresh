/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.rules.campaign_price.impexp;

import de.metas.impexp.processing.ImportRecordsSelection;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.util.DB;

import java.util.Properties;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

@UtilityClass
public class CampaignPriceImportTableSqlUpdater
{
	private final static String targetTableName = I_I_Campaign_Price.Table_Name;

	@Builder(buildMethodName = "updateCampaignPrice")
	private void updateCampaignPrice(@NonNull final ImportRecordsSelection selection, @NonNull final Properties ctx)
	{
		dbUpdateProduct(selection);
		dbUpdateBPartner(selection);
		dbUpdateBpGroup(selection);
		dbUpdatePricingSystem(selection);
		dbUpdateCountry(selection);
		dbUpdateCurrency(selection);
		dbUpdateUom(selection);
		dbUpdateTaxCategory(selection);
		dbUpdateCampaignPrice(selection, ctx);
	}

	private static void dbUpdateProduct(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE p.isActive='Y' AND i.ProductValue=p.Value AND p.AD_Client_ID in (i.AD_Client_ID,0) AND p.AD_Org_ID IN (0, i.AD_Org_ID) ORDER BY p.AD_Client_ID DESC, p.AD_Org_ID DESC LIMIT 1) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Product,' ")
				.append("WHERE M_Product_ID IS NULL AND ProductValue IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateBPartner(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE p.isActive='Y' AND i.BPartner_Value=p.Value AND p.AD_Client_ID in (i.AD_Client_ID, 0) AND p.AD_Org_ID IN (0, i.AD_Org_ID) ORDER BY p.AD_Client_ID DESC, p.AD_Org_ID DESC LIMIT 1) ")
				.append("WHERE C_BPartner_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateBpGroup(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_BP_Group_ID=(SELECT C_BP_Group_ID FROM C_BP_Group g")
				.append(" WHERE g.isActive='Y' AND i.GroupValue=g.Value AND g.AD_Client_ID in (i.AD_Client_ID, 0) ORDER BY g.AD_Client_ID DESC LIMIT 1) ")
				.append("WHERE C_BP_Group_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdatePricingSystem(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_Pricingsystem ps")
				.append(" WHERE ps.isActive='Y' AND i.PricingSystem_Value=ps.Value AND ps.AD_Client_ID in (i.AD_Client_ID, 0) ORDER BY ps.AD_Client_ID DESC LIMIT 1) ")
				.append("WHERE M_PricingSystem_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateCountry(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c")
				.append(" WHERE i.CountryCode=c.CountryCode) ")
				.append("WHERE C_Country_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Country,' ")
				.append("WHERE C_Country_ID IS NULL AND CountryCode IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateCurrency(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c")
				.append(" WHERE c.isActive='Y' AND i.ISO_Code=c.ISO_Code) ")
				.append("WHERE C_Currency_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Currency,' ")
				.append("WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateUom(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_UOM_ID=(SELECT C_UOM_ID FROM C_UOM u")
				.append(" WHERE i.UOM=u.name AND u.AD_Client_ID in (i.AD_Client_ID, 0) LIMIT 1) ")
				.append("WHERE C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM,' ")
				.append("WHERE C_UOM_ID IS NULL AND UOM IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateTaxCategory(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_TaxCategory_ID=(SELECT C_TaxCategory_ID FROM C_TaxCategory tc")
				.append(" WHERE tc.isActive='Y' AND i.C_TaxCategory_Name=tc.Name AND tc.AD_Client_ID in (i.AD_Client_ID, 0) ORDER BY tc.AD_Client_ID DESC LIMIT 1) ")
				.append("WHERE C_TaxCategory_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid TaxCategory,' ")
				.append("WHERE C_TaxCategory_ID IS NULL AND C_TaxCategory_Name IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateCampaignPrice(final ImportRecordsSelection selection, final Properties ctx)
	{
		final String sql = "UPDATE "
				+ targetTableName + " i "
				+ " SET C_Campaign_Price_ID=(SELECT C_Campaign_Price_ID FROM C_Campaign_Price c"
				+ " WHERE i.M_Product_ID=c.M_Product_ID "
				+ " AND (i.C_BPartner_ID=c.C_BPartner_ID  OR (i.C_BPartner_ID IS NULL AND c.C_BPartner_ID IS NULL) ) "
				+ " AND (i.C_BP_Group_ID=c.C_BP_Group_ID OR (i.C_BP_Group_ID IS NULL AND c.C_BP_Group_ID IS NULL ) ) "
				+ " AND (i.M_PricingSystem_ID=c.M_PricingSystem_ID OR i.M_PricingSystem_ID IS NULL and c.M_PricingSystem_ID IS NULL) "
				+ " AND i.C_Country_ID=c.C_Country_ID "
				+ " AND i.C_UOM_ID=c.C_UOM_ID "
				+ " AND i.C_TaxCategory_ID=c.C_TaxCategory_ID "
				+ " AND i.AD_Org_ID=c.AD_Org_ID "
				+ " AND i.AD_Client_ID=c.AD_Client_ID "
				+ " AND i.C_Country_ID=c.C_Country_ID "
				+ " AND i.validFrom = c.validFrom ORDER BY updated DESC LIMIT 1) "
				+ "WHERE C_Campaign_Price_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}
}
