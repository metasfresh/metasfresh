package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.product.ProductCategoryId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
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
	void returnsCategoryOfTakeOverRecord()
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setM_Product_Category_ID(101);
		record.setM_Product_ID(201);
		saveRecord(record);

		assertThat(provider.getAppliesToCategory(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()))
				.contains(ProductCategoryId.ofRepoId(101));
	}

	@Test
	void unknownOrNonPositiveId_returnsEmpty()
	{
		assertThat(provider.getAppliesToCategory(999999)).isEmpty();
		assertThat(provider.getAppliesToCategory(0)).isEmpty();
	}
}
