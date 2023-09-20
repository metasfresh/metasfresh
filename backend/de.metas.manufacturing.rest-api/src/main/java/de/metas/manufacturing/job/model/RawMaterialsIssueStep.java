package de.metas.manufacturing.job.model;

import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
public class RawMaterialsIssueStep
{
	PPOrderIssueScheduleId id;

	boolean isAlternativeIssue;

	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToIssue;

	//
	// Issue From
	@NonNull LocatorInfo issueFromLocator;
	@NonNull HUInfo issueFromHU;

	//
	// Issued
	@With
	@Nullable PPOrderIssueSchedule.Issued issued;

	public boolean isIssued()
	{
		return issued != null;
	}

	public void assertNotIssued()
	{
		if (isIssued())
		{
			throw new AdempiereException(PPOrderIssueScheduleService.MSG_AlreadyIssued)
					.markAsUserValidationError()
					.setParameter("step", this);
		}
	}
}
