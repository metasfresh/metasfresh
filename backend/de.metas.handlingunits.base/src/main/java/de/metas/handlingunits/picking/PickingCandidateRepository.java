package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_Picking_Candidate_IssueToOrder;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Dedicated DAO-ish class centered around {@link I_M_Picking_Candidate}s
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class PickingCandidateRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	public PickingCandidate getById(@NonNull final PickingCandidateId id)
	{
		return toPickingCandidateAndLoadIssuesToBOMLine(getRecordById(id));
	}

	public List<PickingCandidate> getByIds(@NonNull final Set<PickingCandidateId> ids)
	{
		return getRecordsByIds(ids)
				.stream()
				.collect(toPickingCandidatesList());
	}

	private I_M_Picking_Candidate getRecordById(@NonNull final PickingCandidateId id)
	{
		final I_M_Picking_Candidate record = load(id, I_M_Picking_Candidate.class);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @M_Picking_Candidate_ID@: " + id);
		}
		return record;
	}

	private List<I_M_Picking_Candidate> getRecordsByIds(@NonNull final Set<PickingCandidateId> ids)
	{
		return loadByRepoIdAwares(ids, I_M_Picking_Candidate.class);
	}

	public void saveAll(@NonNull final Collection<PickingCandidate> candidates)
	{
		candidates.forEach(this::save);
	}

	public void save(@NonNull final PickingCandidate candidate)
	{
		final I_M_Picking_Candidate record;
		if (candidate.getId() == null)
		{
			record = newInstance(I_M_Picking_Candidate.class);
		}
		else
		{
			record = getRecordById(candidate.getId());
		}

		updateRecord(record, candidate);
		saveRecord(record);
		candidate.markSaved(PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()));

		saveIssuesToBOMLine(candidate.getId(), candidate.getIssuesToPickingOrder());
	}

	private PickingCandidate toPickingCandidateAndLoadIssuesToBOMLine(@NonNull final I_M_Picking_Candidate record)
	{
		return loadIssuesToBOMLine(toPickingCandidate(record));
	}

	/**
	 * @implNote keep in sync with {@link #updateRecord(I_M_Picking_Candidate, PickingCandidate)}
	 */
	private PickingCandidate toPickingCandidate(@NonNull final I_M_Picking_Candidate record)
	{
		final I_C_UOM uom = uomsRepo.getById(record.getC_UOM_ID());
		final Quantity qtyPicked = Quantity.of(record.getQtyPicked(), uom);

		final BigDecimal qtyReview = !isNull(record, I_M_Picking_Candidate.COLUMNNAME_QtyReview) ? record.getQtyReview() : null;

		return PickingCandidate.builder()
				.id(PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()))
				//
				.processingStatus(PickingCandidateStatus.ofCode(record.getStatus()))
				.pickStatus(PickingCandidatePickStatus.ofCode(record.getPickStatus()))
				.approvalStatus(PickingCandidateApprovalStatus.ofCode(record.getApprovalStatus()))
				//
				.pickFrom(PickFrom.builder()
								  .huId(HuId.ofRepoIdOrNull(record.getPickFrom_HU_ID()))
								  .pickingOrderId(PPOrderId.ofRepoIdOrNull(record.getPickFrom_Order_ID()))
								  .build())
				//
				.qtyPicked(qtyPicked)
				.qtyReview(qtyReview)
				.qtyRejected(extractQtyRejected(record, uom))
				//
				.packToSpec(extractPackToSpecOrNull(record))
				.packedToHuId(HuId.ofRepoIdOrNull(record.getM_HU_ID()))
				//
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.pickingSlotId(PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID()))
				//
				.build();
	}

	@Nullable
	private static PackToSpec extractPackToSpecOrNull(final @NonNull I_M_Picking_Candidate record)
	{
		final HUPIItemProductId tuPackingInstructionsId = HUPIItemProductId.ofRepoIdOrNull(record.getPackTo_HU_PI_Item_Product_ID());
		if (tuPackingInstructionsId != null)
		{
			return PackToSpec.ofTUPackingInstructionsId(tuPackingInstructionsId);
		}

		final HuPackingInstructionsId genericPackingInstructionsId = HuPackingInstructionsId.ofRepoIdOrNull(record.getPackTo_HU_PI_ID());
		if (genericPackingInstructionsId != null)
		{
			return PackToSpec.ofGenericPackingInstructionsId(genericPackingInstructionsId);
		}

		return null;
	}

	@Nullable
	private static QtyRejectedWithReason extractQtyRejected(
			final @NonNull I_M_Picking_Candidate record,
			final @NonNull I_C_UOM uom)
	{
		final BigDecimal qtyReject = record.getQtyReject();
		if (qtyReject.signum() <= 0)
		{
			return null;
		}

		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofNullableCode(record.getRejectReason())
				.orElseThrow(() -> new AdempiereException("Reject reason must be set when QtyReject > 0"));

		return QtyRejectedWithReason.of(Quantity.of(qtyReject, uom), reasonCode);
	}

	/**
	 * @implNote keep in sync with {@link #toPickingCandidate(I_M_Picking_Candidate)}
	 */
	private static void updateRecord(final I_M_Picking_Candidate record, final PickingCandidate from)
	{
		record.setStatus(from.getProcessingStatus().getCode());
		record.setPickStatus(from.getPickStatus().getCode());
		record.setApprovalStatus(from.getApprovalStatus().getCode());

		record.setPickFrom_HU_ID(HuId.toRepoId(from.getPickFrom().getHuId()));
		record.setPickFrom_Order_ID(PPOrderId.toRepoId(from.getPickFrom().getPickingOrderId()));

		record.setQtyPicked(from.getQtyPicked().toBigDecimal());
		record.setC_UOM_ID(from.getQtyPicked().getUomId().getRepoId());
		record.setQtyReview(from.getQtyReview());

		if (from.getQtyRejected() != null)
		{
			record.setQtyReject(from.getQtyRejected().toBigDecimal());
			record.setRejectReason(from.getQtyRejected().getReasonCode().getCode());
		}

		final Optional<PackToSpec> packToSpec = Optional.ofNullable(from.getPackToSpec());
		record.setPackTo_HU_PI_ID(packToSpec.map(PackToSpec::getGenericPackingInstructionsId).map(HuPackingInstructionsId::toRepoId).orElse(-1));
		record.setPackTo_HU_PI_Item_Product_ID(packToSpec.map(PackToSpec::getTuPackingInstructionsId).map(HUPIItemProductId::toRepoId).orElse(-1));

		record.setM_HU_ID(HuId.toRepoId(from.getPackedToHuId()));

		record.setM_ShipmentSchedule_ID(from.getShipmentScheduleId().getRepoId());
		record.setM_PickingSlot_ID(PickingSlotId.toRepoId(from.getPickingSlotId()));
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsByPickingCandidateIds(final Collection<PickingCandidateId> pickingCandidateIds)
	{
		if (pickingCandidateIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_Picking_Candidate_ID, pickingCandidateIds)
				.addNotNull(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID)
				.create()
				.listDistinct(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, Integer.class)
				.stream()
				.map(ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public List<PickingCandidate> getByHUIds(@NonNull final Collection<HuId> huIds)
	{
		// tolerate empty
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return retrievePickingCandidatesByHUIdsQuery(huIds)
				.create()
				.stream()
				.collect(toPickingCandidatesList());
	}

	private IQueryBuilder<I_M_Picking_Candidate> retrievePickingCandidatesByHUIdsQuery(@NonNull final Collection<HuId> huIds)
	{
		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter();

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, huIds)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huIds); // pack HUs

		return queryBuilder;
	}

	public ImmutableList<PickingCandidate> getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return getByShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId));
	}

	public ImmutableList<PickingCandidate> getByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.orderBy(I_M_Picking_Candidate.COLUMN_M_Picking_Candidate_ID) // just to have a predictable order
				.create()
				.stream()
				.collect(toPickingCandidatesList());
	}

	public Optional<PickingCandidate> getByShipmentScheduleIdAndHuIdAndPickingSlotId(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final HuId huId,
			@Nullable final PickingSlotId pickingSlotId)
	{
		final I_M_Picking_Candidate existingRecord = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);
		if (existingRecord == null)
		{
			return Optional.empty();
		}

		final PickingCandidate pickingCandidate = toPickingCandidateAndLoadIssuesToBOMLine(existingRecord);
		return Optional.of(pickingCandidate);
	}

	public void deletePickingCandidates(@NonNull final Collection<PickingCandidate> candidates)
	{
		if (candidates.isEmpty())
		{
			return;
		}

		final Set<PickingCandidateId> pickingCandidateIds = candidates.stream()
				.map(PickingCandidate::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (pickingCandidateIds.isEmpty())
		{
			return;
		}

		deleteIssuesToBOMLine(pickingCandidateIds);

		final List<I_M_Picking_Candidate> records = loadByRepoIdAwares(pickingCandidateIds, I_M_Picking_Candidate.class);
		InterfaceWrapperHelper.deleteAll(records);
	}

	public List<PickingCandidate> getByShipmentScheduleIdAndStatus(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final PickingCandidateStatus status)
	{
		return getByShipmentScheduleIdsAndStatus(ImmutableSet.of(shipmentScheduleId), status);
	}

	public List<PickingCandidate> getByShipmentScheduleIdsAndStatus(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final PickingCandidateStatus status)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, status.getCode())
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream(I_M_Picking_Candidate.class)
				.collect(toPickingCandidatesList());
	}

	public boolean existsPickingCandidates(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return false;
		}

		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.anyMatch();

	}

	public boolean hasDraftCandidatesForPickingSlot(final PickingSlotId pickingSlotId)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, PickingCandidateStatus.Draft)
				.create()
				.anyMatch();
	}

	public void inactivateForHUIds(@NonNull final Collection<HuId> huIds)
	{
		Check.assumeNotEmpty(huIds, "huIds is not empty");

		retrievePickingCandidatesByHUIdsQuery(huIds)
				.create()
				.update(record -> {
					markAsInactiveNoSave(record);
					return IQueryUpdater.MODEL_UPDATED;

				});
	}

	private void markAsInactiveNoSave(final I_M_Picking_Candidate record)
	{
		record.setIsActive(false);
		record.setStatus(PickingCandidateStatus.Closed.getCode());
	}

	public TableRecordReference toTableRecordReference(@NonNull final PickingCandidate candidate)
	{
		final PickingCandidateId id = candidate.getId();
		if (id == null)
		{
			throw new AdempiereException("Candidate is not saved: " + candidate);
		}
		return toTableRecordReference(id);
	}

	public TableRecordReference toTableRecordReference(@NonNull final PickingCandidateId id)
	{
		return TableRecordReference.of(I_M_Picking_Candidate.Table_Name, id.getRepoId());
	}

	public List<PickingCandidate> query(@NonNull final PickingCandidatesQuery pickingCandidatesQuery)
	{
		// configure the query builder
		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter();

		//
		// Shipment schedules
		if (!Check.isEmpty(pickingCandidatesQuery.getShipmentScheduleIds()))
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, pickingCandidatesQuery.getShipmentScheduleIds());
		}

		//
		// Not Closed + Not Rack System Picking slots
		if (pickingCandidatesQuery.isOnlyNotClosedOrNotRackSystem())
		{
			final IHUPickingSlotDAO huPickingSlotsRepo = Services.get(IHUPickingSlotDAO.class);
			final Set<PickingSlotId> rackSystemPickingSlotIds = huPickingSlotsRepo.retrieveAllPickingSlotIdsWhichAreRackSystems();
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addNotEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, PickingCandidateStatus.Closed.getCode())
					.addNotInArrayFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, rackSystemPickingSlotIds);
		}

		//
		// Only Picking Slots
		if (!pickingCandidatesQuery.getOnlyPickingSlotIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingCandidatesQuery.getOnlyPickingSlotIds());
		}

		//
		// Picking slot Barcode filter
		final PickingSlotQRCode pickingSlotQRCode = pickingCandidatesQuery.getPickingSlotQRCode();
		if (pickingSlotQRCode != null)
		{
			final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
			final Set<PickingSlotId> pickingSlotIds = pickingSlotDAO.retrievePickingSlotIds(PickingSlotQuery.builder()
																									.qrCode(pickingSlotQRCode)
																									.build());
			if (pickingSlotIds.isEmpty())
			{
				return ImmutableList.of();
			}

			queryBuilder.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotIds);
		}

		//
		// HU filter: not already shipped
		if (!pickingCandidatesQuery.isIncludeShippedHUs())
		{
			final IQuery<I_M_HU> husQuery = queryBL.createQueryBuilder(I_M_HU.class)
					.addNotEqualsFilter(I_M_HU.COLUMNNAME_HUStatus, X_M_HU.HUSTATUS_Shipped) // not already shipped (https://github.com/metasfresh/metasfresh-webui-api/issues/647)
					.create();

			// PickFrom HU
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, null)
					.addInSubQueryFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, I_M_HU.COLUMN_M_HU_ID, husQuery);

			// Picked HU
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, null)
					.addInSubQueryFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, I_M_HU.COLUMN_M_HU_ID, husQuery);
		}

		//
		// Execute query & Fetch picking candidates
		return queryBuilder
				.orderBy(I_M_Picking_Candidate.COLUMNNAME_M_Picking_Candidate_ID)
				.create()
				.stream()
				.collect(toPickingCandidatesList());
	}

	/**
	 * @return {@code true} if the given HU is referenced by an active picking candidate.
	 */
	public boolean isHuIdPicked(@NonNull final HuId huId)
	{
		return queryBL
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.anyMatch();
	}

	@NonNull
	public ImmutableList<PickingCandidate> getDraftedByHuIdAndPickingSlotId(
			@Nullable final HuId huId,
			@Nullable final PickingSlotId pickingSlotId)
	{
		Check.assume(huId != null || pickingSlotId != null, "At least one of HuId and pickingSlotId must be set!");

		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, PickingCandidateStatus.Draft);

		if (huId != null)
		{
			queryBuilder.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId);
		}
		if (pickingSlotId != null)
		{
			queryBuilder.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId);
		}

		return queryBuilder.create()
				.stream()
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());
	}

	private Collector<I_M_Picking_Candidate, ?, ImmutableList<PickingCandidate>> toPickingCandidatesList()
	{
		final Supplier<ArrayList<PickingCandidate>> supplier = ArrayList::new;
		final BiConsumer<ArrayList<PickingCandidate>, I_M_Picking_Candidate> accumulator = (list, record) -> list.add(toPickingCandidate(record));
		final BinaryOperator<ArrayList<PickingCandidate>> combiner = (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
		final Function<ArrayList<PickingCandidate>, ImmutableList<PickingCandidate>> finisher = pickingCandidates -> loadIssuesToBOMLine(ImmutableList.copyOf(pickingCandidates));

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private PickingCandidate loadIssuesToBOMLine(@NonNull final PickingCandidate pickingCandidate)
	{
		return loadIssuesToBOMLine(ImmutableList.of(pickingCandidate)).get(0);
	}

	private ImmutableList<PickingCandidate> loadIssuesToBOMLine(@NonNull final ImmutableList<PickingCandidate> pickingCandidates)
	{
		if (pickingCandidates.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<PickingCandidateId> pickingCandidateIds = pickingCandidates.stream()
				.filter(PickingCandidate::isPickFromPickingOrder)
				.map(PickingCandidate::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
		if (pickingCandidateIds.isEmpty())
		{
			return pickingCandidates;
		}

		final ImmutableListMultimap<PickingCandidateId, PickingCandidateIssueToBOMLine> issuesToPickingOrderMap = retrieveIssuesToOrder(pickingCandidateIds);
		if (issuesToPickingOrderMap.isEmpty())
		{
			return pickingCandidates;
		}

		return pickingCandidates.stream()
				.map(pickingCandidate -> loadIssuesToBOMLine(pickingCandidate, issuesToPickingOrderMap))
				.collect(ImmutableList.toImmutableList());
	}

	private static PickingCandidate loadIssuesToBOMLine(
			@NonNull final PickingCandidate pickingCandidate,
			@NonNull final ImmutableListMultimap<PickingCandidateId, PickingCandidateIssueToBOMLine> issuesToPickingOrderMap)
	{
		final PickingCandidateId pickingCandidateId = pickingCandidate.getId();
		final ImmutableList<PickingCandidateIssueToBOMLine> issuesToPickingOrder = pickingCandidateId != null
				? issuesToPickingOrderMap.get(pickingCandidateId)
				: ImmutableList.of();

		return pickingCandidate.toBuilder()
				.issuesToPickingOrder(issuesToPickingOrder)
				.build();
	}

	private ImmutableListMultimap<PickingCandidateId, PickingCandidateIssueToBOMLine> retrieveIssuesToOrder(final Collection<PickingCandidateId> pickingCandidateIds)
	{
		Check.assumeNotEmpty(pickingCandidateIds, "pickingCandidateIds is not empty");
		return queryBL.createQueryBuilder(I_M_Picking_Candidate_IssueToOrder.class)
				.addInArrayFilter(I_M_Picking_Candidate_IssueToOrder.COLUMNNAME_M_Picking_Candidate_ID, pickingCandidateIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()),
						this::toPickingCandidateIssueToBOMLine));
	}

	private PickingCandidateIssueToBOMLine toPickingCandidateIssueToBOMLine(final I_M_Picking_Candidate_IssueToOrder record)
	{
		final I_C_UOM uom = uomsRepo.getById(record.getC_UOM_ID());

		return PickingCandidateIssueToBOMLine.builder()
				.issueToOrderBOMLineId(PPOrderBOMLineId.ofRepoId(record.getPP_Order_BOMLine_ID()))
				.issueFromHUId(HuId.ofRepoId(record.getM_HU_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyToIssue(Quantity.of(record.getQtyToIssue(), uom))
				.build();
	}

	private void saveIssuesToBOMLine(
			@NonNull final PickingCandidateId pickingCandidateId,
			@NonNull final ImmutableList<PickingCandidateIssueToBOMLine> issuesToPickingOrder)
	{
		final HashMap<PickingCandidateIssueToBOMLineKey, I_M_Picking_Candidate_IssueToOrder> existingRecordsByKey = streamIssuesToBOMLineRecords(pickingCandidateId)
				.collect(GuavaCollectors.toHashMapByKey(PickingCandidateIssueToBOMLineKey::of));

		for (final PickingCandidateIssueToBOMLine issue : issuesToPickingOrder)
		{
			final PickingCandidateIssueToBOMLineKey key = PickingCandidateIssueToBOMLineKey.of(issue);
			final I_M_Picking_Candidate_IssueToOrder existingRecord = existingRecordsByKey.remove(key);

			final I_M_Picking_Candidate_IssueToOrder record;
			if (existingRecord != null)
			{
				record = existingRecord;
			}
			else
			{
				record = newInstance(I_M_Picking_Candidate_IssueToOrder.class);
			}

			record.setIsActive(true);
			record.setM_Picking_Candidate_ID(pickingCandidateId.getRepoId());
			record.setPP_Order_BOMLine_ID(issue.getIssueToOrderBOMLineId().getRepoId());
			record.setM_HU_ID(issue.getIssueFromHUId().getRepoId());
			record.setM_Product_ID(issue.getProductId().getRepoId());
			record.setQtyToIssue(issue.getQtyToIssue().toBigDecimal());
			record.setC_UOM_ID(issue.getQtyToIssue().getUomId().getRepoId());
			saveRecord(record);
		}

		deleteAll(existingRecordsByKey.values());
	}

	private Stream<I_M_Picking_Candidate_IssueToOrder> streamIssuesToBOMLineRecords(final PickingCandidateId pickingCandidateId)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Candidate_IssueToOrder.class)
				.addEqualsFilter(I_M_Picking_Candidate_IssueToOrder.COLUMNNAME_M_Picking_Candidate_ID, pickingCandidateId)
				.create()
				.stream();
	}

	private void deleteIssuesToBOMLine(@NonNull final Collection<PickingCandidateId> pickingCandidateIds)
	{
		if (pickingCandidateIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_Picking_Candidate_IssueToOrder.class)
				.addInArrayFilter(I_M_Picking_Candidate_IssueToOrder.COLUMNNAME_M_Picking_Candidate_ID, pickingCandidateIds)
				.create()
				.delete();
	}
}
