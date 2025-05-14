package de.metas.handlingunits.picking.config.mobileui;

import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingCustomerConfig
{
	@NonNull BPartnerId customerId;
	@Nullable PickingJobOptionsId pickingJobOptionsId;
}
