/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.process;

import org.adempiere.webui.util.ADClassNameMap;
import org.compiere.process.ProcessInfo;

/**
 * @author hengsin
 *
 */
public class WProcessInfo extends ProcessInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2034681838152013686L;

	/**
	 * @param Title
	 * @param AD_Process_ID
	 * @param Table_ID
	 * @param Record_ID
	 */
	public WProcessInfo(String Title, int AD_Process_ID, int Table_ID,
			int Record_ID) {
		super(Title, AD_Process_ID, Table_ID, Record_ID);
	}

	/**
	 * @param Title
	 * @param AD_Process_ID
	 */
	public WProcessInfo(String Title, int AD_Process_ID) {
		super(Title, AD_Process_ID);
	}

	@Override
	public void setClassName(String className) {
		String zkName = null;
		if (className != null && className.trim().length() > 0) {
			zkName = ADClassNameMap.get(className);
			if (zkName == null)
			{
				zkName = dynamicTranslate(className);
			}
		}
		if (zkName == null)
			zkName = className;
		super.setClassName(zkName);
	}

	private String dynamicTranslate(String className) {
		String zkName = null;
		String tail = null;
		
		//null check
		if (className == null || className.trim().length() == 0)
			return null;
		
		String zkPackage = "org.adempiere.webui.";
		String zkPrefix = "W";
		
		//first, try replace package
		if (className.startsWith("org.compiere."))
		{
			tail = className.substring("org.compiere.".length());
		}
		else if(className.startsWith("org.adempiere."))
		{
			tail = className.substring("org.adempiere.".length());
		}
		if (tail != null)
		{
			zkName = zkPackage + tail;
			try {
				this.getClass().getClassLoader().loadClass(zkName);
			} catch (ClassNotFoundException e) {
				zkName = null;
			}
			
			//try replace package and add W prefix to class name
			if (zkName == null)
			{
				zkName = zkPackage;
				int lastdot = tail.lastIndexOf(".");						
				if (lastdot >= 0)
				{
					if (lastdot > 0)
						zkName = zkName + tail.substring(0, lastdot+1);
					zkName = zkName + zkPrefix + tail.substring(lastdot+1);
				}
				else
				{
					zkName = zkName + zkPrefix + tail;
				}
				try { 
					this.getClass().getClassLoader().loadClass(zkName);
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}
		}
		
		//try append W prefix to class name
		if (zkName == null)
		{
			int lastdot = className.lastIndexOf(".");
			zkName = className.substring(0, lastdot) + ".W" +  className.substring(lastdot+1);
			try {
				this.getClass().getClassLoader().loadClass(zkName);
			} catch (ClassNotFoundException e) {
				zkName = null;
			}
		}
		return zkName;
	}
}
