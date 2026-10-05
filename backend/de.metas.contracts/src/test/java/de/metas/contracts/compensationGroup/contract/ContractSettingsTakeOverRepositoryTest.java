package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

/** Reads of the settings' take-overs and their customer discount products. */
class ContractSettingsTakeOverRepositoryTest
{
	private static final ProductCategoryId CATEGORY_ID = ProductCategoryId.ofRepoId(101);
	private static final ProductId PRODUCT_P_ID = ProductId.ofRepoId(201); // own-line discount product
	private static final ProductId PRODUCT_Q_ID = ProductId.ofRepoId(202); // customer discount product of the take-over
	private static final ProductId PRODUCT_R_ID = ProductId.ofRepoId(203); // not a customer discount product of the take-over

	private ContractSettingsTakeOverRepository takeOverRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		takeOverRepository = new ContractSettingsTakeOverRepository();
	}

	@Test
	void getBySettingsId_returnsActiveTakeOverWithActiveCustomerDiscountProducts()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId = createTakeOver(settingsId, true);
		createCustomerDiscountProduct(takeOverId, PRODUCT_Q_ID, true);
		createCustomerDiscountProduct(takeOverId, PRODUCT_R_ID, false); // inactive -> not a customer discount product
		createTakeOver(settingsId, false); // inactive record -> not returned

		final List<ContractSettingsTakeOver> records = takeOverRepository.getBySettingsId(settingsId);

		assertThat(records).containsExactly(ContractSettingsTakeOver.builder()
				.id(takeOverId)
				.productCategoryId(CATEGORY_ID)
				.ownLineProductId(PRODUCT_P_ID)
				.customerDiscountProductIds(ImmutableSet.of(PRODUCT_Q_ID))
				.build());
	}

	@Test
	void isProductListedInSameSettings_otherTakeOverOfSameSettings()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId1 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverId takeOverId2 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverProductId listingId = createCustomerDiscountProduct(takeOverId1, PRODUCT_Q_ID, true);
		createCustomerDiscountProduct(takeOverId1, PRODUCT_R_ID, false);

		assertThat(takeOverRepository.isProductListedInSameSettings(takeOverId2, PRODUCT_Q_ID, null)).isTrue();
		assertThat(takeOverRepository.isProductListedInSameSettings(takeOverId1, PRODUCT_Q_ID, listingId)).isFalse(); // the record itself
		assertThat(takeOverRepository.isProductListedInSameSettings(takeOverId2, PRODUCT_R_ID, null)).isFalse(); // inactive listing
	}

	@Test
	void isProductListedInSameSettings_otherSettingsIgnored()
	{
		final ContractSettingsTakeOverId takeOverOfSettings1 = createTakeOver(createSettings(), true);
		final ContractSettingsTakeOverId takeOverOfSettings2 = createTakeOver(createSettings(), true);
		createCustomerDiscountProduct(takeOverOfSettings1, PRODUCT_Q_ID, true);

		assertThat(takeOverRepository.isProductListedInSameSettings(takeOverOfSettings2, PRODUCT_Q_ID, null)).isFalse();
	}

	@Test
	void getAppliesToProductCategoryIds_returnsCategoryOfEachTakeOverRecord_unknownIdsAbsent()
	{
		final ContractSettingsTakeOverId takeOverId1 = createTakeOver(createSettings(), ProductCategoryId.ofRepoId(101));
		final ContractSettingsTakeOverId takeOverId2 = createTakeOver(createSettings(), ProductCategoryId.ofRepoId(102));
		final ContractSettingsTakeOverId unknownTakeOverId = ContractSettingsTakeOverId.ofRepoId(999999);

		assertThat(takeOverRepository.getAppliesToProductCategoryIds(ImmutableSet.of(takeOverId1, takeOverId2, unknownTakeOverId)))
				.containsOnly(
						entry(takeOverId1, ProductCategoryId.ofRepoId(101)),
						entry(takeOverId2, ProductCategoryId.ofRepoId(102)));
	}

	@Test
	void getAppliesToProductCategoryIds_noIds_returnsEmpty()
	{
		assertThat(takeOverRepository.getAppliesToProductCategoryIds(ImmutableSet.of())).isEmpty();
	}

	private static ContractCompensationGroupSettingsId createSettings()
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(settings);
		return ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID());
	}

	private static ContractSettingsTakeOverId createTakeOver(final ContractCompensationGroupSettingsId settingsId, final boolean active)
	{
		return createTakeOver(settingsId, CATEGORY_ID, active);
	}

	private static ContractSettingsTakeOverId createTakeOver(final ContractCompensationGroupSettingsId settingsId, final ProductCategoryId productCategoryId)
	{
		return createTakeOver(settingsId, productCategoryId, true);
	}

	private static ContractSettingsTakeOverId createTakeOver(
			final ContractCompensationGroupSettingsId settingsId,
			final ProductCategoryId productCategoryId,
			final boolean active)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		record.setM_Product_Category_ID(productCategoryId.getRepoId());
		record.setM_Product_ID(PRODUCT_P_ID.getRepoId());
		record.setIsActive(active);
		saveRecord(record);
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}

	private static ContractSettingsTakeOverProductId createCustomerDiscountProduct(final ContractSettingsTakeOverId takeOverId, final ProductId productId, final boolean active)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setIsActive(active);
		saveRecord(record);
		return ContractSettingsTakeOverProductId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_Product_ID());
	}
}
