package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.product.ProductCategoryId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

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

class ContractTakeOverCategoryProviderTest
{
	private ContractTakeOverCategoryProvider provider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		provider = new ContractTakeOverCategoryProvider(new ContractCompensationGroupSettingsRepository());
	}

	@Test
	void returnsCategoryOfEachTakeOverRecord_unknownIdsAbsent()
	{
		final ContractSettingsTakeOverId takeOverId1 = createTakeOver(ProductCategoryId.ofRepoId(101));
		final ContractSettingsTakeOverId takeOverId2 = createTakeOver(ProductCategoryId.ofRepoId(102));
		final ContractSettingsTakeOverId unknownTakeOverId = ContractSettingsTakeOverId.ofRepoId(999999);

		assertThat(provider.getAppliesToCategories(ImmutableSet.of(takeOverId1, takeOverId2, unknownTakeOverId)))
				.containsOnly(
						entry(takeOverId1, ProductCategoryId.ofRepoId(101)),
						entry(takeOverId2, ProductCategoryId.ofRepoId(102)));
	}

	@Test
	void noIds_returnsEmpty()
	{
		assertThat(provider.getAppliesToCategories(ImmutableSet.of())).isEmpty();
	}

	private static ContractSettingsTakeOverId createTakeOver(final ProductCategoryId productCategoryId)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setM_Product_Category_ID(productCategoryId.getRepoId());
		record.setM_Product_ID(201);
		saveRecord(record);
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}
}
