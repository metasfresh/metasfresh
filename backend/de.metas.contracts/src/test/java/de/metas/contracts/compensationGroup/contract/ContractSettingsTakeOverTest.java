package de.metas.contracts.compensationGroup.contract;

import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class ContractSettingsTakeOverTest
{
	@Test
	void percentagePrecision_defaultsToTwo_whenNotSetOnBuilder()
	{
		final ContractSettingsTakeOver takeOver = ContractSettingsTakeOver.builder()
				.id(ContractSettingsTakeOverId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(101))
				.ownLineProductId(ProductId.ofRepoId(201))
				.build();

		assertThat(takeOver.getPercentagePrecision()).isEqualTo(2);
	}

	@Test
	void percentagePrecision_explicitValueWins()
	{
		final ContractSettingsTakeOver takeOver = ContractSettingsTakeOver.builder()
				.id(ContractSettingsTakeOverId.ofRepoId(1))
				.productCategoryId(ProductCategoryId.ofRepoId(101))
				.ownLineProductId(ProductId.ofRepoId(201))
				.percentagePrecision(0)
				.build();

		assertThat(takeOver.getPercentagePrecision()).isEqualTo(0);
	}
}
