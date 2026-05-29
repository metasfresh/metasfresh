/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.externalsystem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class ExternalSystemId implements RepoIdAware
{
	int repoId;

	public static ExternalSystemId ofRepoId(final int repoId)
	{
		return new ExternalSystemId(repoId);
	}

	@Nullable
	public static ExternalSystemId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ExternalSystemId(repoId) : null;
	}

	@Nullable
	public static ExternalSystemId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ExternalSystemId(repoId) : null;
	}

	public static Optional<ExternalSystemId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@JsonCreator
	public static ExternalSystemId ofObject(@NonNull final Object object)
	{
		return RepoIdAwares.ofObject(object, ExternalSystemId.class, ExternalSystemId::ofRepoId);
	}

	public static int toRepoId(@Nullable final ExternalSystemId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private ExternalSystemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_ExternalSystem.COLUMNNAME_ExternalSystem_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final ExternalSystemId o1, @Nullable final ExternalSystemId o2)
	{
		return Objects.equals(o1, o2);
	}
}
