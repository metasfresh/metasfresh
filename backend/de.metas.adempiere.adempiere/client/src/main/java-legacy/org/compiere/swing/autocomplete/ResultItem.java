package org.compiere.swing.autocomplete;

import javax.swing.DefaultListCellRenderer;

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
 * A result item of {@link ResultItemSource}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ResultItem
{
	/** @return items's text that will be used for displaying in auto-complete text box and also for matching */
	String getText();

	/**
	 * IMPORTANT: needed to check if two {@link ResultItem}s are equal.
	 * 
	 * @param obj
	 * @return true if equals
	 */
	@Override
	public boolean equals(Object obj);

	/**
	 * IMPORTANT: needed to check if two {@link ResultItem}s are equal.
	 * 
	 * @return hash code
	 */
	@Override
	public int hashCode();

	/**
	 * IMPORTANT: you need to implement this because it's used by {@link DefaultListCellRenderer}, or you shall override {@link JTextComponentAutoCompleter#createListCellRenderer()}.
	 */
	@Override
	public String toString();
}
