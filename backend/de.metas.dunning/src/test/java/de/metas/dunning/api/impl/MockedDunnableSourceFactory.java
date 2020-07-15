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


import java.util.Collections;
import java.util.List;

import de.metas.dunning.api.IDunnableSourceFactory;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.dunning.spi.impl.MockedDunnableSource;

public class MockedDunnableSourceFactory implements IDunnableSourceFactory
{
	private final MockedDunnableSource source = new MockedDunnableSource();

	@Override
	public void registerSource(Class<? extends IDunnableSource> clazz)
	{
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public List<IDunnableSource> getSources(IDunningContext context)
	{
		return Collections.singletonList((IDunnableSource)source);
	}

	public MockedDunnableSource getSource()
	{
		return source;
	}

}
