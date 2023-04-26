package de.metas.handlingunits.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class HUReservationEntry
{
	@NonNull HUReservationDocRef documentRef;
	@Nullable BPartnerId customerId;
	@NonNull HuId vhuId;
	@NonNull Quantity qtyReserved;
}
