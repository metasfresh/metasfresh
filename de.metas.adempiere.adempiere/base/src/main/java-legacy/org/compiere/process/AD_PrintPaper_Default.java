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
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/
package org.compiere.process;

import org.compiere.util.DB;

/**
 * Title:	Set Current Format as Default
 *	
 *  @author Carlos Ruiz (globalqss)
 *  @version $Id: AD_PrintPaper_Default.java,v 1.0 2005/09/14 22:29:00 globalqss Exp $
 */
public class AD_PrintPaper_Default extends SvrProcess
{

	/** The Client						*/
	private int		p_AD_Client_ID = -1;
	/** The Record						*/
	private int		p_Record_ID = 0;
	
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
				p_AD_Client_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		StringBuffer sql = new StringBuffer("");
		int cnt = 0;

		log.info("Set Print Format");

		try
		{
			sql.append("UPDATE AD_PrintFormat pf "
	                + "SET AD_PrintPaper_ID = " + p_Record_ID + " "
	                + "WHERE EXISTS (SELECT * FROM AD_PrintPaper pp "
	                + "WHERE pf.AD_PrintPaper_ID=pp.AD_PrintPaper_ID "
	                + "AND IsLandscape = (SELECT IsLandscape FROM AD_PrintPaper " 
	                + "WHERE AD_PrintPaper_ID=" + p_Record_ID + "))");
			if (p_AD_Client_ID != -1) {
				sql.append(" AND AD_Client_ID = " + p_AD_Client_ID);
			}
			cnt = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info("Updated " + cnt + " columns");
			log.debug("Committing ...");
			DB.commit(true, null);
		}
		catch (Exception e)
		{
			log.error("set print format", e);
		}

		return "@Copied@=" + cnt;		
	}	//	doIt
	
}	//	AD_PrintPaper_Default
