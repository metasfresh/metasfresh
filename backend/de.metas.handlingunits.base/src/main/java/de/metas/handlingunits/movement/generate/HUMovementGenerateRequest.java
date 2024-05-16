package de.metas.handlingunits.movement.generate;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.document.dimension.Dimension;
import de.metas.freighcost.FreightCostRule;
import de.metas.handlingunits.HuId;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class HUMovementGenerateRequest
{
	@Nullable LocatorId fromLocatorId;
	@NonNull LocatorId toLocatorId;

	@Singular("huIdToMove")
	@NonNull ImmutableSet<HuId> huIdsToMove;

	@NonNull Instant movementDate;

	@Nullable ClientAndOrgId clientAndOrgId;

	@Nullable DDOrderAndLineId ddOrderLineId;
	@Nullable String poReference;
	@Nullable String description;

	@Nullable UserId salesRepId;

	//
	// Shipper BPartner
	@Nullable BPartnerLocationId bpartnerAndLocationId;
	@Nullable BPartnerContactId bpartnerContactId;

	//
	// Shipper
	@Nullable ShipperId shipperId;
	@Nullable FreightCostRule freightCostRule;
	@Nullable BigDecimal freightAmt;

	//
	// Delivery Rules & Priority
	@Nullable DeliveryRule deliveryRule;
	@Nullable DeliveryViaRule deliveryViaRule;
	@Nullable String priorityRule;

	//
	// Others
	@Nullable Dimension dimensionFields;
}
