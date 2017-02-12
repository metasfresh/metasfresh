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

import org.adempiere.util.Check;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MColumn;
import org.compiere.util.AdempiereUserError;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;

/**
 *	Synchronize Column with Database
 *	
 *  @author Victor Perez, Jorg Janke
 *  @version $Id: ColumnSync.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *  
 *  @author Teo Sarca
 *  	<li>BF [ 2854358 ] SyncColumn should load table in transaction
 *  		https://sourceforge.net/tracker/?func=detail&aid=2854358&group_id=176962&atid=879332
 */
public class ColumnSync extends JavaProcess implements IProcessPrecondition
{
	/** The Column				*/
	private int			p_AD_Column_ID = 0;

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
		p_AD_Column_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("C_Column_ID=" + p_AD_Column_ID);
		if (p_AD_Column_ID == 0)
		{
			throw new AdempiereUserError("@No@ @AD_Column_ID@");
		}

		MColumn column = new MColumn(getCtx(), p_AD_Column_ID, get_TrxName());
		
		if (column.get_ID() == 0)
		{
			throw new AdempiereUserError("@NotFound@ @AD_Column_ID@ " + p_AD_Column_ID);
		}
		
		String sql = column.syncDatabase();
		addLog(sql);
		return sql;
	}	//	doIt

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final I_AD_Column column = context.getSelectedModel(I_AD_Column.class);
		if(column == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		
		final String columnSQL = column.getColumnSQL();

		if (!Check.isEmpty(columnSQL, true))
		{
			return ProcessPreconditionsResolution.reject("virtual columns are not supported");
		}
		
		if(column.getAD_Table().isView())
		{
			return ProcessPreconditionsResolution.reject("views are not supported");
		}
		
		
		return ProcessPreconditionsResolution.accept();
	}

}	//	ColumnSync
