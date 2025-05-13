package de.metas.cucumber.stepdefs.payselection;

import de.metas.banking.PaySelectionId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_C_PaySelection;

public class C_PaySelection_StepDefData extends StepDefData<I_C_PaySelection> implements StepDefDataGetIdAware<PaySelectionId, I_C_PaySelection>
{
	public C_PaySelection_StepDefData()
	{
		super(I_C_PaySelection.class);
	}

	@Override
	public PaySelectionId extractIdFromRecord(final I_C_PaySelection record)
	{
		return PaySelectionId.ofRepoId(record.getC_PaySelection_ID());
	}
}
