package de.metas.handlingunits.attribute.strategy.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeStrategyFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Check;
import de.metas.util.Services;

public class AttributeStrategyFactory implements IAttributeStrategyFactory
{
	private final Map<String, IAttributeStrategy> classname2strategy = new HashMap<String, IAttributeStrategy>();

	private final Map<Class<? extends IAttributeStrategy>, IAttributeStrategy> defaultStrategies = new HashMap<Class<? extends IAttributeStrategy>, IAttributeStrategy>();

	public AttributeStrategyFactory()
	{
		super();

		classname2strategy.put(NullAggregationStrategy.class.getName(), NullAggregationStrategy.instance);
		classname2strategy.put(NullSplitterStrategy.class.getName(), NullSplitterStrategy.instance);
		classname2strategy.put(SkipHUAttributeTransferStrategy.class.getName(), SkipHUAttributeTransferStrategy.instance);
		classname2strategy.put(CopyHUAttributeTransferStrategy.class.getName(), CopyHUAttributeTransferStrategy.instance);
		classname2strategy.put(RedistributeQtyHUAttributeTransferStrategy.class.getName(), RedistributeQtyHUAttributeTransferStrategy.instance);

		defaultStrategies.put(IAttributeAggregationStrategy.class, NullAggregationStrategy.instance);
		defaultStrategies.put(IAttributeSplitterStrategy.class, NullSplitterStrategy.instance);
		defaultStrategies.put(IHUAttributeTransferStrategy.class, SkipHUAttributeTransferStrategy.instance);
	}

	private <T extends IAttributeStrategy> T getDefaultStrategy(final Class<T> strategyClass)
	{
		@SuppressWarnings("unchecked")
		final T strategy = (T)defaultStrategies.get(strategyClass);
		return strategy;
	}

	@Override
	public <T extends IAttributeStrategy> T retrieveStrategy(final Properties ctx, final int adJavaClassId, final Class<T> strategyClass)
	{
		final I_AD_JavaClass javaClass = Services.get(IJavaClassDAO.class).retriveJavaClassOrNull(ctx, adJavaClassId);

		if (javaClass == null)
		{
			return getDefaultStrategy(strategyClass);
		}

		final String classname = javaClass.getClassname();
		if (Check.isEmpty(classname, true))
		{
			throw new AdempiereException("Java Class name is empty for " + javaClass);
		}

		//
		// Check if we have defined a static strategy
		@SuppressWarnings("unchecked")
		final T strategyInstance = (T)classname2strategy.get(classname);
		if (strategyInstance != null)
		{
			return strategyInstance;
		}

		return Util.getInstance(strategyClass, classname);
	}

}
