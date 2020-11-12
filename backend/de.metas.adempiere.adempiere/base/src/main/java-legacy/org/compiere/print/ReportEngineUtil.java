/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere.print;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.report.server.ReportConstants;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.MClient;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@UtilityClass
public class ReportEngineUtil
{
	private static final Logger log = LogManager.getLogger(ReportEngineUtil.class);

	@Value
	@Builder
	public static class StandardDocumentReportInfo
	{
		@NonNull
		ReportEngineType type;

		int adPrintFormatId;
		MPrintFormat adPrintFormat;
		AdProcessId jasperProcessId;

		@NonNull
		Language language;

		int recordId;

		String documentNo;

		int copies;

		BPartnerId bpartnerId;
	}
	public static void startJasperReportsProcess(@NonNull final ProcessInfo processInfo)
	{
		final AdProcessId adProcessId = processInfo.getAdProcessId();

		final ReportEngineType reportEngineDocumentType = ReportEngineType.ofProcessIdOrNull(adProcessId);
		if (reportEngineDocumentType != null)
		{
			final StandardDocumentReportInfo reportInfo = getStandardDocumentReportInfo(
					reportEngineDocumentType,
					processInfo.getRecord_ID(),
					-1 // adPrintFormatToUseId
			);
			startJasperReportsProcess(processInfo, reportInfo.getJasperProcessId());
		}
		else
		{
			startJasperReportsProcess(processInfo, processInfo.getAdProcessId());
		}
	}

	private static final void startJasperReportsProcess(
			@NonNull final ProcessInfo processInfo,
			@NonNull final AdProcessId jasperProcessId)
	{
		final ProcessExecutionResult jasperProcessResult = ProcessInfo.builder()
				//
				.setCtx(processInfo.getCtx())
				.setCreateTemporaryCtx()
				.setClientId(processInfo.getClientId())
				.setUserId(processInfo.getUserId())
				.setRoleId(processInfo.getRoleId())
				.setWhereClause(processInfo.getWhereClause())
				.setWindowNo(processInfo.getWindowNo())
				.setTabNo(processInfo.getTabNo())
				.setPrintPreview(processInfo.isPrintPreview())
				//
				.setAD_Process_ID(jasperProcessId)
				.setRecord(processInfo.getRecordRefOrNull())
				.setReportLanguage(processInfo.getReportLanguage())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, getBarcodeServlet(processInfo.getClientId(), processInfo.getOrgId()))
				// TODO .addParameter(IPrintService.PARAM_PrintCopies, getPrintInfo().getCopies())
				//
				// Execute Process
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		//
		// Throw exception in case of failure
		jasperProcessResult.propagateErrorIfAny();

