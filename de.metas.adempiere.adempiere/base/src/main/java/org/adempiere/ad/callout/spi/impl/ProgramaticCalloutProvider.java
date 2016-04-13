package org.adempiere.ad.callout.spi.impl;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstance;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstanceFactory;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.ICalloutInstanceFactory;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class ProgramaticCalloutProvider implements ICalloutProvider, IProgramaticCalloutProvider
{
	private static final transient Logger logger = LogManager.getLogger(ProgramaticCalloutProvider.class);

	private final Map<ArrayKey, List<ICalloutInstance>> registeredCallouts = new HashMap<ArrayKey, List<ICalloutInstance>>();

	private void registerCalloutProviderToCalloutFactory()
	{
		Services.get(ICalloutFactory.class).registerCalloutProvider(this);
	}

	@Override
	public List<ICalloutInstance> getCallouts(final ICalloutField field)
	{
		final ArrayKey key = mkKey(field.getAD_Table_ID(), field.getColumnName());
		final List<ICalloutInstance> callouts = registeredCallouts.get(key);
		if (callouts == null || callouts.isEmpty())
		{
			return Collections.emptyList();
		}

		return new ArrayList<ICalloutInstance>(callouts);
	}

	@Override
	public boolean registerCallout(final String tableName, final String columnName, String methodNameFQ)
	{
		Check.assumeNotNull(methodNameFQ, "methodNameFQ not null");
		final ICalloutInstance callout = createCalloutFromMethodNameFQ(methodNameFQ);

		return registerCallout(tableName, columnName, callout);
	}

	@Override
	public boolean registerCallout(final String tableName, final String columnName, final ICalloutInstance callout)
	{
		Check.assumeNotNull(tableName, "TableName not null");
		Check.assumeNotNull(columnName, "ColumnName not null");
		Check.assumeNotNull(callout, "callout not null");

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		if (tableId <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@: " + tableName);
		}

		final ArrayKey key = mkKey(tableId, columnName);

		List<ICalloutInstance> callouts = registeredCallouts.get(key);
		if (callouts == null)
		{
			callouts = new ArrayList<ICalloutInstance>();
			registeredCallouts.put(key, callouts);
		}

		if (isCalloutRegistered(callouts, callout))
		{
			logger.info("Callout {} was already registered. Skip.", callout);
			return false;
		}

		callouts.add(callout);
		logger.debug("Callout '{}' registered on {}.{}", new Object[] { callout, tableName, columnName });

		// Make sure this provider is registered to ICalloutFactory
		registerCalloutProviderToCalloutFactory();

		return true;
	}

	@Override
	public boolean registerAnnotatedCallout(final Object annotatedCalloutObj)
	{
		final List<AnnotatedCalloutInstance> calloutInstances = new AnnotatedCalloutInstanceFactory()
				.setAnnotatedCalloutObject(annotatedCalloutObj)
				.create();
		
		if (calloutInstances.isEmpty())
		{
			throw new AdempiereException("No binding columns found for " + annotatedCalloutObj + " (class=" + annotatedCalloutObj.getClass() + ")");
		}

		boolean registered = false;
		for (final AnnotatedCalloutInstance calloutInstance : calloutInstances)
		{
			final String tableName = calloutInstance.getTableName();
			for (final String columnName : calloutInstance.getColumnNames())
			{
				final boolean columNameRegistered = registerCallout(tableName, columnName, calloutInstance);
				if (columNameRegistered)
				{
					registered = true;
				}
			}
		}
		
		return registered;
	}

	private final boolean isCalloutRegistered(final List<ICalloutInstance> callouts, final ICalloutInstance callout)
	{
		final String calloutId = callout.getId();
		for (final ICalloutInstance ci : callouts)
		{
			if (Check.equals(calloutId, ci.getId()))
			{
				return true;
			}
		}

		return false;
	}

	private ArrayKey mkKey(final int adTableId, final String columnName)
	{
		return Util.mkKey(adTableId, columnName);
	}

	private ICalloutInstance createCalloutFromMethodNameFQ(final String methodNameFQ)
	{
		return Services.get(ICalloutInstanceFactory.class)
				.createFromString(methodNameFQ);
	}
}
