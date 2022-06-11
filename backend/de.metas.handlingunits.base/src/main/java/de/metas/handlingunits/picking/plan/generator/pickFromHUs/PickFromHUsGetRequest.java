package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import java.util.Optional;

@Value
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PickFromHUsGetRequest
{
	@NonNull ImmutableSet<LocatorId> pickFromLocatorIds;
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull ShipmentAllocationBestBeforePolicy bestBeforePolicy;
	@NonNull Optional<HUReservationDocRef> reservationRef;

	@Builder
	private PickFromHUsGetRequest(
			@NonNull @Singular final ImmutableSet<LocatorId> pickFromLocatorIds,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final ShipmentAllocationBestBeforePolicy bestBeforePolicy,
			@NonNull final Optional<HUReservationDocRef> reservationRef)
	{
		Check.assumeNotEmpty(pickFromLocatorIds, "pickFromLocatorIds shall not be empty");

		this.pickFromLocatorIds = pickFromLocatorIds;
		this.productId = productId;
		this.asiId = asiId;
		this.bestBeforePolicy = bestBeforePolicy;
		this.reservationRef = reservationRef;
	}
}
