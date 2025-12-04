package de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.ShipmentScheduleInfoLoadingCache;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.inout.ShipmentScheduleId;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class GetNextEligibleLineToPackCommand
{
	public final static AdMessageKey ERR_QR_ProductNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	// services
	@NonNull private static final Logger logger = LogManager.getLogger(GetNextEligibleLineToPackCommand.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull final ShipmentScheduleInfoLoadingCache shipmentSchedules;

	// params
	@NonNull private final GetNextEligibleLineToPackRequest request;

	// state
	@NonNull private final ArrayList<String> logs = new ArrayList<>();

	@Builder
	private GetNextEligibleLineToPackCommand(
			@NonNull final PickingJobService pickingJobService,
			@NonNull final PickingJobHUService huService,
			@NonNull final ShipmentScheduleInfoLoadingCache shipmentSchedules,
			@NonNull final GetNextEligibleLineToPackRequest request)
	{
		this.pickingJobService = pickingJobService;
		this.huService = huService;
		this.shipmentSchedules = shipmentSchedules;
		this.request = request;
	}

	public GetNextEligibleLineToPackResponse execute()
	{
		final IHUQRCode huQRCode = huService.parsePickFromScannedCode(request.getHuScannedCode());
		log("Parsed QR code: " + huQRCode.getAsString());

		@NonNull final PickingJob job = pickingJobService.getById(request.getPickingJobId());
		job.assertCanBeEditedBy(request.getCallerId());

		if (!job.isAllowPickingAnyHU())
		{
			log("Job does not allow picking any HU");
			return notFound();
		}

		shipmentSchedules.warmUpByIds(job.getScheduleIds().getShipmentScheduleIds());

		for (final PickingJobLine line : job.getLines())
		{
			if (line.isFullyPicked())
			{
				log(line, "Skip because fully picked");
				continue;
			}

			if (request.getExcludeLineId() != null && PickingJobLineId.equals(request.getExcludeLineId(), line.getId()))
			{
				log(line, "Skip because it is the one we are excluding");
				continue;
			}

			final ExplainedOptional<HUInfo> resolveResult = resolvePickFromHUQRCode(huQRCode, line, job);
			if (!resolveResult.isPresent())
			{
				log(line, "Skip because it cannot be picked from " + huQRCode + ": " + resolveResult.getExplanationAsString());
				continue;
			}

			return GetNextEligibleLineToPackResponse.builder()
					.lineId(line.getId())
					.huInfo(resolveResult.get())
					.build();
		}

		return notFound();
	}

	private GetNextEligibleLineToPackResponse notFound()
	{
		return GetNextEligibleLineToPackResponse.builder().logs(logs).build();
	}

	private ExplainedOptional<HUInfo> resolvePickFromHUQRCode(
			@NonNull final IHUQRCode huQRCode,
			@NonNull final PickingJobLine line,
			@NonNull final PickingJob job)
	{
		return huService.resolvePickFromHUQRCode(
				huQRCode,
				line.getProductId(),
				getCustomerId(job, line),
				getWarehouseId(line)
		);
	}

	@NonNull
	private WarehouseId getWarehouseId(final PickingJobLine line)
	{
		final ShipmentScheduleId shipmentScheduleId = line.getScheduleId().getShipmentScheduleId();
		return shipmentSchedules.getById(shipmentScheduleId).getWarehouseId();
	}

	@NonNull
	private static BPartnerId getCustomerId(@NonNull final PickingJob job, final @NonNull PickingJobLine line)
	{
		return CoalesceUtil.coalesceNotNull(line.getCustomerId(), job.getCustomerId());
	}

	private void log(@NonNull String message)
	{
		log(null, message);
	}

	private void log(@Nullable final PickingJobLine line, @NonNull String message)
	{
		final StringBuilder sb = new StringBuilder();
		if (line != null)
		{
			sb.append("Line: ").append(line.getId().getRepoId()).append(" - ");
		}
		sb.append(message);
		String messageFinal = sb.toString();

		logger.debug(messageFinal);
		logs.add(messageFinal);
	}
}
