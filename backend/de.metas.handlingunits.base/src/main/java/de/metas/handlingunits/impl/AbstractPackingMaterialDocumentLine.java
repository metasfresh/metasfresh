package de.metas.handlingunits.impl;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
	public final void addSourceOrderLine(@NonNull final IPackingMaterialDocumentLineSource source, BigDecimal qtyToAdd)
	{
		if (!sources.add(source))
		{
			throw new AdempiereException("source " + source + " was already added");
		}

		addQty(qtyToAdd);
	}

	@Override
	public final Set<IPackingMaterialDocumentLineSource> getSources()
	{
		return sourcesRO;
	}

	private final void addQty(@NonNull final BigDecimal qtyToAdd)
	{
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
