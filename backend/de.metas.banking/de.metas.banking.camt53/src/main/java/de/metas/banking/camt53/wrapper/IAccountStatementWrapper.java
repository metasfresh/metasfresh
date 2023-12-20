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

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface IAccountStatementWrapper
{
	@NonNull
	String getId();

	@NonNull
	ExplainedOptional<BankAccountId> getBPartnerBankAccountId();

	@NonNull
	ZonedDateTime getStatementDate(@NonNull ZoneId timeZone);

	@NonNull
	BigDecimal getBeginningBalance();

	@NonNull
	Optional<CurrencyCode> getStatementCurrencyCode();

	@NonNull
	ImmutableList<IStatementLineWrapper> getStatementLines();

	boolean hasNoBankStatementLines();
}
