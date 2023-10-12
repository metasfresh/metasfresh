/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.impexp;

import com.jgoodies.common.base.Strings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.modular.invgroup.InvoicingGroupProductId;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_ModCntr_InvoicingGroup;
import org.compiere.util.DB;
import org.slf4j.Logger;

import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_BPartnerValue;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Bill_BPartner_Value;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_Currency_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_UOM_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_CollectionPointValue;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_CollectionPoint_BPartner_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ContractModuleName;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_DocumentNo;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_FiscalYear;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Harvesting_Year_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ISO_Code;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_I_ErrorMsg;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_I_IsImported;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_IsActive;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_M_Product_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_M_Warehouse_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroupName;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_PriceUOM;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Price_UOM_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Producer_BPartner_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ProductValue;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Qty;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_UOMSymbol;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_WarehouseName;

@UtilityClass
public class ModCntrLogImportTableSqlUpdater
{
	private static final Logger logger = LogManager.getLogger(ModCntrLogImportTableSqlUpdater.class);
	private final static String targetTableName = I_I_ModCntr_Log.Table_Name;

	public void updateModularImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateDocumentType(selection);
		dbUpdateContract(selection);
		dbUpdateContractModule(selection);

		dbUpdateHarvestingYear(selection);
		dbUpdateWarehouse(selection);

		dbUpdateCollectionPoint(selection);
		dbUpdateProducer(selection);
		dbUpdateBillPartner(selection);

		dbUpdateInvoicingGroup(selection);

		dbUpdateProduct(selection);
		dbUpdateUOM(selection);
		dbUpdatePriceUOM(selection);
		dbUpdateCurrency(selection);

