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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.Optional;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;

/**
 * Handling Unit Key
 *
 * @author tsa
 *
 */
public class HUKey extends AbstractHUKey implements ISplittableHUKey, IHUAware
{
	/**
	 * Cast given <code>key</code> to {@link HUKey}.
	 *
	 * @param key
	 * @return {@link HUKey} or null if key was null
	 * @throws ClassCastException if cast is not possible.
	 */
	public static final HUKey cast(final IHUKey key)
	{
		return (HUKey)key;
	}

	/**
	 * Cast given <code>key</code> to {@link HUKey} if possible. If not, <code>null</code> will be returned.
	 *
	 * @param key
	 * @return {@link HUKey} or null
	 */
	public static final HUKey castIfPossible(final IHUKey key)
	{
		if (key instanceof HUKey)
		{
			return cast(key);
		}

		return null;
	}

	// Services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHULockBL huLockBL = Services.get(IHULockBL.class);

	private final I_M_HU _hu;
	private IHUStorage _huStorage;
	private final boolean virtualPI;
	private final boolean aggregateHU;
	private final int aggregatedHUCount;
	private final IHUDocumentLine _documentLine;

	private final String id;
	private final KeyNamePair value;
	private final ArrayKey aggregationKey;

	/**
	 *
	 * @param keyFactory
	 * @param hu
	 * @param documentLine shall be set only if this HU is directly linked to documentLine, else it shall be null
	 */
	public HUKey(final IHUKeyFactory keyFactory, final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		super(keyFactory);

		Check.assumeNotNull(hu, "hu not null");
		_hu = hu;

		Check.assumeNotNull(keyFactory, "keyFactory not null");
		virtualPI = handlingUnitsBL.isVirtual(hu);

		_documentLine = documentLine;

		final int huId = hu.getM_HU_ID();
		id = getClass().getName() + "-" + huId;

		final String name = handlingUnitsBL.getDisplayName(hu); // initial HUKey name
		value = new KeyNamePair(huId, name);

		// FIXME: aggregate by CU too
		final int piId = handlingUnitsBL.getPIVersion(hu).getM_HU_PI_ID();
		if (handlingUnitsBL.isConcretePI(piId))
		{
			aggregationKey = Util.mkKey(piId);
		}
		else
		{
			// never aggregate together non-concrete HUs
			aggregationKey = Util.mkKey(piId, UUID.randomUUID());
		}

		aggregateHU = handlingUnitsBL.isAggregateHU(hu);
		if (aggregateHU)
		{
			aggregatedHUCount = hu.getM_HU_Item_Parent().getQty().intValueExact(); // no NPE because isAggregateHU(hu) returned true.
		}
		else
		{
			aggregatedHUCount = 0;
		}
	}

