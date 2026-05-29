/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.document.archive.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
public class DocOutboundConfigId implements RepoIdAware
{
	int repoId;

	public static DocOutboundConfigId ofRepoId(final int repoId)
	{
		return new DocOutboundConfigId(repoId);
	}

	@Nullable
	public static DocOutboundConfigId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DocOutboundConfigId(repoId) : null;
	}

	@Nullable
	public static DocOutboundConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DocOutboundConfigId(repoId) : null;
	}

	public static Optional<DocOutboundConfigId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@JsonCreator
	public static DocOutboundConfigId ofObject(@NonNull final Object object)
	{
		return RepoIdAwares.ofObject(object, DocOutboundConfigId.class, DocOutboundConfigId::ofRepoId);
	}

	public static int toRepoId(@Nullable final DocOutboundConfigId docOutboundConfigId)
	{
		return toRepoIdOr(docOutboundConfigId, -1);
	}

	public static int toRepoIdOr(@Nullable final DocOutboundConfigId docOutboundConfigId, final int defaultValue)
	{
		return docOutboundConfigId != null ? docOutboundConfigId.getRepoId() : defaultValue;
	}

	private DocOutboundConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Doc_Outbound_Config_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final DocOutboundConfigId o1, @Nullable final DocOutboundConfigId o2)
	{
		return Objects.equals(o1, o2);
	}
}
