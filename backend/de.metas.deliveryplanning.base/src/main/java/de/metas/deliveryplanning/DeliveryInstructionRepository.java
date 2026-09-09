/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.incoterms.IncotermsId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@RequiredArgsConstructor
public class DeliveryInstructionRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final DimensionService dimensionService;

	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return load(deliveryInstructionId, I_M_ShipperTransportation.class);
	}

	public I_M_ShipperTransportation create(@NonNull final DeliveryInstructionCreateRequest request)
	{
		final I_M_ShipperTransportation deliveryInstructionRecord = newInstance(I_M_ShipperTransportation.class);

		deliveryInstructionRecord.setAD_Org_ID(request.getOrgId().getRepoId());

		deliveryInstructionRecord.setTransportDirection(request.getTransportDirection().getCode());

		deliveryInstructionRecord.setShipper_BPartner_ID(request.getShipperBPartnerId().getRepoId());
		deliveryInstructionRecord.setShipper_Location_ID(request.getShipperLocationId().getRepoId());

		deliveryInstructionRecord.setProcessed(request.isProcessed());

		deliveryInstructionRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryInstructionRecord.setIncotermLocation(request.getIncotermLocation());

		deliveryInstructionRecord.setLoadingTime(request.getLoadingTime());
		deliveryInstructionRecord.setDeliveryTime(request.getDeliveryTime());

		deliveryInstructionRecord.setM_Shipper_ID(request.getShipperId().getRepoId());

		deliveryInstructionRecord.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(request.getMeansOfTransportationId()));

		deliveryInstructionRecord.setETA(TimeUtil.asTimestamp(request.getDeliveryDate()));
		deliveryInstructionRecord.setATA(TimeUtil.asTimestamp(request.getAta()));
		deliveryInstructionRecord.setDateDoc(TimeUtil.asTimestamp(request.getDateDoc()));
		deliveryInstructionRecord.setC_DocType_ID(request.getDocTypeId().getRepoId());

		deliveryInstructionRecord.setETD(TimeUtil.asTimestamp(request.getLoadingDate()));
		deliveryInstructionRecord.setATD(TimeUtil.asTimestamp(request.getAtd()));

		deliveryInstructionRecord.setC_BPartner_Location_Delivery_ID(request.getDeliveryPartnerLocationId().getRepoId());
		deliveryInstructionRecord.setC_BPartner_Location_Loading_ID(request.getLoadingPartnerLocationId().getRepoId());

		dimensionService.updateRecord(deliveryInstructionRecord, request.getDimension());

		save(deliveryInstructionRecord);

		return deliveryInstructionRecord;
	}

	/**
	 * Saved only when at least one field actually differs, so a no-op resolution fires no {@code AFTER_CHANGE}.
	 */
	public void updateDates(@NonNull final I_M_ShipperTransportation record, @NonNull final DeliveryInstructionDates dates)
	{
		// Compare as Instant on both sides: the columns are Timestamp, and Objects.equals(Timestamp, Instant)
		// compiles but always answers "differs", which would leave this no-op guard permanently open.
		final boolean changed = !Objects.equals(TimeUtil.asInstant(record.getETD()), dates.getEtd())
				|| !Objects.equals(TimeUtil.asInstant(record.getETA()), dates.getEta())
				|| !Objects.equals(TimeUtil.asInstant(record.getATD()), dates.getAtd())
				|| !Objects.equals(TimeUtil.asInstant(record.getATA()), dates.getAta())
				|| !Objects.equals(record.getLoadingTime(), dates.getLoadingTime())
				|| !Objects.equals(record.getDeliveryTime(), dates.getDeliveryTime());
		if (!changed)
		{
			return;
		}

		record.setETD(TimeUtil.asTimestamp(dates.getEtd()));
		record.setETA(TimeUtil.asTimestamp(dates.getEta()));
		record.setATD(TimeUtil.asTimestamp(dates.getAtd()));
		record.setATA(TimeUtil.asTimestamp(dates.getAta()));
		record.setLoadingTime(dates.getLoadingTime());
		record.setDeliveryTime(dates.getDeliveryTime());
		saveRecord(record);
	}

	public void setDeliveredState(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final DeliveryInstructionDeliveredState deliveredState)
	{
		final I_M_ShipperTransportation deliveryInstructionRecord = load(deliveryInstructionId, I_M_ShipperTransportation.class);
		deliveryInstructionRecord.setDeliveredState(deliveredState.getCode());
		saveRecord(deliveryInstructionRecord);
	}

	public ImmutableMap<ShipperTransportationId, DocStatus> getDocStatuses(@NonNull final Collection<ShipperTransportationId> deliveryInstructionIds)
	{
		if (deliveryInstructionIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID()),
						DeliveryInstructionRepository::extractDocStatus));
	}

	public DocStatus getDocStatus(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return extractDocStatus(load(deliveryInstructionId, I_M_ShipperTransportation.class));
	}

	private static DocStatus extractDocStatus(@NonNull final I_M_ShipperTransportation deliveryInstructionRecord)
	{
		return DocStatus.ofNullableCodeOrUnknown(deliveryInstructionRecord.getDocStatus());
	}

	public Iterator<I_M_ShipperTransportation> iterateByIds(@NonNull final Collection<ShipperTransportationId> deliveryInstructionIds)
	{
		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.create()
				.iterate(I_M_ShipperTransportation.class);
	}

	public boolean hasCompletedAmong(@NonNull final Collection<ShipperTransportationId> deliveryInstructionIds)
	{
		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, DocStatus.Completed)
				.anyMatch();
	}

	public ShippingPackageId createShippingPackage(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest.ShippingPackageData packageData,
			@NonNull final PackageId packageId)
	{
		final int shipperBPartnerId = deliveryInstructionRecord.getShipper_BPartner_ID();
		final int shipperLocationId = deliveryInstructionRecord.getShipper_Location_ID();

		final I_M_ShippingPackage shippingPackageRecord = newInstance(I_M_ShippingPackage.class);
		shippingPackageRecord.setM_ShipperTransportation_ID(deliveryInstructionRecord.getM_ShipperTransportation_ID());
		shippingPackageRecord.setM_Package_ID(packageId.getRepoId());
		shippingPackageRecord.setIsToBeFetched(packageData.isToBeFetched());
		shippingPackageRecord.setM_Product_ID(packageData.getProductId().getRepoId());

		// the four quantity figures are a ColumnSQL read-through of the planning, so there is nothing to write here -
		// a written copy would freeze the package's "actual" at whatever the planning said at creation time
		shippingPackageRecord.setBatch(packageData.getBatchNo());
		shippingPackageRecord.setC_UOM_ID(packageData.getUomId().getRepoId());

		shippingPackageRecord.setC_BPartner_ID(shipperBPartnerId);
		shippingPackageRecord.setC_BPartner_Location_ID(shipperLocationId);

		shippingPackageRecord.setC_OrderLine_ID(OrderLineId.toRepoId(packageData.getOrderLineId()));
		shippingPackageRecord.setC_Order_ID(OrderId.toRepoId(packageData.getOrderId()));

		saveRecord(shippingPackageRecord);

		return ShippingPackageId.ofRepoId(shippingPackageRecord.getM_ShippingPackage_ID());
	}

	public void deactivateShippingPackages(@NonNull final Collection<ShippingPackageId> shippingPackageIds)
	{
		for (final I_M_ShippingPackage shippingPackageRecord : getShippingPackagesByIds(shippingPackageIds))
		{
			shippingPackageRecord.setIsActive(false);
			saveRecord(shippingPackageRecord);
		}
	}

	public void unlinkShippingPackages(@NonNull final Collection<ShippingPackageId> shippingPackageIds)
	{
		for (final I_M_ShippingPackage shippingPackageRecord : getShippingPackagesByIds(shippingPackageIds))
		{
			shippingPackageRecord.setC_OrderLine_ID(-1);
			save(shippingPackageRecord);
		}
	}

	/**
	 * {@code IsActive} is deliberately not filtered: the caller that unlinks a package has just deactivated it.
	 */
	private List<I_M_ShippingPackage> getShippingPackagesByIds(@NonNull final Collection<ShippingPackageId> shippingPackageIds)
	{
		if (shippingPackageIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, shippingPackageIds)
				.create()
				.list();
	}
}
