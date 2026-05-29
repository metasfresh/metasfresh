package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.grai.DummyGRAIProvider;
import de.metas.handlingunits.grai.DummyGRAITemplate;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;

import java.util.HashMap;

class SalesOrderDummyGRAIProviders
{
	@NonNull private final IOrderDAO orderDAO;

	private final HashMap<OrderId, DummyGRAIProvider> providersBySalesOrderId = new HashMap<>();

	@Builder
	private SalesOrderDummyGRAIProviders(@NonNull final IOrderDAO orderDAO)
	{
		this.orderDAO = orderDAO;
	}

	public DummyGRAIProvider getNextGRAIProvider(
			@NonNull final OrderId salesOrderId,
			final HUGraiSnapshotsCollection graiSnapshots
	)
	{
		return providersBySalesOrderId.computeIfAbsent(salesOrderId, k -> createNextGRAIProvider(salesOrderId, graiSnapshots));
	}

	private DummyGRAIProvider createNextGRAIProvider(
			@NonNull final OrderId salesOrderId,
			final HUGraiSnapshotsCollection graiSnapshots
	)
	{
		final String poReference = extractPOReference(salesOrderId);
		final DummyGRAITemplate template = DummyGRAITemplate.migros(poReference);

		final int startCounter = graiSnapshots.findMaxExistingDummyCounter(template) + 1;
		return new DummyGRAIProvider(template, startCounter);
	}

	private String extractPOReference(final OrderId salesOrderId)
	{
		final I_C_Order salesOrder = orderDAO.getById(salesOrderId);
		final String poReference = StringUtils.trimBlankToNull(salesOrder.getPOReference());
		if (poReference == null)
		{
			throw new AdempiereException("Cannot generate dummy GRAIs: POReference not found for " + salesOrderId);
		}
		return poReference;
	}

}
