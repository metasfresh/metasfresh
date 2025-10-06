package de.metas.shipper.gateway.commons;

import com.google.common.collect.ImmutableSet;
import de.metas.shipper.gateway.commons.async.DeliveryOrderWorkpackageProcessor;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest.PackageInfo;
import de.metas.shipper.gateway.spi.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.DeliveryOrderKey.DeliveryOrderKeyBuilder;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.X12DE355;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
@RequiredArgsConstructor
public class ShipperGatewayFacade
{
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ShipperGatewayServicesRegistry shipperRegistry;
	@NonNull private final MPackageRepository mpackageRepository;

	private final UOMPrecision kgPrecision = uomDAO.getStandardPrecision(X12DE355.KILOGRAM);

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean hasServiceSupport(@NonNull final ShipperGatewayId shipperGatewayId)
	{
		return shipperRegistry.hasServiceSupport(shipperGatewayId);
	}

	public void createAndSendDeliveryOrdersForPackages(@NonNull final DeliveryOrderCreateRequest request)
	{
		final DeliveryOrderKeyBuilder orderKeyTemplate = DeliveryOrderKey.builder().setFrom(request);

		mpackageRepository.getByIds(request.getPackageIds())
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(mpackage -> orderKeyTemplate.setFrom(mpackage).build()))
				.asMap()
				.forEach(this::createAndSendDeliveryOrder);
	}

	private void createAndSendDeliveryOrder(
			@NonNull final DeliveryOrderKey deliveryOrderKey,
			@NonNull final Collection<I_M_Package> mpackages)
	{
		final ShipperGatewayId shipperGatewayId = getShipperGatewayId(deliveryOrderKey.getShipperId());
		final DeliveryOrderService shipperGatewayService = shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		DeliveryOrder deliveryOrder = shipperGatewayService.createDraftDeliveryOrder(
				CreateDraftDeliveryOrderRequest.builder()
						.deliveryOrderKey(deliveryOrderKey)
						.packageInfos(toPackageInfos(mpackages))
						.build()
		);
		deliveryOrder = shipperGatewayService.save(deliveryOrder);
		DeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(deliveryOrder.getIdNotNull(), shipperGatewayId, deliveryOrderKey.getAsyncBatchId());
	}

	private ShipperGatewayId getShipperGatewayId(final ShipperId shipperId)
	{
		return shipperDAO.getShipperGatewayId(shipperId).orElseThrow();
	}

	private ImmutableSet<PackageInfo> toPackageInfos(final @NotNull Collection<I_M_Package> mpackages)
	{
		return mpackages.stream()
				.map(mpackage -> PackageInfo.builder()
						.packageId(PackageId.ofRepoId(mpackage.getM_Package_ID()))
						.poReference(mpackage.getPOReference())
						.description(StringUtils.trimBlankToNull(mpackage.getDescription()))
						.weightInKg(extractWeightInKg(mpackage).orElse(null))
						.build())
				.collect(ImmutableSet.toImmutableSet());
	}

	private Optional<BigDecimal> extractWeightInKg(@NonNull final I_M_Package mpackage)
	{
		if (InterfaceWrapperHelper.isNull(mpackage, I_M_Package.COLUMNNAME_PackageWeight))
		{
			return Optional.empty();
		}

		final BigDecimal weightInKg = kgPrecision.round(mpackage.getPackageWeight()); // we assume it's in Kg
		return weightInKg.signum() > 0 ? Optional.of(weightInKg) : Optional.empty();
	}
}
