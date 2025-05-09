/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

public class PP_Order_Candidate_StepDefData extends StepDefData<I_PP_Order_Candidate>
		implements StepDefDataGetIdAware<PPOrderCandidateId, I_PP_Order_Candidate>
{
	public PP_Order_Candidate_StepDefData()
	{
		super(I_PP_Order_Candidate.class);
	}

	@Override
	public PPOrderCandidateId extractIdFromRecord(final I_PP_Order_Candidate record)
	{
		return PPOrderCandidateId.ofRepoId(record.getPP_Order_Candidate_ID());
	}

	public void invalidate(@NonNull final StepDefDataIdentifier identifier)
	{
		final I_PP_Order_Candidate candidate = getOptional(identifier).orElse(null);
		InterfaceWrapperHelper.refresh(candidate);
	}
}
