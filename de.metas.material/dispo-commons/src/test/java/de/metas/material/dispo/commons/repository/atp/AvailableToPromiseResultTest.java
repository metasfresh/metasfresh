package de.metas.material.dispo.commons.repository.atp;

import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.atp.AddToResultGroupRequest.AddToResultGroupRequestBuilder;
import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.collections.CollectionUtils;

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
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofAttributeValueIds(1, 2);
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY_OTHER = AttributesKey.ofAttributeValueIds(1, 2, 3);

	private static final LocalDateTime NOW = LocalDateTime.now();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private I_MD_Candidate_ATP_QueryResult createStockRecord(int warehouseId)
	{
		final I_MD_Candidate_ATP_QueryResult viewRecord = newInstance(I_MD_Candidate_ATP_QueryResult.class);
		viewRecord.setM_Product_ID(PRODUCT_ID);
		viewRecord.setM_Warehouse_ID(warehouseId);
		viewRecord.setDateProjected(new Timestamp(BEFORE_NOW.getTime()));
		viewRecord.setStorageAttributesKey(STORAGE_ATTRIBUTES_KEY.getAsString());
		viewRecord.setQty(BigDecimal.TEN);
		viewRecord.setSeqNo(1);
		save(viewRecord);

		return viewRecord;
	}

	@Test
	public void createEmptyResultForQuery()
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.of(AvailableToPromiseQuery.builder()
						.productId(20)
						.productId(10)
						.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
						.storageAttributesKey(STORAGE_ATTRIBUTES_KEY_OTHER)
						.date(TimeUtil.asLocalDateTime(NOW))
						.build());

		final List<AvailableToPromiseResultGroup> emptyResults = AvailableToPromiseResult.createEmptyWithPredefinedBuckets(query).getResultGroups();

		assertThat(emptyResults).hasSize(4);

		final AvailableToPromiseResultGroup firstResult = emptyResults.get(0);
		assertThat(firstResult.getProductId()).isEqualTo(20);
		assertThat(firstResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(firstResult.getQty()).isEqualByComparingTo("0");

		final AvailableToPromiseResultGroup secondResult = emptyResults.get(1);
		assertThat(secondResult.getProductId()).isEqualTo(20);
		assertThat(secondResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY_OTHER);
		assertThat(secondResult.getQty()).isEqualByComparingTo("0");

		final AvailableToPromiseResultGroup thirdResult = emptyResults.get(2);
		assertThat(thirdResult.getProductId()).isEqualTo(10);
		assertThat(thirdResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(thirdResult.getQty()).isEqualByComparingTo("0");

		final AvailableToPromiseResultGroup fourthResult = emptyResults.get(3);
		assertThat(fourthResult.getProductId()).isEqualTo(10);
		assertThat(fourthResult.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY_OTHER);
		assertThat(fourthResult.getQty()).isEqualByComparingTo("0");
	}

	@Test
	public void createEmptyResultForQuery_NoStrorageAttributesKey()
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.of(AvailableToPromiseQuery.builder()
				.productId(10)
				.date(TimeUtil.asLocalDateTime(NOW))
				.build());

		final List<AvailableToPromiseResultGroup> emptyResults = AvailableToPromiseResult.createEmptyWithPredefinedBuckets(query).getResultGroups();

		assertThat(emptyResults).hasSize(1);

		final AvailableToPromiseResultGroup firstResult = emptyResults.get(0);
		assertThat(firstResult.getProductId()).isEqualTo(10);
		assertThat(firstResult.getStorageAttributesKey()).isSameAs(AttributesKey.ALL);
		assertThat(firstResult.getQty()).isEqualByComparingTo("0");

	}

	@Test
	public void addQtyToMatchedGroups()
	{
		final AvailableToPromiseResultGroup emptyResult1 = AvailableToPromiseResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1))
				.warehouseId(100)
				.bpartnerId(200)
				.build();
		final AvailableToPromiseResultGroup emptyResult2 = AvailableToPromiseResultGroup.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(2))
				.warehouseId(100)
				.bpartnerId(200)
				.build();
		final AvailableToPromiseResult stockResult = new AvailableToPromiseResult(ImmutableList.of(emptyResult1, emptyResult2));

		final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
				.productId(PRODUCT_ID)
				.warehouseId(100)
				.bpartnerId(200)
				.qty(BigDecimal.ONE)
				.seqNo(1)
				.date(NOW);

		stockResult.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(1, 2)).build());
		stockResult.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(1, 2)).build());
		stockResult.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(2)).build());

		final List<AvailableToPromiseResultGroup> resultGroups = stockResult.getResultGroups();
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
		final AvailableToPromiseResultGroup group = AvailableToPromiseResultGroup.builder()
				.productId(PRODUCT_ID)
				.warehouseId(100)
				.bpartnerId(200)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1))
				.build();

		final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
				.productId(PRODUCT_ID)
				.warehouseId(100)
				.bpartnerId(200)
				.qty(BigDecimal.ONE)
				.seqNo(1)
				.date(NOW);

		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(1)).build())).isTrue();
		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(11)).build())).isFalse();

		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(STORAGE_ATTRIBUTES_KEY).build()))
				.as("Should match because the AvailableToPromiseResultGroup's storageAttributesKey <%s> is included in the given storageAttributesKeyToMatch <%s>",
						group.getStorageAttributesKey(),
						STORAGE_ATTRIBUTES_KEY)
				.isTrue();
	}

	@Test
	public void isMatchting_with_two_storageAttributesKeys()
	{
		final AvailableToPromiseResultGroup group = AvailableToPromiseResultGroup.builder()
				.productId(PRODUCT_ID)
				.warehouseId(100)
				.bpartnerId(200)
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(1, 3))
				.build();

		final AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
				.productId(PRODUCT_ID)
				.warehouseId(100)
				.bpartnerId(200)
				.qty(BigDecimal.ONE)
				.seqNo(1)
				.date(NOW);

		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(1, 3)).build())).isTrue();
		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(1)).build())).isFalse();

		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(AttributesKey.ofAttributeValueIds(10, 1, 3)).build())).isTrue();

		final AttributesKey keyWithOtherElementInTheMiddle = AttributesKey.ofAttributeValueIds(1, 2, 3);
		assertThat(group.isMatchting(requestBuilder.storageAttributesKey(keyWithOtherElementInTheMiddle).build()))
				.as("Shall match because the elements of the AvailableToPromiseResultGroup's storageAttributesKey <%s> contain every element of the given storageAttributesKeyToMatch <%s>",
						group.getStorageAttributesKey(), keyWithOtherElementInTheMiddle)
				.isTrue();
	}

	@Test
	public void addQtyToAllMatchingGroups()
	{
		final I_MD_Candidate_ATP_QueryResult stockRecord = createStockRecord(WAREHOUSE_ID);

		final AvailableToPromiseResult result = new AvailableToPromiseResult(ImmutableList.of(
				AvailableToPromiseResultGroup.builder()
						.productId(PRODUCT_ID)
						.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
						.bpartnerId(AvailableToPromiseQuery.BPARTNER_ID_ANY)
						.build()));

		final AddToResultGroupRequest resultAddRequest = AvailableToPromiseRepository.createAddToResultGroupRequest(stockRecord);
		result.addQtyToAllMatchingGroups(resultAddRequest);

		final AvailableToPromiseResultGroup singleElement = CollectionUtils.singleElement(result.getResultGroups());
		assertThat(singleElement.getProductId()).isEqualTo(stockRecord.getM_Product_ID());
		assertThat(singleElement.getQty()).isEqualByComparingTo(stockRecord.getQty());
		assertThat(singleElement.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
	}

}
