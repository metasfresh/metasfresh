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

import de.metas.contracts.model.I_ModCntr_Interest;
import de.metas.contracts.model.I_ModCntr_Interest_Run;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.modular.interest.EnqueueInterestComputationRequest;
import de.metas.contracts.modular.interest.InterestService;
import de.metas.contracts.modular.interest.log.ModularLogInterest;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ModCntr_Interest_Run_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final ModCntr_InvoicingGroup_StepDefData invoicingGroupTable;
	@NonNull private final ModCntr_Interest_Run_StepDefData interestRunTable;
	@NonNull private final AD_User_StepDefData userTable;
	@NonNull private final ModCntr_Log_StepDefData logTable;

	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final InterestService interestService = SpringContextHolder.instance.getBean(InterestService.class);
	private final ModularLogInterestRepository modularLogInterestRepository = SpringContextHolder.instance.getBean(ModularLogInterestRepository.class);

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
		interestService.distributeInterestAndBonus(getRequest(DataTableRow.singleRow(dataTable)));
	}

	@And("^validate created interestRun records for (.*)$")
	public void verifyInterest(@NonNull final String interestRunIdentifier, @NonNull final DataTable dataTable)
	{
		final InterestRunId interestRunId = InterestRunId.ofRepoId(interestRunTable.get(interestRunIdentifier).getModCntr_Interest_Run_ID());
		// make sure it's modifiable, we want to be able to remove matched records from it
		final ArrayList<ModularLogInterest> modularLogInterestsForRun = new ArrayList<>(modularLogInterestRepository.getModularLogInterestsForRun(interestRunId));
		DataTableRows.of(dataTable).forEach(row -> validateInterestRow(row, modularLogInterestsForRun));
	}

	private void validateInterestRow(@NonNull final DataTableRow row, @NonNull final List<ModularLogInterest> modularLogInterestsForRun)
	{
		final ModularLogInterest modularLogInterest = modularLogInterestsForRun.stream()
				.filter(interest -> isRowMatchingInterestRecord(row, interest))
				.findFirst()
				.orElseThrow(() -> new AdempiereException(getNoMatchingInterestRowMessage(row.getAsIdentifier(I_ModCntr_Interest.COLUMNNAME_ModCntr_Interest_ID), modularLogInterestsForRun)));

		modularLogInterestsForRun.remove(modularLogInterest);
	}

	private static @NonNull String getNoMatchingInterestRowMessage(final @NonNull StepDefDataIdentifier rowIdentifier, final @NonNull List<ModularLogInterest> modularLogInterestsForRun)
	{
		final StringBuilder message = new StringBuilder("No interest record found for ID: ");
		message.append(rowIdentifier)
				.append(" in the following rows: ");
		for (final ModularLogInterest modularLogInterest : modularLogInterestsForRun)
		{
			message.append("\n ShippingNotificationLogId: ").append(modularLogInterest.getShippingNotificationLogId())
					.append(", InterimContractLogId: ").append(modularLogInterest.getInterimContractLogId())
					.append(", InterestDays ").append(modularLogInterest.getInterestDays())
					.append(", FinalInterest ").append(modularLogInterest.getFinalInterest());

		}

		return message.toString();
	}

	private boolean isRowMatchingInterestRecord(final @NonNull DataTableRow row, @NonNull final ModularLogInterest interest)
	{
		final ModularContractLogEntryId shippingNotificationLogId = ModularContractLogEntryId.ofRepoId(logTable.get(row.getAsIdentifier(I_ModCntr_Interest.COLUMNNAME_ShippingNotification_ModCntr_Log_ID)).getModCntr_Log_ID());
		final int interestDays = row.getAsInt(I_ModCntr_Interest.COLUMNNAME_InterestDays);
		final BigDecimal finalInterestBD = row.getAsBigDecimal(I_ModCntr_Interest.COLUMNNAME_FinalInterest);
		final CurrencyId currencyId = getCurrencyIdByCurrencyISO(row.getAsString("C_Currency.ISO_Code"));
		final Money finalInterest = Money.of(finalInterestBD, currencyId);

		final Boolean interimContractMatches = row.getAsOptionalIdentifier(I_ModCntr_Interest.COLUMNNAME_InterimContract_ModCntr_Log_ID)
				.map(identifier -> ModularContractLogEntryId.ofRepoId(logTable.get(identifier).getModCntr_Log_ID()))
				.map(logId -> logId.equals(interest.getInterimContractLogId()))
				.orElse(interest.getInterimContractLogId() == null);

		return interimContractMatches &&
				shippingNotificationLogId.equals(interest.getShippingNotificationLogId()) &&
				interest.getInterestDays() == interestDays &&
				finalInterest.equals(interest.getFinalInterest());
	}

	@NonNull
	private CurrencyId getCurrencyIdByCurrencyISO(@NonNull final String currencyISO)
	{
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(currencyISO);
		return currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCode);
	}

	private EnqueueInterestComputationRequest getRequest(final DataTableRow tableRow)
	{
		final I_ModCntr_InvoicingGroup invoicingGroup = invoicingGroupTable.get(tableRow.getAsIdentifier(I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID));
		final InvoicingGroupId invoicingGroupId = InvoicingGroupId.ofRepoId(invoicingGroup.getModCntr_InvoicingGroup_ID());
		final Instant billingDate = tableRow.getAsTimestamp(I_ModCntr_Interest_Run.COLUMNNAME_BillingDate).toInstant();
		final Instant interimDate = tableRow.getAsTimestamp(I_ModCntr_Interest_Run.COLUMNNAME_InterimDate).toInstant();
		final Money interestToDistribute = Money.of(invoicingGroup.getTotalInterest(), CurrencyId.ofRepoId(invoicingGroup.getC_Currency_ID()));
		final UserId userId = userTable.getUserId(tableRow.getAsIdentifier(I_AD_User.COLUMNNAME_AD_User_ID));

		return EnqueueInterestComputationRequest.builder()
				.billingDate(billingDate)
				.interimDate(interimDate)
				.interestToDistribute(interestToDistribute)
				.userId(userId)
				.invoicingGroupId(invoicingGroupId)
				.build();
	}

}
