package de.metas.handlingunits.impl;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base implementation for {@link IPackingMaterialDocumentLinesBuilder}.
 *
 * @author tsa
 */
public abstract class AbstractPackingMaterialDocumentLinesBuilder implements IPackingMaterialDocumentLinesBuilder
{
	/**
	 * Map of "packing material key" to {@link IPackingMaterialDocumentLine}.
	 * <p>
	 * For creating packing material key, see {@link #createPackingMaterialKey(ProductId)}.
	 */
	private final Map<ArrayKey, IPackingMaterialDocumentLine> packingMaterialKey2packingMaterialLine = new HashMap<>();

	/**
	 * Set of sources which have no packing materials.
	 * <p>
	 * We will use this set to be able to unlink those sources from existing packing material lines (if any).
	 */
	private final Set<IPackingMaterialDocumentLineSource> sourcesWithoutPackingMaterials = new HashSet<>();

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
	protected final void addPackingMaterialDocumentLine(@NonNull final IPackingMaterialDocumentLine line)
	{
		final ArrayKey pmKey = createPackingMaterialKey(line.getProductId());
		final IPackingMaterialDocumentLine lineExisting = packingMaterialKey2packingMaterialLine.put(pmKey, line);
		if (lineExisting != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(line.getProductId());
			throw new AdempiereException("An packing material line was already added for product " + productName);
		}
	}

	private ArrayKey createPackingMaterialKey(@NonNull final ProductId productId)
	{
		return Util.mkKey(productId);
	}

	@Override
	public final void addSource(@NonNull final IPackingMaterialDocumentLineSource source)
	{
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
				packingMaterialLine.addSourceOrderLine(source, source.getQty());
			}
			final ProductId luProductID = source.getLUProductId();
			if (luProductID != null)
			{
				final IPackingMaterialDocumentLine luPackingMaterialLine = getLUPackingMaterialDocumentLine(luProductID);
				luPackingMaterialLine.addSourceOrderLine(source, source.getQtyLU());
			}
		}
	}

	private IPackingMaterialDocumentLine getLUPackingMaterialDocumentLine(final ProductId luProductId)
	{
		final ArrayKey pmKey = createPackingMaterialKey(luProductId);

		//
		// Check if we already have a packing material line
		final IPackingMaterialDocumentLine pmLineExisting = packingMaterialKey2packingMaterialLine.get(pmKey);
		if (pmLineExisting != null)
		{
			return pmLineExisting;
		}

		//
		// Packing material order line was not found => Create New
		final IPackingMaterialDocumentLine pmLineNew = createPackingMaterialDocumentLine(luProductId);

		// NOTE: we are not saving here, but later
		addPackingMaterialDocumentLine(pmLineNew);

		return pmLineNew;
	}

	public final void addSources(final Collection<IPackingMaterialDocumentLineSource> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return;
		}

		sources.forEach(this::addSource);
	}

	private IPackingMaterialDocumentLine getCreatePackingMaterialDocumentLine(@NonNull final I_M_HU_PackingMaterial packingMaterial)
	{
		final ProductId productId = ProductId.ofRepoId(packingMaterial.getM_Product_ID());
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
		final IPackingMaterialDocumentLine pmLineNew = createPackingMaterialDocumentLine(productId);

		// NOTE: we are not saving here, but later
		addPackingMaterialDocumentLine(pmLineNew);

		return pmLineNew;
	}

	/**
	 * Create {@link IPackingMaterialDocumentLine} from given {@link I_M_HU_PackingMaterial}.
	 * <p>
	 * NOTE: when implementing this method, if there are some underlying database records, please don't save it because you will be asked to save them when needed (see
	 * {@link #createDocumentLine(IPackingMaterialDocumentLine)}).
	 *
	 * @param productId the product for which the packing material line shall be created.
	 * @return packing material document line.
	 */
	protected abstract IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final ProductId productId);

	@Override
	@OverridingMethodsMustInvokeSuper
	public AbstractPackingMaterialDocumentLinesBuilder create()
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

		return this;
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
	 * <p>
	 * NOTE: Here is the place to delete your underlying database models.
	 *
	 * @param pmLine packing material line to delete
	 */
	protected abstract void removeDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create/update the given packing material line.
	 * <p>
	 * NOTE: Here is the place to save your underlying database models.
	 *
	 * @param pmLine packing material line to create/update
	 */
	protected abstract void createDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create a link from source line to packing material line.
	 * <p>
	 * NOTE: please make sure you are saving your database changes.
	 *
	 * @param source
	 * @param pmLine packing material line; could be <code>null</code> in case we want to unlink any packing material lines from given <code>source</code>.
	 */
	protected abstract void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source, final IPackingMaterialDocumentLine pmLine);
}
