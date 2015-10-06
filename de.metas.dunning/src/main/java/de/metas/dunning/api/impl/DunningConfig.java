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


import org.adempiere.util.Check;

import de.metas.dunning.api.IDunnableSourceFactory;
import de.metas.dunning.api.IDunningCandidateProducerFactory;
import de.metas.dunning.api.IDunningEditableConfig;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.spi.IDunningCandidateSource;

public class DunningConfig implements IDunningEditableConfig
{
	private IDunnableSourceFactory dunnableSourceFactory;
	private IDunningCandidateProducerFactory dunningCandidateProducerFactory;
	private Class<? extends IDunningCandidateSource> dunningCandidateSourceClass;
	private Class<? extends IDunningProducer> dunningProducerClass;

	public DunningConfig()
	{
		// Set defaults
		dunnableSourceFactory = new DefaultDunnableSourceFactory();
		dunningCandidateProducerFactory = new DefaultDunningCandidateProducerFactory();
		dunningCandidateSourceClass = DefaultDunningCandidateSource.class;
		dunningProducerClass = DefaultDunningProducer.class;
	}

	@Override
	public IDunnableSourceFactory getDunnableSourceFactory()
	{
		return dunnableSourceFactory;
	}

	@Override
	public void setDunnableSourceFactory(IDunnableSourceFactory dunnableSourceFactory)
	{
		Check.assume(dunnableSourceFactory != null, "dunnableSourceFactory is not null");
		this.dunnableSourceFactory = dunnableSourceFactory;
	}

	@Override
	public IDunningCandidateProducerFactory getDunningCandidateProducerFactory()
	{
		return dunningCandidateProducerFactory;
	}

	@Override
	public void setDunningCandidateProducerFactory(IDunningCandidateProducerFactory dunningCandidateProducerFactory)
	{
		Check.assume(dunningCandidateProducerFactory != null, "dunningCandidateProducerFactory is not null");
		this.dunningCandidateProducerFactory = dunningCandidateProducerFactory;
	}

	@Override
	public IDunningProducer createDunningProducer()
	{
		try
		{
			final IDunningProducer producer = dunningProducerClass.newInstance();
			return producer;
		}
		catch (Exception e)
		{
			throw new DunningException("Cannot create " + IDunningProducer.class + " for " + dunningProducerClass, e);
		}
	}

	@Override
	public void setDunningProducerClass(Class<IDunningProducer> dunningProducerClass)
	{
		Check.assume(dunningProducerClass != null, "dunningProducerClass is not null");
		this.dunningProducerClass = dunningProducerClass;
	}

	@Override
	public IDunningCandidateSource createDunningCandidateSource()
	{
		try
		{
			final IDunningCandidateSource source = dunningCandidateSourceClass.newInstance();
			return source;
		}
		catch (Exception e)
		{
			throw new DunningException("Cannot create " + IDunningCandidateSource.class + " for " + dunningCandidateSourceClass, e);
		}
	}

	@Override
	public void setDunningCandidateSourceClass(Class<? extends IDunningCandidateSource> dunningCandidateSourceClass)
	{
		Check.assume(dunningCandidateSourceClass != null, "dunningCandidateSourceClass is not null");
		this.dunningCandidateSourceClass = dunningCandidateSourceClass;
	}

	@Override
	public String toString()
	{
		return "DunningConfig ["
				+ "dunnableSourceFactory=" + dunnableSourceFactory
				+ ", dunningCandidateProducerFactory=" + dunningCandidateProducerFactory
				+ ", dunningCandidateSourceClass=" + dunningCandidateSourceClass
				+ ", dunningProducerClass=" + dunningProducerClass
				+ "]";
	}
}
