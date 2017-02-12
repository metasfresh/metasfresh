package org.compiere.apps.search;

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

/**
 * {@link FindPanel}'s action listener.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FindPanelActionListener
{
	public static final transient FindPanelActionListener NULL = new FindPanelActionListener();

	/**
	 * Called after user performed a search
	 * 
	 * @param triggeredFromSearchField true if the search was triggered automatically when user pressed enter in a search field.
	 */
	public void onSearch(final boolean triggeredFromSearchField)
	{
	}

	/**
	 * Called after user pressed the cancel button.
	 */
	public void onCancel()
	{
	}

	/**
	 * Called after user pressed the "New record" button.
	 */
	public void onOpenAsNewRecord()
	{
	}
}
