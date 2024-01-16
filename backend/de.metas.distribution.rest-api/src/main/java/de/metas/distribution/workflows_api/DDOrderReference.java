package de.metas.distribution.workflows_api;

import de.metas.organization.InstantAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import de.metas.distribution.ddorder.DDOrderId;

@Value
@Builder
public class DDOrderReference
{
	@NonNull DDOrderId ddOrderId;
	@NonNull String documentNo;
	@NonNull InstantAndOrgId datePromised;
	@NonNull WarehouseId fromWarehouseId;
	@NonNull WarehouseId toWarehouseId;
}
