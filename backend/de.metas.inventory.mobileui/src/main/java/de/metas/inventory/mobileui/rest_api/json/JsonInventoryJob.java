package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonInventoryJob
{
	@NonNull InventoryJobId id;
	@NonNull String documentNo;
	@NonNull String movementDate;
	@NonNull String warehouseName;
	@NonNull ImmutableList<JsonInventoryJobLine> lines;

	public static JsonInventoryJob of(final InventoryJob job)
	{
		return JsonInventoryJob.builder()
				.id(job.getId())
				.documentNo(job.getDocumentNo())
				.movementDate(job.getMovementDate().toString())
				.warehouseName(job.getWarehouseName())
				.lines(job.getLines()
						.stream()
						.map(JsonInventoryJobLine::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
