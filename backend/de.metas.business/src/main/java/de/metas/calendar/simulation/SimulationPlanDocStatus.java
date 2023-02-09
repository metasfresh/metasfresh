package de.metas.calendar.simulation;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_SimulationPlan;

@AllArgsConstructor
public enum SimulationPlanDocStatus implements ReferenceListAwareEnum
{
	Drafted(X_C_SimulationPlan.DOCSTATUS_Drafted),
	Completed(X_C_SimulationPlan.DOCSTATUS_Completed),
	Voided(X_C_SimulationPlan.DOCSTATUS_Voided),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<SimulationPlanDocStatus> index = ReferenceListAwareEnums.index(values());

	@Getter
	@NonNull private final String code;

	public static SimulationPlanDocStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isDrafted() {return Drafted.equals(this);}

	public boolean isCompleted() {return Completed.equals(this);}

	public boolean isVoided() {return Voided.equals(this);}

	public boolean isProcessed() {return isCompleted() || isVoided();}
}
