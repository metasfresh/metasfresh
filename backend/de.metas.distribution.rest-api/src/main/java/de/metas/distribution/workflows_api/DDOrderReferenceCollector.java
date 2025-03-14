package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Builder
public class DDOrderReferenceCollector implements DistributionOrderCollector<DDOrderReference>
{
	@NonNull private final DDOrderService ddOrderService;

	private final ArrayList<DDOrderReference> _result = new ArrayList<>();
	private final HashSet<DDOrderId> seenDDOrderIds = new HashSet<>();
	private final ArrayList<DDOrderReference> pendingResults = new ArrayList<>();

	@Override
	public void collect(final I_DD_Order ddOrder, final boolean isJobStarted)
	{
		if (!seenDDOrderIds.add(extractDDOrderId(ddOrder)))
		{
			return;
		}
		pendingResults.add(toDDOrderReference(ddOrder, isJobStarted));
	}

	@Override
	public Collection<DDOrderReference> getCollectedItems()
	{
		processPendingRequests();
		return _result;
	}

	public Stream<DDOrderReference> streamCollectedItems() {return getCollectedItems().stream();}

	private void processPendingRequests()
	{
		if (pendingResults.isEmpty()) {return;}

		final ImmutableMap<DDOrderId, DDOrderReference> resultsByDDOrderId = Maps.uniqueIndex(pendingResults, DDOrderReference::getDdOrderId);
		final ImmutableSet<DDOrderId> ddOrderIds = resultsByDDOrderId.keySet();
		final ImmutableListMultimap<DDOrderId, I_DD_OrderLine> linesByDDOrderId = ddOrderService.streamLinesByDDOrderIds(ddOrderIds)
				.collect(ImmutableListMultimap.toImmutableListMultimap(DDOrderReferenceCollector::extractDDOrderId, line -> line));

		ddOrderIds.forEach(ddOrderId -> {
			final DDOrderReference reference = updateDDOrderReference(resultsByDDOrderId.get(ddOrderId), linesByDDOrderId.get(ddOrderId));
			_result.add(reference);
		});

		pendingResults.clear();
	}

	@NonNull
	private static DDOrderReference toDDOrderReference(final I_DD_Order ddOrder, final boolean isJobStarted)
	{
		return DDOrderReference.builder()
				.ddOrderId(extractDDOrderId(ddOrder))
				.documentNo(ddOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID()))
				.pickDate(InstantAndOrgId.ofTimestampOrNull(ddOrder.getPickDate(), OrgId.ofRepoId(ddOrder.getAD_Org_ID())))
				.fromWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID()))
				.toWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_To_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID()))
				.ppOrderId(PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID()))
				.isJobStarted(isJobStarted)
				.plantId(ResourceId.ofRepoIdOrNull(ddOrder.getPP_Plant_ID()))
				.build();
	}

	private static DDOrderReference updateDDOrderReference(final DDOrderReference reference, final List<I_DD_OrderLine> lines)
	{
		final HashSet<ProductId> productIds = new HashSet<>();
		final HashSet<Quantity> qtysEntered = new HashSet<>();
		for (final I_DD_OrderLine line : lines)
		{
			productIds.add(ProductId.ofRepoId(line.getM_Product_ID()));
			qtysEntered.add(extractQtyEntered(line));
		}

		return reference.toBuilder()
				.productId(CollectionUtils.singleElementOrNull(productIds))
				.qty(CollectionUtils.singleElementOrNull(qtysEntered))
				.build();
	}

	@NonNull
	private static DDOrderId extractDDOrderId(final I_DD_Order ddOrder) {return DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());}

	@NonNull
	private static DDOrderId extractDDOrderId(final I_DD_OrderLine ddOrderLine) {return DDOrderId.ofRepoId(ddOrderLine.getDD_Order_ID());}

	@NonNull
	public static Quantity extractQtyEntered(final I_DD_OrderLine ddOrderLine)
	{
		return Quantitys.of(ddOrderLine.getQtyEntered(), UomId.ofRepoId(ddOrderLine.getC_UOM_ID()));
	}

}
