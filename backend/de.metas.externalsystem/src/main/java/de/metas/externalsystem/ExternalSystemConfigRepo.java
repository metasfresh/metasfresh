/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem;

import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.pricing.PriceListId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExternalSystemConfigRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ExternalSystemParentConfig getById(final @NonNull IExternalSystemChildConfigId id)
	{
		switch (id.getType())
		{
			case Alberta:
				return getById(ExternalSystemAlbertaConfigId.cast(id));
			case Shopware6:
				return getById(ExternalSystemShopware6ConfigId.cast(id));
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", id.getType());
		}
	}

	@NonNull
	public Optional<ExternalSystemParentConfig> getByTypeAndValue(@NonNull final ExternalSystemType type, @NonNull final String value)
	{
		switch (type)
		{
			case Alberta:
				return getAlbertaConfigByValue(value)
						.map(this::getExternalSystemParentConfig);

			case Shopware6:
				return getShopware6ConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", type);
		}
	}

	public Optional<IExternalSystemChildConfig> getChildByParentIdAndType(@NonNull final ExternalSystemParentConfigId id, @NonNull final ExternalSystemType externalSystemType)
	{
		switch (externalSystemType)
		{
			case Alberta:
				return getAlbertaConfigByParentId(id);
			case Shopware6:
				return getShopware6ConfigByParentId(id);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Alberta> getAlbertaConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Alberta.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Alberta.class);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Shopware6> getShopware6ConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Shopware6.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getAlbertaConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Alberta.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Alberta.class)
				.map(ex -> buildExternalSystemAlbertaConfig(ex, id));
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getShopware6ConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Shopware6.class)
				.map(ex -> buildExternalSystemShopware6Config(ex, id));
	}

	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemAlbertaConfigId id)
	{
		final I_ExternalSystem_Config_Alberta config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Alberta.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Alberta config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemAlbertaConfig child = buildExternalSystemAlbertaConfig(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemAlbertaConfig buildExternalSystemAlbertaConfig(final @NonNull I_ExternalSystem_Config_Alberta config,
			@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return ExternalSystemAlbertaConfig.builder()
				.id(ExternalSystemAlbertaConfigId.ofRepoId(config.getExternalSystem_Config_Alberta_ID()))
				.parentId(parentConfigId)
				.apiKey(config.getApiKey())
				.baseUrl(config.getBaseURL())
				.value(config.getExternalSystemValue())
				.tenant(config.getTenant())
				.pharmacyPriceListId(PriceListId.ofRepoIdOrNull(config.getPharmacy_PriceList_ID()))
				.build();
	}

	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemShopware6ConfigId id)
	{
		final I_ExternalSystem_Config_Shopware6 config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Shopware6.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Shopware6 config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemShopware6Config child = buildExternalSystemShopware6Config(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemShopware6Config buildExternalSystemShopware6Config(final @NonNull I_ExternalSystem_Config_Shopware6 config,
			@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return ExternalSystemShopware6Config.builder()
				.id(ExternalSystemShopware6ConfigId.ofRepoId(config.getExternalSystem_Config_Shopware6_ID()))
				.parentId(parentConfigId)
				.baseUrl(config.getBaseURL())
				.clientSecret(config.getClient_Secret())
				.clientId(config.getClient_Id())
				.bPartnerIdJSONPath(config.getJSONPathConstantBPartnerID())
				.bPartnerLocationIdJSONPath(config.getJSONPathConstantBPartnerLocationID())
				.build();
	}

	private ExternalSystemParentConfig.ExternalSystemParentConfigBuilder getById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);

		return ExternalSystemParentConfig.builder()
				.type(ExternalSystemType.ofCode(externalSystemConfigRecord.getType()))
				.id(ExternalSystemParentConfigId.ofRepoId(externalSystemConfigRecord.getExternalSystem_Config_ID()))
				.camelUrl(externalSystemConfigRecord.getCamelURL())
				.name(externalSystemConfigRecord.getName());
	}

	public String getParentTypeById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);

		return externalSystemConfigRecord.getType();
	}
}
