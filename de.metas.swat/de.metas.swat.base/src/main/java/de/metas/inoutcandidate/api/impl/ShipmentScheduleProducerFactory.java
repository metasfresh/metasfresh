package de.metas.inoutcandidate.api.impl;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.inoutcandidate.api.IShipmentScheduleProducerFactory;
import de.metas.inoutcandidate.spi.IShipmentScheduleProducer;
import de.metas.inoutcandidate.spi.impl.CompositeShipmentScheduleProducer;

public class ShipmentScheduleProducerFactory implements IShipmentScheduleProducerFactory
{
	private final Map<String, List<Class<? extends  IShipmentScheduleProducer>>> producerClasses = new HashMap<String, List<Class<? extends  IShipmentScheduleProducer>>>();

	@Override
	public IShipmentScheduleProducer createProducer(final String tableName, final boolean async)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		Check.errorIf(async, "async=true is not yet implemented");
		if (async)
		{
			// here will be the asynchronous implementation
		}

		final List<Class<? extends  IShipmentScheduleProducer>> producerClassesForTable = producerClasses.get(tableName);
		if (producerClassesForTable == null || producerClassesForTable.isEmpty())
		{
			throw new AdempiereException("No " +  IShipmentScheduleProducer.class + " implementation was found for " + tableName);
		}

		final CompositeShipmentScheduleProducer compositeProducer = new CompositeShipmentScheduleProducer();
		for (final Class<? extends IShipmentScheduleProducer> producerClass : producerClassesForTable)
		{
			try
			{
				final IShipmentScheduleProducer producer = producerClass.newInstance();
				compositeProducer.addShipmentScheduleProducer(producer);
			}
			catch (Exception e)
			{
				throw new AdempiereException("Cannot instantiate producer class " + producerClass, e);
			}
		}

		return compositeProducer;
	}

	@Override
	public void registerProducer(final String tableName, final Class<? extends  IShipmentScheduleProducer> producerClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(producerClass, "producerClass not null");

		List<Class<? extends  IShipmentScheduleProducer>> producerClassesForTable = producerClasses.get(tableName);
		if (producerClassesForTable == null)
		{
			producerClassesForTable = new ArrayList<Class<? extends  IShipmentScheduleProducer>>();
			producerClasses.put(tableName, producerClassesForTable);
		}

		if (!producerClassesForTable.contains(producerClass))
		{
			producerClassesForTable.add(producerClass);
		}
	}
}
