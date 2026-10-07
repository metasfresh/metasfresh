package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import lombok.NonNull;
import org.compiere.model.I_C_ElementValue;

/**
 * Stores {@link I_C_ElementValue} records (GL accounts) by identifier alias,
 * enabling cross-step references to accounts in accounting assertions.
 */
public class C_ElementValue_StepDefData extends StepDefData<I_C_ElementValue>
		implements StepDefDataGetIdAware<ElementValueId, I_C_ElementValue>
{
	public C_ElementValue_StepDefData()
	{
		super(I_C_ElementValue.class);
	}

	@Override
	public ElementValueId extractIdFromRecord(@NonNull final I_C_ElementValue record)
	{
		return ElementValueId.ofRepoId(record.getC_ElementValue_ID());
	}
}
