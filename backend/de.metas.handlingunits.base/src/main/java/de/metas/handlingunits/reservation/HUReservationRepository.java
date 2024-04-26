package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
	private static final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final CCache<HuId, Optional<HUReservationEntry>> entriesByVhuId = CCache.<HuId, Optional<HUReservationEntry>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(500) // in case of LRU this is the maxSize
			.expireMinutes(5)
			.tableName(I_M_HU_Reservation.Table_Name)
			.build();

	public Optional<HUReservation> getByDocumentRef(
			@NonNull final HUReservationDocRef documentRef,
			@NonNull final ImmutableSet<HuId> onlyVHUIds)
	{
		final List<I_M_HU_Reservation> huReservationRecords = retrieveRecordsByDocumentRef(
				ImmutableSet.of(documentRef),
				onlyVHUIds);
		return !huReservationRecords.isEmpty()
				? Optional.of(toHUReservation(huReservationRecords))
				: Optional.empty();
	}

	private List<I_M_HU_Reservation> retrieveRecordsByDocumentRef(
			@NonNull final Collection<HUReservationDocRef> documentRefs,
			@NonNull final Set<HuId> onlyVHUIds)
	{
		Check.assumeNotEmpty(documentRefs, "documentRefs shall not be empty");

		final IQueryBuilder<I_M_HU_Reservation> queryBuilder = queryByDocumentRefs(documentRefs);

		//
		// Filter by VHUs
		if (!onlyVHUIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_HU_Reservation.COLUMNNAME_VHU_ID, onlyVHUIds);
		}

		return queryBuilder.create().list();
	}

	private IQueryBuilder<I_M_HU_Reservation> queryByDocumentRefs(@NonNull final Collection<HUReservationDocRef> documentRefs)
	{
		Check.assumeNotEmpty(documentRefs, "documentRefs shall not be empty");

		final IQueryBuilder<I_M_HU_Reservation> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter();

		final HashSet<OrderLineId> salesOrderLineIds = new HashSet<>();
		final HashSet<ProjectId> projectIds = new HashSet<>();
		final HashSet<PickingJobStepId> pickingJobStepIds = new HashSet<>();
		final HashSet<DDOrderLineId> ddOrderLineIds = new HashSet<>();
		for (final HUReservationDocRef documentRef : documentRefs)
		{
			documentRef.map(new HUReservationDocRef.CaseMappingFunction<Void>()
			{
				@Override
				public Void salesOrderLineId(@NonNull final OrderLineId salesOrderLineId)
				{
					salesOrderLineIds.add(salesOrderLineId);
					return null;
				}

				@Override
				public Void projectId(@NonNull final ProjectId projectId)
				{
					projectIds.add(projectId);
					return null;
				}

				@Override
				public Void pickingJobStepId(@NonNull final PickingJobStepId pickingJobStepId)
				{
					pickingJobStepIds.add(pickingJobStepId);
					return null;
				}

				@Override
				public Void ddOrderLineId(@NonNull final DDOrderLineId ddOrderLineId)
				{
					ddOrderLineIds.add(ddOrderLineId);
					return null;
				}
			});

			final ICompositeQueryFilter<I_M_HU_Reservation> documentRefFilter = queryBuilder.addCompositeQueryFilter().setJoinOr();
			if (!salesOrderLineIds.isEmpty())
			{
				documentRefFilter.addInArrayFilter(I_M_HU_Reservation.COLUMN_C_OrderLineSO_ID, salesOrderLineIds);
			}
			if (!projectIds.isEmpty())
			{
				documentRefFilter.addInArrayFilter(I_M_HU_Reservation.COLUMNNAME_C_Project_ID, projectIds);
			}
			if (!pickingJobStepIds.isEmpty())
			{
				documentRefFilter.addInArrayFilter(I_M_HU_Reservation.COLUMNNAME_M_Picking_Job_Step_ID, pickingJobStepIds);
			}
			if(!ddOrderLineIds.isEmpty())
			{
				documentRefFilter.addInArrayFilter(I_M_HU_Reservation.COLUMNNAME_DD_OrderLine_ID, ddOrderLineIds);
			}
		}

		return queryBuilder;
	}

	private static HUReservation toHUReservation(@NonNull final List<I_M_HU_Reservation> huReservationRecords)
	{
		return HUReservation.ofEntries(
				huReservationRecords.stream()
						.map(HUReservationRepository::toHUReservationEntry)
						.collect(ImmutableList.toImmutableList()));
	}

	private static HUReservationEntry toHUReservationEntry(final I_M_HU_Reservation record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return HUReservationEntry.builder()
				.documentRef(extractDocumentRef(record))
				.customerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_Customer_ID()))
				.vhuId(HuId.ofRepoId(record.getVHU_ID()))
				.qtyReserved(Quantitys.of(record.getQtyReserved(), uomId))
				.build();
	}

	public ImmutableList<HUReservationEntry> createOrUpdateEntries(@NonNull final List<HUReservationEntryUpdateRepoRequest> requests)
	{
		return requests.stream()
				.map(this::createOrUpdateEntry)
				.collect(ImmutableList.toImmutableList());
	}

	private HUReservationEntry createOrUpdateEntry(final HUReservationEntryUpdateRepoRequest request)
	{
		final I_M_HU_Reservation record = retrieveOrCreateRecordByVhuId(request.getVhuId());
		updateRecordFromDocumentRef(record, request.getDocumentRef());
		record.setC_BPartner_Customer_ID(BPartnerId.toRepoId(request.getCustomerId()));
		record.setQtyReserved(request.getQtyReserved().toBigDecimal());
		record.setC_UOM_ID(request.getQtyReserved().getUomId().getRepoId());
		saveRecord(record);

		return toHUReservationEntry(record);
	}

	private static void updateRecordFromDocumentRef(
			@NonNull final I_M_HU_Reservation record,
			@NonNull final HUReservationDocRef documentRef)
	{
		record.setC_OrderLineSO_ID(OrderLineId.toRepoId(documentRef.getSalesOrderLineId()));
		record.setC_Project_ID(ProjectId.toRepoId(documentRef.getProjectId()));
		record.setM_Picking_Job_Step_ID(PickingJobStepId.toRepoId(documentRef.getPickingJobStepId()));
		record.setDD_OrderLine_ID(DDOrderLineId.toRepoId(documentRef.getDdOrderLineId()));

		setOrgID(record, documentRef);
	}

	private static void setOrgID(@NonNull final I_M_HU_Reservation record, @NonNull final HUReservationDocRef documentRef)
	{
		final PickingJobStepId pickingJobStepId = documentRef.getPickingJobStepId();
		if (pickingJobStepId != null)
		{
			final TableRecordReference recordReference = pickingJobStepId.toTableRecordReference();
			final I_M_Picking_Job_Step stepRecord = recordReference.getModel(I_M_Picking_Job_Step.class);
			record.setAD_Org_ID(stepRecord.getAD_Org_ID());
		}

	}

	private I_M_HU_Reservation retrieveOrCreateRecordByVhuId(@NonNull final HuId vhuId)
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

	public IQuery<I_M_HU_Reservation> createQueryReservedToOtherThan(@NonNull final HUReservationDocRef documentRef)
	{
		final IQueryBuilder<I_M_HU_Reservation> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter();

		documentRef.map(new HUReservationDocRef.CaseMappingFunction<Void>()
		{
			@Override
			public Void salesOrderLineId(@NonNull final OrderLineId salesOrderLineId)
			{
				queryBuilder.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_OrderLineSO_ID, salesOrderLineId);
				return null;
			}

			@Override
			public Void projectId(@NonNull final ProjectId projectId)
			{
				queryBuilder.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_Project_ID, projectId);
				return null;
			}

			@Override
			public Void pickingJobStepId(@NonNull final PickingJobStepId pickingJobStepId)
			{
				queryBuilder.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_M_Picking_Job_Step_ID, pickingJobStepId);
				return null;
			}

			@Override
			public Void ddOrderLineId(@NonNull final DDOrderLineId ddOrderLineId)
			{
				queryBuilder.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId);
				return null;
			}
		});

		return queryBuilder.create();
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

	public Set<HuId> deleteReservationsByDocumentRefs(@NonNull final Set<HUReservationDocRef> documentRefs)
	{
		if (documentRefs.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<I_M_HU_Reservation> records = queryByDocumentRefs(documentRefs)
				.create()
				.list();

		final ImmutableSet<HuId> releasedVHUIds = records.stream()
				.map(record -> HuId.ofRepoId(record.getVHU_ID()))
				.collect(ImmutableSet.toImmutableSet());

		InterfaceWrapperHelper.deleteAll(records);

		return releasedVHUIds;
	}

	public void warmupByVHUIds(@NonNull final Collection<HuId> vhuIds)
	{
		entriesByVhuId.getAllOrLoad(vhuIds, this::retrieveEntriesByVHUId);
	}

	public ImmutableList<HUReservationEntry> getEntriesByVHUIds(@NonNull final Collection<HuId> vhuIds)
	{
		if (vhuIds.isEmpty()) {return ImmutableList.of();}

		return entriesByVhuId.getAllOrLoad(vhuIds, this::retrieveEntriesByVHUId)
				.stream()
				.flatMap(Optionals::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private Map<HuId, Optional<HUReservationEntry>> retrieveEntriesByVHUId(@NonNull final Collection<HuId> vhuIds)
	{
		if (vhuIds.isEmpty()) {return ImmutableMap.of();} // shall not happen

		final HashMap<HuId, Optional<HUReservationEntry>> result = new HashMap<>(vhuIds.size());
		vhuIds.forEach(huId -> result.put(huId, Optional.empty()));

		queryBL.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuIds)
				.create()
				.stream()
				.map(HUReservationRepository::toHUReservationEntry)
				.forEach(entry -> result.put(entry.getVhuId(), Optional.of(entry)));

		return ImmutableMap.copyOf(result);
	}

	public Optional<OrderLineId> getOrderLineIdByReservedVhuId(@NonNull final HuId vhuId)
	{
		return getEntryByVhuId(vhuId)
				.map(entry -> entry.getDocumentRef().getSalesOrderLineId());
	}

	private Optional<HUReservationEntry> getEntryByVhuId(@NonNull final HuId vhuId)
	{
		return entriesByVhuId.getOrLoad(vhuId, this::retrieveEntryByVhuId);
	}

	private Optional<HUReservationEntry> retrieveEntryByVhuId(@NonNull final HuId vhuId)
	{
		return queryBL.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuId) // we have a UC constraint on VHU_ID
				.create()
				.firstOnlyOptional(I_M_HU_Reservation.class)
				.map(HUReservationRepository::toHUReservationEntry);
	}

	private static HUReservationDocRef extractDocumentRef(@NonNull final I_M_HU_Reservation record)
	{
		return HUReservationDocRef.builder()
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()))
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.pickingJobStepId(PickingJobStepId.ofRepoIdOrNull(record.getM_Picking_Job_Step_ID()))
				.ddOrderLineId(DDOrderLineId.ofRepoIdOrNull(record.getDD_OrderLine_ID()))
				.build();
	}

	public void transferReservation(
			@NonNull final Collection<HUReservationDocRef> from,
			@NonNull final HUReservationDocRef to,
			@NonNull final Set<HuId> onlyVHUIds)
	{
		if (from.size() == 1 && Objects.equals(from.iterator().next(), to))
		{
			return;
		}

		final List<I_M_HU_Reservation> records = retrieveRecordsByDocumentRef(from, onlyVHUIds);
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
