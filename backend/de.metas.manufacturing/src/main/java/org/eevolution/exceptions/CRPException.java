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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.model.I_S_Resource;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.I_PP_Order;

import de.metas.document.engine.IDocument;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.planning.pporder.LiberoException;
import lombok.NonNull;

/**
 * @author teo_sarca
 *
 */
@SuppressWarnings("serial")
public class CRPException extends LiberoException
{
	public static CRPException wrapIfNeeded(@NonNull final Throwable throwable)
	{
		final Throwable cause = extractCause(throwable);
		if (cause instanceof CRPException)
		{
			return (CRPException)cause;
		}
		else
		{
			return new CRPException(cause);
		}
	}

	private I_PP_Order order = null;
	private PPOrderRoutingActivity orderActivity = null;
	private I_S_Resource resource = null;

	public CRPException(String message)
	{
		super(message);
	}

	public CRPException(Throwable e)
	{
		super(e);
	}

	public CRPException setPP_Order(I_PP_Order order)
	{
		this.order = order;
		resetMessageBuilt();
		return this;
	}

	public CRPException setOrderActivity(PPOrderRoutingActivity orderActivity)
	{
		this.orderActivity = orderActivity;
		resetMessageBuilt();
		return this;
	}

	public CRPException setS_Resource(I_S_Resource resource)
	{
		this.resource = resource;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected ITranslatableString buildMessage()
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
				info = "" + order.getDocumentNo() + "/" + order.getDatePromised();
			}
			sb.append(" @PP_Order_ID@:").append(info);
		}
		if (this.orderActivity != null)
		{
			sb.append(" @PP_Order_Node_ID@:").append(orderActivity);
		}
		if (this.resource != null)
		{
			sb.append(" @S_Resource_ID@:").append(resource.getValue()).append("_").append(resource.getName());
		}
		//
		return TranslatableStrings.parse(sb.toString());
	}

}
