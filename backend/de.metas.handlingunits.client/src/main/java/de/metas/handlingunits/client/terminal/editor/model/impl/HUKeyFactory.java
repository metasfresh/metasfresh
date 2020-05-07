package de.metas.handlingunits.client.terminal.editor.model.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IdentityHashSet;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableFactory;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.attribute.impl.SaveDecoupledHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.LUTUKeyVisitorAdapter;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.document.IHUDocumentLineFinder;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.document.impl.SingletonHUDocumentLineFinder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HUKeyFactory implements IHUKeyFactory
{
	// Services
	private final IWeightableFactory weightHelperFactory = Services.get(IWeightableFactory.class);

	private ITerminalContext terminalContext;
	private final IHUStorageFactory storageFactory;
	private final HUKeyAttributeStorageFactory attributesStorageFactory;

	private boolean disposed = false;

	public HUKeyFactory()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		storageFactory = handlingUnitsBL.getStorageFactory();

		//
		// Configure the HU Attributes DAO to use:
		// we will use an implementation which is capable of decouple the saving and do all together on a flush()
		final IHUAttributesDAO huAttributesDAO = new SaveDecoupledHUAttributesDAO(HUAttributesDAO.instance)
				.setIncrementalFlush(true) // i.e. save only what was enqueued to be saved from last flush
				.setAutoflushEnabled(true) // by default, save the HU attributes directly; we will temporary disable autoflush when we will need to
		;

		attributesStorageFactory = new HUKeyAttributeStorageFactory();
		attributesStorageFactory.setHUAttributesDAO(huAttributesDAO);
		attributesStorageFactory.setHUStorageFactory(storageFactory);
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		return terminalContext;
	}

	@Override
	public void setTerminalContext(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		if (this.terminalContext != null)
		{
			throw new TerminalException("Service " + this + " was already configured");
		}

		this.terminalContext = terminalContext;
		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public void dispose()
	{
		attributesStorageFactory.clearCache();
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public IHUStorageFactory getStorageFactory()
	{
		return storageFactory;
	}

	@Override
	public HUKeyAttributeStorageFactory getAttributeStorageFactory()
	{
		return attributesStorageFactory;
	}

	@Override
	public void clearCache()
	{
		attributesStorageFactory.clearCache();
	}

	/**
	 * Aggregate and add childKeys to parent
	 *
	 * @param parent
	 * @param childKeys
	 */
	private void addChildren(final AbstractHUKey parent, final List<IHUKey> childKeys)
	{
		aggregateKeys(parent, childKeys, false); // newAggregationKey
	}

	private final List<IHUKey> aggregateKeys(final AbstractHUKey parent, final List<IHUKey> childKeys, final boolean newAggregationKey)
	{
		Check.assumeNotNull(parent, "parent not null"); // 07914: Aggregated keys shall never be null! Composite keys shall be bound to a parent (even if it's Root)

		if (childKeys == null || childKeys.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Get/Create aggregation map
		Map<Object, IHUKey> aggregationKey2key;
		if (!newAggregationKey)
		{
			aggregationKey2key = parent.getProperty("aggregationKey2key");
			if (aggregationKey2key == null)
			{
				aggregationKey2key = new LinkedHashMap<Object, IHUKey>();
				parent.setProperty("aggregationKey2key", aggregationKey2key);
			}
		}
		else
		{
			aggregationKey2key = new LinkedHashMap<Object, IHUKey>();
		}

		//
		// Iterate children and aggregate them
		for (final IHUKey huKey : childKeys)
		{
			final Object aggregationKey = huKey.getAggregationKey();

			final IHUKey huKeyExisting = aggregationKey2key.get(aggregationKey);
			if (huKeyExisting == null)
			{
				aggregationKey2key.put(aggregationKey, huKey);
			}
			else if (huKeyExisting instanceof CompositeHUKey)
			{
				final CompositeHUKey huKeyExistingComposite = (CompositeHUKey)huKeyExisting;
				huKeyExistingComposite.addChild(huKey);
			}
			else
			{
				final CompositeHUKey huKeyComposite = createCompositeHUKey(aggregationKey);
				huKeyComposite.addChild(huKeyExisting);
				huKeyComposite.addChild(huKey);
				aggregationKey2key.put(aggregationKey, huKeyComposite);
			}
		}

		//
		// Get aggregated keys
		final Collection<IHUKey> huKeysAggregated = aggregationKey2key.values();

		//
		// Add them to parent (if exists)
		if (!newAggregationKey)
		{
			parent.addChildren(huKeysAggregated);
		}
		else
		{
			//
			// 07914: Allow setting directly; TODO see how this affects the API - this is a workaround!
			// We need to set parent without actually loading the children
			//
			for (final IHUKey huKeyAggregated : huKeysAggregated)
			{
				huKeyAggregated.setParent(parent); // force parent
			}
		}
		return new ArrayList<>(huKeysAggregated);
	}

	@Override
	public IHUKey createKey(final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		final IHUDocumentLineFinder documentLineFinder = new SingletonHUDocumentLineFinder(documentLine);
		return createKey(hu, documentLineFinder);
	}

	private final IHUKey createKey(final I_M_HU hu, final IHUDocumentLineFinder documentLineFinder)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(documentLineFinder, "documentLineFinder not null");

		//
		// Create and setup Key
		final IHUDocumentLine documentLine = documentLineFinder.findHUDocumentLine(hu);

		final HUKey huKey = createHUKey(hu, documentLine);

		// //
		// // Create and add children (recursively)
		// final IHUQueryBuilder childrenQuery = handlingUnitsDAO.createHUQueryBuilder()
		// .setContext(hu)
		// .setM_HU_Parent_ID(hu.getM_HU_ID())
		// .setOnlyTopLevelHUs(false);
		// // NOTE: we are not setting the documentLine for child keys because documentLine shall be set only on direct references
		// final List<IHUKey> childrenKeys = createKeys(childrenQuery, NullHUDocumentLineFinder.instance);
		// addChildren(huKey, childrenKeys);

		return huKey;
	}

	@Override
	public List<IHUKey> createChildKeys(final HUKey key)
	{
		Check.assumeNotNull(key, "key not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final I_M_HU hu = key.getM_HU();
		Check.assumeNotNull(hu, "hu not null");

		// If this HU is virtual, for sure there are no children
		if (handlingUnitsBL.isVirtual(hu))
		{
			return Collections.emptyList();
		}

		//
		// Create childrenKeys
		final IHUQueryBuilder childrenQuery = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(hu)
				.setM_HU_Parent_ID(hu.getM_HU_ID())
				.setOnlyTopLevelHUs(false);
		// NOTE: we are not setting the documentLine for child keys because documentLine shall be set only on direct references
		final List<IHUKey> childKeys = createKeys(childrenQuery, NullHUDocumentLineFinder.instance);

		final List<IHUKey> childKeysAggregated = aggregateKeys(key, childKeys, true); // newAggregationKey
		return childKeysAggregated;
	}

	@Override
	public IHUKey createVirtualKeyWithParent(final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		Check.assumeNotNull(hu, "hu not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final boolean isVirtualHU = handlingUnitsBL.isVirtual(hu);
		if (isVirtualHU)
		{
			if (hu.getM_HU_Item_Parent_ID() > 0)
			{
				final VirtualHUKeyWithParent virtualKey = new VirtualHUKeyWithParent(this, hu, documentLine);
				return virtualKey;
			}
			else
			{
				return createHUKey(hu, documentLine); // just create a normal virtual key
			}
		}

		final HUKey huKey = createHUKey(hu, documentLine);

		for (final I_M_HU_Item huItem : handlingUnitsDAO.retrieveItems(hu))
		{
			final String huItemType = handlingUnitsBL.getItemType(huItem);

			//
			// Create one key for each M_HU_Item_Storage
			if (!X_M_HU_PI_Item.ITEMTYPE_Material.equals(huItemType))
			{
				continue;
			}

			final List<I_M_HU> virtualHUs = handlingUnitsDAO.retrieveVirtualHUs(huItem);

			final List<IHUKey> virtualKeys = new ArrayList<>();
			for (final I_M_HU virtualHU : virtualHUs)
			{
				// Create and setup Key
				//
				// NOTE: we are not setting the documentLine for child keys because documentLine shall be set only on direct references
				final VirtualHUKeyWithParent virtualKey = new VirtualHUKeyWithParent(this, virtualHU, (IHUDocumentLine)null);
				virtualKeys.add(virtualKey);
			}

			addChildren(huKey, virtualKeys);
		}
		return huKey;
	}

	@Override
	public List<IHUKey> createKeys(final Collection<I_M_HU> hus, final IHUDocumentLine documentLine)
	{
		final IHUDocumentLineFinder documentLineFinder = new SingletonHUDocumentLineFinder(documentLine);
		return createKeys(hus, documentLineFinder);
	}

	@Override
	public List<IHUKey> createKeys(final Collection<I_M_HU> hus, final IHUDocumentLineFinder documentLineFinder)
	{
		if (hus == null || hus.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<IHUKey> keys = new ArrayList<IHUKey>(hus.size());
		for (final I_M_HU hu : hus)
		{
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			if (handlingUnitsBL.isAggregateHU(hu) && hu.getM_HU_Item_Parent().getQty().signum() <= 0)
			{
				continue;
			}
			final IHUKey huKey = createKey(hu, documentLineFinder);
			keys.add(huKey);
		}

		return keys;
	}

	@Override
	public IHUKey createRootKey(final IHUQueryBuilder husQuery, final IHUDocumentLineFinder documentLineFinder)
	{
		//
		// Create a Root HU Key
		final IHUKey rootKey = createRootKey();

		//
		// Create "+10 items" button
		final MoreHUKey moreHUKey = new MoreHUKey(this, husQuery, documentLineFinder);
		moreHUKey.setFetchSize(10);
		rootKey.addChild(moreHUKey);

		// TODO: in case "documentLineFinder" is null, we will have some problems when we try to created the HUAllocations
		// because our HUKeys won't be found (because are "inside" MoreHUKey button).
		// see de.metas.handlingunits.client.terminal.editor.model.impl.HUKeyFactory.createHUAllocations(IHUKey, Collection<I_M_HU>)
		if (!NullHUDocumentLineFinder.isNull(documentLineFinder))
		{
			moreHUKey.extractAllKeys();
		}

		//
		// Extract the first set
		moreHUKey.extractNextKeys();

		return rootKey;
	}

	private List<IHUKey> createKeys(final IHUQueryBuilder husQuery, final IHUDocumentLineFinder documentLineFinder)
	{
		final List<I_M_HU> hus = husQuery.list();
		final List<IHUKey> huKeys = createKeys(hus, documentLineFinder);
		return huKeys;
	}

	@Override
	public final RootHUKey createRootKey()
	{
		return new RootHUKey(this);
	}

	private final HUKey createHUKey(final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		return new HUKey(this, hu, documentLine);
	}

	private final CompositeHUKey createCompositeHUKey(final Object aggregationKey)
	{
		return new CompositeHUKey(this, aggregationKey);
	}

	@Override
	public void createHUAllocations(final IHUKey rootKey, final Collection<I_M_HU> originalAllocatedHUs)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final IdentityHashSet<IHUDocumentLine> clearedDocumentLines = new IdentityHashSet<IHUDocumentLine>();

		rootKey.iterate(new LUTUKeyVisitorAdapter()
		{
			@Override
			protected void visitTU(final HUKey luHUKey, final HUKey tuHUKey)
			{
				Check.assumeNotNull(tuHUKey, "tuHUKey not null");

				final IHUDocumentLine documentLine = tuHUKey.findDocumentLineOrNull();
				if (documentLine == null)
				{
					// Case: we are calling this from a POS window were we don't have our HUKeys assigned to document lines
					// e.g. Bereitsteller POS
					return;
				}

				final IHUAllocations huAllocations = documentLine.getHUAllocations();
				final I_M_Product product = documentLine.getM_Product();
				final I_C_UOM uom = documentLine.getC_UOM();

				//
				// Clear old allocations
				// (only first time when we touch this document, else we would delete our previous assignments and allocations)
				if (clearedDocumentLines.add(documentLine))
				{
					//
					// 07451: do NOT destroy ALL HU assignments; only destroy those with which were given (with which the editor was opened)
					// huAllocations.clearAssignmentsAndAllocations();
					// FIXME: i think we shall take care of them on VHU level, not sure yet...
					huAllocations.removeAssignedHUs(originalAllocatedHUs);
				}

				final I_M_HU luHU = luHUKey == null ? null : luHUKey.getM_HU();
				final I_M_HU tuHU = tuHUKey.getM_HU();
				// 07692: Delete old TU allocations when doing HU operations on HUKey-level
				boolean deleteOldTUAllocations = true;

				//
				// Iterate VHUs
				// FIXME: handle the case of an VHU on LU directly !!!!!!! .... in that case there will be no children!!!
				final List<IHUKey> vhuKeys;
				if (tuHUKey.isVirtualPI() || tuHUKey.isAggregateHU())
				{
					vhuKeys = Collections.<IHUKey> singletonList(tuHUKey);
				}
				else
				{
					vhuKeys = tuHUKey.getChildren();
				}
				for (final IHUKey vhuKey0 : vhuKeys)
				{
					final HUKey vhuKey = HUKey.cast(vhuKey0);
					final I_M_HU vhu = vhuKey.getM_HU();

					//
					// Get Product Storage
					final IHUProductStorage vhuProductStorage = vhuKey.getProductStorageOrNull(product);
					if (vhuProductStorage == null)
					{
						// HU does not contain document line's product. That's fine, just skip it.
						continue;
					}

					//
					// Create new allocation
					final BigDecimal qtyToAllocate = vhuProductStorage.getQty(uom);

					//
					huAllocations.allocate(luHU, tuHU, vhu, qtyToAllocate, uom, deleteOldTUAllocations);
					deleteOldTUAllocations = false; // set it to false to not delete what we freshly created
				}
			}
		});
	}

	@Override
	public IWeightable getWeightOrNull(final IHUKey key)
	{
		if (key == null)
		{
			return null;
		}

		final IAttributeStorage attributeStorage = key.getAttributeSet();
		return weightHelperFactory.createWeightableOrNull(attributeStorage);
	}
}
