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

import de.metas.document.engine.IDocument;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.resource.Resource;
import lombok.NonNull;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;

/**
 * @author teo_sarca
 *
 */
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

	@Nullable private I_PP_Order order = null;
	@Nullable private PPOrderRoutingActivity orderActivity = null;
	@Nullable private Resource resource = null;

	public CRPException(final String message)
	{
		super(message);
	}

	public CRPException(final Throwable e)
	{
		super(e);
	}

	public CRPException setPP_Order(final I_PP_Order order)
	{
		this.order = order;
		resetMessageBuilt();
		return this;
	}

	public CRPException setOrderActivity(final PPOrderRoutingActivity orderActivity)
	{
		this.orderActivity = orderActivity;
		resetMessageBuilt();
		return this;
	}

	public CRPException setResource(@Nullable final Resource resource)
	{
		this.resource = resource;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder result = TranslatableStrings.builder();
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
			result.append(" ").appendADElement("PP_Order_ID").append(": ").append(info);
		}
		if (this.orderActivity != null)
		{
			result.append(" ").appendADElement("PP_Order_Node_ID").append(": ").append(orderActivity.toString());
		}
		if (this.resource != null)
		{
			result.append(" ").appendADElement("S_Resource_ID").append(": ").append(resource.getName());
		}
		//
		return result.build();
	}

}
