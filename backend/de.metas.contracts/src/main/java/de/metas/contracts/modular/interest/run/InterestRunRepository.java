/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest.run;

import de.metas.contracts.model.I_ModCntr_Interest_Run;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

@Repository
public class InterestRunRepository
{
	@NonNull
	public InterestRunId create(@NonNull final CreateInterestRunRequest request)
	{
		final I_ModCntr_Interest_Run record = InterfaceWrapperHelper.newInstance(I_ModCntr_Interest_Run.class);

		record.setInterimDate(TimeUtil.asTimestamp(request.getInterimDate()));
		record.setBillingDate(TimeUtil.asTimestamp(request.getBillingDate()));
		record.setModCntr_InvoicingGroup_ID(request.getInvoicingGroupId().getRepoId());
		record.setTotalInterest(request.getInterestToDistribute().toBigDecimal());
		record.setC_Currency_ID(request.getInterestToDistribute().getCurrencyId().getRepoId());

		InterfaceWrapperHelper.save(record);

		return InterestRunId.ofRepoId(record.getModCntr_Interest_Run_ID());
	}
}
