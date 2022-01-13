/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class RevolutPaymentExportId implements RepoIdAware
{
	@JsonCreator
	public static RevolutPaymentExportId ofRepoId(final int repoId)
	{
		return new RevolutPaymentExportId(repoId);
	}

	@Nullable
	public static RevolutPaymentExportId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new RevolutPaymentExportId(repoId) : null;
	}

	public static int toRepoId(@Nullable final RevolutPaymentExportId RevolutPaymentExportId)
	{
		return RevolutPaymentExportId != null ? RevolutPaymentExportId.getRepoId() : -1;
	}

	int repoId;

	private RevolutPaymentExportId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "Revolut_Payment_Export_ID");
	}

	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
