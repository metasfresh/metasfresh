/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.metasfresh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemMetasfreshConfigId implements IExternalSystemChildConfigId
{
	int repoId;

	@JsonCreator
	@NonNull
	public static ExternalSystemMetasfreshConfigId ofRepoId(final int repoId)
	{
		return new ExternalSystemMetasfreshConfigId(repoId);
	}

	@Nullable
	public static ExternalSystemMetasfreshConfigId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ExternalSystemMetasfreshConfigId(repoId) : null;
	}

	public static ExternalSystemMetasfreshConfigId cast(@NonNull final IExternalSystemChildConfigId id)
	{
		return (ExternalSystemMetasfreshConfigId)id;
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	private ExternalSystemMetasfreshConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "ExternalSystem_Config_Metasfresh_ID");
	}

	@Override
	public ExternalSystemType getType()
	{
		return ExternalSystemType.Metasfresh;
	}
}
