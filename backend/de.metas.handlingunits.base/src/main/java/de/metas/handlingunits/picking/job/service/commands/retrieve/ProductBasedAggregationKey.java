package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

@Value
@EqualsAndHashCode(exclude = "uom")
@Builder
class ProductBasedAggregationKey
{
	@NonNull OrgId orgId;
	@NonNull ProductId productId;
	@NonNull UomId uomId;
	@NonNull I_C_UOM uom;
	@Nullable WarehouseTypeId warehouseTypeId;

	public static ProductBasedAggregationKey of(@NonNull final ScheduledPackageable item)
	{
		final I_C_UOM uom = item.getUOM();
		return builder()
				.orgId(item.getOrgId())
				.productId(item.getProductId())
				.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
				.uom(uom)
				.warehouseTypeId(item.getWarehouseTypeId())
				.build();
	}
}
