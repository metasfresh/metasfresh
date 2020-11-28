package org.adempiere.ad.callout.spi.impl;

import java.util.Properties;

import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.ICalloutProvider;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * A {@link ICalloutProvider} which supplies no callouts.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class NullCalloutProvider implements ICalloutProvider
{
	public static final transient NullCalloutProvider instance = new NullCalloutProvider();

	public static final boolean isNull(final ICalloutProvider provider)
	{
		return provider == null || provider == instance;
	}

	private NullCalloutProvider()
	{
		super();
	}

	@Override
	public TableCalloutsMap getCallouts(Properties ctx, String tableName)
	{
		return TableCalloutsMap.EMPTY;
	}

}
