package de.metas.distribution.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.dao.ValueRestriction;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
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
	@Nullable Set<WarehouseId> warehouseToIds;
	@Nullable Set<OrderId> salesOrderIds;
	@Nullable Set<PPOrderId> manufacturingOrderIds;
	@Nullable Set<LocalDate> datesPromised;
	@Nullable Set<ProductId> productIds;
	@Nullable Set<Quantity> qtysEntered;

	//
	//
	//

	public enum OrderBy
	{
		PriorityRule,
		DatePromised,
	}
}
