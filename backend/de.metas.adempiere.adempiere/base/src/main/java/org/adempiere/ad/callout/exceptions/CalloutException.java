package org.adempiere.ad.callout.exceptions;

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

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.exceptions.AdempiereException;

public class CalloutException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766621229698377244L;

	private ICalloutInstance calloutInstance = null;
	private ICalloutExecutor calloutExecutor = null;

	private ICalloutField field;

	public CalloutException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CalloutException(String message)
	{
		super(message);
	}

	public CalloutException(Throwable cause)
	{
		super(cause);
	}

	public ICalloutInstance getCalloutInstance()
	{
		return calloutInstance;
	}

	public CalloutException setCalloutInstance(ICalloutInstance calloutInstance)
	{
		this.calloutInstance = calloutInstance;
		return this;
	}

	public CalloutException setCalloutInstanceIfAbsent(ICalloutInstance calloutInstance)
	{
		if (this.calloutInstance == null)
		{
			this.calloutInstance = calloutInstance;
		}
		return this;
	}

	public ICalloutExecutor getCalloutExecutor()
	{
		return calloutExecutor;
	}

	public CalloutException setCalloutExecutor(ICalloutExecutor calloutExecutor)
	{
		this.calloutExecutor = calloutExecutor;
		return this;
	}

	public CalloutException setCalloutExecutorIfAbsent(ICalloutExecutor calloutExecutor)
	{
		if (this.calloutExecutor == null)
		{
			this.calloutExecutor = calloutExecutor;
		}
		return this;
	}

	public CalloutException setField(final ICalloutField field)
	{
		this.field = field;
		return this;
	}

	public CalloutException setFieldIfAbsent(final ICalloutField field)
	{
		if (this.field == null)
		{
			this.field = field;
		}
		return this;
	}

	public ICalloutField getCalloutField()
	{
		return field;
	}
}
