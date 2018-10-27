package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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
import java.util.List;

import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningCandidateProducerFactory;
import de.metas.dunning.exception.DunningException;
import de.metas.util.Check;

public class DefaultDunningCandidateProducerFactory implements IDunningCandidateProducerFactory
{
	private final List<Class<? extends IDunningCandidateProducer>> producerClasses = new ArrayList<Class<? extends IDunningCandidateProducer>>();

	private final List<IDunningCandidateProducer> producers = new ArrayList<IDunningCandidateProducer>();

	@Override
	public void registerDunningCandidateProducer(final Class<? extends IDunningCandidateProducer> clazz)
	{
		if (producerClasses.contains(clazz))
		{
			return;
		}

		producerClasses.add(clazz);

		final IDunningCandidateProducer producer = createProducer(clazz);
		producers.add(producer);
	}

	public List<Class<? extends IDunningCandidateProducer>> getProducerClasses()
	{
		return new ArrayList<Class<? extends IDunningCandidateProducer>>(producerClasses);
	}

	private IDunningCandidateProducer createProducer(final Class<? extends IDunningCandidateProducer> clazz)
	{
		try
		{
			final IDunningCandidateProducer producer = clazz.newInstance();
			return producer;
		}
		catch (Exception e)
		{
			throw new DunningException("Cannot create producer for " + clazz, e);
		}

	}

	@Override
	public IDunningCandidateProducer getDunningCandidateProducer(final IDunnableDoc sourceDoc)
	{
		Check.assume(sourceDoc != null, "sourceDoc is not null");

		IDunningCandidateProducer selectedProducer = null;
		for (final IDunningCandidateProducer producer : producers)
		{
			if (!producer.isHandled(sourceDoc))
			{
				continue;
			}

			if (selectedProducer != null)
			{
				throw new DunningException("Multiple producers found for " + sourceDoc + ": " + selectedProducer + ", " + producer);
			}

			selectedProducer = producer;
		}

		if (selectedProducer == null)
		{
			throw new DunningException("No " + IDunningCandidateProducer.class + " found for " + sourceDoc);
		}

		return selectedProducer;
	}

	@Override
	public String toString()
	{
		return "DefaultDunningCandidateProducerFactory [producerClasses=" + producerClasses + ", producers=" + producers + "]";
	}
}
