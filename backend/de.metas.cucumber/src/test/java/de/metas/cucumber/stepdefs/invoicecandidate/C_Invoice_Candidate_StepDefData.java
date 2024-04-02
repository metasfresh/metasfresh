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

package de.metas.cucumber.stepdefs.invoicecandidate;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class C_Invoice_Candidate_StepDefData extends StepDefData<I_C_Invoice_Candidate>
		implements StepDefDataGetIdAware<InvoiceCandidateId, I_C_Invoice_Candidate>
{
	public C_Invoice_Candidate_StepDefData()
	{
		super(I_C_Invoice_Candidate.class);
	}

	@Override
	public InvoiceCandidateId extractIdFromRecord(final I_C_Invoice_Candidate record)
	{
		return InvoiceCandidateId.ofRepoId(record.getC_Invoice_Candidate_ID());
	}
}