package de.metas.handlingunits.impl;

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


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/**
 * Base implementation for {@link IPackingMaterialDocumentLinesBuilder}.
 *
 * @author tsa
 */
public abstract class AbstractPackingMaterialDocumentLinesBuilder implements IPackingMaterialDocumentLinesBuilder
{
	/**
	 * Map of "packing material key" to {@link IPackingMaterialDocumentLine}.
	 *
	 * For creating packing material key, see {@link #createPackingMaterialKey(int)}.
	 */
	private final Map<ArrayKey, IPackingMaterialDocumentLine> packingMaterialKey2packingMaterialLine = new HashMap<ArrayKey, IPackingMaterialDocumentLine>();

	/**
	 * Set of sources which have no packing materials.
	 *
	 * We will use this set to be able to unlink those sources from existing packing material lines (if any).
	 */
	private final Set<IPackingMaterialDocumentLineSource> sourcesWithoutPackingMaterials = new HashSet<IPackingMaterialDocumentLineSource>();

	public AbstractPackingMaterialDocumentLinesBuilder()
	{
		super();
	}

	@Override
	public boolean isEmpty()
	{
		// If we have already have packing material lines, consider it not empty
		if (!packingMaterialKey2packingMaterialLine.isEmpty())
		{
			return false;
		}

		return true;
	}

	/**
	 * Adds an existing {@link IPackingMaterialDocumentLine} to our builder to be considered on subsequent {@link #addSource(IPackingMaterialDocumentLineSource)} calls.
	 *
	 * @param line packing material document line
	 */
	protected final void addPackingMaterialDocumentLine(final IPackingMaterialDocumentLine line)
	{
		Check.assumeNotNull(line, "line not null");

		final ArrayKey pmKey = createPackingMaterialKey(line.getM_Product_ID());
		final IPackingMaterialDocumentLine lineExisting = packingMaterialKey2packingMaterialLine.put(pmKey, line);
		if (lineExisting != null)
		{
			throw new AdempiereException("An packing material line was already added for product " + line.getM_Product());
		}
	}

	private ArrayKey createPackingMaterialKey(final int productId)
	{
		Check.assume(productId > 0, "productId > 0");
		return Util.mkKey(productId);
	}

	@Override
	public final void addSource(final IPackingMaterialDocumentLineSource source)
	{
		Check.assumeNotNull(source, "source not null");
		assertValid(source);

		final List<I_M_HU_PackingMaterial> packingMaterials = source.getM_HU_PackingMaterials();
		if (packingMaterials == null || packingMaterials.isEmpty())
		{
			// If our source has no packing materials, remember it,
			// and later, when we are creating the document lines we will call "linkSourceDocumentLine()" method
			// to unlink the source from an existing packing material document line
			sourcesWithoutPackingMaterials.add(source);
		}
		else
		{
			for (final I_M_HU_PackingMaterial packingMaterial : packingMaterials)
			{
				final IPackingMaterialDocumentLine packingMaterialLine = getCreatePackingMaterialDocumentLine(packingMaterial);
				packingMaterialLine.addSourceOrderLine(source);
			}
		}
	}

	private IPackingMaterialDocumentLine getCreatePackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial)
	{
		final int productId = packingMaterial.getM_Product_ID();
		final ArrayKey pmKey = createPackingMaterialKey(productId);

		//
		// Check if we already have a packing material line
		final IPackingMaterialDocumentLine pmLineExisting = packingMaterialKey2packingMaterialLine.get(pmKey);
		if (pmLineExisting != null)
		{
			return pmLineExisting;
		}

		//
		// Packing material order line was not found => Create New
		final IPackingMaterialDocumentLine pmLineNew = createPackingMaterialDocumentLine(packingMaterial);

		// NOTE: we are not saving here, but later
		addPackingMaterialDocumentLine(pmLineNew);

		return pmLineNew;
	}

	/**
	 * Create {@link IPackingMaterialDocumentLine} from given {@link I_M_HU_PackingMaterial}.
	 *
	 * NOTE: when implementing this method, if there are some underlying database records, please don't save it because you will be asked to save them when needed (see
	 * {@link #createDocumentLine(IPackingMaterialDocumentLine)}).
	 *
	 * @param packingMaterial
	 * @return packing material document line.
	 */
	protected abstract IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial);

	@Override
	public final void create()
	{
		//
		// Process all packing material lines
		for (final IPackingMaterialDocumentLine pmLine : packingMaterialKey2packingMaterialLine.values())
		{
			createUpdateDeleteDocumentLineAndUpdateSources(pmLine);
		}

		//
		// Iterate those sources without packing materials
		// and make sure they are not linked to some packing material lines (i.e. unlink them)
		for (final IPackingMaterialDocumentLineSource source : sourcesWithoutPackingMaterials)
		{
			final IPackingMaterialDocumentLine pmLine = null; // no packing material line
			linkSourceToDocumentLine(source, pmLine);
		}
	}

	/**
	 * Create/update <b>or delete</b> the given packing material line.
	 * <p>
	 * Also, the linked source lines will be updated.
	 *
	 * @param pmLine
	 */
	private void createUpdateDeleteDocumentLineAndUpdateSources(final IPackingMaterialDocumentLine pmLine)
	{
		final IPackingMaterialDocumentLine pmLineToLink;

		// If there is no qty on current packing material order line, just delete it
		if (pmLine.getQty().signum() <= 0)
		{
			removeDocumentLine(pmLine);
			pmLineToLink = null; // we need to unlink our sources
		}
		// We have packing material qty => create the actual line
		else
		{
			createDocumentLine(pmLine);
			pmLineToLink = pmLine;
		}

		//
		// Update the link between "source" to "packing material line"
		for (final IPackingMaterialDocumentLineSource source : pmLine.getSources())
		{
			linkSourceToDocumentLine(source, pmLineToLink);
		}
	}

	/**
	 * Makes sure the given is <code>source</code> accepted by this builder
	 *
	 * @param source
	 */
	protected abstract void assertValid(final IPackingMaterialDocumentLineSource source);

	/**
	 * Called when we need to delete the given packing material line.
	 *
	 * NOTE: Here is the place to delete your underlying database models.
	 *
	 * @param pmLine packing material line to delete
	 */
	protected abstract void removeDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create/update the given packing material line.
	 *
	 * NOTE: Here is the place to save your underlying database models.
	 *
	 * @param pmLine packing material line to create/update
	 */
	protected abstract void createDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create a link from source line to packing material line.
	 *
	 * NOTE: please make sure you are saving your database changes.
	 *
	 * @param source
	 * @param pmLine packing material line; could be <code>null</code> in case we want to unlink any packing material lines from given <code>source</code>.
	 */
	protected abstract void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source, final IPackingMaterialDocumentLine pmLine);
}
