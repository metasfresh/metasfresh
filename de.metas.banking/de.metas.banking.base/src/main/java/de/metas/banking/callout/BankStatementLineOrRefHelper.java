/**
 * 
 */
package de.metas.banking.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_C_BankStatementLine;

import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BankStatementLineOrRefHelper
{
	public static void setBankStatementLineOrRefAmountsToZero(@NonNull final I_C_BankStatementLine line)
	{
		line.setDiscountAmt(BigDecimal.ZERO);
		line.setWriteOffAmt(BigDecimal.ZERO);
		line.setOverUnderAmt(BigDecimal.ZERO);
		line.setIsOverUnderPayment(false);
	}

	@Builder
	@Value
	private static class InvoiceInfoVO
	{
		private final CurrencyId currencyId;
		private final int bpartnerId;
		private final int invoiceId;
		private final BigDecimal openAmt;
		private final BigDecimal discountAmt;
	}

	@Builder
	@Value
	private static class PreparedStatementParamsForInvoice
	{
		private final int invoicePayScheduleId;
		private final int invoiceId;
		private final Timestamp dateTrx;
	}

	@Data
	@Builder
	private static class Amounts
	{
		private BigDecimal invoiceOpenAmt;
		private BigDecimal discountAmt;
		private BigDecimal writeOffAmt;
		private BigDecimal overUnderAmt;
		private BigDecimal payAmt;
	}
}
