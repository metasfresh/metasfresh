package de.metas.handlingunits.client.terminal.editor.model.filter;

/*
 * #%L
 * de.metas.handlingunits.client
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

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.IQuery;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.MoreHUKey;
import de.metas.handlingunits.model.I_M_HU;

/**
 * HU Key filter interface
 *
 * @author al
 */
public interface IHUKeyFilter
{
	/**
	 * @param key
	 * @return available property values for the children of the given key
	 */
	List<? extends NamePair> getPropertyAvailableValues(IHUKey key);

	/**
	 * @param queryBuilder
	 * @return available property values for the children of the query builder
	 */
	List<? extends NamePair> getPropertyAvailableValues(IQueryBuilder<I_M_HU> queryBuilder);

	/**
	 * @param contextProvider
	 * @param value
	 * @return sub-query filter (used generally when scanning HUs with applied filter - i.e {@link MoreHUKey})
	 */
	IQuery<I_M_HU> getSubQueryFilter(IContextAware contextProvider, Object value);

	/**
	 * @param key
	 * @return terminal lookup
	 */
	ITerminalLookup getPropertyLookup(IHUKey key);

	/**
	 * @param key
	 * @param value
	 * @return true if given key (child) is accepted by this filter's current <code>value</code>
	 */
	boolean accept(IHUKey key, Object value);
}
