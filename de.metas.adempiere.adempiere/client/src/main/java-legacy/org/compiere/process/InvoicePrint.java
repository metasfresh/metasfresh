/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MUser;
import org.compiere.model.MUserMail;
import org.compiere.model.PrintInfo;
import org.compiere.model.X_C_Invoice;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;

/**
 *	Print Invoices on Paper or send PDFs
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: InvoicePrint.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class InvoicePrint extends SvrProcess
{
	/**	Mail PDF			*/
	private boolean		p_EMailPDF = false;
	/** Mail Template		*/
	private int			p_R_MailText_ID = 0;
	
	private Timestamp	m_dateInvoiced_From = null;
	private Timestamp	m_dateInvoiced_To = null;
	private int			m_C_BPartner_ID = 0;
	private int			m_C_Invoice_ID = 0;
	private String		m_DocumentNo_From = null;
	private String		m_DocumentNo_To = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateInvoiced"))
			{
				m_dateInvoiced_From = ((Timestamp)para[i].getParameter());
				m_dateInvoiced_To = ((Timestamp)para[i].getParameter_To());
			}
			else if (name.equals("EMailPDF"))
				p_EMailPDF = "Y".equals(para[i].getParameter());
			else if (name.equals("R_MailText_ID"))
				p_R_MailText_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				m_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Invoice_ID"))
				m_C_Invoice_ID = para[i].getParameterAsInt();
			else if (name.equals("DocumentNo"))
			{
				m_DocumentNo_From = (String)para[i].getParameter();
				m_DocumentNo_To = (String)para[i].getParameter_To();
			}
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
		if (m_DocumentNo_From != null && m_DocumentNo_From.length() == 0)
			m_DocumentNo_From = null;
		if (m_DocumentNo_To != null && m_DocumentNo_To.length() == 0)
			m_DocumentNo_To = null;
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		//	Need to have Template
		if (p_EMailPDF && p_R_MailText_ID == 0)
			throw new AdempiereUserError ("@NotFound@: @R_MailText_ID@");
		log.info("C_BPartner_ID=" + m_C_BPartner_ID
			+ ", C_Invoice_ID=" + m_C_Invoice_ID
			+ ", EmailPDF=" + p_EMailPDF + ",R_MailText_ID=" + p_R_MailText_ID
			+ ", DateInvoiced=" + m_dateInvoiced_From + "-" + m_dateInvoiced_To
			+ ", DocumentNo=" + m_DocumentNo_From + "-" + m_DocumentNo_To);
		
		IMailTextBuilder mText = null;
		if (p_R_MailText_ID > 0)
		{
			mText = Services.get(IMailBL.class).newMailTextBuilder(getCtx(), p_R_MailText_ID);
		}

		//	Too broad selection
		if (m_C_BPartner_ID == 0 && m_C_Invoice_ID == 0 && m_dateInvoiced_From == null && m_dateInvoiced_To == null
			&& m_DocumentNo_From == null && m_DocumentNo_To == null)
			throw new AdempiereUserError ("@RestrictSelection@");

		MClient client = MClient.get(getCtx());
		
		//	Get Info
		StringBuffer sql = new StringBuffer (
			"SELECT i.C_Invoice_ID,bp.AD_Language,c.IsMultiLingualDocument,"		//	1..3
			//	Prio: 1. C_BP_PrintFormat 2. BPartner 3. DocType, 4. PrintFormat (Org)	//	see ReportCtl+MInvoice
			+ " COALESCE (bppf_inv.AD_PrintFormat_ID,bp.Invoice_PrintFormat_ID,dt.AD_PrintFormat_ID,pf.Invoice_PrintFormat_ID),"	//	4
			+ " dt.DocumentCopies+bp.DocumentCopies,"								//	5
			+ " bpc.AD_User_ID, i.DocumentNo,"										//	6..7
			+ " bp.C_BPartner_ID "													//	8
			+ "FROM C_Invoice i"
			+ " INNER JOIN C_BPartner bp ON (i.C_BPartner_ID=bp.C_BPartner_ID)"
			+ " LEFT OUTER JOIN AD_User bpc ON (i.AD_User_ID=bpc.AD_User_ID)"
			+ " INNER JOIN AD_Client c ON (i.AD_Client_ID=c.AD_Client_ID)"
			+ " INNER JOIN AD_PrintForm pf ON (i.AD_Client_ID=pf.AD_Client_ID)"
			+ " INNER JOIN C_DocType dt ON (i.C_DocType_ID=dt.C_DocType_ID)"
			+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_inv "
						+ " ON (bp.C_BPartner_ID=bppf_inv.C_BPartner_ID) "
						+ " AND (bppf_inv.IsActive='Y')"
						+ " AND (dt.C_DocType_ID=bppf_inv.C_DocType_ID) "
						+ " AND (d.IsSOTrx=dt.IsSOTrx) "
						+ " AND (dt.DocBaseType IN ('AEI', 'APC', 'API', 'ARC', 'ARI', 'AVI', 'MXI')) " // Invoice types
		    + " WHERE i.AD_Client_ID=? AND i.AD_Org_ID=? AND i.isSOTrx='Y' AND "
		    + "       pf.AD_Org_ID IN (0,i.AD_Org_ID) AND " );	//	more them 1 PF
		boolean needAnd = false;
		if (m_C_Invoice_ID != 0)
			sql.append("i.C_Invoice_ID=").append(m_C_Invoice_ID);
		else
		{
			if (m_C_BPartner_ID != 0)
			{
				sql.append ("i.C_BPartner_ID=").append (m_C_BPartner_ID);
				needAnd = true;
			}
			if (m_dateInvoiced_From != null && m_dateInvoiced_To != null)
			{
				if (needAnd)
					sql.append(" AND ");
				sql.append("TRUNC(i.DateInvoiced) BETWEEN ")
					.append(DB.TO_DATE(m_dateInvoiced_From, true)).append(" AND ")
					.append(DB.TO_DATE(m_dateInvoiced_To, true));
				needAnd = true;
			}
			else if (m_dateInvoiced_From != null)
			{
				if (needAnd)
					sql.append(" AND ");
				sql.append("TRUNC(i.DateInvoiced) >= ")
					.append(DB.TO_DATE(m_dateInvoiced_From, true));
				needAnd = true;
			}
			else if (m_dateInvoiced_To != null)
			{
				if (needAnd)
					sql.append(" AND ");
				sql.append("TRUNC(i.DateInvoiced) <= ")
					.append(DB.TO_DATE(m_dateInvoiced_To, true));
				needAnd = true;
			}
			else if (m_DocumentNo_From != null && m_DocumentNo_To != null)
			{
				if (needAnd)
					sql.append(" AND ");
				sql.append("i.DocumentNo BETWEEN ")
					.append(DB.TO_STRING(m_DocumentNo_From)).append(" AND ")
					.append(DB.TO_STRING(m_DocumentNo_To));
			}
			else if (m_DocumentNo_From != null)
			{
				if (needAnd)
					sql.append(" AND ");
				if (m_DocumentNo_From.indexOf('%') == -1)
					sql.append("i.DocumentNo >= ")
						.append(DB.TO_STRING(m_DocumentNo_From));
				else
					sql.append("i.DocumentNo LIKE ")
						.append(DB.TO_STRING(m_DocumentNo_From));
			}
			
			if (p_EMailPDF)
			{
				if (needAnd)
				{
					sql.append(" AND ");
				}
				/* if emailed to customer only select COmpleted & CLosed invoices */ 
				sql.append("i.DocStatus IN ('CO','CL') "); 
			}
		}
		sql.append(" ORDER BY i.C_Invoice_ID, pf.AD_Org_ID DESC");	//	more than 1 PF record
		log.debug(sql.toString());

		MPrintFormat format = null;
		int old_AD_PrintFormat_ID = -1;
		int old_C_Invoice_ID = -1;
		int C_BPartner_ID = 0;
		int count = 0;
		int errors = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName()); 
			pstmt.setInt(1, Env.getAD_Client_ID(Env.getCtx()));
			pstmt.setInt(2, Env.getAD_Org_ID(Env.getCtx()));
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				int C_Invoice_ID = rs.getInt(1);
				if (C_Invoice_ID == old_C_Invoice_ID)	//	multiple pf records
					continue;
				old_C_Invoice_ID = C_Invoice_ID;
				//	Set Language when enabled
				Language language = Language.getLoginLanguage();		//	Base Language
				String AD_Language = rs.getString(2);
				if (AD_Language != null && "Y".equals(rs.getString(3)))
					language = Language.getLanguage(AD_Language);
				//
				int AD_PrintFormat_ID = rs.getInt(4);
				int copies = rs.getInt(5);
				if (copies == 0)
					copies = 1;
				int AD_User_ID = rs.getInt(6);
				MUser to = new MUser (getCtx(), AD_User_ID, get_TrxName());
				String DocumentNo = rs.getString(7);
				C_BPartner_ID = rs.getInt(8);
				//
				String documentDir = client.getDocumentDir();
				if (documentDir == null || documentDir.length() == 0)
					documentDir = ".";
				//
				if (p_EMailPDF && (to.get_ID() == 0 || to.getEMail() == null || to.getEMail().length() == 0))
				{
					addLog (C_Invoice_ID, null, null, DocumentNo + " @RequestActionEMailNoTo@");
					errors++;
					continue;
				}
				if (AD_PrintFormat_ID == 0)
				{
					addLog (C_Invoice_ID, null, null, DocumentNo + " No Print Format");
					errors++;
					continue;
				}
				//	Get Format & Data
				if (AD_PrintFormat_ID != old_AD_PrintFormat_ID)
				{
					format = MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false);
					old_AD_PrintFormat_ID = AD_PrintFormat_ID;
				}
				format.setLanguage(language);
				format.setTranslationLanguage(language);
				//	query
				MQuery query = new MQuery("C_Invoice_Header_v");
				query.addRestriction("C_Invoice_ID", Operator.EQUAL, new Integer(C_Invoice_ID));

				//	Engine
				PrintInfo info = new PrintInfo(
					DocumentNo,
					X_C_Invoice.Table_ID,
					C_Invoice_ID,
					C_BPartner_ID);
				info.setCopies(copies);
				ReportEngine re = new ReportEngine(getCtx(), format, query, info);
				boolean printed = false;
				if (p_EMailPDF)
				{
					String subject = mText.getMailHeader() + " - " + DocumentNo;
					EMail email = client.createEMail(to.getEMail(), subject, null);
					if (!email.isValid())
					{
						addLog (C_Invoice_ID, null, null,
						  DocumentNo + " @RequestActionEMailError@ Invalid EMail: " + to);
						errors++;
						continue;
					}
					mText.setAD_User(to);					//	Context
					mText.setC_BPartner(C_BPartner_ID);	//	Context
					mText.setRecord(new MInvoice(getCtx(), C_Invoice_ID, get_TrxName()));
					String message = mText.getFullMailText();
					if (mText.isHtml())
						email.setMessageHTML(subject, message);
					else
					{
						email.setSubject (subject);
						email.setMessageText (message);
					}
					//
					File invoice = null;
					if (!Ini.isClient())
						invoice = new File(MInvoice.getPDFFileName(documentDir, C_Invoice_ID));
					File attachment = re.getPDF(invoice);
					log.debug(to + " - " + attachment);
					email.addAttachment(attachment);
					//
					final EMailSentStatus emailSentStatus = email.send();
					MUserMail um = new MUserMail(getCtx(), mText.getR_MailText_ID(), getAD_User_ID(), email, emailSentStatus);
					um.save();
					if (emailSentStatus.isSentOK())
					{
						addLog (C_Invoice_ID, null, null,
						  DocumentNo + " @RequestActionEMailOK@ - " + to.getEMail());
						count++;
						printed = true;
					}
					else
					{
						addLog (C_Invoice_ID, null, null,
						  DocumentNo + " @RequestActionEMailError@ " + emailSentStatus.getSentMsg()
						  + " - " + to.getEMail());
						errors++;
					}
				}
				else
				{
					// metas: Abfrage ob PrintFormat mit Jasper oder nicht.
					if (re.getPrintFormat() != null
						&& re.getPrintFormat().getJasperProcess_ID() > 0) {
						ReportCtl.startDocumentPrint(ReportEngine.INVOICE, C_Invoice_ID, null, -1, true);
					} else {
						re.print();
					}
					// metas end
					count++;
					printed = true;
				}
				//	Print Confirm
				if (printed)
				{
					StringBuffer sb = new StringBuffer ("UPDATE C_Invoice "
						+ "SET DatePrinted=now(), IsPrinted='Y' WHERE C_Invoice_ID=")
						.append (C_Invoice_ID);
					DB.executeUpdate(sb.toString(), get_TrxName());
				}
			}	//	for all entries						
		}
		catch (Exception e)
		{
			log.error("doIt - " + sql, e);
			throw new Exception (e);
		}
		finally {
			DB.close(rs, pstmt);
		}
		//
		if (p_EMailPDF)
			return "@Sent@=" + count + " - @Errors@=" + errors;
		return "@Printed@=" + count;
	}	//	doIt

}	//	InvoicePrint
