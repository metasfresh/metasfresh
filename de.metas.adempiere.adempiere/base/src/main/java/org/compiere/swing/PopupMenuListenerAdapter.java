package org.compiere.swing;

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


import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/** Convenient implement what you need adapter for {@link PopupMenuListener} */
public abstract class PopupMenuListenerAdapter implements PopupMenuListener
{
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
	{
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
	{
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e)
	{
	}
}
