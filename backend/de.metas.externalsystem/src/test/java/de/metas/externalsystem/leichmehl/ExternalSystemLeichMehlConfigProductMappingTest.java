/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.leichmehl;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExternalSystemLeichMehlConfigProductMappingTest
{
	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigMatched_ByProductId()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productId(ProductId.ofRepoId(1))
				.build();

		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productId(ProductId.ofRepoId(1))
				.build();

		//when
		final boolean matchingQuery = productMapping.isMatchingQuery(query);

		//then
		assertThat(matchingQuery).isTrue();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigMatched_ByProductCategoryId()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productId(ProductId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isTrue();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigMatched_ByBPartnerId()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productId(ProductId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isTrue();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigMatched_all()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.productId(ProductId.ofRepoId(2))
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.productId(ProductId.ofRepoId(2))
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isTrue();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigMatched()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.productId(ProductId.ofRepoId(2))
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isTrue();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigNotMatched_NoBPartnerId()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productId(ProductId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productId(ProductId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.bPartnerId(BPartnerId.ofRepoId(3))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isFalse();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigNotMatched_NoProductId()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productId(ProductId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isFalse();
	}

	@Test
	public void givenConfig_whenIsMatchingQuery_ThenConfigNotMatched_DifferentProductCategory()
	{
		//given
		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.build();

		//when
		final ExternalSystemLeichMehlConfigId configId = ExternalSystemLeichMehlConfigId.ofRepoId(1);
		final ExternalSystemLeichMehlConfigProductMappingId id = ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(configId, 2);

		final ExternalSystemLeichMehlConfigProductMapping productMapping = ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(id)
				.seqNo(10)
				.pluFile("pluFile")
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		//then
		final boolean matchingQuery = productMapping.isMatchingQuery(query);
		assertThat(matchingQuery).isFalse();
	}
}
