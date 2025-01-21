package de.metas.distribution.workflows_api.facets;

import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.RepoIdAware;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class DistributionFacetId
{
	@NonNull WorkflowLaunchersFacetId workflowLaunchersFacetId;
	@NonNull DistributionFacetGroupType group;
	@Nullable WarehouseId warehouseId;
	@Nullable OrderId salesOrderId;
	@Nullable PPOrderId manufacturingOrderId;
	@Nullable LocalDate datePromised;
	@Nullable ProductId productId;
	@Nullable Quantity qty;

	private DistributionFacetId(@NonNull WorkflowLaunchersFacetId workflowLaunchersFacetId)
	{
		this.workflowLaunchersFacetId = workflowLaunchersFacetId;
		this.group = DistributionFacetGroupType.ofWorkflowLaunchersFacetGroupId(workflowLaunchersFacetId.getGroupId());
		switch (group)
		{
			case WAREHOUSE_FROM:
			case WAREHOUSE_TO:
			{
				this.warehouseId = workflowLaunchersFacetId.getAsId(WarehouseId.class);
				this.salesOrderId = null;
				this.manufacturingOrderId = null;
				this.datePromised = null;
				this.productId = null;
				this.qty = null;
				break;
			}
			case SALES_ORDER:
			{
				this.warehouseId = null;
				this.salesOrderId = workflowLaunchersFacetId.getAsId(OrderId.class);
				this.manufacturingOrderId = null;
				this.datePromised = null;
				this.productId = null;
				this.qty = null;
				break;
			}
			case MANUFACTURING_ORDER_NO:
			{
				this.warehouseId = null;
				this.salesOrderId = null;
				this.manufacturingOrderId = workflowLaunchersFacetId.getAsId(PPOrderId.class);
				this.datePromised = null;
				this.productId = null;
				this.qty = null;
				break;
			}
			case DATE_PROMISED:
			{
				this.warehouseId = null;
				this.salesOrderId = null;
				this.manufacturingOrderId = null;
				this.datePromised = workflowLaunchersFacetId.getAsLocalDate();
				this.productId = null;
				this.qty = null;
				break;
			}
			case PRODUCT:
			{
				this.warehouseId = null;
				this.salesOrderId = null;
				this.manufacturingOrderId = null;
				this.datePromised = null;
				this.productId = workflowLaunchersFacetId.getAsId(ProductId.class);
				this.qty = null;
				break;
			}

			case QUANTITY:
				this.warehouseId = null;
				this.salesOrderId = null;
				this.manufacturingOrderId = null;
				this.datePromised = null;
				this.productId = null;
				this.qty = getAsQuantity(workflowLaunchersFacetId);
				break;
			default:
			{
				throw new AdempiereException("Unknown group: " + group);
			}
		}
	}

	public static DistributionFacetId ofWorkflowLaunchersFacetId(@NonNull WorkflowLaunchersFacetId facetId)
	{
		return new DistributionFacetId(facetId);
	}

	public static DistributionFacetId ofWarehouseFromId(@NonNull WarehouseId warehouseId)
	{
		return ofRepoId(DistributionFacetGroupType.WAREHOUSE_FROM, warehouseId);
	}

	public static DistributionFacetId ofWarehouseToId(@NonNull WarehouseId warehouseId)
	{
		return ofRepoId(DistributionFacetGroupType.WAREHOUSE_TO, warehouseId);
	}

	public static DistributionFacetId ofSalesOrderId(@NonNull OrderId salesOrderId)
	{
		return ofRepoId(DistributionFacetGroupType.SALES_ORDER, salesOrderId);
	}

	public static DistributionFacetId ofManufacturingOrderId(@NonNull PPOrderId manufacturingOrderId)
	{
		return ofRepoId(DistributionFacetGroupType.MANUFACTURING_ORDER_NO, manufacturingOrderId);
	}

	public static DistributionFacetId ofDatePromised(@NonNull LocalDate datePromised)
	{
		return ofLocalDate(DistributionFacetGroupType.DATE_PROMISED, datePromised);
	}

	public static DistributionFacetId ofProductId(@NonNull ProductId productId)
	{
		return ofRepoId(DistributionFacetGroupType.PRODUCT, productId);
	}

	private static DistributionFacetId ofRepoId(@NonNull DistributionFacetGroupType groupType, @NonNull RepoIdAware id)
	{
		final WorkflowLaunchersFacetId workflowLaunchersFacetId = WorkflowLaunchersFacetId.ofId(groupType.toWorkflowLaunchersFacetGroupId(), id);
		return ofWorkflowLaunchersFacetId(workflowLaunchersFacetId);
	}

	@SuppressWarnings("SameParameterValue")
	private static DistributionFacetId ofLocalDate(@NonNull DistributionFacetGroupType groupType, @NonNull LocalDate localDate)
	{
		final WorkflowLaunchersFacetId workflowLaunchersFacetId = WorkflowLaunchersFacetId.ofLocalDate(groupType.toWorkflowLaunchersFacetGroupId(), localDate);
		return ofWorkflowLaunchersFacetId(workflowLaunchersFacetId);
	}

	public static DistributionFacetId ofQuantity(@NonNull Quantity qty)
	{
		return ofWorkflowLaunchersFacetId(
				WorkflowLaunchersFacetId.ofQuantity(
						DistributionFacetGroupType.QUANTITY.toWorkflowLaunchersFacetGroupId(),
						qty.toBigDecimal(),
						qty.getUomId()
				)
		);
	}

	private static Quantity getAsQuantity(final @NonNull WorkflowLaunchersFacetId workflowLaunchersFacetId)
	{
		final ImmutablePair<BigDecimal, Integer> parts = workflowLaunchersFacetId.getAsQuantity();
		return Quantitys.of(parts.getLeft(), UomId.ofRepoId(parts.getRight()));
	}

	public WorkflowLaunchersFacetId toWorkflowLaunchersFacetId() {return workflowLaunchersFacetId;}
}
