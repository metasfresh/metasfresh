package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import de.metas.distribution.ddorder.DDOrderId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@ToString
@Getter
public class DistributionJob
{
	@NonNull private final DDOrderId ddOrderId;
	@NonNull private final String documentNo;
	@NonNull private final ZonedDateTime dateRequired;
	@NonNull private final WarehouseInfo pickFromWarehouse;
	@NonNull private final WarehouseInfo dropToWarehouse;
	@Nullable private final UserId responsibleId;
	@NonNull private final ImmutableList<DistributionJobLine> lines;

	@Builder
	private DistributionJob(
			final @NonNull DDOrderId ddOrderId,
			final @NonNull String documentNo,
			final @NonNull ZonedDateTime dateRequired,
			final @NonNull WarehouseInfo pickFromWarehouse,
			final @NonNull WarehouseInfo dropToWarehouse,
			final @Nullable UserId responsibleId,
			final @NonNull List<DistributionJobLine> lines)
	{
		this.ddOrderId = ddOrderId;
		this.documentNo = documentNo;
		this.dateRequired = dateRequired;
		this.pickFromWarehouse = pickFromWarehouse;
		this.dropToWarehouse = dropToWarehouse;
		this.responsibleId = responsibleId;
		this.lines = ImmutableList.copyOf(lines);
	}
}
