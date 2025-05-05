/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.CCache;
import de.metas.handlingunits.model.I_QRCode_Attribute_Config;
import de.metas.handlingunits.model.I_QRCode_Configuration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfiguration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfigurationId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class QRCodeConfigurationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, QRCodeConfigurationMap> cache = CCache.<Integer, QRCodeConfigurationMap>builder()
			.tableName(I_QRCode_Configuration.Table_Name)
			.additionalTableNameToResetFor(I_QRCode_Attribute_Config.Table_Name)
			.build();

	@NonNull
	public QRCodeConfiguration getById(@NonNull final QRCodeConfigurationId id)
	{
		return getConfigurationMap().getById(id);
	}

	@NonNull
	public ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> getByIds(@NonNull final ImmutableSet<QRCodeConfigurationId> ids)
	{
		return getConfigurationMap().getByIds(ids);
	}

	public boolean isAtLeastOneActiveConfig()
	{
		return !getConfigurationMap().isEmpty();
	}

	@NonNull
	private QRCodeConfigurationMap getConfigurationMap()
	{
		return cache.getOrLoad(0, this::load);
	}

	@NonNull
	private QRCodeConfigurationMap load()
	{
		final ImmutableList<I_QRCode_Configuration> configurationList = queryBL.createQueryBuilder(I_QRCode_Configuration.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_QRCode_Configuration.class);

		return QRCodeConfigurationMap.ofList(toQRCodeConfiguration(configurationList, getAttributeIds(configurationList)));
	}

	@NonNull
	private ImmutableSetMultimap<QRCodeConfigurationId, AttributeId> getAttributeIds(@NonNull final Collection<I_QRCode_Configuration> qrCodeConfigurationList)
	{
		final ImmutableSet<QRCodeConfigurationId> codeConfigurationIds = qrCodeConfigurationList.stream()
				.map(I_QRCode_Configuration::getQRCode_Configuration_ID)
				.map(QRCodeConfigurationId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_QRCode_Attribute_Config.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_QRCode_Attribute_Config.COLUMNNAME_QRCode_Configuration_ID, codeConfigurationIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(attributeConfig -> QRCodeConfigurationId.ofRepoId(attributeConfig.getQRCode_Configuration_ID()),
																	 attributeConfig -> AttributeId.ofRepoId(attributeConfig.getAD_Attribute_ID())));
	}

	@NonNull
	private static ImmutableList<QRCodeConfiguration> toQRCodeConfiguration(
			@NonNull final ImmutableList<I_QRCode_Configuration> qrCodeConfigurations,
			@NonNull final ImmutableSetMultimap<QRCodeConfigurationId, AttributeId> configurationId2Attributes)
	{
		return qrCodeConfigurations.stream()
				.map(qrCodeConfiguration -> QRCodeConfiguration.builder()
						.id(QRCodeConfigurationId.ofRepoId(qrCodeConfiguration.getQRCode_Configuration_ID()))
						.name(qrCodeConfiguration.getName())
						.isOneQrCodeForAggregatedHUs(qrCodeConfiguration.isOneQRCodeForAggregatedHUs())
						.isOneQrCodeForMatchingAttributes(qrCodeConfiguration.isOneQRCodeForMatchingAttributes())
						.groupByAttributeIds(configurationId2Attributes.get(QRCodeConfigurationId.ofRepoId(qrCodeConfiguration.getQRCode_Configuration_ID())))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	private static class QRCodeConfigurationMap
	{
		@NonNull
		ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> id2Configuration;

		@NonNull
		public static QRCodeConfigurationMap ofList(@NonNull final ImmutableList<QRCodeConfiguration> configList)
		{
			final ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> id2Configuration = configList.stream()
					.collect(ImmutableMap.toImmutableMap(QRCodeConfiguration::getId, Function.identity()));
			return new QRCodeConfigurationMap(id2Configuration);
		}

		@NonNull
		public QRCodeConfiguration getById(@NonNull final QRCodeConfigurationId id)
		{
			return Optional.ofNullable(id2Configuration.get(id))
					.orElseThrow(() -> new AdempiereException("No QRCodeConfiguration found for id=" + id.getRepoId()));
		}

		@NonNull
		public ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> getByIds(@NonNull final Collection<QRCodeConfigurationId> ids)
		{
			return ImmutableSet.copyOf(ids)
					.stream()
					.collect(ImmutableMap.toImmutableMap(Function.identity(), id2Configuration::get));
		}

		public boolean isEmpty()
		{
			return id2Configuration.isEmpty();
		}
	}
}
