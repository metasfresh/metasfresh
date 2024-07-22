/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_ModCntr_Interest_Run;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.modular.interest.InterestBonusComputationRequest;
import de.metas.contracts.modular.interest.InterestService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class ModCntr_Interest_Run_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final ModCntr_InvoicingGroup_StepDefData invoicingGroupTable;
	@NonNull private final ModCntr_Interest_Run_StepDefData interestRunTable;
	@NonNull private final AD_User_StepDefData userTable;
	@NonNull private final InterestService interestService;

	@And("^load latest ModCntr_Interest_Run for invoicing group (.*) as (.*)$")
	public void metasfresh_contains_ModCntr_InvoicingGroup(@NonNull final String invoicingGroupIdentifier, @NonNull final String interestRunIdentifier)
	{
		final I_ModCntr_InvoicingGroup invoicingGroup = invoicingGroupTable.get(invoicingGroupIdentifier);

		final I_ModCntr_Interest_Run lastInterestRun = queryBL.createQueryBuilder(I_ModCntr_Interest_Run.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Interest_Run.COLUMNNAME_ModCntr_InvoicingGroup_ID, invoicingGroup.getModCntr_InvoicingGroup_ID())
				.orderByDescending(I_ModCntr_Interest_Run.COLUMNNAME_Created)
				.first();
		Check.assumeNotNull(lastInterestRun, "Found no interest run for invoicing group {}", invoicingGroupIdentifier);
		interestRunTable.putOrReplace(interestRunIdentifier, lastInterestRun);
	}

	@And("distribute interest")
	public void distributeInterest(@NonNull final DataTable dataTable)
	{
		if (dataTable.height() != 1)
		{
			throw new AdempiereException("Can only distribute interest for a single request");
		}

		final InterestBonusComputationRequest computationRequest = getRequest(DataTableRow.singleRow(dataTable));
		try
		{
			interestService.distributeInterestAndBonus(computationRequest);
		}
		finally
		{
			computationRequest.getInvolvedModularLogsLock().unlockAll();
		}
	}

	private InterestBonusComputationRequest getRequest(final DataTableRow tableRow)
	{
		final I_ModCntr_InvoicingGroup invoicingGroup = invoicingGroupTable.get(tableRow.getAsIdentifier(I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID));
		final InvoicingGroupId invoicingGroupId = InvoicingGroupId.ofRepoId(invoicingGroup.getModCntr_InvoicingGroup_ID());
		final Timestamp billingDate = tableRow.getAsTimestamp(I_ModCntr_Interest_Run.COLUMNNAME_BillingDate);
		final Timestamp interimDate = tableRow.getAsTimestamp(I_ModCntr_Interest_Run.COLUMNNAME_InterimDate);
		final Money interestToDistribute = Money.of(invoicingGroup.getTotalInterest(), CurrencyId.ofRepoId(invoicingGroup.getC_Currency_ID()));
		final UserId userId = userTable.getUserId(tableRow.getAsIdentifier(I_AD_User.COLUMNNAME_AD_User_ID));

		return InterestBonusComputationRequest.builder()
				.invoicingGroupId(invoicingGroupId)
				.interimDate(interimDate.toInstant())
				.billingDate(billingDate.toInstant())
				.interestToDistribute(interestToDistribute)
				.userId(userId)
				.build();
	}
}
