package org.adempiere.ad.callout.api.impl.legacy;

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
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.util.Util;

/**
 * Wraps a legacy {@link org.compiere.model.Callout} to {@link ICallout}.
 * 
 * @author tsa
 * 
 */
public class LegacyCalloutAdapter implements ICallout
{
	private final org.compiere.model.Callout callout;
	private final String methodName;

	public LegacyCalloutAdapter(final String methodNameFQ)
	{
		super();

		final String classname;
		final String methodName;

		int methodStart = methodNameFQ.lastIndexOf('.');
		if (methodStart != -1)      // no class
		{
			classname = methodNameFQ.substring(0, methodStart);
			methodName = methodNameFQ.substring(methodStart + 1);
		}
		else
		{
			throw new CalloutInitException("Invalid callout: " + methodNameFQ);
		}

		if (Check.isEmpty(classname, true))
		{
			throw new CalloutInitException("Invalid classname  for " + methodNameFQ);
		}
		if (Check.isEmpty(methodName, true))
		{
			throw new CalloutInitException("Invalid methodName for " + methodNameFQ);
		}

		org.compiere.model.Callout callout;
		try
		{
			callout = Util.getInstance(org.compiere.model.Callout.class, classname);
		}
		catch (Exception e)
		{
			throw new CalloutInitException("Cannot load callout class for " + methodNameFQ, e);
		}
		Check.assumeNotNull(callout, "callout not null");

		this.callout = callout;
		this.methodName = methodName;
	}

	public LegacyCalloutAdapter(final org.compiere.model.Callout callout, final String methodName)
	{
		super();
		Check.assumeNotNull(callout, "callout not null");
		this.callout = callout;

		Check.assumeNotEmpty(methodName, "methodName not empty");
		this.methodName = methodName.trim();
	}

	@Override
	public String toString()
	{
		return "LegacyCalloutInstance["
				+ "callout=" + callout
				+ ", methodName=" + methodName
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(callout)
				.append(methodName)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final LegacyCalloutAdapter other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.callout, other.callout)
				.append(this.methodName, other.methodName)
				.isEqual();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onFieldChanged(final ICalloutExecutor executor, final ICalloutField field)
	{
		callout.start(methodName, field);
	}

	public org.compiere.model.Callout getCallout()
	{
		return callout;
	}

	public String getMethodName()
	{
		return methodName;
	}
}
