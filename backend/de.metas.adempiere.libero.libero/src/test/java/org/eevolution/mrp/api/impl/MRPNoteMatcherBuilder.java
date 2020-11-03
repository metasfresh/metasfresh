package org.eevolution.mrp.api.impl;

import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_AD_Note;

import de.metas.util.collections.CompositePredicate;

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
