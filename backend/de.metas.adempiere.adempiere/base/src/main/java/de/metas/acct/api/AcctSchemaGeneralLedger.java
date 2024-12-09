package de.metas.acct.api;

<<<<<<< HEAD
=======
import de.metas.acct.Account;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
<<<<<<< HEAD
	AccountId suspenseBalancingAcctId;

	boolean currencyBalancing;
	AccountId currencyBalancingAcctId;

	@NonNull
	AccountId intercompanyDueToAcctId;
	@NonNull
	AccountId intercompanyDueFromAcctId;

	@NonNull
	AccountId incomeSummaryAcctId;
	@NonNull
	AccountId retainedEarningAcctId;
	
	@NonNull
	AccountId purchasePriceVarianceOffsetAcctId;

	public AccountId getDueToAcctId(final AcctSchemaElementType segment)
	{
		return intercompanyDueToAcctId;
	}

	public AccountId getDueFromAcct(final AcctSchemaElementType segment)
	{
		return intercompanyDueFromAcctId;
=======
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
	Account cashRoundingAcct;

	@NonNull
	public Account getDueToAcct(final AcctSchemaElementType segment)
	{
		return intercompanyDueToAcct;
	}

	@NonNull
	public Account getDueFromAcct(final AcctSchemaElementType segment)
	{
		return intercompanyDueFromAcct;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
