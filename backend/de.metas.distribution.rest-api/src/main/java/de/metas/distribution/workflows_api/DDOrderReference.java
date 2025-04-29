package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.frontend_testing.JsonTestId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class DDOrderReference
{
	@NonNull DDOrderId ddOrderId;
	@NonNull String documentNo;
	@NonNull InstantAndOrgId datePromised;
	@Nullable InstantAndOrgId pickDate;
	@NonNull WarehouseId fromWarehouseId;
	@NonNull WarehouseId toWarehouseId;
	@Nullable PPOrderId ppOrderId;
	@Nullable OrderId salesOrderId;
	@Nullable ProductId productId;
	@Nullable Quantity qty;
	@Nullable ResourceId plantId;
	boolean isJobStarted;

	@NonNull
	public JsonTestId getTestId() {return JsonTestId.ofRepoId(ddOrderId);}

	@NonNull
	public InstantAndOrgId getDisplayDate()
	{
		if (pickDate != null)
		{
			return pickDate;
		}

		return datePromised;
	}
}
