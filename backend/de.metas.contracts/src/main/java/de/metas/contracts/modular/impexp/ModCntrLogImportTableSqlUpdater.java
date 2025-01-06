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
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_BPartnerValue;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Bill_BPartner_Value;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_Calendar_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_Currency_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_C_UOM_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_CalendarName;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_CollectionPointValue;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_CollectionPoint_BPartner_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_DateTrx;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_DocumentNo;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_FiscalYear;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_Harvesting_Year_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ISO_Code;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_I_ErrorMsg;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_I_IsImported;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_IsActive;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_IsSOTrx;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_M_Product_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_M_Warehouse_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroupName;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType;
import static de.metas.contracts.model.I_I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID;
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

		dbUpdateCalendar(selection);
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

		dbUpdateContractModule(selection);
		dbUpdateContract(selection);

		// update error message
		dbUpdateErrorMessage(selection);
	}

	private void dbUpdateContract(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlValidContractIdsForModCntrSettings = "SELECT c." + COLUMNNAME_C_Flatrate_Term_ID
				+ " FROM " + I_C_Flatrate_Term.Table_Name + " t "
				+ " INNER JOIN " + I_C_Flatrate_Conditions.Table_Name + " fc ON t." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + " = fc." + I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID
				+ " INNER JOIN " + I_ModCntr_Settings.Table_Name + " s ON fc." + I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID + " = s." + I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID
				+ " INNER JOIN " + I_ModCntr_Module.Table_Name + " m ON s." + I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID + " = s." + I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID
				+ " WHERE i." + COLUMNNAME_ModCntr_Module_ID + " = m." + I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID;

		final String sqlContractIdForBp = "SELECT c." + COLUMNNAME_C_Flatrate_Term_ID
				+ " FROM " + I_C_Flatrate_Term.Table_Name + " c"
				+ " WHERE c." + I_C_Flatrate_Term.COLUMNNAME_DocumentNo + " = i." + COLUMNNAME_DocumentNo
				+ " AND c." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND c." + COLUMNNAME_C_Flatrate_Term_ID + " IN (" + sqlValidContractIdsForModCntrSettings + ")"
				+ " LIMIT 1 ";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_Flatrate_Term_ID + " = (" + sqlContractIdForBp + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_C_Flatrate_Term_ID + " IS NULL"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_DocumentNo + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateContractModule(@NonNull final ImportRecordsSelection selection)
	{
		//keep in sync with unique index: modcntr_settings_calender_year_product_issotrx_unique_idx
		final String sqlContractModuleId = "SELECT m." + I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID
				+ " FROM " + I_ModCntr_Module.Table_Name + " m"
				+ " INNER JOIN " + I_ModCntr_Type.Table_Name + " t on m." + I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID + " = t." + I_ModCntr_Type.COLUMNNAME_ModCntr_Type_ID
				+ " AND t." + I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType + " = '" + ComputingMethodType.IMPORT_LOG_DEPRECATED + "'"
				+ " INNER JOIN " + I_ModCntr_Settings.Table_Name + " s ON m." + I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID + " = s." + I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID
				+ " WHERE i." + COLUMNNAME_M_Product_ID + " = m." + I_ModCntr_Module.COLUMNNAME_M_Product_ID
				+ " AND i." + COLUMNNAME_Harvesting_Year_ID + " = s." + I_ModCntr_Settings.COLUMNNAME_C_Year_ID
				+ " AND i." + COLUMNNAME_C_Calendar_ID + " = s." + I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID
				+ " AND i." + COLUMNNAME_IsSOTrx + " = s." + I_ModCntr_Settings.COLUMNNAME_IsSOTrx
				+ " AND m." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
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
				+ " AND y." + I_C_Year.COLUMNNAME_C_Calendar_ID + " = i." + I_I_ModCntr_Log.COLUMNNAME_C_Calendar_ID
				+ " AND y." + I_C_Year.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND y." + I_C_Year.COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY y." + I_C_Year.COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Harvesting_Year_ID + " = (" + sqlYearId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_Harvesting_Year_ID + " IS NULL"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_FiscalYear + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCalendar(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlCalendarId = "SELECT c." + I_C_Calendar.COLUMNNAME_C_Calendar_ID
				+ " FROM " + I_C_Calendar.Table_Name + " c"
				+ " WHERE c." + I_C_Calendar.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_ModCntr_Log.COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND c." + I_C_Calendar.COLUMNNAME_IsActive + "='Y'"
				+ " AND (c." + I_C_Calendar.COLUMNNAME_Name + "= i." + COLUMNNAME_CalendarName + " OR c." + I_C_Calendar.COLUMNNAME_IsDefault + "='Y')"
				+ " ORDER BY " + I_C_Calendar.COLUMNNAME_IsDefault
				+ " LIMIT 1";

		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_Calendar_ID + " = (" + sqlCalendarId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_Harvesting_Year_ID + " IS NULL"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_FiscalYear + ")) > 0 "
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_WarehouseName + ")) > 0 "
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_ProductValue + ")) > 0 "
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_ModCntr_InvoicingGroupName + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessage(@NonNull final ImportRecordsSelection selection)
	{
		// DocumentType = ImportLog
		updateNonImportTypedLogs(selection);
		updateAlreadyExistingLogs(selection);

		// DateTrx
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_DateTrx)
								   .errorMessage(COLUMNNAME_DateTrx + " is Mandatory !")
								   .build());
		// C_Calendar_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_C_Calendar_ID)
								   .errorMessage(COLUMNNAME_C_Calendar_ID + " No Calendar Match!")
								   .build());

		// Harvesting_Year_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_Harvesting_Year_ID)
								   .linkColumnName(COLUMNNAME_FiscalYear)
								   .errorMessage(COLUMNNAME_Harvesting_Year_ID + " No Harvesting Year Match!")
								   .build());

		// ModCnr_Module_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_ModCntr_Module_ID)
								   .errorMessage(COLUMNNAME_ModCntr_Module_ID + " is Mandatory!")
								   .build());

		// C_Flatrate_Term_ID
		updateErrorMessage(DBUpdateErrorMessageRequest.builder()
								   .selection(selection)
								   .mandatoryColumnName(COLUMNNAME_C_Flatrate_Term_ID)
								   .linkColumnName(COLUMNNAME_DocumentNo)
								   .errorMessage(COLUMNNAME_C_Flatrate_Term_ID + " No C_Flatrate_Term Match!")
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

	private static void updateAlreadyExistingLogs(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i " +
				" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Existing modular contract logs shall not be re-imported.'" +
				" WHERE i." + COLUMNNAME_ModCntr_Log_ID + " IS NOT NULL " +
				" AND i." + COLUMNNAME_I_IsImported + "<>'Y'" +
				selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void updateNonImportTypedLogs(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i " +
				" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Only records with " + LogEntryDocumentType.IMPORT_LOG.getCode() + " will be imported!'" +
				" WHERE i." + COLUMNNAME_ModCntr_Log_DocumentType + " != '" + LogEntryDocumentType.IMPORT_LOG.getCode() + "'" +
				" AND i." + COLUMNNAME_I_IsImported + "<>'Y'" +
				selection.toSqlWhereClause("i");
		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_ModCntr_Log_DocumentType + ")) > 0 "
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_ISO_Code + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCollectionPoint(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_CollectionPoint_BPartner_ID + "=(SELECT C_BPartner_ID FROM C_BPartner c"
				+ " WHERE c.isActive='Y' AND " + COLUMNNAME_CollectionPointValue + "=c.Value) "
				+ " WHERE " + COLUMNNAME_CollectionPoint_BPartner_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_CollectionPointValue + ")) > 0 "
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
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_BPartnerValue + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateBillPartner(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Bill_BPartner_ID + "=(SELECT c.C_BPartner_ID FROM C_BPartner c"
				+ " WHERE c.isActive='Y' AND " + COLUMNNAME_Bill_BPartner_Value + "=c.Value) "
				+ " WHERE " + COLUMNNAME_Bill_BPartner_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_Bill_BPartner_Value + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePriceUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_Price_UOM_ID + "=(SELECT C_UOM_ID FROM C_UOM c"
				+ " WHERE c.isActive='Y' AND i." + COLUMNNAME_PriceUOM + "=c.uomsymbol) "
				+ " WHERE " + COLUMNNAME_Price_UOM_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_PriceUOM + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + targetTableName + " i "
				+ " SET " + COLUMNNAME_C_UOM_ID + "=(SELECT C_UOM_ID FROM C_UOM c"
				+ " WHERE c.isActive='Y' AND i." + COLUMNNAME_UOMSymbol + "=c.uomsymbol) "
				+ " WHERE " + COLUMNNAME_C_UOM_ID + " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'"
				+ " AND LENGTH(TRIM( i." + COLUMNNAME_UOMSymbol + ")) > 0 "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	public int countRecordsWithErrors(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "SELECT COUNT(1) FROM " + targetTableName + " WHERE I_IsImported='E' " + selection.toSqlWhereClause();
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
	}

	private void updateErrorMessage(@NonNull final DBUpdateErrorMessageRequest request)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + targetTableName);
		sql.append(" SET ").append(COLUMNNAME_I_IsImported).append(" = 'E', ").append(COLUMNNAME_I_ErrorMsg).append(" = ").append(COLUMNNAME_I_ErrorMsg).append("||'ERR = ").append(request.errorMessage()).append(", '");
		sql.append(" WHERE ").append(COLUMNNAME_I_IsImported).append(" != 'E' AND ").append(request.mandatoryColumnName()).append(" IS NULL ");

		if (Strings.isNotBlank(request.linkColumnName()))
		{
			sql.append(" AND ").append(request.linkColumnName()).append(" IS NOT NULL ");
		}

		sql.append(request.selection().toSqlWhereClause());

		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("Updated {} records having a null {}", no, request.mandatoryColumnName());
		}
	}

	@Builder
	record DBUpdateErrorMessageRequest(
			@NonNull ImportRecordsSelection selection,
			@NonNull String mandatoryColumnName,
			@Nullable String linkColumnName,
			@NonNull String errorMessage
	)
	{
	}

}
