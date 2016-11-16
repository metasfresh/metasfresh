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
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	Delete Data in Import Table
 *	
 *  @author Jorg Janke
 *  @version $Id: ImportDelete.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportDelete extends SvrProcess
{
	/**	Table be deleted		*/
	private int				p_AD_Table_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Table_ID"))
				p_AD_Table_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return clear Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("AD_Table_ID=" + p_AD_Table_ID);
		//	get Table Info
		MTable table = new MTable (getCtx(), p_AD_Table_ID, get_TrxName());
		if (table.get_ID() == 0)
			throw new IllegalArgumentException ("No AD_Table_ID=" + p_AD_Table_ID);
		String tableName = table.getTableName();
		if (!tableName.startsWith("I"))
			throw new IllegalArgumentException ("Not an import table = " + tableName);
		
		//	Delete
		String sql = "DELETE FROM " + tableName + " WHERE AD_Client_ID=" + getAD_Client_ID();
		int no = DB.executeUpdate(sql, get_TrxName());
		String msg = Msg.translate(getCtx(), tableName + "_ID") + " #" + no;
		return msg;
	}	//	ImportDelete

}	//	ImportDelete
