/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.RowSet;

import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.ValueNamePair;
import org.eevolution.model.X_T_BOMLine;

import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Multi-Level BOM & Formula Detail
 * 
 * @author victor.perez@e-evolution.com,Sergio Ramazzina
 * @version $Id: PrintBOM.java,v 1.2 2005/04/19 12:54:30 srama Exp $
 * 
 */
public class PrintBOM extends SvrProcess
{
	private static final Properties ctx = Env.getCtx();
	private int p_M_Product_ID = 0;
	private boolean p_implosion = false;
	private int LevelNo = 1;
	private int SeqNo = 0;
	private String levels = new String("....................");
	private int AD_PInstance_ID = 0;

	/**
	 * Prepare - e.g., get Parameters.
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
			else if (name.equals("M_Product_ID"))
			{
				p_M_Product_ID = ((BigDecimal) para[i].getParameter()).intValue();
			}
			else if (name.equals("Implosion"))
			{
				p_implosion = ((String) para[i].getParameter()).equals("N") ? false : true;
			}
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	} // prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (clear text)
	 * @throws Exception
	 *             if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		AD_PInstance_ID = getAD_PInstance_ID();

		try 
		{
			loadBOM();
			print();
		}
		catch (Exception e)
		{
			log.error("PrintBOM", e.toString());
			throw new Exception(e.getLocalizedMessage());
		}
		finally
		{
			String sql = "DELETE FROM T_BomLine WHERE AD_PInstance_ID = " + AD_PInstance_ID;
			DB.executeUpdate(sql, null);		
		}

		return "@OK@";
	} // doIt

	private static final int X_RV_PP_Product_BOMLine_Table_ID = 53063;
	private static final String X_RV_PP_Product_BOMLine_Table_Name = "RV_PP_Product_BOMLine";
	
	/**
	 * Print result generate for this report
	 */
	void print() throws Exception
	{
		Language language = Env.getLanguage(getCtx()); // Base Language
		MPrintFormat pf = null;
		int pfid = 0;
		
		// get print format for client, else copy system to client  
		RowSet pfrs = MPrintFormat.getAccessiblePrintFormats(X_RV_PP_Product_BOMLine_Table_ID, -1, null);
		pfrs.next();
		pfid = pfrs.getInt("AD_PrintFormat_ID");
		
		if(pfrs.getInt("AD_Client_ID") != 0) pf = MPrintFormat.get(getCtx(), pfid, false);
		else pf = MPrintFormat.copyToClient(getCtx(), pfid, getAD_Client_ID());
		pfrs.close();		

		if (pf == null) raiseError("Error: ","No Print Format");

		pf.setLanguage(language);
		pf.setTranslationLanguage(language);
		// query
		MQuery query = MQuery.get(getCtx(), AD_PInstance_ID, X_RV_PP_Product_BOMLine_Table_Name);
		query.addRestriction("AD_PInstance_ID", Operator.EQUAL, AD_PInstance_ID);

		PrintInfo info = new PrintInfo(X_RV_PP_Product_BOMLine_Table_Name, 
				X_RV_PP_Product_BOMLine_Table_ID, getRecord_ID());
		ReportEngine re = new ReportEngine(getCtx(), pf, query, info);

		ReportCtl.preview(re);
		// wait for report window to be closed as t_bomline   
		// records are deleted when process ends 
		while (re.getView().isDisplayable()) 
		{
			Env.sleep(1);
		}	
	}

