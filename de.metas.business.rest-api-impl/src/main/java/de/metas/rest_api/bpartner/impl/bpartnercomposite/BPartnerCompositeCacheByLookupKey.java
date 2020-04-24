package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.cache.CacheIndex;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class BPartnerCompositeCacheByLookupKey
{
	private final transient CCache<BPartnerCompositeLookupKey, BPartnerComposite> cache;

	public BPartnerCompositeCacheByLookupKey(@NonNull final String identifier)
	{
		final CacheIndex<BPartnerId/* RK */, BPartnerCompositeLookupKey/* CK */, BPartnerComposite/* V */> //
		cacheIndex = CacheIndex.of(new BPartnerCompositeCacheIndex());

		cache = CCache.<BPartnerCompositeLookupKey, BPartnerComposite> builder()
				.cacheName("BPartnerComposite_by_LookupKey" + "_" + identifier)
				.additionalTableNameToResetFor(I_AD_User.Table_Name)
				.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
				.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
				.cacheMapType(CacheMapType.LRU)
				.initialCapacity(100)
				.invalidationKeysMapper(cacheIndex::computeCachingKeys)
				.removalListener(cacheIndex::remove)
				.additionListener(cacheIndex::add)
				.build();
	}

	public Collection<BPartnerComposite> getAllOrLoad(
			@NonNull final Collection<BPartnerCompositeLookupKey> keys,
			@NonNull final Function<Collection<BPartnerCompositeLookupKey>, Map<BPartnerCompositeLookupKey, BPartnerComposite>> valuesLoader)
	{
		return cache.getAllOrLoad(keys, valuesLoader);
	}

	/** Get all the records, assuming that there is a cache entry for each single record. If not, throw an exception. */
	@VisibleForTesting
	public Collection<BPartnerComposite> getAssertAllCached(
			@NonNull final Collection<BPartnerCompositeLookupKey> keys)
	{
		final ImmutableList.Builder<BPartnerComposite> result = ImmutableList.builder();
		for (final BPartnerCompositeLookupKey key : keys)
		{
			result.add(cache.getOrElseThrow(
					key,
					() -> new AdempiereException("Missing record for key=" + key)));
		}
		return result.build();
	}
}
