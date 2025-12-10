package de.metas.invoicecandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.inout.IInOutBL;
import de.metas.invoice.interceptor.C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.logging.LogManager;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Interceptor(I_M_InOut.class)
public class M_InOut
{
	private static final Logger log = LogManager.getLogger(C_Invoice.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	// Moved here from {@link de.metas.inout.model.validator.M_InOut}
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, //
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL, //
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_COMPLETE // needed in case we complete an inout that was previously reactivated
	})

	public void invalidateInvoiceCandidatesOnReversal(final I_M_InOut inout)
	{
		invoiceCandidateHandlerBL.invalidateCandidatesFor(inout);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void copyProjectIdFromLinesIfApplicable(final I_M_InOut inout)
	{
		final Set<ProjectId> projectIds = inOutBL.getLines(inout)
				.stream()
				.map(line -> ProjectId.ofRepoIdOrNull(line.getC_Project_ID()))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (projectIds.size() == 1)
		{
			inout.setC_Project_ID(Objects.requireNonNull(projectIds.iterator().next()).getRepoId());
		}
		else if (projectIds.size() > 1)
		{
			log.debug("InOut {} has multiple projects {}, not setting header project",
					inout.getM_InOut_ID(), projectIds);
			inout.setC_Project_ID(ProjectId.toRepoId(null));
		}
	}
}
