package de.metas.acct.gldistribution;

import java.math.BigDecimal;

import de.metas.acct.api.AccountDimension;
import de.metas.money.CurrencyId;
import lombok.Data;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Result line of a {@link GLDistributionBuilder} run.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
public class GLDistributionResultLine
{
	public enum Sign
	{
		DEBIT, CREDIT,

		/** choose whatever side to make the mount positive */
		DETECT
	};

	@NonNull
	private AccountDimension accountDimension = AccountDimension.NULL;
	private BigDecimal percent = BigDecimal.ZERO;

	private BigDecimal amount = BigDecimal.ZERO;

	private Sign amountSign = Sign.DETECT;

	private CurrencyId currencyId;
	private BigDecimal qty = BigDecimal.ZERO;
	private String description = null;
}
