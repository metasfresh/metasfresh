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
import lombok.Singular;
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
	@NonNull @Singular ImmutableList<OrderBy> orderBys;

	@Nullable DocStatus docStatus;
	@NonNull @Builder.Default ValueRestriction<UserId> responsibleId = ValueRestriction.any();
	@Nullable Set<WarehouseId> warehouseFromIds;
	@Nullable InSetPredicate<WarehouseId> warehouseToIds;
	@Nullable InSetPredicate<LocatorId> locatorToIds;
	@Nullable Set<OrderId> salesOrderIds;
	@Nullable Set<PPOrderId> manufacturingOrderIds;
	@Nullable Set<LocalDate> datesPromised;
	@Nullable Set<ProductId> productIds;
	@Nullable Set<Quantity> qtysEntered;
	@Nullable Set<ResourceId> plantIds;

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
		DatePromised,
		SeqNo,
	}
}
