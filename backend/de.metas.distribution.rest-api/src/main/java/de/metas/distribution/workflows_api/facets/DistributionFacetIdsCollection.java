package de.metas.distribution.workflows_api.facets;

import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

public class DistributionFacetIdsCollection implements Iterable<DistributionFacetId>
{
	public static final DistributionFacetIdsCollection EMPTY = new DistributionFacetIdsCollection(ImmutableSet.of());
	private final ImmutableSet<DistributionFacetId> set;

	private DistributionFacetIdsCollection(@NonNull final ImmutableSet<DistributionFacetId> set)
	{
		this.set = set;
	}

	public static DistributionFacetIdsCollection ofWorkflowLaunchersFacetIds(@Nullable Collection<WorkflowLaunchersFacetId> facetIds)
	{
		if (facetIds == null || facetIds.isEmpty())
		{
			return EMPTY;
		}

		return ofCollection(facetIds.stream().map(DistributionFacetId::ofWorkflowLaunchersFacetId).collect(ImmutableSet.toImmutableSet()));
	}

	public static DistributionFacetIdsCollection ofCollection(final Collection<DistributionFacetId> collection)
	{
		return !collection.isEmpty() ? new DistributionFacetIdsCollection(ImmutableSet.copyOf(collection)) : EMPTY;
	}

	@Override
	@NonNull
	public Iterator<DistributionFacetId> iterator() {return set.iterator();}

	public boolean isEmpty() {return set.isEmpty();}

	public Set<WarehouseId> getWarehouseFromIds() {return getValues(DistributionFacetGroupType.WAREHOUSE_FROM, DistributionFacetId::getWarehouseId);}

	public Set<WarehouseId> getWarehouseToIds() {return getValues(DistributionFacetGroupType.WAREHOUSE_TO, DistributionFacetId::getWarehouseId);}

	public Set<OrderId> getSalesOrderIds() {return getValues(DistributionFacetGroupType.SALES_ORDER, DistributionFacetId::getSalesOrderId);}

	public Set<PPOrderId> getManufacturingOrderIds() {return getValues(DistributionFacetGroupType.MANUFACTURING_ORDER_NO, DistributionFacetId::getManufacturingOrderId);}

	public Set<LocalDate> getDatesPromised() {return getValues(DistributionFacetGroupType.DATE_PROMISED, DistributionFacetId::getDatePromised);}

	public Set<ProductId> getProductIds() {return getValues(DistributionFacetGroupType.PRODUCT, DistributionFacetId::getProductId);}

	public <T> Set<T> getValues(DistributionFacetGroupType groupType, Function<DistributionFacetId, T> valueExtractor)
	{
		if (set.isEmpty())
		{
			return ImmutableSet.of();
		}

		return set.stream()
				.filter(facetId -> facetId.getGroup().equals(groupType))
				.map(valueExtractor)
				.collect(ImmutableSet.toImmutableSet());
	}

}
