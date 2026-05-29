package de.metas.mforecast.impexp;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import de.metas.product.impexp.ProductImportProcess;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

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
 */
public class MForecastImportTableSqlUpdater
{
	private static final Logger logger = LogManager.getLogger(MForecastImportTableSqlUpdater.class);

	private final ImportRecordsSelection selection;
	private final String targetTableName;

	@Builder
	private MForecastImportTableSqlUpdater(
			@NonNull final ImportRecordsSelection selection,
			@NonNull final String tableName)
	{
		this.selection = selection;
		this.targetTableName = tableName;
	}

	public void updateIForecast()
	{
		dbUpdateBPartners(selection);

		dbUpdateProducts(selection);

		dbUpdateUOM(selection);

		dbUpdatePriceLists(selection);

		dbUpdateWarehouses(selection);

		dbUpdateProjects(selection);

		dbUpdateActivities(selection);

		dbUpdateCampaigns(selection);
	}

	private void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE i.BPValue=p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE C_BPartner_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Partners Existing Value={}", no);

		//  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Partner,' "
				+ "WHERE C_BPartner_ID IS NULL AND  i.BPValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProducts(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.ProductValue = p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Product Existing Value={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Product,' "
				+ "WHERE M_Product_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET C_UOM_ID =(SELECT C_UOM_ID FROM C_UOM u"
				+ " WHERE i.UOM = u.X12DE355 AND u.AD_Client_ID IN (0,i.AD_Client_ID) and u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) "
				+ "WHERE C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		logger.info("Set UOM={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM,' "
				+ "WHERE C_UOM_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePriceLists(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE "
				+ targetTableName + " i set "
				+ "M_PriceList_ID=(select pl.M_PriceList_ID FROM M_PriceList pl "
				+ " WHERE (pl.InternalName=i.PriceList or pl.Name=i.PriceList) "
				+ " AND pl.AD_Org_ID = i.AD_Org_ID"
				+ " AND pl.IsActive='Y' order by pl.M_PriceList_ID limit 1)"
				+ "WHERE M_PriceList_ID IS NULL AND PriceList IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Price List ={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Price List,' "
				+ "WHERE M_PriceList_ID IS NULL AND PriceList IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateWarehouses(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET M_Warehouse_ID =(SELECT M_Warehouse_ID FROM M_Warehouse p"
				+ " WHERE i.WarehouseValue = p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE M_Warehouse_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Warehouse Existing Value={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Warehouse,' "
				+ "WHERE M_Warehouse_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProjects(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET C_Project_ID =(SELECT C_Project_ID FROM C_Project p"
				+ " WHERE i.ProjectValue = p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Projects Existing Value={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Project,' "
				+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateActivities(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET C_Activity_ID =(SELECT C_Activity_ID FROM C_Activity p"
				+ " WHERE i.ActivityValue = p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE C_Activity_ID IS NULL AND ActivityValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Activities Existing Value={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Activity,' "
				+ "WHERE C_Activity_ID IS NULL AND ActivityValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCampaigns(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET C_Campaign_ID =(SELECT C_Campaign_ID FROM C_Campaign p"
				+ " WHERE i.CampaignValue = p.Value AND i.AD_Org_ID=p.AD_Org_ID) "
				+ "WHERE C_Campaign_ID IS NULL AND CampaignValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + " <> 'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.info("Campaigns Existing Value={}", no);

		///  set error message if needed
		final String errorSql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Campaign,' "
				+ "WHERE C_Campaign_ID IS NULL AND CampaignValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(errorSql, ITrx.TRXNAME_ThreadInherited);
	}
}
