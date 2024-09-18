package de.metas.pos;

import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
@Builder
public class POSShipFrom
{
	@NonNull WarehouseId warehouseId;
	@NonNull OrgId orgId;
	@NonNull CountryId countryId;
}
