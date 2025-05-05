package de.metas.contracts.modular.log;

import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.eevolution.api.PPCostCollectorId;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LogSubEntryId
{
	@NonNull String columnName;
	@NonNull RepoIdAware id;

	public static LogSubEntryId ofCostCollectorId(@NonNull final PPCostCollectorId costCollectorId)
	{
		return new LogSubEntryId(I_ModCntr_Log.COLUMNNAME_PP_Cost_Collector_ID, costCollectorId);
	}
}
