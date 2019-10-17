package de.metas.material.event.stock;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.process.PInstanceId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2019 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public final class ResetStockPInstanceId
{
	@JsonCreator
	public static ResetStockPInstanceId ofRepoId(final int repoId)
	{
		return new ResetStockPInstanceId(repoId);
	}

	public static ResetStockPInstanceId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ResetStockPInstanceId(repoId) : null;
	}

	public static ResetStockPInstanceId ofPInstanceId(@NonNull final PInstanceId pinstanceId)
	{
		return ofRepoId(pinstanceId.getRepoId());
	}

	public static int toRepoId(@Nullable final ResetStockPInstanceId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private ResetStockPInstanceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
