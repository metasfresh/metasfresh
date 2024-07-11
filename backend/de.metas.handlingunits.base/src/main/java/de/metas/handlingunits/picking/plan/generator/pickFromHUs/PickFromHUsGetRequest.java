package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PickFromHUsGetRequest
{
	@NonNull ImmutableSet<LocatorId> pickFromLocatorIds;
	@NonNull ProductId productId;
	@NonNull BPartnerId partnerId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull ShipmentAllocationBestBeforePolicy bestBeforePolicy;
	@NonNull Optional<HUReservationDocRef> reservationRef;
	@Nullable ImmutableSet<HuId> onlyHuIds;

	/**
	 * If {@code true}, then check, which attributes are mandatory for the HUs' products. 
	 * Ignore those HUs that don't have all those attributes set.
	 */
	boolean enforceMandatoryAttributesOnPicking;
	
	@Builder(toBuilder = true)
	private PickFromHUsGetRequest(
			@NonNull @Singular final ImmutableSet<LocatorId> pickFromLocatorIds,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId partnerId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final ShipmentAllocationBestBeforePolicy bestBeforePolicy,
			@NonNull final Optional<HUReservationDocRef> reservationRef, 
			@Nullable final Boolean enforceMandatoryAttributesOnPicking,
			@Nullable final ImmutableSet<HuId> onlyHuIds)
	{
		Check.assumeNotEmpty(pickFromLocatorIds, "pickFromLocatorIds shall not be empty");

		this.pickFromLocatorIds = pickFromLocatorIds;
		this.productId = productId;
		this.partnerId = partnerId;
		this.asiId = asiId;
		this.bestBeforePolicy = bestBeforePolicy;
		this.reservationRef = reservationRef;
		this.enforceMandatoryAttributesOnPicking = CoalesceUtil.coalesceNotNull(enforceMandatoryAttributesOnPicking, false);
		this.onlyHuIds = onlyHuIds;
	}
}
