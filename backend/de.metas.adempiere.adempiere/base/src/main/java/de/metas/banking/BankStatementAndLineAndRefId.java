package de.metas.banking;

import java.util.Objects;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
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
public class BankStatementAndLineAndRefId
{
	private static final Logger logger = LogManager.getLogger(BankStatementAndLineAndRefId.class);

	public static BankStatementAndLineAndRefId ofRepoIdsOrNull(
			final int bankStatementRepoId,
			final int bankStatementLineRepoId,
			final int bankStatementLineRefRepoId)
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
		if (bankStatementRepoId <= 0)
		{
			logger.warn("ofRepoIdsOrNull: Possible development error: bankStatementLineRepoId={} while bankStatementRepoId={}. Returning null", bankStatementLineRepoId, bankStatementRepoId);
			return null;
		}

		return ofRepoIds(bankStatementRepoId, bankStatementLineRepoId, bankStatementLineRefRepoId);
	}

	public static BankStatementAndLineAndRefId ofRepoIds(
			final int bankStatementRepoId,
			final int bankStatementLineRepoId,
			final int bankStatementLineRefRepoId)
	{
		return of(
				BankStatementId.ofRepoId(bankStatementRepoId),
				BankStatementLineId.ofRepoId(bankStatementLineRepoId),
				BankStatementLineRefId.ofRepoId(bankStatementLineRefRepoId));
	}

	public static BankStatementAndLineAndRefId of(
			final BankStatementId bankStatementId,
			final BankStatementLineId bankStatementLineId,
			final BankStatementLineRefId bankStatementLineRefId)
	{
		return new BankStatementAndLineAndRefId(bankStatementId, bankStatementLineId, bankStatementLineRefId);
	}

	BankStatementId bankStatementId;
	BankStatementLineId bankStatementLineId;
	BankStatementLineRefId bankStatementLineRefId;

	private BankStatementAndLineAndRefId(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final BankStatementLineId bankStatementLineId,
			@NonNull final BankStatementLineRefId bankStatementLineRefId)
	{
		this.bankStatementId = bankStatementId;
		this.bankStatementLineId = bankStatementLineId;
		this.bankStatementLineRefId = bankStatementLineRefId;
	}

	public static boolean equals(final BankStatementAndLineAndRefId o1, final BankStatementAndLineAndRefId o2)
	{
		return Objects.equals(o1, o2);
	}
}
