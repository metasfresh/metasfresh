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
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class CustomerTradeMarginId implements RepoIdAware
{
	@NonNull
	@JsonCreator
	public static CustomerTradeMarginId ofRepoId(final int repoId)
	{
		return new CustomerTradeMarginId(repoId);
	}

	@Nullable
	public static CustomerTradeMarginId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CustomerTradeMarginId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CustomerTradeMarginId repoId)
	{
		return repoId != null ? repoId.getRepoId() : -1;
	}

	int repoId;

	private CustomerTradeMarginId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Customer_Trade_Margin.COLUMNNAME_C_Customer_Trade_Margin_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
