package de.metas.handlingunits.impl;

import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.util.Check;

/**
 * Abstract {@link IPackingMaterialDocumentLine} implementation which implements the common methods.
 *
 * @author tsa
 *
 */
public abstract class AbstractPackingMaterialDocumentLine implements IPackingMaterialDocumentLine
{
	final Set<IPackingMaterialDocumentLineSource> sources = new HashSet<>();
	final Set<IPackingMaterialDocumentLineSource> sourcesRO = Collections.unmodifiableSet(sources);

	@Override
	public final void addSourceOrderLine(@NonNull final IPackingMaterialDocumentLineSource source)
	{
		if (!sources.add(source))
		{
			throw new AdempiereException("source " + source + " was already added");
		}

		addQty(source.getQty());
	}

	@Override
	public final Set<IPackingMaterialDocumentLineSource> getSources()
	{
		return sourcesRO;
	}

	private final void addQty(final BigDecimal qtyToAdd)
	{
		Check.assumeNotNull(qtyToAdd, "qtyToAdd not null");
		final BigDecimal qtyOld = getQty();
		final BigDecimal qtyNew = qtyOld.add(qtyToAdd);

		setQty(qtyNew);
	}

	/**
	 *
	 * @param qty how many packing materials there are
	 */
	protected abstract void setQty(BigDecimal qty);
}
