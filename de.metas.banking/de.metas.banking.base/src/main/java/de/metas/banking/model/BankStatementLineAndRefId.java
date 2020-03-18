package de.metas.banking.model;

import java.util.Objects;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class BankStatementLineAndRefId
{
	private static final Logger logger = LogManager.getLogger(BankStatementLineAndRefId.class);

	public static BankStatementLineAndRefId ofRepoIdsOrNull(final int bankStatementLineRepoId, final int bankStatementLineRefRepoId)
	{
		if (bankStatementLineRefRepoId <= 0)
		{
			return null;
		}
		if (bankStatementLineRepoId <= 0)
		{
			logger.warn("ofRepoIdsOrNull: Possible development error: bankStatementLineRefRepoId={} while bankStatementLineRepoId={}. Returning null", bankStatementLineRefRepoId, bankStatementLineRepoId);
			return null;
		}
		return ofRepoIds(bankStatementLineRepoId, bankStatementLineRefRepoId);
	}

	public static BankStatementLineAndRefId ofRepoIds(final int bankStatementLineRepoId, final int bankStatementLineRefRepoId)
	{
		return of(BankStatementLineId.ofRepoId(bankStatementLineRepoId), bankStatementLineRefRepoId);
	}

	public static BankStatementLineAndRefId of(final BankStatementLineId bankStatementLineId, final int bankStatementLineRefRepoId)
	{
		return new BankStatementLineAndRefId(bankStatementLineId, bankStatementLineRefRepoId);
	}

	BankStatementLineId bankStatementLineId;
	int bankStatementLineRefRepoId;

	private BankStatementLineAndRefId(
			@NonNull final BankStatementLineId bankStatementLineId,
			final int bankStatementLineRefRepoId)
	{
		Check.assumeGreaterThanZero(bankStatementLineRefRepoId, "C_BankStatementLine_Ref_ID");

		this.bankStatementLineId = bankStatementLineId;
		this.bankStatementLineRefRepoId = bankStatementLineRefRepoId;
	}

	public static boolean equals(final BankStatementLineAndRefId o1, final BankStatementLineAndRefId o2)
	{
		return Objects.equals(o1, o2);
	}
}
