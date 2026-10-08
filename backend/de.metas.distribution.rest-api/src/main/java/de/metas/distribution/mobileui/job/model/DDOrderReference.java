package de.metas.distribution.mobileui.job.model;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.frontend_testing.JsonTestId;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Value
@Builder(toBuilder = true)
public class DDOrderReference
{
	@NonNull DDOrderId ddOrderId;
	@NonNull String documentNo;
	@NonNull SeqNo seqNo;
	@NonNull ZonedDateTime datePromised;
	@Nullable ZonedDateTime pickDate;
	@Nullable ITranslatableString pickingInstruction;
	@NonNull WarehouseId fromWarehouseId;
	@Nullable LocatorId fromLocatorId;
	@NonNull WarehouseId toWarehouseId;
	@Nullable LocatorId toLocatorId;
	@Nullable PPOrderId ppOrderId;
	@Nullable OrderId salesOrderId;
	@Nullable ProductId productId;
	@Nullable Quantity qty;
	@Nullable ResourceId plantId;
	@Nullable String priority;
	boolean isInTransit;
	boolean isJobStarted;

	@NonNull
	public JsonTestId getTestId() {return JsonTestId.ofRepoId(ddOrderId);}

	@NonNull
	public ZonedDateTime getDisplayDate()
	{
		if (pickDate != null)
		{
			return pickDate;
		}

		return datePromised;
	}
}
