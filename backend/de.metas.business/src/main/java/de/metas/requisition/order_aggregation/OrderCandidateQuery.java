package de.metas.requisition.order_aggregation;

import de.metas.bpartner.BPGroupId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class OrderCandidateQuery
{
	@Nullable OrgId orgId;
	@Nullable WarehouseId warehouseId;
	@Nullable Instant dateDocFrom;
	@Nullable Instant dateDocTo;
	@Nullable Instant dateRequiredFrom;
	@Nullable Instant dateRequiredTo;
	@Nullable String priorityRule;
	@Nullable UserId requestorId;
	@Nullable ProductId productId;
	@Nullable ProductCategoryId productCategoryId;
	@Nullable BPGroupId bpGroupId;

	boolean orderByRequisitionId;
	boolean orderByRequestor;
}
