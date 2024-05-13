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

package de.metas.contracts.modular.interest;

import com.google.common.collect.ImmutableMap;
import de.metas.contracts.model.I_ModCntr_Interest;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ModularLogInterestRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModularLogInterest create(@NonNull final CreateModularLogInterestRequest request)
	{
		final I_ModCntr_Interest record = InterfaceWrapperHelper.newInstance(I_ModCntr_Interest.class);

		record.setC_Invoice_ID(InvoiceId.toRepoId(request.getAllocatedToInterimInvoiceId()));
		record.setModCntr_Log_ID(request.getLogId().getRepoId());
		final Money allocatedAmt = request.getAllocatedAmt();
		record.setMatchedAmt(allocatedAmt.toBigDecimal());
		record.setC_Currency_ID(allocatedAmt.getCurrencyId().getRepoId());

		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	public ModularLogInterest save(@NonNull final ModularLogInterest request)
	{
		final Money allocatedAmt = request.getAllocatedAmt();
		final Money finalInterest = request.getFinalInterest();
		Money.assertSameCurrency(allocatedAmt, finalInterest);
		final I_ModCntr_Interest record = InterfaceWrapperHelper.load(request.getInterestLogId(), I_ModCntr_Interest.class);

		record.setC_Invoice_ID(InvoiceId.toRepoId(request.getAllocatedToInterimInvoiceId()));
		record.setModCntr_Log_ID(request.getLogId().getRepoId());
		record.setMatchedAmt(allocatedAmt.toBigDecimal());
		record.setInterestScore(request.getInterestScore());
		record.setFinalInterest(Money.toBigDecimalOrZero(finalInterest));
		record.setC_Currency_ID(allocatedAmt.getCurrencyId().getRepoId());

		return ofRecord(record);
	}

	public void deleteByQuery(@NonNull final LogInterestQuery query)
	{
		//todo
	}

	private static ModularLogInterest ofRecord(@NonNull final I_ModCntr_Interest interest)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(interest.getC_Currency_ID());
		return ModularLogInterest.builder()
				.interestLogId(ModularInterestLogId.ofRepoId(interest.getModCntr_Interest_ID()))
				.logId(ModularContractLogEntryId.ofRepoId(interest.getModCntr_Log_ID()))
				.allocatedAmt(Money.of(interest.getMatchedAmt(), currencyId))
				.finalInterest(Money.ofOrNull(interest.getFinalInterest(), currencyId))
				.interestScore(interest.getInterestScore())
				.allocatedToInterimInvoiceId(InvoiceId.ofRepoIdOrNull(interest.getC_Invoice_ID()))
				.build();
	}

	@NonNull
	public Optional<Money> getTotalInterest(@NonNull final LogInterestQuery query)
	{
		final IQueryBuilder<I_ModCntr_Log> builder = getQueryBuilder(query);
		final ImmutableMap<CurrencyId, Money> currenciesMap = builder
				.create()
				.sumMoney(I_ModCntr_Interest.COLUMNNAME_FinalInterest, I_ModCntr_Interest.COLUMNNAME_C_Currency_ID)
				.stream()
				.collect(Money.sumByCurrency());
		if (currenciesMap.isEmpty())
		{
			return Optional.empty();
		}
		Check.assumeEquals(currenciesMap.size(), 1);
		return currenciesMap.values().stream().findFirst();
	}

	private IQueryBuilder<I_ModCntr_Log> getQueryBuilder(final @NonNull LogInterestQuery query)
	{
		final IQueryBuilder<I_ModCntr_Log> builder = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.setOnlySelection(query.getLogSelection())
				.andCollect(I_ModCntr_Interest.COLUMN_ModCntr_Log_ID);
		if (query.getInterestBasedOnInterim() != null)
		{
			if (query.getInterestBasedOnInterim())
			{
				builder.addNotNull(I_ModCntr_Interest.COLUMNNAME_C_Invoice_ID);
			}
			else
			{
				builder.addIsNull(I_ModCntr_Interest.COLUMNNAME_C_Invoice_ID);
			}
		}
		return builder;
	}

	@Value
	@Builder
	public static class LogInterestQuery
	{
		@NonNull PInstanceId logSelection;
		@Nullable Boolean interestBasedOnInterim;
	}
}
