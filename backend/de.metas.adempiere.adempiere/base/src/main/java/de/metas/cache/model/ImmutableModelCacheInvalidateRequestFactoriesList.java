package de.metas.cache.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.cache.TableNamesGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public final class ImmutableModelCacheInvalidateRequestFactoriesList
{
	private final ImmutableSetMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName;
	@Getter private final TableNamesGroup tableNamesToEnableRemoveCacheInvalidation;

	@Builder
	private ImmutableModelCacheInvalidateRequestFactoriesList(
			@NonNull final SetMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName,
			@NonNull final TableNamesGroup tableNamesToEnableRemoveCacheInvalidation)
	{
		this.factoriesByTableName = ImmutableSetMultimap.copyOf(factoriesByTableName);
		this.tableNamesToEnableRemoveCacheInvalidation = tableNamesToEnableRemoveCacheInvalidation;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("size", factoriesByTableName.size())
				.add("factories", factoriesByTableName)
				.toString();
	}

	public int size() {return factoriesByTableName.size();}

	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull final String tableName)
	{
		return factoriesByTableName.get(tableName);
	}
}
