package de.metas.contracts.compensationGroup.contract.process;

import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupTermService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

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

/**
 * Daily (scheduled) update of the contract status of completed compensation-group contracts; see
 * {@link ContractCompensationGroupTermService#updateContractStatusOfCompletedTerms()}.
 */
public class C_Flatrate_Term_CompensationGroup_UpdateContractStatus extends JavaProcess
{
	private final ContractCompensationGroupTermService termService = SpringContextHolder.instance.getBean(ContractCompensationGroupTermService.class);

	@Override
	protected String doIt()
	{
		final int updatedCount = termService.updateContractStatusOfCompletedTerms();
		addLog("Updated the contract status of {} compensation-group contract terms", updatedCount);
		return MSG_OK;
	}
}