		//
		// Update caller process result
		final ProcessExecutionResult callerProcessResult = processInfo.getResult();
		callerProcessResult.setReportData(jasperProcessResult.getReportData(), jasperProcessResult.getReportFilename(), jasperProcessResult.getReportContentType());
	}


	public static String getBarcodeServlet(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getValue(ReportConstants.SYSCONFIG_BarcodeServlet,
				null,  // defaultValue,
				clientId.getRepoId(),
				orgId.getRepoId());
	}

	@Value(staticConstructor = "of")
	private static class ReportEngineTypeAndRecordId
	{
		@With
		@NonNull ReportEngineType type;

		@With
		int recordId;
	}

	@NonNull
	private static ReportEngineTypeAndRecordId getDocumentWhat(final int orderId)
	{
		final DocBaseAndSubType docBaseTypeAndSubType = retrieveDocBaseTypeAndSubTypeByOrderId(orderId);
		final String docSubType = docBaseTypeAndSubType.getDocSubType();

		if (X_C_DocType.DOCSUBTYPE_POSOrder.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(docSubType))
		{
			final int invoiceId = retrieveLastInvoiceIdByOrderId(orderId);
			return invoiceId > 0
					? ReportEngineTypeAndRecordId.of(ReportEngineType.INVOICE, invoiceId)
					: ReportEngineTypeAndRecordId.of(ReportEngineType.ORDER, orderId);
		}
		else if (X_C_DocType.DOCSUBTYPE_WarehouseOrder.equals(docSubType))
		{
			final int inoutId = retrieveLastInOutIdByOrderId(orderId);
			return inoutId > 0
					? ReportEngineTypeAndRecordId.of(ReportEngineType.SHIPMENT, inoutId)
					: ReportEngineTypeAndRecordId.of(ReportEngineType.ORDER, orderId);
		}
		else
		{
			return ReportEngineTypeAndRecordId.of(ReportEngineType.ORDER, orderId);
		}
	}

	private static DocBaseAndSubType retrieveDocBaseTypeAndSubTypeByOrderId(final int orderId)
	{
		final DocTypeId docTypeId = retrieveDocTypeByOrderId(orderId);

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		return docTypeDAO.getDocBaseAndSubTypeById(docTypeId);
	}

	private static DocTypeId retrieveDocTypeByOrderId(final int orderId)
	{
		final String sql = "SELECT C_DocType_ID, C_DocTypeTarget_ID FROM C_Order WHERE C_Order_ID=?";
		final Object[] sqlParams = { orderId };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final DocTypeId docTypeId = CoalesceUtil.coalesce(
						DocTypeId.ofRepoIdOrNull(rs.getInt(1)),
						DocTypeId.ofRepoIdOrNull(rs.getInt(2)));
				if (docTypeId == null)
				{
					throw new AdempiereException("Cannot determin doc type of C_Order_ID=" + orderId);
				}
				return docTypeId;
			}
			else
			{
				throw new AdempiereException("Order not found for C_Order_ID=" + orderId); // shall not happen
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static int retrieveLastInvoiceIdByOrderId(final int orderId)
	{
		final String sql = "SELECT C_Invoice_ID REC FROM C_Invoice WHERE C_Order_ID=? ORDER BY C_Invoice_ID DESC";
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, orderId);
	}

	private static int retrieveLastInOutIdByOrderId(final int orderId)
	{
		final String sql = "SELECT M_InOut_ID REC FROM M_InOut WHERE C_Order_ID=? ORDER BY M_InOut_ID DESC";
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, orderId);
	}

	@NonNull
	public static StandardDocumentReportInfo getStandardDocumentReportInfo(
			ReportEngineType type,
			int Record_ID,
			final int adPrintFormatToUseId)
	{
		Check.assumeGreaterThanZero(Record_ID, "Record_ID");

		// Order - Print Shipment or Invoice
		if (type == ReportEngineType.ORDER)
		{
			final ReportEngineTypeAndRecordId what = getDocumentWhat(Record_ID);
			type = what.getType();
			Record_ID = what.getRecordId();
		}    // Order

		int adPrintFormatId = 0;
		BPartnerId bpartnerId = null;
		String documentNo = null;
		int copies = 1;

		Language language = MClient.get(Env.getCtx()).getLanguage();

		// Get Document Info
		final String sql;
		final int printFormatColumnIndex;
		if (type == ReportEngineType.CHECK)
		{
			throw new AdempiereException("Not supported for type " + type);
		}
		else if (type == ReportEngineType.DUNNING)
		{
			printFormatColumnIndex = 1;
			sql = "SELECT dl.Dunning_PrintFormat_ID,"
					+ " c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,dr.DunningDate "
					+ "FROM C_DunningRunEntry d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
					+ " INNER JOIN C_DunningRun dr ON (d.C_DunningRun_ID=dr.C_DunningRun_ID)"
					+ " INNER JOIN C_DunningLevel dl ON (dl.C_DunningLevel_ID=d.C_DunningLevel_ID) "
					+ "WHERE d.C_DunningRunEntry_ID=?";            // info from Dunning
		}
		else if (type == ReportEngineType.REMITTANCE)
		{
			throw new UnsupportedOperationException("Not supported for type " + type);
		}
		else if (type == ReportEngineType.PROJECT)
		{
			printFormatColumnIndex = 1;
			sql = "SELECT pf.Project_PrintFormat_ID,"
					+ " c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,d.Value "
					+ "FROM C_Project d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
					+ " LEFT OUTER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
					+ "WHERE d.C_Project_ID=?"                    // info from PrintForm
					+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		}
		else if (type == ReportEngineType.MANUFACTURING_ORDER)
		{
			printFormatColumnIndex = 1;
			sql = "SELECT COALESCE(bppf_pporder.AD_PrintFormat_ID, pf.Manuf_Order_PrintFormat_ID),"
					+ " c.IsMultiLingualDocument,"
					+ " bp.AD_Language, "
					+ " 0, "
					+ " d.documentNo "
					+ " FROM PP_Order d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " LEFT OUTER JOIN AD_User u ON (u.AD_User_ID=d.Planner_ID)"
					+ " LEFT OUTER JOIN C_BPartner bp ON (u.C_BPartner_ID=bp.C_BPartner_ID) "
					+ " LEFT OUTER JOIN C_DocType dt ON dt.DocBaseType IN ('MOP')"
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_pporder "
					+ " ON (bp.C_BPartner_ID=bppf_pporder.C_BPartner_ID) "
					+ " AND (bppf_pporder.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_pporder.C_DocType_ID) " // Manufacturing Order
					+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
					+ "WHERE d.PP_Order_ID=?"                    // info from PrintForm
					+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		}
		else if (type == ReportEngineType.DISTRIBUTION_ORDER)
		{
			printFormatColumnIndex = 1;
			sql = "SELECT COALESCE(bppf_ddorder.AD_PrintFormat_ID, pf.Distrib_Order_PrintFormat_ID),"
					+ " c.IsMultiLingualDocument,"
					+ " bp.AD_Language, "
					+ " bp.C_BPartner_ID, "
					+ " d.documentNo "
					+ " FROM DD_Order d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
					+ " LEFT OUTER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
					+ " LEFT OUTER JOIN C_DocType dt ON dt.DocBaseType IN ('DOO')"
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ddorder "
					+ " ON (bp.C_BPartner_ID=bppf_ddorder.C_BPartner_ID) "
					+ " AND (bppf_ddorder.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_ddorder.C_DocType_ID) " // Distribution Order
					+ "WHERE d.DD_Order_ID=?"                    // info from PrintForm
					+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		}
		// Fix [2574162] Priority to choose invoice print format not working
		else if (type == ReportEngineType.ORDER || type == ReportEngineType.INVOICE)
		{
			if (type == ReportEngineType.ORDER)
			{
				printFormatColumnIndex = 1;
			}
			else if (type == ReportEngineType.INVOICE)
			{
				printFormatColumnIndex = 3;
			}
			else
			{
				throw new AdempiereException("Internal error: type not handled here: " + type); // shall not happen
			}

			// Prio: 1. C_BP_PrintFormat 2. BPartner 3. DocType, 4. PrintFormat (Org) // see InvoicePrint
			sql = "SELECT COALESCE (bppf_order.AD_PrintFormat_ID,pf.Order_PrintFormat_ID),"                    // 1
					+ " COALESCE (bppf_ship.AD_PrintFormat_ID,pf.Shipment_PrintFormat_ID),"                        // 2
					+ " COALESCE (bppf_inv.AD_PrintFormat_ID,bp.Invoice_PrintFormat_ID,dt.AD_PrintFormat_ID,pf.Invoice_PrintFormat_ID),"    // 3
					+ " pf.Project_PrintFormat_ID,"                                                                // 4
					+ " pf.Remittance_PrintFormat_ID,"                                                            // 5
					+ " c.IsMultiLingualDocument, bp.AD_Language,"                                                // 6..7
					+ " COALESCE(dt.DocumentCopies,0)+COALESCE(bp.DocumentCopies,1), "                            // 8
					+ " COALESCE(bppf.AD_PrintFormat_ID, dt.AD_PrintFormat_ID),"                                // 9
					+ " bp.C_BPartner_ID,d.documentNo "                                                            // 10..11
					+ "FROM " + type.getBaseTableName() + " d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
					+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
					+ " LEFT OUTER JOIN C_DocType dt ON ((d.C_DocType_ID>0 AND d.C_DocType_ID=dt.C_DocType_ID) OR (d.C_DocType_ID=0 AND d.C_DocTypeTarget_ID=dt.C_DocType_ID)) "
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf "
					+ " ON (bp.C_BPartner_ID=bppf.C_BPartner_ID) "
					+ " AND (bppf.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf.C_DocType_ID) "
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_order "
					+ " ON (bp.C_BPartner_ID=bppf_order.C_BPartner_ID) "
					+ " AND (bppf_order.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_order.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('SOO', 'POO')) "
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ship "
					+ " ON (bp.C_BPartner_ID=bppf_ship.C_BPartner_ID) "
					+ " AND (bppf_ship.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_ship.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('MMR', 'MMS')) "
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_inv "
					+ " ON (bp.C_BPartner_ID=bppf_inv.C_BPartner_ID) "
					+ " AND (bppf_inv.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_inv.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('AEI', 'APC', 'API', 'ARC', 'ARI', 'AVI', 'MXI')) " // Invoice types
					+ "WHERE d." + type.getKeyColumnName() + "=?"            // info from PrintForm
					+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) "
					+ "ORDER BY pf.AD_Org_ID DESC";
		}
		else if (type == ReportEngineType.SHIPMENT)
		{
			printFormatColumnIndex = 2;
			// Get PrintFormat from Org or 0 of document client
			// Prio: 1. C_BP_PrintFormat 2. BPartner 3. DocType, 4. PrintFormat (Org) // see InvoicePrint
			sql = "SELECT COALESCE (bppf_order.AD_PrintFormat_ID,pf.Order_PrintFormat_ID),"                    // 1
					+ " COALESCE (bppf_ship.AD_PrintFormat_ID,pf.Shipment_PrintFormat_ID),"                        // 2
					+ " COALESCE (bppf_inv.AD_PrintFormat_ID,bp.Invoice_PrintFormat_ID,dt.AD_PrintFormat_ID,pf.Invoice_PrintFormat_ID),"    // 3
					+ " pf.Project_PrintFormat_ID,"                                                                // 4
					+ " pf.Remittance_PrintFormat_ID,"                                                            // 5
					+ " c.IsMultiLingualDocument, bp.AD_Language,"                                                // 6..7
					+ " COALESCE(dt.DocumentCopies,0)+COALESCE(bp.DocumentCopies,1), "                            // 8
					+ " COALESCE(bppf.AD_PrintFormat_ID, dt.AD_PrintFormat_ID),"                                // 9
					+ " bp.C_BPartner_ID,d.documentNo, "                                                        // 10..11
					+ " COALESCE(bppf_pporder.AD_PrintFormat_ID, pf.Manuf_Order_PrintFormat_ID), "                // 12
					+ " COALESCE(bppf_ddorder.AD_PrintFormat_ID, pf.Distrib_Order_PrintFormat_ID) "                // 13
					+ "FROM " + type.getBaseTableName() + " d"
					+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
					+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
					+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
					+ " LEFT OUTER JOIN C_DocType dt ON (d.C_DocType_ID=dt.C_DocType_ID) "
					//
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf "
					+ " ON (bp.C_BPartner_ID=bppf.C_BPartner_ID) "
					+ " AND (bppf.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf.C_DocType_ID) "
					//
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_order "
					+ " ON (bp.C_BPartner_ID=bppf_order.C_BPartner_ID) "
					+ " AND (bppf_order.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_order.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('SOO', 'POO')) " // Auftrag, Bestellung
					//
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ship "
					+ " ON (bp.C_BPartner_ID=bppf_ship.C_BPartner_ID) "
					+ " AND (bppf_ship.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_ship.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('MMS', 'MMR')) " // Shipment, Receipt
					//
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_inv "
					+ " ON (bp.C_BPartner_ID=bppf_inv.C_BPartner_ID) "
					+ " AND (bppf_inv.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_inv.C_DocType_ID) "
					+ " AND (d.IsSOTrx=dt.IsSOTrx) "
					+ " AND (dt.DocBaseType IN ('AEI', 'APC', 'API', 'ARC', 'ARI', 'AVI', 'MXI')) " // Invoice types
					//
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_pporder "
					+ " ON (bp.C_BPartner_ID=bppf_pporder.C_BPartner_ID) "
					+ " AND (bppf_pporder.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_pporder.C_DocType_ID) "
					+ " AND (dt.DocBaseType IN ('MOP')) " // Manufacturing Order
					+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ddorder "
					+ " ON (bp.C_BPartner_ID=bppf_ddorder.C_BPartner_ID) "
					+ " AND (bppf_ddorder.IsActive='Y')"
					+ " AND (dt.C_DocType_ID=bppf_ddorder.C_DocType_ID) "
					+ " AND (dt.DocBaseType IN ('DOO')) " // Distribution Order
					//
					+ "WHERE d." + type.getKeyColumnName() + "=?"            // info from PrintForm
					+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) "
					+ " ORDER BY pf.AD_Org_ID DESC";
		}
		else
		{
			throw new AdempiereException("Type not handled: " + type);
		}

		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, Record_ID);
			rs = pstmt.executeQuery();
			if (rs.next())    // first record only
			{
				if (type == ReportEngineType.CHECK
						|| type == ReportEngineType.DUNNING
						|| type == ReportEngineType.REMITTANCE
						|| type == ReportEngineType.PROJECT
						|| type == ReportEngineType.MANUFACTURING_ORDER
						|| type == ReportEngineType.DISTRIBUTION_ORDER)
				{
					adPrintFormatId = rs.getInt(printFormatColumnIndex);
					copies = 1;

					// Set Language when enabled
					final String adLanguage = rs.getString(3);
					if (adLanguage != null)// && "Y".equals(rs.getString(2))) // IsMultiLingualDocument
					{
						language = Language.getLanguage(adLanguage);
					}

					bpartnerId = BPartnerId.ofRepoIdOrNull(rs.getInt(4));
					if (type == ReportEngineType.DUNNING)
					{
						Timestamp ts = rs.getTimestamp(5);
						documentNo = ts.toString();
					}
					else
					{
						documentNo = rs.getString(5);
					}
				}
				else
				{
					// Set PrintFormat
					adPrintFormatId = rs.getInt(printFormatColumnIndex);
					if (type != ReportEngineType.INVOICE && rs.getInt(9) > 0)        // C_DocType.adPrintFormatId
					{
						adPrintFormatId = rs.getInt(9);
					}

					copies = rs.getInt(8);

					// Set Language when enabled
					String AD_Language = rs.getString(7);
					if (AD_Language != null) // && "Y".equals(rs.getString(6))) // IsMultiLingualDocument
						language = Language.getLanguage(AD_Language);

					bpartnerId = BPartnerId.ofRepoIdOrNull(rs.getInt(10));
					documentNo = rs.getString(11);
				}
			}
		}
		catch (Exception ex)
		{
			throw new DBException(ex, sql, Record_ID);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// metas: begin: Use provided print format
		if (adPrintFormatToUseId > 0)
		{
			log.debug("Using AD_PrintFormat_ID={}(provided) instead of {}(detected)", adPrintFormatToUseId, adPrintFormatId);
			adPrintFormatId = adPrintFormatToUseId;
		}
		// metas: end

		if (adPrintFormatId <= 0)
		{
			throw new AdempiereException("No PrintFormat found for Type=" + type + ", Record_ID=" + Record_ID);
		}

		final MPrintFormat adPrintFormat = MPrintFormat.get(Env.getCtx(), adPrintFormatId, false);
		if (adPrintFormat == null)
		{
			throw new AdempiereException("No print format found for AD_PrintFormat_ID=" + adPrintFormatId);
		}

		final AdProcessId jasperProcessId = AdProcessId.ofRepoIdOrNull(adPrintFormat.getJasperProcess_ID());

		return StandardDocumentReportInfo.builder()
				.type(type)
				.adPrintFormatId(adPrintFormatId)
				.adPrintFormat(adPrintFormat)
				.jasperProcessId(jasperProcessId)
				// format.getPrinterName()
				.language(language)
				.recordId(Record_ID)
				.documentNo(documentNo)
				.copies(copies)
				.bpartnerId(bpartnerId)
				.build();
	}
}
