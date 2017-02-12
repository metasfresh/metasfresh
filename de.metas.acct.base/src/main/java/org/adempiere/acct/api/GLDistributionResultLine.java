package org.adempiere.acct.api;

import java.math.BigDecimal;

import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Result line of a {@link GLDistributionBuilder} run.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GLDistributionResultLine
{
	private IAccountDimension accountDimension = AccountDimension.NULL;
	private BigDecimal percent = BigDecimal.ZERO;
	private BigDecimal amount = BigDecimal.ZERO;
	private int currencyId = -1;
	private BigDecimal qty = BigDecimal.ZERO;
	private String description = null;

	GLDistributionResultLine()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	public IAccountDimension getAccountDimension()
	{
		return accountDimension;
	}

	void setAccountDimension(IAccountDimension accountDimension)
	{
		Check.assumeNotNull(accountDimension, "accountDimension not null");
		this.accountDimension = accountDimension;
	}

	void setPercent(BigDecimal percent)
	{
		this.percent = percent;
	}

	public BigDecimal getPercent()
	{
		return percent;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	void setC_Currency_ID(final int currencyId)
	{
		this.currencyId = currencyId;
	}

	public int getC_Currency_ID()
	{
		return currencyId;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	void setQty(BigDecimal qty)
	{
		this.qty = qty;
	}
}