	/**
	 * Action: Fill Tree with all nodes
	 */
	private void loadBOM() throws Exception
	{
		int count = 0;
		if (p_M_Product_ID == 0) 
			raiseError("Error: ","Product ID not found");

		X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
		tboml.setPP_Product_BOM_ID(0);
		tboml.setPP_Product_BOMLine_ID(0);
		tboml.setM_Product_ID(p_M_Product_ID);
		tboml.setSel_Product_ID(p_M_Product_ID);
		tboml.setImplosion(p_implosion);
		tboml.setLevelNo(0);
		tboml.setLevels("0");
		tboml.setSeqNo(0);
		tboml.setAD_PInstance_ID(AD_PInstance_ID);
		tboml.save();

		if (p_implosion)
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "SELECT PP_Product_BOMLine_ID FROM PP_Product_BOMLine " 
					+ "WHERE IsActive = 'Y' AND M_Product_ID = ? ";
			try
			{
				stmt = DB.prepareStatement(sql, get_TrxName());
				stmt.setInt(1, p_M_Product_ID);
				rs = stmt.executeQuery();

				while (rs.next())
				{
					parentImplotion(rs.getInt(1));
					++count;
				}
				if (count == 0)
					raiseError("Error: ","Product is not a component");
			}
			catch (SQLException e)
			{
				log.error(e.getLocalizedMessage() + sql, e);
				throw new Exception("SQLException: "+e.getLocalizedMessage());
			}
			finally
			{
				DB.close(rs, stmt);
				rs = null;
				stmt = null;
			}
		}
		else
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "SELECT PP_Product_BOM_ID FROM PP_Product_BOM " 
					+ "WHERE IsActive = 'Y' AND M_Product_ID = ? ";
			try
			{
				stmt = DB.prepareStatement(sql, get_TrxName());
				stmt.setInt(1, p_M_Product_ID);
				rs = stmt.executeQuery();
				while (rs.next())
				{
					parentExplotion(rs.getInt(1));
					++count;
				}
				if (count == 0)
					raiseError("Error: ","Product is not a BOM");				
			}
			catch (SQLException e)
			{
				log.error(e.getLocalizedMessage() + sql, e);
				throw new Exception("SQLException: "+e.getLocalizedMessage());
			}
			finally
			{
				DB.close(rs, stmt);
				rs = null;
				stmt = null;
			}
		}
	}

	/**
	 * Generate an Implotion for this BOM Line
	 * 
	 * @param PP_Product_BOMLine_ID
	 *            ID BOM Line
	 */
	public void parentImplotion(int PP_Product_BOMLine_ID) throws Exception
	{
		int PP_Product_BOM_ID = 0;
		int M_Product_ID = 0;
		
		X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
		 
		PP_Product_BOM_ID = DB.getSQLValue(null, 
				"SELECT PP_Product_BOM_ID FROM PP_Product_BOMLine WHERE PP_Product_BOMLine_ID=?",PP_Product_BOMLine_ID);
		if (PP_Product_BOM_ID < 0)
			throw new Exception(MetasfreshLastError.retrieveErrorString("Error: PrintBOM.parentImplotion()"));
		M_Product_ID = DB.getSQLValue(null,
				"SELECT M_Product_ID FROM PP_Product_BOM WHERE PP_Product_BOM_ID=?", PP_Product_BOM_ID);
		if (M_Product_ID < 0)
			throw new Exception(MetasfreshLastError.retrieveErrorString("Error: PrintBOM.parentImplotion()"));

		tboml.setPP_Product_BOM_ID(PP_Product_BOM_ID);
		tboml.setPP_Product_BOMLine_ID(PP_Product_BOMLine_ID);
		tboml.setM_Product_ID(M_Product_ID);
		tboml.setLevelNo(LevelNo);
		tboml.setSel_Product_ID(p_M_Product_ID);
		tboml.setImplosion(p_implosion);

		if (LevelNo >= 11)
			tboml.setLevels(levels + ">" + LevelNo);
		else if (LevelNo >= 1) tboml.setLevels(levels.substring(0, LevelNo) + LevelNo);

		tboml.setSeqNo(SeqNo);
		tboml.setAD_PInstance_ID(AD_PInstance_ID);
		tboml.save();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT PP_Product_BOM_ID, M_Product_ID FROM PP_Product_BOM " 
					+ "WHERE IsActive = 'Y' AND M_Product_ID = ? ";
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, M_Product_ID);
			rs = stmt.executeQuery();

			while (rs.next())
			{
				SeqNo += 1;
				component(rs.getInt(2));
			}
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage() + sql, e);
			throw new Exception("SQLException: "+e.getLocalizedMessage());
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
	}

	/**
	 * Generate an Explotion for this BOM
	 * 
	 * @param PP_Product_BOMLine_ID
	 *            ID BOM Line
	 */
	public void parentExplotion(int PP_Product_BOM_ID) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT PP_Product_BOMLine_ID, M_Product_ID FROM PP_Product_BOMLine boml "
				+ "WHERE IsActive = 'Y' AND PP_Product_BOM_ID = ? ORDER BY Line ";
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, PP_Product_BOM_ID);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				SeqNo += 1;
				X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
				tboml.setPP_Product_BOM_ID(PP_Product_BOM_ID);
				tboml.setPP_Product_BOMLine_ID(rs.getInt(1));
				tboml.setM_Product_ID(rs.getInt(2));
				tboml.setLevelNo(LevelNo);
				tboml.setLevels(levels.substring(0, LevelNo) + LevelNo);
				tboml.setSeqNo(SeqNo);
				tboml.setAD_PInstance_ID(AD_PInstance_ID);
				tboml.setSel_Product_ID(p_M_Product_ID);
				tboml.setImplosion(p_implosion);
				tboml.save();
				component(rs.getInt(2));
			}
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage() + sql, e);
			throw new Exception("SQLException: "+e.getLocalizedMessage());
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
	}

	/**
	 * Find if this product as component
	 * 
	 * @param M_Product_ID
	 *            ID Component
	 */
	public void component(int M_Product_ID) throws Exception
	{
		if (p_implosion)
		{
			LevelNo += 1;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "SELECT PP_Product_BOMLine_ID FROM PP_Product_BOMLine " 
				+ "WHERE IsActive = 'Y' AND M_Product_ID = ? ";
			try
			{
				stmt = DB.prepareStatement(sql, get_TrxName());
				stmt.setInt(1, M_Product_ID);
				rs = stmt.executeQuery();
				while (rs.next())
				{
					parentImplotion(rs.getInt(1));
				}
				rs.close();
				stmt.close();
				LevelNo -= 1;
				return;
			}
			catch (SQLException e)
			{
				log.error(e.getLocalizedMessage() + sql, e);
				throw new Exception("SQLException: "+e.getLocalizedMessage());
			}
			finally
			{
				DB.close(rs, stmt);
				rs = null;
				stmt = null;
			}
		}
		else
		{
			String sql = "SELECT PP_Product_BOM_ID FROM PP_Product_BOM  " 
					+ "WHERE IsActive = 'Y' AND Value = ? ";
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String Value = DB.getSQLValueString(get_TrxName(), 
						"SELECT Value FROM M_PRODUCT WHERE M_PRODUCT_ID=?", M_Product_ID);
				if (Value == null) 
				{
					throw new Exception(MetasfreshLastError.retrieveErrorString("Error: PrintBOM.component()"));
				}
				stmt = DB.prepareStatement(sql, get_TrxName());
				stmt.setString(1, Value);
				rs = stmt.executeQuery();
				boolean level = false;
				while (rs.next())
				{
					if (!level) LevelNo += 1;
					level = true;
					parentExplotion(rs.getInt(1));
					LevelNo -= 1;
				}
			}
			catch (SQLException e)
			{
				log.error(e.getLocalizedMessage() + sql, e);
				throw new Exception("SQLException: "+e.getLocalizedMessage());
			}
			finally
			{
				DB.close(rs, stmt);
				rs = null;
				stmt = null;
			}
		}
		return;
	}
	
	private void raiseError(String string, String hint) throws Exception
	{
		String msg = string;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null) msg = pp.getName() + " - ";
		msg += hint;
		throw new Exception(msg);
	}

}
