package de.metas.handlingunits.picking.job.service.commands.grai;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.DummyGRAIProvider;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.CUBookingSpecification;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

public class PickingJobGRAIValidator
{
	@NonNull private final PickingJobHUService huService;

	// Parameters
	@NonNull private final PickingJob pickingJob;

	// State
	@NonNull private final SalesOrderDummyGRAIProviders salesOrderDummyGRAIProviders;
	/** computed on first use: customers that do not require GRAIs never need it */
	@Nullable private ImmutableSet<HuId> _cusBookedWithLabelScan;
	@NonNull private final CustomerGRAIConfigProvider customerGRAIConfigProvider;

	@Builder
	private PickingJobGRAIValidator(
			@NonNull final PickingJobHUService huService,
			@NonNull final PickingJobBPartnerService bpartnerService,
			@NonNull final PickingJob pickingJob)
	{
		this.huService = huService;
		this.pickingJob = pickingJob;

		this.salesOrderDummyGRAIProviders = SalesOrderDummyGRAIProviders.builder()
				.orderDAO(Services.get(IOrderDAO.class))
				.build();
		this.customerGRAIConfigProvider = CustomerGRAIConfigProvider.builder()
				.bpartnerService(bpartnerService)
				.build();
	}

	public void validate()
	{
		getPickedHUIdsBySegment().forEach(this::validate);
	}

	/**
	 * The picks that were booked with a label scan ({@link CUBookingSpecification#filterBookedWithLabelScan}):
	 * the check follows what was picked, not the customer's current mode.
	 * Only the picks of lines that require GRAIs are looked up: the lookup walks each CU up to its box.
	 */
	@NonNull
	private ImmutableSet<HuId> getCUsBookedWithLabelScan()
	{
		ImmutableSet<HuId> result = this._cusBookedWithLabelScan;
		if (result == null)
		{
			final ImmutableSet<HUInfo> pickedOfGraiLines = pickingJob.getPickedHUInfos(line -> !extractGRAIValidationSegment(line).getGraiRequired().isNo());
			result = this._cusBookedWithLabelScan = huService.getCUBookingSpecification().filterBookedWithLabelScan(pickedOfGraiLines);
		}
		return result;
	}

	private void validate(
			@NonNull final GRAIValidationSegment segment,
			@NonNull final Collection<HuId> pickedHUIds)
	{
		final GRAIRequired graiRequired = segment.getGraiRequired();
		if (graiRequired.isNo()) {return;}

		final HUGraiSnapshotsCollection snapshots = huService.getGraiSnapshotsOfBoxes(ImmutableSet.copyOf(pickedHUIds), getCUsBookedWithLabelScan());

		if (graiRequired == GRAIRequired.Yes)
		{
			snapshots.assertAllGraisAssigned();
		}
		else if (graiRequired == GRAIRequired.YesWithDummyGRAIs)
		{
			final DummyGRAIProvider nextGraiProvider = salesOrderDummyGRAIProviders.getNextGRAIProvider(segment.getSalesOrderId(), snapshots);
			huService.generateMissingGRAIs(snapshots, nextGraiProvider);
		}
	}

	private Map<GRAIValidationSegment, Collection<HuId>> getPickedHUIdsBySegment()
	{
		final SetMultimap<GRAIValidationSegment, HuId> pickedHUIdsBySegment = HashMultimap.create();
		for (final PickingJobLine line : pickingJob.getLines())
		{
			final GRAIValidationSegment segment = extractGRAIValidationSegment(line);
			pickedHUIdsBySegment.putAll(segment, line.getPickedHUIds());
		}
		return pickedHUIdsBySegment.asMap();
	}

	private GRAIValidationSegment extractGRAIValidationSegment(final PickingJobLine line)
	{
		final BPartnerId customerId = CoalesceUtil.coalesce(line.getCustomerId(), pickingJob.getCustomerId());
		final GRAIRequired graiRequired = customerId != null
				? customerGRAIConfigProvider.getGRAIRequired(customerId)
				: GRAIRequired.No;

		return GRAIValidationSegment.builder()
				.salesOrderId(line.getSalesOrderAndLineId().getOrderId())
				.graiRequired(graiRequired)
				.build();
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class GRAIValidationSegment
	{
		@NonNull OrderId salesOrderId;
		@NonNull GRAIRequired graiRequired;
	}
}
