package org.adempiere.ad.dao.cache;

import javax.annotation.Nullable;

import org.adempiere.util.ISingletonService;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Service responsible for invalidating model caches.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IModelCacheInvalidationService extends ISingletonService
{
	void register(String tableName, ModelCacheInvalidateRequestFactory requestFactory);

	@Nullable
	CacheInvalidateMultiRequest createRequest(Object model, ModelCacheInvalidationTiming timing);

	void invalidate(CacheInvalidateMultiRequest request, ModelCacheInvalidationTiming timing);

	default void invalidateForModel(@NonNull final Object model, @NonNull final ModelCacheInvalidationTiming timing)
	{
		final CacheInvalidateMultiRequest request = createRequest(model, timing);
		if (request == null)
		{
			return;
		}

		invalidate(request, timing);
	}

}
