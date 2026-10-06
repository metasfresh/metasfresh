package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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

	/**
	 * @return whether the product is a customer discount product of an active take-over product record of any active take-over
	 * record of the given take-over record's settings, other than {@code excludeTakeOverProductId}
	 */
	public boolean isCustomerDiscountProductOfSameSettings(
			@NonNull final ContractSettingsTakeOverId takeOverId,
			@NonNull final ProductId productId,
			@Nullable final ContractSettingsTakeOverProductId excludeTakeOverProductId)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = load(takeOverId, I_C_CompensationGroup_ContractSettings_TakeOver.class);

		final ImmutableList<ContractSettingsTakeOverId> takeOverIdsOfSettings = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, takeOver.getC_CompensationGroup_ContractSettings_ID())
				.create()
				.listIds(ContractSettingsTakeOverId::ofRepoId);

		final IQueryBuilder<I_C_CompensationGroup_ContractSettings_TakeOver_Product> queryBuilder = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_M_Product_ID, productId)
				.addInArrayFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOverIdsOfSettings);
		if (excludeTakeOverProductId != null)
		{
			queryBuilder.addNotEqualsFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_Product_ID, excludeTakeOverProductId);
		}
		return queryBuilder.create().anyMatch();
	}

	/** @return the settings' active take-overs, each with its active customer discount products */
	public ImmutableList<ContractSettingsTakeOver> getBySettingsId(@NonNull final ContractCompensationGroupSettingsId settingsId)
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
