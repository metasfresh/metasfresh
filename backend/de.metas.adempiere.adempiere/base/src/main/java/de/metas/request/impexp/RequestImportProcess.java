package de.metas.request.impexp;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_Request;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.I_R_Status;
import org.compiere.model.PO;
import org.compiere.model.X_I_Request;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;

/**
 * Imports {@link I_I_Request} records to {@link I_R_Request}.
 *
 * @author cg
 *
 */
public class RequestImportProcess extends SimpleImportProcessTemplate<I_I_Request>
{
	private static final Logger log = LogManager.getLogger(RequestImportProcess.class);


	@Override
	public Class<I_I_Request> getImportModelClass()
	{
		return I_I_Request.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Request.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_R_Request.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Request.COLUMNNAME_DocumentNo;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		final String sqlImportWhereClause = COLUMNNAME_I_IsImported + "<>" + DB.TO_BOOLEAN(true)
				+ "\n " + selection.toSqlWhereClause("i");
		//
		// Update C_BPartner_ID
		{
			dbUpdateBPartnerIds(sqlImportWhereClause);
		}
		//
		// Update R_RequestType_ID by Name
		{
			dbUpdateRequestTypeIds(sqlImportWhereClause);
		}
		//
		// Update R_Status_ID by Name
		{
			dbUpdateStatusIds(sqlImportWhereClause);
		}
	}

	private void dbUpdateBPartnerIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + ")"
				+ " from " + I_C_BPartner.Table_Name + " bp "
				+ " where bp." + I_C_BPartner.COLUMNNAME_Value + "=i." + I_I_Request.COLUMNNAME_Value
				+ " and bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + "=i." + I_I_Request.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_Request.Table_Name + " i "
				+ "\n SET " + I_I_Request.COLUMNNAME_C_BPartner_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Request.COLUMNNAME_C_BPartner_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set C_BPartner_ID for {} records", no);
		//
		// Flag missing BPartners
		markAsError("BPartner not found", I_I_Request.COLUMNNAME_C_BPartner_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private void dbUpdateRequestTypeIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(rt." + I_R_RequestType.COLUMNNAME_R_RequestType_ID + ")"
				+ " from " + I_R_RequestType.Table_Name + " rt "
				+ " where rt." + I_R_RequestType.COLUMNNAME_Name + "=i." + I_I_Request.COLUMNNAME_RequestType
				+ " and rt." + I_R_RequestType.COLUMNNAME_AD_Client_ID + "=i." + I_I_Request.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_Request.Table_Name + " i "
				+ "\n SET " + I_I_Request.COLUMNNAME_R_RequestType_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Request.COLUMNNAME_R_RequestType_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set R_RequestType_ID for {} records", no);
		//
		// Flag missing R_RequestType_ID
		markAsError("Request Type not found", I_I_Request.COLUMNNAME_R_RequestType_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private void dbUpdateStatusIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(r." + I_R_Status.COLUMNNAME_R_Status_ID + ")"
				+ " from " + I_R_Status.Table_Name + " r "
				+ " where r." + I_R_Status.COLUMNNAME_Name + "=i." + I_I_Request.COLUMNNAME_Status
				+ " and r." + I_R_Status.COLUMNNAME_AD_Client_ID + "=i." + I_I_Request.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_Request.Table_Name + " i "
				+ "\n SET " + I_I_Request.COLUMNNAME_R_Status_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_Request.COLUMNNAME_R_Status_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set M_Product_ID for {} records (by Value)", no);
		//
		// Flag missing status
		markAsError("R_Status not found", I_I_Request.COLUMNNAME_R_Status_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private final void markAsError(final String errorMsg, final String sqlWhereClause)
	{
		final String sql = "UPDATE " + I_I_Request.Table_Name + " i "
				+ "\n SET " + COLUMNNAME_I_IsImported + "=?, " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||? "
				+ "\n WHERE " + sqlWhereClause;
		final Object[] sqlParams = new Object[] { X_I_Request.I_ISIMPORTED_ImportFailed, errorMsg + "; " };
		DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_ThreadInherited);

	}

	@Override
	protected I_I_Request retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		final PO po = TableModelLoader.instance.getPO(ctx, I_I_Request.Table_Name, rs, ITrx.TRXNAME_ThreadInherited);
		return InterfaceWrapperHelper.create(po, I_I_Request.class);
	}

	/*
	 * @param isInsertOnly ignored. This import is only for updates.
	 */
	@Override
	protected ImportRecordResult importRecord(
			final IMutable<Object> state_NOTUSED,
			final I_I_Request importRecord,
			final boolean isInsertOnly_NOTUSED)
	{
		//
		// Create a new request
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class, importRecord);
		request.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		// BPartner
		{
			int bpartnerId = importRecord.getC_BPartner_ID();
			if (bpartnerId <= 0)
			{
				throw new AdempiereException("BPartner not found");
			}
			request.setC_BPartner_ID(bpartnerId);
		}
		//
		// request type
		{
			int requesTypeId = importRecord.getR_RequestType_ID();
			if (requesTypeId <= 0)
			{
				throw new AdempiereException("Request Type not found");
			}
			request.setR_RequestType_ID(requesTypeId);
		}
		//
		// status
		{
			int statusId = importRecord.getR_Status_ID();
			if (statusId <= 0)
			{
				throw new AdempiereException("Status not found");
			}
			request.setR_Status_ID(statusId);
		}
		//
		// set data from the other fields
		request.setDateTrx(importRecord.getDateTrx());
		request.setDateNextAction(importRecord.getDateNextAction());
		request.setSummary(importRecord.getSummary());
		request.setDocumentNo(importRecord.getDocumentNo());
		int userid = Env.getAD_User_ID(getCtx());
		request.setSalesRep_ID(userid);
		//
		InterfaceWrapperHelper.save(request);
		//
		// Link back the request to current import record
		importRecord.setR_Request(request);
		//
		return ImportRecordResult.Inserted;
	}

	@Override
	protected void markImported(final I_I_Request importRecord)
	{
		importRecord.setI_IsImported(X_I_Request.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
