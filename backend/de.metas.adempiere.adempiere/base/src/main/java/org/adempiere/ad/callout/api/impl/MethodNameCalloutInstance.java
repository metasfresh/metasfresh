package org.adempiere.ad.callout.api.impl;

import java.util.Objects;
import java.util.function.Supplier;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.util.Util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

public final class MethodNameCalloutInstance implements ICalloutInstance
{
	public static final Supplier<ICalloutInstance> supplier(final String methodNameFQ)
	{
		Check.assumeNotEmpty(methodNameFQ, "methodNameFQ not empty");

		final String classname;
		final String methodName;

		final int methodStartIdx = methodNameFQ.lastIndexOf('.');
		if (methodStartIdx != -1)          // no class
		{
			classname = methodNameFQ.substring(0, methodStartIdx);
			methodName = methodNameFQ.substring(methodStartIdx + 1);
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

		final String id = MethodNameCalloutInstance.class.getSimpleName() + "-" + methodNameFQ.trim();
		return () -> {
			try
			{
				final org.compiere.model.Callout legacyCallout = Util.getInstance(org.compiere.model.Callout.class, classname);
				return new MethodNameCalloutInstance(id, legacyCallout, methodName);
			}
			catch (final Exception e)
			{
				throw new CalloutInitException("Cannot load callout class for " + methodNameFQ, e);
			}
		};
	}

	private final String id;
	private final org.compiere.model.Callout legacyCallout;
	private final String methodName;

	private MethodNameCalloutInstance(final String id, final org.compiere.model.Callout legacyCallout, final String methodName)
	{
		super();
		this.id = id;

		Check.assumeNotNull(legacyCallout, "Parameter legacyCallout is not null");
		this.legacyCallout = legacyCallout;

		Check.assumeNotNull(methodName, "Parameter methodName is not null");
		this.methodName = methodName;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final MethodNameCalloutInstance other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return id.equals(other.id);
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field)
	{
		try
		{
			legacyCallout.start(methodName, field);
		}
		catch (final CalloutException e)
		{
			throw e.setCalloutExecutor(executor)
					.setCalloutInstance(this)
					.setField(field);
		}
		catch (final Exception e)
		{
			throw new CalloutExecutionException(e.getLocalizedMessage(), e)
					.setCalloutExecutor(executor)
					.setCalloutInstance(this)
					.setField(field);
		}
	}
	
	@VisibleForTesting
	public org.compiere.model.Callout getLegacyCallout()
	{
		return legacyCallout;
	}

	public String getMethodName()
	{
		return methodName;
	}
}
