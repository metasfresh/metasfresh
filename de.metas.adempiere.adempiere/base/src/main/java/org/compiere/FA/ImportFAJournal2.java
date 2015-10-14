/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 *
 * Copyright (C) 2005 Robert KLEIN. robeklein@gmail.com * 
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.compiere.FA;

/*
 * #%L
 * ADempiere ERP - Base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.adempiere.document.service.IDocumentNoBuilderFactory;
import org.adempiere.util.Services;
import org.compiere.model.MAccount;
import org.compiere.model.MAssetChange;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MJournalLine;
import org.compiere.model.MXIFAJournal;
import org.compiere.model.X_I_FAJournal;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;


/**
 *	Import GL Journal Batch/JournalLine from I_FAJournal
 *
 * 	@author 	Rob Klein
 * 	@version 	$Id: ImportFAJournal2.java,v 1.0 $
 */
public class ImportFAJournal2 extends SvrProcess
{
	/**	Client to be imported to		*/
	private int 			m_AD_Client_ID = 0;
	/**	Organization to be imported to	*/
	private int 			m_AD_Org_ID = 0;
	/**	Acct Schema to be imported to	*/
	private int				m_C_AcctSchema_ID = 0;
	/** Default Date					*/
	private Timestamp		m_DateAcct = null;
	/**	Delete old Imported				*/
	private boolean			m_DeleteOldImported = false;
	/**	Don't import					*/
	private boolean			m_IsValidateOnly = false;
	/** Import if no Errors				*/
	private boolean			m_IsImportOnlyNoErrors = true;
	/** Entry Type				*/
	private String			m_EntryType = null;
	
