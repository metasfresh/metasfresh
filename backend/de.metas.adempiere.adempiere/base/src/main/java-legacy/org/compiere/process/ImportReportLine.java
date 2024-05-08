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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *	Import ReportLines from I_ReportLine
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportReportLine.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportReportLine extends JavaProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/** Default Report Line Set			*/
	private int				m_PA_ReportLineSet_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;

	/** Effective						*/
	private Timestamp		m_DateValue = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("PA_ReportLineSet_ID"))
				m_PA_ReportLineSet_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		if (m_DateValue == null)
			m_DateValue = new Timestamp (System.currentTimeMillis());
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_ReportLine "
				+ "WHERE I_IsImported='Y'").append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted Old Imported =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
			+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
			+ " IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, now()),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, now()),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = ' ',"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Reset=" + no);

		//	ReportLineSetName (Default)
		if (m_PA_ReportLineSet_ID != 0)
		{
			sql = new StringBuffer ("UPDATE I_ReportLine i "
				+ "SET ReportLineSetName=(SELECT Name FROM PA_ReportLineSet r"
				+ " WHERE PA_ReportLineSet_ID=").append(m_PA_ReportLineSet_ID).append(" AND i.AD_Client_ID=r.AD_Client_ID) "
				+ "WHERE ReportLineSetName IS NULL AND PA_ReportLineSet_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Set ReportLineSetName Default=" + no);
		}
		//	Set PA_ReportLineSet_ID
		sql = new StringBuffer ("UPDATE I_ReportLine i "
			+ "SET PA_ReportLineSet_ID=(SELECT PA_ReportLineSet_ID FROM PA_ReportLineSet r"
			+ " WHERE i.ReportLineSetName=r.Name AND i.AD_Client_ID=r.AD_Client_ID) "
			+ "WHERE PA_ReportLineSet_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PA_ReportLineSet_ID=" + no);
		//
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid ReportLineSet, ' "
			+ "WHERE PA_ReportLineSet_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid ReportLineSet=" + no);

		//	Ignore if there is no Report Line Name or ID
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Ignored=NoLineName, ' "
			+ "WHERE PA_ReportLine_ID IS NULL AND Name IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid LineName=" + no);

		//	Validate ElementValue
		sql = new StringBuffer ("UPDATE I_ReportLine i "
			+ "SET C_ElementValue_ID=(SELECT C_ElementValue_ID FROM C_ElementValue e"
			+ " WHERE i.ElementValue=e.Value AND i.AD_Client_ID=e.AD_Client_ID) "
			+ "WHERE C_ElementValue_ID IS NULL AND ElementValue IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set C_ElementValue_ID=" + no);
		
		//	Validate C_ElementValue_ID
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid ElementValue, ' "
			+ "WHERE C_ElementValue_ID IS NULL AND LineType<>'C'" // MReportLine.LINETYPE_Calculation
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid AccountType=" + no);

		//	Set SeqNo
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET SeqNo=I_ReportLine_ID "
			+ "WHERE SeqNo IS NULL"
			+ " AND I_IsImported='N'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set SeqNo Default=" + no);

		//	Copy/Sync from first Row of Line
		sql = new StringBuffer ("UPDATE I_ReportLine i "
			+ "SET (Description, SeqNo, IsSummary, IsPrinted, LineType, CalculationType, AmountType, PAAmountType, PAPeriodType, PostingType)="
			+ " (SELECT Description, SeqNo, IsSummary, IsPrinted, LineType, CalculationType, AmountType, PAAmountType, PAPeriodType, PostingType"
			+ " FROM I_ReportLine ii WHERE i.Name=ii.Name AND i.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID"
			+ " AND ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
			+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID)) "
			+ "WHERE EXISTS (SELECT *"
			+ " FROM I_ReportLine ii WHERE i.Name=ii.Name AND i.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID"
			+ " AND ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
			+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID))"
			+ " AND I_IsImported='N'").append(clientCheck);		//	 not if previous error
		no = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		log.debug("Sync from first Row of Line=" + no);

		//	Validate IsSummary - (N) Y
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET IsSummary='N' "
			+ "WHERE IsSummary IS NULL OR IsSummary NOT IN ('Y','N')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set IsSummary Default=" + no);

		//	Validate IsPrinted - (Y) N
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET IsPrinted='Y' "
			+ "WHERE IsPrinted IS NULL OR IsPrinted NOT IN ('Y','N')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set IsPrinted Default=" + no);

		//	Validate Line Type - (S) C
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET LineType='S' "
			+ "WHERE LineType IS NULL OR LineType NOT IN ('S','C')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set LineType Default=" + no);

		//	Validate Optional Calculation Type - A P R S
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid CalculationType, ' "
			+ "WHERE CalculationType IS NOT NULL AND CalculationType NOT IN ('A','P','R','S')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid CalculationType=" + no);

		//	Convert Optional Amount Type to PAAmount Type and PAPeriodType
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET PAAmountType = substr(AmountType,1,1), PAPeriodType = substr(AmountType,1,2) "
			+ "WHERE AmountType IS NOT NULL AND (PAAmountType IS NULL OR PAPeriodType IS NULL) "
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Converted AmountType=" + no);
		
		//		Validate Optional Amount Type -
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid PAAmountType, ' "
			+ "WHERE PAAmountType IS NOT NULL AND UPPER(AmountType) NOT IN ('B','C','D','Q','S','R')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid AmountType=" + no);
		
		//		Validate Optional Period Type -
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid PAPeriodType, ' "
			+ "WHERE PAPeriodType IS NOT NULL AND UPPER(AmountType) NOT IN ('P','Y','T','N')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid PeriodType=" + no);

		//	Validate Optional Posting Type - A B E S R
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid CalculationType, ' "
			+ "WHERE PostingType IS NOT NULL AND PostingType NOT IN ('A','B','E','S','R')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Invalid PostingType=" + no);

		//	Set PA_ReportLine_ID
		sql = new StringBuffer ("UPDATE I_ReportLine i "
			+ "SET PA_ReportLine_ID=(SELECT MAX(PA_ReportLine_ID) FROM PA_ReportLine r"
			+ " WHERE i.Name=r.Name AND i.PA_ReportLineSet_ID=r.PA_ReportLineSet_ID) "
			+ "WHERE PA_ReportLine_ID IS NULL AND PA_ReportLineSet_ID IS NOT NULL"
			+ " AND I_IsImported='N'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PA_ReportLine_ID=" + no);

		commitEx();
		
		//	-------------------------------------------------------------------
		int noInsertLine = 0;
		int noUpdateLine = 0;

		//	****	Create Missing ReportLines
		sql = new StringBuffer ("SELECT DISTINCT PA_ReportLineSet_ID, Name "
			+ "FROM I_ReportLine "
			+ "WHERE I_IsImported='N' AND PA_ReportLine_ID IS NULL"
			+ " AND I_IsImported='N'").append(clientCheck);
		try
		{
			//	Insert ReportLine
			PreparedStatement pstmt_insertLine = DB.prepareStatement
				("INSERT INTO PA_ReportLine "
				+ "(PA_ReportLine_ID,PA_ReportLineSet_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "Name,SeqNo,IsPrinted,IsSummary,LineType)"
				+ "SELECT ?,PA_ReportLineSet_ID,"
				+ "AD_Client_ID,AD_Org_ID,'Y',now(),CreatedBy,now(),UpdatedBy,"
				+ "Name,SeqNo,IsPrinted,IsSummary,LineType "
				//jz + "FROM I_ReportLine "
				// + "WHERE PA_ReportLineSet_ID=? AND Name=? AND ROWNUM=1"		//	#2..3
				+ "FROM I_ReportLine "
				+ "WHERE I_ReportLine_ID=(SELECT MAX(I_ReportLine_ID) "		
				+ "FROM I_ReportLine "
				+ "WHERE PA_ReportLineSet_ID=? AND Name=? "		//	#2..3
				//jz + clientCheck, get_TrxName());
				+ clientCheck + ")", get_TrxName());

			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				int PA_ReportLineSet_ID = rs.getInt(1);
				String Name = rs.getString(2);
				//
				try
				{
					int PA_ReportLine_ID = DB.getNextID(m_AD_Client_ID, "PA_ReportLine", get_TrxName());
					if (PA_ReportLine_ID <= 0)
						throw new DBException("No NextID (" + PA_ReportLine_ID + ")");
					pstmt_insertLine.setInt(1, PA_ReportLine_ID);
					pstmt_insertLine.setInt(2, PA_ReportLineSet_ID);
					pstmt_insertLine.setString(3, Name);
					//
					no = pstmt_insertLine.executeUpdate();
					log.trace("Insert ReportLine = " + no + ", PA_ReportLine_ID=" + PA_ReportLine_ID);
					noInsertLine++;
				}
				catch (Exception ex)
				{
					log.trace(ex.toString());
					continue;
				}
			}
			rs.close();
			pstmt.close();
			//
			pstmt_insertLine.close();
		}
		catch (SQLException e)
		{
			log.error("Create ReportLine", e);
		}

		//	Set PA_ReportLine_ID (for newly created)
		sql = new StringBuffer ("UPDATE I_ReportLine i "
			+ "SET PA_ReportLine_ID=(SELECT MAX(PA_ReportLine_ID) FROM PA_ReportLine r"
			+ " WHERE i.Name=r.Name AND i.PA_ReportLineSet_ID=r.PA_ReportLineSet_ID) "
			+ "WHERE PA_ReportLine_ID IS NULL AND PA_ReportLineSet_ID IS NOT NULL"
			+ " AND I_IsImported='N'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PA_ReportLine_ID=" + no);

		//	****	Update ReportLine
		sql = new StringBuffer ("UPDATE PA_ReportLine r "
			+ "SET (Description,SeqNo,IsSummary,IsPrinted,LineType,CalculationType,AmountType,PAAmountType,PAPeriodType,PostingType,Updated,UpdatedBy)="
			+ " (SELECT Description,SeqNo,IsSummary,IsPrinted,LineType,CalculationType,AmountType,PAAmountType,PAPeriodType,PostingType,now(),UpdatedBy"
			+ " FROM I_ReportLine i WHERE r.Name=i.Name AND r.PA_ReportLineSet_ID=i.PA_ReportLineSet_ID"
			+ " AND i.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
			+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID)) "
			+ "WHERE EXISTS (SELECT *"
			+ " FROM I_ReportLine i WHERE r.Name=i.Name AND r.PA_ReportLineSet_ID=i.PA_ReportLineSet_ID"
			+ " AND i.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
			+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID AND i.I_IsImported='N'))")
			.append(clientCheck);
		noUpdateLine = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		log.info("Update PA_ReportLine=" + noUpdateLine);


		//	-------------------------------------------------------------------
		int noInsertSource = 0;
		int noUpdateSource = 0;

		//	****	Create ReportSource
		sql = new StringBuffer ("SELECT I_ReportLine_ID, PA_ReportSource_ID "
			+ "FROM I_ReportLine "
			+ "WHERE PA_ReportLine_ID IS NOT NULL"
			+ " AND I_IsImported='N'").append(clientCheck);
		
		try
		{
			//	Insert ReportSource
			PreparedStatement pstmt_insertSource = DB.prepareStatement
				("INSERT INTO PA_ReportSource "
				+ "(PA_ReportSource_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "PA_ReportLine_ID,ElementType,C_ElementValue_ID) "
				+ "SELECT ?,"
				+ "AD_Client_ID,AD_Org_ID,'Y',now(),CreatedBy,now(),UpdatedBy,"
				+ "PA_ReportLine_ID,'AC',C_ElementValue_ID "
				+ "FROM I_ReportLine "
				+ "WHERE I_ReportLine_ID=?"
				+ " AND I_IsImported='N'"
				+ clientCheck, get_TrxName());

			//	Update ReportSource
			//jz 
			/*
			String sqlt="UPDATE PA_ReportSource "
				+ "SET (ElementType,C_ElementValue_ID,Updated,UpdatedBy)="
				+ " (SELECT 'AC',C_ElementValue_ID,now(),UpdatedBy"
				+ " FROM I_ReportLine"
				+ " WHERE I_ReportLine_ID=?) "
				+ "WHERE PA_ReportSource_ID=?"
				+ clientCheck;
			PreparedStatement pstmt_updateSource = DB.prepareStatement
				(sqlt, get_TrxName());
				*/

			// Delete ReportSource - afalcone 22/02/2007 - F.R. [ 1642250 ] Import ReportLine / Very Slow Reports
			PreparedStatement pstmt_deleteSource = DB.prepareStatement
				("DELETE FROM PA_ReportSource "
				+ "WHERE C_ElementValue_ID IS NULL" 
				+ " AND PA_ReportSource_ID=?"
				+ clientCheck, get_TrxName());
			//End afalcone 22/02/2007 - F.R. [ 1642250 ] Import ReportLine / Very Slow Reports
			
			//	Set Imported = Y
			PreparedStatement pstmt_setImported = DB.prepareStatement
				("UPDATE I_ReportLine SET I_IsImported='Y',"
				+ " PA_ReportSource_ID=?, "
				+ " Updated=now(), Processed='Y' WHERE I_ReportLine_ID=?", get_TrxName());

			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				int I_ReportLine_ID = rs.getInt(1);
				int PA_ReportSource_ID = rs.getInt(2);
				//
				if (PA_ReportSource_ID == 0)			//	New ReportSource
				{
					try
					{
						PA_ReportSource_ID = DB.getNextID(m_AD_Client_ID, "PA_ReportSource", get_TrxName());
						if (PA_ReportSource_ID <= 0)
							throw new DBException("No NextID (" + PA_ReportSource_ID + ")");
						pstmt_insertSource.setInt(1, PA_ReportSource_ID);
						pstmt_insertSource.setInt(2, I_ReportLine_ID);
						//
						no = pstmt_insertSource.executeUpdate();
						log.trace("Insert ReportSource = " + no + ", I_ReportLine_ID=" + I_ReportLine_ID + ", PA_ReportSource_ID=" + PA_ReportSource_ID);
						noInsertSource++;
					}
					catch (Exception ex)
					{
						log.trace("Insert ReportSource - " + ex.toString());
						sql = new StringBuffer ("UPDATE I_ReportLine i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert ElementSource: " + ex.toString()))
							.append("WHERE I_ReportLine_ID=").append(I_ReportLine_ID);
						DB.executeUpdate(sql.toString(), get_TrxName());
						continue;
					}
				}
				else								//	update Report Source
				{
					//jz
					String sqlt=DB.convertSqlToNative("UPDATE PA_ReportSource "
						+ "SET (ElementType,C_ElementValue_ID,Updated,UpdatedBy)="
						+ " (SELECT CAST('AC' AS CHAR(2)),C_ElementValue_ID,now(),UpdatedBy"  //jz
						+ " FROM I_ReportLine"
						+ " WHERE I_ReportLine_ID=" + I_ReportLine_ID + ") "
						+ "WHERE PA_ReportSource_ID="+PA_ReportSource_ID+" "
						+ clientCheck);
					PreparedStatement pstmt_updateSource = DB.prepareStatement
						(sqlt, get_TrxName());
					//pstmt_updateSource.setInt(1, I_ReportLine_ID);
					//pstmt_updateSource.setInt(2, PA_ReportSource_ID);
					try
					{
						no = pstmt_updateSource.executeUpdate();
						//no = DB.executeUpdate(sqlt, get_TrxName());
						log.trace("Update ReportSource = " + no + ", I_ReportLine_ID=" + I_ReportLine_ID + ", PA_ReportSource_ID=" + PA_ReportSource_ID);
						noUpdateSource++;
					}
					catch (SQLException ex)
					{
						log.trace( "Update ReportSource - " + ex.toString());
						sql = new StringBuffer ("UPDATE I_ReportLine i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Update ElementSource: " + ex.toString()))
							.append("WHERE I_ReportLine_ID=").append(I_ReportLine_ID);
						DB.executeUpdate(sql.toString(), get_TrxName());
						continue;
					}
					pstmt_updateSource.close();
				}	//	update source

				//	Set Imported to Y
				pstmt_setImported.setInt(1, PA_ReportSource_ID);
				pstmt_setImported.setInt(2, I_ReportLine_ID);
				no = pstmt_setImported.executeUpdate();
				if (no != 1)
					log.error("Set Imported=" + no);
				//
				
				// afalcone 22/02/2007 - F.R. [ 1642250 ] Import ReportLine / Very Slow Reports
				// Delete report sources with null account
				pstmt_deleteSource.setInt(1, PA_ReportSource_ID);
				no = pstmt_deleteSource.executeUpdate();
				log.trace("Deleted ReportSource with Null Account= " + no + ", I_ReportLine_ID=" + I_ReportLine_ID + ", PA_ReportSource_ID=" + PA_ReportSource_ID);
				// End afalcone 22/02/2007 - F.R. [ 1642250 ] Import ReportLine / Very Slow Reports

				commitEx();
			}
			rs.close();
			pstmt.close();
			//
			pstmt_insertSource.close();
			//jz pstmt_updateSource.close();
			pstmt_setImported.close();
			//
		}
		catch (SQLException e)
		{
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_ReportLine "
			+ "SET I_IsImported='N', Updated=now() "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		addLog (0, null, new BigDecimal (noInsertLine), "@PA_ReportLine_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noUpdateLine), "@PA_ReportLine_ID@: @Updated@");
		addLog (0, null, new BigDecimal (noInsertSource), "@PA_ReportSource_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noUpdateSource), "@PA_ReportSource_ID@: @Updated@");

		return "";
	}	//	doIt

}	//	ImportReportLine
