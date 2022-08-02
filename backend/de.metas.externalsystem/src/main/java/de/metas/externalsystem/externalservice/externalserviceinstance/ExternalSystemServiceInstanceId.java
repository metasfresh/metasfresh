/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.externalservice.externalserviceinstance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.model.I_ExternalSystem_Service_Instance;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemServiceInstanceId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	@NonNull
	public static ExternalSystemServiceInstanceId ofRepoId(final int repoId)
	{
		return new ExternalSystemServiceInstanceId(repoId);
	}

	@Nullable
	public static ExternalSystemServiceInstanceId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ExternalSystemServiceInstanceId(repoId) : null;
	}

	private ExternalSystemServiceInstanceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_ExternalSystem_Service_Instance.COLUMNNAME_ExternalSystem_Service_Instance_ID);
	}

	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
