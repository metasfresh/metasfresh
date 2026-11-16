package de.metas.payment.sepa.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
public class SEPAExportLineId implements RepoIdAware
{
	int repoId;

	private SEPAExportLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "SEPA_Export_Line_ID");
	}

	@JsonCreator
	public static SEPAExportLineId ofRepoId(final int repoId)
	{
		return new SEPAExportLineId(repoId);
	}

	@Nullable
	public static SEPAExportLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new SEPAExportLineId(repoId) : null;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
