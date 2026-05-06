package de.metas.datev;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-datev
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
public class DATEVExportConfig
{
	int id;
	@NonNull String clientNumber;
	@NonNull String advisorNumber;
	@NonNull String chartOfAccounts;
	int chartOfAccountsNumberLength;

	@Builder
	private DATEVExportConfig(
			final int id,
			@NonNull final String clientNumber,
			@NonNull final String advisorNumber,
			@NonNull final String chartOfAccounts,
			final int chartOfAccountsNumberLength)
	{

		this.id = id;
		this.clientNumber = clientNumber;
		this.advisorNumber = advisorNumber;
		this.chartOfAccounts = chartOfAccounts;
		this.chartOfAccountsNumberLength = chartOfAccountsNumberLength;
	}

}
