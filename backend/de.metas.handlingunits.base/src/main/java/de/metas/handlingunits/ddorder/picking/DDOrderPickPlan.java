package de.metas.handlingunits.ddorder.picking;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.DDOrderId;

@Value
@Builder
public class DDOrderPickPlan
{
	@NonNull DDOrderId ddOrderId;
	@NonNull ImmutableList<DDOrderLinePickPlan> lines;
}
