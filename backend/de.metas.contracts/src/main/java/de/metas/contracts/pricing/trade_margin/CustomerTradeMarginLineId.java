/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.pricing.trade_margin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class CustomerTradeMarginLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	CustomerTradeMarginId customerTradeMarginId;

	@JsonCreator
	public static CustomerTradeMarginLineId ofRepoId(@NonNull final CustomerTradeMarginId customerTradeMarginId, final int repoId)
	{
		return new CustomerTradeMarginLineId(customerTradeMarginId, repoId);
	}

	@Nullable
	public static CustomerTradeMarginLineId ofRepoIdOrNull(@Nullable final CustomerTradeMarginId customerTradeMarginId, final int repoId)
	{
		return customerTradeMarginId != null && repoId > 0 ? new CustomerTradeMarginLineId(customerTradeMarginId, repoId) : null;
	}

	public static int toRepoId(@Nullable final CustomerTradeMarginLineId repoId)
	{
		return repoId != null ? repoId.getRepoId() : -1;
	}

	private CustomerTradeMarginLineId(@NonNull final CustomerTradeMarginId customerTradeMarginId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Customer_Trade_Margin_Line.COLUMNNAME_C_Customer_Trade_Margin_Line_ID);
		this.customerTradeMarginId = customerTradeMarginId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