		// update error message
		dbUpdateErrorMessage(selection);
	}

	private void dbUpdateContract(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlContractId = "SELECT c." + COLUMNNAME_C_Flatrate_Term_ID
				+ " FROM " + I_C_Flatrate_Term.Table_Name + " c"
				+ " WHERE c." + I_C_Flatrate_Term.COLUMNNAME_DocumentNo + " = i." + COLUMNNAME_DocumentNo
				+ " AND c." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " LIMIT 1 ";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_Flatrate_Term_ID + " = (" + sqlContractId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_C_Flatrate_Term_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}
	private void dbUpdateContractModule(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlContractModuleId = "SELECT c." + I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID
				+ " FROM " + I_ModCntr_Module.Table_Name + " c"
				+ " WHERE c." + I_ModCntr_Module.COLUMNNAME_Name + " = i." + COLUMNNAME_ContractModuleName
				+ " AND c." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " LIMIT 1 ";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_ModCntr_Module_ID + " = (" + sqlContractModuleId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_ModCntr_Module_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateHarvestingYear(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlYearId = "SELECT y." + I_C_Year.COLUMNNAME_C_Year_ID
				+ " FROM " + I_C_Year.Table_Name + " y"
				+ " WHERE y." + I_C_Year.COLUMNNAME_FiscalYear + " = i." + I_I_ModCntr_Log.COLUMNNAME_FiscalYear
				+ " AND y." + I_C_Year.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND y." + I_C_Year.COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY y." + I_C_Year.COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Harvesting_Year_ID + " = (" + sqlYearId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_Harvesting_Year_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateWarehouse(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlWarehouseId = "SELECT w." + I_M_Warehouse.COLUMNNAME_M_Warehouse_ID
				+ " FROM " + I_M_Warehouse.Table_Name + " w"
				+ " WHERE w." + I_M_Warehouse.COLUMNNAME_Name + " = i." + I_I_ModCntr_Log.COLUMNNAME_WarehouseName
				+ " AND w." + I_M_Warehouse.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND w." + I_M_Warehouse.COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY w." + I_M_Warehouse.COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_M_Warehouse_ID + " = (" + sqlWarehouseId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_M_Warehouse_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProduct(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlProductId = "SELECT p." + COLUMNNAME_M_Product_ID
				+ " FROM " + I_M_Product.Table_Name + " p"
				+ " WHERE p." + I_M_Product.COLUMNNAME_Value + " = i." + COLUMNNAME_ProductValue
				+ " AND p." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND p." + COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY p." + COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + I_I_ModCntr_Log.Table_Name + " i "
				+ " SET " + COLUMNNAME_M_Product_ID + " = (" + sqlProductId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_M_Product_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateInvoicingGroup(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlInvoicingGroupId = "SELECT p." + I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID
				+ " FROM " + I_ModCntr_InvoicingGroup.Table_Name + " p"
				+ " WHERE p." + I_ModCntr_InvoicingGroup.COLUMNNAME_Name + " = i." + COLUMNNAME_ModCntr_InvoicingGroupName
				+ " AND p." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND p." + COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY p." + COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + I_I_ModCntr_Log.Table_Name + " i "
				+ " SET " + COLUMNNAME_ModCntr_InvoicingGroup_ID + " = (" + sqlInvoicingGroupId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_ModCntr_InvoicingGroup_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessage(@NonNull final ImportRecordsSelection selection)
	{

		//  C_Flatrate_Term_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_C_Flatrate_Term_ID)
								   .linkColumnName(COLUMNNAME_DocumentNo)
								   .errorMessage(COLUMNNAME_C_Flatrate_Term_ID + " No C_Flatrate_Term Match!")
								   .build());
		//  ModCnr_Module_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_ModCntr_Module_ID)
								   .linkColumnName(COLUMNNAME_ContractModuleName)
								   .errorMessage(COLUMNNAME_ModCntr_Module_ID + " No Contract Module Match!")
								   .build());

		// Harvesting_Year_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_Harvesting_Year_ID)
								   .linkColumnName(COLUMNNAME_FiscalYear)
								   .errorMessage(COLUMNNAME_Harvesting_Year_ID + " No Harvesting Year Match!")
								   .build());

		// CollectionPoint_BPartner_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_CollectionPoint_BPartner_ID)
								   .linkColumnName(COLUMNNAME_CollectionPointValue)
								   .errorMessage(COLUMNNAME_CollectionPoint_BPartner_ID + " No CollectionPoint (Business Partner) Match!")
								   .build());

		// Producer_BPartner_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_Producer_BPartner_ID)
								   .linkColumnName(COLUMNNAME_BPartnerValue)
								   .errorMessage(COLUMNNAME_Producer_BPartner_ID + " No Producer (Business Partner) Match!")
								   .build());

		// Bill_BPartner_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_Bill_BPartner_ID)
								   .linkColumnName(COLUMNNAME_BPartnerValue)
								   .errorMessage(COLUMNNAME_Bill_BPartner_ID + " No Bill Partner (Business Partner) Match!")
								   .build());

		// M_Product_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_M_Product_ID)
								   .linkColumnName(COLUMNNAME_ProductValue)
								   .errorMessage(COLUMNNAME_M_Product_ID + " No Product Match!")
								   .build());

		// C_UOM_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_C_UOM_ID)
								   .linkColumnName(COLUMNNAME_UOMSymbol)
								   .errorMessage(COLUMNNAME_C_UOM_ID + " No UOM Match!")
								   .build());

		// Price_UOM_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_Price_UOM_ID)
								   .linkColumnName(COLUMNNAME_PriceUOM)
								   .errorMessage(COLUMNNAME_C_UOM_ID + " No Price UOM Match!")
								   .build());

		// C_Currency_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_C_Currency_ID)
								   .linkColumnName(COLUMNNAME_ISO_Code)
								   .errorMessage(COLUMNNAME_C_Currency_ID + " No Currency Match!")
								   .build());

		// M_Warehouse_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_M_Warehouse_ID)
								   .linkColumnName(COLUMNNAME_WarehouseName)
								   .errorMessage(COLUMNNAME_M_Warehouse_ID + " No Warehouse Match!")
								   .build());

		// Qty
		updateQtyErrormessage(selection);
	}

	private void updateQtyErrormessage(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = " + COLUMNNAME_Qty + " must be positive!" + ", '"
				+ " WHERE SIGN(i." + COLUMNNAME_Qty + ") != 1 "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateDocumentType(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = " + COLUMNNAME_ModCntr_Log_DocumentType + " must be " + LogEntryDocumentType.IMPORT_LOG.getCode() + " !" + ", '"
				+ " WHERE i." + COLUMNNAME_ModCntr_Log_DocumentType + " <> '" + LogEntryDocumentType.IMPORT_LOG.getCode() + "' "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCurrency(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_Currency_ID + "=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE c.isActive='Y' AND i." + COLUMNNAME_ISO_Code + "=c.ISO_Code) "
				+ " WHERE " + COLUMNNAME_C_Currency_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCollectionPoint(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_CollectionPoint_BPartner_ID + "=(SELECT C_BPartner_ID FROM C_BPartner c"
				+ " WHERE c.isActive='Y' AND " + COLUMNNAME_CollectionPointValue + "=c.Value) "
				+ " WHERE " + COLUMNNAME_CollectionPoint_BPartner_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProducer(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Producer_BPartner_ID + "=(SELECT c.C_BPartner_ID FROM C_BPartner c"
				+ " WHERE c.isActive='Y' AND " + COLUMNNAME_BPartnerValue + "=c.Value) "
				+ " WHERE " + COLUMNNAME_Producer_BPartner_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateBillPartner(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Bill_BPartner_ID + "=(SELECT c.C_BPartner_ID FROM C_BPartner c"
				+ " WHERE c.isActive='Y' AND " + COLUMNNAME_Bill_BPartner_Value + "=c.Value) "
				+ " WHERE " + COLUMNNAME_Bill_BPartner_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Price_UOM_ID + "=(SELECT C_UOM_ID FROM C_UOM c"
				+ " WHERE c.isActive='Y' AND i." + COLUMNNAME_PriceUOM + "=c.uomsymbol) "
				+ " WHERE " + COLUMNNAME_Price_UOM_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePriceUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_UOM_ID + "=(SELECT C_UOM_ID FROM C_UOM c"
				+ " WHERE c.isActive='Y' AND i." + COLUMNNAME_UOMSymbol + "=c.uomsymbol) "
				+ " WHERE " + COLUMNNAME_C_UOM_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	public int countRecordsWithErrors(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "SELECT COUNT(1) FROM " + targetTableName + " WHERE I_IsImported='E' " + selection.toSqlWhereClause();
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
	}

	private void updateErrorMessage(@NonNull final DBUpdateErrorMessageRequest request)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + targetTableName);
		sql.append(" SET " + COLUMNNAME_I_IsImported + " = 'E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = " + request.errorMessage() + ", '");
		sql.append(" WHERE " + request.mandatoryColumnName() + " IS NULL ");

		if (Strings.isNotBlank(request.linkColumnName()))
		{
			sql.append(" AND " + request.linkColumnName() + " IS NOT NULL ");
		}

		sql.append(request.selection().toSqlWhereClause());

		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No " + request.mandatoryColumnName() + " = {}", no);
		}
	}

}
