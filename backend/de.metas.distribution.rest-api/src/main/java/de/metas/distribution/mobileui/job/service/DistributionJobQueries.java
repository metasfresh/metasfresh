package de.metas.distribution.mobileui.job.service;

import de.metas.dao.ValueRestriction;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderQuery.DDOrderQueryBuilder;
import de.metas.distribution.mobileui.config.DistributionJobSorting;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetIdsCollection;
import de.metas.document.engine.DocStatus;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.WarehouseId;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;

@UtilityClass
public class DistributionJobQueries
{
	public static DDOrderQueryBuilder newDDOrdersQuery()
	{
		return DDOrderQuery.builder()
				.docStatus(DocStatus.Completed)
				.orderBys(DistributionJobSorting.DEFAULT.toDDOrderQueryOrderBys());
	}

	public static DDOrderQuery ddOrdersAssignedToUser(@NonNull final DDOrderReferenceQuery query)
	{
		return ddOrdersAssignedToUser(query.getResponsibleId(), query.getSorting()).build();
	}

	public static DDOrderQueryBuilder ddOrdersAssignedToUser(@NonNull final UserId responsibleId)
	{
		return ddOrdersAssignedToUser(responsibleId, DistributionJobSorting.DEFAULT);
	}

	private static DDOrderQueryBuilder ddOrdersAssignedToUser(@NonNull final UserId responsibleId, @NonNull DistributionJobSorting sorting)
	{
		return newDDOrdersQuery()
				.orderBys(sorting.toDDOrderQueryOrderBys())
				.responsibleId(ValueRestriction.equalsTo(responsibleId));
	}

	public static DDOrderQuery toActiveNotAssignedDDOrderQuery(final @NonNull DDOrderReferenceQuery query)
	{
		final DistributionFacetIdsCollection activeFacetIds = query.getActiveFacetIds();

		final InSetPredicate<WarehouseId> warehouseToIds = extractWarehouseToIds(query);

		return newDDOrdersQuery()
				.orderBys(query.getSorting().toDDOrderQueryOrderBys())
				.responsibleId(ValueRestriction.isNull())
				.warehouseFromIds(activeFacetIds.getWarehouseFromIds())
				.warehouseToIds(warehouseToIds)
				.locatorToIds(InSetPredicate.onlyOrAny(query.getLocatorToId()))
				.excludeLocatorToIds(query.getExcludeLocatorToIds())
				// No sales-order restriction here by design, and DDOrderQuery deliberately has no salesOrderIds
				// field to set: filtering on the header DD_Order.C_Order_ID alone misses the replenishment path,
				// which never sets it. The whole sales-order facet is carried by the demand-side line restriction
				// (DistributionWorkflowLaunchersProvider#buildLineIdRestrictions ->
				// DDOrderLineDemandSqlHelper#bySalesOrderIds), which ORs the header column back in as one of its
				// three routes. Re-adding the field would let that predicate be applied here as a SECOND top-level
				// restriction -- top-level restrictions AND, so the header route would once again exclude exactly
				// the jobs the three-route OR exists to find -- and would misrepresent the header column as the
				// authoritative source of a job's sales order, which it is not.
				.manufacturingOrderIds(activeFacetIds.getManufacturingOrderIds())
				.datesPromised(activeFacetIds.getDatesPromised())
				.productIds(activeFacetIds.getProductIds())
				.qtysEntered(activeFacetIds.getQuantities())
				.plantIds(activeFacetIds.getPlantIds())
				.build();
	}

	@Nullable
	private static InSetPredicate<WarehouseId> extractWarehouseToIds(final @NotNull DDOrderReferenceQuery query)
	{
		final Set<WarehouseId> facetWarehouseToIds = query.getActiveFacetIds().getWarehouseToIds();

		final WarehouseId onlyWarehouseToId = query.getWarehouseToId();
		if (onlyWarehouseToId != null)
		{
			if (facetWarehouseToIds.isEmpty() || facetWarehouseToIds.contains(onlyWarehouseToId))
			{
				return InSetPredicate.only(onlyWarehouseToId);
			}
			else
			{
				return InSetPredicate.none();
			}
		}
		else if (!facetWarehouseToIds.isEmpty())
		{
			return InSetPredicate.only(facetWarehouseToIds);
		}
		else
		{
			return InSetPredicate.any();
		}
	}
}
