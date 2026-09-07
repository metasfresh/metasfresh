package org.adempiere.inout.util;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ReservationKey
{
	public static final ReservationKey NO_KEY = new ReservationKey(null);

	@Nullable OrderLineId salesOrderLineId;

	private ReservationKey(@Nullable final OrderLineId salesOrderLineId)
	{
		this.salesOrderLineId = salesOrderLineId;
	}

	public static ReservationKey ofSalesOrderLineId(@NonNull final OrderLineId salesOrderLineId)
	{
		return new ReservationKey(salesOrderLineId);
	}

	public static ReservationKey ofShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderLineId salesOrderLineId = OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID());
		return salesOrderLineId != null ? ofSalesOrderLineId(salesOrderLineId) : NO_KEY;
	}

	public boolean isNoKey() {return Objects.equals(this, NO_KEY);}
}
