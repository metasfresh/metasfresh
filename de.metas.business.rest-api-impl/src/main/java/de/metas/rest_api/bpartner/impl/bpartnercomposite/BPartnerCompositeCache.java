package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
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

public class BPartnerCompositeCache
{
	private final transient CCache<BPartnerCompositeLookupKey, BPartnerComposite> cache;

	private final transient CacheIndex<BPartnerId/* RK */, BPartnerCompositeLookupKey/* CK */, BPartnerComposite/* V */> cacheIndex = new CacheIndex<>(new BPartnerCompositeCacheIndex());

	public BPartnerCompositeCache(@NonNull final String identifier)
	{
		cache = CCache.<BPartnerCompositeLookupKey, BPartnerComposite> builder()
				.cacheName("bpartnerQuery2BPartnerId" + "_" + identifier)
				.additionalTableNameToResetFor(I_AD_User.Table_Name)
				.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
				.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
				.cacheMapType(CacheMapType.LRU)
				.initialCapacity(100)
				.invalidationKeysMapper(cacheIndex::computeCachingKeys)
				.removalListener(cacheIndex::remove)
				.build();
	}

	public Collection<BPartnerComposite> getAllOrLoad(
			@NonNull final Collection<BPartnerCompositeLookupKey> keys,
			@NonNull final Function<Collection<BPartnerCompositeLookupKey>, Map<BPartnerCompositeLookupKey, BPartnerComposite>> valuesLoader)
	{
		return cache.getAllOrLoad(keys, valuesLoader);
	}
}
