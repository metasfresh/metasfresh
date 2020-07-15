package de.metas.impex.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.metas.adempiere.util.Parameter;
import de.metas.impex.api.IInboundProcessorBL;
import de.metas.impex.api.impl.InboundProcessorBL;
import de.metas.impex.spi.IImportConnector;

public abstract class BaseConnector implements IImportConnector
{

	Map<String, Parameter> currentParams;

	final IInboundProcessorBL inboundProcessorBL = new InboundProcessorBL();

	@Override
	public final void open(final Collection<Parameter> params)
	{
		currentParams = mkMap(params);
		openSpecific();
	}

	public final void open(final Map<String, Parameter> params)
	{
		currentParams = params;
		openSpecific();
	}

	@Override
	public final void close()
	{
		currentParams = null;
		closeSpecific();
	}

	protected abstract void openSpecific();

	protected abstract void closeSpecific();

	public final Map<String, Parameter> getCurrentParams()
	{
		return currentParams;
	}

	@Override
	public Parameter getParameter(final String name)
	{
		return getCurrentParams().get(name);
	}

	@Override
	public boolean hasParameter(final String name)
	{

		return getCurrentParams().containsKey(name);
	}

	private static Map<String, Parameter> mkMap(Collection<Parameter> params)
	{

		final Map<String, Parameter> name2param = new HashMap<String, Parameter>(
				params.size());

		for (final Parameter param : params)
		{
			name2param.put(param.name, param);
		}
		return Collections.unmodifiableMap(name2param);
	}
}
