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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.Util.ArrayKey;

public class CalloutExecutor implements ICalloutExecutor
{
	private final CLogger logger = CLogger.getCLogger(getClass());

	private final Properties ctx;
	private final int windowNo;

	private final Map<ArrayKey, List<ICalloutInstance>> calloutInstances = new HashMap<ArrayKey, List<ICalloutInstance>>();
	private final List<ICalloutInstance> activeCalloutInstances = new ArrayList<ICalloutInstance>();

	private ICalloutFactory calloutFactory = null;

	public CalloutExecutor(final Properties ctx, final int windowNo)
	{
		super();

		this.ctx = ctx;
		this.windowNo = windowNo;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public int getWindowNo()
	{
		return windowNo;
	}

	public void setCalloutFactory(ICalloutFactory calloutFactory)
	{
		this.calloutFactory = calloutFactory;
	}

	private ICalloutFactory getCalloutFactoryToUse()
	{
		if (calloutFactory != null)
		{
			return calloutFactory;
		}
		return Services.get(ICalloutFactory.class);
	}

	@Override
	public void execute(final ICalloutField field)
	{
		if (!isEligibleForExecuting(field))
		{
			return;
		}

		final List<ICalloutInstance> fieldCalloutInstances = getCalloutInstances(field);
		if (fieldCalloutInstances.isEmpty())
		{
			return;
		}

		for (final Iterator<ICalloutInstance> it = fieldCalloutInstances.iterator(); it.hasNext();)
		{
			final ICalloutInstance fieldCalloutInstance = it.next();
			try
			{
				execute(fieldCalloutInstance, field);
			}
			catch (CalloutInitException e)
			{
				logger.log(Level.SEVERE, "Callout " + fieldCalloutInstance + " failed with init error on execution. Discarding it.");
				it.remove();
				throw e;
			}
		}
	}

	private boolean isEligibleForExecuting(final ICalloutField field)
	{
		if (field == null)
		{
			// shall not happen
			logger.log(Level.WARNING, "field is null", new Exception());
			return false;
		}

		return field.isTriggerCalloutAllowed();
	}

	private void execute(final ICalloutInstance fieldCalloutInstance, final ICalloutField field)
	{
		// detect infinite loop
		if (activeCalloutInstances.contains(fieldCalloutInstance))
		{
			logger.log(Level.FINE, "Skip callout {0} because is already active", fieldCalloutInstance);
			return;
		}

		final Object value = field.getValue();
		final Object valueOld = field.getOldValue();

		logger.log(Level.FINE, "{0}={1} - old={2}, callout={3}", new Object[] { field, value, valueOld, fieldCalloutInstance });

		try
		{
			activeCalloutInstances.add(fieldCalloutInstance);

			fieldCalloutInstance.execute(this, field);
		}
		catch (CalloutException e)
		{
			throw e.setCalloutExecutor(this)
					.setCalloutInstance(fieldCalloutInstance);
		}
		catch (Exception e)
		{
			throw new CalloutExecutionException(fieldCalloutInstance, e.getLocalizedMessage(), e)
					.setCalloutExecutor(this);
		}
		finally
		{
			activeCalloutInstances.remove(fieldCalloutInstance);
		}

	}

	@Override
	public List<ICalloutInstance> getActiveCalloutInstances()
	{
		return new ArrayList<ICalloutInstance>(activeCalloutInstances);
	}

	@Override
	public boolean hasCallouts(final ICalloutField field)
	{
		if (field == null)
		{
			return false;
		}

		final List<ICalloutInstance> instances = getCalloutInstances(field);
		return !instances.isEmpty();
	}

	/* package */List<ICalloutInstance> getCalloutInstances(final ICalloutField field)
	{
		final ArrayKey key = new ArrayKey(field.getAD_Table_ID(), field.getAD_Column_ID());
		List<ICalloutInstance> fieldCalloutInstances = calloutInstances.get(key);
		if (fieldCalloutInstances == null)
		{
			fieldCalloutInstances = getCalloutFactoryToUse().getCallouts(field);
			calloutInstances.put(key, fieldCalloutInstances);
		}
		return fieldCalloutInstances;
	}
}
