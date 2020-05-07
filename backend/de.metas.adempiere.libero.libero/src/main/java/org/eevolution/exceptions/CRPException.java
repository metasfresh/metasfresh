/**
 * 
 */
package org.eevolution.exceptions;

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


import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;

import de.metas.document.engine.IDocument;
import de.metas.material.planning.pporder.LiberoException;

/**
 * @author teo_sarca
 *
 */
public class CRPException extends LiberoException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1927337830932438508L;
	
	private I_PP_Order order = null;
	private I_PP_Order_Node node = null;
	private I_S_Resource resource = null;
	
	public CRPException(String message)
	{
		super(message);
	}
	
	public CRPException(Exception e)
	{
		super(e);
	}
	
	public CRPException setPP_Order(I_PP_Order order)
	{
		this.order = order;
		resetMessageBuilt();
		return this;
	}
	public CRPException setPP_Order_Node(I_PP_Order_Node node)
	{
		this.node = node;
		resetMessageBuilt();
		return this;
	}

	public CRPException setS_Resource(I_S_Resource resource)
	{
		this.resource  = resource;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected String buildMessage()
	{
		String msg = super.getMessage();
		StringBuffer sb = new StringBuffer(msg);
		//
		if (this.order != null)
		{
			final String info;
			if (order instanceof IDocument)
			{
				info = ((IDocument)order).getSummary();
			}
			else
			{
				info = ""+order.getDocumentNo()+"/"+order.getDatePromised();
			}
			sb.append(" @PP_Order_ID@:").append(info);
		}
		if (this.node != null)
		{
			sb.append(" @PP_Order_Node_ID@:").append(node.getValue()).append("_").append(node.getName());
		}
		if (this.resource != null)
		{
			sb.append(" @S_Resource_ID@:").append(resource.getValue()).append("_").append(resource.getName());
		}
		//
		return sb.toString();
	}
	
	
}
