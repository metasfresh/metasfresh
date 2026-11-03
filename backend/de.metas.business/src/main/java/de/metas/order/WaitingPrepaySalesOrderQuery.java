/*
 * #%L
 * de.metas.business
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

package de.metas.order;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankStatementLineId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Criteria of {@link IOrderDAO#getWaitingPrepaySalesOrderIds(WaitingPrepaySalesOrderQuery)}.
 */
@Value
@Builder
public class WaitingPrepaySalesOrderQuery
{
	/** Candidate {@code C_Order.DocumentNo} values, e.g. the words of a bank line's text. */
	@NonNull ImmutableSet<String> documentNos;

	/** {@code C_Order.C_Currency_ID} */
	@NonNull CurrencyId currencyId;

	/** If set, the order's {@code C_Order.Bill_BPartner_ID} or {@code C_Order.C_BPartner_ID} has to be this partner. */
	@Nullable BPartnerId bpartnerId;

	/** Has to equal {@code C_Order.GrandTotal} */
	@NonNull BigDecimal amount;

	/** Orders carried by this bank statement line are not excluded by the "already on an unprocessed bank statement line" rule. */
	@Nullable BankStatementLineId currentBankStatementLineId;
}
