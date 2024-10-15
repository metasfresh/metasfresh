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

package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternativeId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public class PickingJobSaver
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);

	protected final HashMap<PickingJobId, I_M_Picking_Job> pickingJobs = new HashMap<>();
	protected final ArrayListMultimap<PickingJobId, I_M_Picking_Job_HUAlternative> pickingJobHUAlternatives = ArrayListMultimap.create();
	protected final ArrayListMultimap<PickingJobId, I_M_Picking_Job_Line> pickingJobLines = ArrayListMultimap.create();
	protected final ArrayListMultimap<PickingJobLineId, I_M_Picking_Job_Step> pickingJobSteps = ArrayListMultimap.create();
	protected final ArrayListMultimap<PickingJobStepId, I_M_Picking_Job_Step_HUAlternative> pickingJobStepAlternatives = ArrayListMultimap.create();
	protected final ArrayListMultimap<PickingJobStepId, I_M_Picking_Job_Step_PickedHU> pickedHUs = ArrayListMultimap.create();

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

		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		saveLines(pickingJob.getLines(), pickingJobId, orgId, pickingJob.getDocStatus());
	}

	protected void loadRecordsFromDB(final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return;
		}

		final List<I_M_Picking_Job> records = InterfaceWrapperHelper.loadByRepoIdAwares(pickingJobIds, I_M_Picking_Job.class);

		records.forEach(record -> pickingJobs.put(PickingJobId.ofRepoId(record.getM_Picking_Job_ID()), record));

		queryBL.createQueryBuilder(I_M_Picking_Job_HUAlternative.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_HUAlternative.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(alt -> pickingJobHUAlternatives.put(PickingJobId.ofRepoId(alt.getM_Picking_Job_ID()), alt));

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
				.forEach(step -> pickingJobSteps.put(PickingJobLineId.ofRepoId(step.getM_Picking_Job_Line_ID()), step));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step_HUAlternative.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step_HUAlternative.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(stepAlt -> pickingJobStepAlternatives.put(extractPickingJobStepId(stepAlt), stepAlt));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step_PickedHU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step_PickedHU.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(pickedHU -> pickedHUs.put(PickingJobStepId.ofRepoId(pickedHU.getM_Picking_Job_Step_ID()), pickedHU));
	}

	private void saveLines(
			final ImmutableList<PickingJobLine> lines,
			final PickingJobId pickingJobId,
			final OrgId orgId,
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

			saveSteps(line.getSteps(), pickingJobId, line.getId(), orgId, docStatus);
		}
	}

	private void saveSteps(
			final ImmutableList<PickingJobStep> steps,
			final PickingJobId pickingJobId,
			final PickingJobLineId pickingJobLineId,
			final OrgId orgId,
			final PickingJobDocStatus docStatus)
	{
		final HashMap<PickingJobStepId, I_M_Picking_Job_Step> existingRecords = pickingJobSteps.get(pickingJobLineId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(step -> PickingJobStepId.ofRepoId(step.getM_Picking_Job_Step_ID())));

		for (final PickingJobStep step : steps)
		{
			final PickingJobStepPickFrom mainPickFrom = step.getPickFrom(PickingJobStepPickFromKey.MAIN);

			I_M_Picking_Job_Step stepRecord = existingRecords.remove(step.getId());
			if (stepRecord == null)
			{
				stepRecord = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
				stepRecord.setM_Picking_Job_Step_ID(step.getId().getRepoId());
				stepRecord.setM_Picking_Job_ID(pickingJobId.getRepoId());
				stepRecord.setM_Picking_Job_Line_ID(pickingJobLineId.getRepoId());
				stepRecord.setIsDynamic(step.isGeneratedOnFly());
				stepRecord.setAD_Org_ID(orgId.getRepoId());
				stepRecord.setM_ShipmentSchedule_ID(step.getShipmentScheduleId().getRepoId());
				stepRecord.setC_Order_ID(step.getSalesOrderAndLineId().getOrderRepoId());
				stepRecord.setC_OrderLine_ID(step.getSalesOrderAndLineId().getOrderLineRepoId());

				//
				// What?
				stepRecord.setM_Product_ID(step.getProductId().getRepoId());
				stepRecord.setC_UOM_ID(step.getQtyToPick().getUomId().getRepoId());
				stepRecord.setQtyToPick(step.getQtyToPick().toBigDecimal());
			}

			updateRecord(stepRecord, step, docStatus);
			InterfaceWrapperHelper.save(stepRecord);

			saveStepPickFromAlternatives(step, pickingJobId);

			saveStepPickedTo(
					mainPickFrom.getPickedTo(),
					pickingJobId,
					step.getId(),
					null);
		}

		deleteStepsCascade(existingRecords.values());
	}

	private void deleteStepsCascade(final Collection<I_M_Picking_Job_Step> steps)
	{
		if (steps.isEmpty())
		{
			return;
		}

		final ImmutableSet<PickingJobStepId> pickingJobStepIds = steps.stream().map(step -> PickingJobStepId.ofRepoId(step.getM_Picking_Job_Step_ID())).collect(ImmutableSet.toImmutableSet());

		pickingJobStepIds.forEach(pickingJobStepId -> {
			InterfaceWrapperHelper.deleteAll(pickingJobStepAlternatives.removeAll(pickingJobStepId));
			InterfaceWrapperHelper.deleteAll(pickedHUs.removeAll(pickingJobStepId));
		});

		InterfaceWrapperHelper.deleteAll(steps);
	}

	private void saveStepPickFromAlternatives(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobId pickingJobId)
	{
		final ImmutableMap<PickingJobStepPickFromKey, I_M_Picking_Job_Step_HUAlternative> records = Maps.uniqueIndex(
				pickingJobStepAlternatives.get(step.getId()),
				record -> PickingJobStepPickFromKey.alternative(extractAlternativeId(record)));

		for (final PickingJobStepPickFromKey pickFromKey : step.getPickFromKeys())
		{
			if (pickFromKey.isAlternative())
			{
				final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
				final I_M_Picking_Job_Step_HUAlternative record = Objects.requireNonNull(records.get(pickFromKey));

				updateRecord(record, pickFrom);
				InterfaceWrapperHelper.save(record);

				saveStepPickedTo(
						pickFrom.getPickedTo(),
						pickingJobId,
						step.getId(),
						pickFromKey.getAlternativeId());
			}
		}
	}

	private void saveStepPickedTo(
			@Nullable final PickingJobStepPickedTo pickedTo,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobStepId pickingStepId,
			@Nullable final PickingJobPickFromAlternativeId alternativeId)
	{
		final BiFunction<HuId, String, String> huIdAndQrCode2StringKey = (huId, qrCode) -> {
			if (Check.isBlank(qrCode))
			{
				return huId.getRepoId() + "_" + UIDStringUtil.createRandomUUID();
			}
			return huId.getRepoId() + "_" + qrCode;
		};

		final HashMap<String, I_M_Picking_Job_Step_PickedHU> existingRecordsByPickedHUId = pickedHUs.get(pickingStepId)
				.stream()
				.filter(record -> PickingJobPickFromAlternativeId.equals(extractAlternativeId(record), alternativeId))
				.collect(GuavaCollectors.toHashMapByKey(record -> huIdAndQrCode2StringKey.apply(HuId.ofRepoId(record.getPicked_HU_ID()),
																								record.getPicked_RenderedQRCode())));

		if (pickedTo != null)
		{
			for (final PickingJobStepPickedToHU pickedHU : pickedTo.getActualPickedHUs())
			{
				final HUInfo actualPickedHU = pickedHU.getActualPickedHU();
				final String huIdAndQrCodeKey = huIdAndQrCode2StringKey.apply(
						actualPickedHU.getId(),
						actualPickedHU.getQrCode().toGlobalQRCodeString());
				I_M_Picking_Job_Step_PickedHU record = existingRecordsByPickedHUId.remove(huIdAndQrCodeKey);
				if (record == null)
				{
					record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step_PickedHU.class);
					record.setM_Picking_Job_ID(pickingJobId.getRepoId());
					record.setM_Picking_Job_Step_ID(pickingStepId.getRepoId());
					record.setM_Picking_Job_HUAlternative_ID(PickingJobPickFromAlternativeId.toRepoId(alternativeId));
				}

				record.setPickFrom_HU_ID(pickedHU.getPickFromHUId().getRepoId());
				record.setPicked_HU_ID(actualPickedHU.getId().getRepoId());
				record.setPicked_RenderedQRCode(actualPickedHU.getQrCode().toGlobalQRCodeString());
				record.setC_UOM_ID(pickedHU.getQtyPicked().getUomId().getRepoId());
				record.setQtyPicked(pickedHU.getQtyPicked().toBigDecimal());
				record.setCatchWeight(pickedHU.getCatchWeight() != null ? pickedHU.getCatchWeight().toBigDecimal() : null);
				record.setCatch_UOM_ID(pickedHU.getCatchWeight() != null ? pickedHU.getCatchWeight().getUomId().getRepoId() : -1);
				InterfaceWrapperHelper.save(record);
			}
		}

		InterfaceWrapperHelper.deleteAll(existingRecordsByPickedHUId.values());
	}

	@NonNull
	protected static PickingJobStepId extractPickingJobStepId(final I_M_Picking_Job_Step_HUAlternative record)
	{
		return PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID());
	}

	@NonNull
	protected static PickingJobPickFromAlternativeId extractAlternativeId(final I_M_Picking_Job_Step_HUAlternative record)
	{
		return PickingJobPickFromAlternativeId.ofRepoId(record.getM_Picking_Job_HUAlternative_ID());
	}

	@Nullable
	protected static PickingJobPickFromAlternativeId extractAlternativeId(final I_M_Picking_Job_Step_PickedHU record)
	{
		return PickingJobPickFromAlternativeId.ofRepoIdOrNull(record.getM_Picking_Job_HUAlternative_ID());
	}

	private static void updateRecord(final I_M_Picking_Job record, final PickingJob from)
	{
		record.setPicking_User_ID(UserId.toRepoId(from.getLockedBy()));
		record.setM_PickingSlot_ID(from.getPickingSlotId().map(PickingSlotId::getRepoId).orElse(-1));

		final LUPickingTarget pickTarget = from.getLuPickTarget().orElse(null);
		record.setM_LU_HU_PI_ID(HuPackingInstructionsId.toRepoId(pickTarget != null ? pickTarget.getLuPIId() : null));
		record.setM_LU_HU_ID(HuId.toRepoId(pickTarget != null ? pickTarget.getLuId() : null));

		final TUPickingTarget tuPickingTarget = from.getTuPickTarget().orElse(null);
		record.setM_TU_HU_PI_ID(HuPackingInstructionsId.toRepoId(tuPickingTarget != null ? tuPickingTarget.getTuPIId() : null));

		record.setDocStatus(from.getDocStatus().getCode());
		record.setProcessed(from.getDocStatus().isProcessed());
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Line record,
			@NonNull final PickingJobLine line,
			@NonNull final PickingJobDocStatus docStatus)
	{
		final boolean isManuallyClosed = line.isManuallyClosed();
		record.setIsManuallyClosed(isManuallyClosed);

		record.setProcessed(isManuallyClosed || docStatus.isProcessed());
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@NonNull final PickingJobStep from,
			@NonNull final PickingJobDocStatus docStatus)
	{

		record.setProcessed(docStatus.isProcessed());

		updateRecord(record, from.getPickFrom(PickingJobStepPickFromKey.MAIN));
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@NonNull final PickingJobStepPickFrom mainPickFrom)
	{
		record.setPickFrom_Warehouse_ID(mainPickFrom.getPickFromWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(mainPickFrom.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(mainPickFrom.getPickFromHUId().getRepoId());
		updateRecord(record, mainPickFrom.getPickedTo());
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@Nullable final PickingJobStepPickedTo pickedTo)
	{
		final BigDecimal qtyRejectedBD;
		final String rejectReason;
		if (pickedTo != null)
		{
			qtyRejectedBD = pickedTo.getQtyRejected() != null ? pickedTo.getQtyRejected().toBigDecimal() : BigDecimal.ZERO;
			rejectReason = pickedTo.getQtyRejected() != null ? pickedTo.getQtyRejected().getReasonCode().getCode() : null;
		}
		else
		{
			qtyRejectedBD = BigDecimal.ZERO;
			rejectReason = null;
		}

		record.setQtyRejectedToPick(qtyRejectedBD);
		record.setRejectReason(rejectReason);
	}

	private static void updateRecord(final I_M_Picking_Job_Step_HUAlternative existingRecord, final PickingJobStepPickFrom pickFrom)
	{
		existingRecord.setPickFrom_HU_ID(pickFrom.getPickFromHUId().getRepoId());
		updateRecord(existingRecord, pickFrom.getPickedTo());
	}

	private static void updateRecord(final I_M_Picking_Job_Step_HUAlternative existingRecord, final PickingJobStepPickedTo pickedTo)
	{
		final UomId uomId;
		final BigDecimal qtyRejectedBD;
		final String rejectReason;
		if (pickedTo != null && pickedTo.getQtyRejected() != null)
		{
			final QtyRejectedWithReason qtyRejected = pickedTo.getQtyRejected();
			uomId = qtyRejected.toQuantity().getUomId();
			qtyRejectedBD = qtyRejected.toBigDecimal();
			rejectReason = qtyRejected.getReasonCode().getCode();
		}
		else
		{
			uomId = null;
			qtyRejectedBD = BigDecimal.ZERO;
			rejectReason = null;
		}

		existingRecord.setC_UOM_ID(UomId.toRepoId(uomId));
		existingRecord.setQtyRejectedToPick(qtyRejectedBD);
		existingRecord.setRejectReason(rejectReason);
	}
}
