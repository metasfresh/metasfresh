/*
 * #%L
 * de.metas.banking.camt53
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

package de.metas.banking.camt53.wrapper;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.money.Money;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface IStatementLineWrapper
{
	@NonNull
	ImmutableSet<String> getDocumentReferenceCandidates();

	@NonNull
	String getUnstructuredRemittanceInfo();

	@NonNull
	Optional<ZonedDateTime> getStatementLineDate(@NonNull ZoneId zoneId);

	@NonNull
	Optional<Money> getInterestAmount();

	@NonNull
	Optional<BigDecimal> getCurrencyRate();

	@NonNull
	String getLineDescription();

	@NonNull
	Amount getStatementAmount();

	/**
	 * @return true if this is a "credit" line (i.e. we get money)
	 */
	boolean isCRDT();

	@Nullable
	String getAcctSvcrRef();

	@NonNull
	String getDbtrNames();

	@NonNull
	String getCdtrNames();

	@Nullable
	String getLineReference();

	boolean isBatchTransaction();

	List<ITransactionDtlsWrapper> getTransactionDtlsWrapper();
}
