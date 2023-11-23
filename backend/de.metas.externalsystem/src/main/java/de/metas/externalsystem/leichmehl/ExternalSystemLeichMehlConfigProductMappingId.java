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

package de.metas.externalsystem.leichmehl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl_ProductMapping;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemLeichMehlConfigProductMappingId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	@NonNull
	public static ExternalSystemLeichMehlConfigProductMappingId ofRepoId(final int repoId)
	{
		return new ExternalSystemLeichMehlConfigProductMappingId(repoId);
	}

	@Nullable
	public static ExternalSystemLeichMehlConfigProductMappingId ofRepoIdOrNull(final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ExternalSystemLeichMehlConfigProductMappingId(repoId) : null;
	}

	public static int toRepoId(@Nullable final ExternalSystemLeichMehlConfigProductMappingId configProductMappingId)
	{
		return configProductMappingId != null ? configProductMappingId.getRepoId() : -1;
	}

	private ExternalSystemLeichMehlConfigProductMappingId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
