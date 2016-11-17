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
 * Portions created by Carlos Ruiz are Copyright (C) 2005 QSS Ltda.
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.compiere.model.X_M_Product;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;

import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Title:	Check BOM Structure (free of cycles)
 * Description:
 *		Tree cannot contain BOMs which are already referenced
 *	
 *  @author Carlos Ruiz (globalqss)
 *  @version $Id: M_Product_BOM_Check.java,v 1.0 2005/09/17 13:32:00 globalqss Exp $
 * @author Carlos Ruiz (globalqss)
 *         Make T_Selection tables permanent         
 */
public class M_Product_BOM_Check extends JavaProcess
{

	/** The Record						*/
	private int		p_Record_ID = 0;

	private int m_AD_PInstance_ID = 0; 
	
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
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
		m_AD_PInstance_ID = getAD_PInstance_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
        StringBuffer sql1 = null;
		int no = 0;

		log.info("Check BOM Structure");

		//	Record ID is M_Product_ID of product to be tested
		X_M_Product xp = new X_M_Product(Env.getCtx(), p_Record_ID, get_TrxName());
		
		if (! xp.isBOM()) {
			log.info("NOT BOM Product");
			// No BOM - should not happen, but no problem
			xp.setIsVerified(true);
			xp.save(get_TrxName());
			return "OK";
		}

		// Table to put all BOMs - duplicate will cause exception
        sql1 = new StringBuffer("DELETE FROM T_Selection2 WHERE Query_ID = 0 AND AD_PInstance_ID="+ m_AD_PInstance_ID);
        no = DB.executeUpdate(sql1.toString(), get_TrxName());
        sql1 = new StringBuffer("INSERT INTO T_Selection2 (AD_PInstance_ID, Query_ID, T_Selection_ID) VALUES ("
        		+ m_AD_PInstance_ID
        		+ ", 0, " 
        		+ p_Record_ID + ")");
        no = DB.executeUpdate(sql1.toString(), get_TrxName());
		// Table of root modes
        sql1 = new StringBuffer("DELETE FROM T_Selection WHERE AD_PInstance_ID="+ m_AD_PInstance_ID);
        no = DB.executeUpdate(sql1.toString(), get_TrxName());
        sql1 = new StringBuffer("INSERT INTO T_Selection (AD_PInstance_ID, T_Selection_ID) VALUES ("
        		+ m_AD_PInstance_ID
        		+ ", " 
        		+ p_Record_ID + ")");
        no = DB.executeUpdate(sql1.toString(), get_TrxName());
        
        while (true) {
        	
    		//	Get count remaining on t_selection
    		int countno = 0;
    		try
    		{
    			PreparedStatement pstmt = DB.prepareStatement
    				("SELECT COUNT(*) FROM T_Selection WHERE AD_PInstance_ID="+ m_AD_PInstance_ID, get_TrxName());
    			ResultSet rs = pstmt.executeQuery();
    			if (rs.next())
    				countno = rs.getInt(1);
    			rs.close();
    			pstmt.close();
    		}
    		catch (SQLException e)
    		{
    			throw new Exception ("count t_selection", e);
    		}
    		log.debug("Count T_Selection =" + countno);
    		
    		if (countno == 0)
    			break;

    		try
    		{
    			// if any command fails (no==-1) break and inform failure 
    			// Insert BOM Nodes into "All" table
    			sql1 = new StringBuffer("INSERT INTO T_Selection2 (AD_PInstance_ID, Query_ID, T_Selection_ID) " 
    					+ "SELECT " + m_AD_PInstance_ID + ", 0, p.M_Product_ID FROM M_Product p WHERE IsBOM='Y' AND EXISTS " 
    					//+ "(SELECT * FROM M_Product_BOM b WHERE p.M_Product_ID=b.M_ProductBOM_ID AND b.M_Product_ID IN " 
    					+ "(SELECT * FROM PP_Product_BOM b WHERE p.M_Product_ID=b.M_Product_ID AND b.M_Product_ID IN " 
    					+ "(SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID="+ m_AD_PInstance_ID + "))");
    			no = DB.executeUpdate(sql1.toString(), get_TrxName());
    			if (no == -1) raiseError("InsertingRoot:ERROR", sql1.toString());
    			// Insert BOM Nodes into temporary table
    			sql1 = new StringBuffer("DELETE FROM T_Selection2 WHERE Query_ID = 1 AND AD_PInstance_ID="+ m_AD_PInstance_ID);
    			no = DB.executeUpdate(sql1.toString(), get_TrxName());
    			if (no == -1) raiseError("InsertingRoot:ERROR", sql1.toString());
    			sql1 = new StringBuffer("INSERT INTO T_Selection2 (AD_PInstance_ID, Query_ID, T_Selection_ID) " 
    					+ "SELECT " + m_AD_PInstance_ID + ", 1, p.M_Product_ID FROM M_Product p WHERE IsBOM='Y' AND EXISTS " 
    					//+ "(SELECT * FROM M_Product_BOM b WHERE p.M_Product_ID=b.M_ProductBOM_ID AND b.M_Product_ID IN "  
    					+ "(SELECT * FROM PP_Product_BOM b WHERE p.M_Product_ID=b.M_Product_ID AND b.M_Product_ID IN " 
    					+ "(SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID="+ m_AD_PInstance_ID + "))");
    			no = DB.executeUpdate(sql1.toString(), get_TrxName());
    			if (no == -1) raiseError("InsertingRoot:ERROR", sql1.toString());
    			// Copy into root table
    			sql1 = new StringBuffer("DELETE FROM T_Selection WHERE AD_PInstance_ID="+ m_AD_PInstance_ID);
    			no = DB.executeUpdate(sql1.toString(), get_TrxName());
    			if (no == -1) raiseError("InsertingRoot:ERROR", sql1.toString());
    			sql1 = new StringBuffer("INSERT INTO T_Selection (AD_PInstance_ID, T_Selection_ID) " 
    					+ "SELECT " + m_AD_PInstance_ID + ", T_Selection_ID " 
    					+ "FROM T_Selection2 WHERE Query_ID = 1 AND AD_PInstance_ID="+ m_AD_PInstance_ID);
    			no = DB.executeUpdate(sql1.toString(), get_TrxName());
    			if (no == -1) raiseError("InsertingRoot:ERROR", sql1.toString());
    		}
    		catch (Exception e)
    		{
    			throw new Exception ("root insert", e);
    		}
        	
        }

        // Finish process
		xp.setIsVerified(true);
		xp.save(get_TrxName());
		return "OK";
	}	//	doIt
	
	private void raiseError(String string, String sql) throws Exception {
		DB.rollback(false, get_TrxName());
		String msg = string;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null)
			msg = pp.getName() + " - ";
		msg += sql;
		throw new AdempiereUserError (msg);
	}
	
}	//	M_Product_BOM_Check
