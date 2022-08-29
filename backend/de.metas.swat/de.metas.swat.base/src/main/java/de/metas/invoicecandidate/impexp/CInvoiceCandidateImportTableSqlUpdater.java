/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.impexp;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;
import org.slf4j.Logger;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_AD_Client_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_AD_Org_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_Value;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_DocBaseType;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_DocSubType;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_I_ErrorMsg;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_IsActive;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value;
import static de.metas.invoicecandidate.model.I_I_Invoice_Candidate.COLUMNNAME_OrgCode;
import static org.compiere.model.I_AD_User_NotificationGroup.COLUMNNAME_AD_User_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_Value;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_UOM.COLUMNNAME_IsDefault;
import static org.compiere.model.I_C_UOM.COLUMNNAME_X12DE355;

@UtilityClass
public class CInvoiceCandidateImportTableSqlUpdater
{
	private static final Logger logger = LogManager.getLogger(CInvoiceCandidateImportTableSqlUpdater.class);

	public void updateInvoiceCandImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateOrg(selection);
		dbUpdateProducts(selection);
		dbUpdateBPartners(selection);
		dbUpdateUOM(selection);
		dbUpdateDocBaseType(selection);
		dbUpdateInvoiceRule(selection);

		dbUpdateErrorMessages(selection);
	}

	public int countRecordsWithErrors(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "SELECT COUNT(1) FROM I_Invoice_Candidate "
				+ " WHERE I_IsImported='E' "
				+ selection.toSqlWhereClause();
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
	}

	private static void dbUpdateProducts(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlProductId = "SELECT p." + COLUMNNAME_M_Product_ID
				+ " FROM " + I_M_Product.Table_Name + " p"
				+ " WHERE p." + COLUMNNAME_Value + " = i." + COLUMNNAME_M_Product_Value
				+ " AND p." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND p." + COLUMNNAME_IsActive + "='Y'"
				+ " ORDER BY p." + COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_M_Product_ID + " = (" + sqlProductId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_M_Product_ID + " IS NULL"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlBPartnerId = "SELECT " + COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name + " bp"
				+ " WHERE bp." + COLUMNNAME_Value + " = i." + COLUMNNAME_Bill_BPartner_Value
				+ " AND bp." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " AND bp." + COLUMNNAME_IsActive + "= 'Y'"
				+ " ORDER BY bp." + COLUMNNAME_AD_Org_ID + " DESC"
				+ " LIMIT 1";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_Bill_BPartner_ID + " = (" + sqlBPartnerId + ")"
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ " AND i." + COLUMNNAME_Bill_BPartner_ID + " IS NULL "
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateOrg(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_AD_Org_ID + " = o." + COLUMNNAME_AD_Org_ID
				+ " FROM " + I_AD_Org.Table_Name + " o "
				+ " WHERE o." + COLUMNNAME_Value + " = i." + COLUMNNAME_OrgCode
				+ " AND o." + COLUMNNAME_AD_Client_ID + " = i." + COLUMNNAME_AD_Client_ID
				+ " AND o." + COLUMNNAME_IsActive + " = 'Y'"
				+ " AND i." + COLUMNNAME_I_IsImported + "<> 'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateUOM(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlUOMFromUOMCode = "SELECT " + COLUMNNAME_C_UOM_ID
				+ " FROM " + I_C_UOM.Table_Name + " u"
				+ " WHERE u." + COLUMNNAME_X12DE355 + " = i." + COLUMNNAME_X12DE355
				+ " AND u." + COLUMNNAME_IsActive + "='Y'";

		final String sqlUOMFromProduct = "SELECT " + COLUMNNAME_C_UOM_ID
				+ " FROM " + I_M_Product.Table_Name + " p"
				+ " WHERE p." + COLUMNNAME_M_Product_ID + " = i." + COLUMNNAME_M_Product_ID
				+ " AND p." + COLUMNNAME_IsActive + "='Y'";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_C_UOM_ID + " = "
				+ " CASE "
				+ " 	WHEN i." + COLUMNNAME_X12DE355 + " IS NULL "
				+ " 		THEN ( " + sqlUOMFromProduct + " )"
				+ "		 ELSE ( " + sqlUOMFromUOMCode + " ) "
				+ " END "
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateDocBaseType(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlDefaultDocType = "SELECT defaultDocType." + COLUMNNAME_C_DocType_ID
				+ " FROM " + I_C_DocType.Table_Name + " defaultDocType"
				+ " WHERE defaultDocType." + COLUMNNAME_IsSOTrx + " = i." + COLUMNNAME_IsSOTrx
				+ " AND defaultDocType." + COLUMNNAME_DocBaseType + " = "
				+ " 	CASE "
				+ " 		WHEN i." + COLUMNNAME_IsSOTrx + " = 'Y' "
				+ " 			THEN '" + X_C_DocType.DOCBASETYPE_ARInvoice + "' "
				+ " 		ELSE '" + X_C_DocType.DOCBASETYPE_APInvoice + "' "
				+ " 	END"
				+ " AND defaultDocType." + COLUMNNAME_DocSubType + " IS NULL "
				+ " AND defaultDocType." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " ORDER BY defaultDocType." + COLUMNNAME_AD_Org_ID + ", defaultDocType." + COLUMNNAME_IsDefault + " DESC "
				+ " LIMIT 1";

		final String sqlCustomDocType = "SELECT customDocType." + COLUMNNAME_C_DocType_ID
				+ " FROM " + I_C_DocType.Table_Name + " customDocType"
				+ " WHERE customDocType." + COLUMNNAME_IsSOTrx + " = i." + COLUMNNAME_IsSOTrx
				+ " AND customDocType." + COLUMNNAME_DocBaseType + " = i." + COLUMNNAME_DocBaseType
				+ " AND COALESCE(customDocType." + COLUMNNAME_DocSubType + ",'') = COALESCE(i." + COLUMNNAME_DocSubType + ",'') "
				+ " AND customDocType." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)"
				+ " ORDER BY customDocType." + COLUMNNAME_AD_Org_ID + ", customDocType." + COLUMNNAME_IsDefault + " DESC "
				+ " LIMIT 1";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_C_DocType_ID + " = "
				+ " CASE"
				+ "		 WHEN i." + COLUMNNAME_DocBaseType + " IS NULL "
				+ "				 THEN ( " + sqlDefaultDocType + " ) "
				+ "		 ELSE ( " + sqlCustomDocType + " ) "
				+ " END "
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private static void dbUpdateInvoiceRule(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlInvoiceRule = "SELECT " + COLUMNNAME_InvoiceRule
				+ " FROM " + I_C_BPartner.Table_Name + " p"
				+ " WHERE p." + COLUMNNAME_C_BPartner_ID + " = i." + COLUMNNAME_Bill_BPartner_ID
				+ " AND p." + COLUMNNAME_IsActive + "='Y'";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_InvoiceRule + " = ( " + sqlInvoiceRule + " )"
				+ " WHERE i." + COLUMNNAME_InvoiceRule + " IS NULL "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessages(@NonNull final ImportRecordsSelection selection)
	{
		final String missingMandatoryFieldMessage = "Mandatory " + I_C_Invoice_Candidate.Table_Name + ".";

		//
		// No BillBPartner
		updateErrorMessage(selection, COLUMNNAME_Bill_BPartner_ID, missingMandatoryFieldMessage + COLUMNNAME_Bill_BPartner_ID + " is missing!");

		//
		// No Product
		updateErrorMessage(selection, COLUMNNAME_M_Product_ID, COLUMNNAME_M_Product_ID + " is missing!");

		//
		// No BillLocation
		updateBillLocationErrorMessage(selection);

		//
		// No BillUser
		updateBillUserErrorMessage(selection);

		//
		// No C_DocType
		updateErrorMessage(selection, COLUMNNAME_C_DocType_ID, COLUMNNAME_C_DocType_ID + " not found for provided pair ( " + COLUMNNAME_DocBaseType + ", " + COLUMNNAME_DocSubType + " )!");

		//
		// No AD_Org
		updateOrgErrorMessage(selection);
	}

	private void updateErrorMessage(
			@NonNull final ImportRecordsSelection selection,
			@NonNull final String mandatoryColumnName,
			@NonNull final String errorMessage)
	{
		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name
				+ " SET " + COLUMNNAME_I_IsImported + " = 'E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = " + errorMessage + ", '"
				+ " WHERE " + mandatoryColumnName + " IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause();
		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No " + mandatoryColumnName + " = {}", no);
		}
	}

	private void updateBillLocationErrorMessage(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlBillBPartnerIdForProvidedBillLocation = "SELECT " + COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner_Location.Table_Name + " bpl"
				+ " WHERE bpl." + COLUMNNAME_C_BPartner_Location_ID + " = i." + COLUMNNAME_Bill_Location_ID
				+ " AND bpl." + COLUMNNAME_IsActive + "='Y'";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + COLUMNNAME_Bill_Location_ID + " not found for " + COLUMNNAME_Bill_BPartner_ID + "!" + ", '"
				+ " WHERE i." + COLUMNNAME_Bill_BPartner_ID + " <> ( " + sqlBillBPartnerIdForProvidedBillLocation + " ) "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);

		if (no != 0)
		{
			logger.warn("No " + COLUMNNAME_Bill_Location_ID + " = {}", no);
		}
	}

	private void updateBillUserErrorMessage(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlBillBPartnerIdForProvidedBillUser = "SELECT " + COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_AD_User.Table_Name + " us"
				+ " WHERE us." + COLUMNNAME_AD_User_ID + " = i." + COLUMNNAME_Bill_User_ID
				+ " AND us." + COLUMNNAME_IsActive + "='Y'";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + COLUMNNAME_Bill_User_ID + " not found for " + COLUMNNAME_Bill_BPartner_ID + "!" + ", '"
				+ " WHERE i." + COLUMNNAME_Bill_BPartner_ID + " <> ( " + sqlBillBPartnerIdForProvidedBillUser + " ) "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No " + COLUMNNAME_Bill_User_ID + " = {}", no);
		}
	}

	private void updateOrgErrorMessage(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlOrgIdForProvidedOrgCode = "SELECT " + COLUMNNAME_AD_Org_ID
				+ " FROM " + I_AD_Org.Table_Name + " o"
				+ " WHERE o." + COLUMNNAME_Value + " = i." + COLUMNNAME_OrgCode
				+ " AND o." + COLUMNNAME_IsActive + "= 'Y'";

		final String sql = "UPDATE " + I_I_Invoice_Candidate.Table_Name + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + COLUMNNAME_OrgCode + " does not match " + COLUMNNAME_AD_Org_ID + "!" + ", '"
				+ " WHERE i." + COLUMNNAME_AD_Org_ID + " NOT IN ( " + sqlOrgIdForProvidedOrgCode + " ) "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No " + COLUMNNAME_AD_Org_ID + " = {}", no);
		}
	}
}
