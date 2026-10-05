package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_DocType;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.document.DocTypeId;
import de.metas.order.compensationGroup.GroupTemplateId;
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
import java.util.Set;
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
 * Repository Tables: C_CompensationGroup_ContractSettings, C_CompensationGroup_ContractSettings_DocType, C_CompensationGroup_ContractSettings_TakeOver, C_CompensationGroup_ContractSettings_TakeOver_Product, C_Flatrate_Conditions
 * <p>
 * Repository Cluster: sole owner of these tables within this scope (no other class declares a
 * {@code Repository Tables:} line for them; {@code C_Flatrate_Conditions} is only read here, via
 * its plain {@code C_CompensationGroup_ContractSettings_ID} column).
 * <p>
 * Loads a {@code C_CompensationGroup_ContractSettings} record and resolves the settings id a
 * {@code C_Flatrate_Conditions} record points to.
 */
@Repository
public class ContractCompensationGroupSettingsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ContractCompensationGroupSettings getBySettingsId(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		final I_C_CompensationGroup_ContractSettings settingsRecord = load(settingsId, I_C_CompensationGroup_ContractSettings.class);

		final GroupTemplateId schemaId = GroupTemplateId.ofRepoId(settingsRecord.getC_CompensationGroup_Schema_ID());

		final ImmutableSet<DocTypeId> docTypeIds = retrieveDocTypeIds(settingsId);

		return ContractCompensationGroupSettings.builder()
				.settingsId(settingsId)
				.schemaId(schemaId)
				.docTypeIds(docTypeIds)
				.build();
	}

	/** @return the settings id that {@code conditionsId}'s {@code C_CompensationGroup_ContractSettings_ID} points to, or {@code null} when unset */
	@Nullable
	public ContractCompensationGroupSettingsId getSettingsIdByConditionsId(@NonNull final ConditionsId conditionsId)
	{
		final I_C_Flatrate_Conditions conditions = load(conditionsId, I_C_Flatrate_Conditions.class);
		return ContractCompensationGroupSettingsId.ofRepoIdOrNull(conditions.getC_CompensationGroup_ContractSettings_ID());
	}

	/**
	 * @return the compensation-group settings {@code conditionsId} points to, or {@code null} when the conditions
	 * carry no compensation-group settings. The single resolution path from a term's/order's conditions to its
	 * settings — combines {@link #getSettingsIdByConditionsId} and {@link #getBySettingsId}.
	 */
	@Nullable
	public ContractCompensationGroupSettings getByConditionsId(@NonNull final ConditionsId conditionsId)
	{
		final ContractCompensationGroupSettingsId settingsId = getSettingsIdByConditionsId(conditionsId);
		return settingsId != null ? getBySettingsId(settingsId) : null;
	}

	/** @return the product category of each given take-over record (active or not, so an own line keeps its category); a record that does not exist or has no category is absent */
	public ImmutableMap<ContractSettingsTakeOverId, ProductCategoryId> getTakeOverProductCategoryIds(@NonNull final Set<ContractSettingsTakeOverId> takeOverIds)
	{
		if (takeOverIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver.class)
				.addInArrayFilter(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOverIds)
				.create()
				.stream()
				.filter(record -> ProductCategoryId.ofRepoIdOrNull(record.getM_Product_Category_ID()) != null)
				.collect(ImmutableMap.toImmutableMap(
						record -> ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()),
						record -> ProductCategoryId.ofRepoId(record.getM_Product_Category_ID())));
	}

	/**
	 * @return whether the product is listed on an active take-over product record of any active take-over record of the given
	 * take-over record's settings, other than {@code excludeTakeOverProductId}
	 */
	public boolean isProductListedInSameSettings(
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

	/** @return the settings' active take-over records, each with its active listed customer products */
	public List<TakeOverRecord> getTakeOverRecords(@NonNull final ContractCompensationGroupSettingsId settingsId)
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
				.map(ContractCompensationGroupSettingsRepository::extractTakeOverId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ContractSettingsTakeOverId, ImmutableSet<ProductId>> listedProductIdsByTakeOverId = queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, takeOverIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						record -> ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()),
						Collectors.mapping(record -> ProductId.ofRepoId(record.getM_Product_ID()), ImmutableSet.toImmutableSet())));

		return takeOverRecords.stream()
				.map(record -> TakeOverRecord.builder()
						.takeOverId(extractTakeOverId(record))
						.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
						.ownLineProductId(ProductId.ofRepoId(record.getM_Product_ID()))
						.listedCustomerProductIds(listedProductIdsByTakeOverId.getOrDefault(extractTakeOverId(record), ImmutableSet.of()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static ContractSettingsTakeOverId extractTakeOverId(@NonNull final I_C_CompensationGroup_ContractSettings_TakeOver record)
	{
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}

	private ImmutableSet<DocTypeId> retrieveDocTypeIds(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		return queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, settingsId)
				.create()
				.listDistinct(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_DocType_ID, Integer.class)
				.stream()
				.map(DocTypeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
