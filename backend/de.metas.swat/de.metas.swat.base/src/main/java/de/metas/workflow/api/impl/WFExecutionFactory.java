package de.metas.workflow.api.impl;

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
import java.util.List;

import de.metas.workflow.api.IWFExecutionFactory;
import de.metas.workflow.api.IWFExecutionListener;

public class WFExecutionFactory implements IWFExecutionFactory
{
	private final List<IWFExecutionListener> executionListeners = new ArrayList<IWFExecutionListener>();

	@Override
	public void registerListener(final IWFExecutionListener listener)
	{
		if (listener == null)
		{
			return;
		}

		synchronized (executionListeners)
		{
			if (executionListeners.contains(listener))
			{
				return;
			}

			executionListeners.add(listener);
		}
	}

	@Override
	public void notifyActivityPerformed(Object fromModel, Object toModel)
	{
		synchronized (executionListeners)
		{
			for (IWFExecutionListener listener : executionListeners)
			{
				listener.onActivityPerformed(fromModel, toModel);
			}
		}
	}
}
