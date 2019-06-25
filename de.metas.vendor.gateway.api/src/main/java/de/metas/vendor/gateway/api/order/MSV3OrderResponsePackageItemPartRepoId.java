package de.metas.vendor.gateway.api.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.api
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

/**
 * @see de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil
 */
@Value
public class MSV3OrderResponsePackageItemPartRepoId implements RepoIdAware
{
	@JsonCreator
	public static MSV3OrderResponsePackageItemPartRepoId ofRepoId(final int repoId)
	{
		return new MSV3OrderResponsePackageItemPartRepoId(repoId);
	}

	public static MSV3OrderResponsePackageItemPartRepoId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new MSV3OrderResponsePackageItemPartRepoId(repoId) : null;
	}

	public static int getRepoId(final MSV3OrderResponsePackageItemPartRepoId id)
	{
		return getRepoIdOr(id, -1);
	}

	public static int getRepoIdOr(final MSV3OrderResponsePackageItemPartRepoId id, final int defaultValue)
	{
		return id != null ? id.getRepoId() : defaultValue;
	}

	int repoId;

	private MSV3OrderResponsePackageItemPartRepoId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
