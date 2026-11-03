package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupTermService;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/** Fills contract status, contract date and master start date of compensation-group contract terms when they are completed. */
@Interceptor(I_C_Flatrate_Term.class)
@Component
@RequiredArgsConstructor
public class C_Flatrate_Term_ContractCompensationGroup
{
	@NonNull private final ContractCompensationGroupTermService termService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void setContractStatusAndDatesBeforeComplete(@NonNull final I_C_Flatrate_Term term)
	{
		if (TypeConditions.ofNullableCode(term.getType_Conditions()) != TypeConditions.COMPENSATION_GROUP)
		{
			return;
		}
		termService.setDefaultsBeforeComplete(term);
	}
}
