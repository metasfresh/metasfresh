package de.metas.printing;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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
public class HardwareTrayId implements RepoIdAware
{
	int repoId;

	@NonNull
	HardwarePrinterId printerId;

	public static HardwareTrayId cast(@Nullable final RepoIdAware repoIdAware)
	{
		return (HardwareTrayId)repoIdAware;
	}

	public static HardwareTrayId ofRepoId(@NonNull final HardwarePrinterId printerId, final int trayId)
	{
		return new HardwareTrayId(printerId, trayId);
	}

	public static HardwareTrayId ofRepoId(final int printerId, final int trayId)
	{
		return new HardwareTrayId(HardwarePrinterId.ofRepoId(printerId), trayId);
	}

	public static HardwareTrayId ofRepoIdOrNull(
			@Nullable final Integer printerId,
			@Nullable final Integer trayId)
	{
		return printerId != null && printerId > 0 && trayId != null && trayId > 0
				? ofRepoId(printerId, trayId)
				: null;
	}

	public static HardwareTrayId ofRepoIdOrNull(
			@Nullable final HardwarePrinterId printerId,
			final int trayId)
	{
		return printerId != null && trayId > 0 ? ofRepoId(printerId, trayId) : null;
	}

	private HardwareTrayId(@NonNull final HardwarePrinterId printerId, final int trayId)
	{
		this.repoId = Check.assumeGreaterThanZero(trayId, "trayId");
		this.printerId = printerId;
	}

	public static int toRepoId(@Nullable final HardwareTrayId trayId)
	{
		return trayId != null ? trayId.getRepoId() : -1;
	}

}
