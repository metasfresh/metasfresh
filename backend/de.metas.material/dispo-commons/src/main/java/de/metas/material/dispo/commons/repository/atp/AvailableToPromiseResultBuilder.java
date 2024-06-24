package de.metas.material.dispo.commons.repository.atp;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.commons.attributes.clasifiers.ProductClassifier;
import de.metas.material.commons.attributes.clasifiers.WarehouseClassifier;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.keys.AttributesKeyMatcher;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.warehouse.WarehouseId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

final class AvailableToPromiseResultBuilder
{
	public static AvailableToPromiseResultBuilder createEmpty()
	{
		return new AvailableToPromiseResultBuilder(ImmutableList.of(AvailableToPromiseResultBucket.newAcceptingAny()));
	}

	@NonNull
	public static AvailableToPromiseResultBuilder createEmptyWithPredefinedBuckets(@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		final ImmutableList.Builder<AvailableToPromiseResultBucket> buckets = ImmutableList.builder();

		for (final AvailableToPromiseQuery query : multiQuery.getQueries())
		{
			final List<AttributesKeyMatcher> storageAttributesKeyMatchers = AttributesKeyPatternsUtil.extractAttributesKeyMatchers(query.getStorageAttributesKeyPatterns());

			Set<WarehouseId> warehouseIds = query.getWarehouseIds();
			if (warehouseIds.isEmpty())
			{
				warehouseIds = Collections.singleton(null);
			}

			final BPartnerClassifier bpartner = query.getBpartner();
			final List<Integer> productIds = query.getProductIds();

			for (final WarehouseId warehouseId : warehouseIds)
			{
				for (final int productId : productIds)
				{
					for (final AttributesKeyMatcher storageAttributesKeyMatcher : storageAttributesKeyMatchers)
					{
						final AvailableToPromiseResultBucket bucket = AvailableToPromiseResultBucket.builder()
								.warehouse(WarehouseClassifier.specificOrAny(warehouseId))
								.bpartner(bpartner)
								.product(ProductClassifier.specific(productId))
								.storageAttributesKeyMatcher(storageAttributesKeyMatcher)
								.build();

						bucket.addDefaultEmptyGroupIfPossible();

						buckets.add(bucket);
					}
				}
			}
		}

		return new AvailableToPromiseResultBuilder(buckets.build());
	}

	private final ArrayList<AvailableToPromiseResultBucket> buckets;

	@VisibleForTesting
	AvailableToPromiseResultBuilder(final List<AvailableToPromiseResultBucket> buckets)
	{
		this.buckets = new ArrayList<>(buckets);
	}

	public AvailableToPromiseResult build()
	{
		final ImmutableList<AvailableToPromiseResultGroup> groups = buckets.stream()
				.flatMap(AvailableToPromiseResultBucket::buildAndStreamGroups)
				.collect(ImmutableList.toImmutableList());

		return AvailableToPromiseResult.ofGroups(groups);
	}

	public void addQtyToAllMatchingGroups(@NonNull final AddToResultGroupRequest request)
	{
		// note that we might select more quantities than we actually wanted (bc of the way we match attributes in the query using LIKE)
		// for that reason, we need to be lenient in case not all quantities can be added to a bucked
		buckets.forEach(bucket -> bucket.addQtyToAllMatchingGroups(request));
	}

	public void addToNewGroupIfFeasible(@NonNull final AddToResultGroupRequest request)
	{
		boolean addedToAtLeastOneGroup = false;
		for (final AvailableToPromiseResultBucket bucket : buckets)
		{
			if (bucket.addToNewGroupIfFeasible(request))
			{
				addedToAtLeastOneGroup = true;
				break;
			}
		}

		if (!addedToAtLeastOneGroup)
		{
			final AvailableToPromiseResultBucket bucket = newBucket(request);
			if (!bucket.addToNewGroupIfFeasible(request))
			{
				throw new AdempiereException("Internal error: cannot add " + request + " to newly created bucket: " + bucket);
			}
		}
	}

	private AvailableToPromiseResultBucket newBucket(final AddToResultGroupRequest request)
	{
		final AvailableToPromiseResultBucket bucket = AvailableToPromiseResultBucket.builder()
				.warehouse(WarehouseClassifier.specificOrAny(request.getWarehouseId()))
				.bpartner(request.getBpartner())
				.product(ProductClassifier.specific(request.getProductId().getRepoId()))
				.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(request.getStorageAttributesKey()))
				.build();

		buckets.add(bucket);

		return bucket;
	}

	@VisibleForTesting
	ImmutableList<AvailableToPromiseResultBucket> getBuckets()
	{
		return ImmutableList.copyOf(buckets);
	}
}
