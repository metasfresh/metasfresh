/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.promotioncode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PromotionCode;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository Tables: C_PromotionCode
 * Repository Cluster: PromotionCodeRepository
 */
@Repository
public class PromotionCodeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Caches Value → PromotionCodeId for fast by-value lookups.
	 * Optional.empty() means "not found" so we do NOT re-query on every repeated bad code.
	 * Invalidated automatically on any C_PromotionCode table change.
	 */
	private final CCache<String, Optional<PromotionCodeId>> cacheByValue = CCache.<String, Optional<PromotionCodeId>>builder()
			.tableName(I_C_PromotionCode.Table_Name)
			.build();

	/**
	 * Caches PromotionCodeId → Value for the reverse direction (used by getValuesByIds).
	 * null is never put here; ids absent from the DB are simply not cached.
	 * Invalidated automatically on any C_PromotionCode table change.
	 */
	private final CCache<PromotionCodeId, String> cacheById = CCache.<PromotionCodeId, String>builder()
			.tableName(I_C_PromotionCode.Table_Name)
			.build();

	@NonNull
	public PromotionCodeId getPromotionCodeIdByValue(@NonNull final String value)
	{
		final PromotionCodeId id = cacheByValue.getOrLoadNonNull(value, () -> lookupIdByValue(value)).orElse(null);
		if (id == null)
		{
			throw new AdempiereException("Promotion code not found: " + value)
					.appendParametersToMessage()
					.setParameter("Value", value);
		}
		return id;
	}

	/** Returns Optional.empty() when not found — never returns null. */
	@NonNull
	private Optional<PromotionCodeId> lookupIdByValue(@NonNull final String value)
	{
		final PromotionCodeId id = queryBL
				.createQueryBuilderOutOfTrx(I_C_PromotionCode.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PromotionCode.COLUMNNAME_Value, value)
				.create()
				.firstIdOnly(PromotionCodeId::ofRepoIdOrNull);
		return Optional.ofNullable(id);
	}

	@Nullable
	public String getValueByIdOrNull(@Nullable final PromotionCodeId promotionCodeId)
	{
		if (promotionCodeId == null)
		{
			return null;
		}
		return getValuesByIds(ImmutableList.of(promotionCodeId)).get(promotionCodeId);
	}

	/**
	 * Batch reverse-lookup: maps each given {@link PromotionCodeId} to its {@code C_PromotionCode.Value}
	 * in a single query, so callers serializing a list of records avoid an N+1 round-trip.
	 * Note: filters by PK only (no active filter) — the ids come from stored columns, so a since-deactivated
	 * code is still echoed to reflect the persisted state. Blank values and missing ids are absent from the map.
	 * <p>
	 * Cache-aware: ids already cached are served from {@link #cacheById} without a DB round-trip;
	 * only the misses are fetched in one batch query, and the results are back-filled into the cache.
	 */
	@NonNull
	public ImmutableMap<PromotionCodeId, String> getValuesByIds(@NonNull final Collection<PromotionCodeId> promotionCodeIds)
	{
		if (promotionCodeIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<PromotionCodeId, String> result = ImmutableMap.builder();
		final List<PromotionCodeId> misses = new ArrayList<>();

		for (final PromotionCodeId id : promotionCodeIds)
		{
			final String cachedValue = cacheById.get(id);
			if (cachedValue != null)
			{
				result.put(id, cachedValue);
			}
			else
			{
				misses.add(id);
			}
		}

		if (!misses.isEmpty())
		{
			queryBL
					.createQueryBuilderOutOfTrx(I_C_PromotionCode.class)
					.addInArrayFilter(I_C_PromotionCode.COLUMNNAME_C_PromotionCode_ID, misses)
					.create()
					.stream()
					.forEach(record -> {
						final String value = record.getValue();
						if (!Check.isBlank(value))
						{
							final PromotionCodeId id = PromotionCodeId.ofRepoId(record.getC_PromotionCode_ID());
							cacheById.put(id, value);
							result.put(id, value);
						}
					});
		}

		return result.build();
	}
}
