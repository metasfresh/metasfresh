package de.metas.handlingunits.client.terminal.editor.model.impl;

import java.util.function.Predicate;

import org.compiere.model.IQuery;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;

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