	private IHUStorage getHUStorage()
	{
		if (_huStorage == null)
		{
			final I_M_HU hu = getM_HU();
			_huStorage = getKeyFactory().getStorageFactory().getStorage(hu);
		}
		return _huStorage;
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		return new HUKeyNameBuilder(this);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public final I_M_HU getM_HU()
	{
		return _hu;
	}
	
	public final int getM_HU_ID()
	{
		return _hu.getM_HU_ID();
	}

	@Override
	public Object getAggregationKey()
	{
		return aggregationKey;
	}

	@Override
	public List<IHUProductStorage> getProductStorages()
	{
		// NOTE: don't cache at this level because we rely on having fresh data here
		return getHUStorage().getProductStorages();
	}

	public IHUProductStorage getProductStorage(final I_M_Product product)
	{
		return getHUStorage().getProductStorage(product);
	}

	public IHUProductStorage getProductStorageOrNull(final I_M_Product product)
	{
		return getHUStorage().getProductStorageOrNull(product);
	}

	public final I_C_UOM getStorageUOMOrNull()
	{
		return getHUStorage().getC_UOMOrNull();
	}

	private IHUDocumentLine getHUDocumentLineOrNull()
	{
		return _documentLine;
	}

	@Override
	public IHUDocumentLine findDocumentLineOrNull()
	{
		IHUDocumentLine documentLine = null;

		//
		// Find HUDocumentLine recursively starting from this node
		IHUKey key = this;
		while (key != null)
		{
			if (key instanceof HUKey)
			{
				documentLine = ((HUKey)key).getHUDocumentLineOrNull();
			}
			if (documentLine != null)
			{
				break;
			}

			key = key.getParent();
		}

		return documentLine;
	}

	// @Override
	public IHUDocumentLine findDocumentLine()
	{
		final IHUDocumentLine documentLine = findDocumentLineOrNull();
		if (documentLine == null)
		{
			throw new AdempiereException("DocumentLine was not found for " + this);
		}
		return documentLine;
	}

	/**
	 * @return {@code true} if the wrapped HU is a virtual <b>and not</b> aggregate HU.
	 */
	@Override
	public boolean isVirtualPI()
	{
		return virtualPI && !aggregateHU;
	}

	@Override
	public boolean isGrouping()
	{
		return false;
	}

	@Override
	public void removeFromParent()
	{
		final IHUKey parent = getParent();
		if (null != parent)
		{
			parent.removeChild(this);
		}
	}

	@Override
	public boolean destroyIfEmptyStorage()
	{
		//
		// Destroy this HU if it was empty storage
		// NOTE: this will also destroy all it's parents if empty.
		final I_M_HU hu = getM_HU();
		final boolean destroyed = handlingUnitsBL.destroyIfEmptyStorage(hu);

		//
		// Case: HU was destroyed (now or in the past)
		if (destroyed)
		{
			// Remove allocations between this destroyed HU and it's document line
			final IHUDocumentLine documentLine = findDocumentLineOrNull();
			if (documentLine != null)
			{
				//
				// Always run removal in transaction (thread needs a transaction)
				Services.get(ITrxManager.class).run(new TrxRunnable()
				{
					@Override
					public void run(final String localTrxName) throws Exception
					{
						documentLine.getHUAllocations().removeAssignedHUs(Collections.singletonList(hu));
					}
				});
			}

			final IHUKey parentOld = getParent();
			removeFromParent();

			// Make sure we are refreshing the OLD parent because it could happend to be also destroyed
			if (parentOld != null)
			{
				parentOld.refresh();
			}
		}
		//
		// Case: HU is not destroyed
		else
		{
			refresh();
		}
		return destroyed;
	}

	@Override
	public IAllocationSource createAllocationSource()
	{
		// FIXME duplicate code
		final I_M_HU hu = getM_HU();

		final IHUDocumentLine documentLine = findDocumentLineOrNull();
		if (documentLine == null)
		{
			// If there is no document line, just create a simple Source/Destination that wraps our HU.
			return HUListAllocationSourceDestination.of(hu);
		}

		return documentLine.createAllocationSource(hu);
	}

	/**
	 * @return <code>true</code> if <code>this</code> key's HU is locked or if the key's parent is read-only. Note that currently, a HU is locked when it is added to a shipper transportation.
	 */
	@Override
	public boolean isReadonly()
	{
		// Check if it was programatically set as read-only on super-class
		if (super.isReadonly())
		{
			return true;
		}

		final I_M_HU hu = getM_HU();
		if(huLockBL.isLocked(hu))
		{
			return true;
		}

		// Inherit read-only attribute from it's parent
		final IHUKey parent = getParent();
		return parent != null && parent.isReadonly();
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("HUKey [id=");
		builder.append(id);
		builder.append(", _documentLine=");
		builder.append(_documentLine);
		builder.append(", hu=").append(_hu);
		builder.append(", value=");
		builder.append(value);
		builder.append(", virtualPI=");
		builder.append(virtualPI);
		builder.append(", huStorage=").append(_huStorage == null ? "<not loaded>" : _huStorage.toString());
		builder.append(", aggregationKey=");
		builder.append(aggregationKey);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Does nothing, just returns the given <code>children</code> unchanged.
	 */
	@Override
	protected Collection<IHUKey> preprocessChildrenBeforeAdding(final Collection<IHUKey> children)
	{
		return children;
	}

	/**
	 *
	 * @return actual HU's weight or <code>null</code> if not available
	 */
	public final BigDecimal getWeightActualOrNull()
	{
		return getWeightOrNull()
				.getWeightNetOrNull();
	}

	@Override
	protected List<IHUKey> retrieveChildren()
	{
		final IHUKeyFactory keyFactory = getKeyFactory();
		final List<IHUKey> childKeys = keyFactory.createChildKeys(this);
		return childKeys;
	}

	@Override
	public void refresh()
	{
		//
		// Refresh underlying HU,
		// but do this only if it's not destroyed because if it's destroyed makes no sense because:
		// * destroyed is considered an ireversible state, so no news are expected
		// * it will be removed from parent anyways
		if (!isDestroyed())
		{
			InterfaceWrapperHelper.markStaled(_hu);
			_huStorage = null;
		}

		super.refresh();
	}

	/**
	 * Checks if the underlying HU is destroyed.
	 * 
	 * NOTE: this method will NEVER refresh the underlying HU before checking it because this method is supported to be called a lot of times and this would not be performant
	 * 
	 * @return true if the underlying HU is destroyed
	 */
	@Override
	public boolean isDestroyed()
	{
		return handlingUnitsBL.isDestroyed(getM_HU());
	}

	public boolean isAggregateHU()
	{
		return aggregateHU;
	}

	public int getAggregatedHUCount()
	{
		return aggregatedHUCount;
	}

	/**
	 * @return {@link IQualityInspectionSchedulable} if the underlying HU supports it.
	 * @task 08639
	 */
	public final Optional<IQualityInspectionSchedulable> asQualityInspectionSchedulable()
	{
		final IAttributeStorage attributes = getAttributeSet();
		return Services.get(IHUMaterialTrackingBL.class).asQualityInspectionSchedulable(getTerminalContext(), attributes);
	}
}
