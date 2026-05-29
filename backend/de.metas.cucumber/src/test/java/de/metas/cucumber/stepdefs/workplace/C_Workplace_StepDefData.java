package de.metas.cucumber.stepdefs.workplace;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;

public class C_Workplace_StepDefData extends StepDefData<Workplace> implements StepDefDataGetIdAware<WorkplaceId, Workplace>
{
	public C_Workplace_StepDefData()
	{
		super(Workplace.class);
	}

	@Override
	public WorkplaceId extractIdFromRecord(final Workplace record)
	{
		return record.getId();
	}
}
