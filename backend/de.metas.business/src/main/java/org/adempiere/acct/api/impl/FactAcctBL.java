package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.Account;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.impl.FactAcctDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class FactAcctBL implements IFactAcctBL
{
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	@Override
	public Account getAccount(@NonNull final I_Fact_Acct factAcct)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(factAcct);
		final AccountDimension accountDimension = IFactAcctBL.extractAccountDimension(factAcct);
		return Account.ofId(AccountId.ofRepoId(MAccount.get(ctx, accountDimension).getC_ValidCombination_ID()))
				.withAccountConceptualName(FactAcctDAO.extractAccountConceptualName(factAcct));
	}

	@Override
	public Optional<Money> getAcctBalance(@NonNull final List<FactAcctQuery> queries)
	{
		final List<I_Fact_Acct> factLines = factAcctDAO.list(queries);
		if (factLines.isEmpty())
		{
			return Optional.empty();
		}

		final AcctSchemaId acctSchemaId = factLines.stream()
				.map(factLine -> AcctSchemaId.ofRepoId(factLine.getC_AcctSchema_ID()))
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Mixing multiple Accounting Schemas when summing amounts is not allowed")));
		final CurrencyId acctCurrencyId = Services.get(IAcctSchemaBL.class).getAcctCurrencyId(acctSchemaId);

		final BigDecimal acctBalanceBD = factLines.stream()
				.map(factLine -> factLine.getAmtAcctDr().subtract(factLine.getAmtAcctCr()))
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		return Optional.of(Money.of(acctBalanceBD, acctCurrencyId));
	}

	@Override
	public Stream<I_Fact_Acct> stream(@NonNull final FactAcctQuery query)
	{
		return factAcctDAO.stream(query);
	}
}
