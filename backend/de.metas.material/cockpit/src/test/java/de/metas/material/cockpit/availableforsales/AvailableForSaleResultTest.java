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

import com.google.common.collect.ImmutableList;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.material.commons.attributes.clasifiers.ProductClassifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class AvailableForSaleResultTest
{
	private static final String delim = AttributesKey.ATTRIBUTES_KEY_DELIMITER;

	@Test
	public void two_buckets()
	{
		final AvailableForSaleResultBuilder resultBuilder;
		{
			final AvailableForSaleResultBucket emptyBucket1 = AvailableForSaleResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("1")))
					.build();

			final AvailableForSaleResultBucket emptyBucket2 = AvailableForSaleResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("2")))
					.build();

			resultBuilder = new AvailableForSaleResultBuilder(ImmutableList.of(emptyBucket1, emptyBucket2));
		}

		final AddToResultGroupRequest.AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
				.productId(ProductId.ofRepoId(100001))
				.qtyOnHandStock(BigDecimal.valueOf(2))
				.qtyToBeShipped(BigDecimal.valueOf(1))
				.queryNo(0);

		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "2")).build());
		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1" + delim + "2")).build());
		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("2")).build());

		final List<AvailableForSalesLookupBucketResult> groups = resultBuilder.build().getAvailableForSalesResults();
		assertThat(groups).hasSize(2);

		assertThat(groups.get(0).getProductId().getRepoId()).isEqualTo(100001);
		assertThat(groups.get(0).getStorageAttributesKey().getAsString()).isEqualTo(AttributesKey.ofString("1").getAsString());
		assertThat(groups.get(0).getQuantities().getQtyOnHandStock()).isEqualByComparingTo("2");
		assertThat(groups.get(0).getQuantities().getQtyToBeShipped()).isEqualByComparingTo("1");

		assertThat(groups.get(1).getProductId().getRepoId()).isEqualTo(100001);
		assertThat(groups.get(1).getStorageAttributesKey().getAsString()).isEqualTo(AttributesKey.ofString("2").getAsString());
		assertThat(groups.get(1).getQuantities().getQtyOnHandStock()).isEqualByComparingTo("4");
		assertThat(groups.get(1).getQuantities().getQtyToBeShipped()).isEqualByComparingTo("2");
	}

	@Test
	public void three_buckets()
	{
		final AvailableForSaleResultBuilder resultBuilder;
		{
			final AvailableForSaleResultBucket emptyBucket1 = AvailableForSaleResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKeyPatternsUtil.parseCommaSeparatedString("1*").stream().findFirst().get()))
					.build();

			final AvailableForSaleResultBucket emptyBucket2 = AvailableForSaleResultBucket.builder()
					.product(ProductClassifier.specific(100001))
					.storageAttributesKeyMatcher(AttributesKeyPatternsUtil.matching(AttributesKey.ofString("2")))
					.build();

			resultBuilder = new AvailableForSaleResultBuilder(ImmutableList.of(emptyBucket1, emptyBucket2));
		}

		final BigDecimal TWO = BigDecimal.valueOf(2);
		final AddToResultGroupRequest.AddToResultGroupRequestBuilder requestBuilder = AddToResultGroupRequest.builder()
				.productId(ProductId.ofRepoId(100001))
				.qtyOnHandStock(TWO)
				.qtyToBeShipped(BigDecimal.valueOf(1))
				.queryNo(0);

		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1=1")).build());
		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("1=11" + delim + "2")).build());
		resultBuilder.addQtyToAllMatchingGroups(requestBuilder.storageAttributesKey(AttributesKey.ofString("2")).build());

		final List<AvailableForSalesLookupBucketResult> groups = resultBuilder.build().getAvailableForSalesResults();
		assertThat(groups).hasSize(3);
		assertThat(groups).extracting("storageAttributesKey", "quantities.qtyOnHandStock", "quantities.qtyToBeShipped")
				.containsExactlyInAnyOrder(tuple(AttributesKey.ofString("1=1"), TWO, BigDecimal.ONE),
						tuple(AttributesKey.ofString("1=11"), TWO, BigDecimal.ONE),
						tuple(AttributesKey.ofString("2"), BigDecimal.valueOf(4), TWO));
	}

}
