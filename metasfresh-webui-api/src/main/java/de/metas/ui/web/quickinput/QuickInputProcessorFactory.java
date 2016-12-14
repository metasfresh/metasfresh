package de.metas.ui.web.quickinput;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
public class QuickInputProcessorFactory
{
	private final ConcurrentHashMap<String, Class<? extends IQuickInputProcessor>> tableName2quickInputProcessors = new ConcurrentHashMap<>();

	public QuickInputProcessorFactory()
	{
		super();

		// Defaults
		tableName2quickInputProcessors.put(I_C_OrderLine.Table_Name, OrderLineQuickInputProcessor.class);
	}

	public IQuickInputProcessor createProcessor(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		final Class<? extends IQuickInputProcessor> processorClass = tableName2quickInputProcessors.get(tableName);
		if (processorClass == null)
		{
			throw new IllegalArgumentException("No " + IQuickInputProcessor.class + " found for tableName=" + tableName);
		}

		try
		{
			return processorClass.newInstance();
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}
}
