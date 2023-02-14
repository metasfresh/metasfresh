package de.metas.acct.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.acct.Account;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class AcctSchemaGeneralLedger
{
	boolean suspenseBalancing;
	Account suspenseBalancingAcct;

	boolean currencyBalancing;
	Account currencyBalancingAcct;

	@NonNull
	Account intercompanyDueToAcct;
	@NonNull
	Account intercompanyDueFromAcct;

	@NonNull
	Account incomeSummaryAcct;
	@NonNull
	Account retainedEarningAcct;
	
	@NonNull
	Account purchasePriceVarianceOffsetAcct;

	@NonNull
	public Account getDueToAcct(final AcctSchemaElementType segment)
	{
		return intercompanyDueToAcct;
	}

	@NonNull
	public Account getDueFromAcct(final AcctSchemaElementType segment)
	{
		return intercompanyDueFromAcct;
	}
}
