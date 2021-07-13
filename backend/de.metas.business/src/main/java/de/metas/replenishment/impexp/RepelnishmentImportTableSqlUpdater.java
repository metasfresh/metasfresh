package de.metas.replenishment.impexp;

import de.metas.adempiere.model.I_M_Product;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_M_Replenish;
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
 * A helper class for {@link ReplenishmentImportProcess} that updates {@link I_I_Replenish} table.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class RepelnishmentImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(RepelnishmentImportTableSqlUpdater.class);

	public void updateReplenishmentImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateOrg(selection);
		dbUpdateProducIds(selection);
		dbUpdateWarehouse(selection);
		dbUpdateLocators(selection);
		dbUpdatePeriodIds(selection);
		//
		dbUpdateReplenishments(selection);

		dbUpdateErrorMessages(selection);
	}

	private void dbUpdateProducIds(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p ")
				.append("WHERE i." + I_I_Replenish.COLUMNNAME_ProductValue)
				.append("=p." + I_M_Product.COLUMNNAME_Value)
				.append(" AND p.AD_Client_ID=i.AD_Client_ID ")
				.append(" AND p.IsActive='Y') ")
				.append("WHERE M_Product_ID IS NULL AND " + I_I_Replenish.COLUMNNAME_ProductValue + " IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Found Products={}", no);
	}

	private void dbUpdateOrg(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET AD_Org_ID=(SELECT AD_Org_ID FROM AD_org o WHERE o.value = ")
				.append(I_I_Replenish.COLUMNNAME_OrgValue)
				.append(" ) WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateWarehouse(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w WHERE w.value = ")
				.append(I_I_Replenish.COLUMNNAME_WarehouseValue)
				.append(" ) WHERE M_Warehouse_ID IS NULL AND WarehouseValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateLocators(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET M_Locator_ID=(SELECT MAX(M_Locator_ID) FROM M_Locator l ")
				.append("WHERE i.LocatorValue=l.Value AND i.M_Warehouse_ID = l.M_Warehouse_ID AND i.AD_Client_ID=l.AD_Client_ID) ")
				.append("WHERE M_Locator_ID IS NULL AND LocatorValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePeriodIds(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET C_Period_ID=(SELECT C_Period_ID FROM C_Period p ")
				.append("WHERE to_timestamp(i." +I_I_Replenish.COLUMNNAME_DateGeneral+",'dd-mm-yyyy')")
				.append(">=p." + I_C_Period.COLUMNNAME_StartDate)
				.append(" AND to_timestamp(i." +I_I_Replenish.COLUMNNAME_DateGeneral+",'dd-mm-yyyy')")
				.append("<=p." + I_C_Period.COLUMNNAME_EndDate)
				.append(" AND p.AD_Client_ID=i.AD_Client_ID ")
				.append(" AND p.IsActive='Y') ")
				.append("WHERE " + I_I_Replenish.COLUMNNAME_DateGeneral + " IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Found Products={}", no);
	}


	private void dbUpdateReplenishments(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name + " i ")
				.append("SET M_Replenish_ID=(SELECT M_Replenish_ID FROM M_Replenish r ")
				.append("WHERE i." + I_I_Replenish.COLUMNNAME_M_Product_ID)
				.append("=r." + I_M_Replenish.COLUMNNAME_M_Product_ID)
				.append(" AND i." + I_I_Replenish.COLUMNNAME_M_Warehouse_ID)
				.append("=r." + I_M_Replenish.COLUMNNAME_M_Warehouse_ID)
				.append(" AND coalesce(i." + I_I_Replenish.COLUMNNAME_M_Locator_ID)
				.append(",1)=coalesce(r." + I_M_Replenish.COLUMNNAME_M_Locator_ID+",1)")
				.append(" AND coalesce(i." + I_I_Replenish.COLUMNNAME_C_Calendar_ID)
				.append(",1)=coalesce(r." + I_M_Replenish.COLUMNNAME_C_Calendar_ID+",1)")
				.append(" AND coalesce(i." + I_I_Replenish.COLUMNNAME_C_Period_ID)
				.append(",1)=coalesce(r." + I_M_Replenish.COLUMNNAME_C_Period_ID+",1)")
				.append(" AND r.AD_Client_ID=i.AD_Client_ID ")
				.append(" AND r.IsActive='Y') ")
				.append("WHERE M_Replenish_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Found Products={}", no);
	}


	private void dbUpdateErrorMessages(final ImportRecordsSelection selection)
	{

		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Product is mandatory, ' ")
				.append("WHERE " + I_I_Replenish.COLUMNNAME_M_Product_ID + " IS NULL AND " + I_I_Replenish.COLUMNNAME_ProductValue + " IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product is mandatory={}", no);


		sql = new StringBuilder("UPDATE " + I_I_Replenish.Table_Name)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Wareouse is mandatory, ' ")
				.append("WHERE " + I_I_Replenish.COLUMNNAME_M_Warehouse_ID + " IS NULL AND " + I_I_Replenish.COLUMNNAME_WarehouseValue + " IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Warehouse is mandatory={}", no);
	}
}
