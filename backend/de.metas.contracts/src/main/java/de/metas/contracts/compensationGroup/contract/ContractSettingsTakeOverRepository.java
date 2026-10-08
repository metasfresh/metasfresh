package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

/**
 * Repository Tables: C_CompensationGroup_ContractSettings_TakeOver, C_CompensationGroup_ContractSettings_TakeOver_Product
 * <p>
 * Repository Cluster: ContractSettingsTakeOverRepository
 */
@Repository
public class ContractSettingsTakeOverRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ContractCompensationGroupSettingsId, ImmutableList<ContractSettingsTakeOver>> takeOversBySettingsId = CCache.<ContractCompensationGroupSettingsId, ImmutableList<ContractSettingsTakeOver>>builder()
			.tableName(I_C_CompensationGroup_ContractSettings_TakeOver.Table_Name)
			.additionalTableNameToResetFor(I_C_CompensationGroup_ContractSettings_TakeOver_Product.Table_Name)
			.initialCapacity(10)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@VisibleForTesting
	public static ContractSettingsTakeOverRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ContractSettingsTakeOverRepository.class, ContractSettingsTakeOverRepository::new);
	}

	/**
	 * @return whether the product is a customer discount product of an active take-over product record of any active take-over
	 * record of the given take-over record's settings, other than {@code excludeTakeOverProductId}
	 */
	public boolean isCustomerDiscountProductOfSameSettings(
			@NonNull final ContractSettingsTakeOverId takeOverId,
			@NonNull final ProductId productId,
			@Nullable final ContractSettingsTakeOverProductId excludeTakeOverProductId)
	{
		final IQuery<I_C_CompensationGroup_ContractSettings_TakeOver> settingsOfTakeOver = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class)
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOverId)
				.create();
		final IQuery<I_C_CompensationGroup_ContractSettings_TakeOver> takeOversOfSettings = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, settingsOfTakeOver)
				.create();

		final IQueryBuilder<I_C_CompensationGroup_ContractSettings_TakeOver_Product> queryBuilder = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_M_Product_ID, productId)
				.addInSubQueryFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOversOfSettings);
		if (excludeTakeOverProductId != null)
		{
			queryBuilder.addNotEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_Product_ID, excludeTakeOverProductId);
		}
		return queryBuilder.create().anyMatch();
	}

	/** @return the settings' active take-overs, each with its active customer discount products */
	public ImmutableList<ContractSettingsTakeOver> getBySettingsId(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		return takeOversBySettingsId.getOrLoad(settingsId, this::retrieveBySettingsId);
	}

	private ImmutableList<ContractSettingsTakeOver> retrieveBySettingsId(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		final List<I_C_CompensationGroup_ContractSettings_TakeOver> takeOverRecords = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, settingsId)
				.orderBy(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID)
				.create()
				.list();
		if (takeOverRecords.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<ContractSettingsTakeOverId> takeOverIds = takeOverRecords.stream()
				.map(ContractSettingsTakeOverRepository::extractTakeOverId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ContractSettingsTakeOverId, ImmutableSet<ProductId>> customerDiscountProductIdsByTakeOverId = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOverIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						record -> ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()),
						Collectors.mapping(record -> ProductId.ofRepoId(record.getM_Product_ID()), ImmutableSet.toImmutableSet())));

		return takeOverRecords.stream()
				.map(record -> ContractSettingsTakeOver.builder()
						.id(extractTakeOverId(record))
						.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
						.ownLineProductId(ProductId.ofRepoId(record.getM_Product_ID()))
						.customerDiscountProductIds(customerDiscountProductIdsByTakeOverId.getOrDefault(extractTakeOverId(record), ImmutableSet.of()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static ContractSettingsTakeOverId extractTakeOverId(@NonNull final I_C_CompensationGroup_ContractSettings_TakeOver record)
	{
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}
}
