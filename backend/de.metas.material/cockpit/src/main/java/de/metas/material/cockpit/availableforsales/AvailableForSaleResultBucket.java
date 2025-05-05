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
import org.adempiere.mm.attributes.keys.AttributesKeyMatcher;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.material.commons.attributes.clasifiers.ProductClassifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

@ToString
final class AvailableForSaleResultBucket
{
	public static AvailableForSaleResultBucket newAcceptingAny()
	{
		return builder().build();
	}

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final ProductClassifier product;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final AttributesKeyMatcher storageAttributesKeyMatcher;

	private final ArrayList<AvailableForSaleResultGroupBuilder> groups = new ArrayList<>();

	@Builder
	private AvailableForSaleResultBucket(
			@Nullable final ProductClassifier product,
			@Nullable final AttributesKeyMatcher storageAttributesKeyMatcher)
	{
		this.product = product != null ? product : ProductClassifier.any();
		this.storageAttributesKeyMatcher = storageAttributesKeyMatcher != null
				? storageAttributesKeyMatcher
				: AttributesKeyPatternsUtil.matchingAll();
	}

	Stream<AvailableForSalesLookupBucketResult> buildAndStreamGroups()
	{
		return groups.stream().map(AvailableForSaleResultGroupBuilder::build);
	}

	private boolean isGroupMatching(
			@NonNull final AvailableForSaleResultGroupBuilder group,
			@NonNull final AddToResultGroupRequest request)
	{
		if (!Objects.equals(group.getProductId(), request.getProductId()))
		{
			return false;
		}

		return isGroupAttributesKeyMatching(group, request.getStorageAttributesKey()) && isExactGroupMatch(group, request);
	}

	private boolean isExactGroupMatch(final @NonNull AvailableForSaleResultGroupBuilder group, final @NonNull AddToResultGroupRequest request)
	{
		final AttributesKey storageAttributesKey = group.getStorageAttributesKey();
		return storageAttributesKey.isAll() || storageAttributesKey.isOther() ||
				storageAttributesKeyMatcher.toAttributeKeys(request.getStorageAttributesKey()).getAsString().equals(storageAttributesKey.getAsString());
	}

	@VisibleForTesting
	boolean isMatching(final AddToResultGroupRequest request)
	{
		if (!product.isMatching(request.getProductId().getRepoId()))
		{
			return false;
		}

		return storageAttributesKeyMatcher.matches(request.getStorageAttributesKey());
	}

	public void addQtyToAllMatchingGroups(@NonNull final AddToResultGroupRequest request)
	{
		if (!isMatching(request))
		{
			return;
		}

		boolean addedToAtLeastOneGroup = false;
		for (final AvailableForSaleResultGroupBuilder group : groups)
		{
			if (!isGroupMatching(group, request))
			{
				continue;
			}

			if (!group.isAlreadyIncluded(request))
			{
				group.addQty(request);
			}

			addedToAtLeastOneGroup = true;
		}

		if (!addedToAtLeastOneGroup)
		{
			final AttributesKey storageAttributesKey = storageAttributesKeyMatcher.toAttributeKeys(request.getStorageAttributesKey());
			final AvailableForSaleResultGroupBuilder group = newGroup(request, storageAttributesKey);
			group.addQty(request);
		}
	}

	private boolean isGroupAttributesKeyMatching(
			@NonNull final AvailableForSaleResultGroupBuilder group,
			@NonNull final AttributesKey requestStorageAttributesKey)
	{
		final AttributesKey groupAttributesKey = group.getStorageAttributesKey();
		if (groupAttributesKey.isAll())
		{
			return true;
		}
		else if (groupAttributesKey.isOther())
		{
			// accept it. We assume that the actual matching was done on Bucket level and not on Group level
			return true;
		}
		else if (groupAttributesKey.isNone())
		{
			// shall not happen
			return false;
		}
		else
		{
			final AttributesKeyPattern groupAttributePattern = AttributesKeyPatternsUtil.ofAttributeKey(groupAttributesKey);

			return groupAttributePattern.matches(requestStorageAttributesKey);
		}
	}

	private AvailableForSaleResultGroupBuilder newGroup(
			@NonNull final AddToResultGroupRequest request,
			@NonNull final AttributesKey storageAttributesKey)
	{
		final AvailableForSaleResultGroupBuilder group = AvailableForSaleResultGroupBuilder.builder()
				.productId(request.getProductId())
				.storageAttributesKey(storageAttributesKey)
				.build();

		groups.add(group);

		return group;
	}

	public void addDefaultEmptyGroupIfPossible()
	{
		if (product.isAny())
		{
			return;
		}

		final AttributesKey defaultAttributesKey = this.storageAttributesKeyMatcher.toAttributeKeys().orElse(null);
		if (defaultAttributesKey == null)
		{
			return;
		}

		final AvailableForSaleResultGroupBuilder group = AvailableForSaleResultGroupBuilder.builder()
				.productId(ProductId.ofRepoId(product.getProductId()))
				.storageAttributesKey(defaultAttributesKey)
				.build();

		groups.add(group);
	}

}
