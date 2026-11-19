package de.metas.hu_consolidation.mobile.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationJob
{
	@NonNull HUConsolidationJobId id;
	@NonNull String shipToAddress;
	@NonNull ImmutableList<JsonHUConsolidationJobPickingSlot> pickingSlots;
	
	@Nullable JsonHUConsolidationTarget currentTarget;

	/** true iff an active top-level HU in the job's slots carries a GRAI */
	boolean graiScanEnabled;
}
