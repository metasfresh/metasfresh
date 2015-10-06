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


import java.util.List;

import de.metas.async.processor.impl.WorkpackageProcessorBlackList.BlackListItem;

/**
 * MBean for {@link WorkpackageProcessorFactory}.
 * 
 * @author tsa
 * 
 */
public class WorkpackageProcessorFactoryJMX implements WorkpackageProcessorFactoryJMXMBean
{
	private final WorkpackageProcessorFactory factory;

	public WorkpackageProcessorFactoryJMX(final WorkpackageProcessorFactory factory)
	{
		super();
		this.factory = factory;
	}

	@Override
	public String[] getBlackListItemsInfo()
	{
		final List<BlackListItem> blacklistItems = factory.getBlackList().getItems();
		final String[] info = new String[blacklistItems.size()];
		for (int i = 0; i < info.length; i++)
		{
			info[i] = blacklistItems.get(i).toString();
		}

		return info;
	}

	@Override
	public void removeFromBlackList(final int packageProcessorId)
	{
		factory.getBlackList().removeFromBlacklist(packageProcessorId);
	}

	@Override
	public void clearBlackList()
	{
		factory.getBlackList().clear();
	}
}
