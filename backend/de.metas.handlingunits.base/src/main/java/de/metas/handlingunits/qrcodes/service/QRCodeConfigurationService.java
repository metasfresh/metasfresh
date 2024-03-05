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
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.QRCodeConfiguration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfigurationId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QRCodeConfigurationService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull
	private final QRCodeConfigurationRepository repository;

	public boolean isOneQrCodeForAggregatedHUsEnabledFor(@NonNull final I_M_HU hu)
	{
		if (!handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			return false;
		}

		return getConfigurationForHuId(hu)
				.map(QRCodeConfiguration::isOneQRCodeForAggregatedTUsEnabled)
				.orElse(false);
	}

	@NonNull
	public ImmutableSet<HuId> filterSplitHUsForSharingQr(@NonNull final I_M_HU sourceHU, @NonNull final ImmutableList<I_M_HU> newHUs)
	{
		if (!isOneQrCodeForAggregatedHUsEnabledFor(sourceHU))
		{
			return ImmutableSet.of();
		}

		return newHUs.stream()
				.flatMap(newHU -> {
					if (handlingUnitsBL.isLoadingUnit(newHU))
					{
						return handlingUnitsDAO.retrieveIncludedHUs(newHU).stream();
					}
					else
					{
						return Stream.of(newHU);
					}
				})
				.filter(handlingUnitsBL::isTransportUnitOrAggregate)
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isAtLeastOneActiveConfig()
	{
		return repository.isAtLeastOneActiveConfig();
	}

	@NonNull
	public ImmutableMap<QRCodeConfigurationId, QRCodeConfiguration> getByIds(@NonNull final ImmutableSet<QRCodeConfigurationId> qrCodeConfigurationIds)
	{
		return repository.getByIds(qrCodeConfigurationIds);
	}

	@NonNull
	private Optional<QRCodeConfiguration> getConfigurationForHuId(@NonNull final I_M_HU hu)
	{
		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		return Optional.ofNullable(productId)
				.map(productBL::getById)
				.map(I_M_Product::getQRCode_Configuration_ID)
				.map(QRCodeConfigurationId::ofRepoIdOrNull)
				.map(repository::getById);
	}
}
