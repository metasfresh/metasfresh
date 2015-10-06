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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.spi.AsyncReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.impl.OrderLineReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.impl.OrderReceiptScheduleProducer;

public class ReceiptScheduleProducerFactory implements IReceiptScheduleProducerFactory
{
	private final Map<String, List<Class<? extends IReceiptScheduleProducer>>> producerClasses = new HashMap<String, List<Class<? extends IReceiptScheduleProducer>>>();

	public ReceiptScheduleProducerFactory()
	{
		super();

		// Register standard producers:
		registerProducer(I_C_Order.Table_Name, OrderReceiptScheduleProducer.class);
		registerProducer(I_C_OrderLine.Table_Name, OrderLineReceiptScheduleProducer.class);
	}

	@Override
	public IReceiptScheduleProducer createProducer(final String tableName, final boolean async)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		if (async)
		{
			return new AsyncReceiptScheduleProducer();
		}

		final List<Class<? extends IReceiptScheduleProducer>> producerClassesForTable = producerClasses.get(tableName);
		if (producerClassesForTable == null || producerClassesForTable.isEmpty())
		{
			throw new AdempiereException("No " + IReceiptScheduleProducer.class + " implementation was found for " + tableName);
		}

		final CompositeReceiptScheduleProducerExecutor compositeProducer = new CompositeReceiptScheduleProducerExecutor();
		compositeProducer.setReceiptScheduleProducerFactory(this);
		for (final Class<? extends IReceiptScheduleProducer> producerClass : producerClassesForTable)
		{
			try
			{
				final IReceiptScheduleProducer producer = producerClass.newInstance();
				producer.setReceiptScheduleProducerFactory(this);
				compositeProducer.addReceiptScheduleProducer(producer);
			}
			catch (Exception e)
			{
				throw new AdempiereException("Cannot instantiate producer class " + producerClass, e);
			}
		}

		return compositeProducer;
	}

	@Override
	public void registerProducer(final String tableName, final Class<? extends IReceiptScheduleProducer> producerClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(producerClass, "producerClass not null");

		List<Class<? extends IReceiptScheduleProducer>> producerClassesForTable = producerClasses.get(tableName);
		if (producerClassesForTable == null)
		{
			producerClassesForTable = new ArrayList<Class<? extends IReceiptScheduleProducer>>();
			producerClasses.put(tableName, producerClassesForTable);
		}

		if (!producerClassesForTable.contains(producerClass))
		{
			producerClassesForTable.add(producerClass);
		}
	}
}
