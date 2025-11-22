package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.DDOrderId;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.model.I_DD_Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Builder
public class DDOrderReferenceCollector implements DistributionOrderCollector<DDOrderReference>
{
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;

	private final ArrayList<DDOrderReference> _result = new ArrayList<>();
	private final HashSet<DDOrderId> seenDDOrderIds = new HashSet<>();
	private final ArrayList<I_DD_Order> collectedDDOrders = new ArrayList<>();

	@Override
	public void collect(final I_DD_Order ddOrder)
	{
		final DDOrderId ddOrderId = extractDDOrderId(ddOrder);
		if (!seenDDOrderIds.add(ddOrderId))
		{
			return;
		}
		collectedDDOrders.add(ddOrder);
	}

	@Override
	public Collection<DDOrderReference> getCollectedItems()
	{
		processPendingRequests();
		return _result;
	}

	@NonNull
	private DistributionJobLoader newLoader()
	{
		return new DistributionJobLoader(loadingSupportServices);
	}

	private void processPendingRequests()
	{
		if (collectedDDOrders.isEmpty()) {return;}

		newLoader()
				.loadByRecords(collectedDDOrders)
				.stream()
				.map(DDOrderReferenceCollector::toDDOrderReference)
				.forEach(_result::add);

		collectedDDOrders.clear();
	}

	@NonNull
	private static DDOrderReference toDDOrderReference(final DistributionJob job)
	{
		return DDOrderReference.builder()
				.ddOrderId(job.getDdOrderId())
				.documentNo(job.getDocumentNo())
				.datePromised(job.getDateRequired())
				.pickDate(job.getPickDate())
				.fromWarehouseId(job.getPickFromWarehouse().getWarehouseId())
				.toWarehouseId(job.getDropToWarehouse().getWarehouseId())
				.salesOrderId(job.getSalesOrderRef() != null ? job.getSalesOrderRef().getId() : null)
				.ppOrderId(job.getManufacturingOrderRef() != null ? job.getManufacturingOrderRef().getId() : null)
				.isJobStarted(job.isJobAssigned())
				.plantId(job.getPlantInfo() != null ? job.getPlantInfo().getResourceId() : null)
				.priority(job.getPriority())
				.fromLocatorId(job.getSinglePickFromLocatorIdOrNull())
				.toLocatorId(job.getSingleDropToLocatorIdOrNull())
				.productId(job.getSingleProductIdOrNull())
				.qty(job.getSingleUnitQuantityOrNull())
				.isInTransit(job.isInTransit())
				.build();
	}

	@NonNull
	private static DDOrderId extractDDOrderId(final I_DD_Order ddOrder) {return DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());}
}
