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
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Criteria of {@link IOrderDAO#getOrderIds(OrderQuery)}. Every criterion is optional; only the ones that are set are applied.
 * <p>
 * Not to be confused with the deprecated {@link OrderCriteria}.
 */
@Value
@Builder
public class OrderQuery
{
	/** {@code C_Order.DocStatus} */
	@Nullable DocStatus docStatus;

	/** {@code C_Order.IsSOTrx} */
	@Nullable SOTrx soTrx;

	/** If set, {@code C_Order.C_DocType_ID} has to be one of these (an empty set matches nothing). */
	@Nullable ImmutableSet<DocTypeId> docTypeIds;

	/** If set, {@code C_Order.DocumentNo} has to be one of these (an empty set matches nothing), e.g. the words of a bank line's text. */
	@Nullable ImmutableSet<String> documentNos;

	/** If set, has to equal {@code C_Order.C_Currency_ID} */
	@Nullable CurrencyId currencyId;

	/** If set, the order's {@code C_Order.Bill_BPartner_ID} or {@code C_Order.C_BPartner_ID} has to be this partner. */
	@Nullable BPartnerId bpartnerId;

	/** If set, has to equal {@code C_Order.GrandTotal} */
	@Nullable BigDecimal amount;
}
