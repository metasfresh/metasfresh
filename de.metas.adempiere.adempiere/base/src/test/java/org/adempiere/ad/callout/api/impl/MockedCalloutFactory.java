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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.compiere.util.Util.ArrayKey;
import org.junit.Ignore;

@Ignore
public class MockedCalloutFactory implements ICalloutFactory
{
	private final Map<ArrayKey, List<ICalloutInstance>> calloutsMap = new HashMap<ArrayKey, List<ICalloutInstance>>();

	@Override
	public List<ICalloutInstance> getCallouts(ICalloutField field)
	{
		final ArrayKey key = mkKey(field);
		final List<ICalloutInstance> callouts = calloutsMap.get(key);
		if (callouts == null)
		{
			return Collections.emptyList();
		}
		return callouts;
	}

	public void regiterCallout(ICalloutField field, ICalloutInstance callout)
	{
		final ArrayKey key = mkKey(field);
		List<ICalloutInstance> callouts = calloutsMap.get(key);
		if (callouts == null)
		{
			callouts = new ArrayList<ICalloutInstance>();
			calloutsMap.put(key, callouts);
		}

		callouts.add(callout);
	}

	private ArrayKey mkKey(final ICalloutField field)
	{
		return new ArrayKey(field.getAD_Table_ID(), field.getAD_Column_ID());
	}

	@Override
	public void registerCalloutProvider(ICalloutProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ICalloutProvider> getCalloutProviders()
	{
		return Collections.emptyList();
	}

}
