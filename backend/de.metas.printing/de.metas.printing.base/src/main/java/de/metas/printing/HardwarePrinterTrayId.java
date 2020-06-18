package de.metas.printing;

import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
public class HardwarePrinterTrayId implements RepoIdAware
{
	int repoId;

	@NonNull
	HardwarePrinterId printerId;

	public static HardwarePrinterTrayId cast(@Nullable final RepoIdAware repoIdAware)
	{
		return (HardwarePrinterTrayId)repoIdAware;
	}

	public static HardwarePrinterTrayId ofRepoId(@NonNull final HardwarePrinterId printerId, final int trayId)
	{
		return new HardwarePrinterTrayId(printerId, trayId);
	}

	public static HardwarePrinterTrayId ofRepoId(final int printerId, final int trayId)
	{
		return new HardwarePrinterTrayId(HardwarePrinterId.ofRepoId(printerId), trayId);
	}

	public static HardwarePrinterTrayId ofRepoIdOrNull(
			@Nullable final Integer printerId,
			@Nullable final Integer trayId)
	{
		return printerId != null && printerId > 0 && trayId != null && trayId > 0
				? ofRepoId(printerId, trayId)
				: null;
	}

	public static HardwarePrinterTrayId ofRepoIdOrNull(
			@Nullable final HardwarePrinterId printerId,
			final int trayId)
	{
		return printerId != null && trayId > 0 ? ofRepoId(printerId, trayId) : null;
	}

	private HardwarePrinterTrayId(@NonNull final HardwarePrinterId printerId, final int trayId)
	{
		this.repoId = Check.assumeGreaterThanZero(trayId, "trayId");
		this.printerId = printerId;
	}

	public static int toRepoId(@Nullable final HardwarePrinterTrayId trayId)
	{
		return trayId != null ? trayId.getRepoId() : -1;
	}

}
