package de.metas.handlingunits.client.terminal.editor.model.impl;

import java.util.function.Predicate;

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


import org.adempiere.util.Check;
import org.compiere.model.IQuery;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;
import de.metas.handlingunits.model.I_M_HU;

/* package */class HUKeyChildrenFilter implements IHUKeyChildrenFilter
{
	private final Predicate<IHUKey> predicate;
	private final IQuery<I_M_HU> query;

	public HUKeyChildrenFilter(final Predicate<IHUKey> predicate, final IQuery<I_M_HU> query)
	{
		super();

		Check.assumeNotNull(predicate, "predicate not null");
		this.predicate = predicate;

		Check.assumeNotNull(query, "query not null");
		this.query = query;
	}

	@Override
	public boolean accept(IHUKey child)
	{
		return predicate.test(child);

	}

	@Override
	public IQuery<I_M_HU> getQuery()
	{
		return query;
	}

}
