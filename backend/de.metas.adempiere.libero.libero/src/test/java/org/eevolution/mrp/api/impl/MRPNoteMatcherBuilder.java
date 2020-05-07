package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.util.collections.CompositePredicate;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_AD_Note;

/* package */class MRPNoteMatcherBuilder
{
	private final CompositePredicate<I_AD_Note> matchers;
	private boolean matcherAdded = false;
	private final MRPNoteMatcher matcher = new MRPNoteMatcher();

	public MRPNoteMatcherBuilder(final CompositePredicate<I_AD_Note> matchers)
	{
		super();
		this.matchers = matchers;
	}

	private final void addMatcherIfNotAlreadyAdded()
	{
		if (matcherAdded)
		{
			return;
		}

		matchers.addPredicate(matcher);
		matcherAdded = true;
	}

	public MRPNoteMatcherBuilder setMRPCode(final String mrpCode)
	{
		matcher.setMRPCode(mrpCode);

		addMatcherIfNotAlreadyAdded();
		return this;
	}

	public MRPNoteMatcherBuilder setM_Product(final I_M_Product product)
	{
		matcher.setM_Product(product);

		addMatcherIfNotAlreadyAdded();
		return this;
	}

	public MRPNoteMatcherBuilder setM_Warehouse(final I_M_Warehouse warehouse)
	{
		matcher.setM_Warehouse(warehouse);

		addMatcherIfNotAlreadyAdded();
		return this;
	}
}
