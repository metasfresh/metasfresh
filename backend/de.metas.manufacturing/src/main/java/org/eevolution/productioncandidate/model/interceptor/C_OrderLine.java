/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.model.interceptor;

import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.eevolution.productioncandidate.service.SimulatedPPOrderCandidateCleanUpService;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	@NonNull
	private final SimulatedPPOrderCandidateCleanUpService simulatedPPOrderCandidateCleanUpService;

	public C_OrderLine(final @NonNull SimulatedPPOrderCandidateCleanUpService simulatedPPOrderCandidateCleanUpService)
	{
		this.simulatedPPOrderCandidateCleanUpService = simulatedPPOrderCandidateCleanUpService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeSimulatedPPOrderCandidates(final I_C_OrderLine orderLine)
	{
		simulatedPPOrderCandidateCleanUpService.deleteSimulatedPPOrderCandidateFor(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
	}
}
