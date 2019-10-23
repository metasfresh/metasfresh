package org.adempiere.ui.sideactions.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


/**
 * Base class for easily creating clickable side panel actions.
 * 
 * @author tsa
 *
 */
public abstract class ExecutableSideAction extends AbstractSideAction
{
	@Override
	public SideActionType getType()
	{
		return SideActionType.ExecutableAction;
	}

	@Override
	public final void setToggled(boolean toggled)
	{
		if (toggled)
		{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public final boolean isToggled()
	{
		return false;
	}

	@Override
	public abstract String getDisplayName();

	@Override
	public abstract void execute();
}
