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

import de.metas.contracts.modular.interest.InterestScore;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public class ModularLogInterest
{
	@NonNull ModularInterestLogId interestLogId;
	@NonNull ModularContractLogEntryId shippingNotificationLogId;
	@NonNull InterestRunId interestRunId;
	@NonNull Money allocatedAmt;
	@NonNull Integer interestDays;

	@Nullable ModularContractLogEntryId interimInvoiceLogId;
	@Nullable Money finalInterest;

	@Builder(toBuilder = true)
	public ModularLogInterest(
			@NonNull final ModularInterestLogId interestLogId,
			@NonNull final ModularContractLogEntryId shippingNotificationLogId,
			@NonNull final InterestRunId interestRunId,
			@NonNull final Money allocatedAmt,
			@NonNull final Integer interestDays,
			@Nullable final ModularContractLogEntryId interimInvoiceLogId,
			@Nullable final Money finalInterest)
	{
		if (finalInterest != null)
		{
			finalInterest.assertCurrencyId(allocatedAmt.getCurrencyId());
		}

		this.interestLogId = interestLogId;
		this.shippingNotificationLogId = shippingNotificationLogId;
		this.interestRunId = interestRunId;
		this.allocatedAmt = allocatedAmt;
		this.interestDays = interestDays;
		this.interimInvoiceLogId = interimInvoiceLogId;
		this.finalInterest = finalInterest;
	}

	@NonNull
	public ModularLogInterest withFinalInterest(@NonNull final Money finalInterest)
	{
		return toBuilder().finalInterest(finalInterest).build();
	}

	@NonNull
	public InterestScore getInterestScore()
	{
		return InterestScore.of(allocatedAmt, interestDays);
	}

	@NonNull
	public BigDecimal getInterestScoreEnsuringCurrency(@NonNull final CurrencyId currencyId)
	{
		final InterestScore interestScore = getInterestScore();
		interestScore.assertCurrency(currencyId);
		return interestScore.getScore();
	}
}
