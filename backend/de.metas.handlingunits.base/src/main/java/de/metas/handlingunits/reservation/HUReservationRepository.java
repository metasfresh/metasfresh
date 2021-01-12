package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectAndLineId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Repository
public class HUReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final CCache<HuId, Optional<HUReservationDocRef>> documentRefByVhuId = CCache.newLRUCache(
			I_M_HU_Reservation.Table_Name + "#by#" + I_M_HU_Reservation.COLUMNNAME_VHU_ID, 500, 5);

	public Optional<HUReservation> getByDocumentRef(@NonNull final HUReservationDocRef documentRef)
	{
		final List<I_M_HU_Reservation> huReservationRecords = retrieveRecordsByDocumentRef(documentRef);
		if (huReservationRecords.isEmpty())
		{
			return Optional.empty();
		}

		final HUReservation huReservation = toHUReservation(documentRef, huReservationRecords);
		return Optional.of(huReservation);
	}

	private List<I_M_HU_Reservation> retrieveRecordsByDocumentRef(final HUReservationDocRef documentRef)
	{
		final IQueryBuilder<I_M_HU_Reservation> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter();

		if (documentRef.getSalesOrderLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Reservation.COLUMN_C_OrderLineSO_ID, documentRef.getSalesOrderLineId());
		}
		else if (documentRef.getProjectAndLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_Project_ID, documentRef.getProjectAndLineId().getProjectId());
			queryBuilder.addEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_ProjectLine_ID, documentRef.getProjectAndLineId().getProjectLineRepoId());
		}

		return queryBuilder.create().list();
	}

	private HUReservation toHUReservation(
			@NonNull final HUReservationDocRef documentRef,
			@NonNull final List<I_M_HU_Reservation> huReservationRecords)
	{
		final Map<HuId, Quantity> reservedQtyByVhuId = new HashMap<>();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final I_C_UOM uom = uomDAO.getById(huReservationRecord.getC_UOM_ID());
			final Quantity reservedQty = Quantity.of(huReservationRecord.getQtyReserved(), uom);

			reservedQtyByVhuId.put(vhuId, reservedQty);
		}

		final ImmutableSet<BPartnerId> customerIds = huReservationRecords.stream()
				.map(huReservationRecord -> BPartnerId.ofRepoIdOrNull(huReservationRecord.getC_BPartner_Customer_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final BPartnerId customerId = customerIds.size() == 1 ? customerIds.iterator().next() : null;

		return HUReservation.builder()
				.documentRef(documentRef)
				.customerId(customerId)
				.reservedQtyByVhuIds(reservedQtyByVhuId)
				.build();
	}

	public void save(@NonNull final HUReservation huReservation)
	{
		for (final HuId vhuId : huReservation.getVhuIds())
		{
			final Quantity qtyReserved = huReservation.getReservedQtyByVhuId(vhuId);

			final I_M_HU_Reservation huReservationRecord = retrieveOrCreateByVhuId(vhuId);
			updateRecordFromDocumentRef(huReservationRecord, huReservation.getDocumentRef());
			huReservationRecord.setC_BPartner_Customer_ID(BPartnerId.toRepoId(huReservation.getCustomerId()));
			huReservationRecord.setQtyReserved(qtyReserved.toBigDecimal());
			huReservationRecord.setC_UOM_ID(qtyReserved.getUomId().getRepoId());

			saveRecord(huReservationRecord);
		}
	}

	private static void updateRecordFromDocumentRef(
			@NonNull final I_M_HU_Reservation record,
			@NonNull final HUReservationDocRef documentRef)
	{
		record.setC_OrderLineSO_ID(OrderLineId.toRepoId(documentRef.getSalesOrderLineId()));
		record.setC_Project_ID(documentRef.getProjectAndLineId() != null ? documentRef.getProjectAndLineId().getProjectId().getRepoId() : -1);
		record.setC_ProjectLine_ID(documentRef.getProjectAndLineId() != null ? documentRef.getProjectAndLineId().getProjectLineRepoId() : -1);
	}

	private I_M_HU_Reservation retrieveOrCreateByVhuId(@NonNull final HuId vhuId)
	{
		final I_M_HU_Reservation existingHuReservationRecord = queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuId)
				.create()
				.firstOnly(I_M_HU_Reservation.class);
		if (existingHuReservationRecord != null)
		{
			return existingHuReservationRecord;
		}
		else
		{
			final I_M_HU_Reservation newHuReservationRecord = newInstance(I_M_HU_Reservation.class);
			newHuReservationRecord.setVHU_ID(vhuId.getRepoId());
			return newHuReservationRecord;
		}
	}

	public IQuery<I_M_HU_Reservation> createQueryReservedToOtherThan(@NonNull final OrderLineId orderLineId)
	{
		return queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_OrderLineSO_ID, orderLineId)
				.create();
	}

	public void deleteReservationsByVhuIds(@NonNull final Collection<HuId> vhuIds)
	{
		if (vhuIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuIds)
				.create()
				.delete();
	}

	public void warmup(@NonNull final Collection<HuId> huIds)
	{
		documentRefByVhuId.getAllOrLoad(huIds, this::retrieveDocumentRefsByVhuIds);
	}

	private Map<HuId, Optional<HUReservationDocRef>> retrieveDocumentRefsByVhuIds(@NonNull final Collection<HuId> vhuIds)
	{
		final HashMap<HuId, Optional<HUReservationDocRef>> map = new HashMap<>(vhuIds.size());

		vhuIds.forEach(huId -> map.put(huId, Optional.empty()));

		final List<I_M_HU_Reservation> huReservationRecords = queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuIds)
				.create()
				.list();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final HUReservationDocRef documentRef = extractDocumentRef(huReservationRecord);
			map.put(vhuId, Optional.of(documentRef));
		}
		return ImmutableMap.copyOf(map);
	}

	public Optional<OrderLineId> getOrderLineIdByReservedVhuId(@NonNull final HuId vhuId)
	{
		return getDocumentRefByVhuId(vhuId)
				.map(HUReservationDocRef::getSalesOrderLineId);
	}

	public Optional<HUReservationDocRef> getDocumentRefByVhuId(@NonNull final HuId vhuId)
	{
		return documentRefByVhuId.getOrLoad(vhuId, this::retrieveDocumentRefByVhuId);
	}

	private Optional<HUReservationDocRef> retrieveDocumentRefByVhuId(@NonNull final HuId vhuId)
	{
		final I_M_HU_Reservation huReservationRecord = queryBL.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuId) // we have a UC constraint on VHU_ID
				.create()
				.firstOnly(I_M_HU_Reservation.class);
		if (huReservationRecord == null)
		{
			return Optional.empty();
		}

		return Optional.of(extractDocumentRef(huReservationRecord));
	}

	private static HUReservationDocRef extractDocumentRef(@NonNull final I_M_HU_Reservation record)
	{
		return HUReservationDocRef.builder()
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()))
				.projectAndLineId(ProjectAndLineId.ofRepoIdOrNull(record.getC_Project_ID(), record.getC_ProjectLine_ID()))
				.build();
	}

	public void transferReservation(
			@NonNull final HUReservationDocRef from,
			@NonNull final HUReservationDocRef to)
	{
		if (Objects.equals(from, to))
		{
			return;
		}

		final List<I_M_HU_Reservation> records = retrieveRecordsByDocumentRef(from);
		if (records.isEmpty())
		{
			return;
		}

		for (final I_M_HU_Reservation record : records)
		{
			updateRecordFromDocumentRef(record, to);
			saveRecord(record);
		}
	}

}
