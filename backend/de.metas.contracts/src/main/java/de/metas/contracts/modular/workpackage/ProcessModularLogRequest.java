package de.metas.contracts.modular.workpackage;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
@Builder
@Jacksonized
public class ProcessModularLogRequest
{
	@NonNull TableRecordReference recordReference;
	@NonNull ModelAction action;
	@NonNull LogEntryContractType logEntryContractType;
	@NonNull FlatrateTermId flatrateTermId;
	@NonNull ComputingMethodType computingMethodType;
}