	/** Record ID				*/
	private int p_Depreciation_Entry_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			p_Depreciation_Entry_ID = getRecord_ID();
			
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_AcctSchema_ID"))
				m_C_AcctSchema_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DateAcct"))
				m_DateAcct = (Timestamp)para[i].getParameter();
			else if (name.equals("IsValidateOnly"))
				m_IsValidateOnly = "Y".equals(para[i].getParameter());
			else if (name.equals("IsImportOnlyNoErrors"))
				m_IsImportOnlyNoErrors = "Y".equals(para[i].getParameter());
			else if (name.equals("DeleteOldImported"))
				m_DeleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.info("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

        CallableStatement cs;

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("IsValidateOnly=" + m_IsValidateOnly + ", IsImportOnlyNoErrors=" + m_IsImportOnlyNoErrors);
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;
		
		// Clear imported and processed records
		
		String sqldel = null;
		
		sqldel = "SELECT A_ENTRY_TYPE "		  
		  + "FROM A_DEPRECIATION_ENTRY "
		  + "WHERE A_DEPRECIATION_ENTRY.A_DEPRECIATION_ENTRY_ID = ?";		  
		
		m_EntryType = DB.getSQLValueString(get_TrxName(), sqldel, p_Depreciation_Entry_ID);

		if (m_DeleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_FAJournal "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (),get_TrxName());
			log.info ("doIt - Delete Old Impored =" + no);
			
			sql = new StringBuffer ("DELETE FROM A_Depreciation_Exp "
					  + "WHERE A_Entry_Type = '"+ m_EntryType + "'" 
					  + " AND Processed='Y'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (),get_TrxName());
			log.info ("doIt - Delete Old Impored =" + no);				
			
		}

		
		//	****	Copy from Workfile to Entry File	****
		
		log.info("doIt - Depreciation_Build_Entry_ID=" + p_Depreciation_Entry_ID);
		if (p_Depreciation_Entry_ID == 0)
			throw new IllegalArgumentException("No Record");		
		//		
		
		//X_A_Depreciation_Build DepBuild = new X_A_Depreciation_Build (getCtx(), p_Depreciation_Entry_ID);
		
		String sqlst = null;
		
		sqlst = "SELECT A_DEPRECIATION_EXP.AD_CLIENT_ID, A_DEPRECIATION_EXP.CREATED, A_DEPRECIATION_EXP.CREATEDBY, "
		  + "A_DEPRECIATION_EXP.UPDATED, A_DEPRECIATION_EXP.UPDATEDBY, A_DEPRECIATION_EXP.DESCRIPTION, "
		  + "A_DEPRECIATION_EXP.AD_ORG_ID, A_DEPRECIATION_EXP.A_ACCOUNT_NUMBER,A_DEPRECIATION_ENTRY.C_CURRENCY_ID, "
		  + "A_DEPRECIATION_EXP.EXPENSE, A_DEPRECIATION_EXP.A_ASSET_ID, A_DEPRECIATION_EXP.ISDEPRECIATED, "
		  + "A_DEPRECIATION_EXP.PROCESSED, A_DEPRECIATION_EXP.A_DEPRECIATION_EXP_ID, A_DEPRECIATION_EXP.A_Period," 
		  +	"A_DEPRECIATION_ENTRY.DATEACCT, A_DEPRECIATION_ENTRY.POSTINGTYPE, A_DEPRECIATION_ENTRY.A_ENTRY_TYPE,"
		  + "A_DEPRECIATION_ENTRY.PROCESSED AS ENTRY_PROCESS,A_DEPRECIATION_ENTRY.GL_CATEGORY_ID, "
		  + "A_DEPRECIATION_ENTRY.C_DOCTYPE_ID,A_DEPRECIATION_ENTRY.C_ACCTSCHEMA_ID, A_DEPRECIATION_ENTRY.A_DEPRECIATION_ENTRY_ID "
		  + "FROM A_DEPRECIATION_EXP, A_DEPRECIATION_ENTRY "
		  + "WHERE A_DEPRECIATION_EXP.PROCESSED = 'N' AND A_DEPRECIATION_ENTRY.PROCESSED = 'N' AND "		  
		  + "A_DEPRECIATION_EXP.A_PERIOD = A_DEPRECIATION_ENTRY.C_PERIOD_ID "
		  + "AND A_DEPRECIATION_ENTRY.POSTINGTYPE = A_DEPRECIATION_EXP.POSTINGTYPE AND "
		  + "A_DEPRECIATION_EXP.A_ENTRY_TYPE = A_DEPRECIATION_ENTRY.A_ENTRY_TYPE";
		  
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sqlst, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE,get_TrxName());		
		ResultSet rs = null;		
		try {
			int v_FirstTime = 0;
			String v_C_BatchNo = "";
			BigDecimal v_AMTSOURCEDR ;
			BigDecimal v_AMTSOURCECR ;
			int v_ID_START = 10;
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				
				X_I_FAJournal FAInsert = new X_I_FAJournal (getCtx (), 0, get_TrxName());			
				if  (v_FirstTime == 0)
				{
					final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
					v_C_BatchNo = documentNoFactory.forDocType(rs.getInt("C_DOCTYPE_ID"), true)
							.setTrxName(get_TrxName())
							.setPO(FAInsert)
							.setFailOnError(false)
							.build();
					
					v_FirstTime = 1;
				}

				String v_Line_Description = rs.getString("DESCRIPTION") + " - FA # " + rs.getInt("A_ASSET_ID");			  
			  
				if (rs.getBigDecimal("EXPENSE").compareTo(new BigDecimal("0.0"))== 1)
				{
					v_AMTSOURCEDR = rs.getBigDecimal("EXPENSE") ;
					v_AMTSOURCECR = Env.ZERO;
				}
				else
				{
					v_AMTSOURCECR = (rs.getBigDecimal("EXPENSE").multiply(new BigDecimal("-1.0")));
					v_AMTSOURCEDR = Env.ZERO;
				}		 
			  
				FAInsert.setI_IsImported(false);			  
				FAInsert.setProcessed(false);
				FAInsert.setBatchDocumentNo(v_C_BatchNo);
				FAInsert.setBatchDescription (rs.getString("DESCRIPTION"));
				FAInsert.setPostingType(rs.getString("POSTINGTYPE"));
				FAInsert.setC_AcctSchema_ID(rs.getInt("C_ACCTSCHEMA_ID"));
				FAInsert.setC_DocType_ID( rs.getInt("C_DOCTYPE_ID"));
				FAInsert.setGL_Category_ID(rs.getInt("GL_CATEGORY_ID"));
				FAInsert.setLine( v_ID_START);
				FAInsert.setDateAcct (rs.getTimestamp("DATEACCT"));
				FAInsert.setC_Period_ID(rs.getInt("A_Period"));
				FAInsert.setDescription ( v_Line_Description);
				FAInsert.setAmtSourceDr ( v_AMTSOURCEDR);
				FAInsert.setAmtSourceCr ( v_AMTSOURCECR);
				FAInsert.setC_Currency_ID ( rs.getInt("C_CURRENCY_ID"));
				FAInsert.setQty (Env.ZERO);
 		  	  	FAInsert.setC_ValidCombination_ID ( rs.getInt("A_ACCOUNT_NUMBER"));
 		  	  	FAInsert.setA_Asset_ID ( rs.getInt("A_ASSET_ID"));	
 		  	  	FAInsert.setIsDepreciated ( rs.getString("ISDEPRECIATED"));

 		  	  	v_ID_START = v_ID_START+10;
 		  	  	FAInsert.save();

 		  	  	String sql4 = "UPDATE A_DEPRECIATION_EXP SET PROCESSED = 'Y' "
			  		+ " WHERE A_DEPRECIATION_EXP_ID = " + rs.getInt("A_DEPRECIATION_EXP_ID");
 		  	  	no = DB.executeUpdate (sql4,get_TrxName());
			  
 		  	  	sql4 = new String ("UPDATE A_DEPRECIATION_ENTRY SET PROCESSED = 'Y', ISACTIVE = 'N' "
			  		+ " WHERE A_DEPRECIATION_ENTRY_ID = " + rs.getInt("A_DEPRECIATION_ENTRY_ID"));
 		  	  	no = DB.executeUpdate (sql4,get_TrxName());			 
			}
		} 
		catch (Exception e)
		{
			log.info("ImportFAJournal2"+ e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		//	Set IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, now()),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, now()),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = NULL,"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Reset=" + no);

		//	Set Client from Name
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET AD_Client_ID=(SELECT c.AD_Client_ID FROM AD_Client c WHERE c.Value=i.ClientValue) "
			+ "WHERE (AD_Client_ID IS NULL OR AD_Client_ID=0) AND ClientValue IS NOT NULL"
			+ " AND I_IsImported<>'Y'");
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Client from Value=" + no);

		//	Set Client, Doc Org, AcctSchema, DatAcct
		sql = new StringBuffer ("UPDATE I_FAJournal "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (m_AD_Client_ID).append ("),"
			  + " AD_OrgDoc_ID = COALESCE (AD_OrgDoc_ID,").append (m_AD_Org_ID).append ("),");
		if (m_C_AcctSchema_ID != 0)
			sql.append(" C_AcctSchema_ID = COALESCE (C_AcctSchema_ID,").append (m_C_AcctSchema_ID).append ("),");
		if (m_DateAcct != null)
			sql.append(" DateAcct = COALESCE (DateAcct,").append (DB.TO_DATE(m_DateAcct)).append ("),");
		sql.append(" Updated = COALESCE (Updated, now()) "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Client/DocOrg/Default=" + no);

		//	Error Doc Org
		sql = new StringBuffer ("UPDATE I_FAJournal o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Doc Org, '"
			+ "WHERE (AD_OrgDoc_ID IS NULL OR AD_OrgDoc_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Doc Org=" + no);

		//	Set AcctSchema
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_AcctSchema_ID=(SELECT a.C_AcctSchema_ID FROM C_AcctSchema a"
			+ " WHERE i.AcctSchemaName=a.Name AND i.AD_Client_ID=a.AD_Client_ID) "
			+ "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set AcctSchema from Name=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_AcctSchema_ID=(SELECT c.C_AcctSchema1_ID FROM AD_ClientInfo c WHERE c.AD_Client_ID=i.AD_Client_ID) "
			+ "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set AcctSchema from Client=" + no);
		//	Error AcctSchema
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid AcctSchema, '"
			+ "WHERE (C_AcctSchema_ID IS NULL OR C_AcctSchema_ID=0"
			+ " OR NOT EXISTS (SELECT * FROM C_AcctSchema a WHERE i.AD_Client_ID=a.AD_Client_ID))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid AcctSchema=" + no);

		//	Set DateAcct (mandatory)
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET DateAcct=now() "
			+ "WHERE DateAcct IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set DateAcct=" + no);

		//	Document Type
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_DocType_ID=(SELECT d.C_DocType_ID FROM C_DocType d"
			+ " WHERE d.Name=i.DocTypeName AND d.DocBaseType='GLJ' AND i.AD_Client_ID=d.AD_Client_ID) "
			+ "WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set DocType=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid DocType, '"
			+ "WHERE (C_DocType_ID IS NULL OR C_DocType_ID=0"
			+ " OR NOT EXISTS (SELECT * FROM C_DocType d WHERE i.AD_Client_ID=d.AD_Client_ID AND d.DocBaseType='GLJ'))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid DocType=" + no);

		//	GL Category
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET GL_Category_ID=(SELECT c.GL_Category_ID FROM GL_Category c"
			+ " WHERE c.Name=i.CategoryName AND i.AD_Client_ID=c.AD_Client_ID) "
			+ "WHERE GL_Category_ID IS NULL AND CategoryName IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set DocType=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Category, '"
			+ "WHERE (GL_Category_ID IS NULL OR GL_Category_ID=0)"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Category=" + no);

		//	Set Currency
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_Currency_ID=(SELECT c.C_Currency_ID FROM C_Currency c"
			+ " WHERE c.ISO_Code=i.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Currency from ISO=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_Currency_ID=(SELECT a.C_Currency_ID FROM C_AcctSchema a"
			+ " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.AD_Client_ID=i.AD_Client_ID)"
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Default Currency=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency, '"
			+ "WHERE (C_Currency_ID IS NULL OR C_Currency_ID=0)"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Currency=" + no);

//		Set Conversion Type
			sql = new StringBuffer ("UPDATE I_FAJournal i " 
				+ "SET ConversionTypeValue='S' "
				+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NULL"
				+ " AND I_IsImported='N'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (),get_TrxName());
			log.info ("doIt - Set CurrencyType Value to Spot =" + no);
			sql = new StringBuffer ("UPDATE I_FAJournal i " 
				+ "SET C_ConversionType_ID=(SELECT c.C_ConversionType_ID FROM C_ConversionType c"
				+ " WHERE c.Value=i.ConversionTypeValue AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (),get_TrxName());
			log.info ("doIt - Set CurrencyType from Value=" + no);
			sql = new StringBuffer ("UPDATE I_FAJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid CurrencyType, '"
				+ "WHERE (C_ConversionType_ID IS NULL OR C_ConversionType_ID=0) AND ConversionTypeValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (),get_TrxName());
			if (no != 0)
				log.info ("doIt - Invalid CurrencyTypeValue=" + no);



		//	Set/Overwrite Home Currency Rate
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET CurrencyRate=1"
			+ "WHERE EXISTS (SELECT * FROM C_AcctSchema a"
			+ " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.C_Currency_ID=i.C_Currency_ID)"
			+ " AND C_Currency_ID IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Home CurrencyRate=" + no);
		//	Set Currency Rate
		sql = new StringBuffer ("UPDATE I_FAJournal i "
		   + "SET CurrencyRate=(SELECT r.MultiplyRate FROM C_Conversion_Rate r, C_AcctSchema s"
		   + " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
		   + " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
		   + " AND r.AD_Client_ID=i.AD_Client_ID AND r.AD_Org_ID=i.AD_OrgDoc_ID"
		   + " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
		   + " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo AND ROWNUM=1"
	   //	ORDER BY ValidFrom DESC
		   + ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
		   + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Org Rate=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET CurrencyRate=(SELECT r.MultiplyRate FROM C_Conversion_Rate r, C_AcctSchema s"
			+ " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
			+ " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
			+ " AND r.AD_Client_ID=i.AD_Client_ID"
			+ " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
			+ " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo AND ROWNUM=1"
		//	ORDER BY ValidFrom DESC
			+ ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Client Rate=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Rate, '"
			+ "WHERE CurrencyRate IS NULL OR CurrencyRate=0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - No Rate=" + no);

		//	Set Period
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_Period_ID=(SELECT p.C_Period_ID FROM C_Period p"
			+ " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
			+ " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID)"
			+ " WHERE c.AD_Client_ID=i.AD_Client_ID"
			+ " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.PeriodType='S' AND ROWNUM=1) "
			+ "WHERE C_Period_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Period=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Period, '"
			+ "WHERE C_Period_ID IS NULL OR C_Period_ID<>"
			+ "(SELECT C_Period_ID FROM C_Period p"
			+ " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
			+ " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID) "
			+ " WHERE c.AD_Client_ID=i.AD_Client_ID"
			+ " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.PeriodType='S' AND ROWNUM=1)"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Period=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Period Closed, ' "
			+ "WHERE C_Period_ID IS NOT NULL AND NOT EXISTS"
			+ " (SELECT * FROM C_PeriodControl pc WHERE pc.C_Period_ID=i.C_Period_ID AND DocBaseType='GLJ' AND PeriodStatus='O') "
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Period Closed=" + no);

		//	Posting Type
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET PostingType='A' "
			+ "WHERE PostingType IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Actual PostingType=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid PostingType, ' "
			+ "WHERE PostingType IS NULL OR NOT EXISTS"
			+ " (SELECT * FROM AD_Ref_List r WHERE r.AD_Reference_ID=125 AND i.PostingType=r.Value)"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid PostingTypee=" + no);


		//	** Account Elements (optional) **
		//	(C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0)

		//	Set Org from Name
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET AD_Org_ID=(SELECT o.AD_Org_ID FROM AD_Org o"
			+ " WHERE o.Value=i.OrgValue AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID) "
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'");
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Org from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET AD_Org_ID=AD_OrgDoc_ID "
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NULL AND AD_OrgDoc_ID IS NOT NULL AND AD_OrgDoc_ID<>0"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Org from Doc Org=" + no);
		//	Error Org
		sql = new StringBuffer ("UPDATE I_FAJournal o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Org=" + no);

		//	Set Account
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET Account_ID=(SELECT ev.C_ElementValue_ID FROM C_ElementValue ev"
			+ " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
			+ " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
			+ " WHERE ev.Value=i.AccountValue AND ev.IsSummary='N'"
			+ " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID AND ROWNUM=1) "
			+ "WHERE Account_ID IS NULL AND AccountValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Account from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account, '"
			+ "WHERE (Account_ID IS NULL OR Account_ID=0)"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Account=" + no);

		//	Set BPartner
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_BPartner_ID=(SELECT bp.C_BPartner_ID FROM C_BPartner bp"
			+ " WHERE bp.Value=i.BPartnerValue AND bp.IsSummary='N' AND i.AD_Client_ID=bp.AD_Client_ID) "
			+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set BPartner from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner, '"
			+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid BPartner=" + no);

		//	Set Product
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET M_Product_ID=(SELECT p.M_Product_ID FROM M_Product p"
			+ " WHERE (p.Value=i.ProductValue OR p.UPC=i.UPC OR p.SKU=i.SKU)"
			+ " AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID AND ROWNUM=1) "
			+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Product from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, '"
			+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Product=" + no);

		//	Set Project
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET C_Project_ID=(SELECT p.C_Project_ID FROM C_Project p"
			+ " WHERE p.Value=i.ProjectValue AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID) "
			+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set Project from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Project, '"
			+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid Project=" + no);

		//	Set TrxOrg
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET AD_OrgTrx_ID=(SELECT o.AD_Org_ID FROM AD_Org o"
			+ " WHERE o.Value=i.OrgValue AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID) "
			+ "WHERE AD_OrgTrx_ID IS NULL AND OrgTrxValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set OrgTrx from Value=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid OrgTrx, '"
			+ "WHERE AD_OrgTrx_ID IS NULL AND OrgTrxValue IS NOT NULL"
			+ " AND (C_ValidCombination_ID IS NULL OR C_ValidCombination_ID=0) AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Invalid OrgTrx=" + no);


		//	Source Amounts
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET AmtSourceDr = 0 "
			+ "WHERE AmtSourceDr IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),null);
		log.info ("doIt - Set 0 Source Dr=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET AmtSourceCr = 0 "
			+ "WHERE AmtSourceCr IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Set 0 Source Cr=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Source Balance, ' "
			+ "WHERE (AmtSourceDr-AmtSourceCr)=0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Zero Source Balance=" + no);

		//	Accounted Amounts (Only if No Error)
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET AmtAcctDr = ROUND(AmtSourceDr * CurrencyRate, 2) "	//	HARDCODED rounding
			+ "WHERE AmtAcctDr IS NULL OR AmtAcctDr=0"
			+ " AND I_IsImported='N'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Calculate Acct Dr=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET AmtAcctCr = ROUND(AmtSourceCr * CurrencyRate, 2) "
			+ "WHERE AmtAcctCr IS NULL OR AmtAcctCr=0"
			+ " AND I_IsImported='N'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		log.info ("doIt - Calculate Acct Cr=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Acct Balance, ' "
			+ "WHERE (AmtSourceDr-AmtSourceCr)<>0 AND (AmtAcctDr-AmtAcctCr)=0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Zero Acct Balance=" + no);
		sql = new StringBuffer ("UPDATE I_FAJournal i "
			+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Check Acct Balance, ' "
			+ "WHERE ABS(AmtAcctDr-AmtAcctCr)>100000000"	//	100 mio
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (),get_TrxName());
		if (no != 0)
			log.info ("doIt - Chack Acct Balance=" + no);


		/*********************************************************************/

		//	Get Balance
		sql = new StringBuffer ("SELECT SUM(AmtSourceDr)-SUM(AmtSourceCr), SUM(AmtAcctDr)-SUM(AmtAcctCr) "
			+ "FROM I_FAJournal "
			+ "WHERE I_IsImported='N'").append (clientCheck);
		pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(),get_TrxName());
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				BigDecimal source = rs.getBigDecimal(1);
				BigDecimal acct = rs.getBigDecimal(2);
				if (source != null && source.compareTo(Env.ZERO) == 0
					&& acct != null && acct.compareTo(Env.ZERO) == 0)
					log.info ("doIt - Import Balance = 0");
				else
					log.info("doIt - Balance Source=" + source  + ", Acct=" + acct);
				if (source != null)
					addLog (0, null, source, "@AmtSourceDr@ - @AmtSourceCr@");
				if (acct != null)
					addLog (0, null, acct, "@AmtAcctDr@- @AmtAcctCr@");
			}
		}
		catch (SQLException ex)
		{
			log.info ("doIt - get balance"+ ex);
		}
		finally
		{
		  DB.close(rs, pstmt);
		  rs = null; pstmt = null;
		}

		//	Count Errors
		int errors = DB.getSQLValue(get_TrxName(), "SELECT COUNT(*) FROM I_FAJournal WHERE I_IsImported NOT IN ('Y','N')" + clientCheck);

		if (errors != 0)
		{
			if (m_IsValidateOnly || m_IsImportOnlyNoErrors)
				throw new Exception ("@Errors@=" + errors);
		}
		else if (m_IsValidateOnly)
			return "@Errors@=" + errors;

		log.info("doIt - Validation Errors=" + errors);

		/*********************************************************************/

		int noInsert = 0;
		int noInsertJournal = 0;
		int noInsertLine = 0;
        //int oldA_Asset_Id = 0;

		MJournalBatch batch = null;		//	Change Batch per Batch DocumentNo
		String BatchDocumentNo = "";
		MJournal journal = null;
		String JournalDocumentNo = "";
		Timestamp DateAcct = null;

		//	Go through Journal Records
		sql = new StringBuffer ("SELECT * FROM I_FAJournal "
			+ "WHERE I_IsImported='N'").append (clientCheck)
			.append(" ORDER BY BatchDocumentNo, JournalDocumentNo, C_AcctSchema_ID, PostingType, C_DocType_ID, GL_Category_ID, C_Currency_ID, TRUNC(DateAcct), Line, I_FAJournal_ID");
		try
		{
			pstmt = DB.prepareStatement (sql.toString (),get_TrxName());
			rs = pstmt.executeQuery ();
			//
			while (rs.next())
			{
				MXIFAJournal imp = new MXIFAJournal (getCtx (), rs, get_TrxName());

				//	Batch
				String impBatchDocumentNo = imp.getBatchDocumentNo();
				if (impBatchDocumentNo == null)
					impBatchDocumentNo = "";
				if (batch == null || !BatchDocumentNo.equals(impBatchDocumentNo))
				{
					BatchDocumentNo = impBatchDocumentNo;	//	cannot compare real DocumentNo
					batch = new MJournalBatch (getCtx(), 0, get_TrxName());
					batch.setClientOrg(imp.getAD_Client_ID(), imp.getAD_OrgDoc_ID());
					if (imp.getBatchDocumentNo() != null && imp.getBatchDocumentNo().length() > 0)
						batch.setDocumentNo (imp.getBatchDocumentNo());
					batch.setC_DocType_ID(imp.getC_DocType_ID());
					batch.setDateDoc(imp.getDateAcct());
					batch.setDateAcct(imp.getDateAcct());
					batch.setGL_Category_ID(imp.getGL_Category_ID());
					batch.setC_Currency_ID(imp.getC_Currency_ID());
					String description = imp.getBatchDescription();
					if (description == null || description.length() == 0)
						description = "*Import-";
					else
						description += " *Import-";
					description += new Timestamp(System.currentTimeMillis());
					batch.setDescription(description);
					if (!batch.save())
					{
						log.info("doIt - Batch not saved");
						break;
					}
					noInsert++;
					journal = null;
				}
				//	Journal
				String impJournalDocumentNo = imp.getJournalDocumentNo();
				if (impJournalDocumentNo == null)
					impJournalDocumentNo = "";
				Timestamp impDateAcct = TimeUtil.getDay(imp.getDateAcct());
				if (journal == null || !JournalDocumentNo.equals(impJournalDocumentNo)
					|| journal.getC_AcctSchema_ID() != imp.getC_AcctSchema_ID()
					|| journal.getC_DocType_ID() != imp.getC_DocType_ID()
					|| journal.getGL_Category_ID() != imp.getGL_Category_ID()
					|| !journal.getPostingType().equals(imp.getPostingType())
					|| journal.getC_Currency_ID() != imp.getC_Currency_ID()
					|| !impDateAcct.equals(DateAcct)
				)
				{
					JournalDocumentNo = impJournalDocumentNo;	//	cannot compare real DocumentNo
					DateAcct = impDateAcct;
					journal = new MJournal (getCtx(), 0, get_TrxName());
					journal.setGL_JournalBatch_ID(batch.getGL_JournalBatch_ID());
					journal.setClientOrg(imp.getAD_Client_ID(), imp.getAD_OrgDoc_ID());
					//
					String description = imp.getBatchDescription();
					if (description == null || description.length() == 0)
						description = "(Import)";
					journal.setDescription (description);
					if (imp.getJournalDocumentNo() != null && imp.getJournalDocumentNo().length() > 0)
						journal.setDocumentNo (imp.getJournalDocumentNo());			
					//
					journal.setC_AcctSchema_ID (imp.getC_AcctSchema_ID());
					journal.setC_DocType_ID (imp.getC_DocType_ID());
					journal.setGL_Category_ID (imp.getGL_Category_ID());
					journal.setPostingType (imp.getPostingType());
					journal.setC_ConversionType_ID (imp.getC_ConversionType_ID());
					//					
					journal.setCurrency (imp.getC_Currency_ID(), imp.getC_ConversionType_ID(), imp.getCurrencyRate());				
					//
					journal.setDateAcct(imp.getDateAcct());		
					journal.setDateDoc (imp.getDateAcct());
					//
					if (!journal.save())
					{
						log.info("doIt - Journal not saved");
						break;
					}
					noInsertJournal++;
				}

				//	Lines
				MJournalLine line = new MJournalLine (journal);
				//
				line.setDescription(imp.getDescription());				
				line.setCurrency (imp.getC_Currency_ID(), imp.getC_ConversionType_ID(), imp.getCurrencyRate());
//				Set/Get Account Combination
				if (imp.getC_ValidCombination_ID() == 0)
				{
					MAccount acct = MAccount.get(getCtx(), imp.getAD_Client_ID(), imp.getAD_Org_ID(), 
						imp.getC_AcctSchema_ID(), imp.getAccount_ID(),imp.getC_SubAcct_ID(),
						//imp.getC_AcctSchema_ID(), imp.getAccount_ID(),
						imp.getM_Product_ID(), imp.getC_BPartner_ID(), imp.getAD_OrgTrx_ID(),
						imp.getC_LocFrom_ID(), imp.getC_LocTo_ID(), imp.getC_SalesRegion_ID(),
						imp.getC_Project_ID(), imp.getC_Campaign_ID(), imp.getC_Activity_ID(),
						imp.getUser1_ID(), imp.getUser2_ID(),imp.getUserElement1_ID(),imp.getUserElement2_ID());					
					    //imp.getUser1_ID(), imp.getUser2_ID());
					if (acct != null && acct.get_ID() == 0)
						acct.save();
					if (acct == null || acct.get_ID() == 0)
					{
						imp.setI_ErrorMsg("ERROR creating Account");
						imp.setI_IsImported(false);
						imp.save();
						continue;
					}
					else
					{
						// TODO: C_ValidCombination_ID is no longer a column because we have DR/CR accounts
						//line.setC_ValidCombination_ID(acct.get_ID());
						imp.setC_ValidCombination_ID(acct.get_ID());
					}
				}
				else
				{
					// TODO: C_ValidCombination_ID is no longer a column because we have DR/CR accounts
					// line.setC_ValidCombination_ID (imp.getC_ValidCombination_ID());
				}
				//
				line.setLine (imp.getLine());
				line.setAmtSourceCr (imp.getAmtSourceCr());
				line.setAmtSourceDr (imp.getAmtSourceDr());
				line.setAmtAcct (imp.getAmtAcctDr(), imp.getAmtAcctCr());
				line.setDateAcct (imp.getDateAcct());
				//
				line.setC_UOM_ID(imp.getC_UOM_ID());
				line.setQty(imp.getQty());
				//
				if (line.save())
				{
					imp.setGL_JournalBatch_ID(batch.getGL_JournalBatch_ID());
					imp.setGL_Journal_ID(journal.getGL_Journal_ID());
					imp.setGL_JournalLine_ID(line.getGL_JournalLine_ID());
					imp.setI_IsImported(true);
					imp.setProcessed(true);
                    sql = new StringBuffer ("UPDATE A_DEPRECIATION_WORKFILE "
                         +"SET A_ACCUMULATED_DEPR = A_ACCUMULATED_DEPR + ")
                         .append(imp.getExpenseDr()).append(" - ").append(imp.getExpenseCr())
                         .append(", A_PERIOD_POSTED = A_CURRENT_PERIOD")
						 .append(",	ASSETDEPRECIATIONDATE = ").append (DB.TO_DATE(imp.getDateAcct()))
                         .append(" WHERE A_ASSET_ID = ").append(imp.getA_Asset_ID())
                         .append(" AND ISACTIVE = '").append(imp.getIsDepreciated())
                         .append("' AND POSTINGTYPE = '").append(imp.getPostingType())
                         .append("'");
                    no = DB.executeUpdate(sql.toString(),get_TrxName());
                    log.info("doIt - SET Accumulated Depreciation =" + no);

                    //	Copy Expense Worktable to Import Worktable
                    String impgetIsDepreciated = imp.getIsDepreciated();
                    //impgetIsDepreciated = impgetIsDepreciated + "STOP";

                    if(impgetIsDepreciated.equals("Y"))
                    {
                    	cs = DB.prepareCall("{call AD_Sequence_Next(?,?,?)}");
	                    cs.setString(1, "A_Asset_Change");
	                    cs.setInt(2,imp.getAD_Client_ID());
	                    cs.registerOutParameter(3, java.sql.Types.INTEGER);
	                    cs.execute();
	
	
	                    MAssetChange assetChange = new MAssetChange (getCtx(), 0, get_TrxName());
	                    assetChange.setA_Asset_Change_ID(cs.getInt(3));
	                    assetChange.set_ValueOfColumn("AD_CLIENT_ID", imp.getAD_Client_ID());
	                    assetChange.setAD_Org_ID(imp.getAD_OrgDoc_ID());
	                    assetChange.set_ValueOfColumn("CREATEDBY", getAD_User_ID());
	                    assetChange.set_ValueOfColumn("UPDATEDBY", getAD_User_ID());
	                    assetChange.setChangeType("DEP");
	                    assetChange.setChangeAmt(imp.getAmtAcctTotal());
	                    assetChange.setA_Asset_ID(imp.getA_Asset_ID());
	                    assetChange.setTextDetails(imp.getDescription());
	                    assetChange.setC_ValidCombination_ID(imp.getC_ValidCombination_ID());
	                    assetChange.setDateAcct(imp.getDateAcct());
	                    assetChange.setPostingType(imp.getPostingType());
	                    assetChange.saveEx();
	                      
                    }
	                else if(impgetIsDepreciated.equals("B"))
	                {
	                	cs = DB.prepareCall("{call AD_Sequence_Next(?,?,?)}");
	                    cs.setString(1, "A_Asset_Change");
	                    cs.setInt(2,imp.getAD_Client_ID());
	                    cs.registerOutParameter(3, java.sql.Types.INTEGER);
	                    cs.execute();
	
	                    MAssetChange assetChange = new MAssetChange (getCtx(), 0, get_TrxName());
	                    assetChange.setA_Asset_Change_ID(cs.getInt(3));
	                    assetChange.set_ValueOfColumn("AD_CLIENT_ID", imp.getAD_Client_ID());
	                    assetChange.setAD_Org_ID(imp.getAD_OrgDoc_ID());
	                    assetChange.set_ValueOfColumn("CREATEDBY", getAD_User_ID());
	                    assetChange.set_ValueOfColumn("UPDATEDBY", getAD_User_ID());
	                    assetChange.setChangeType("BUD");
	                    assetChange.setChangeAmt(imp.getAmtAcctTotal());
	                    assetChange.setA_Asset_ID(imp.getA_Asset_ID());
	                    assetChange.setTextDetails(imp.getDescription());
	                    assetChange.setC_ValidCombination_ID(imp.getC_ValidCombination_ID());
	                    assetChange.setDateAcct(imp.getDateAcct());
	                    assetChange.setPostingType(imp.getPostingType());
	                    assetChange.saveEx();
	                      
	                }


					if (imp.save())
						noInsertLine++;
				}
			}
		}
		catch (Exception e)
		{
			log.severe("doIt"+ e);
		}
		//	clean up
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_FAJournal "
			+ "SET I_IsImported='N', Updated=now() "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(),get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@GL_JournalBatch_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertJournal), "@GL_Journal_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@GL_JournalLine_ID@: @Inserted@");
        //int test = DB.getSQLValue("SELECT 2*2 from dual");

                
		String sql3 = null;
		
		sql3 = "SELECT A_ASSET.A_ASSET_ID, A_ASSET.ISFULLYDEPRECIATED, A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED, "
		+ "A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD "
		+ "FROM A_DEPRECIATION_WORKFILE,A_ASSET "
		+ "WHERE A_ASSET.A_ASSET_ID = A_DEPRECIATION_WORKFILE.A_ASSET_ID "
		+ "AND A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED = A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD ";
		
		try
		{
			pstmt = DB.prepareStatement(sql3,get_TrxName());		
			rs = pstmt.executeQuery();
			while (rs.next()){
				
				String sql4 = "UPDATE A_ASSET SET ISFULLYDEPRECIATED = 'Y' WHERE A_Asset_ID = " + rs.getInt("A_ASSET_ID");
				no = DB.executeUpdate (sql4,get_TrxName());
			}
	 	}
		catch (Exception e)
		{
			log.info("Post Depreciation"+ e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	doIt

}	//	ImportFAJournal2
