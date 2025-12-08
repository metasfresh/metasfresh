package de.metas.material.dispo.commons.repository.atp;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import org.adempiere.mm.attributes.keys.AttributesKeyMatcher;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.mm.attributes.keys.ExcludeAttributesKeyMatcher;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.commons.attributes.clasifiers.ProductClassifier;
import de.metas.material.commons.attributes.clasifiers.WarehouseClassifier;
import de.metas.material.dispo.commons.repository.atp.AddToResultGroupRequest.AddToResultGroupRequestBuilder;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

public class AvailableToPromiseResultTest
{
	private static final String delim = AttributesKey.ATTRIBUTES_KEY_DELIMITER;
	private static final ZonedDateTime NOW = ZonedDateTime.now();

	private static AttributesKeyPattern pattern(final String attributesKeyString)
	{
		return AttributesKeyPatternsUtil.ofAttributeKey(AttributesKey.ofString(attributesKeyString));
	}

	private static AttributesKeyMatcher matcher(final String attributesKeyString)
	{
		return AttributesKeyPatternsUtil.matching(AttributesKey.ofString(attributesKeyString));
	}

	@Nested
	public class bucket_isMatching
	{
		@Test
		public void single_storageAttributesKey()
		{
			final AvailableToPromiseResultBucket bucket = AvailableToPromiseResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.warehouse(WarehouseClassifier.specific(WarehouseId.ofRepoId(100)))
					.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("1")))
					.build();

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.productId(ProductId.ofRepoId(100001))
					.warehouseId(WarehouseId.ofRepoId(100))
					.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
					.qty(BigDecimal.ONE)
					.seqNo(1)
					.date(NOW.toInstant());

			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("1")).build())).isTrue();
			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("11")).build())).isFalse();

			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "2")).build()))
					.as("Should match because the AvailableToPromiseResultGroup's storageAttributesKey <%s> is included in the given storageAttributesKeyToMatch <%s>",
							bucket.getStorageAttributesKeyMatcher(),
							AttributesKey.ofString("1" + delim + "2"))
					.isTrue();
		}

		@Test
		public void two_storageAttributesKeys()
		{
			final AvailableToPromiseResultBucket bucket = AvailableToPromiseResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.warehouse(WarehouseClassifier.specific(WarehouseId.ofRepoId(100)))
					.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("1" + delim + "3")))
					.build();

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.productId(ProductId.ofRepoId(100001))
					.warehouseId(WarehouseId.ofRepoId(100))
					.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
					.qty(BigDecimal.ONE)
					.seqNo(1)
					.date(NOW.toInstant());

			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "3")).build())).isTrue();
			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("1")).build())).isFalse();

			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(AttributesKey.ofString("10" + delim + "1" + delim + "3")).build())).isTrue();

			final AttributesKey keyWithOtherElementInTheMiddle = AttributesKey.ofString("1" + delim + "2" + delim + "3");
			assertThat(bucket.isMatching(requestBuilder.storageAttributesKey(keyWithOtherElementInTheMiddle).build()))
					.as("Shall match because the elements of the AvailableToPromiseResultGroup's storageAttributesKey <%s> contain every element of the given storageAttributesKeyToMatch <%s>",
							bucket.getStorageAttributesKeyMatcher(),
							keyWithOtherElementInTheMiddle)
					.isTrue();
		}
	}

	@Nested
	public class createEmptyWithPredefinedBuckets
	{
		@Test
		public void with_2products_2attributeKeys()
		{
			final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
					.of(AvailableToPromiseQuery.builder()
							.productId(20)
							.productId(10)
							.storageAttributesKeyPattern(pattern("1" + delim + "2"))
							.storageAttributesKeyPattern(pattern("1" + delim + "2" + delim + "3"))
							.date(NOW)
							.build());

			final AvailableToPromiseResultBuilder resultBuilder = AvailableToPromiseResultBuilder.createEmptyWithPredefinedBuckets(query);

			//
			// Check buckets
			final List<AvailableToPromiseResultBucket> emptyBuckets = resultBuilder.getBuckets();
			assertThat(emptyBuckets).hasSize(4);
			/* bucket 1 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(0);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(20);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isEqualTo(matcher("1" + delim + "2"));
				assertThat(bucket.isZeroQty()).isTrue();
			}
			/* bucket 2 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(1);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(20);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isEqualTo(matcher("1" + delim + "2" + delim + "3"));
				assertThat(bucket.isZeroQty()).isTrue();
			}
			/* bucket 3 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(2);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(10);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isEqualTo(matcher("1" + delim + "2"));
				assertThat(bucket.isZeroQty()).isTrue();
			}
			/* bucket 4 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(3);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(10);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isEqualTo(matcher("1" + delim + "2" + delim + "3"));
				assertThat(bucket.isZeroQty()).isTrue();
			}

			//
			// Check auto-generated empty groups
			final ImmutableList<AvailableToPromiseResultGroup> emptyGroups = resultBuilder.build().getResultGroups();
			assertThat(emptyGroups).hasSize(4);
			/* group 1 */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(0);
				assertThat(group.getProductId().getRepoId()).isEqualTo(20);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("1" + delim + "2"));
				assertThat(group.getQty()).isZero();
			}
			/* group 2 */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(1);
				assertThat(group.getProductId().getRepoId()).isEqualTo(20);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("1" + delim + "2" + delim + "3"));
				assertThat(group.getQty()).isZero();
			}
			/* group 3 */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(2);
				assertThat(group.getProductId().getRepoId()).isEqualTo(10);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("1" + delim + "2"));
				assertThat(group.getQty()).isZero();
			}
			/* group 4 */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(3);
				assertThat(group.getProductId().getRepoId()).isEqualTo(10);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("1" + delim + "2" + delim + "3"));
				assertThat(group.getQty()).isZero();
			}
		}

		@Test
		public void without_attributeKeys()
		{
			final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.of(AvailableToPromiseQuery.builder()
					.productId(10)
					.date(NOW)
					.build());

			final AvailableToPromiseResultBuilder resultBuilder = AvailableToPromiseResultBuilder.createEmptyWithPredefinedBuckets(query);
			final List<AvailableToPromiseResultBucket> emptyBuckets = resultBuilder.getBuckets();
			assertThat(emptyBuckets).hasSize(1);
			{
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(0);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(10);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isSameAs(AttributesKeyPatternsUtil.matchingAll());
				assertThat(bucket.isZeroQty()).isTrue();
			}

			//
			// Check auto-generated empty groups
			final ImmutableList<AvailableToPromiseResultGroup> emptyGroups = resultBuilder.build().getResultGroups();
			assertThat(emptyGroups).hasSize(1);
			{
				final AvailableToPromiseResultGroup group = emptyGroups.get(0);
				assertThat(group.getProductId().getRepoId()).isEqualTo(10);
				assertThat(group.getStorageAttributesKey()).isSameAs(AttributesKey.ALL);
				assertThat(group.getQty()).isZero();
			}
		}

		@Test
		public void with_ALL_attributeWildcard_OTHERS()
		{
			final AttributeId attributeId = AttributeId.ofRepoId(111);

			final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
					.of(AvailableToPromiseQuery.builder()
							.productId(10)
							.storageAttributesKeyPattern(AttributesKeyPattern.ALL)
							.storageAttributesKeyPattern(AttributesKeyPattern.attributeId(attributeId))
							.storageAttributesKeyPattern(AttributesKeyPattern.OTHER)
							.date(NOW)
							.build());

			final AvailableToPromiseResultBuilder resultBuilder = AvailableToPromiseResultBuilder.createEmptyWithPredefinedBuckets(query);

			//
			// Check buckets
			final List<AvailableToPromiseResultBucket> emptyBuckets = resultBuilder.getBuckets();
			assertThat(emptyBuckets).hasSize(3);
			/* bucket 1 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(0);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isSameAs(AttributesKeyPatternsUtil.matchingAll());
				assertThat(bucket.isZeroQty()).isTrue();
			}
			/* bucket 2 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(1);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isEqualTo(AttributesKeyPatternsUtil.matching(AttributesKeyPattern.attributeId(attributeId)));
				assertThat(bucket.isZeroQty()).isTrue();
			}
			/* bucket 3 */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(2);
				assertThat(bucket.getProduct().getProductId()).isEqualTo(10);
				assertThat(bucket.getStorageAttributesKeyMatcher()).isInstanceOf(ExcludeAttributesKeyMatcher.class);
				assertThat(bucket.isZeroQty()).isTrue();
			}

			//
			// Check auto-generated empty groups
			final ImmutableList<AvailableToPromiseResultGroup> emptyGroups = resultBuilder.build().getResultGroups();
			assertThat(emptyGroups).hasSize(2);
			// NOTE: we expect no group from bucket2 because it was a wildcard matching attribute which cannot generate an empty group
			/* group 1 (from bucket1) */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(0);
				assertThat(group.getStorageAttributesKey()).isSameAs(AttributesKey.ALL);
				assertThat(group.getQty()).isZero();
			}
			/* group 2 (from bucket3) */ {
				final AvailableToPromiseResultGroup group = emptyGroups.get(1);
				assertThat(group.getStorageAttributesKey()).isSameAs(AttributesKey.OTHER);
				assertThat(group.getQty()).isZero();
			}
		}
	}

	@Nested
	public class createEmpty
	{
		@Test
		public void afterCall_expectOneBucket_NoAutoGeneratedGroups()
		{
			final AvailableToPromiseResultBuilder resultBuilder = AvailableToPromiseResultBuilder.createEmpty();
			final List<AvailableToPromiseResultBucket> emptyBuckets = resultBuilder.getBuckets();
			assertThat(emptyBuckets).hasSize(1);
			/* bucket 1 - accepting everything */ {
				final AvailableToPromiseResultBucket bucket = emptyBuckets.get(0);
				assertThat(bucket.getWarehouse()).isSameAs(WarehouseClassifier.any());
				assertThat(bucket.getProduct()).isSameAs(ProductClassifier.any());
				assertThat(bucket.getBpartner()).isSameAs(BPartnerClassifier.any());
				assertThat(bucket.getStorageAttributesKeyMatcher()).isSameAs(AttributesKeyPatternsUtil.matchingAll());
				assertThat(bucket.isZeroQty()).isTrue();
			}

			//
			// Make sure no auto-generated groups
			final ImmutableList<AvailableToPromiseResultGroup> emptyGroups = resultBuilder.build().getResultGroups();
			assertThat(emptyGroups).isEmpty();
		}
	}

	@Nested
	public class addQtyToAllMatchingGroups
	{
		@Test
		public void one_bucket_ok()
		{
			final AttributesKey attributesKey = AttributesKey.ofString("1" + delim + "2");

			final AvailableToPromiseResultBuilder result = new AvailableToPromiseResultBuilder(ImmutableList.of(
					AvailableToPromiseResultBucket.builder()
							.bpartner(BPartnerClassifier.any())
							.product(ProductClassifier.specific(100001))
							.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(attributesKey))
							.build()));

			result.addQtyToAllMatchingGroups(AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(ProductId.ofRepoId(100001))
					.storageAttributesKey(attributesKey)
					.qty(new BigDecimal("10"))
					.date(NOW.toInstant())
					.seqNo(1)
					.build());

			final ImmutableList<AvailableToPromiseResultGroup> groups = result.build().getResultGroups();
			assertThat(groups).hasSize(1);

			final AvailableToPromiseResultGroup group = groups.get(0);
			assertThat(group.getProductId().getRepoId()).isEqualTo(100001);
			assertThat(group.getQty()).isEqualByComparingTo("10");
			assertThat(group.getStorageAttributesKey()).isEqualTo(attributesKey);
		}

		@Test
		public void one_bucket_not_matching()
		{
			final AvailableToPromiseResultBuilder result = new AvailableToPromiseResultBuilder(ImmutableList.of(
					AvailableToPromiseResultBucket.builder()
							.product(ProductClassifier.specific(100001))
							.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("1" + delim + "2")))
							.build()));

			final AddToResultGroupRequest request = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(ProductId.ofRepoId(100001))
					.storageAttributesKey(AttributesKey.ofString("1" + delim + "3"))
					.qty(new BigDecimal("10"))
					.date(NOW.toInstant())
					.seqNo(1)
					.build();

			result.addQtyToAllMatchingGroups(request);

			final AvailableToPromiseResult availableToPromiseResult = result.build();

			assertThat(availableToPromiseResult.getResultGroups()).isEmpty();
		}

		@Test
		public void two_buckets()
		{
			final AvailableToPromiseResultBuilder resultBuilder;
			{
				final AvailableToPromiseResultBucket emptyBucket1 = AvailableToPromiseResultBucket.builder()
						.product(ProductClassifier.specific(100001))
						.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("1")))
						.warehouse(WarehouseClassifier.specific(WarehouseId.ofRepoId(100)))
						.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
						.build();

				final AvailableToPromiseResultBucket emptyBucket2 = AvailableToPromiseResultBucket.builder()
						.product(ProductClassifier.specific(100001))
						.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("2")))
						.warehouse(WarehouseClassifier.specific(WarehouseId.ofRepoId(100)))
						.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
						.build();

				resultBuilder = new AvailableToPromiseResultBuilder(ImmutableList.of(emptyBucket1, emptyBucket2));
			}

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.productId(ProductId.ofRepoId(100001))
					.warehouseId(WarehouseId.ofRepoId(100))
					.bpartner(BPartnerClassifier.specific(BPartnerId.ofRepoId(200)))
					.qty(BigDecimal.ONE)
					.seqNo(1)
					.date(NOW.toInstant());

			resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "2")).build());
			resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "2")).build());
			resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("2")).build());

			final List<AvailableToPromiseResultGroup> groups = resultBuilder.build().getResultGroups();
			assertThat(groups).hasSize(2);

			assertThat(groups.get(0).getProductId().getRepoId()).isEqualTo(100001);
			assertThat(groups.get(0).getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("1"));
			assertThat(groups.get(0).getQty()).isEqualByComparingTo("2");

			assertThat(groups.get(1).getProductId().getRepoId()).isEqualTo(100001);
			assertThat(groups.get(1).getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("2"));
			assertThat(groups.get(1).getQty()).isEqualByComparingTo("3");
		}

		@Test
		public void all_attributeWildcard_others_buckets()
		{
			final AvailableToPromiseResultBuilder result = AvailableToPromiseResultBuilder
					.createEmptyWithPredefinedBuckets(AvailableToPromiseMultiQuery
							.of(AvailableToPromiseQuery.builder()
									.productId(10)
									.storageAttributesKeyPattern(AttributesKeyPattern.ALL)
									.storageAttributesKeyPattern(AttributesKeyPattern.attributeId(AttributeId.ofRepoId(111)))
									.storageAttributesKeyPattern(AttributesKeyPattern.OTHER)
									.date(NOW)
									.build()));

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(ProductId.ofRepoId(10))
					// .storageAttributesKey(attributesKey)
					// .qty(qty)
					.date(NOW.minusMinutes(10).toInstant())
					.seqNo(1);

			result.addQtyToAllMatchingGroups(requestBuilder
					.qty(new BigDecimal("100"))
					.storageAttributesKey(AttributesKey.ofString("111=1"))
					.build());
			result.addQtyToAllMatchingGroups(requestBuilder
					.qty(new BigDecimal("50"))
					.storageAttributesKey(AttributesKey.ofString("111=2"))
					.build());
			result.addQtyToAllMatchingGroups(requestBuilder
					.qty(new BigDecimal("30"))
					.storageAttributesKey(AttributesKey.ofString("111=3"))
					.build());
			result.addQtyToAllMatchingGroups(requestBuilder
					.qty(new BigDecimal("10"))
					.storageAttributesKey(AttributesKey.ofString("222=1"))
					.build());

			final ImmutableList<AvailableToPromiseResultGroup> groups = result.build().getResultGroups();
			assertThat(groups).hasSize(5);

			/* group 1 */ {
				final AvailableToPromiseResultGroup group = groups.get(0);
				assertThat(group.getStorageAttributesKey()).isSameAs(AttributesKey.ALL);
				assertThat(group.getQty()).isEqualByComparingTo("190");
			}
			/* group 2 */ {
				final AvailableToPromiseResultGroup group = groups.get(1);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=1"));
				assertThat(group.getQty()).isEqualByComparingTo("100");
			}
			/* group 3 */ {
				final AvailableToPromiseResultGroup group = groups.get(2);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=2"));
				assertThat(group.getQty()).isEqualByComparingTo("50");
			}
			/* group 4 */ {
				final AvailableToPromiseResultGroup group = groups.get(3);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=3"));
				assertThat(group.getQty()).isEqualByComparingTo("30");
			}
			/* group 5 */ {
				final AvailableToPromiseResultGroup group = groups.get(4);
				assertThat(group.getStorageAttributesKey()).isSameAs(AttributesKey.OTHER);
				assertThat(group.getQty()).isEqualByComparingTo("10");
			}
		}

		@Test
		public void oneGroup_withEmptyAttributeValues_forAllMatchingRequests()
		{
			final String emptyValueAttribute = "1=";
			final ProductId testProductId = ProductId.ofRepoId(1);

			//the attributes key of the group
			final AttributesKey emptyAttributesKey = AttributesKey.ALL;

			final AvailableToPromiseResultBucket testBucket = AvailableToPromiseResultBucket.builder()
				.bpartner(BPartnerClassifier.any())
				.product(ProductClassifier.specific(1))
				.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(emptyAttributesKey))
				.build();

			testBucket.addDefaultEmptyGroupIfPossible();

			final AvailableToPromiseResultBuilder result = new AvailableToPromiseResultBuilder(ImmutableList.of(testBucket));

			//req 1
			AddToResultGroupRequest addToResultGroupRequest = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(testProductId)
					.storageAttributesKey(AttributesKey.ofString(emptyValueAttribute + "1"))
					.qty(BigDecimal.TEN)
					.date(NOW.toInstant())
					.seqNo(1)
					.build();

			result.addQtyToAllMatchingGroups(addToResultGroupRequest);

			//req 2
			addToResultGroupRequest = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(testProductId)
					.storageAttributesKey(AttributesKey.ofString(emptyValueAttribute + "2"))
					.qty(BigDecimal.TEN)
					.date(NOW.toInstant())
					.seqNo(2)
					.build();

			result.addQtyToAllMatchingGroups(addToResultGroupRequest);

			final ImmutableList<AvailableToPromiseResultGroup> groups = result.build().getResultGroups();
			assertThat(groups).hasSize(1);

			final AvailableToPromiseResultGroup group = groups.get(0);
			assertThat(group.getProductId()).isEqualTo(testProductId);
			assertThat(group.getQty()).isEqualByComparingTo("20");
			assertThat(group.getStorageAttributesKey()).isEqualTo(emptyAttributesKey);
		}
	}

	@Nested
	public class addToNewGroupIfFeasible
	{
		@Test
		public void addOneTime_expect_OneGroup()
		{
			final AvailableToPromiseResultBuilder result = AvailableToPromiseResultBuilder.createEmpty();

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(ProductId.ofRepoId(10))
					.date(NOW.minusMinutes(10).toInstant())
					.seqNo(1);

			result.addToNewGroupIfFeasible(requestBuilder
					.qty(new BigDecimal("100"))
					.storageAttributesKey(AttributesKey.ofString("111=1"))
					.build());

			final ImmutableList<AvailableToPromiseResultGroup> groups = result.build().getResultGroups();
			assertThat(groups).hasSize(1);
			/* group 1 */ {
				final AvailableToPromiseResultGroup group = groups.get(0);
				assertThat(group.getBpartner()).isSameAs(BPartnerClassifier.any());
				assertThat(group.getWarehouse()).isEqualTo(WarehouseClassifier.specific(WarehouseId.ofRepoId(1)));
				assertThat(group.getProductId().getRepoId()).isEqualTo(10);
				assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=1"));
				assertThat(group.getQty()).isEqualByComparingTo("100");
			}
		}

		@Test
		public void addThreeTimes_expect_TwoGroups()
		{
			final AvailableToPromiseResultBuilder result = AvailableToPromiseResultBuilder.createEmpty();

			final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
					.bpartner(BPartnerClassifier.any())
					.warehouseId(WarehouseId.ofRepoId(1))
					.productId(ProductId.ofRepoId(10))
					.date(NOW.minusMinutes(10).toInstant())
					.seqNo(1);

			result.addToNewGroupIfFeasible(requestBuilder
					.qty(new BigDecimal("100"))
					.storageAttributesKey(AttributesKey.ofString("111=1"))
					.build());
			result.addToNewGroupIfFeasible(requestBuilder
					.qty(new BigDecimal("40"))
					.storageAttributesKey(AttributesKey.ofString("111=2"))
					.build());
			result.addToNewGroupIfFeasible(requestBuilder
					.qty(new BigDecimal("1"))
					.storageAttributesKey(AttributesKey.ofString("111=1"))
					.build());

			{
				final ImmutableList<AvailableToPromiseResultGroup> groups = result.build().getResultGroups();
				assertThat(groups).hasSize(2);

				/* group 1 */ {
					final AvailableToPromiseResultGroup group = groups.get(0);
					assertThat(group.getBpartner()).isSameAs(BPartnerClassifier.any());
					assertThat(group.getWarehouse()).isEqualTo(WarehouseClassifier.specific(WarehouseId.ofRepoId(1)));
					assertThat(group.getProductId().getRepoId()).isEqualTo(10);
					assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=1"));
					assertThat(group.getQty()).isEqualByComparingTo("101");
				}

				/* group 2 */ {
					final AvailableToPromiseResultGroup group = groups.get(1);
					assertThat(group.getBpartner()).isSameAs(BPartnerClassifier.any());
					assertThat(group.getWarehouse()).isEqualTo(WarehouseClassifier.specific(WarehouseId.ofRepoId(1)));
					assertThat(group.getProductId().getRepoId()).isEqualTo(10);
					assertThat(group.getStorageAttributesKey()).isEqualTo(AttributesKey.ofString("111=2"));
					assertThat(group.getQty()).isEqualByComparingTo("40");
				}

			}
		}

	}
}
