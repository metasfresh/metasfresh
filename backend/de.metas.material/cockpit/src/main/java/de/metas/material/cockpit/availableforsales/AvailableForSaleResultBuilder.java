/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.cockpit.availableforsales;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.material.commons.attributes.clasifiers.ProductClassifier;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.mm.attributes.keys.AttributesKeyMatcher;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class AvailableForSaleResultBuilder
{
	public static AvailableForSaleResultBuilder createEmpty()
	{
		return new AvailableForSaleResultBuilder(ImmutableList.of(AvailableForSaleResultBucket.newAcceptingAny()));
	}

	@NonNull
	public static AvailableForSaleResultBuilder createEmptyWithPredefinedBuckets(@NonNull final AvailableForSalesMultiQuery multiQuery)
	{
		final ImmutableList.Builder<AvailableForSaleResultBucket> buckets = ImmutableList.builder();

		final List<AvailableForSalesQuery> availableForSalesQueries = multiQuery.getAvailableForSalesQueries();
		for (final AvailableForSalesQuery query : availableForSalesQueries)
		{
			final List<AttributesKeyMatcher> storageAttributesKeyMatchers = AttributesKeyPatternsUtil.extractAttributesKeyMatchers(Collections.singletonList(query.getStorageAttributesKeyPattern()));

			final ProductId productId = query.getProductId();

			for (final AttributesKeyMatcher storageAttributesKeyMatcher : storageAttributesKeyMatchers)
			{
				final AvailableForSaleResultBucket bucket = AvailableForSaleResultBucket.builder()
						.product(ProductClassifier.specific(productId.getRepoId()))
						.storageAttributesKeyMatcher(storageAttributesKeyMatcher)
						.build();

				bucket.addDefaultEmptyGroupIfPossible();

				buckets.add(bucket);
			}
		}

		return new AvailableForSaleResultBuilder(buckets.build());
	}

	private final ArrayList<AvailableForSaleResultBucket> buckets;

	@VisibleForTesting
	AvailableForSaleResultBuilder(final List<AvailableForSaleResultBucket> buckets)
	{
		this.buckets = new ArrayList<>(buckets);
	}

	public AvailableForSalesLookupResult build()
	{
		final ImmutableList<AvailableForSalesLookupBucketResult> groups = buckets.stream()
				.flatMap(AvailableForSaleResultBucket::buildAndStreamGroups)
				.collect(ImmutableList.toImmutableList());

		return AvailableForSalesLookupResult.builder().availableForSalesResults(groups).build();
	}

	public void addQtyToAllMatchingGroups(@NonNull final AddToResultGroupRequest request)
	{
		// note that we might select more quantities than we actually wanted (bc of the way we match attributes in the query using LIKE)
		// for that reason, we need to be lenient in case not all quantities can be added to a bucked
		buckets.forEach(bucket -> bucket.addQtyToAllMatchingGroups(request));
	}

	@VisibleForTesting
	ImmutableList<AvailableForSaleResultBucket> getBuckets()
	{
		return ImmutableList.copyOf(buckets);
	}
}
