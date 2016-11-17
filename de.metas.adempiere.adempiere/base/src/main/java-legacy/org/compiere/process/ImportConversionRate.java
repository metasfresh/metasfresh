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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.model.PO;
import org.compiere.model.X_C_Conversion_Rate;
import org.compiere.model.X_I_Conversion_Rate;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *	Import Currency Conversion Rates 
 *	
 *  @author Jorg Janke
 *  @version $Id: ImportConversionRate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportConversionRate extends JavaProcess
{
	
	/**	Client to be imported to			*/
	private int				p_AD_Client_ID = 0;
	/**	Organization to be imported to		*/
	private int				p_AD_Org_ID = 0;
	/**	Conversion Type to be imported to	*/
	private int				p_C_ConversionType_ID = 0;
	/**	Default Date					*/
	private Timestamp		p_ValidFrom = null;
	/** Default Reciprocal				*/
	private boolean 		p_CreateReciprocalRate = false;
	/**	Delete old Imported				*/
	private boolean			p_DeleteOldImported = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_ConversionType_ID"))
				p_C_ConversionType_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("ValidFrom"))
				p_ValidFrom = (Timestamp)para[i].getParameter();
			else if (name.equals("CreateReciprocalRate"))
				p_CreateReciprocalRate = "Y".equals(para[i].getParameter());
			else if (name.equals("DeleteOldImported"))
				p_DeleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("doIt - AD_Client_ID=" + p_AD_Client_ID
			+ ",AD_Org_ID=" + p_AD_Org_ID
			+ ",C_ConversionType_ID=" + p_C_ConversionType_ID
			+ ",ValidFrom=" + p_ValidFrom
			+ ",CreateReciprocalRate=" + p_CreateReciprocalRate);
		//
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + p_AD_Client_ID;
		//	****	Prepare	****

		//	Delete Old Imported
		if (p_DeleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_Conversion_Rate "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted Old Imported =" + no);
		}

		//	Set Client, Org, Location, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_Conversion_Rate "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (p_AD_Client_ID).append ("),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (p_AD_Org_ID).append ("),");
		if (p_C_ConversionType_ID != 0)
			sql.append(" C_ConversionType_ID = COALESCE (C_ConversionType_ID,").append (p_C_ConversionType_ID).append ("),");
		if (p_ValidFrom != null)
			sql.append(" ValidFrom = COALESCE (ValidFrom,").append (DB.TO_DATE(p_ValidFrom)).append ("),");
		else
			sql.append(" ValidFrom = COALESCE (ValidFrom,now()),");
		sql.append(" CreateReciprocalRate = COALESCE (CreateReciprocalRate,'").append (p_CreateReciprocalRate ? "Y" : "N").append ("'),"
			+ " IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, now()),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, now()),"
			+ " UpdatedBy = ").append(getAD_User_ID()).append(","
			+ " I_ErrorMsg = ' ',"
			+ " Processed = 'N',"	
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset =" + no);

		//	Org
		sql = new StringBuffer ("UPDATE I_Conversion_Rate o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Org =" + no);
			
		//	Conversion Type
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET C_ConversionType_ID = (SELECT C_ConversionType_ID FROM C_ConversionType c"
			+ " WHERE c.Value=i.ConversionTypeValue AND c.AD_Client_ID IN (0,i.AD_Client_ID) AND c.IsActive='Y') "
			+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no > 0)
			log.debug("Set ConversionType =" + no);
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid ConversionType, ' "
			+ "WHERE (C_ConversionType_ID IS NULL"
			+ " OR NOT EXISTS (SELECT * FROM C_ConversionType c "
				+ "WHERE i.C_ConversionType_ID=c.C_ConversionType_ID AND c.IsActive='Y'"
				+ " AND c.AD_Client_ID IN (0,i.AD_Client_ID)))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid ConversionType =" + no);
		
		//	Currency
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET C_Currency_ID = (SELECT C_Currency_ID FROM C_Currency c"
			+ "	WHERE c.ISO_Code=i.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID) AND c.IsActive='Y') "
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no > 0)
			log.debug("Set Currency =" + no);
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency, ' "
			+ "WHERE (C_Currency_ID IS NULL"
			+ " OR NOT EXISTS (SELECT * FROM C_Currency c "
				+ "WHERE i.C_Currency_ID=c.C_Currency_ID AND c.IsActive='Y'"
				+ " AND c.AD_Client_ID IN (0,i.AD_Client_ID)))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Currency =" + no);

		//	Currency To
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET C_Currency_ID_To = (SELECT C_Currency_ID FROM C_Currency c"
			+ "	WHERE c.ISO_Code=i.ISO_Code_To AND c.AD_Client_ID IN (0,i.AD_Client_ID) AND c.IsActive='Y') "
			+ "WHERE C_Currency_ID_To IS NULL AND ISO_Code_To IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no > 0)
			log.debug("Set Currency To =" + no);
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency To, ' "
			+ "WHERE (C_Currency_ID_To IS NULL"
			+ " OR NOT EXISTS (SELECT * FROM C_Currency c "
				+ "WHERE i.C_Currency_ID_To=c.C_Currency_ID AND c.IsActive='Y'"
				+ " AND c.AD_Client_ID IN (0,i.AD_Client_ID)))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Currency To =" + no);
			
		//	Rates
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET MultiplyRate = 1 / DivideRate "
			+ "WHERE (MultiplyRate IS NULL OR MultiplyRate = 0) AND DivideRate IS NOT NULL AND DivideRate<>0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no > 0)
			log.debug("Set MultiplyRate =" + no);
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET DivideRate = 1 / MultiplyRate "
			+ "WHERE (DivideRate IS NULL OR DivideRate = 0) AND MultiplyRate IS NOT NULL AND MultiplyRate<>0"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no > 0)
			log.debug("Set DivideRate =" + no);
		sql = new StringBuffer ("UPDATE I_Conversion_Rate i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Rates, ' "
			+ "WHERE (MultiplyRate IS NULL OR MultiplyRate = 0 OR DivideRate IS NULL OR DivideRate = 0)"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Rates =" + no);
	//	sql = new StringBuffer ("UPDATE I_Conversion_Rate i "	//	Rate diff > 10%
	//		+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Inconsistent Rates='||(MultiplyRate - (1/DivideRate)) "
	//		+ "WHERE ((MultiplyRate - (1/DivideRate)) > (MultiplyRate * .1))"
	//		+ " AND I_IsImported<>'Y'").append (clientCheck);
	//	no = DB.executeUpdate(sql.toString(), get_TrxName());
	//	if (no != 0)
	//		log.warn ("Inconsistent Rates =" + no);
		
		commitEx();
		/*********************************************************************/

		int noInsert = 0;
		sql = new StringBuffer ("SELECT * FROM I_Conversion_Rate "
			+ "WHERE I_IsImported='N'").append (clientCheck)
			.append(" ORDER BY C_Currency_ID, C_Currency_ID_To, ValidFrom");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_I_Conversion_Rate imp = new X_I_Conversion_Rate (getCtx(), rs, get_TrxName());
				X_C_Conversion_Rate rate = newConversionRate (imp, 
					imp.getC_ConversionType_ID(), 
					imp.getC_Currency_ID(), imp.getC_Currency_ID_To(),
					imp.getMultiplyRate(), imp.getValidFrom());
				if (imp.getValidTo() != null)
					rate.setValidTo(imp.getValidTo());
				if (rate.save())
				{
					imp.setC_Conversion_Rate_ID(rate.getC_Conversion_Rate_ID());
					imp.setI_IsImported(true);
					imp.setProcessed(true);
					imp.save();
					noInsert++;
					//
					if (imp.isCreateReciprocalRate())
					{
						rate = newConversionRate(imp, 
							imp.getC_ConversionType_ID(), 
							imp.getC_Currency_ID_To(), imp.getC_Currency_ID(),
							imp.getDivideRate(), imp.getValidFrom());
						if (imp.getValidTo() != null)
							rate.setValidTo(imp.getValidTo());
						if (rate.save())					
							noInsert++;
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_Conversion_Rate "
			+ "SET I_IsImported='N', Updated=now() "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@C_Conversion_Rate_ID@: @Inserted@");
		return "";
	}	//	doIt
	
	private static X_C_Conversion_Rate newConversionRate(PO po,
			int C_ConversionType_ID,
			int C_Currency_ID, int C_Currency_ID_To,
			BigDecimal MultiplyRate, Timestamp ValidFrom)
	{
		final BigDecimal divideRate = MultiplyRate != null && MultiplyRate.signum() != 0 ? BigDecimal.ONE.divide(MultiplyRate, 12, RoundingMode.HALF_UP) : BigDecimal.ZERO;
		
		final X_C_Conversion_Rate conversionRate = new X_C_Conversion_Rate(po.getCtx(), 0, po.get_TrxName());
		conversionRate.setAD_Org_ID(po.getAD_Org_ID());
		conversionRate.setC_ConversionType_ID(C_ConversionType_ID);
		conversionRate.setC_Currency_ID(C_Currency_ID);
		conversionRate.setC_Currency_ID_To(C_Currency_ID_To);
		//
		conversionRate.setMultiplyRate(MultiplyRate);
		conversionRate.setDivideRate(divideRate);
		conversionRate.setValidFrom(ValidFrom);
		return conversionRate;
	}
}	// ImportConversionRate
