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

	/**
	 * Workplace visibility filter. When {@code workplaceWarehouseId} is set, matches orders that either
	 * <ul>
	 *     <li>ship <b>from</b> this warehouse ({@code M_Warehouse_From_ID}) — the source/picking side, OR</li>
	 *     <li>deliver <b>to</b> this warehouse ({@code M_Warehouse_To_ID}) — the destination side; and, when
	 *         {@code workplacePickFromLocatorId} is also set, only orders that have a line delivering to that
	 *         locator ({@code DD_OrderLine.M_LocatorTo_ID}).</li>
	 * </ul>
	 * The pick-from locator narrows the destination side only — it never suppresses source-side matches.
	 * Applied in addition to (AND with) {@code warehouseFromIds} / {@code warehouseToIds} when those are set.
	 */
	@Nullable WarehouseId workplaceWarehouseId;
	@Nullable LocatorId workplacePickFromLocatorId;
	@Nullable Set<OrderId> salesOrderIds;
	@Nullable Set<PPOrderId> manufacturingOrderIds;
	@Nullable Set<LocalDate> datesPromised;
	@Nullable Set<ProductId> productIds;
	@Nullable Set<Quantity> qtysEntered;
	@Nullable Set<ResourceId> plantIds;
	@Nullable Set<DDOrderId> onlyDDOrderIds;

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
