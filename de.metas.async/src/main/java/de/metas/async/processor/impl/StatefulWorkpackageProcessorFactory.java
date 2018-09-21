package de.metas.async.processor.impl;

/*
 * #%L
 * de.metas.async
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

import de.metas.async.processor.IStatefulWorkpackageProcessorFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Check;

class StatefulWorkpackageProcessorFactory extends WorkpackageProcessorFactory implements IStatefulWorkpackageProcessorFactory
{
	private final Map<String, IWorkpackageProcessor> processors = new HashMap<String, IWorkpackageProcessor>();

	@Override
	public void registerWorkpackageProcessor(IWorkpackageProcessor packageProcessor)
	{
		Check.assumeNotNull(packageProcessor, "packageProcessor not null");

		final String classname = packageProcessor.getClass().getName();
		processors.put(classname, packageProcessor);
	}

	@Override
	public IWorkpackageProcessor getWorkpackageProcessorInstance(final String classname)
	{
		final IWorkpackageProcessor packageProcessor = processors.get(classname);
		if (packageProcessor != null)
		{
			return packageProcessor;
		}

		return super.getWorkpackageProcessorInstance(classname);
	}
}
