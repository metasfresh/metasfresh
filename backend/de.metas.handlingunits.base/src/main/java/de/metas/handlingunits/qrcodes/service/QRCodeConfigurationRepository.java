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
import de.metas.handlingunits.model.I_QRCode_Attribute_Config;
import de.metas.handlingunits.model.I_QRCode_Configuration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfiguration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfigurationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class QRCodeConfigurationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public QRCodeConfiguration getById(@NonNull final QRCodeConfigurationId id)
	{
		return toQRCodeConfiguration(ImmutableList.of(InterfaceWrapperHelper.load(id, I_QRCode_Configuration.class)))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No QRCodeConfiguration found for id!")
						.appendParametersToMessage()
						.setParameter("id", id));
	}

	@NonNull
	public ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> getByIds(@NonNull final ImmutableSet<QRCodeConfigurationId> qrCodeConfigurationIds)
	{
		return toQRCodeConfiguration(InterfaceWrapperHelper.loadByRepoIdAwares(qrCodeConfigurationIds, I_QRCode_Configuration.class))
				.collect(ImmutableMap.toImmutableMap(QRCodeConfiguration::getId, Function.identity()));
	}

	public boolean isAtLeastOneActiveConfig()
	{
		return queryBL.createQueryBuilder(I_QRCode_Configuration.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	@NonNull
	private Stream<QRCodeConfiguration> toQRCodeConfiguration(@NonNull final List<I_QRCode_Configuration> qrCodeConfigurations)
	{
		final Set<QRCodeConfigurationId> codeConfigurationIds = qrCodeConfigurations.stream()
				.map(I_QRCode_Configuration::getQRCode_Configuration_ID)
				.map(QRCodeConfigurationId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final Map<QRCodeConfigurationId, Set<AttributeId>> configId2Attributes = getAttributeIds(codeConfigurationIds);

		return qrCodeConfigurations.stream()
				.map(qrCodeConfiguration -> QRCodeConfiguration.builder()
						.id(QRCodeConfigurationId.ofRepoId(qrCodeConfiguration.getQRCode_Configuration_ID()))
						.name(qrCodeConfiguration.getName())
						.isOneQrCodeForAggregatedHUs(qrCodeConfiguration.isOneQRCodeForAggregatedHUs())
						.isOneQrCodeForMatchingAttributes(qrCodeConfiguration.isOneQRCodeForMatchingAttributes())
						.attributeIds(configId2Attributes.get(QRCodeConfigurationId.ofRepoId(qrCodeConfiguration.getQRCode_Configuration_ID())))
						.build());
	}

	@NonNull
	private Map<QRCodeConfigurationId, Set<AttributeId>> getAttributeIds(@NonNull final Collection<QRCodeConfigurationId> qrCodeConfigurationIds)
	{
		return queryBL.createQueryBuilder(I_QRCode_Attribute_Config.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_QRCode_Attribute_Config.COLUMNNAME_QRCode_Configuration_ID, qrCodeConfigurationIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(attributeConfig -> QRCodeConfigurationId.ofRepoId(attributeConfig.getQRCode_Attribute_Config_ID()),
											   Collectors.mapping(attributeConfig -> AttributeId.ofRepoId(attributeConfig.getAD_Attribute_ID()),
																  Collectors.toSet())));
	}
}
