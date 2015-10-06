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

import java.util.Enumeration;
import java.util.Hashtable;

import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class ProcessInfoHandler {

	protected ProcessInfo pi;
	protected MPInstance pinstance;
	protected Hashtable param;
	protected MProcess process;
	
	public ProcessInfoHandler(int processID) {
	
		init(processID);
	}
	
	private void init(int processID) {
		
		process = new MProcess(Env.getCtx(), processID, null);

		if(process != null) {
			
			pi = getProcessInfo(Msg.translate(Env.getCtx(), process.getName()), process.get_ID());
			pinstance = getProcessInstance(pi);
			pi.setAD_PInstance_ID (pinstance.getAD_PInstance_ID());
		}
	}
	
	protected ProcessInfo getProcessInfo(String name, int id) {
		
		ProcessInfo info = new ProcessInfo(name, id);
		info.setAD_User_ID (Env.getAD_User_ID(Env.getCtx()));
		info.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
		
		return info;
	}
	
	protected MPInstance getProcessInstance(ProcessInfo info) {
		
		MPInstance instance = new MPInstance(Env.getCtx(), info);
		if (!instance.save()) {
			
			info.setSummary (Msg.getMsg(Env.getCtx(), "ProcessNoInstance"));
			info.setError (true);
			return null;
		}
		
		return instance;
	}

	protected int countParams() {
		
		return (process != null) ? process.getParameters().length : 0;
	}
	
	protected Hashtable extractParameters() {
		
		Hashtable param = new Hashtable();
		
		MPInstancePara p = null;
        int i = 0;
		int b = countParams();
		while(i < b) {
			
			p = new MPInstancePara(getProcessInstance(), i);
			p.load(null);

			param.put(p.getParameterName(), getValueFrom(p));
			i++;
		}

		return param;
	}
	
	protected Object getValueFrom(MPInstancePara p) {

		Object o = null;
		
		o = (o == null) ? p.getP_Date() : o;
		o = (o == null) ? p.getP_Date_To() : o;
		o = (o == null) ? p.getP_Number() : o;
		o = (o == null) ? p.getP_Number_To() : o;
		o = (o == null) ? p.getP_String() : o;
		o = (o == null) ? p.getP_String_To() : o;
		
		return o;
	}
	
	public void setProcessError() {
		
		pi.setSummary(Msg.getMsg(Env.getCtx(), "ProcessCancelled"));
		pi.setError(true);
	}
	
	public MPInstance getProcessInstance() {
		
		return pinstance;
	}

	public ProcessInfo getProcessInfo() {
		
		return pi;
	}
	
	public Object getParameterValue(String param) {
		
		if(this.param == null) {
			
			this.param = extractParameters();
		}
		
		return this.param.get(param);
	}
	
	public Enumeration getParameters() {
		
		if(this.param == null) {
			
			this.param = extractParameters();
		}

		return param.keys();
	}
}
