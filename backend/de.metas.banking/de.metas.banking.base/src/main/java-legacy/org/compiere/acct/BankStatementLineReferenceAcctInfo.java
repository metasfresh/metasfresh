/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.compiere.acct;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class BankStatementLineReferenceAcctInfo
{
	@NonNull BankStatementAndLineAndRefId id;
	@NonNull BPartnerId bpartnerId;
	@NonNull OrgId paymentOrgId;
	@NonNull Money trxAmt;
	@NonNull CurrencyConversionContext currencyConversionContext;

	public BankStatementLineRefId getBankStatementLineRefId()
	{
		return getId().getBankStatementLineRefId();
	}

}
