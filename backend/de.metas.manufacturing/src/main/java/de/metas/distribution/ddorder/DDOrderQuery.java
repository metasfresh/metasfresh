package de.metas.distribution.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.dao.ValueRestriction;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class DDOrderQuery
{
	@NonNull ImmutableList<OrderBy> orderBys;

	@Nullable DocStatus docStatus;
	@NonNull @Builder.Default ValueRestriction<UserId> responsibleId = ValueRestriction.any();
	@Nullable Set<WarehouseId> warehouseFromIds;
	@Nullable InSetPredicate<WarehouseId> warehouseToIds;
	@Nullable InSetPredicate<LocatorId> locatorToIds;
	/** Plain {@link Set} (not {@link InSetPredicate}) because the exclude filter uses a NOT-IN subquery via {@code DD_OrderLine}; there is no meaningful "exclude all" wildcard case. */
	@Nullable Set<LocatorId> excludeLocatorToIds;
	@Nullable Set<OrderId> salesOrderIds;
	@Nullable Set<PPOrderId> manufacturingOrderIds;
	@Nullable Set<LocalDate> datesPromised;
	@Nullable Set<ProductId> productIds;
	@Nullable Set<Quantity> qtysEntered;
	@Nullable Set<ResourceId> plantIds;
	@Nullable Set<DDOrderId> onlyDDOrderIds;

	/**
	 * Max number of records to fetch. Pushed down into the SQL query (and thus into the {@code T_Query_Selection}
	 * INSERT built by guaranteed iterators), so an unbounded selection is never materialized.
	 * Defaults to {@link QueryLimit#NO_LIMIT} to keep existing callers unchanged.
	 */
	@NonNull @Builder.Default QueryLimit limit = QueryLimit.NO_LIMIT;

	//
	//
	//
	@Value(staticConstructor = "of")
	public static class OrderBy
	{
		@NonNull OrderByField field;
		@NonNull Direction direction;
	}

	public enum OrderByField
	{
		PriorityRule,
		LocatorPriority,
		DatePromised,
		SeqNo,
	}
}
