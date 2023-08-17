/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_C_BPartner_InterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractUpsertRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

public class C_BPartner_InterimContract_Upsert extends JavaProcess implements IProcessPrecondition
{
	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);

	@Param(parameterName = I_C_BPartner_InterimContract.COLUMNNAME_C_Harvesting_Calendar_ID, mandatory = true)
	private int p_C_Harvesting_Calendar_ID;

	@Param(parameterName = I_C_BPartner_InterimContract.COLUMNNAME_Harvesting_Year_ID, mandatory = true)
	private int p_Harvesting_Year_ID;

	@Param(parameterName = I_C_BPartner_InterimContract.COLUMNNAME_IsInterimContract, mandatory = true)
	private boolean p_IsInterimContract;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The process only runs with C_BPartner table");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(getRecord_ID());

		final BPartnerInterimContractUpsertRequest request = BPartnerInterimContractUpsertRequest.builder()
				.bPartnerId(bPartnerId)
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(YearId.ofRepoId(p_Harvesting_Year_ID), CalendarId.ofRepoId(p_C_Harvesting_Calendar_ID)))
				.isInterimContract(p_IsInterimContract)
				.build();

		bPartnerInterimContractService.upsert(request);

		return MSG_OK;
	}
}
