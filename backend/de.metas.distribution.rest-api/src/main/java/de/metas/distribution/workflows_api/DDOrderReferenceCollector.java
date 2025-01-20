package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
class DDOrderReferenceCollector implements DistributionOrderCollector<DDOrderReference>
{
	private final ArrayList<DDOrderReference> result = new ArrayList<>();

	@Override
	public void collect(final I_DD_Order ddOrder, final boolean isJobStarted)
	{
		result.add(toDDOrderReference(ddOrder, isJobStarted));
	}

	@Override
	public Collection<DDOrderReference> getCollectedItems() {return result;}

	public Stream<DDOrderReference> streamCollectedItems() {return getCollectedItems().stream();}

	@NonNull
	private static DDOrderReference toDDOrderReference(final I_DD_Order ddOrder, final boolean isJobStarted)
	{
		return DDOrderReference.builder()
				.ddOrderId(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.documentNo(ddOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID()))
				.fromWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID()))
				.toWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_To_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID()))
				.ppOrderId(PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID()))
				.isJobStarted(isJobStarted)
				.build();
	}
}
