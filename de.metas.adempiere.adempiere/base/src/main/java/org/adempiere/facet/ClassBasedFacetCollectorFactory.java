package org.adempiere.facet;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.facet.impl.CompositeFacetCollector;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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
 * {@link IFacetCollector} factory which is creating a composite {@link IFacetCollector} based on registered classes.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ModelType>
 */
public class ClassBasedFacetCollectorFactory<ModelType>
{
	// services
	private static final transient Logger defaultLogger = LogManager.getLogger(ClassBasedFacetCollectorFactory.class);
	private transient Logger logger = defaultLogger;

	private final CopyOnWriteArrayList<Class<? extends IFacetCollector<ModelType>>> facetCollectorClasses = new CopyOnWriteArrayList<>();

	public ClassBasedFacetCollectorFactory()
	{
		super();
	}

	public ClassBasedFacetCollectorFactory<ModelType> setLogger(final Logger logger)
	{
		Check.assumeNotNull(logger, "logger not null");
		this.logger = logger;
		return this;
	}

	public ClassBasedFacetCollectorFactory<ModelType> registerFacetCollectorClass(final Class<? extends IFacetCollector<ModelType>> facetCollectorClass)
	{
		Check.assumeNotNull(facetCollectorClass, "facetCollectorClass not null");
		facetCollectorClasses.addIfAbsent(facetCollectorClass);

		return this;
	}

	@SafeVarargs
	public final ClassBasedFacetCollectorFactory<ModelType> registerFacetCollectorClasses(final Class<? extends IFacetCollector<ModelType>>... facetCollectorClasses)
	{
		if (facetCollectorClasses == null || facetCollectorClasses.length == 0)
		{
			return this;
		}

		for (final Class<? extends IFacetCollector<ModelType>> facetCollectorClass : facetCollectorClasses)
		{
			registerFacetCollectorClass(facetCollectorClass);
		}

		return this;
	}

	public IFacetCollector<ModelType> createFacetCollectors()
	{
		final CompositeFacetCollector<ModelType> collectors = new CompositeFacetCollector<>();
		for (final Class<? extends IFacetCollector<ModelType>> collectorClass : facetCollectorClasses)
		{
			try
			{
				final IFacetCollector<ModelType> collector = collectorClass.newInstance();
				collectors.addFacetCollector(collector);
			}
			catch (final Exception e)
			{
				logger.warn("Failed to instantiate collector " + collectorClass + ". Skip it.", e);
			}
		}

		if (!collectors.hasCollectors())
		{
			throw new IllegalStateException("No valid facet collector classes were defined");
		}

		return collectors;
	}

}
