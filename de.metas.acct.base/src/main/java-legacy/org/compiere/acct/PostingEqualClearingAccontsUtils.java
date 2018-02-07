package org.compiere.acct;

import java.math.BigDecimal;
import java.util.function.Predicate;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.acct.base
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

@UtilityClass
class PostingEqualClearingAccontsUtils
{
	public static void removeFactLinesIfEqual(
			final Fact fact,
			final FactLine dr,
			final FactLine cr,
			final Predicate<MAcctSchema> isInterOrg)
	{
		final MAcctSchema as = fact.getAcctSchema();
		if (as.isPostIfClearingEqual())
		{
			return;
		}

		if (dr == null || cr == null)
		{
			return;
		}

		if (isInterOrg.test(as))
		{
			return;
		}

		final MAccount acct_dr = dr.getAccount(); // not_invoiced_receipts
		final MAccount acct_cr = cr.getAccount(); // inventory_clearing
		if (!acct_dr.equals(acct_cr))
		{
			return;
		}

		BigDecimal debit = dr.getAmtAcctDr();
		BigDecimal credit = cr.getAmtAcctCr();
		if (debit.compareTo(credit) != 0)
		{
			return;
		}

		fact.remove(dr);
		fact.remove(cr);
	}

}
