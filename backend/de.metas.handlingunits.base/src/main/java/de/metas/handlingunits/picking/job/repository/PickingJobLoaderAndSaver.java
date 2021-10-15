package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobHeader;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class PickingJobLoaderAndSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PickingJobLoaderSupportingServices _loadingSupportingServices;

	private final HashMap<PickingJobId, I_M_Picking_Job> pickingJobs = new HashMap<>();
	private final ArrayListMultimap<PickingJobId, I_M_Picking_Job_Line> pickingJobLines = ArrayListMultimap.create();
	private final ArrayListMultimap<PickingJobLineId, I_M_Picking_Job_Step> pickingJobSteps = ArrayListMultimap.create();

	private PickingJobLoaderAndSaver(@Nullable final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		this._loadingSupportingServices = loadingSupportingServices;
	}

	public static PickingJobLoaderAndSaver forLoading(@NonNull final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		return new PickingJobLoaderAndSaver(loadingSupportingServices);
	}

	public static PickingJobLoaderAndSaver forSaving()
	{
		return new PickingJobLoaderAndSaver(null);
	}

	public PickingJob loadById(@NonNull final PickingJobId pickingJobId)
	{
		if (pickingJobs.get(pickingJobId) == null)
		{
			loadRecordsFromDB(ImmutableSet.of(pickingJobId));
		}

		return fromRecord(pickingJobs.get(pickingJobId));
	}

	public List<PickingJob> loadByIds(@NonNull final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		// IMPORTANT to take a snapshot of Sets.difference because that's a life view and we are gonna add data TO pickingJobS map...
		final ImmutableSet<PickingJobId> pickingJobIdsToLoad = ImmutableSet.copyOf(Sets.difference(pickingJobIds, pickingJobs.keySet()));
		if (!pickingJobIdsToLoad.isEmpty())
		{
			loadRecordsFromDB(pickingJobIdsToLoad);
		}

		return pickingJobIds.stream()
				.map(pickingJobs::get)
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());

	}

	public void save(@NonNull final PickingJob pickingJob)
	{
		final PickingJobId pickingJobId = pickingJob.getId();
		if (pickingJobs.get(pickingJobId) == null)
		{
			loadRecordsFromDB(ImmutableSet.of(pickingJobId));
		}

		final I_M_Picking_Job record = pickingJobs.get(pickingJobId);
		updateRecord(record, pickingJob);
		InterfaceWrapperHelper.save(record);

		saveLines(pickingJob.getLines(), pickingJobId, pickingJob.getDocStatus());
	}

	private void saveLines(
			final ImmutableList<PickingJobLine> lines,
			final PickingJobId pickingJobId,
			final PickingJobDocStatus docStatus)
	{
		final HashMap<PickingJobLineId, I_M_Picking_Job_Line> existingRecords = pickingJobLines.get(pickingJobId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(line -> PickingJobLineId.ofRepoId(line.getM_Picking_Job_Line_ID())));

		for (final PickingJobLine line : lines)
		{
			final I_M_Picking_Job_Line existingRecord = existingRecords.get(line.getId());
			Check.assumeNotNull(existingRecord, "line record shall exist for {}", line);

			// NOTE: atm we have nothing to sync on line level
			updateRecord(existingRecord, line, docStatus);
			InterfaceWrapperHelper.save(existingRecord);

			saveSteps(line.getSteps(), line.getId(), docStatus);
		}
	}

	private void saveSteps(
			final ImmutableList<PickingJobStep> steps,
			final PickingJobLineId pickingJobLineId,
			final PickingJobDocStatus docStatus)
	{
		final HashMap<PickingJobStepId, I_M_Picking_Job_Step> existingRecords = pickingJobSteps.get(pickingJobLineId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(step -> PickingJobStepId.ofRepoId(step.getM_Picking_Job_Step_ID())));

		for (final PickingJobStep step : steps)
		{
			final I_M_Picking_Job_Step existingRecord = existingRecords.get(step.getId());
			Check.assumeNotNull(existingRecord, "step record shall exist for {}", step);

			updateRecord(existingRecord, step, docStatus);
			InterfaceWrapperHelper.save(existingRecord);
		}
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		pickingJobs.put(pickingJobId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_Line record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		pickingJobLines.put(pickingJobId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_Step record)
	{
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());
		pickingJobSteps.put(pickingJobLineId, record);
	}

	private void loadRecordsFromDB(final ImmutableSet<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return;
		}

		final List<I_M_Picking_Job> records = InterfaceWrapperHelper.loadByRepoIdAwares(pickingJobIds, I_M_Picking_Job.class);

		records.forEach(record -> pickingJobs.put(PickingJobId.ofRepoId(record.getM_Picking_Job_ID()), record));

		queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(line -> pickingJobLines.put(PickingJobId.ofRepoId(line.getM_Picking_Job_ID()), line));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(step -> pickingJobSteps.put(
						PickingJobLineId.ofRepoId(step.getM_Picking_Job_Line_ID()),
						step));
	}

	private PickingJobLoaderSupportingServices loadingSupportingServices()
	{
		if (_loadingSupportingServices == null)
		{
			throw new AdempiereException("No loadingSupportingServices available when saving");
		}
		return _loadingSupportingServices;
	}

	private PickingJob fromRecord(final I_M_Picking_Job record)
	{
		final OrderId salesOrderId = OrderId.ofRepoId(record.getC_Order_ID());
		final BPartnerLocationId deliveryBPLocationId = BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID());
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID());
		final Optional<PickingSlotIdAndCaption> pickingSlot = Optional.ofNullable(pickingSlotId)
				.map(loadingSupportingServices()::getPickingSlotIdAndCaption);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());

		return PickingJob.builder()
				.id(pickingJobId)
				.header(PickingJobHeader.builder()
						.salesOrderDocumentNo(loadingSupportingServices().getSalesOrderDocumentNo(salesOrderId))
						.preparationDate(loadingSupportingServices().toZonedDateTime(record.getPreparationDate(), orgId))
						.customerName(loadingSupportingServices().getBPartnerName(deliveryBPLocationId.getBpartnerId()))
						.deliveryBPLocationId(deliveryBPLocationId)
						.deliveryRenderedAddress(record.getDeliveryToAddress())
						.lockedBy(UserId.ofRepoId(record.getPicking_User_ID()))
						.build())
				.pickingSlot(pickingSlot)
				.docStatus(PickingJobDocStatus.ofCode(record.getDocStatus()))
				.lines(pickingJobLines.get(pickingJobId)
						.stream()
						.map(this::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static void updateRecord(final I_M_Picking_Job record, final PickingJob from)
	{
		record.setM_PickingSlot_ID(from.getPickingSlotId().map(PickingSlotId::getRepoId).orElse(-1));
		record.setDocStatus(from.getDocStatus().getCode());
		record.setProcessed(from.getDocStatus().isProcessed());
	}

	private PickingJobLine fromRecord(@NonNull final I_M_Picking_Job_Line record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());

		return PickingJobLine.builder()
				.id(pickingJobLineId)
				.productId(productId)
				.productName(loadingSupportingServices().getProductName(productId))
				.steps(pickingJobSteps.get(pickingJobLineId)
						.stream()
						.map(this::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private void updateRecord(
			final I_M_Picking_Job_Line record,
			final PickingJobLine from,
			final PickingJobDocStatus docStatus)
	{
		record.setProcessed(docStatus.isProcessed());
	}

	private PickingJobStep fromRecord(@NonNull final I_M_Picking_Job_Step record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		final LocatorId locatorId = LocatorId.ofRepoId(record.getM_Warehouse_ID(), record.getM_Locator_ID());
		final HuId huId = HuId.ofRepoId(record.getM_HU_ID());

		return PickingJobStep.builder()
				.id(PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.pickingCandidateId(PickingCandidateId.ofRepoIdOrNull(record.getM_Picking_Candidate_ID()))
				//
				// What?
				.productId(productId)
				.productName(loadingSupportingServices().getProductName(productId))
				.qtyToPick(Quantitys.create(record.getQtyToPick(), uomId))
				.qtyPicked(Quantitys.create(record.getQtyPicked(), uomId))
				.qtyRejectedReasonCode(QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null))
				//
				// From where?
				.locatorId(locatorId)
				.locatorName(loadingSupportingServices().getLocatorName(locatorId))
				.huId(huId)
				.huBarcode(loadingSupportingServices().getHUBarcode(huId))
				//
				.build();
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step existingRecord,
			@NonNull final PickingJobStep from,
			@NonNull final PickingJobDocStatus docStatus)
	{
		existingRecord.setC_UOM_ID(from.getUOM().getC_UOM_ID());
		existingRecord.setQtyPicked(from.getQtyPicked().toBigDecimal());
		existingRecord.setQtyRejectedToPick(from.getQtyRejected().toBigDecimal());
		existingRecord.setRejectReason(from.getQtyRejectedReasonCode() != null
				? from.getQtyRejectedReasonCode().getCode()
				: null);
		existingRecord.setM_Picking_Candidate_ID(PickingCandidateId.toRepoId(from.getPickingCandidateId()));
		existingRecord.setProcessed(docStatus.isProcessed());
	}
}
