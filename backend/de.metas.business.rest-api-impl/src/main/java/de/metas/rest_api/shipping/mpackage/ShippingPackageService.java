/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipping.mpackage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.shipment.mpackage.JsonCreateShippingPackageInfo;
import de.metas.common.shipment.mpackage.JsonCreateShippingPackagesRequest;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.impl.AddTrackingInfosForInOutWithoutHUReq;
import de.metas.handlingunits.shipmentschedule.spi.impl.PackageInfo;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.rest_api.shipping.ShipmentService;
import de.metas.rest_api.shipping.ShippedCandidateKey;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingPackageService
{

	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);

	private final ShipmentService shipmentService;

	public ShippingPackageService(final ShipmentService shipmentService)
	{
		this.shipmentService = shipmentService;
	}

	public void generateShippingPackages(@NonNull final JsonCreateShippingPackagesRequest request)
	{
		try
		{
			generateShippingPackages_0(request);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("requestBody", request);
		}
	}

	public void generateShippingPackages_0(@NonNull final JsonCreateShippingPackagesRequest request)
	{
		//1. request => shippedCandidateKeySet
		final ImmutableSet<ShippedCandidateKey> shippedCandidateKeys = request.getPackageInfos().stream()
				.map(this::extractShippedCandidateKey)
				.collect(ImmutableSet.toImmutableSet());

		//2. get shipment Ids by shippedCandidateKeys
		final ImmutableMap<ShippedCandidateKey, InOutId> candidateKey2ShipmentId = shipmentService.retrieveShipmentIdsByCandidateKey(shippedCandidateKeys);

		//3. load shippers by internal name
		final ImmutableSet<String> shipperInternalNameSet = request.getPackageInfos().stream()
				.map(JsonCreateShippingPackageInfo::getShipperInternalName)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<String, I_M_Shipper> internalName2Shipper = shipperDAO.getByInternalName(shipperInternalNameSet);

		//4. group by GenerateShippingPackagesGroupingKey
		final HashMap<GenerateShippingPackagesGroupingKey, List<PackageInfo>> groupingKey2PackageInfoList = new HashMap<>();

		for (final JsonCreateShippingPackageInfo createPackageInfo : request.getPackageInfos())
		{
			final GenerateShippingPackagesGroupingKey groupingKey = extractShippingPackagesGroupingKey(candidateKey2ShipmentId, internalName2Shipper, createPackageInfo);

			final List<PackageInfo> packageInfos = extractPackageInfoMutableList(createPackageInfo, internalName2Shipper);

			groupingKey2PackageInfoList.merge(groupingKey, packageInfos, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		}

		//5 create packages
		createTransportationOrderAndPackages(groupingKey2PackageInfoList);
	}

	@NonNull
	private ShippedCandidateKey extractShippedCandidateKey(@NonNull final JsonCreateShippingPackageInfo packageInfo)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(packageInfo.getShipmentCandidateId().getValue());

		return ShippedCandidateKey.builder()
				.shipmentDocumentNo(packageInfo.getShipmentDocumentNo())
				.shipmentScheduleId(shipmentScheduleId)
				.build();
	}

	@NonNull
	private GenerateShippingPackagesGroupingKey extractShippingPackagesGroupingKey(
			@NonNull final ImmutableMap<ShippedCandidateKey, InOutId> candidateKey2ShipmentId,
			@NonNull final ImmutableMap<String, I_M_Shipper> internalName2Shipper,
			@NonNull final JsonCreateShippingPackageInfo packageInfo)
	{
		final ShippedCandidateKey shippedCandidateKey = extractShippedCandidateKey(packageInfo);

		final InOutId shipmentId = candidateKey2ShipmentId.get(shippedCandidateKey);

		if (shipmentId == null)
		{
			throw new AdempiereException("No shipment (M_InOut) was found for the given candidateKey!")
					.appendParametersToMessage()
					.setParameter("CandidateKey", shippedCandidateKey);
		}

		final I_M_Shipper shipper = internalName2Shipper.get(packageInfo.getShipperInternalName());

		if (shipper == null)
		{
			throw new AdempiereException("No M_Shipper was found for the given internal name!")
					.appendParametersToMessage()
					.setParameter("InternalName", packageInfo.getShipperInternalName());
		}

		return GenerateShippingPackagesGroupingKey.builder()
				.inOutId(shipmentId)
				.shipperId(ShipperId.ofRepoId(shipper.getM_Shipper_ID()))
				.build();
	}

	@NonNull
	private List<PackageInfo> extractPackageInfoMutableList(
			@NonNull final JsonCreateShippingPackageInfo createShippingPackageInfo,
			@NonNull final ImmutableMap<String, I_M_Shipper> internalName2Shipper)
	{
		final I_M_Shipper shipper = internalName2Shipper.get(createShippingPackageInfo.getShipperInternalName());

		final String trackingURL = shipper != null ? shipper.getTrackingURL() : null;

		return createShippingPackageInfo.getPackageInfos()
				.stream()
				.map(jsonPackage -> PackageInfo.builder()
						.trackingUrl(trackingURL)
						.trackingNumber(jsonPackage.getTrackingCode())
						.weight(jsonPackage.getWeight())
						.build())
				.collect(Collectors.toList());
	}

	private void createTransportationOrderAndPackages(@NonNull final HashMap<GenerateShippingPackagesGroupingKey, List<PackageInfo>> groupingKey2PackageInfoList)
	{
		groupingKey2PackageInfoList.entrySet()
				.stream()
				.map(entry -> AddTrackingInfosForInOutWithoutHUReq.builder()
						.inOutId(entry.getKey().getInOutId())
						.shipperId(entry.getKey().getShipperId())
						.packageInfos(entry.getValue())
						.build())
				.map(huShipperTransportationBL::addTrackingInfosForInOutWithoutHU)
				.forEach(huShipperTransportationBL::processShipperTransportation);
	}

	@Value
	@Builder
	private static class GenerateShippingPackagesGroupingKey
	{
		@NonNull
		InOutId inOutId;

		@NonNull
		ShipperId shipperId;
	}
}
