package de.metas.mforecast.generator;

import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.time.LocalDate;

/**
 * Read-only domain object representing the forecast header during line generation.
 * Contains only the fields the generator needs — not a full Forecast model.
 */
@Value
@Builder
public class GeneratorForecast
{
	@NonNull ForecastId id;
	@NonNull OrgId orgId;
	@NonNull WarehouseId warehouseId;
	@NonNull LocalDate referenceDate;
}
