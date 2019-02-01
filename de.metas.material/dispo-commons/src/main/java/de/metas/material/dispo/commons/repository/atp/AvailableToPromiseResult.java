package de.metas.material.dispo.commons.repository.atp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributesKey;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@Data
public class AvailableToPromiseResult
{
	public static AvailableToPromiseResult createEmpty()
	{
		return new AvailableToPromiseResult(new ArrayList<>());
	}

	@NonNull
	public static AvailableToPromiseResult createEmptyWithPredefinedBuckets(
			@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		final ImmutableList.Builder<AvailableToPromiseResultGroup> resultBuilder = ImmutableList.builder();

		for (final AvailableToPromiseQuery query : multiQuery.getQueries())
		{
			final List<IPair<AttributesKey, Predicate<AttributesKey>>> storageAttributesKeysAndMatchers = extractStorageAttributesKeyAndMatchers(query);

			Set<Integer> warehouseIds = query.getWarehouseIds();
			if (warehouseIds.isEmpty())
			{
				warehouseIds = ImmutableSet.of(AvailableToPromiseResultGroup.WAREHOUSE_ID_ANY);
			}

			final BPartnerClassifier bpartner = query.getBpartner();
			final List<Integer> productIds = query.getProductIds();

			for (final int warehouseId : warehouseIds)
			{
				for (final int productId : productIds)
				{
					for (final IPair<AttributesKey, Predicate<AttributesKey>> storageAttributesKeyAndMatcher : storageAttributesKeysAndMatchers)
					{
						resultBuilder.add(AvailableToPromiseResultGroup.builder()
								.productId(productId)
								.storageAttributesKey(storageAttributesKeyAndMatcher.getLeft())
								.storageAttributesKeyMatcher(storageAttributesKeyAndMatcher.getRight())
								.warehouseId(warehouseId)
								.bpartner(bpartner)
								.build());
					}
				}
			}
		}

		return new AvailableToPromiseResult(resultBuilder.build());
	}

	private static final List<IPair<AttributesKey, Predicate<AttributesKey>>> extractStorageAttributesKeyAndMatchers(@NonNull final AvailableToPromiseQuery query)
	{
		final List<AttributesKey> storageAttributesKeys = query.getStorageAttributesKeys();
		if (storageAttributesKeys.isEmpty())
		{
			final AttributesKey attributesKey = AttributesKey.ALL;
			return ImmutableList.of(ImmutablePair.of(attributesKey, createStorageAttributesKeyMatcher(attributesKey)));
		}
		else if (!storageAttributesKeys.contains(AttributesKey.OTHER))
		{
			return storageAttributesKeys.stream()
					.map(storageAttributesKey -> ImmutablePair.of(storageAttributesKey, createStorageAttributesKeyMatcher(storageAttributesKey)))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			final Predicate<AttributesKey> othersMatcher = storageAttributesKeys.stream()
					.filter(storageAttributesKey -> !AttributesKey.ALL.equals(storageAttributesKey))
					.filter(storageAttributesKey -> !AttributesKey.OTHER.equals(storageAttributesKey))
					.map(storageAttributesKey -> createStorageAttributesKeyMatcher(storageAttributesKey).negate())
					.reduce(Predicate::and)
					.orElse(Predicates.alwaysTrue());

			return storageAttributesKeys.stream()
					.map(storageAttributesKey -> {
						if (AttributesKey.OTHER.equals(storageAttributesKey))
						{
							return ImmutablePair.of(storageAttributesKey, othersMatcher);
						}
						else
						{
							return ImmutablePair.of(storageAttributesKey, createStorageAttributesKeyMatcher(storageAttributesKey));
						}
					})
					.collect(ImmutableList.toImmutableList());
		}
	}

	static Predicate<AttributesKey> createStorageAttributesKeyMatcher(@NonNull final AttributesKey attributesKey)
	{
		if (AttributesKey.ALL.equals(attributesKey))
		{
			return Predicates.alwaysTrue();
		}
		else if (AttributesKey.OTHER.equals(attributesKey))
		{
			throw new AdempiereException("Creating a matcher for 'OTHERS' storage attributes key is not supported at this level");
		}
		else
		{
			return storageAttributesKeyToMatch -> storageAttributesKeyToMatch.contains(attributesKey);
		}
	}

	private final List<AvailableToPromiseResultGroup> resultGroups;

	public void addQtyToAllMatchingGroups(@NonNull final AddToResultGroupRequest request)
	{
		boolean stillNeedsToBeAdded = true;
		for (final AvailableToPromiseResultGroup group : resultGroups)
		{
			if (!group.isMatchting(request))
			{
				continue;
			}
			if (!group.isMatchting(request) && !group.isAlreadyIncluded(request))
			{
				continue;
			}

			if (group.isAlreadyIncluded(request))
			{
				stillNeedsToBeAdded = false; // btw: don't break; a request might be added to >1 groups
			}
			else
			{
				group.addQty(request);
				stillNeedsToBeAdded = false;
			}
		}

		if (stillNeedsToBeAdded)
		{
			throw new AdempiereException("No matching group found for AddToResultGroupRequest")
					.appendParametersToMessage()
					.setParameter("request", request)
					.setParameter("available resultGroups", resultGroups)
					.setParameter("this", this);
		}
	}

	public void addToNewGroupIfFeasible(@NonNull final AddToResultGroupRequest request)
	{
		boolean alreadyIncludedInMatchingGroup = false;

		if(!request.getBpartner().isSpecificBPartner())
		{
			for (final AvailableToPromiseResultGroup resultGroup : resultGroups)
			{
				if (!resultGroup.isMatchting(request))
				{
					continue;
				}
				if (resultGroup.isAlreadyIncluded(request))
				{
					alreadyIncludedInMatchingGroup = true;
				}
				else
				{
					resultGroup.addQty(request);
					alreadyIncludedInMatchingGroup = true;
				}
			}
		}

		if (alreadyIncludedInMatchingGroup)
		{
			return;
		}

		final AvailableToPromiseResultGroup group = AvailableToPromiseResultGroup.builder()
				.productId(request.getProductId())
				.storageAttributesKey(request.getStorageAttributesKey())
				.warehouseId(request.getWarehouseId())
				.bpartner(request.getBpartner())
//				.qty(request.getQty())
//				.date(request.getDate())
//				.seqNo(request.getSeqNo())
//				.empty(false)
				.build();

		group.addQty(request);

		resultGroups.add(group);
	}
}
