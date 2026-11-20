package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
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

/** On a change of {@code C_OrderLine.C_Project_ID}, see {@link ContractCompensationGroupService#copyPositionToCompensationLines}. */
@Interceptor(I_C_OrderLine.class)
@Component
@RequiredArgsConstructor
public class C_OrderLine_ContractCompensationGroup
{
	@NonNull private final ContractCompensationGroupService contractCompensationGroupService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Project_ID)
	public void copyPositionToCompensationLines(@NonNull final I_C_OrderLine orderLine)
	{
		contractCompensationGroupService.copyPositionToCompensationLines(orderLine);
	}
}
