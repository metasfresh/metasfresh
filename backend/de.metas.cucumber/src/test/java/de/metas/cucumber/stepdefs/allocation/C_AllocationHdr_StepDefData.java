package de.metas.cucumber.stepdefs.allocation;

import de.metas.allocation.api.PaymentAllocationId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import org.compiere.model.I_C_AllocationHdr;

import java.util.Objects;

public class C_AllocationHdr_StepDefData extends StepDefData<I_C_AllocationHdr>
		implements StepDefDataGetIdAware<PaymentAllocationId, I_C_AllocationHdr>
{
	public C_AllocationHdr_StepDefData()
	{
		super(I_C_AllocationHdr.class);
	}

	@Override
	public PaymentAllocationId extractIdFromRecord(final I_C_AllocationHdr record)
	{
		return PaymentAllocationId.ofRepoId(record.getC_AllocationHdr_ID());
	}

	public void putOrReplaceIfSameId(final StepDefDataIdentifier identifier, final I_C_AllocationHdr newRecord)
	{
		final PaymentAllocationId newId = extractIdFromRecord(newRecord);
		final PaymentAllocationId currentId = getIdOptional(identifier).orElse(null);
		if (currentId != null && !Objects.equals(currentId, newId))
		{
			throw new RuntimeException("Cannot replace " + identifier + " because its current id is " + currentId + " and the new id is " + newId);
		}
		
		putOrReplace(identifier, newRecord);
	}
}
