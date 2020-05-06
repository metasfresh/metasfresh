package org.adempiere.ui.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.ui.IContextMenuAction;

final class RootContextMenuAction extends AbstractContextMenuAction
{
	public static final RootContextMenuAction EMPTY = new RootContextMenuAction();

	private final List<IContextMenuAction> actions;

	/**
	 * Empty constructor
	 */
	private RootContextMenuAction()
	{
		super();
		this.actions = Collections.emptyList();
	}

	/* package */RootContextMenuAction(List<IContextMenuAction> actions)
	{
		super();
		this.actions = actions;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getIcon()
	{
		return null;
	}

	@Override
	public boolean isAvailable()
	{
		return true;
	}

	@Override
	public boolean isRunnable()
	{
		return true;
	}

	@Override
	public List<IContextMenuAction> getChildren()
	{
		return actions;
	}

	@Override
	public void run()
	{
		// shall not be invoked
		throw new UnsupportedOperationException();
	}
}
