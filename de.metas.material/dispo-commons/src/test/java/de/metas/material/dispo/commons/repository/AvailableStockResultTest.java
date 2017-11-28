package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.AvailableStockResult.ResultGroup;
import de.metas.material.event.commons.AttributesKey;

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

public class AvailableStockResultTest
{
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofAttributeValueIds(1, 2);
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY_OTHER = AttributesKey.ofAttributeValueIds(1, 2, 3);

	@Test
	public void createEmptyResultForQuery()
	{
		final MaterialQuery query = MaterialQuery.builder()
				.productId(20)
				.productId(10)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY_OTHER)
				.date(NOW)
				.build();

		final List<ResultGroup> emptyResults = AvailableStockResult.createEmptyForQuery(query).getResultGroups();

		assertThat(emptyResults).hasSize(4);

		final ResultGroup firstResult = emptyResults.get(0);
		assertThat(firstResult.getProductId()).isEqualTo(20);
		assertThat(firstResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(firstResult.getQty()).isEqualByComparingTo("0");

		final ResultGroup secondResult = emptyResults.get(1);
		assertThat(secondResult.getProductId()).isEqualTo(20);
		assertThat(secondResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY_OTHER);
		assertThat(secondResult.getQty()).isEqualByComparingTo("0");

		final ResultGroup thirdResult = emptyResults.get(2);
		assertThat(thirdResult.getProductId()).isEqualTo(10);
		assertThat(thirdResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(thirdResult.getQty()).isEqualByComparingTo("0");

		final ResultGroup fourthResult = emptyResults.get(3);
		assertThat(fourthResult.getProductId()).isEqualTo(10);
		assertThat(fourthResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY_OTHER);
		assertThat(fourthResult.getQty()).isEqualByComparingTo("0");
	}

	@Test
	public void addQtyToMatchedGroups()
	{
		final ResultGroup emptyResult1 = ResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1))
				.build();
		final ResultGroup emptyResult2 = ResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(2))
				.build();
		final AvailableStockResult availableStockResult = new AvailableStockResult(ImmutableList.of(emptyResult1, emptyResult2));

		availableStockResult.addQtyToMatchedGroups(BigDecimal.ONE, PRODUCT_ID, STORAGE_ATTRIBUTES_KEY);
		availableStockResult.addQtyToMatchedGroups(BigDecimal.ONE, PRODUCT_ID, STORAGE_ATTRIBUTES_KEY);
		availableStockResult.addQtyToMatchedGroups(BigDecimal.ONE, PRODUCT_ID, AttributesKey.ofAttributeValueIds(2));

		final List<ResultGroup> resultGroups = availableStockResult.getResultGroups();
		assertThat(resultGroups).hasSize(2);

		assertThat(resultGroups.get(0).getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(resultGroups.get(0).getStorageAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(1));
		assertThat(resultGroups.get(0).getQty()).isEqualByComparingTo("2");

		assertThat(resultGroups.get(1).getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(resultGroups.get(1).getStorageAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(2));
		assertThat(resultGroups.get(1).getQty()).isEqualByComparingTo("3");
	}

	@Test
	public void ResultGroup_matches_with_single_storageAttributesKey()
	{
		final ResultGroup group = ResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1))
				.build();

		assertThat(group.matches(PRODUCT_ID, AttributesKey.ofAttributeValueIds(1))).isTrue();
		assertThat(group.matches(PRODUCT_ID, AttributesKey.ofAttributeValueIds(11))).isFalse();

		assertThat(group.matches(PRODUCT_ID, STORAGE_ATTRIBUTES_KEY))
				.as("Should match because the ResultGroup's storageAttributesKey <%s> is included in the given storageAttributesKeyToMatch <%s>",
						group.getStorageAttributesKey(), STORAGE_ATTRIBUTES_KEY)
				.isTrue();
	}

	@Test
	public void ResultGroup_matches_with_two_storageAttributesKeys()
	{
		final ResultGroup group = ResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1, 3))
				.build();

		assertThat(group.matches(PRODUCT_ID, AttributesKey.ofAttributeValueIds(1, 3))).isTrue();
		assertThat(group.matches(PRODUCT_ID, AttributesKey.ofAttributeValueIds(1))).isFalse();

		assertThat(group.matches(PRODUCT_ID, AttributesKey.ofAttributeValueIds(10, 1, 3))).isTrue();

		final AttributesKey keyWithOtherElementInTheMiddle = AttributesKey.ofAttributeValueIds(1, 2, 3);
		assertThat(group.matches(PRODUCT_ID, keyWithOtherElementInTheMiddle))
				.as("Shall match because the elements of the ResultGroup's storageAttributesKey <%s> contain every element of the given storageAttributesKeyToMatch <%s>",
						group.getStorageAttributesKey(), keyWithOtherElementInTheMiddle)
				.isTrue();
	}
}
