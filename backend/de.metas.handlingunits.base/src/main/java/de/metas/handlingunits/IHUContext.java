package de.metas.handlingunits;

import java.time.ZonedDateTime;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.List;

import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IContextAware;

import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUStorageFactory;

import javax.annotation.Nullable;

/**
 * Immutable Handling Unit Context. Use {@link IHUContextFactory} to create an instance.
 * <p>
 * Implementations do not propagate anything back to the database. It is supposed to be used to pass already loaded things, transaction names etc. Will be extended as
 * required in future (e.g. to also pass on options).
 *
 * @author tsa
 *
 */
public interface IHUContext extends IContextAware
{
	/**
	 * DateTrx provider used to get the DateTrx when a new IHUContext is created.
	 * 
	 * By default, it's returning the system date, but if you want to temporary override it, use {@link HUContextDateTrxProvider#temporarySet(Date)}.
	 */
	HUContextDateTrxProvider DateTrxProvider = new HUContextDateTrxProvider();

	String PROPERTY_Configured = "Configured";

	/**
	 * {@link Boolean} flag used to advice the HU transaction processor that we are doing Weight to Storage Qty adjustments.
	 * 
	 * In this case, we would expect transaction listeners to NOT change some Weight attribute because some storage qty was changed.
	 * 
	 * task http://dewiki908/mediawiki/index.php/08728_HU_Weight_Net_changes_after_Material_Receipt_%28107972107210%29
	 */
	String PROPERTY_IsStorageAdjustmentFromWeightAttribute = "IsStorageAdjustmentFromWeightAttribute";

	@Nullable
	<T> T getProperty(String propertyName);

	@Nullable
	Object setProperty(String propertyName, Object value);

	/**
	 * Create a mutable copy of this context.
	 *
	 * NOTE: this method won't do a deep copy (e.g. in case of storages it will just assign then to newly create mutable context; it will NOT deep copy them too)
	 *
	 * @return mutable copy of this context
	 */
	IMutableHUContext copyAsMutable();

	/**
	 * @return Qty storage factory; never return null
	 */
	IHUStorageFactory getHUStorageFactory();

	/**
	 * @return attribute storage factory; never return null
	 */
	IAttributeStorageFactory getHUAttributeStorageFactory();

	/**
	 * @return processing date to be used
	 */
	ZonedDateTime getDate();

	/**
	 * Returns the collector that is supposed to be used to keep track of packaging material that was allocated/"picked up" during the creation of new HUs, and the packaging material that was
	 * released/discarded during the destruction of HUs.
	 *
	 * task 07617
	 */
	IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> getHUPackingMaterialsCollector();

	/** @return transaction listeners */
	IHUTrxListener getTrxListeners();

	/** @return previously added listeners that want to be notified before an empty HU is destroyed */
	List<EmptyHUListener> getEmptyHUListeners();

	/**
	 * Flush the contents stored only in memory.
	 * Useful for example to persist the HU Attributes.
	 */
	void flush();

	/**
	 * Temporarily make sure that the given HU is not destroyed.
	 * We sometimes need this when splitting out something from a TU that is on an LU
	 * and the new TU has to be on the same LU. 
	 * Without this, the LU might be destroyed after the old TU was destroyed (if it was the last one) and before the new TU was added.
	 * 
	 * @return a closable that makes the HUId destructible again. 
	 * Note that the result of {@link #copyAsMutable()} contains a copy and this closable won'T remove the HuId from the copy. 
	 */
	IAutoCloseable temporarilyDontDestroyHU(@NonNull HuId huId);

	/**
	 * @return {@code true} if the HU shall not be destroyed right now
	 */
	boolean isDontDestroyHu(@NonNull HuId ofRepoId);
}
