package de.metas.handlingunits.picking.job.service.commands.grai;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.grai.DummyGRAIProvider;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.Map;

public class PickingJobGRAIValidator
{
	@NonNull private final PickingJobHUService huService;

	// Parameters
	@NonNull private final PickingJob pickingJob;

	// State
	@NonNull private final SalesOrderDummyGRAIProviders salesOrderDummyGRAIProviders;
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
		getLinesBySegment().forEach(this::validate);
	}

	private void validate(
			@NonNull final GRAIValidationSegment segment,
			@NonNull final Collection<PickingJobLine> segmentLines)
	{
		final GRAIRequired graiRequired = segment.getGraiRequired();
		if (graiRequired.isNo()) {return;}

		final HUGraiSnapshotsCollection snapshots = huService.getGraiSnapshotsOfPicked(segmentLines);

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

	private Map<GRAIValidationSegment, Collection<PickingJobLine>> getLinesBySegment()
	{
		final ListMultimap<GRAIValidationSegment, PickingJobLine> linesBySegment = ArrayListMultimap.create();
		for (final PickingJobLine line : pickingJob.getLines())
		{
			linesBySegment.put(extractGRAIValidationSegment(line), line);
		}
		return linesBySegment.asMap();
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
