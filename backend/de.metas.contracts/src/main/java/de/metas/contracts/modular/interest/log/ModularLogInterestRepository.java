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

package de.metas.contracts.modular.interest.log;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_ModCntr_Interest;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class ModularLogInterestRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ModularLogInterest create(@NonNull final CreateModularLogInterestRequest request)
	{
		final I_ModCntr_Interest record = InterfaceWrapperHelper.newInstance(I_ModCntr_Interest.class);

		record.setModCntr_Interest_Run_ID(request.getInterestRunId().getRepoId());
		record.setShippingNotification_ModCntr_Log_ID(request.getShippingNotificationLogId().getRepoId());
		record.setInterimInvoice_ModCntr_Log_ID(ModularContractLogEntryId.toRepoId(request.getInterimInvoiceLogId()));

		final Money allocatedAmt = request.getAllocatedAmt();
		record.setMatchedAmt(allocatedAmt.toBigDecimal());
		record.setC_Currency_ID(allocatedAmt.getCurrencyId().getRepoId());

		record.setInterestDays(request.getInterestDays());
		record.setInterestScore(request.getInterestScore().getScore());
		record.setFinalInterest(Money.toBigDecimalOrZero(request.getFinalInterest()));

		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	@NonNull
	public ModularLogInterest save(@NonNull final ModularLogInterest request)
	{
		final I_ModCntr_Interest record = InterfaceWrapperHelper.load(request.getInterestLogId(), I_ModCntr_Interest.class);

		record.setModCntr_Interest_Run_ID(request.getInterestRunId().getRepoId());
		record.setShippingNotification_ModCntr_Log_ID(request.getShippingNotificationLogId().getRepoId());
		record.setInterimInvoice_ModCntr_Log_ID(ModularContractLogEntryId.toRepoId(request.getInterimInvoiceLogId()));

		final Money allocatedAmt = request.getAllocatedAmt();
		record.setMatchedAmt(allocatedAmt.toBigDecimal());
		record.setC_Currency_ID(allocatedAmt.getCurrencyId().getRepoId());

		record.setInterestDays(request.getInterestDays());
		record.setInterestScore(request.getInterestScore().getScore());
		record.setFinalInterest(Money.toBigDecimalOrZero(request.getFinalInterest()));

		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	public void deleteByQuery(@NonNull final LogInterestQuery query)
	{
		getQueryBuilder(query).create().delete();
	}

	@NonNull
	public List<ModularLogInterest> getForShippingNotificationLogId(
			@NonNull final InterestRunId interestRunId,
			@NonNull final ModularContractLogEntryId shippingNotificationLogId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Interest.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Interest.COLUMNNAME_ModCntr_Interest_Run_ID, interestRunId)
				.addEqualsFilter(I_ModCntr_Interest.COLUMNNAME_ShippingNotification_ModCntr_Log_ID, shippingNotificationLogId)
				.create()
				.stream()
				.map(ModularLogInterestRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ModularLogInterest ofRecord(@NonNull final I_ModCntr_Interest interest)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(interest.getC_Currency_ID());
		return ModularLogInterest.builder()
				.interestLogId(ModularInterestLogId.ofRepoId(interest.getModCntr_Interest_ID()))
				.interestRunId(InterestRunId.ofRepoId(interest.getModCntr_Interest_Run_ID()))
				.shippingNotificationLogId(ModularContractLogEntryId.ofRepoId(interest.getShippingNotification_ModCntr_Log_ID()))
				.interimInvoiceLogId(ModularContractLogEntryId.ofRepoIdOrNull(interest.getInterimInvoice_ModCntr_Log_ID()))

				.allocatedAmt(Money.of(interest.getMatchedAmt(), currencyId))

				.interestDays(interest.getInterestDays())
				.finalInterest(Money.ofOrNull(interest.getFinalInterest(), currencyId))
				.build();
	}

	@NonNull
	public Stream<ModularLogInterest> streamInterestEntries(final @NonNull LogInterestQuery query)
	{
		return getQueryBuilder(query)
				.create()
				.iterateAndStream()
				.map(ModularLogInterestRepository::ofRecord);
	}

	@NonNull
	private IQueryBuilder<I_ModCntr_Interest> getQueryBuilder(final @NonNull LogInterestQuery query)
	{
		return setSelectionOnQueryBuilder(query, queryBL.createQueryBuilder(I_ModCntr_Log.class))
				.andCollectChildren(I_ModCntr_Interest.COLUMN_ShippingNotification_ModCntr_Log_ID);
	}

	private static IQueryBuilder<I_ModCntr_Log> setSelectionOnQueryBuilder(final @NonNull LogInterestQuery query, final IQueryBuilder<I_ModCntr_Log> modCntrLogQueryBuilder)
	{
		return modCntrLogQueryBuilder.setOnlySelection(query.modularLogSelection());
	}

	@Builder
	public record LogInterestQuery(@NonNull PInstanceId modularLogSelection)
	{
	}
}
