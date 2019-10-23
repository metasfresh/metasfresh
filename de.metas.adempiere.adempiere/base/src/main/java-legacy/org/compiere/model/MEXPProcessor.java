/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 *********************************************************************/

package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.IExportProcessor;

import de.metas.cache.CCache;

/**
 * @author Trifon N. Trifonov
 */
public class MEXPProcessor extends X_EXP_Processor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2866411384427811427L;
	
	private static CCache<Integer, MEXPProcessor> s_cache = new CCache<Integer, MEXPProcessor>(Table_Name, 10, 0);
	private I_EXP_ProcessorParameter[] parameters = null;
	
	public static MEXPProcessor get(Properties ctx, int EXP_Processor_ID, String trxName)
	{
		if (trxName != null)
			return new MEXPProcessor(ctx, EXP_Processor_ID, trxName);
		
		MEXPProcessor processor = s_cache.get(EXP_Processor_ID);
		if (processor != null)
			return processor;
		processor = new MEXPProcessor(ctx, EXP_Processor_ID, trxName);
		if (processor.getEXP_Processor_ID() != EXP_Processor_ID)
		{
			processor = null;
		}
		
		s_cache.put(EXP_Processor_ID, processor);
		return processor;
	}
	
	public MEXPProcessor(Properties ctx, int EXP_Processor_ID, String trxName)
	{
		super(ctx, EXP_Processor_ID, trxName);
	}
	
	public MEXPProcessor(Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}
	
	public I_EXP_ProcessorParameter[] getEXP_ProcessorParameters()
	{    
	    if(parameters != null)
		return parameters;
	    
		final String whereClause = I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID+"=?";
		List<I_EXP_ProcessorParameter> list = new Query(getCtx(), I_EXP_ProcessorParameter.Table_Name, whereClause, get_TrxName())
		.setParameters(getEXP_Processor_ID())
		.setOnlyActiveRecords(true)
		.list(I_EXP_ProcessorParameter.class);
		parameters = list.toArray(new I_EXP_ProcessorParameter[list.size()]);
		return parameters;
	}

	
	public IExportProcessor getIExportProcessor()
	{
		I_EXP_Processor_Type type = getEXP_Processor_Type(); // TODO: caching 
		String classname = type.getJavaClass();
		try
		{
			return (IExportProcessor)getClass().getClassLoader().loadClass(classname).newInstance();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	private void deleteParameters()
	{
		for (I_EXP_ProcessorParameter para : getEXP_ProcessorParameters())
		{
			InterfaceWrapperHelper.delete(para);
		}
	}
	
	public I_EXP_ProcessorParameter createParameter(String key, String name, String desc, String help, String value)
	{
		final String whereClause = I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID+"=?"
			+" AND "+I_EXP_ProcessorParameter.COLUMNNAME_Value+"=?";
		I_EXP_ProcessorParameter para = new Query(this.getCtx(), I_EXP_ProcessorParameter.Table_Name, whereClause, this.get_TrxName())
		.setParameters(this.getEXP_Processor_ID(), key)
		.firstOnly(I_EXP_ProcessorParameter.class);
		if (para == null)
		{
			para = new X_EXP_ProcessorParameter(this.getCtx(), 0, this.get_TrxName());
			para.setEXP_Processor_ID(this.getEXP_Processor_ID());
			para.setValue(key);
		}
		para.setIsActive(true);
		para.setName(name);
		para.setDescription(desc);
		para.setHelp(help);
		para.setParameterValue(value);
		InterfaceWrapperHelper.save(para);
		return para;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		deleteParameters();
//		deleteLog(true);
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return false;
		
		if (newRecord || is_ValueChanged(COLUMNNAME_EXP_Processor_Type_ID))
		{
			deleteParameters();
			final IExportProcessor proc = getIExportProcessor();
			proc.createInitialParameters(this);
		}
		
		return true;
	}
}
