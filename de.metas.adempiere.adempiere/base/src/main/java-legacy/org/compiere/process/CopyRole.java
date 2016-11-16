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
 *
 * Copyright (C) 2005 Robert KLEIN. robeklein@gmail.com * 
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;

import org.compiere.model.X_AD_Role_Included;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;


/**
 *	Copy role access records
 *	
 *  @author Robert Klein
 *  @ author Paul Bowden
 *  @version $Id: CopyRole.java,v 1.0$
 *  
 */

public class CopyRole extends SvrProcess
{
	private int m_AD_Role_ID_From = 0;
	private int m_AD_Role_ID_To = 0;
	private int m_AD_Client_ID = 0;	
	private int m_AD_Org_ID = 0;
	
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
			else if (name.equals("AD_Role_ID") && i == 0)
				m_AD_Role_ID_From = para[i].getParameterAsInt();
			else if (name.equals("AD_Role_ID")&& i == 1)
				m_AD_Role_ID_To = para[i].getParameterAsInt();
			else if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = para[i].getParameterAsInt();
		}		
	}	//	prepare

	/**
	 * 	Copy the role access records
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{	
		String[] tables = new String[] {"AD_Window_Access", "AD_Process_Access", "AD_Form_Access",
				"AD_Workflow_Access", "AD_Task_Access", "AD_Document_Action_Access",
				X_AD_Role_Included.Table_Name,
		};
		String[] keycolumns = new String[] {"AD_Window_ID", "AD_Process_ID", "AD_Form_ID",
				"AD_Workflow_ID", "AD_Task_ID", "C_DocType_ID, AD_Ref_List_ID",
				X_AD_Role_Included.COLUMNNAME_Included_Role_ID,
		};
		
		int action = 0;
		
		for ( int i = 0; i < tables.length; i++ )
		{
			String table = tables[i];
			String keycolumn = keycolumns[i];
			
			String sql = "DELETE FROM " + table + " WHERE AD_Role_ID = " + m_AD_Role_ID_To;
			int no = DB.executeUpdateEx(sql, get_TrxName());
			addLog(action++, null, BigDecimal.valueOf(no), "Old records deleted from " + table );
			
			final boolean column_IsReadWrite =
				!table.equals("AD_Document_Action_Access")
				&& !table.equals(X_AD_Role_Included.Table_Name);
			final boolean column_SeqNo = table.equals(X_AD_Role_Included.Table_Name); 
			
			sql = "INSERT INTO " + table
			+   " (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, " 
			+   "AD_Role_ID, " + keycolumn +", isActive";
			if (column_SeqNo)
				sql += ", SeqNo ";
			if (column_IsReadWrite)
				sql += ", isReadWrite) ";
			else
				sql +=  ") ";
			sql	+= "SELECT " + m_AD_Client_ID
			+	", "+ m_AD_Org_ID
			+	", getdate(), "+ Env.getAD_User_ID(Env.getCtx())
			+	", getdate(), "+ Env.getAD_User_ID(Env.getCtx())
			+	", " + m_AD_Role_ID_To
			+	", " + keycolumn
			+	", IsActive ";
			if (column_SeqNo)
				sql += ", SeqNo ";
			if (column_IsReadWrite)
				sql += ", isReadWrite ";
			sql += "FROM " + table + " WHERE AD_Role_ID = " + m_AD_Role_ID_From;

			no = DB.executeUpdateEx (sql, get_TrxName());

			addLog(action++, null, new BigDecimal(no), "New records inserted into " + table );
		}
	
		return "Role copied";
	}	//	doIt
			}	//	CopyRole
