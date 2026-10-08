package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CCacheConfig;
import de.metas.cache.CCacheStatsPredicate;
import de.metas.cache.CacheMgt;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
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

/** Reads of the settings' take-overs and their customer discount products. */
class ContractSettingsTakeOverRepositoryTest
{
	private static final ProductCategoryId CATEGORY_ID = ProductCategoryId.ofRepoId(101);
	private static final ProductId PRODUCT_P_ID = ProductId.ofRepoId(201); // own-line discount product
	private static final ProductId PRODUCT_Q_ID = ProductId.ofRepoId(202); // customer discount product of the take-over
	private static final ProductId PRODUCT_R_ID = ProductId.ofRepoId(203); // not a customer discount product of the take-over
	private static final ProductCategoryId OTHER_CATEGORY_ID = ProductCategoryId.ofRepoId(102);

	private IQueryBL queryBLSpy;
	private ContractSettingsTakeOverRepository takeOverRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy); // before the repository picks up IQueryBL
		takeOverRepository = ContractSettingsTakeOverRepository.newInstanceForUnitTesting();
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
	void getBySettingsId_secondReadOfTheSameSettings_runsNoQuery()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		createCustomerDiscountProduct(createTakeOver(settingsId, true), PRODUCT_Q_ID, true);
		final List<ContractSettingsTakeOver> firstRead = takeOverRepository.getBySettingsId(settingsId);
		Mockito.clearInvocations(queryBLSpy);

		final List<ContractSettingsTakeOver> secondRead = takeOverRepository.getBySettingsId(settingsId);

		assertThat(secondRead).isEqualTo(firstRead);
		Mockito.verify(queryBLSpy, Mockito.never()).createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		Mockito.verify(queryBLSpy, Mockito.never()).createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
	}

	@Test
	void getBySettingsId_savingATakeOverOrACustomerDiscountProduct_isSeenByTheNextRead()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId = createTakeOver(settingsId, true);
		createCustomerDiscountProduct(takeOverId, PRODUCT_Q_ID, true);
		assertThat(takeOverRepository.getBySettingsId(settingsId)).extracting(ContractSettingsTakeOver::getProductCategoryId).containsExactly(CATEGORY_ID);

		final I_C_CompensationGroup_ContractSettings_TakeOver takeOverRecord = load(takeOverId, I_C_CompensationGroup_ContractSettings_TakeOver.class);
		takeOverRecord.setM_Product_Category_ID(OTHER_CATEGORY_ID.getRepoId());
		saveRecord(takeOverRecord);
		assertThat(takeOverRepository.getBySettingsId(settingsId)).extracting(ContractSettingsTakeOver::getProductCategoryId).containsExactly(OTHER_CATEGORY_ID);

		createCustomerDiscountProduct(takeOverId, PRODUCT_R_ID, true);
		assertThat(takeOverRepository.getBySettingsId(settingsId)).extracting(ContractSettingsTakeOver::getCustomerDiscountProductIds).containsExactly(ImmutableSet.of(PRODUCT_Q_ID, PRODUCT_R_ID));
	}

	@Test
	void isCustomerDiscountProductOfSameSettings_otherTakeOverOfSameSettings()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId1 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverId takeOverId2 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverProductId takeOverProductId = createCustomerDiscountProduct(takeOverId1, PRODUCT_Q_ID, true);
		createCustomerDiscountProduct(takeOverId1, PRODUCT_R_ID, false);

		assertThat(takeOverRepository.isCustomerDiscountProductOfSameSettings(takeOverId2, PRODUCT_Q_ID, null)).isTrue();
		assertThat(takeOverRepository.isCustomerDiscountProductOfSameSettings(takeOverId1, PRODUCT_Q_ID, takeOverProductId)).isFalse(); // the record itself
		assertThat(takeOverRepository.isCustomerDiscountProductOfSameSettings(takeOverId2, PRODUCT_R_ID, null)).isFalse(); // inactive take-over product record
	}

	@Test
	void isCustomerDiscountProductOfSameSettings_otherSettingsIgnored()
	{
		final ContractSettingsTakeOverId takeOverOfSettings1 = createTakeOver(createSettings(), true);
		final ContractSettingsTakeOverId takeOverOfSettings2 = createTakeOver(createSettings(), true);
		createCustomerDiscountProduct(takeOverOfSettings1, PRODUCT_Q_ID, true);

		assertThat(takeOverRepository.isCustomerDiscountProductOfSameSettings(takeOverOfSettings2, PRODUCT_Q_ID, null)).isFalse();
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

	private static ContractSettingsTakeOverId createTakeOver(final ContractCompensationGroupSettingsId settingsId, final boolean isActive)
	{
		return createTakeOver(settingsId, CATEGORY_ID, isActive);
	}

	private static ContractSettingsTakeOverId createTakeOver(
			final ContractCompensationGroupSettingsId settingsId,
			final ProductCategoryId productCategoryId,
			final boolean isActive)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		record.setM_Product_Category_ID(productCategoryId.getRepoId());
		record.setM_Product_ID(PRODUCT_P_ID.getRepoId());
		record.setIsActive(isActive);
		saveRecord(record);
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}

	private static ContractSettingsTakeOverProductId createCustomerDiscountProduct(final ContractSettingsTakeOverId takeOverId, final ProductId productId, final boolean isActive)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setIsActive(isActive);
		saveRecord(record);
		return ContractSettingsTakeOverProductId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_Product_ID());
	}

	@Test
	void cache_isBoundedLRU()
	{
		// repository created in beforeEach

		assertThat(CacheMgt.get().streamStats(CCacheStatsPredicate.builder().cacheNameContains(I_C_CompensationGroup_ContractSettings_TakeOver.Table_Name).build()).filter(stats -> stats.getName().equals(I_C_CompensationGroup_ContractSettings_TakeOver.Table_Name)))
				.isNotEmpty()
				.allSatisfy(stats -> assertThat(stats.getConfig())
						.returns(CCache.CacheMapType.LRU, CCacheConfig::getCacheMapType)
						.returns(100, CCacheConfig::getMaximumSize));
	}
}
