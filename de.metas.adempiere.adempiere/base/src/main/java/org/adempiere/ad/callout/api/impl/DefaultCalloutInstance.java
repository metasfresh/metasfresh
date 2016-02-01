package org.adempiere.ad.callout.api.impl;

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


import org.adempiere.ad.callout.api.ICallout;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.impl.legacy.LegacyCalloutAdapter;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.util.Util;

public class DefaultCalloutInstance implements ICalloutInstance
{
	// private static final transient CLogger logger = CLogger.getCLogger(DefaultCalloutInstance.class);

	private final String id;
	private ICallout callout;

	public DefaultCalloutInstance(final String classname)
	{
		super();

		Check.assumeNotEmpty(classname, "classname not empty");
		this.callout = createCallout(classname.trim());
		this.id = getClass().getSimpleName() + "-" + classname.trim();
	}

	public DefaultCalloutInstance(final ICallout callout)
	{
		super();

		Check.assumeNotNull(callout, "callout not empty");
		this.callout = callout;
		this.id = getClass().getSimpleName() + "-" + callout.getClass().getName();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "callout=" + callout
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(id)
				.append(callout)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final DefaultCalloutInstance other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.id, other.id)
				.append(this.callout, other.callout)
				.isEqual();
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field)
	{
		try
		{
			callout.onFieldChanged(executor, field);
		}
		catch (CalloutException e)
		{
			throw e.setCalloutExecutor(executor)
					.setCalloutInstance(this)
					.setField(field);
		}
		catch (Exception e)
		{
			throw new CalloutExecutionException(e.getLocalizedMessage(), e)
					.setCalloutExecutor(executor)
					.setCalloutInstance(this)
					.setField(field);
		}
	}

	private static ICallout createCallout(final String classname)
	{
		//
		// First try: check if we are dealing with a legacy callout
		// NOTE: we are checking first because ATM those are majority
		Exception errorLoadLegacy = null;
		try
		{
			// NOTE: in case of legacy callouts the classname is actually a FQ method name
			final String methodNameFQ = classname;
			final ICallout callout = new LegacyCalloutAdapter(methodNameFQ);
			if (callout != null)
			{
				return callout;
			}
		}
		catch (Exception e)
		{
			errorLoadLegacy = e;
		}

		//
		// Second try: we are dealing with ICallout
		try
		{
			final ICallout callout = Util.getInstanceOrNull(ICallout.class, classname);
			if (callout != null)
			{
				return callout;
			}
		}
		catch (Exception e)
		{
			// NOTE: we are throwing CalloutInitException instead of CalloutExecutionException
			// because this one is a permanent error which won't change next time when we run the callout
			throw new CalloutInitException("Cannot instantiate class " + classname, e);
		}

		//
		// Fallback
		throw new CalloutInitException("Error loading callout: " + classname, errorLoadLegacy);
	}

	public ICallout getCallout()
	{
		return callout;
	}
}
