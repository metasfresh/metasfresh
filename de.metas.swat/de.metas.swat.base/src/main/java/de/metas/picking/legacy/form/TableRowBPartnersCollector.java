package de.metas.picking.legacy.form;

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


import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.collections.Predicate;
import org.compiere.util.KeyNamePair;

/**
 * Collect BPartner {@link KeyNamePair}s for {@link TableRow}s
 * 
 * @author tsa
 * 
 */
public class TableRowBPartnersCollector implements Predicate<TableRow>
{
	private final Set<Integer> seenIds = new HashSet<Integer>();
	private final Set<KeyNamePair> bpartners = new TreeSet<KeyNamePair>();

	@Override
	public boolean evaluate(final TableRow currentRow)
	{
		final int bpartnerId = currentRow.getBpartnerId();
		if (bpartnerId <= 0)
		{
			// invalid BPartner ID; shall not happen
			return false;
		}

		if (!seenIds.add(bpartnerId))
		{
			// already added
			return false;
		}

		final String bpartnerName = currentRow.getBpartnerName();
		final KeyNamePair bpartner = new KeyNamePair(bpartnerId, bpartnerName);
		bpartners.add(bpartner);

		return false;
	}

	/**
	 * 
	 * @return collected partners
	 */
	public Set<KeyNamePair> getBPartners()
	{
		return bpartners;
	}
}
