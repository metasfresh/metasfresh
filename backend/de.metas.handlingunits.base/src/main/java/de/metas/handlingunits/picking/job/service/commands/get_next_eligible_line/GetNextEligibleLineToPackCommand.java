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
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.inout.ShipmentScheduleId;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class GetNextEligibleLineToPackCommand
{
	// services
	@NonNull private static final Logger logger = LogManager.getLogger(GetNextEligibleLineToPackCommand.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull final ShipmentScheduleInfoLoadingCache shipmentSchedules;

	// params
	@NonNull private final GetNextEligibleLineToPackRequest request;

	// state
	@Nullable private IHUQRCode _huQRCode;
	@Nullable private PickingJob _job;
	@NonNull private final HashMap<PickingJobLineId, ExplainedOptional<HUInfo>> resolvedHUInfos = new HashMap<>();
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
		final IHUQRCode huQRCode = getHUQRCode();
		log("Parsed QR code: " + huQRCode.getAsString());

		@NonNull final PickingJob job = getJob();
		job.assertCanBeEditedBy(request.getCallerId());

		if (!job.isAllowPickingAnyHU())
		{
			log("Job does not allow picking any HU");
			return notFound();
		}

		shipmentSchedules.warmUpByIds(job.getScheduleIds().getShipmentScheduleIds());

		final ArrayList<PickingJobLine> lines = new ArrayList<>(job.getLines());

		//
		// First, try matching not fully picked lines
		GetNextEligibleLineToPackResponse response = resolveOverLines(
				lines,
				line -> BooleanWithReason.falseIf(line.isFullyPicked(), "fully picked")
		);
		if (response.isFound())
		{
			return response;
		}

		//
		// Next, try matching not fully picked lines (excluding rejected qty)
		log("No line found that is not fully picked. Trying to find one that is not fully picked (excluding rejected qty)");
		response = resolveOverLines(
				lines,
				line -> BooleanWithReason.falseIf(line.isFullyPickedExcludingRejectedQty(), "fully picked (even when not considering rejected qty)")
		);

		return response;
	}

	private GetNextEligibleLineToPackResponse resolveOverLines(
			@NonNull final ArrayList<PickingJobLine> lines,
			@NonNull final Function<PickingJobLine, BooleanWithReason> eligiblePredicate)
	{
		for (final Iterator<PickingJobLine> it = lines.iterator(); it.hasNext(); )
		{
			final PickingJobLine line = it.next();
			if (request.getExcludeLineId() != null && PickingJobLineId.equals(request.getExcludeLineId(), line.getId()))
			{
				it.remove(); // remove it from the list because it will never be eligible
				log(line, "Skip because it is the one we are excluding");
				continue;
			}

			final BooleanWithReason eligible = eligiblePredicate.apply(line);
			if (eligible.isFalse())
			{
				log(line, "Skip because " + eligible.getReasonAsString());
				continue;
			}

			final ExplainedOptional<HUInfo> resolveResult = resolvePickFromHUQRCode(line);
			if (!resolveResult.isPresent())
			{
				log(line, "Skip because HU QR Code is not matching the line: " + resolveResult.getExplanationAsString());
				it.remove();
				continue;
			}

			return found(line, resolveResult.get());
		}

		return notFound();

	}

	private GetNextEligibleLineToPackResponse notFound()
	{
		return GetNextEligibleLineToPackResponse.builder()
				.lineId(null)
				.logs(logs)
				.build();
	}

	private GetNextEligibleLineToPackResponse found(PickingJobLine line, HUInfo huInfo)
	{
		return GetNextEligibleLineToPackResponse.builder()
				.lineId(line.getId())
				.huInfo(huInfo)
				.logs(logs)
				.build();
	}

	@NonNull
	private PickingJob getJob()
	{
		if (_job == null)
		{
			_job = pickingJobService.getById(request.getPickingJobId());
		}
		return _job;
	}

	private IHUQRCode getHUQRCode()
	{
		if (_huQRCode == null)
		{
			_huQRCode = huService.parsePickFromScannedCode(request.getHuScannedCode());
			log("Parsed QR code: " + _huQRCode.getAsString());
		}
		return _huQRCode;
	}

	private ExplainedOptional<HUInfo> resolvePickFromHUQRCode(@NonNull final PickingJobLine line)
	{
		return resolvedHUInfos.computeIfAbsent(
				line.getId(),
				k -> huService.resolvePickFromHUQRCode(
						getHUQRCode(),
						line.getProductId(),
						getCustomerId(line),
						getWarehouseId(line)
				));
	}

	@NonNull
	private BPartnerId getCustomerId(@NonNull final PickingJobLine line)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				line::getCustomerId,
				() -> getJob().getCustomerId()
		);
	}

	@NonNull
	private WarehouseId getWarehouseId(@NonNull final PickingJobLine line)
	{
		final ShipmentScheduleId shipmentScheduleId = line.getScheduleId().getShipmentScheduleId();
		return shipmentSchedules.getById(shipmentScheduleId).getWarehouseId();
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
