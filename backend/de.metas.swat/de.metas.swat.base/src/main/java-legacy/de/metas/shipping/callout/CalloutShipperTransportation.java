/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
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
package de.metas.shipping.callout;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.Properties;
import org.compiere.grid.ICreateFrom;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ini;


/**
 *	Shipper Transportation Callout
 *	
 *  @author Carlos Ruiz
 */
public class CalloutShipperTransportation extends CalloutEngine
{

	/**
	 *	Invoke the form VCreateFromPackage
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String createShippingPackages (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (mTab.getValue("M_ShipperTransportation_ID") == null || ((Integer) mTab.getValue("M_ShipperTransportation_ID")).intValue() <= 0)
			return "";
		
		String swingclassname = "de.metas.shipping.compiere.form.VCreateFromPackage";
		String zkclassname = "de.metas.shipping.adempiere.webui.form.WCreateFromPackage";
		String classname;
		if (Ini.isSwingClient())
			classname = swingclassname;
		else
			classname = zkclassname;
		
		ICreateFrom cf = null;
		Class cl;
		try {
			if (Ini.isSwingClient())
				cl = Class.forName(classname);
			else
				cl = Thread.currentThread().getContextClassLoader().loadClass(classname);
		} catch (ClassNotFoundException e) {
			log.error(e.getLocalizedMessage(), e);
			return e.getLocalizedMessage();
		}
		if (cl != null)
		{
			try
			{
				java.lang.reflect.Constructor<? extends ICreateFrom> ctor = cl.getConstructor(GridTab.class);
				cf = ctor.newInstance(mTab);
			}
			catch (Throwable e)
			{
				log.error(e.getLocalizedMessage(), e);
				return e.getLocalizedMessage();
			}
		}
		
		if (cf != null)
		{
			if (cf.isInitOK())
			{
				cf.showWindow();
				cf.closeWindow();
				mTab.dataRefresh();
			}
			else
				cf.closeWindow();
		}

		return "";
	}	//	createShippingPackages
	
}	//	CalloutShipperTransportation
