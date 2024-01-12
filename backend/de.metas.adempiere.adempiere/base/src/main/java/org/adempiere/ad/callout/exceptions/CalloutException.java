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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.Getter;
import lombok.NonNull;
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

	@Getter private ICalloutInstance calloutInstance = null;
	@Getter private ICalloutExecutor calloutExecutor = null;

	private ICalloutField field;

	public CalloutException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	protected CalloutException(final String message)
	{
		super(message);
	}

	@Override
	public CalloutException setParameter(final @NonNull String name, final Object value)
	{
		super.setParameter(name, value);
		return this;
	}

	public CalloutException setCalloutInstance(final ICalloutInstance calloutInstance)
	{
		this.calloutInstance = calloutInstance;
		setParameter("calloutInstance", calloutInstance);
		return this;
	}

	public CalloutException setCalloutInstanceIfAbsent(final ICalloutInstance calloutInstance)
	{
		if (this.calloutInstance == null)
		{
			setCalloutInstance(calloutInstance);
		}
		return this;
	}

	public CalloutException setCalloutExecutor(final ICalloutExecutor calloutExecutor)
	{
		this.calloutExecutor = calloutExecutor;
		return this;
	}

	public CalloutException setCalloutExecutorIfAbsent(final ICalloutExecutor calloutExecutor)
	{
		if (this.calloutExecutor == null)
		{
			setCalloutExecutor(calloutExecutor);
		}
		return this;
	}

	public CalloutException setField(final ICalloutField field)
	{
		this.field = field;
		setParameter("field", field);
		return this;
	}

	public CalloutException setFieldIfAbsent(final ICalloutField field)
	{
		if (this.field == null)
		{
			setField(field);
		}
		return this;
	}

	public ICalloutField getCalloutField()
	{
		return field;
	}
}
